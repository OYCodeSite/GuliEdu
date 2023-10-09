package com.oy.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oy.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oy.guli.teacher.entity.frontvo.CourseFrontVo;
import com.oy.guli.teacher.entity.frontvo.CourseWebVo;
import com.oy.guli.teacher.entity.query.CourseQuery;
import com.oy.guli.teacher.entity.vo.CoursePublishVo;
import com.oy.guli.teacher.entity.vo.CourseVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guli
 * @since 2021-03-11
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 保存课程基本信息
     * @param vo
     * @return
     */
    String saveVo(CourseVo vo);

    /**
     * 根据课程ID查询课程基本信息和描述
     * @param id
     * @return
     */
    CourseVo getCourseVoById(String id);

    /**
     * 修改课程基本信息
     * @param vo
     * @return
     */
    Boolean updateVo(CourseVo vo);

    /**
     * 根据课程ID删除课程信息
     * @param eduCoursePage
     * @param courseQuery
     */
    void getPageList(Page<EduCourse> eduCoursePage, CourseQuery courseQuery);

    /**
     * 根据课程ID删除课程信息
     * @param id
     * @return
     */
    Boolean deleteById(String id);

    /**
     * 根据课程ID删除课程信息
     * @param id
     * @return
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 根据课程ID修改课程状态
     * @param id
     * @return
     */
    Boolean updateStatusById(String id);

    /**
     * 根据课程ID查询Map对象
     * @param id
     * @return
     */
    Map<String, Object> getMapById(String id);

    /**
     * 条件查询分页查询课程
     * @param pageParam
     * @param courseFrontVo
     * @return
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo);

    /**
     * 课程详情的方法
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
