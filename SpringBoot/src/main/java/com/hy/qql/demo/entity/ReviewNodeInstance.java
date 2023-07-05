package com.hy.qql.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewNodeInstance {
    @TableId(value = "review_node_instance_id", type = IdType.AUTO)
    private Integer reviewNodeInstanceId;
    private Long createTime;
    private Integer flowInstanceId;
    private Integer sequence;
    private String result;
    private String opinion;
}
