package com.wxj.edu.service;

import com.wxj.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxj.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-19
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file,SubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
