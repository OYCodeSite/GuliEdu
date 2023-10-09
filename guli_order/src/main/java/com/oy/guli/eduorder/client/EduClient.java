package com.oy.guli.eduorder.client;

import com.oy.guli.common.order.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("guli-teacher")
public interface EduClient {
    /**
     * 根据用户id获取用户信息
     */
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);
}
