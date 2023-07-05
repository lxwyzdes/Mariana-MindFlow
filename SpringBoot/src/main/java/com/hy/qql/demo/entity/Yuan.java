package com.hy.qql.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
  @TableName("sys_yuan")
@ApiModel(value = "Yuan对象", description = "")
public class Yuan implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String yname;

    private Integer age;

    private String gender;

    private String address;

    private Integer xid;

    private Integer gid;

    private Boolean state;

    @TableField(exist = false)
    private String sname;

    @TableField(exist = false)
    private String xname;

}
