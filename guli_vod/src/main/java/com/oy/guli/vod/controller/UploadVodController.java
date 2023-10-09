package com.oy.guli.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.oy.guli.common.exceptionhandler.GuliException;
import com.oy.guli.common.result.Result;

import com.oy.guli.vod.service.VodService;
import com.oy.guli.vod.util.AliyunVodSDKUtil;
import com.oy.guli.vod.util.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/21
 */
@RestController
@RequestMapping("vod")
//@CrossOrigin
public class UploadVodController {

    @Autowired
    private VodService vodService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("upload")
    public Result upload(MultipartFile file){
        String videoSourceId = vodService.upload(file);
        return Result.ok().data("videoSourceId",videoSourceId);
    }


    /**
     * 根据视频ID获取播放凭证
     * @param videoSourceId
     * @return
     */
    @DeleteMapping("{videoSourceId}")
    public Result getVideoPlayAuth(@PathVariable String videoSourceId){

        Boolean flag = vodService.deleteVodById(videoSourceId);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }

    }

    // 删除多个阿里云视频的方法
    // 参数多个视频id List videoIdList
    @DeleteMapping("delete-batch")
    public Result deleteBatch(@RequestParam("videoIdList")List<String> videoIdList){
        Boolean flag = vodService.removeMoreVideo(videoIdList);
        if(flag){
            return Result.ok();
        }else{
            return Result.error().message("删除视频失败");
        }
    }

    /**
     * 根据id获取视频凭证
     * @param id
     * @return
     */
    @GetMapping("getPlayAuth/{id}")
    public  Result getPlayAuth(@PathVariable String id){

        try {
            // 创建初始化对象
            DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 创建对象获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 向request设置视频id
            request.setVideoId(id);
            // 调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            throw new GuliException(20001,"获取凭证失败");
        }
    }
}
