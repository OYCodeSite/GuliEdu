package com.oy.guli.msmservice.controller;

import com.oy.guli.common.result.Result;
import com.oy.guli.common.utils.RandomUtil;
import com.oy.guli.msmservice.service.MsmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author OY
 * @Date 2021/3/27
 */
@RequestMapping("/edumsm/msm")
@RestController
//@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable String phone){
        // 1. 从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return Result.ok();
        }

        // 2. 如果redis获取不到，进行阿里云发送
        // 生成随机值，传递给阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        HashMap<String, Object> param = new HashMap<>();
        param.put("code",code);
        // 调用service发送短信的方法
        boolean isSend  = msmService.set(param,phone);
        if(isSend){
            // 发送成功，把发送成功验证码放到redis里面
            // 设置有效期时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return Result.ok();
        }else{
            return Result.error().message("短信发送失败");
        }

    }
}
