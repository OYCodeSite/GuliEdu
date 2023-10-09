package com.oy.guli.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oy.guli.educms.entity.CrmBanner;
import com.oy.guli.educms.mapper.CrmBannerMapper;
import com.oy.guli.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author guli
 * @since 2021-03-24
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 查询所有banner
     * @return
     */
    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {

        // 根据id 进行降序排列，显示排列之后前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // 使用last方法，拼接sql语句
        wrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }
}
