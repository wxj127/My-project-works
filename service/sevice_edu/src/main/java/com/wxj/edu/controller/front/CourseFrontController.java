package com.wxj.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxj.JwtUtils;
import com.wxj.R;
import com.wxj.edu.Client.OrderClient;
import com.wxj.edu.entity.Course;
import com.wxj.edu.entity.FrontVo.CourseFrontVo;
import com.wxj.edu.entity.FrontVo.CourseWebVo;
import com.wxj.edu.entity.Teacher;
import com.wxj.edu.entity.chapter.ChapterVo;
import com.wxj.edu.service.ChapterService;
import com.wxj.edu.service.CourseService;
import com.wxj.edu.service.TeacherService;
import com.wxj.orderVo.CourseWebOrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu/coursefront")
public class CourseFrontController {
    @Autowired
    private ChapterService ChapterService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private OrderClient orderClient;
    //1.分页查询教师
    @PostMapping("getFrontCourse/{page}/{limit}")
    public R getFrontCourse(@PathVariable long page, @PathVariable long limit,
                            @RequestBody(required = false)  CourseFrontVo courseFrontVo){
        Page<Course> pageCourse = new Page<>(page,limit);
          Map<String,Object> map=courseService.getFrontCourseList(pageCourse,courseFrontVo);
        return R.Ok().data(map);
    }
   //查询课程详情
    @GetMapping("getFrontCourseInfo/{CourseId}")
    public R getFrontCourseInfo(@PathVariable String CourseId, HttpServletRequest request){
     //根据课程id ，编写sql语句查询
        List<CourseWebVo> courseWebVos = courseService.getBaseCourseInfo(CourseId);
        boolean buyCourse = orderClient.isBuyCourse(CourseId, JwtUtils.getMemberIdByJwtToken(request));
        List<ChapterVo> chapterVideoList = ChapterService.getChapterVideoByCourseId(CourseId);
    //根据课程id查询章节和小节
        return R.Ok().data("courseWebVos",courseWebVos).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);
    }

  //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebOrderVo getCourseInfoOrder(@PathVariable String id){
        CourseWebOrderVo courseWebOrderVo = new CourseWebOrderVo();
        List<CourseWebVo> baseCourseInfo = courseService.getBaseCourseInfo(id);
        BeanUtils.copyProperties(baseCourseInfo,courseWebOrderVo);
        return courseWebOrderVo;
    }


}
