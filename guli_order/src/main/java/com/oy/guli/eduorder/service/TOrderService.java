package com.oy.guli.eduorder.service;

import com.oy.guli.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author guli
 * @since 2021-04-03
 */
public interface TOrderService extends IService<TOrder> {

    /**
     * 生成订单的方法
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    String createOrders(String courseId, String memberIdByJwtToken);

    /**
     * 查询最新支付的7条支付订单
     * @return
     */
    List<TOrder> selectSevenList();
}
