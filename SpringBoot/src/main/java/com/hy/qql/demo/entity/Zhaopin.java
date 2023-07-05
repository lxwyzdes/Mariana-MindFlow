package com.hy.qql.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 * 
 * </p>
 *
 * @author qql
 * @since 2022-06-08
 */
@Getter
@Setter
  @TableName("sys_zhaopin")
@ApiModel(value = "Zhaopin对象", description = "")
public class Zhaopin implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String src;

    private String title;

    private String author;

    private String introduce;

    private String xingzhi;

    private String createtime;

    private Integer monney;

    private String gangwei;
    private String phone;

    private String ccc;

    private String xxx;

    private String address;

}
