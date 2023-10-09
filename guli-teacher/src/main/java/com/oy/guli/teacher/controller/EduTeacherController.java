package com.oy.guli.teacher.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oy.guli.common.result.Result;
import com.oy.guli.common.result.ResultCode;
import com.oy.guli.teacher.entity.EduTeacher;
import com.oy.guli.teacher.entity.query.TeacherQuery;
import com.oy.guli.teacher.excetion.EduException;
import com.oy.guli.teacher.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author guli
 * @since 2021-02-24
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("list")
    public Result list(){
        try {
            List<EduTeacher> list = teacherService.list(null);
            return Result.ok().data("items",list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }

    }


    @ApiOperation(value="根据ID删除讲师")
    @DeleteMapping("/{id}") // 占位符
//     1、如果占位符中的参数名和形参名一致的话那么@PathVariable可以省略
//     2、如果配置了 Swagger、并在形参前加了其他注解,那么 @PathVariable 必须加
    public Result removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        try {
            teacherService.removeById(id);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 分页
     */
    @ApiOperation(value = "讲师分页列表")
    @GetMapping("/{page}/{limit}")
    public Result selectTeacherByPage(
            @ApiParam(name = "page",value = "当前页",required = true)
            @PathVariable(value = "page") Integer page,
            @ApiParam(name = "limit",value = "每页显示记录数", required = true)
            @PathVariable Integer limit){
        try {
            Page<EduTeacher> teacherPage = new Page<>(page,limit);
            teacherService.page(teacherPage, null);
            return Result.ok().data("total",teacherPage.getTotal()).data("rows",teacherPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     *  根据条件查询
     * @param page
     * @param limit
     * @param query
     * @return
     */
    @ApiOperation(value = "根据讲师条件分页查询")
    @PostMapping("/{page}/{limit}")
    public Result selectTeacherByPageAndWrapper(
            @ApiParam(name="page",value = "当前页", required = true)
            @PathVariable(value = "page") Integer page,
            @ApiParam(name="limit",value = "每页显示记录数", required = true)
            @PathVariable Integer limit,
            @RequestBody TeacherQuery query){


        try {
            Page<EduTeacher> teacherPage = new Page<>(page, limit);
            //Wrapper<TeacherQuery> queryWrapper = new QueryWrapper<TeacherQuery>();
            teacherService.pageQuery(teacherPage,query);
            return Result.ok().data("total",teacherPage.getTotal()).data("rows",teacherPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "保存讲师对象")
    @PostMapping("/save")
    public Result saveTeacher(@RequestBody EduTeacher teacher){
        try {
            teacherService.save(teacher);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value="根据ID查询")
    @GetMapping("{id}")
    public Result selectTeacherById(
            @ApiParam(name = "id",value = "讲师ID",required = true)
            @PathVariable String id){
        // 当我们的业务被非法参数操作时、我们可以自定义异常（业务异常）
        EduTeacher teacher = teacherService.getById(id);
        if(teacher == null){
            throw new EduException(ResultCode.EDU_ID_ERROR,"没有此讲师的信息");
        }

        try {
//            EduTeacher teacher = teacherService.getById(id);
            return Result.ok().data("teacher",teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value="修改讲师信息")
    @PutMapping("/update")
    public Result selectTeacherById(@RequestBody EduTeacher teacher){
        try {
            teacherService.updateById(teacher);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

}

