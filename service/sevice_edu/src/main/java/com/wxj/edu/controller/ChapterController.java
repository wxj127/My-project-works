package com.wxj.edu.controller;


import com.wxj.R;
import com.wxj.edu.entity.Chapter;
import com.wxj.edu.entity.chapter.ChapterVo;
import com.wxj.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/edu/chapter")

public class ChapterController {

    @Autowired
    private ChapterService chapterService;
 //课程大纲列表，根据Id查询
    @GetMapping("getChapterVideo/{chapterId}")
    public R getChapterVideo(@PathVariable("chapterId") String chapterId ){

      List<ChapterVo> list = chapterService.getChapterVideoByCourseId(chapterId);
        return R.Ok().data("allChapterVideo",list);
    }
    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody Chapter chapter){
       chapterService.save(chapter);
       return R.Ok();
    }
    //根据id查询
    @GetMapping("getChapter/{chapterId}")
    public R getChapter(@PathVariable("chapterId") String chapterId){
        Chapter eduChapter = chapterService.getById(chapterId);
        return R.Ok().data("chapter",eduChapter);
    }
    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody Chapter chapter){
          chapterService.updateById(chapter);
        return R.Ok();
    }
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag){
            return R.Ok();
        }else {
            return R.error();
        }

    }


}

