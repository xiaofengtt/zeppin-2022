package com.makati.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.makati.common.exception.ErrorResultEnums;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("返回数据")
public class Response<T> implements Serializable {

    public final static String SUCCESS = "200";

    @ApiModelProperty("状态码：(200成功、非200失败)")
    private String code ;
    @ApiModelProperty("状态信息")
    private String msg;
    @ApiModelProperty("返回对象")
    private T data;



    public static Response successResponce(){
        Response<Object> success = new Response<>();
        success.setCode(SUCCESS);
        return success;
    }

    public static Response successResponce(Object data){
        Response<Object> success = new Response<>();
        success.setCode(SUCCESS);
        success.setData(data);
        success.setMsg("成功");
        return success;
    }

    public static Response failResponce(ErrorResponse errorResponse){
        Response<Object> fail = new Response<>();
        fail.setCode(errorResponse.getErrorCode());
        fail.setMsg(errorResponse.getErrorMsg());
        return fail;
    }

    public static Response failResponce(String code ,String msg){
        Response<Object> fail = new Response<>();
        fail.setCode(code);
        fail.setMsg(msg);
        return fail;
    }

    public static Response failResponce(ErrorResultEnums errorResultEnums){
        Response<Object> fail = new Response<>();
        fail.setCode(errorResultEnums.getCode().toString());
        fail.setMsg(errorResultEnums.getMsg());
        return fail;
    }

}
