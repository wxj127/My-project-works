package com.wxj.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxj.edu.entity.Subject;
import com.wxj.edu.entity.excel.SubjectData;
import com.wxj.edu.entity.subject.OneSubject;
import com.wxj.edu.entity.subject.TwoSubject;
import com.wxj.edu.listener.SubjectExcelListener;
import com.wxj.edu.mapper.SubjectMapper;
import com.wxj.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void saveSubject(MultipartFile file,SubjectService subjectService) {
        //文件输入流
        try {
            InputStream in= file.getInputStream();
            EasyExcel.read(in,SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级分类
        QueryWrapper<Subject> wrapperOne =new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<Subject> OneSubjects = baseMapper.selectList(wrapperOne);

        //查询所有二级分类
        QueryWrapper<Subject> wrapperTwo =new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<Subject> TwoSubjectsList = baseMapper.selectList(wrapperTwo);
          //封装一级分类
        List<OneSubject> finaSubjectList = new ArrayList<>();
        for (Subject oneSubjectList : OneSubjects) {
            OneSubject oneSubject = new OneSubject();
            //工具类把oneSubjectList复制到oneSubject中去
            BeanUtils.copyProperties(oneSubjectList, oneSubject);
            finaSubjectList.add(oneSubject);

            List<TwoSubject> twoSubject = new ArrayList<>();
            for (Subject twoSubjectList : TwoSubjectsList) {
                //判断一级二级对应 getParentId = id
                if (twoSubjectList.getParentId().equals(oneSubjectList.getId())){
                  TwoSubject twoSubject1 = new TwoSubject();
                  BeanUtils.copyProperties(twoSubjectList, twoSubject1);
                  twoSubject.add(twoSubject1);
                }
            }
            oneSubject.setChildren(twoSubject);
        }




        return finaSubjectList;
    }
}
