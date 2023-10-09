package com.oy.guli.educenter.mapper;

import com.oy.guli.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author guli
 * @since 2021-03-27
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegisterDay(String day);
}
