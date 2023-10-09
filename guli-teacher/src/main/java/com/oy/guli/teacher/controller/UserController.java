package com.oy.guli.teacher.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.oy.guli.common.result.Result;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

/**
 * @Author OY
 * @Date 2021/3/2
 */
@RestController
@RequestMapping("user")
//@CrossOrigin
public class UserController {

    // login
    @PostMapping ("login")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public Result info(){
        return Result.ok()
                .data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    //info
    @PostMapping("logout")
    public Result logout(){
        return Result.ok().data("token","");
    }
}
