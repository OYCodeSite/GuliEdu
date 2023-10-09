package com.oy.guli.educms.controller;

import com.oy.guli.common.result.Result;
import com.oy.guli.educms.entity.CrmBanner;
import com.oy.guli.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/24
 */
@Api(tags = "前台Banner")
@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 查询所有的banner
     * @return
     */

    @GetMapping("getAllBanner")
    public Result getAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return Result.ok().data("list",list);

    }
}
