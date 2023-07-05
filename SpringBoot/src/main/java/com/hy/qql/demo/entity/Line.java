package com.hy.qql.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Line {
    @TableId(value = "line_id", type = IdType.AUTO)
    private Integer lineId;
    private Integer from;
    private Integer to;
    private String label;
    private Integer flowModelId;
}
