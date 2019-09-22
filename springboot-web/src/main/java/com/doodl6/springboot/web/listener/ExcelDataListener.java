package com.doodl6.springboot.web.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.common.excel.ExcelProcessResult;
import com.doodl6.springboot.web.dto.ExcelData;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExcelDataListener extends AnalysisEventListener<ExcelData> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ExcelProcessResult result;

    private List<ExcelData> dataList = Lists.newLinkedList();

    public ExcelDataListener(ExcelProcessResult result) {
        this.result = result;
    }

    /**
     * 每解析一行调用一次
     */
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        dataList.add(excelData);
        logger.info("读取到excel上传数据 | {} | {}", analysisContext.readRowHolder().getRowIndex(), JSON.toJSONString(excelData));
        //这里可以处理批量入库
    }

    /**
     * 全部解析完成
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        //这里可以处理统一入库
        result.setRows(dataList.size());
        result.processDone();
    }
}
