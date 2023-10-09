package com.oy.guli.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    String upload(MultipartFile file);

    /**
     * 根据视频资源获取ID删除云端视频
     * @param videoSourceId
     * @return
     */
    Boolean deleteVodById(String videoSourceId);

    /**
     * 删除多个阿里云视频的方法
     * @param videoIdList
     */
    Boolean removeMoreVideo(List<String> videoIdList);
}
