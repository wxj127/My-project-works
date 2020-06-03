package com.demo.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    @Test
    public void readExcel(){
        String filename="F:\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();

    }



    public static void main(String[] args) {
        //实现excel的写操作
        //1，设置名称和地址
        String filename="F:\\write.xlsx";
        //2.调用EasyExcel方法
        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());
    }
    //创建方法创建list
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSname("lice"+i);
            demoData.setSno(i);
            list.add(demoData);
        }
        return list;
    }


}
