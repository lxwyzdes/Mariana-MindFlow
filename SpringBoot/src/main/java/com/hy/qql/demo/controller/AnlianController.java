package com.hy.qql.demo.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.qql.demo.service.IAnlianService;
import com.hy.qql.demo.entity.Anlian;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qql
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/anlian")
public class AnlianController {
@Resource
private IAnlianService anlianService;
// 新增或者更新
@PostMapping
public Result save(@RequestBody Anlian anlian) {
    anlianService.saveOrUpdate(anlian);
        return Result.success();
        }
@DeleteMapping("/{id}")
public Result delete(@PathVariable Integer id) {
    anlianService.removeById(id);
        return Result.success();
        }

@PostMapping("/del/batch")
public Result deleteBatch(@RequestBody List<Integer> ids) {
    anlianService.removeByIds(ids);
        return Result.success();
        }
@GetMapping
public Result findAll() {
        return Result.success(anlianService.list());
        }
@GetMapping("/{id}")
public Result findOne(@PathVariable Integer id) {
        return Result.success(anlianService.getById(id));
        }
@GetMapping("/page")
public Result findPage(@RequestParam Integer pageNum,
@RequestParam Integer pageSize) {
        QueryWrapper<Anlian> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(anlianService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }
}

