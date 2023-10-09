package com.oy.guli.teacher.entity.vo;

import com.oy.guli.teacher.entity.EduCourse;
import com.oy.guli.teacher.entity.EduCourseDescription;
import lombok.Data;

/**
 * @Author OY
 * @Date 2021/3/11
 */
@Data
public class CourseVo {

    private EduCourse eduCourse;

    private EduCourseDescription courseDescription;
}
