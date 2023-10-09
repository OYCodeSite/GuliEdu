package com.oy.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oy.guli.teacher.entity.EduChapter;
import com.oy.guli.teacher.entity.EduVideo;
import com.oy.guli.teacher.entity.vo.OneChapter;
import com.oy.guli.teacher.entity.vo.TwoVideo;
import com.oy.guli.teacher.excetion.EduException;
import com.oy.guli.teacher.mapper.EduChapterMapper;
import com.oy.guli.teacher.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oy.guli.teacher.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guli
 * @since 2021-03-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<OneChapter> getChapterAndVideoById(String courseId) {

        List<OneChapter> list = new ArrayList<>();
        // 判断ID
        // 1、根据ID查询章节列表
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByAsc("sort");
        List<EduChapter> chapterList = baseMapper.selectList(wrapper);
        //判断集合
        //2、遍历章节列表
        for (EduChapter chapter : chapterList) {

            // 3、把每一个章节对象复制到OneChapter
            OneChapter oneChapter = new OneChapter();
            BeanUtils.copyProperties(chapter, oneChapter);
            // 4. 根据每一小章节ID查询小节列表
            QueryWrapper<EduVideo> wr = new QueryWrapper<>();
            wr.eq("chapter_id", oneChapter.getId());
            wr.orderByAsc("sort");
            List<EduVideo> videoList = videoService.list(wr);
            // 5、 遍历每一个小节
            for(EduVideo video : videoList){
                // 6、把每一个小节复制到TwoVideo
                TwoVideo twoVideo = new TwoVideo();
                BeanUtils.copyProperties(video,twoVideo);
                // 7、把每一个TwoVideo加到章节children
                oneChapter.getChildren().add(twoVideo);
            }

            // 8、把每一个章节加到总集合中
            list.add(oneChapter);
        }
        return list;
    }

    @Override
    public boolean saveChapter(EduChapter chapter) {
        if(chapter  == null){
            return false;
        }
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        // 判断章节是否存在
        wrapper.eq("title",chapter.getTitle());
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            return false;
        }

        int insert = baseMapper.insert(chapter);

        return insert  == 1;
    }

    @Override
    public boolean updateChapterById(EduChapter chapter) {

        if(chapter == null){
            return false;
        }

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("title",chapter.getTitle());
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new EduException(20001,"章节名称已存在");
        }

        int i = baseMapper.updateById(chapter);

        return i == 1;
    }

    @Override
    public Boolean removeChapterById(String id) {
        // 判断章节的ID下面是否存在小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        List<EduVideo> list = videoService.list(wrapper);
        if(list.size() != 0){
            throw new EduException(20001,"此章节下有小节，先删除");
        }
        // 2、如果有不能删除直接false
        int i = baseMapper.deleteById(id);

        // 3、删除章节
        return i == 1;
    }

    /**
     * 根据课程id删除章节
     * @param courseId
     */
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }


}
