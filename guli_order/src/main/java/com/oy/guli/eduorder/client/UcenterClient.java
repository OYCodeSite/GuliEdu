package com.oy.guli.eduorder.client;

import com.oy.guli.common.order.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author OY
 * @Date 2021/4/3
 */
@Component
@FeignClient("guli-ucenter")
public interface UcenterClient {

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/educenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}
