package com.hy.qql.demo;

import com.hy.qql.demo.entity.FlowModel;
import com.hy.qql.demo.mapper.FlowModelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
    @Autowired
    FlowModelMapper flowModelMapper;
    @Test
    void contextLoads() {
        FlowModel flowModel = flowModelMapper.selectById(1);
        flowModel.setFlowModelId(null);
        int insert = flowModelMapper.insert(flowModel);
        System.out.println(flowModel.getFlowModelId());
    }

}
