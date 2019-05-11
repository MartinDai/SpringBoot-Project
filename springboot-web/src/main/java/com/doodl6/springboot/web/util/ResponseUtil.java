package com.doodl6.springboot.web.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URLEncoder;

/**
 * 响应工具类
 */
public final class ResponseUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtil.class);

    private ResponseUtil() {
    }

    /**
     * 输出客户端JSON内容
     */
    public static void responseJSON(HttpServletResponse response, Object object) {
        writeResponse(response, JSON.toJSONString(object), "application/json");
    }

    /**
     * 输出客户端html内容
     */
    public static void responseHtml(HttpServletResponse response, String text) {
        writeResponse(response, text, "text/html");
    }

    /**
     * 输出客户端xml内容
     */
    public static void responseXml(HttpServletResponse response, String text) {
        writeResponse(response, text, "text/xml");
    }

    /**
     * 输出客户端excel
     */
    public static void responseExcel(HttpServletResponse response, Workbook workbook, String fileName) {
        try (OutputStream os = response.getOutputStream()) {
            // 设定字符集
            response.setCharacterEncoding("UTF-8");
            // 设定Content类型
            response.setContentType("multipart/form-data");
            // 设定Http头部
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(os);
        } catch (IOException e) {
            LOGGER.error("输出excel异常", e);
        }
    }

    public static void writeSupportCrossDomain(HttpServletRequest request, HttpServletResponse response) {
        String origin = request.getHeader("Origin");
        if (StringUtils.isNotBlank(origin) && origin.endsWith("doodl6.com")) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
    }

    private static void writeResponse(HttpServletResponse response, String content, String contentType) {
        response.setContentType(contentType);
        response.setCharacterEncoding("utf-8");
        try (Writer writer = response.getWriter()) {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("输出客户端内容出现异常", e);
        }
    }
}
