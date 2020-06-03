package com.wxj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxj.edu.Client.VodClient;
import com.wxj.edu.entity.Video;
import com.wxj.edu.mapper.VideoMapper;
import com.wxj.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-20
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;


    @Override
    public void removeVideoByCourseId(String id) {
       
        //1.根据课程id查询所有的视频id
        QueryWrapper<Video> wrapperVideo = new QueryWrapper();
        wrapperVideo.eq("course_id",id);
        wrapperVideo.select("video_source_id");
        List<Video> videos = baseMapper.selectList(wrapperVideo);
        //遍历取出视频id
        List<String> videoIds = new ArrayList<>();
        for (Video video : videos) {
            String videoSourceId = video.getVideoSourceId();
            if (StringUtils.isEmpty(videoSourceId)){
                videoIds.add(videoSourceId);
            }
        }
        if (videoIds.size()>0){
            vodClient.deleteBatch(videoIds);
        }



        QueryWrapper<Video> wrapper = new QueryWrapper();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
