package com.oy.guli.teacher.service;

import com.oy.guli.teacher.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author guli
 * @since 2021-03-17
 */
public interface EduVideoService extends IService<EduVideo> {

    Boolean removeVideoById(String id);

    /**
     * 根据课程id删除小节
     */
    Boolean removeVideoByCourseId(String courseId);
}
