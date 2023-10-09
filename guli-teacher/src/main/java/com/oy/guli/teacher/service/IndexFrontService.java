package com.oy.guli.teacher.service;

import com.oy.guli.teacher.entity.EduCourse;
import com.oy.guli.teacher.entity.EduTeacher;

import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/26
 */
public interface IndexFrontService {

    /**
     * 查询前8条热门课程
     * @return
     */
    List<EduCourse> getHotCourseList();

    /**
     * 查询前4条名师
     * @return
     */
    List<EduTeacher> getFourTeacherList();
}
