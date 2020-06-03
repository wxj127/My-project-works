package com.wxj.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxj.R;
import com.wxj.edu.entity.Course;
import com.wxj.edu.entity.Teacher;
import com.wxj.edu.service.CourseService;
import com.wxj.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/IndexFront")
public class IndexFrontController {
    //查询前8条热门课程 前四个名师
    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;


    @GetMapping("index")
    public R index(){
        //查询前8条热门课程
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id").last("limit 8");
        List<Course> courseList = courseService.list(courseQueryWrapper);

        //查询前四个名师
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id").last("limit 4");
        List<Teacher> teacherList = teacherService.list(teacherQueryWrapper);

        return R.Ok().data("courseList",courseList).data("teacherList",teacherList);
    }

}
