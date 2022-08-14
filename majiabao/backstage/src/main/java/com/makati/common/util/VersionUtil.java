package com.makati.common.util;

import com.makati.common.exception.BusinessException;

public class VersionUtil {
    public static long getVersionLong(String version){
        if(version == null){
            throw new BusinessException("9987","版本号为空");
        }
        version = version.replace("v","");
        String[] split = version.split("\\.");
        if(split.length != 3){
            throw new BusinessException("9988","版本号有误");
        }
        return DecimalCalculate.getLong(split[2]) + DecimalCalculate.getLong(split[1]) * 100 + DecimalCalculate.getLong(split[0]) * 10000;
    }

    public static void main(String[] args) {
        System.out.println(getVersionLong("v1.1.5"));
    }
}
