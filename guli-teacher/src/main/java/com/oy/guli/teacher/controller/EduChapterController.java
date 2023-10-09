package com.oy.guli.teacher.controller;


import com.oy.guli.common.result.Result;
import com.oy.guli.teacher.entity.EduChapter;
import com.oy.guli.teacher.entity.vo.OneChapter;
import com.oy.guli.teacher.service.EduChapterService;
import com.oy.guli.teacher.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guli
 * @since 2021-03-11
 */
@RestController
@RequestMapping("/chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 根据课程的ID获取章节和小节列表
     * Tree
     * 1、创建一个对象，作为章节Vo，里面包括二级集合
     * 2、创建二级的Vo （video）
     * 3、根据课程ID查询章节的列表，遍历这个列表，根据每一个章节的ID查询二级列表（video集合）
     */
    @GetMapping("{courseId}")
    private Result getChapterAndVideoById(@PathVariable String courseId) {
        List<OneChapter> list = chapterService.getChapterAndVideoById(courseId);
        return Result.ok().data("list",list);
    }

    /**
     *
     */
    @PostMapping("save")
    private Result save(@RequestBody EduChapter chapter){
        boolean save =chapterService.saveChapter(chapter);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @GetMapping("get/{id}")
    public Result getChapterById(@PathVariable String id){
        EduChapter chapter = chapterService.getById(id);
        return Result.ok().data("chapter",chapter);
    }

    @PutMapping("update")
    public Result updateById(@RequestBody EduChapter chapter){
        boolean b = chapterService.updateChapterById(chapter);
        if(b){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id){
        Boolean flag = chapterService.removeChapterById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
}

