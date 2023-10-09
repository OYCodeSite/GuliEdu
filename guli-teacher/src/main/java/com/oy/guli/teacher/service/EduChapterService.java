package com.oy.guli.teacher.service;

import com.oy.guli.teacher.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oy.guli.teacher.entity.vo.OneChapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guli
 * @since 2021-03-11
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程ID查询章节和小节列表
     * @param courseId
     * @return
     */
    List<OneChapter> getChapterAndVideoById(String courseId);

    /**
     * 保存章节
     * 判断保存的章节名称是否存在
     * @param chapter
     * @return
     */
    boolean saveChapter(EduChapter chapter);

    /**
     * 修改章节
     * 修改时判断章节名称是否存在
     * @param chapter
     * @return
     */
    boolean updateChapterById(EduChapter chapter);


    /**
     * 根据章节ID删除章节信息
     * @param id
     * @return
     */
    Boolean removeChapterById(String id);

    /**
     * 根据课程id删除章节
     * @param courseId
     */
    void removeChapterByCourseId(String courseId);
}
