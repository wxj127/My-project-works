package com.wxj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxj.edu.entity.Chapter;
import com.wxj.edu.entity.Video;
import com.wxj.edu.entity.chapter.ChapterVo;
import com.wxj.edu.entity.chapter.VideoVo;
import com.wxj.edu.mapper.ChapterMapper;
import com.wxj.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxj.edu.service.VideoService;
import com.wxj.servicebase.ExceptionHandler.wxjException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-20
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
      @Autowired
   private   VideoService videoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据课程id查询所有章节
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
         wrapper.eq("course_id",courseId);
        List<Chapter> chapterList = baseMapper.selectList(wrapper);
        //根据课程id查询所有小节
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<Video> videoList = videoService.list(wrapperVideo);

      List<ChapterVo>  finalList = new ArrayList<>();
        for (Chapter chapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            finalList.add(chapterVo);
          List<VideoVo> videoVos =new ArrayList<>();
        for (Video video : videoList) {
              if (video.getChapterId().equals(chapter.getId())){

             VideoVo videoVo = new VideoVo();
              BeanUtils.copyProperties(video,videoVo);
              videoVos.add(videoVo);

              }
            chapterVo.setChildren(videoVos);


          }
        }

        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count>0){
            throw new wxjException(20001,"不能删除");
        }else {
            int result = baseMapper.deleteById(chapterId);

            return result>0; //当result>0  true 否则 false
        }


    }

    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }


}
