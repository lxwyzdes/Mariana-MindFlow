package com.hy.qql.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.qql.demo.common.Result;
import com.hy.qql.demo.entity.*;
import com.hy.qql.demo.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class FlowModelController {
    @Autowired
    private FlowModelMapper flowModelMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LineMapper lineMapper;
    @Autowired
    private NodeMapper nodeMapper;
    @Autowired
    private FlowInstanceMapper flowInstanceMapper;

    @RequestMapping("/getMyFlow")
    public Result getMyFlow(@RequestBody Integer userId, @RequestBody String query) {
        QueryWrapper<FlowModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.and(wrapper ->wrapper.like("flow_model_name",query).or().like("description",query));
        List<FlowModel> modelList = flowModelMapper.selectList(queryWrapper);

        return Result.success(modelList);
    }

    @RequestMapping("/setRecommand")
    public Result setRecommand(@RequestBody Integer flowModelId,@RequestBody Integer userId){
        FlowModel flowModel = flowModelMapper.selectById(flowModelId);
        flowModel.setFlowModelId(null);
        flowModel.setCreateTime(new Date().getTime());
        flowModel.setUpdateTime(new Date().getTime());
        flowModel.setCreateUserId(userId);
        flowModel.setCreateUserName(userMapper.selectById(userId).getUsername());
        int insert = flowModelMapper.insert(flowModel);

        //先把线复制一份，然后一个个的复制节点同时修改线
        QueryWrapper<Line> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flow_model_id", flowModelId);
        List<Line> lineList = lineMapper.selectList(queryWrapper);
        for (Line line : lineList) {
            line.setFlowModelId(flowModel.getFlowModelId());
            line.setLineId(null);
        }
        QueryWrapper<Node> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("flow_model_id", flowModelId);
        List<Node> nodeList=nodeMapper.selectList(queryWrapper1);
        for(Node node:nodeList){
            node.setFlowModelId(flowModel.getFlowModelId());
            Integer nodeIdBefore=node.getNodeId();
            node.setNodeId(null);
            nodeMapper.insert(node);
            lineList.stream().filter(o->o.getFrom().equals(nodeIdBefore))
                    .forEach(f -> f.setFrom(node.getNodeId()));
            lineList.stream().filter(o->o.getTo().equals(nodeIdBefore))
                    .forEach(f -> f.setTo(node.getNodeId()));
        }
        for(Line line:lineList){
            lineMapper.insert(line);
        }

        return Result.success(flowModel);
    }

    @RequestMapping("/deleteModel")
    public Result deleteModel(@RequestBody Integer flowModelId){
        QueryWrapper<FlowInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flow_model_id",flowModelId);
        queryWrapper.and(wrapper ->wrapper.eq("state",0).or().eq("state",1));
        List<FlowInstance> flowInstances = flowInstanceMapper.selectList(queryWrapper);
        if(flowInstances.size()>0){
            return Result.error("400","有任务待开始/进行中的流程不允许删除");
        }
        flowModelMapper.deleteById(flowModelId);
        return Result.success();
    }



}
