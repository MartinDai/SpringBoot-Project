package com.doodl6.springboot.common.excel;

import com.google.common.collect.Maps;
import org.apache.poi.ss.usermodel.*;

import java.util.Map;

/**
 * 样式工厂类
 * Created by daixiaoming on 2018/9/19.
 */
public class CellStyleFactory {

    public static final String STYLE_NAME_TITLE = "title";

    public static final String STYLE_NAME_HEAD = "head";

    public static final String STYLE_NAME_CELL = "cell";

    public static final String STYLE_NAME_ERROR = "error";

    public static final String STYLE_NAME_WARNING = "warning";

    public static final String STYLE_NAME_DATE_CELL = "dateCell";

    public static final String STYLE_NAME_DATE_ERROR = "dateError";

    public static final String STYLE_NAME_DATE_WARNING = "dateWarning";

    public static Map<String, CellStyle> generateDefaultStyles(Workbook workbook) {
        Map<String, CellStyle> styles = Maps.newHashMap();
        // 标题样式
        styles.put(STYLE_NAME_TITLE, makeTitleStyle(workbook));
        // 表头样式
        styles.put(STYLE_NAME_HEAD, makeHeadStyle(workbook));
        // 数据样式
        styles.put(STYLE_NAME_CELL, makeCellStyle(workbook));
        // 数据样式
        styles.put(STYLE_NAME_ERROR, makeErrorStyle(workbook));
        // 数据样式
        styles.put(STYLE_NAME_WARNING, makeWarningStyle(workbook));
        // 数据样式
        styles.put(STYLE_NAME_DATE_CELL, makeDateCellStyle(workbook));
        // 数据样式
        styles.put(STYLE_NAME_DATE_ERROR, makeDateErrorStyle(workbook));
        // 数据样式
        styles.put(STYLE_NAME_DATE_WARNING, makeDateWarningStyle(workbook));

        return styles;
    }

    /**
     * 创建标题样式
     */
    private static CellStyle makeTitleStyle(Workbook workbook) {
        // 创建单元格样式
        CellStyle style = workbook.createCellStyle();
        // 设置前景填充样式
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 填充字前景色:紫色
        style.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
        // 左边框
        style.setBorderLeft(BorderStyle.THIN);
        // 右边框
        style.setBorderRight(BorderStyle.THIN);
        // 上边框
        style.setBorderTop(BorderStyle.THIN);
        // 下边框
        style.setBorderBottom(BorderStyle.THIN);
        // 居中对齐
        style.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 创建字体
        Font font = workbook.createFont();
        // 字体
        font.setFontName("Arial");
        // 字号
        font.setFontHeightInPoints((short) 20);
        // 加粗
        font.setBold(true);
        // 颜色
        font.setColor(IndexedColors.WHITE.index);
        // 设定字体
        style.setFont(font);
        // 创建格式
        DataFormat format = workbook.createDataFormat();
        // 数据格式
        style.setDataFormat(format.getFormat("@"));
        // 返回标题样式
        return style;
    }

    /**
     * 创建头部样式
     */
    private static CellStyle makeHeadStyle(Workbook workbook) {
        // 创建单元格样式
        CellStyle style = workbook.createCellStyle();
        // 设置前景填充样式
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 填充字前景色:紫色
        style.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
        // 左边框
        style.setBorderLeft(BorderStyle.THIN);
        // 右边框
        style.setBorderRight(BorderStyle.THIN);
        // 上边框
        style.setBorderTop(BorderStyle.THIN);
        // 下边框
        style.setBorderBottom(BorderStyle.THIN);
        // 居中对齐
        style.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 创建字体
        Font font = workbook.createFont();
        // 字体
        font.setFontName("Arial");
        // 字号
        font.setFontHeightInPoints((short) 11);
        // 加粗
        font.setBold(true);
        // 颜色
        font.setColor(IndexedColors.WHITE.index);
        // 设定字体
        style.setFont(font);
        // 创建格式
        DataFormat format = workbook.createDataFormat();
        // 数据格式
        style.setDataFormat(format.getFormat("@"));
        // 返回样式
        return style;
    }

    /**
     * Description: 设定样式<br>
     */
    public static CellStyle makeCellStyle(Workbook workbook) {
        // 创建单元格样式
        CellStyle style = workbook.createCellStyle();
        // 左对齐
        style.setAlignment(HorizontalAlignment.LEFT);
        // 垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 左边框
        style.setBorderLeft(BorderStyle.THIN);
        // 右边框
        style.setBorderRight(BorderStyle.THIN);
        // 上边框
        style.setBorderTop(BorderStyle.THIN);
        // 下边框
        style.setBorderBottom(BorderStyle.THIN);
        // 创建字体
        Font font = workbook.createFont();
        // 字体
        font.setFontName("Arial");
        // 字号
        font.setFontHeightInPoints((short) 9);
        // 颜色
        font.setColor(IndexedColors.BLACK.index);
        // 设定字体
        style.setFont(font);
        // 创建格式
        DataFormat format = workbook.createDataFormat();
        // 数据格式
        style.setDataFormat(format.getFormat("@"));
        // 返回样式
        return style;
    }

    /**
     * Description: 设定样式<br>
     */
    private static CellStyle makeErrorStyle(Workbook workbook) {
        // 创建单元格样式
        CellStyle style = makeCellStyle(workbook);
        // 填充背景色:红色
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        // 设置前景填充样式
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 创建格式
        DataFormat format = workbook.createDataFormat();
        // 数据格式
        style.setDataFormat(format.getFormat("@"));
        // 返回样式
        return style;
    }

    /**
     * Description: 设定样式<br>
     */
    private static CellStyle makeWarningStyle(Workbook workbook) {
        // 创建单元格样式
        CellStyle style = makeCellStyle(workbook);
        // 填充背景色:橙色
        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        // 设置前景填充样式
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 创建格式
        DataFormat format = workbook.createDataFormat();
        // 数据格式
        style.setDataFormat(format.getFormat("@"));
        // 返回样式
        return style;
    }

    /**
     * Description: 设定样式<br>
     */
    private static CellStyle makeDateCellStyle(Workbook workbook) {
        // 创建单元格样式
        CellStyle style = makeCellStyle(workbook);
        // 创建格式
        DataFormat format = workbook.createDataFormat();
        // 数据格式
        style.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));
        // 返回样式
        return style;
    }

    /**
     * Description: 设定样式<br>
     */
    private static CellStyle makeDateErrorStyle(Workbook workbook) {
        // 创建单元格样式
        CellStyle style = makeDateCellStyle(workbook);
        // 填充背景色:红色
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        // 设置前景填充样式
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 创建格式
        DataFormat format = workbook.createDataFormat();
        // 数据格式
        style.setDataFormat(format.getFormat("@"));
        // 返回样式
        return style;
    }

    /**
     * Description: 设定样式<br>
     */
    private static CellStyle makeDateWarningStyle(Workbook workbook) {
        // 创建单元格样式
        CellStyle style = makeDateCellStyle(workbook);
        // 填充背景色:橙色
        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        // 设置前景填充样式
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 创建格式
        DataFormat format = workbook.createDataFormat();
        // 数据格式
        style.setDataFormat(format.getFormat("@"));
        // 返回样式
        return style;
    }

}
