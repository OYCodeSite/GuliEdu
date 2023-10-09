package com.oy.guli.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.oy.guli.vod.service.VodService;
import com.oy.guli.vod.util.AliyunVodSDKUtil;
import com.oy.guli.vod.util.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/21
 */
@Service
@Slf4j
public class VodServiceImpl implements VodService {

    @Override
    public String upload(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.warn(errorMessage);
                if(StringUtils.isEmpty(videoId)){
                    //throw new EduException(20001, errorMessage);
                    log.warn("videoId为空");
                }
            }

            return videoId;
        } catch (IOException e) {
            //throw new EduException(20001, "guli vod 服务上传失败");
            log.warn("guli vod 服务上传失败");
            return null;
        }
    }

    @Override
    public Boolean deleteVodById(String videoSourceId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            DeleteVideoResponse response = new DeleteVideoResponse();
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoSourceId);

            response = client.getAcsResponse(request);

            return true;
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Boolean removeMoreVideo(List<String> videoIdList) {
        try {
            // 初始化对象
            DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            // videoIdList值转成 1,2,3
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");

            // 向request设置视频id
            request.setVideoIds(videoIds);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return true;
        } catch (ClientException e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            return false;
        }
    }
}
