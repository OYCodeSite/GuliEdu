package com.oy.guli.educms.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oy.guli.common.result.Result;
import com.oy.guli.educms.entity.CrmBanner;
import com.oy.guli.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author guli
 * @since 2021-03-24
 */
@Api(tags = "后台Banner管理")
@RestController
@RequestMapping("/educms/crm-banner")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public Result pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<CrmBanner>(page, limit);
        bannerService.page(pageBanner,null);
        return Result.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    /**
     * 添加banner
     * @return
     */
    @PostMapping("addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return Result.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public Result getBanner(@PathVariable String id){
        CrmBanner banner = bannerService.getById(id);
        return Result.ok().data("item",banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public Result updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return Result.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        bannerService.removeById(id);
        return Result.ok();
    }


}

