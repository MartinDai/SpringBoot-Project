package com.doodl6.springboot.common.web.advice;

import com.doodl6.springboot.common.util.LogUtil;
import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.common.web.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一处理异常
 * Created by daixiaoming on 2018/5/5.
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse<String> handleException(HttpServletRequest request, Exception e) {
        BaseResponse<String> response = new BaseResponse<>();
        if (e instanceof IllegalArgumentException) {
            response.setMessage(e.getMessage());
            response.setResult(ResponseCode.PARAMETER_ERROR);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            response.setMessage("不支持的请求方式");
            response.setResult(ResponseCode.PARAMETER_ERROR);
        } else {
            log.error(LogUtil.buildLog("请求出现异常", request.getRequestURI(), request.getParameterMap()), e);

            response.setMessage("服务器未知异常");
            response.setResult(ResponseCode.UNKNOWN_ERROR);
        }

        return response;
    }

}
