package com.wxj.statistics.schedule;


import com.wxj.statistics.service.DailyService;
import com.wxj.statistics.utlis.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public class Schedule {
    @Autowired
    private DailyService dailyService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void time(){
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));

    }
}
