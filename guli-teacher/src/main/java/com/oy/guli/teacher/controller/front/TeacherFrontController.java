package com.oy.guli.teacher.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oy.guli.common.result.Result;
import com.oy.guli.teacher.entity.EduCourse;
import com.oy.guli.teacher.entity.EduTeacher;
import com.oy.guli.teacher.service.EduCourseService;
import com.oy.guli.teacher.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * @Author OY
 * @Date 2021/3/30
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 分页查询讲师的方法
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(page,limit);
        Map<String,Object> map = eduTeacherService.getTeacherFrontList(pageTeacher);
        // 返回分页所有数据
        return Result.ok().data(map);
    }

    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId){
        // 1. 根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
        // 2. 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = eduCourseService.list(wrapper);
        return Result.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }

}
