package com.oy.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oy.guli.teacher.entity.EduCourse;
import com.oy.guli.teacher.entity.EduTeacher;
import com.oy.guli.teacher.service.EduCourseService;
import com.oy.guli.teacher.service.EduTeacherService;
import com.oy.guli.teacher.service.IndexFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/26
 */
@Service
public class IndexFrontServiceImpl implements IndexFrontService {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @Cacheable(value ="eduCourse",key = "'selectCourse'")
    @Override
    public List<EduCourse> getHotCourseList() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);
        return eduList;
    }

    @Cacheable(value ="eduTeacher",key = "'seleTeacher'")
    @Override
    public List<EduTeacher> getFourTeacherList() {
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(wrapperTeacher);
        return teacherList;
    }
}
