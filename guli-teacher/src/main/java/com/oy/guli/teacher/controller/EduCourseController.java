package com.oy.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oy.guli.common.result.Result;
import com.oy.guli.teacher.entity.EduCourse;
import com.oy.guli.teacher.entity.query.CourseQuery;
import com.oy.guli.teacher.entity.vo.CoursePublishVo;
import com.oy.guli.teacher.entity.vo.CourseVo;
import com.oy.guli.teacher.service.EduCourseDescriptionService;
import com.oy.guli.teacher.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guli
 * @since 2021-03-11
 */
@RestController
@RequestMapping("/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 保存基本信息
     * @param vo
     * @return
     */
    @PostMapping("saveVo")
    @Transactional
    public Result saveCourse(@RequestBody CourseVo vo){

        try {
            String courseId = courseService.saveVo(vo);
            return Result.ok().data("id",courseId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 根据课程ID获取课程基本信息和描述
     */
    @GetMapping("{id}")
    public Result getCourseVoById(@PathVariable String id){
        CourseVo vo = courseService.getCourseVoById(id);
        return Result.ok().data("courseInfo",vo);
    }

    /**
     * 修改课程基本信息
     */
    @PutMapping("updateVo")
    public Result updataVo(@RequestBody CourseVo vo){
        Boolean flag = courseService.updateVo(vo);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @PostMapping("{page}/{limit}")
    public Result getPageList(@PathVariable Integer page,
                              @PathVariable Integer limit,
                              @RequestBody CourseQuery courseQuery){

        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        courseService.getPageList(eduCoursePage, courseQuery);
        return Result.ok()
                .data("rows", eduCoursePage.getRecords())
                .data("total",eduCoursePage.getTotal());


    }

    /**
     * 根据课程ID删除课程信息
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id){
        Boolean flag = courseService.deleteById(id);
        if(flag){
           return Result.ok();
        }else{
            return Result.error();
        }
    }

    /**
     * 根据课程ID查询发布课程的详细
     * @param id
     * @return
     */
    @GetMapping("vo/{id}")
    public Result getCoursePublishVoById(@PathVariable String id){
//        CoursePublishVo voursePublishVo  = courseService.getCoursePublishVoById(id);

        Map<String, Object> map = courseService.getMapById(id);

        if(map != null){
//            return Result.ok().data("coursePublishVo",voursePublishVo);
            return Result.ok().data(map);
        }else{
            return Result.error();
        }
    }

    @GetMapping("updateStatusById/{id}")
    public Result updateStatusById(@PathVariable String id){
        Boolean flag = courseService.updateStatusById(id);

        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}

