package com.makati.common.header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeaderInfo implements Serializable {
    private  String platform       ;//1-andorid 2-ios
    private  String deviceModel   ;// 设备唯一标识符
    private  String version        ;//当前版本  1.1.1
    private  String userToken     ;//token
    private  String md5Salt       ;//密钥
}
