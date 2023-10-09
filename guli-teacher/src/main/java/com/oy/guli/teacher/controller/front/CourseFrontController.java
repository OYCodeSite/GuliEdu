package com.oy.guli.teacher.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.oy.guli.common.order.CourseWebVoOrder;
import com.oy.guli.common.result.Result;
import com.oy.guli.common.utils.JwtUtils;
import com.oy.guli.teacher.client.OrdersClient;
import com.oy.guli.teacher.entity.EduChapter;
import com.oy.guli.teacher.entity.EduCourse;
import com.oy.guli.teacher.entity.frontvo.CourseFrontVo;
import com.oy.guli.teacher.entity.frontvo.CourseWebVo;
import com.oy.guli.teacher.entity.vo.OneChapter;
import com.oy.guli.teacher.service.EduChapterService;
import com.oy.guli.teacher.service.EduCourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author OY
 * @Date 2021/3/31
 */
@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    /**
     * 条件查询分页查询课程
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable long page,@PathVariable  long limit,
                                        @RequestBody(required = false)CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        // 返回分页所有数据
        return Result.ok().data(map);
    }

    /**
     * 课程详情的方法
     * @param courseId
     * @return
     */
    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        // 根据课程id, 编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        // 根据课程id查询章节和小节
        List<OneChapter> chapterVideoList =  chapterService.getChapterAndVideoById(courseId);

        //根据课程id和用户id查询当前课程是否已经支付过了
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return Result.error().code(28004).message("请登入");
        }
        boolean buyCourse = ordersClient.isBuyCourse(courseId, memberId);

        return Result.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList)
                .data("isBuy",buyCourse);
    }

    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
