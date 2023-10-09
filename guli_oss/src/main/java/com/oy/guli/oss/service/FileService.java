package com.oy.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author OY
 * @Date 2021/3/6
 */
public interface FileService {
    String upload(MultipartFile file);
}
