package com.oy.guli.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oy.guli.common.utils.JwtUtils;
import com.oy.guli.common.utils.MD5;
import com.oy.guli.educenter.entity.vo.RegisterVo;
import com.oy.guli.common.exceptionhandler.GuliException;
import org.springframework.util.StringUtils;
import com.oy.guli.educenter.entity.UcenterMember;
import com.oy.guli.educenter.mapper.UcenterMemberMapper;
import com.oy.guli.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author guli
 * @since 2021-03-27
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        // 获取登录手机和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        // 手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }

        // 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        // 判断查询对象是否为空
        if(mobileMember == null){
            throw new GuliException(20001,"登录失败");
        }

        // 加密，判断密码
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"登录失败");
        }

        // 登录成功
        // 生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        List<String> list = new ArrayList<>();
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)  || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
            throw new GuliException(20001,"注册失败");
        }

        // 判断验证码
        // 获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new GuliException(20001,"注册失败");
        }

        // 判断手机号码是否重复，表里面存在相同手机号码不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new GuliException(20001,"注册失败");
        }

        // 数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDeleted(false);
        member.setAvatar("https://oss.imoyt.top/2021/03/28/78e190328042153.png");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenidMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
