package com.oy.guli.educenter.controller;


import com.oy.guli.common.order.UcenterMemberOrder;
import com.oy.guli.common.result.Result;
import com.oy.guli.common.utils.JwtUtils;
import com.oy.guli.educenter.entity.UcenterMember;
import com.oy.guli.educenter.entity.vo.RegisterVo;
import com.oy.guli.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author guli
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 登录
     * @param member
     * @return
     */
    @PostMapping("login")
    public Result loginUser(@RequestBody UcenterMember member){
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = memberService.login(member);
        return Result.ok().data("token",token);
    }

    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo){
        try {
            memberService.register(registerVo);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("注册失败，请重新输入");
        }
    }

    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("getMemberInfo")
     public Result getMemberInfo(HttpServletRequest request){

        // 调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return Result.ok().data("userInfo",member);
    }

    /**
     * 评论模块根据用户id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMember memeber = new UcenterMember();
        BeanUtils.copyProperties(ucenterMember,memeber);
        return memeber;
    }

    /**
     * 订单模块根据用户id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    /**
     * 查询某一天注册人数
     * @param day
     * @return
     */
    @GetMapping("countRegister/{day}")
    public Result countRegister(@PathVariable String day){
        Integer count = memberService.countRegisterDay(day);
        return Result.ok().data("countRegister",count);
    }





}

