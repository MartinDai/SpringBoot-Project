package com.doodl6.springboot.common.web.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class RequestBodyWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public RequestBodyWrapper(HttpServletRequest request) {
        super(request);
        body = getRequestBody(request);
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    private static byte[] getRequestBody(HttpServletRequest request) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream in = request.getInputStream();
            byte[] buf = new byte[1024];

            while (true) {
                int len = in.read(buf);
                if (len == -1) {
                    return outputStream.toByteArray();
                }

                if (len > 0) {
                    outputStream.write(buf, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

}
