package com.oy.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oy.guli.teacher.entity.EduCourse;
import com.oy.guli.teacher.entity.EduCourseDescription;
import com.oy.guli.teacher.entity.frontvo.CourseFrontVo;
import com.oy.guli.teacher.entity.frontvo.CourseWebVo;
import com.oy.guli.teacher.entity.query.CourseQuery;
import com.oy.guli.teacher.entity.vo.CoursePublishVo;
import com.oy.guli.teacher.entity.vo.CourseVo;
import com.oy.guli.teacher.mapper.EduCourseMapper;
import com.oy.guli.teacher.service.EduChapterService;
import com.oy.guli.teacher.service.EduCourseDescriptionService;
import com.oy.guli.teacher.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oy.guli.teacher.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guli
 * @since 2021-03-11
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;


    @Override
    public String saveVo(CourseVo vo) {

        // 1. 添加课程
        baseMapper.insert(vo.getEduCourse());
        // 2. 获取课程ID
        String courseId = vo.getEduCourse().getId();
        // 3. 添加课程描述
        vo.getCourseDescription().setId(courseId);
        courseDescriptionService.save(vo.getCourseDescription());
        return courseId;
    }

    @Override
    public CourseVo getCourseVoById(String id) {

        // 创建一个Vo对象
        CourseVo vo = new CourseVo();
        // 根据课程ID获取课程对象
        EduCourse eduCourse = baseMapper.selectById(id);
        if (eduCourse == null) {
            return vo;
        }
        // 把课程加到vo对象中
        vo.setEduCourse(eduCourse);
        // 根据课程ID获取课程描述
        EduCourseDescription eduCourseDescription = courseDescriptionService.getById(id);
        // 把描述加到vo对象中
        if(eduCourseDescription == null){
            return vo;
        }
        vo.setCourseDescription(eduCourseDescription);

        return vo;
    }

    @Override
    public Boolean updateVo(CourseVo vo) {
        // 1. 先判断ID是否存在、如果不存在直接返回false
        if(StringUtils.isEmpty(vo.getEduCourse().getId())){
            return false;
        }

        // 2. 修改course
        int i = baseMapper.updateById(vo.getEduCourse());
        if(i <= 0){
            return false;
        }

        // 3. 修改courseDesc
        vo.getCourseDescription().setId(vo.getEduCourse().getId());
        boolean isDesc = courseDescriptionService.updateById(vo.getCourseDescription());
        return isDesc;
    }

    @Override
    public void getPageList(Page<EduCourse> eduCoursePage, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        if(courseQuery == null){
            baseMapper.selectPage(eduCoursePage,wrapper);
        }
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String teacherId = courseQuery.getTeacherId();
        String title = courseQuery.getTitle();

        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }

        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }

        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }

        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }

        baseMapper.selectPage(eduCoursePage,wrapper);
    }

    @Override
    public Boolean deleteById(String id) {

        // TODO 删除课程相关的小节
        videoService.removeVideoByCourseId(id);

        // TODO 删除课程相关的章节
        chapterService.removeChapterByCourseId(id);

        // 删除描述
        boolean b = courseDescriptionService.removeById(id);
        if(!b){
            return false;
        }

        int i = baseMapper.deleteById(id);

        return i == 1;
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        CoursePublishVo vo = baseMapper.getCoursePublishVoById(id);
        return vo;
    }

    @Override
    public Boolean updateStatusById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        int i = baseMapper.updateById(course);
        return i == 1;
    }

    @Override
    public Map<String, Object> getMapById(String id) {
        Map<String,Object> map = baseMapper.getMapById(id);
        return map;
    }

    /**
     * 条件查询带分页查询课程
     * @param pageParam
     * @param courseFrontVo
     * @return
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        // 根据讲师id查询所讲的课程
        QueryWrapper<EduCourse> wrapper= new QueryWrapper<>();
        // 判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){ // 一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }

        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){ // 二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }

        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam,wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页


        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

}
