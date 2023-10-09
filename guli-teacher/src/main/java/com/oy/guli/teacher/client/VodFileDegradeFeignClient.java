package com.oy.guli.teacher.client;

import com.oy.guli.common.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/22
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public Result getVideoPlayAuth(String videoSourceId) {
        return Result.error().message("删除视频出错了");
    }

    @Override
    public Result deleteBatch(List<String> videoIdList) {
        return Result.error().message("删除多个视频出错了");
    }
}
