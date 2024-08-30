package com.doodl6.springboot.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 可以支持实时输出内容，供xhttp.html页面使用
 * Created by daixiaoming on 2018/12/20.
 */
@Tag(name = "Xhttp")
@RestController
@RequestMapping("/xhttp")
public class XhttpController {

    /**
     * 获取响应内容
     */
    @Operation(summary = "获取响应内容")
    @GetMapping("/getResponseContent")
    public void getResponseContent(HttpServletResponse response, String content) throws IOException, InterruptedException {
        if (StringUtils.isEmpty(content)) {
            return;
        }

        response.setHeader("Content-Type", "text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        for (int i = 0; i < 10; i++) {
            writer.write(content + i);
            writer.flush();
            Thread.sleep(1000);
        }
    }

}
