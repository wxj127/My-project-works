package com.wxj.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxj.edu.entity.Subject;
import com.wxj.edu.entity.excel.SubjectData;
import com.wxj.edu.service.SubjectService;
import com.wxj.servicebase.ExceptionHandler.wxjException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    public SubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
           if (subjectData==null){
               throw new wxjException(20001,"文件数据为空！");
           }
        Subject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
           if (existOneSubject==null){ //没有相同一级分类，进行添加
               existOneSubject = new Subject();
               existOneSubject.setParentId("0");
               existOneSubject.setTitle(subjectData.getOneSubjectName());
               subjectService.save(existOneSubject);
           }
           //判断二级分类
        //取一级id值
        String pid =existOneSubject.getId();
        Subject existtwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(),pid);
        if (existtwoSubject==null){
            existtwoSubject = new Subject();
            existtwoSubject.setParentId(pid);
            existtwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existtwoSubject);
        }
    }
    //查询一级分类
    private Subject  existOneSubject(SubjectService subjectService,String name){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        Subject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }
    //查询二级分类
    private Subject  existTwoSubject(SubjectService subjectService,String name,String pid){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        Subject TwoSubject = subjectService.getOne(wrapper);
        return TwoSubject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
