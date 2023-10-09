package com.oy.guli.staservice.service;

import com.oy.guli.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author guli
 * @since 2021-04-05
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 统计某一天注册人数，生成统计数据
     * @param day
     */
    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
