package com.oy.guli.educms.service;

import com.oy.guli.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author guli
 * @since 2021-03-24
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 查询所有banner
     * @return
     */
    List<CrmBanner> selectAllBanner();
}
