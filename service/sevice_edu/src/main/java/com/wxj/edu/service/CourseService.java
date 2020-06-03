package com.wxj.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxj.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxj.edu.entity.FrontVo.CourseFrontVo;
import com.wxj.edu.entity.FrontVo.CourseWebVo;
import com.wxj.edu.entity.vo.CourseInfoVo;
import com.wxj.edu.entity.vo.CoursePublicVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-20
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

     void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublicVo getPublishCourseInfo(String courseId);

    void removeCourse(String id);
// 前端分页，排序
    Map<String, Object> getFrontCourseList(Page<Course> pageCourse, CourseFrontVo courseFrontVo);
 //前端课程详情查询
   List<CourseWebVo>  getBaseCourseInfo(String courseId);
}
