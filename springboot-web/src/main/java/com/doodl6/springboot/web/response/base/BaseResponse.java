package com.doodl6.springboot.web.response.base;


/**
 * 基础响应结果类
 */
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

    public String getMessage() {
        return message;
    }

    public BaseResponse() {
    }

    public static <T> BaseResponse<T> success(T t) {
        return new BaseResponse<>(t);
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getResult() {
        return result;
    }

    public BaseResponse setResult(int result) {
        this.result = result;
        return this;
    }

    public T getData() {
        return data;
    }

    public BaseResponse setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 判断是否响应成功
     */
    public boolean isSuccess() {
        return result == ResponseCode.SUCCESS;
    }

}
