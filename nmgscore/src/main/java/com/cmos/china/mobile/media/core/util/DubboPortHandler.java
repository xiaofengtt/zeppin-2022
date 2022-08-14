package com.cmos.china.mobile.media.core.util;

import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.ProtocolConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Created by 李刚 on 2016/9/1.
 * dubbo端口注入
 */
public class DubboPortHandler implements ApplicationContextAware {

    private String port;

    private ApplicationContext applicationContext;

    public  void init(){
        Map<String, ProtocolConfig> beansOfType  = applicationContext.getBeansOfType(ProtocolConfig.class);
        for (Map.Entry<String, ProtocolConfig> item : beansOfType.entrySet()) {
            if(StringUtils.isEmpty(port)){
                item.getValue().setPort(NetUtils.getAvailablePort());
            }else{
                item.getValue().setPort(Integer.valueOf(port));
            }

        }
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
