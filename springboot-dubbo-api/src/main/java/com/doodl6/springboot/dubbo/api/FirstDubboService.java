package com.doodl6.springboot.dubbo.api;


import com.doodl6.springboot.dubbo.api.request.GetDubboInfoRequest;
import com.doodl6.springboot.dubbo.api.response.GetDubboInfoResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("dubbo-rest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface FirstDubboService {

    @POST
    @Path("getDubboInfo")
    GetDubboInfoResponse getDubboInfo(GetDubboInfoRequest getDubboInfoRequest);

}
