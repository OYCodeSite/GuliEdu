package com.oy.guli.teacher.service;

import com.oy.guli.teacher.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oy.guli.teacher.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author guli
 * @since 2021-03-07
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 根据传递的EXCEL 表格模板导入课程分类
     * @param file
     * @return 错误集合信息
     */
    List<String> importEXCL(MultipartFile file);

    /**
     * 获取课程分类树状数据
     * @return
     */
    List<OneSubject> getTree();

    /**
     * 根据ID删除课程分类
     * @param id
     * @return
     */
    boolean deleteById(String id);

    /**
     * 添加课程一级分类
     * @param subject
     * @return
     */
    Boolean saveLevelOne(EduSubject subject);

    /**
     * 保存二级分类
     * @param subject
     * @return
     */
    Boolean saveLevelTwo(EduSubject subject);
}
