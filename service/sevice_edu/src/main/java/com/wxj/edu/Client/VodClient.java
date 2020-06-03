package com.wxj.edu.Client;
import com.wxj.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-vod")  //生成者的注册服务名称
@Component
public interface VodClient {
    //根据视频Id删除阿里云视频
    @DeleteMapping("/vod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);
    //删除多个阿里云视频
    @DeleteMapping("/vod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
