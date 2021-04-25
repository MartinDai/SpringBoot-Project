package com.doodl6.springboot.common.web.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 响应代码
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseCode {

    /**
     * 未知异常
     */
    public static final int UNKNOWN_ERROR = -1;

    /**
     * 成功
     */
    public static final int SUCCESS = 200;

    /**
     * 参数错误
     */
    public static final int PARAMETER_ERROR = 300;

    /**
     * 业务异常
     */
    public static final int BIZ_ERROR = 400;

}
