package com.doodl6.springboot.web.service.cache.memcached;

import com.alibaba.fastjson.JSON;
import com.schooner.MemCached.AbstractTransCoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by daixiaoming on 2018/5/12.
 */
public class JSONObjectTransCoder extends AbstractTransCoder {

    @Override
    public void encode(OutputStream out, Object object) throws IOException {
        out.write(JSON.toJSONString(object).getBytes());
        out.close();
    }

    @Override
    public Object decode(InputStream input) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        input.close();

        return result.toString("UTF-8");
    }
}
