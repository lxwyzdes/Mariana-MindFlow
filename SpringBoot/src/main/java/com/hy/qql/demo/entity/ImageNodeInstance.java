package com.hy.qql.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageNodeInstance {
    @TableId(value = "image_node_instance_id", type = IdType.AUTO)
    private Integer imageNodeInstanceId;
    private Long createTime;
    private Integer flowInstanceId;
    private Integer sequence;
    private String positivePrompt;
    private String negativePrompt;
    private String title;
    private byte[] result;
    private String bottomModel;
    private String lora;
    private Integer width;
    private Integer height;
    private String samplingMethod;
    private Integer seed;
    private double correlation;
    private Integer step;
    private Integer imageNum;
}
