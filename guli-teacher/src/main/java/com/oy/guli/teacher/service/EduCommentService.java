package com.oy.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oy.guli.teacher.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author guli
 * @since 2021-04-03
 */
public interface EduCommentService extends IService<EduComment> {

    /**
     * 根据课程id查询评论列表
     * @param pageParam
     * @param courseId
     * @return
     */
    Map<String, Object> getCommentFrontList(Page<EduComment> pageParam, String courseId);

    boolean SaveComment(String memberId, EduComment comment);
}
