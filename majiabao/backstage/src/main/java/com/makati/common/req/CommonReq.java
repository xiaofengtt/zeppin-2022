package com.makati.common.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 公共请求参数
 * platform	 （必传项）	1：android  2：ios
 * device_model	false	设备型号，如小米4s，iPhone5s
 * platform_flag		设备标识 android获取的是imei ios获取的是idfv
 * sign	 （必传项）	每一次请求，如果有参数就要带上sign值，其计算方式为： 需将键值对按照自然排序后，在拼接上加密的key值（登陆后用md5_salt,没有登陆则用default_key），用md5获取摘要
 */
@Data
public class CommonReq implements Serializable {

    private String platform ;

    private String device_model ;

    private String platform_flag;

    private String sign ;
}
