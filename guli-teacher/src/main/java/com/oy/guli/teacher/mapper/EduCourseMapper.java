package com.oy.guli.teacher.mapper;

import com.oy.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oy.guli.teacher.entity.frontvo.CourseWebVo;
import com.oy.guli.teacher.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author guli
 * @since 2021-03-11
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishVoById(String id);

    Map<String, Object> getMapById(String id);

    CourseWebVo getBaseCourseInfo(String courseId);
}
