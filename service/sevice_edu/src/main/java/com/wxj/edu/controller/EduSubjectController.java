package com.wxj.edu.controller;


import com.wxj.R;
import com.wxj.edu.entity.subject.OneSubject;
import com.wxj.edu.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-19
 */
@RestController
@RequestMapping("/edu/subject")

public class EduSubjectController {
    @Autowired
    private SubjectServiceImpl subjectService;
    //添加课程分类
    //获取上传过来的文件，把内容读取处理
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传过来的文件
        subjectService.saveSubject(file,subjectService);
         return R.Ok();
    }
    //课程分类的列表功能
    @GetMapping("getAllSubject")
    public R  getAllSubject(){
        //OneSubject已经包含TwoSubject，所有只需要返回二级分类
     List<OneSubject> list =subjectService.getAllOneTwoSubject();

        return R.Ok().data("list",list);
    }

}

