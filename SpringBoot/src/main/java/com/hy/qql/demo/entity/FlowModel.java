package com.hy.qql.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlowModel {
    @TableId(value = "flow_model_id", type = IdType.AUTO)
    private Integer flowModelId;
    private String flowModelName;
    private Long createTime;
    private Long updateTime;
    private Integer createUserId;
    private String description;
    private String createUserName;
    private Integer nodeNum;
}
