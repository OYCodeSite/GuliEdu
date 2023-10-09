package com.oy.guli.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;
import com.oy.guli.msmservice.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author OY
 * @Date 2021/3/27
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean set(HashMap<String, Object> param, String phone) {

        if(StringUtils.isEmpty(phone)){
            return false;
        }
        DefaultProfile profile = DefaultProfile.getProfile("default", "<you keyid>", "<you keysecret>");
        DefaultAcsClient client = new DefaultAcsClient(profile);

        // 设置相关固定的参数
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        // 设置相关的参数
        request.putQueryParameter("PhoneNumbers",phone); // 手机号
        request.putQueryParameter("SignName","<签名>"); // 申请阿里云 签名失败
        request.putQueryParameter("TemplateCode","<模板>" ); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码数据，转换json数据传递

        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
