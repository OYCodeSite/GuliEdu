package com.oy.guli.educenter.controller;

import com.google.gson.Gson;
import com.oy.guli.common.exceptionhandler.GuliException;
import com.oy.guli.common.utils.JwtUtils;
import com.oy.guli.educenter.entity.UcenterMember;
import com.oy.guli.educenter.service.UcenterMemberService;
import com.oy.guli.educenter.utils.ConstantWxUtils;
import com.oy.guli.educenter.utils.HttpClientUtils;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author OY
 * @Date 2021/3/29
 */
//@CrossOrigin
@Controller  //只是请求地址，不需要返回数据
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("callback")
    public String callback(String code, String state) throws Exception {
        try {
            // 1. 获取code值，临时票据，类似于验证码
            // 2. 拿着code请求 微信固定的地址，得到两个值 access_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            // 拼接三个参数： id 秘钥 和 code 值
            String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantWxUtils.WX_OPEN_APP_ID, ConstantWxUtils.WX_OPEN_APP_SECRET, code);

            // 请求这个拼接好的地址，得到返回两个值 access_token 和 appenid
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
//        System.err.println(accessTokenInfo);

            // 从accessTokenInfo 字符串获取出来两个值 access_token 和 apenid
            // 把accessTokenInfo 字符串转换map集合，根据map里面key获取对应值
            // 使用json 转换工具 Gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");


            // 把扫描人信息添加数据库里面
            // 判断数据表是否存在相同的微信信息,根据openid判断
            UcenterMember member = memberService.getOpenidMember(openid);
            if(member == null){
                // 3. 拿着得到access_token 和 openid, 再去请求微信提供固定的地址，获取到扫描人信息
                // 访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                // 发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
        //        System.err.println(userInfo);
                // 获取返回userinfo字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname"); // 昵称
                String headmigUrl = (String) userInfoMap.get("headimgurl"); // 头像

                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headmigUrl);
                memberService.save(member);
            }

            // 使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            // 最后： 返回首页面，通过路径传递token字符串
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            throw new GuliException(20001,"登录失败");
        }
    }

    /**
     * 生成微扫描二维码
     * @return
     */
    @GetMapping("login")
    public String getWxCode(){
        //固定地址，后面拼接参数
//        String url = "https://open.weixin.qq.com/" +
//                "connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";


        // 微信开放平台授权baseUrl %s相当于？代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对redirect_url 进行URLEncode编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(
                baseUrl, ConstantWxUtils.WX_OPEN_APP_ID, redirectUrl, "guli");

        return "redirect:"+url;

    }
}
