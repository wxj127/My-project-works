package com.wxj.oss.controller;


import com.wxj.R;
import com.wxj.oss.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@Api(tags = "Oss头像上传")
public class OssController {
    @Autowired
    private OssService ossService;
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        //MultipartFile file 得到上传文件
       String url = ossService.uploadAvatarFile(file);
       return R.Ok().data("url",url);
    }
}
