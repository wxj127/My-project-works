package com.wxj.edu.service;

import com.wxj.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-20
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(String id);
}
