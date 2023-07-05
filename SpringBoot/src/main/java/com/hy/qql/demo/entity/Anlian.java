package com.hy.qql.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author qql
 * @since 2022-05-07
 */
@Getter
@Setter
  @TableName("sys_anlian")
@ApiModel(value = "Anlian对象", description = "")
public class Anlian implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String src;

    private String title;

    private String author;

    private String releaseTime;

    private String introduce;

    private String bookType;


}
