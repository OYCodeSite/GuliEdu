package com.oy.guli.teacher.controller;


import com.oy.guli.common.result.Result;
import com.oy.guli.teacher.entity.EduSubject;
import com.oy.guli.teacher.entity.vo.OneSubject;
import com.oy.guli.teacher.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author guli
 * @since 2021-03-07
 */
@RestController
@RequestMapping("/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("import")
    public Result importSubject(MultipartFile file){
        // 因为考虑到Excl 模板数据不准确所以返回多个错误信息，那么多个错误信息放在集合中
        List<String> mesList = subjectService.importEXCL(file);
        if(mesList.size() == 0){
            return Result.ok();
        }else{
            return Result.error().data("messageList",mesList);
        }

    }

    @GetMapping("tree")
    public Result TreeSubject(){
       List<OneSubject> subjectList = subjectService.getTree();
       return Result.ok().data("subjectList",subjectList);
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id){
        boolean b = subjectService.deleteById(id);

        if(b){
            return Result.ok();
        }else{

            return Result.error();
        }
    }

    @PostMapping("saveLevelOne")
    public Result saveLevelOne(@RequestBody EduSubject subject){
        Boolean flag = subjectService.saveLevelOne(subject);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @PostMapping("saveLevelTwo")
    public Result saveLevelTwo(@RequestBody EduSubject subject){
        Boolean flag = this.subjectService.saveLevelTwo(subject);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}

