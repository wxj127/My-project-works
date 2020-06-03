package com.wxj.vod.controller;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wxj.R;
import com.wxj.servicebase.ExceptionHandler.wxjException;
import com.wxj.vod.Utils.ConstantVodUtils;
import com.wxj.vod.Utils.InitVodClient;
import com.wxj.vod.service.VodService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vod/video")
@Api(tags = "阿里云文件上传")
public class VodController {
    @Autowired
    private VodService vodService;
    //上传视频到阿里云
    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) throws Exception {
     String videoId = vodService.uploadAlyVideo(file);
        return R.Ok().data("videoId",videoId);
    }
    //根据视频Id删除阿里云视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        try {
            //初始化对象
            DefaultAcsClient Client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_Secret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            Client.getAcsResponse(request);
            return R.Ok();
        }catch (Exception e){
            e.fillInStackTrace();
            throw new wxjException(20001,"删除视频失败！");
        }
    }
    //删除多个阿里云视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreAlyVideo(videoIdList);
        return R.Ok();
    }
    //根据视频id获取凭证
    @GetMapping("getPlayAuth/{id}")
   public R  getPlayAuth(@PathVariable String id){
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_Secret);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse Response = client.getAcsResponse(request);
            String playAuth = Response.getPlayAuth();

            return R.Ok().data("playAuth",playAuth);
        }catch (Exception e){
             throw  new wxjException(20001,"视频播放失败");

        }

    }

}
