package com.wxj.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wxj.oss.service.OssService;
import com.wxj.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    //上传头像到Oss
    @Override
    public String uploadAvatarFile(MultipartFile file) {
        //获取工具类的值
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String backerName =ConstantPropertiesUtils.BUCKET_NAME;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 获取上传文件流。
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();
            //加上随机数，每次上传的文件名不同，防止覆盖
            String uuid = UUID.randomUUID().toString().replace("-","");
            filename  = uuid+filename;
            //三个参数 1.backerName，2.文件的原始路径，3.文件的输入流
            //添加"yyyy/MM/dd" 时间分类管理
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            filename=dataPath+"/"+filename;
            ossClient.putObject(backerName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //需要手动拼接返回路径
           // https://edu-1015.oss-cn-beijing.aliyuncs.com/IMG_20200509_122936.jpg
            String url = "https://"+backerName+"."+endpoint+"/"+filename;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        

    }
}
