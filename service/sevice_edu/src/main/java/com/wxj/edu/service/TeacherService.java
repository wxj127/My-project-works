package com.wxj.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxj.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxj.orderVo.MemberOrder;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-14
 */
public interface TeacherService extends IService<Teacher> {

    Map<String, Object> getTeacherFront(Page<Teacher> teacherPage);


    MemberOrder ss(String id);
}
