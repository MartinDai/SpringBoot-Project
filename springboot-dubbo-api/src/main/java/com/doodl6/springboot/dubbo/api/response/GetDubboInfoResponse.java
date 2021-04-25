package com.doodl6.springboot.dubbo.api.response;


import com.doodl6.springboot.dubbo.api.domain.DubboDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetDubboInfoResponse extends BaseResponse {

    private DubboDomain dubboDomain;

}
