package com.wxj.vod.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.wxj.R;
import com.wxj.servicebase.ExceptionHandler.wxjException;
import com.wxj.vod.Utils.ConstantVodUtils;
import com.wxj.vod.Utils.InitVodClient;
import com.wxj.vod.service.VodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadAlyVideo(MultipartFile file){

        try {
            String fileName =file.getOriginalFilename();
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_Secret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch (Exception e){
            e.fillInStackTrace();
            return null;

        }

    }

    @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient Client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_Secret);
            DeleteVideoRequest request = new DeleteVideoRequest();
           //使用工具类StringUtils 每个字符串+”,“
            String videoIds = StringUtils.join(videoIdList, ",");
            request.setVideoIds(videoIds);
            Client.getAcsResponse(request);
        }catch (Exception e){
            e.fillInStackTrace();
            throw new wxjException(20001,"删除视频失败！");
        }


    }
}
