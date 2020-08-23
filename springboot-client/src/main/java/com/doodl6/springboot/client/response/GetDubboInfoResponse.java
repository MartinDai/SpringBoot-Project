package com.doodl6.springboot.client.response;


import com.doodl6.springboot.client.domain.DubboDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetDubboInfoResponse extends BaseResponse {

    private DubboDomain dubboDomain;

}
