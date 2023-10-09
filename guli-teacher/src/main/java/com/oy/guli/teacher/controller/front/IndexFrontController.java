package com.oy.guli.teacher.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oy.guli.common.result.Result;
import com.oy.guli.teacher.entity.EduCourse;
import com.oy.guli.teacher.entity.EduTeacher;
import com.oy.guli.teacher.service.EduCourseService;
import com.oy.guli.teacher.service.EduTeacherService;
import com.oy.guli.teacher.service.IndexFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/24
 */
@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {


    @Autowired
    private IndexFrontService indexFrontService;

    /**
     * 查询前8条热门课程,查询前4条名师
     * @return
     */
    @GetMapping("index")
    public Result index(){

        // 查询前8条热门课程
        List<EduCourse> eduList = indexFrontService.getHotCourseList();

        // 查询前4条名师
        List<EduTeacher> teacherList = indexFrontService.getFourTeacherList();

        return Result.ok().data("eduList",eduList).data("teacherList",teacherList);
    }
}
