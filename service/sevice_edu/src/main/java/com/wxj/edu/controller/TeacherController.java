package com.wxj.edu.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxj.R;
import com.wxj.edu.entity.vo.TeacherQuery;
import com.wxj.edu.entity.Teacher;
import com.wxj.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-14
 */
@Api( tags= "讲师管理")
@RestController
@RequestMapping("/edu/teacher")

public class TeacherController {
 @Autowired
 private TeacherService teacherService;

    //1.查询所有
    @ApiOperation(value = "查询所有讲师")
  @GetMapping("findAll")
  public R findAll(){
      List<Teacher> teachers = teacherService.list(null);
      return R.Ok().data("items",teachers);
  }
  //2.逻辑删除
    @ApiOperation(value = "根据Id 逻辑删除讲师")
    @DeleteMapping("{id}")                                              //必填项
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable("id") String id){
        boolean b = teacherService.removeById(id);
        if (b){
          return  R.Ok();
        }else {
            return R.error();
        }
    }
    //分页查询讲师
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current,
                             @PathVariable("limit") long limit){
        Page<Teacher> page= new Page<>(current,limit);
        //查询的都封装到Teacher
         teacherService.page(page,null);
        long total = page.getTotal(); //获取总记录数
        List<Teacher> teachers = page.getRecords(); //数据list集合
        return R.Ok().data("total",total).data("teachers",teachers);
    }
    //条件组合分页查询讲师
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageListTeacherCondition(@PathVariable("current") long current,
                                      @PathVariable("limit") long limit,
                                      @RequestBody(required = false) TeacherQuery teacherQuery){

        Page<Teacher> pageTeacher= new Page<>(current,limit);
        //查询的都封装到Teacher
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        //判断条件值是否为空
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)){
          queryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }
         queryWrapper.orderByDesc("gmt_create");
        teacherService.page(pageTeacher,queryWrapper);
        long total = pageTeacher.getTotal(); //获取总记录数
        List<Teacher> teachers = pageTeacher.getRecords(); //数据list集合
        return R.Ok().data("total",total).data("teachers",teachers);
    }
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        if (save){
            return R.Ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation(value = "根据Id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id){
        Teacher teacher = teacherService.getById(id);
            return R.Ok().data("teacher",teacher);
    }
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher){
        boolean b = teacherService.updateById(teacher);
        if (b){
            return R.Ok();
        }else {
            return R.error();
        }

    }

}

