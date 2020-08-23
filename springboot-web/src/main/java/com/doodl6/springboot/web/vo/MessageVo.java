package com.doodl6.springboot.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by daixiaoming on 2018-12-10.
 */
@Getter
@Setter
public class MessageVo {

    private int userId;

    private String userName;

    private String content;

    private Date sendTime;

}
