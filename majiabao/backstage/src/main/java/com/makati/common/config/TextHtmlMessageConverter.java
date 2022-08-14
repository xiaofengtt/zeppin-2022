package com.makati.common.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;


/**
 * 由于Fegin的默认解析器只支持json解析器，由于老项目中很多接口返回值是[text/plain] [text/html]，所以增加此类解析方式
 * @author david
 */
public class TextHtmlMessageConverter extends MappingJackson2HttpMessageConverter {
    public TextHtmlMessageConverter(){
        List<MediaType> mediaTypes = new ArrayList<>();
        //加入text/plain类型的支持
        mediaTypes.add(MediaType.TEXT_PLAIN);
        //加入text/html类型的支持
        mediaTypes.add(MediaType.TEXT_HTML);
        setSupportedMediaTypes(mediaTypes);// tag6

    }
}
