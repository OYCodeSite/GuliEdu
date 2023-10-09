package com.oy.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oy.guli.teacher.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oy.guli.teacher.entity.query.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author guli
 * @since 2021-02-24
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    /**
     * 分页查询讲师的方法
     * @param pageTeacher
     * @return
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
