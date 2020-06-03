package com.wxj.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxj.R;
import com.wxj.edu.Client.test;
import com.wxj.edu.entity.Course;
import com.wxj.edu.entity.Teacher;
import com.wxj.edu.service.CourseService;
import com.wxj.edu.service.TeacherService;
import com.wxj.orderVo.MemberOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu/teacherfront")
public class TeacherFrontController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;


    //1.分页查询教师
    @PostMapping("getTeacherFront/{page}/{limit}")
    public R getTeacherFront(@PathVariable long page,@PathVariable long limit){
        Page<Teacher> teacherPage = new Page<>(page,limit);
          Map<String,Object> map=teacherService.getTeacherFront(teacherPage);
        return R.Ok().data(map);
    }
    //查询讲师详细
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        Teacher teacher = teacherService.getById(teacherId);
        QueryWrapper<Course> wrapper= new QueryWrapper();
        wrapper.eq("teacher_id",teacherId);
        List<Course> courseList = courseService.list(wrapper);
        return R.Ok().data("teacher",teacher).data("courseList",courseList);
    }
    @GetMapping("getTeacher/{Id}")
    public R FrontInfo(@PathVariable String Id){

        MemberOrder memberOrder = teacherService.ss(Id);

        return R.Ok().data("aa",memberOrder);
    }


}
