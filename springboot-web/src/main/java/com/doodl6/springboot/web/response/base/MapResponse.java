package com.doodl6.springboot.web.response.base;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Map结果类
 */
public class MapResponse extends BaseResponse<Map<String, Object>> {

    public MapResponse appendData(String key, Object value) {
        Map<String, Object> data = getData();
        if (data == null) {
            data = Maps.newHashMap();
            setData(data);
        }
        data.put(key, value);

        return this;
    }

}
