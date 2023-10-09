package com.oy.guli.staservice.controller;


import com.oy.guli.common.result.Result;
import com.oy.guli.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author guli
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    /**
     * 统计某一天注册人数，生成统计数据
     * @param day
     * @return
     */
    @PostMapping("registerCount/{day}")
    public Result registerCount(@PathVariable String day){
        staService.registerCount(day);
        return Result.ok();
    }

    /**
     * 图表显示，返回两部分数据，日期json数组，数量json数组
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("showData/{type}/{begin}/{end}")
    public  Result showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return Result.ok().data(map);
    }

}

