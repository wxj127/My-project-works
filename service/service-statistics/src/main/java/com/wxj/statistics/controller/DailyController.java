package com.wxj.statistics.controller;


import com.wxj.R;
import com.wxj.statistics.Client.UcenterClient;
import com.wxj.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-01
 */
@RestController
@RequestMapping("/statistics/sta")
public class DailyController {
    @Autowired
    private DailyService dailyService;
    //统计某一天的人数,生成统计数据
    @PostMapping("registerCount/{day}")
    public R  registerCount(@PathVariable String day){
        dailyService.registerCount(day);
        return R.Ok();
    }
  @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable
          String begin,@PathVariable String end){
     Map<String,Object> map =dailyService.getShowData(type,begin,end);
        return R.Ok().data(map);




  }

}

