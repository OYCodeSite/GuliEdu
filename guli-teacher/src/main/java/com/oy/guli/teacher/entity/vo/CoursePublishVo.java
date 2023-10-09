package com.oy.guli.teacher.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author OY
 * @Date 2021/3/19
 */
@Data
public class CoursePublishVo implements Serializable {

    // 课程ID
    private String id;

    //  课程名称
    private String title;

    // 一级分类
    private String subjectParentTitle;

    // 二级分类
    private String subjectTitle;

    // 课时
    private String lessonNum;

    // 讲师名称
    private String teacherName;

    // 课程价格
    private String price;

    // 封面
    private String cover;
}
