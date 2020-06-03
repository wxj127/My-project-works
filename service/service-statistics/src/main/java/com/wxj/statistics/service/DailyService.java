package com.wxj.statistics.service;

import com.wxj.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-01
 */
public interface DailyService extends IService<Daily> {

    void registerCount(String day);
     //图表显示，返回两部分数据 ，日期数组，数量json数组
    Map<String, Object> getShowData(String type, String begin, String end);
}
