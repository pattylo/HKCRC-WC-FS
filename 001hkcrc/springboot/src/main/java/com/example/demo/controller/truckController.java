package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.truck;
import com.example.demo.mapper.truckMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController //返回Json的Controller
@RequestMapping("/truck") //接口的路由
public class truckController {

  @Resource
  truckMapper truckMapper; //一般会写一个service类，control引入service， service引入mapper

  @PostMapping
  public Result<?> save(@RequestBody truck truck){ //前台传过来的对象映射成实体
    truckMapper.insert(truck); //将数据传输到数据库
    return Result.success();
  }

  @GetMapping
  public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(defaultValue = "") String search){
    LambdaQueryWrapper<truck> wrapper = Wrappers.<truck>lambdaQuery();

    if(StrUtil.isNotBlank(search))
    {
      wrapper.like(truck::getTruck,search);
    }
    Page<truck> truckPage = truckMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    return Result.success(truckPage);
  }

  @PutMapping
  public Result<?> update(@RequestBody truck truck){ //前台传过来的对象映射成实体
    truckMapper.updateById(truck); //将数据传输到数据库
    return Result.success();
  }

  @DeleteMapping("/{id}")
  public Result<?> update(@PathVariable long id){ //前台传过来的对象映射成实体
    truckMapper.deleteById(id);
    return Result.success();
  }

}
