package com.oy.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.Query;
import com.oy.guli.teacher.client.VodClient;
import com.oy.guli.teacher.entity.EduVideo;
import com.oy.guli.teacher.mapper.EduVideoMapper;
import com.oy.guli.teacher.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author guli
 * @since 2021-03-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    @Override
    public Boolean removeVideoById(String id) {

        // TODO 删除阿里云上的视频

        // 删除数据库中的Video
        int delete = baseMapper.deleteById(id);

        return delete == 1;
    }

    @Override
    public Boolean removeVideoByCourseId(String courseId) {
        // 1. 根据课程id查询所有的视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);

        // list<EduVideo>变成list<String>
        ArrayList<String> videoIds = new ArrayList<>();
        for(int i =0; i < eduVideoList.size(); i++){
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                // 放到videoIds集合里面
                videoIds.add(videoSourceId);
            }
        }

        // 根据多个视频id删除多个视频
        if(videoIds.size() > 0){
            vodClient.deleteBatch(videoIds);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        Integer delete = baseMapper.delete(wrapper);

        return null != delete && delete > 0;
    }
}
