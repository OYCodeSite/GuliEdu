package com.oy.guli.oss.controller;

import com.oy.guli.common.result.Result;
import com.oy.guli.oss.service.FileService;
import com.oy.guli.oss.utils.ConstantPropertiesUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author OY
 * @Date 2021/3/6
 */
@RestController
@RequestMapping("oss")
//@CrossOrigin
public class FileController {

    @Autowired
    FileService fileService;

    /**
     * 上传文件、接收请求、返回响应
     * @param file
     * @return
     */
    @PostMapping("file/upload")
    public Result upload(
            @ApiParam(name = "file", value = "文件",required = true)
            @RequestParam("file") MultipartFile file,

            @ApiParam(name = "host",value = "文件上传路径", required = false)
            @RequestParam(value = "host", required = false) String host){

        if(!StringUtils.isEmpty(host)){
            ConstantPropertiesUtil.FILE_HOST = host;
        }

        String url = fileService.upload(file);
        if(!StringUtils.isEmpty(url)){
            return Result.ok().data("url",url);
        }
        return Result.error().message("上传失败");
    }
}
