package com.wxj.edu.controller;


import com.wxj.R;
import com.wxj.edu.entity.Course;
import com.wxj.edu.entity.vo.CourseInfoVo;
import com.wxj.edu.entity.vo.CoursePublicVo;
import com.wxj.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edu/course")

public class CourseController {
    @Autowired
    private CourseService courseService;


    @DeleteMapping("deleteCourse/{id}")
    public R deleteCourse(@PathVariable String id){

        courseService.removeCourse(id);
        return R.Ok();
    }


    @GetMapping("getCourseList")
    public R getCourseList() {
        List<Course> courseList = courseService.list(null);
     return R.Ok().data("courseList", courseList);
    }
    //添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
       String id  = courseService.saveCourseInfo(courseInfoVo);
          return R.Ok().data("courseId",id);
    }
    //根据Id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId){
       CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.Ok().data("courseInfoVo",courseInfoVo);
    }
    //修改课程
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
       courseService.updateCourseInfo(courseInfoVo);
        return R.Ok();
    }
    //根据课程Id查询课程最终确认信息
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable("courseId") String courseId){
        CoursePublicVo coursePublicVo = courseService.getPublishCourseInfo(courseId);
        return R.Ok().data("coursePublicVo",coursePublicVo);
    }
 //课程最终发布
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable("id") String id){
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");  //将状态改为发布
        courseService.updateById(course);
        return R.Ok();
    }


}

