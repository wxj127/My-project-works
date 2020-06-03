package com.wxj.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxj.R;
import com.wxj.statistics.Client.UcenterClient;
import com.wxj.statistics.entity.Daily;
import com.wxj.statistics.mapper.DailyMapper;
import com.wxj.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-01
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {


    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public void registerCount(String day) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        R r = ucenterClient.countRegister(day);
        Integer num = (Integer)r.getData().get("num");
        Daily daily = new Daily();
        daily.setRegisterNum(num);
        daily.setDateCalculated(day);
        int i = RandomUtils.nextInt(100, 200);
        daily.setVideoViewNum(i);
        daily.setCourseNum(i);
        daily.setLoginNum(i);
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);//根据条件查询对应的数据
        List<Daily> dailyList = baseMapper.selectList(wrapper);


        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();
        for (Daily daily : dailyList) {
            date_calculatedList.add(daily.getDateCalculated());
            switch (type){
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                   break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                default:
                    break;

            }

        }
        Map<String,Object>  map= new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;


    }
}
