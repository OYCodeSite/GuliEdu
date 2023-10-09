package com.oy.guli.staservice.client;

import com.oy.guli.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author OY
 * @Date 2021/4/5
 */
@Component
@FeignClient("guli-ucenter")
public interface UcenterClient {

    @GetMapping("/educenter/member/countRegister/{day}")
    public Result countRegister(@PathVariable("day") String day);
}
