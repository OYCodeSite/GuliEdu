package com.oy.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oy.guli.teacher.client.UcenterClient;
import com.oy.guli.teacher.entity.EduComment;
import com.oy.guli.teacher.entity.EduCourse;
import com.oy.guli.teacher.entity.UcenterMember;
import com.oy.guli.teacher.mapper.EduCommentMapper;
import com.oy.guli.teacher.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oy.guli.teacher.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author guli
 * @since 2021-04-03
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public Map<String, Object> getCommentFrontList(Page<EduComment> pageParam, String courseId) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        commentService.page(pageParam,wrapper);
        List<EduComment> commentList = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String,Object> map = new HashMap<>();
        map.put("items",commentList);
        map.put("current",current);
        map.put("pages",pages);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }

    @Override
    public boolean SaveComment(String memberId, EduComment comment) {

        comment.setCourseId(comment.getCourseId());
        comment.setTeacherId(comment.getTeacherId());
        comment.setMemberId(memberId);
        UcenterMember info = ucenterClient.getInfo(memberId);
        
        comment.setNickname(info.getNickname());
        comment.setAvatar(info.getAvatar());

        boolean isSave = commentService.save(comment);
        return isSave;
    }
}
