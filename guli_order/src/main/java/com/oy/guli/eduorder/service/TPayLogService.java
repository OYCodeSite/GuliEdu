package com.oy.guli.eduorder.service;

import com.oy.guli.eduorder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author guli
 * @since 2021-04-03
 */
public interface TPayLogService extends IService<TPayLog> {

    /**
     * 生成微信支付二维码接口
     * @param orderNo
     * @return
     */
    Map createNatvie(String orderNo);

    /**
     * 查询订单支付状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 更新订单表订单状态
     * @param map
     */
    void updateOrdersStatus(Map<String, String> map);
}
