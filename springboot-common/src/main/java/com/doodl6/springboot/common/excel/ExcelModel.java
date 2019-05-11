package com.doodl6.springboot.common.excel;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * Created by daixiaoming on 2018/9/19.
 */
public class ExcelModel {

    private ExcelVersion excelVersion;

    private List<SheetModel> sheets;

    public ExcelModel(ExcelVersion excelVersion, List<SheetModel> sheets) {
        this.excelVersion = excelVersion;
        this.sheets = sheets;
    }

    public ExcelVersion getExcelVersion() {
        return excelVersion;
    }

    public void setExcelVersion(ExcelVersion excelVersion) {
        this.excelVersion = excelVersion;
    }

    public List<SheetModel> getSheets() {
        return sheets;
    }

    public void setSheets(List<SheetModel> sheets) {
        this.sheets = sheets;
    }

    /**
     * 生成excel对象
     */
    public Workbook generateWorkbook() {
        Workbook workbook;
        switch (excelVersion) {
            case XLS:
                workbook = new HSSFWorkbook();
                break;
            case XLSX:
                workbook = new XSSFWorkbook();
                break;
            default:
                throw new IllegalArgumentException("不支持的excel格式");
        }

        Map<String, CellStyle> styles = CellStyleFactory.generateDefaultStyles(workbook);
        CellStyle dataStyle = createDataStyle(workbook);
        if (CollectionUtils.isNotEmpty(sheets)) {
            for (SheetModel sheetModel : sheets) {
                int rowIndex = 0;
                Sheet sheet;
                String sheetName = sheetModel.getName();
                if (StringUtils.isBlank(sheetName)) {
                    sheet = workbook.createSheet();
                } else {
                    sheet = workbook.createSheet(sheetName);
                }

                List<Header> headers = sheetModel.getHeaders();
                // 创建Excel标题
                CellStyle titleStyle = styles.get(CellStyleFactory.STYLE_NAME_TITLE);
                rowIndex = createTitle(sheet, sheetModel.getTitle(), headers.size(), rowIndex, titleStyle);
                // 创建Excel头部
                rowIndex = createHead(sheet, headers, rowIndex, titleStyle);
                // 创建Excel数据
                createData(sheet, headers, rowIndex, sheetModel.getDataList(), dataStyle);
            }
        }

        return workbook;
    }

    /**
     * 创建标题
     */
    private static int createTitle(Sheet sheet, String title, int headerSize, int rowIndex, CellStyle style) {
        // 如果未设定Title
        if (StringUtils.isBlank(title)) {
            return rowIndex;
        }

        Row row = sheet.createRow(rowIndex);
        Cell cell = row.getCell(0);
        if (null == cell) {
            cell = row.createCell(0);
        }
        cell.setCellValue(title);
        cell.setCellStyle(style);
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, headerSize - 1));
        return rowIndex + 1;
    }

    /**
     * 创建表头
     */
    private static int createHead(Sheet sheet, List<Header> headers, int rowIndex, CellStyle style) {
        Row row = sheet.createRow(rowIndex);
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            Header head = headers.get(i);
            sheet.setColumnWidth(i, 20 * 256);
            Cell cell = row.createCell(i);
            cell.setCellValue(head.getName());
            cell.setCellStyle(style);
        }

        return rowIndex + 1;
    }

    private static void createData(Sheet sheet, List<Header> headers, int rowIndex, List<ExcelData> dataList, CellStyle style) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }

        for (ExcelData data : dataList) {
            rowIndex = createDataRow(sheet, rowIndex, data, headers, style);
        }
    }

    /**
     * 创建数据行
     */
    private static int createDataRow(Sheet sheet, int rowIndex, ExcelData data, List<Header> headers, CellStyle style) {
        // 头部列数
        int size = headers.size();
        // 需要合并单元格
        if (data.isMergeCells()) {
            //最大合并格数
            int mergeRowCount = data.getMergeRowCount();
            //遍历列数
            for (int i = 0; i < size; i++) {
                Header header = headers.get(i);
                List<String> values = data.getValue(header.getField());
                int valueSize = values.size();
                //每次合并的行数
                int merCount = mergeRowCount / (valueSize == 0 ? 1 : valueSize) - 1;
                //首行数
                int firstRowCount = rowIndex;
                //尾行数
                int lastRowCount = firstRowCount + merCount;

                //如果值为空! 合并单元格之后跳过
                if (valueSize == 0) {
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRowCount, lastRowCount, i, i);
                    setRangeAddressStyle(sheet, cellRangeAddress);
                    sheet.addMergedRegion(cellRangeAddress);
                    continue;
                }

                //填充合并单元格里的值
                for (String value : values) {
                    if (merCount != 0) {
                        CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRowCount, lastRowCount, i, i);
                        setRangeAddressStyle(sheet, cellRangeAddress);
                        sheet.addMergedRegion(cellRangeAddress);
                    }
                    Row row = getRow(sheet, firstRowCount);
                    Cell cell = getCell(row, i);
                    // 设定单元格值为filed值
                    cell.setCellValue(value);
                    // 设定样式
                    cell.setCellStyle(style);

                    //下移一行
                    if (merCount != 0) {
                        firstRowCount = lastRowCount + 1;
                    } else {
                        firstRowCount++;
                    }
                }
            }

            //返回下一行
            return rowIndex + mergeRowCount;
        }
        //无需合并单元格
        else {
            Row row = sheet.createRow(rowIndex);
            // 循环处理
            for (int i = 0; i < size; i++) {
                // 当前列
                Header header = headers.get(i);
                // 创建当前单元格
                Cell cell = row.createCell(i);
                // 取得filed
                String field = header.getField();
                // 取得值
                List<String> values = data.getValue(field);
                if (CollectionUtils.isNotEmpty(values)) {
                    String value = values.get(0);
                    // 设定单元格值为filed值
                    cell.setCellValue(value);
                }
                // 设定样式
                cell.setCellStyle(style);
            }
        }

        return rowIndex + 1;
    }

    /**
     * 创建数据样式
     */
    private static CellStyle createDataStyle(Workbook workBook) {
        CellStyle style = workBook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        Font font = workBook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);// 字体
        font.setFontHeightInPoints((short) 9);// 字号
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 颜色
        style.setFont(font);
        //设置单元格内换行 若不设置 "\r\n"是不会换行的.需要鼠标点一下才会换行 加上这个设置就会换行了!
        style.setWrapText(true);

        //指定单元格内容为文本类型
        DataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("@"));

        return style;
    }

    private static void setRangeAddressStyle(Sheet sheet, CellRangeAddress cellRangeAddress) {
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeAddress, sheet);
    }

    private static Row getRow(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        return row;
    }

    private static Cell getCell(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }
        return cell;
    }

}
