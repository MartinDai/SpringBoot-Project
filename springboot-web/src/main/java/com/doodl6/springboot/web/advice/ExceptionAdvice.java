package com.doodl6.springboot.web.advice;

import com.doodl6.springboot.common.util.LogUtil;
import com.doodl6.springboot.web.response.base.BaseResponse;
import com.doodl6.springboot.web.response.base.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一处理异常
 * Created by daixiaoming on 2018/5/5.
 */
@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse<String> handleException(HttpServletRequest request, Exception e) {
        BaseResponse<String> response = new BaseResponse<>();
        if (e instanceof IllegalArgumentException) {
            response.setMessage(e.getMessage());
            response.setResult(ResponseCode.PARAMETER_ERROR);
        } else {
            LOGGER.error(LogUtil.buildLog("请求出现异常", request.getRequestURI(), request.getParameterMap()), e);

            response.setMessage("服务器未知异常");
            response.setResult(ResponseCode.UNKNOWN_ERROR);
        }

        return response;
    }

}
