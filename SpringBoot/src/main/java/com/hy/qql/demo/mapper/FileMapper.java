package com.hy.qql.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.qql.demo.entity.Files;

import java.util.List;

public interface FileMapper extends BaseMapper<Files> {
   List<Files> findAll();
}
