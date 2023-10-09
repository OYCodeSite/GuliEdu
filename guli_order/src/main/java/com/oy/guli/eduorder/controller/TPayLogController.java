package com.oy.guli.eduorder.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.oy.guli.common.result.Result;
import com.oy.guli.eduorder.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author guli
 * @since 2021-04-03
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class TPayLogController {

    @Autowired
    private TPayLogService payLogService;

    /**
     * 生成微信支付二维码接口
     * 参数是订单号
     * @return
     */
    @GetMapping("createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo){
       // 返回信息，包含二维码地址，还有其他需要的信息
       Map map = payLogService.createNatvie(orderNo);
       System.out.println("****返回二维码map集合:"+map);
       return Result.ok().data(map);
    }

    /**
     * 查询订单支付状态
     * @param orderNo 订单号，根据订单号查询 支付状态
     * @return
     */
    @GetMapping("queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo) {
        Map<String,String> map  = payLogService.queryPayStatus(orderNo);
        System.out.println("*****查询订单状态map集合:"+map);
        if(map == null){
            return Result.error().message("支付出错了");
        }

        // 如果返回map里面不能为空，通过map获取订单状态
        if(map.get("trade_state" ).equals("SUCCESS")){// 支付成功
            // 添加记录到支付表，更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return Result.ok().message("支付成功");
        }
        return Result.ok().code(25000).message("支付中");

    }

}

