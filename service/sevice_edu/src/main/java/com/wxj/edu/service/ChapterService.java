package com.wxj.edu.service;

import com.wxj.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxj.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-20
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoByCourseId(String chapterId);


    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String id);
}
