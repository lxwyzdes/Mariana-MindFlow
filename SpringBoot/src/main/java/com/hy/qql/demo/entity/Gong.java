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
 * @since 2022-05-10
 */
@Getter
@Setter
  @TableName("sys_gong")
@ApiModel(value = "Gong对象", description = "")
public class Gong implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "gid", type = IdType.AUTO)
      private Integer gid;

    private String gname;


}
