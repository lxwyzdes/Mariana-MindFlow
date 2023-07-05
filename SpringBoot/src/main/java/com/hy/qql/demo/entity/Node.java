package com.hy.qql.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Node {
    @TableId(value = "node_id", type = IdType.AUTO)
    private Integer nodeId;
    private String name;
    private String type;
    private String ico;
    private Integer executorId;
    private String executorName;
    private Integer flowModelId;
}
