package com.oy.guli.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.oy.guli.common.order.CourseWebVoOrder;
import com.oy.guli.common.order.UcenterMemberOrder;
import com.oy.guli.eduorder.client.EduClient;
import com.oy.guli.eduorder.client.UcenterClient;
import com.oy.guli.eduorder.entity.TOrder;
import com.oy.guli.eduorder.mapper.TOrderMapper;
import com.oy.guli.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oy.guli.eduorder.utils.OrderNoUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author guli
 * @since 2021-04-03
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 生成订单的方法
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    @Override
    public String createOrders(String courseId, String memberIdByJwtToken) {
        // 通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberIdByJwtToken);

        // 通过远程调用根据课程id获取课信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo()); // 订单号
        order.setCourseId(courseId); // 课程id
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }

    @Cacheable(value ="eduOrderList",key = "'selectSevenList'")
    @Override
    public List<TOrder> selectSevenList() {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.last("limit 7");
        List<TOrder> orderList = baseMapper.selectList(wrapper);
        return orderList;
    }
}
