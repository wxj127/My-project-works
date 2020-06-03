package com.wxj.edu.controller;


import com.wxj.R;
import com.wxj.edu.Client.VodClient;
import com.wxj.edu.entity.Chapter;
import com.wxj.edu.entity.Video;
import com.wxj.edu.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/edu/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
     private  VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video){
     videoService.save(video);
            return R.Ok();
    }
    //删除小节
    //删除需要完善
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable("id") String id){
        //根据小节获取视频id
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        //判断是否有视频没有不执行删除。
        if (!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeAlyVideo(videoSourceId);
        }
        videoService.removeById(id);
       return   R.Ok();


    }
   //
   //根据id查询
   @GetMapping("getVideo/{videoId}")
   public R getVideo(@PathVariable("videoId") String videoId){
       Video video = videoService.getById(videoId);
       return R.Ok().data("video",video);
   }


    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody Video video){
        videoService.updateById(video);
        return R.Ok();
    }


}

