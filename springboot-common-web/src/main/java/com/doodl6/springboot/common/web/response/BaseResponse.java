package com.doodl6.springboot.common.web.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 基础响应结果类
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class BaseResponse<T> {

    /**
     * 错误信息
     */
    private String message;
    /**
     * 结果代码
     */
    private int result = ResponseCode.SUCCESS;
    /**
     * 响应数据
     */
    private T data;

    public static BaseResponse<Void> success() {
        return new BaseResponse<>();
    }

    public static <T> BaseResponse<T> success(T t) {
        return new BaseResponse<>(t);
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    /**
     * 判断是否响应成功
     */
    public boolean isSuccess() {
        return result == ResponseCode.SUCCESS;
    }

}
