package com.wxj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxj.edu.Client.test;
import com.wxj.edu.entity.Teacher;
import com.wxj.edu.mapper.TeacherMapper;
import com.wxj.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxj.orderVo.MemberOrder;
import org.openxmlformats.schemas.drawingml.x2006.main.CTEffectStyleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-14
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private test   test;
    @Override
    public Map<String, Object> getTeacherFront(Page<Teacher> pageParam) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageParam,wrapper);

        List<Teacher> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页
        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public MemberOrder ss(String id) {
        MemberOrder userInfoOrder = test.getUserInfoOrder(id);
        return  userInfoOrder;

    }
}
