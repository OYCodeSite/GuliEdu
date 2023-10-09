package com.oy.guli.educenter.service;

import com.oy.guli.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oy.guli.educenter.entity.vo.RegisterVo;

import java.util.List;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author guli
 * @since 2021-03-27
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    /**
     * 登录获取token令牌
     * @param member
     * @return
     */
    String login(UcenterMember member);

    /**
     * 注册用户
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 微信登，根据openid判断
     * @param openid
     * @return
     */
    UcenterMember getOpenidMember(String openid);

    /**
     * 查询某一天注册人数
     * @param day
     * @return
     */
    Integer countRegisterDay(String day);
}
