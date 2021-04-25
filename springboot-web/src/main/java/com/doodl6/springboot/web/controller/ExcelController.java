package com.doodl6.springboot.web.controller;

import com.alibaba.excel.EasyExcel;
import com.doodl6.springboot.common.excel.ExcelProcessResult;
import com.doodl6.springboot.common.excel.ExcelVersion;
import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.web.dto.ExcelData;
import com.doodl6.springboot.web.listener.ExcelDataListener;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

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
            excelData.setAge(18);
            dataList.add(excelData);

            excelData = new ExcelData();
            excelData.setName("李四");
            excelData.setAge(19);
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
    public BaseResponse<String> upload(@RequestParam MultipartFile file) throws IOException {
        ExcelProcessResult processResult = new ExcelProcessResult();
        EasyExcel.read(file.getInputStream(), ExcelData.class, new ExcelDataListener(processResult)).sheet().doRead();

        BaseResponse<String> response = new BaseResponse<>();
        processResult.waitForDone();
        response.setData("文件上传成功，文件名:" + file.getOriginalFilename() + "，有效行数:" + processResult.getRows());

        return response;
    }
}
