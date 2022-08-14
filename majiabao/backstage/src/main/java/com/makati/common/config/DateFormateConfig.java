package com.makati.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DateFormateConfig {
    private String dateFormate = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean() {
        Jackson2ObjectMapperFactoryBean r = new Jackson2ObjectMapperFactoryBean();
        r.setDateFormat(new SimpleDateFormat(dateFormate));
        return r;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(Jackson2ObjectMapperFactoryBean objectMapper) {
        MappingJackson2HttpMessageConverter r = new MappingJackson2HttpMessageConverter(objectMapper.getObject());
        List<MediaType> mediaTypeList= Arrays.asList(MediaType.APPLICATION_JSON_UTF8);
        r.setSupportedMediaTypes(mediaTypeList);
        return r;
    }
}
