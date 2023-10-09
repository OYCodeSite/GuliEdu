package com.oy.guli.teacher.controller;


import com.oy.guli.common.result.Result;
import com.oy.guli.teacher.client.VodClient;
import com.oy.guli.teacher.entity.EduVideo;
import com.oy.guli.teacher.excetion.EduException;
import com.oy.guli.teacher.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author guli
 * @since 2021-03-17
 */
@RestController
@RequestMapping("/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("save")
    public Result save(@RequestBody EduVideo video) {
        boolean save = videoService.save(video);
        if (save) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    /**
     * 根据ID查询Video对象的回显
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result getVideoById(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        return Result.ok().data("video", video);
    }

    /**
     * 修改
     *
     * @param video
     * @return
     */
    @PutMapping("update")
    public Result update(@RequestBody EduVideo video) {
        boolean update = videoService.updateById(video);
        if (update) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id) {
        // 根据小节id获取视频id，调用方法实现视频删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();

        // 判断小节里面是否有视频id
        if(!StringUtils.isEmpty(videoSourceId)){
            // 根据视频id,远程调用实现视频删除
            Result result = vodClient.getVideoPlayAuth(videoSourceId);
            if(result.getCode() == 20001){
                throw new EduException(20001,"删除视频失败，熔断器...");
            }
        }

        // 删除小节
        Boolean flag = videoService.removeVideoById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
}

