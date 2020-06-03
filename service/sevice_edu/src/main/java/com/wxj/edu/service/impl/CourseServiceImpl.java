package com.wxj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxj.edu.entity.Course;
import com.wxj.edu.entity.CourseDescription;
import com.wxj.edu.entity.FrontVo.CourseFrontVo;
import com.wxj.edu.entity.FrontVo.CourseWebVo;
import com.wxj.edu.entity.vo.CourseInfoVo;
import com.wxj.edu.entity.vo.CoursePublicVo;
import com.wxj.edu.mapper.CourseMapper;
import com.wxj.edu.service.ChapterService;
import com.wxj.edu.service.CourseDescriptionService;
import com.wxj.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxj.edu.service.VideoService;
import com.wxj.servicebase.ExceptionHandler.wxjException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-20
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
  //注入课程描述Service
    @Autowired
    private CourseDescriptionService courseDescriptionService;

    //注入小节Service
    @Autowired
    private VideoService videoService;

    //注入章节节Service
    @Autowired
    private ChapterService chapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        //将CourseInfoVo 转换成 Course 操作
        BeanUtils.copyProperties(courseInfoVo,course);
        int insert = baseMapper.insert(course);
        if (insert==0){
            throw new wxjException(20001,"添加课程失败");
        }
        String cid = course.getId();
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        Course course = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int update = baseMapper.updateById(course);
        if (update==0){
            throw new wxjException(20001,"修改课程失败");

        }
        CourseDescription Description = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo,Description);
        courseDescriptionService.updateById(Description);



    }

    @Override
    public CoursePublicVo getPublishCourseInfo(String courseId) {
        CoursePublicVo publicCourse = baseMapper.getPublicCourse(courseId);
        return publicCourse;
    }

    @Override
    public void removeCourse(String id) {
        //1.先删除小节 .章节
    videoService.removeVideoByCourseId(id);   //删小节
    chapterService.removeChapterByCourseId(id); //删章节

        //2.先删除描述
courseDescriptionService.removeById(id);

        //3.删除课程本身
        int deleteById = baseMapper.deleteById(id);
        if (deleteById == 0){
            throw new wxjException(20001,"删除课程失败");
        }


    }
   //前台分页条件查询
    @Override
    public Map<String, Object> getFrontCourseList(Page<Course> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        //判断条件是否为空
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageCourse,wrapper);

        List<Course> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();//下一页
        boolean hasPrevious = pageCourse.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    @Override
    public List<CourseWebVo> getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
