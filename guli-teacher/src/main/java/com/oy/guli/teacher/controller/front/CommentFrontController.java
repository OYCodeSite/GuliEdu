package com.oy.guli.teacher.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oy.guli.common.result.Result;
import com.oy.guli.common.utils.JwtUtils;
import com.oy.guli.teacher.client.UcenterClient;
import com.oy.guli.teacher.entity.EduComment;
import com.oy.guli.teacher.service.EduCommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author OY
 * @Date 2021/4/3
 */
@RestController
@RequestMapping("/eduservice/comment")
//@CrossOrigin
public class CommentFrontController {

    @Autowired
    private EduCommentService commentService;

    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,@PathVariable Long limit, String courseId){
        Page<EduComment> pageParam = new Page<>(page, limit);
        Map<String,Object> map = commentService.getCommentFrontList(pageParam,courseId);
        return Result.ok().data(map);
    }

    @PostMapping("auth/save")
    public Result save(@RequestBody EduComment comment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return Result.error().code(28004).message("请登录");
        }

        boolean isSave = commentService.SaveComment(memberId, comment);
        if(isSave){
          return Result.ok();
        }
        return Result.error();
    }



}
