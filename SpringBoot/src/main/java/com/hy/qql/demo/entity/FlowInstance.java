package com.hy.qql.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlowInstance {
    @TableId(value = "flow_instance_id", type = IdType.AUTO)
    private Integer flowInstanceId;
    private Integer flowModelId;
    private Long createTime;
    private Long updateTime;
    private String name;
    private String description;
    private Integer state;
    private Integer nextNodeId;
    private Integer nextNodeSequence;
    private Integer flowLength;
    private Integer createUserId;
    private Integer nextNodeExecutorId;
}
