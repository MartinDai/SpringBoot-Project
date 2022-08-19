package com.doodl6.springboot.web.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.doodl6.springboot.common.excel.ExcelVersion;
import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.web.dto.ExcelData;
import com.google.common.collect.Lists;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * excel控制类
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    /**
     * 下载接口
     */
    @GetMapping("download")
    public void down(HttpServletResponse response) {
        try {
            List<ExcelData> dataList = Lists.newArrayList();
            ExcelData excelData = new ExcelData();
            excelData.setName("张三");
            excelData.setAge("18");
            dataList.add(excelData);

            excelData = new ExcelData();
            excelData.setName("李四");
            excelData.setAge("19");
            dataList.add(excelData);

            String fileName = "人员数据表" + System.currentTimeMillis() + ExcelVersion.XLSX.getSuffix();
            // 设定字符集
            response.setCharacterEncoding("UTF-8");
            // 设定Content类型
            response.setContentType("multipart/form-data");
            // 设定Http头部
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            EasyExcel.write(response.getOutputStream(), ExcelData.class).sheet("人员数据").doWrite(dataList);
        } catch (Exception e) {
            throw new IllegalStateException("下载出现异常");
        }
    }

    /**
     * 上传接口
     */
    @PostMapping("upload")
    public BaseResponse<List<ExcelData>> upload(@RequestParam MultipartFile file) throws IOException {
        ExcelReader excelReader = EasyExcel.read(file.getInputStream()).head(ExcelData.class).build();
        List<ExcelData> dataList = new ExcelReaderSheetBuilder(excelReader).sheetNo(0).headRowNumber(1).doReadSync();
        ExcelReadHeadProperty headProperty = excelReader.analysisContext().currentReadHolder().excelReadHeadProperty();
        Set<String> headSet = headProperty.getHeadMap().values().stream().map(Head::getHeadNameList).map(strings -> strings.get(0)).collect(Collectors.toSet());
        Assert.isTrue(headSet.contains("姓名"), "缺少【姓名】字段");
        Assert.isTrue(headSet.contains("年龄"), "缺少【年龄】字段");

        return BaseResponse.success(dataList);
    }
}
