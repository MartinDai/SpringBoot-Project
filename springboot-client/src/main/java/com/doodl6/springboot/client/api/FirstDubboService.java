package com.doodl6.springboot.client.api;

import com.doodl6.springboot.client.request.GetDubboInfoRequest;
import com.doodl6.springboot.client.response.GetDubboInfoResponse;

public interface FirstDubboService {

    GetDubboInfoResponse getDubboInfo(GetDubboInfoRequest getDubboInfoRequest);
}
