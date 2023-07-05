package com.hy.qql.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.qql.demo.common.Result;
import com.hy.qql.demo.entity.*;
import com.hy.qql.demo.mapper.*;
import com.hy.qql.demo.utils.OpenAIAPI;
import com.hy.qql.demo.utils.StableDiffusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class FlowInstanceController {
    @Autowired
    private FlowInstanceMapper flowInstanceMapper;
    @Autowired
    private FlowModelMapper flowModelMapper;

    @Autowired
    private LineMapper lineMapper;
    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private TextNodeInstanceMapper textNodeInstanceMapper;
    @Autowired
    private ImageNodeInstanceMapper imageNodeInstanceMapper;
    @Autowired
    private ReviewNodeInstanceMapper reviewNodeInstanceMapper;

    @RequestMapping("/getToBeginNum")
    public Result getToBeginNum(@RequestBody Integer userId) {
        QueryWrapper<FlowInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 0)
                .eq("create_user_id",userId);
        List<FlowInstance> modelList = flowInstanceMapper.selectList(queryWrapper);
        return Result.success(modelList);
    }

    @RequestMapping("/getOnGoingNum")
    public Result getOnGoingNum(@RequestBody Integer userId) {
        QueryWrapper<FlowInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1)
                .eq("create_user_id",userId);
        List<FlowInstance> modelList = flowInstanceMapper.selectList(queryWrapper);
        return Result.success(modelList);
    }

    @RequestMapping("/getCompletedNum")
    public Result getCompletedNum(@RequestBody Integer userId) {
        QueryWrapper<FlowInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 2)
                .eq("create_user_id",userId);
        List<FlowInstance> modelList = flowInstanceMapper.selectList(queryWrapper);
        return Result.success(modelList);
    }

    @RequestMapping("/initFlow")
    public Result initFlow(@RequestBody Integer flowModelId,@RequestBody String name,@RequestBody String description){
        FlowModel flowModel = flowModelMapper.selectById(flowModelId);
        FlowInstance flowInstance=new FlowInstance();
        flowInstance.setFlowModelId(flowModelId);
        flowInstance.setCreateTime(new Date().getTime());
        flowInstance.setUpdateTime(new Date().getTime());
        flowInstance.setName(name);
        flowInstance.setDescription(description);
        flowInstance.setState(1);
        flowInstance.setNextNodeSequence(1);
        flowInstance.setFlowLength(flowModel.getNodeNum());
        flowInstance.setCreateUserId(flowModel.getCreateUserId());
        QueryWrapper<Node> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flow_model_id", flowModelId);
        List<Node> nodeList = nodeMapper.selectList(queryWrapper);
        QueryWrapper<Line> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("flow_model_id", flowModelId);
        List<Line> lineList = lineMapper.selectList(queryWrapper1);
        Node initNode=null;
        for(Node node:nodeList){
            QueryWrapper<Line> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("to",node.getNodeId()).eq("flow_model_id",flowModelId);
            if(lineMapper.selectList(queryWrapper2).size()==0){
                initNode=node;
            }
        }
        flowInstance.setNextNodeId(initNode.getNodeId());
        flowInstance.setNextNodeExecutorId(initNode.getExecutorId());
        flowInstanceMapper.insert(flowInstance);
        return Result.success(flowInstance);
    }

    @RequestMapping("/deleteInstance")
    public Result deleteInstance(@RequestBody Integer flowInstanceId){
        FlowInstance flowInstance = flowInstanceMapper.selectById(flowInstanceId);
        if(flowInstance.getState()==0){
            flowInstanceMapper.deleteById(flowInstanceId);
        }else {
            return Result.error("400","请先中止该任务");
        }
        return Result.success();
    }

    @RequestMapping("/discontinueInstance")
    public Result discontinueInstance(@RequestBody Integer flowInstanceId){
        FlowInstance flowInstance = flowInstanceMapper.selectById(flowInstanceId);
        flowInstance.setState(0);
        flowInstanceMapper.updateById(flowInstance);
        return Result.success();
    }

    @RequestMapping("/myInstance")
    public Result myInstance(@RequestBody Integer userId,@RequestBody String query){
        QueryWrapper<FlowInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper ->wrapper.eq("create_user_id",userId).or().eq("next_node_executor_id",userId));
        queryWrapper.and(wrapper ->wrapper.like("name",query).or().like("description",query));
        List<FlowInstance> flowInstances = flowInstanceMapper.selectList(queryWrapper);
        return Result.success(flowInstances);
    }

    @RequestMapping("/finalResult")
    public Result finalResult(@RequestBody Integer flowInstanceId){
        FlowInstance flowInstance = flowInstanceMapper.selectById(flowInstanceId);
        List resultList=null;
        int i= Integer.parseInt(null);
        for (i=1;i<flowInstance.getNextNodeSequence();i++){
            QueryWrapper<ReviewNodeInstance> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("flow_instance_id",flowInstanceId).eq("sequence",i);
            List<ReviewNodeInstance> reviewNodeInstances = reviewNodeInstanceMapper.selectList(queryWrapper1);
            resultList.add(reviewNodeInstances);

            QueryWrapper<TextNodeInstance> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("flow_instance_id",flowInstanceId).eq("sequence",i);
            List<TextNodeInstance> textNodeInstances = textNodeInstanceMapper.selectList(queryWrapper2);
            resultList.add(textNodeInstances);

            QueryWrapper<ImageNodeInstance> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("flow_instance_id",flowInstanceId).eq("sequence",i);
            List<ImageNodeInstance> imageNodeInstances = imageNodeInstanceMapper.selectList(queryWrapper3);
            resultList.add(imageNodeInstances);
        }
        return Result.success(resultList);
    }
    @RequestMapping("/submitReview")
    public Result submitReview(@RequestBody ReviewNodeInstance reviewNodeInstance){
        reviewNodeInstance.setReviewNodeInstanceId(null);
        FlowInstance flowInstance = flowInstanceMapper.selectById(reviewNodeInstance.getFlowInstanceId());
        reviewNodeInstance.setSequence(flowInstance.getNextNodeSequence());
        reviewNodeInstanceMapper.insert(reviewNodeInstance);
        flowInstance.setUpdateTime(new Date().getTime());
        if(flowInstance.getNextNodeSequence()>=flowInstance.getFlowLength()){
            flowInstance.setState(2);
        }else {
            if (reviewNodeInstance.getResult().equals("pass")){
                QueryWrapper<Line> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("from",flowInstance.getNextNodeId()).eq("flow_model_id",flowInstance.getFlowModelId());
                List<Line> lineList = lineMapper.selectList(queryWrapper);
                for (Line line:lineList){
                    Node node = nodeMapper.selectById(line.getTo());
                    flowInstance.setNextNodeId(node.getNodeId());
                    flowInstance.setNextNodeExecutorId(node.getExecutorId());
                }
                flowInstance.setNextNodeSequence(flowInstance.getNextNodeSequence()+1);
            }else {
                QueryWrapper<Line> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("to",flowInstance.getNextNodeId()).eq("flow_model_id",flowInstance.getFlowModelId());
                List<Line> lineList = lineMapper.selectList(queryWrapper);
                for (Line line:lineList){
                    Node node = nodeMapper.selectById(line.getFrom());
                    flowInstance.setNextNodeId(node.getNodeId());
                    flowInstance.setNextNodeExecutorId(node.getExecutorId());
                }
                flowInstance.setNextNodeSequence(flowInstance.getNextNodeSequence()+1);
                flowInstance.setFlowLength(flowInstance.getFlowLength()+2);
            }
        }
        flowInstanceMapper.updateById(flowInstance);
        return Result.success(flowInstance);
    }

    @RequestMapping("/submitText")
    public Result submitText(@RequestBody TextNodeInstance textNodeInstance){
        textNodeInstance.setTextNodeInstanceId(null);
        FlowInstance flowInstance = flowInstanceMapper.selectById(textNodeInstance.getFlowInstanceId());
        textNodeInstance.setSequence(flowInstance.getNextNodeSequence());
        textNodeInstanceMapper.insert(textNodeInstance);
        flowInstance.setUpdateTime(new Date().getTime());
        if(flowInstance.getNextNodeSequence()>=flowInstance.getFlowLength()){
            flowInstance.setState(2);
        }else {
            QueryWrapper<Line> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("from",flowInstance.getNextNodeId()).eq("flow_model_id",flowInstance.getFlowModelId());
            List<Line> lineList = lineMapper.selectList(queryWrapper);
            for (Line line:lineList){
                Node node = nodeMapper.selectById(line.getTo());
                flowInstance.setNextNodeId(node.getNodeId());
                flowInstance.setNextNodeExecutorId(node.getExecutorId());
            }
            flowInstance.setNextNodeSequence(flowInstance.getNextNodeSequence()+1);
        }
        flowInstanceMapper.updateById(flowInstance);
        return Result.success(flowInstance);
    }

    @RequestMapping("/submitImage")
    public Result submitImage(@RequestBody ImageNodeInstance imageNodeInstance){
        imageNodeInstance.setImageNodeInstanceId(null);
        FlowInstance flowInstance = flowInstanceMapper.selectById(imageNodeInstance.getFlowInstanceId());
        imageNodeInstance.setSequence(flowInstance.getNextNodeSequence());
        imageNodeInstanceMapper.insert(imageNodeInstance);
        flowInstance.setUpdateTime(new Date().getTime());
        if(flowInstance.getNextNodeSequence()>=flowInstance.getFlowLength()){
            flowInstance.setState(2);
        }else {
            QueryWrapper<Line> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("from",flowInstance.getNextNodeId()).eq("flow_model_id",flowInstance.getFlowModelId());
            List<Line> lineList = lineMapper.selectList(queryWrapper);
            for (Line line:lineList){
                Node node = nodeMapper.selectById(line.getTo());
                flowInstance.setNextNodeId(node.getNodeId());
                flowInstance.setNextNodeExecutorId(node.getExecutorId());
            }
            flowInstance.setNextNodeSequence(flowInstance.getNextNodeSequence()+1);
        }
        flowInstanceMapper.updateById(flowInstance);
        return Result.success(flowInstance);
    }

    @RequestMapping("/getGPT")
    public Result getGPT(@RequestBody TextNodeInstance textNodeInstance){
        String chat = OpenAIAPI.chat(textNodeInstance.getPrompt());
        return Result.success(chat);
    }

    @RequestMapping("/getSD")
    public Result getSD(@RequestBody ImageNodeInstance imageNodeInstance){
        String body="{ \"prompt\": \""+imageNodeInstance.getPositivePrompt()+"\" ," +
                "\"negative_prompt\":\""+imageNodeInstance.getNegativePrompt()+"\"," +
                "\"steps\": \""+imageNodeInstance.getStep()+"\" }";
        String imageEncodeByBase64 = StableDiffusion.sendPost("http://127.0.0.1:7860/sdapi/v1/txt2img",body);
        return Result.success(imageEncodeByBase64);
    }

}
