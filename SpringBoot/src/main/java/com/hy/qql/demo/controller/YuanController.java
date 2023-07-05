package com.hy.qql.demo.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.qql.demo.service.IYuanService;
import com.hy.qql.demo.entity.Yuan;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qql
 * @since 2022-05-10
 */
@RestController
@RequestMapping("/yuan")
public class YuanController {
@Resource
private IYuanService yuanService;
// 新增或者更新
@PostMapping
public Result save(@RequestBody Yuan yuan) {
    yuanService.saveOrUpdate(yuan);
        return Result.success();
        }
@DeleteMapping("/{id}")
public Result delete(@PathVariable Integer id) {
    yuanService.removeById(id);
        return Result.success();
        }
@PostMapping("/del/batch")
public Result deleteBatch(@RequestBody List<Integer> ids) {
    yuanService.removeByIds(ids);
        return Result.success();
        }
@GetMapping
public Result findAll() {
        return Result.success(yuanService.list());
        }
@GetMapping("/{id}")
public Result findOne(@PathVariable Integer id) {
        return Result.success(yuanService.getById(id));
        }
@GetMapping("/page")
public Result findPage(@RequestParam Integer pageNum,
@RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "")String yname
                       ) {
        Page<Yuan> page = yuanService.findPage(new Page<>(pageNum,pageSize),yname);
        return Result.success(page);
}

@GetMapping("/XueFindAll")
    public Result XueFindAll(){
    return Result.success(yuanService.findAll());
}
@GetMapping("/GoodFindAll")
    public Result GoodFindAll(){
    return Result.success(yuanService.GoodFindAll());
}
}

