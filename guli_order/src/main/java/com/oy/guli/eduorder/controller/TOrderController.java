package com.oy.guli.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.oy.guli.common.result.Result;
import com.oy.guli.common.utils.JwtUtils;
import com.oy.guli.eduorder.entity.TOrder;
import com.oy.guli.eduorder.service.TOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author guli
 * @since 2021-04-03
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService orderService;


    /**
     * 根据创建时间查询最新最新的7条支付订单
     * @return
     */
    @GetMapping("list")
    public Result OrderList(){
        List<TOrder> orderList = orderService.selectSevenList();
        if(orderList != null){
            return Result.ok().data("orderList",orderList);
        }
        return Result.error();
    }
    /**
     * 生成订单的方法
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("createOrder/{courseId}")
    public Result saveOder(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return Result.error().code(28004).message("请登录");
        }
        // 创建订单，返回订单号
        String orderNo = orderService.createOrders(courseId,memberId );
        return Result.ok().data("orderId",orderNo);
    }

    // 2 根据订单id 查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public Result getOrderInfo(@PathVariable String orderId){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);
        return Result.ok().data("items",order);
    }

    //根据课程id和用户id查询订单表中订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);//支付状态 1代表已经支付
        int count = orderService.count(wrapper);
        if(count>0){//已经支付
            return true;
        }else{
            return false;
        }
    }

}

