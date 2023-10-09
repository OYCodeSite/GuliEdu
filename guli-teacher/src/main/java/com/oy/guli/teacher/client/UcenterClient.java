package com.oy.guli.teacher.client;

import com.oy.guli.teacher.client.impl.UcenterClientImpl;
import com.oy.guli.teacher.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "guli-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {

    @GetMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable("id") String id) ;
}
