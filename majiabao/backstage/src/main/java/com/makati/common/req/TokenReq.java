package com.makati.common.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 需要user_token 校验的请求
 */
@Data
public class TokenReq extends CommonReq{
    @NotEmpty
    private String user_token;
    @NotEmpty
    private String md5_salt;

}
