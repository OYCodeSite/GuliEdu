package com.oy.guli.teacher.client;

import com.oy.guli.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/21
 */
@FeignClient(name = "guli-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    // 定义调用的方法路径
    // 根据视频ID删除阿里云视频
    // @PathVariable注解一定要指定参数名称，否则出错
    @DeleteMapping("/vod/{videoSourceId}")
    public Result getVideoPlayAuth(@PathVariable("videoSourceId") String videoSourceId);

    @DeleteMapping("/vod/delete-batch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);


}
