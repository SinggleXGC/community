package com.xgc.community.dto;

import lombok.Data;

/**
 * DTO 就是数据传输对象
 * 封装了获取AccessToken需要传递的参数
 */
@Data
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
