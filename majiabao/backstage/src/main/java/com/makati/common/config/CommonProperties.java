package com.makati.common.config;

import com.makati.common.exception.BusinessException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "58qipai")
@Slf4j
@Data
@Component
public class CommonProperties {
    private Map<String,String> common ;


    public String getValue(String key){
        String value = common.get(key);
        if(null == value){
            throw new BusinessException("9999","58qipai.common."+ key + " 未在yml中配置");
        }
        return value;
    }

    public int getIntegeValue(String key){
        return Integer.valueOf(getValue(key));
    }

    public boolean getBooleanValue(String key){
        return Boolean.valueOf(getValue(key));
    }

//    private String imgPrefix ;
    private String registerAgent ;
    private String chargeTypeFlag;
    private String qrcode;
    private String qrcodeName;
}
