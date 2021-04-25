package com.doodl6.springboot.dubbo.api;


import com.doodl6.springboot.dubbo.api.request.GetDubboInfoRequest;
import com.doodl6.springboot.dubbo.api.response.GetDubboInfoResponse;

public interface FirstDubboService {

    GetDubboInfoResponse getDubboInfo(GetDubboInfoRequest getDubboInfoRequest);
}
