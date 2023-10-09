package com.oy.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.Query;
import com.oy.guli.teacher.entity.EduTeacher;
import com.oy.guli.teacher.entity.query.TeacherQuery;
import com.oy.guli.teacher.mapper.EduTeacherMapper;
import com.oy.guli.teacher.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.util.*;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author guli
 * @since 2021-02-24
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {

        if(teacherQuery == null){
            baseMapper.selectPage(pageParam,null);
        }

        // 获取对象属性
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        Date gmtCreate = teacherQuery.getGmtCreate();
        Date gmtModified = teacherQuery.getGmtModified();

        //创建一个Wrapper
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();


        // 判断对象属性是否存在
        if(!StringUtils.isEmpty(name)){
            // 如果存在
           wrapper.like("name",name);
        }

        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }

        // 判断创建时间是否存在，如果存在，查询是大于等于此时间的
        if(!StringUtils.isEmpty(gmtCreate)){
            wrapper.ge("gmt_create",gmtCreate);
        }

        if(!StringUtils.isEmpty(gmtModified)){
            wrapper.le("gmt_create",gmtModified);
        }
        baseMapper.selectPage(pageParam,wrapper);
    }

    /**
     * 分页查询讲师方法
     * @param pageParam
     * @return
     */
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageParam) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // 把分页的数据封装到pageTeacher对象
        baseMapper.selectPage(pageParam,wrapper);

        List<EduTeacher> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext(); // 下一页
        boolean hasPrevious = pageParam.hasPrevious(); // 上一页

        Map<String, Object> map = new HashMap<>();
        map.put("items",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        // map 返回
        return map;
    }


//根据多个ID删分类

    /*Tree
     A
            B  刪除
                    b1
                    b2
                            bb1
                            bb2
            C*/
//    public void deletes(String parent_id){
//        List<String> ids = new ArrayList<>();
//        ids.add(parent_id);
//
//        //递归获取ID
//        getIds(ids,parent_id);
//        baseMapper.deleteBatchIds(ids);
//    }
//
//    //方法递归方法
//    public void getIds(List<String> ids, String parent_id){
//        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
//        wrapper.eq("parent_id",parent_id);
//        List<EduTeacher> teacherList = baseMapper.selectList(wrapper);
//        for(EduTeacher teacher : teacherList){
//            ids.add(teacher.getId());
//            getIds(ids,teacher.getId());
//        }
//    }
}
