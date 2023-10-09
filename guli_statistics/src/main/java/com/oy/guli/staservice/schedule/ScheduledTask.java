package com.oy.guli.staservice.schedule;

import com.oy.guli.staservice.service.StatisticsDailyService;
import com.oy.guli.staservice.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author OY
 * @Date 2021/4/6
 */
@Component
public class ScheduledTask {

    private StatisticsDailyService staService;

    /**
     * 0/5 * * * * ? 表示每个5秒执行一次这个方法
     */
/*    @Scheduled(cron = "0/5 * * * * ?")
    public void task1(){
        System.out.println("********task1执行了....");
    }*/

    /**
     * 在每天凌晨1点，把前一天数据进行查询添加
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2(){
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
