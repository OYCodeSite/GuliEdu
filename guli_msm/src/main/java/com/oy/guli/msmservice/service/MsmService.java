package com.oy.guli.msmservice.service;

import java.util.HashMap;

public interface MsmService {
    /**
     * 阿里云发送短信
     * @param param
     * @param phone
     * @return
     */
    boolean set(HashMap<String, Object> param, String phone);
}
