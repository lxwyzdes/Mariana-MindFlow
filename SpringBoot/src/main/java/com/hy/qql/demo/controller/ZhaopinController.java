package com.hy.qql.demo.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.qql.demo.service.IZhaopinService;
import com.hy.qql.demo.entity.Zhaopin;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qql
 * @since 2022-06-08
 */
@RestController
@RequestMapping("/zhaopin")
public class ZhaopinController {
@Resource
private IZhaopinService zhaopinService;
// 新增或者更新
@PostMapping
public Result save(@RequestBody Zhaopin zhaopin) {
    zhaopinService.saveOrUpdate(zhaopin);
        return Result.success();
        }
@DeleteMapping("/{id}")
public Result delete(@PathVariable Integer id) {
    zhaopinService.removeById(id);
        return Result.success();
        }
@PostMapping("/del/batch")
public Result deleteBatch(@RequestBody List<Integer> ids) {
    zhaopinService.removeByIds(ids);
        return Result.success();
        }
@GetMapping
public Result findAll() {
        return Result.success(zhaopinService.list());
        }
@GetMapping("/{id}")
public Result findOne(@PathVariable Integer id) {
        return Result.success(zhaopinService.getById(id));
        }
@GetMapping("/page")
public Result findPage(@RequestParam Integer pageNum,
@RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "") String title) {
       Page<Zhaopin> page = zhaopinService.findPage(new Page<>(pageNum,pageSize),title);
       return Result.success(page);
        }
}

