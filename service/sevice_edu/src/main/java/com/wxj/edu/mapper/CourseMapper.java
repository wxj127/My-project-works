package com.wxj.edu.mapper;

import com.wxj.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxj.edu.entity.FrontVo.CourseWebVo;
import com.wxj.edu.entity.vo.CoursePublicVo;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-05-20
 */
public interface CourseMapper extends BaseMapper<Course> {
    public CoursePublicVo getPublicCourse(String courseId);

    List<CourseWebVo> getBaseCourseInfo(String courseId);
}
