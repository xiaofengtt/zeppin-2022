package com.makati.common.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 需要user_id 校验的请求
 */
@Data
public class UserIdReq extends CommonReq{
    @NotEmpty
    private String user_id;
}
