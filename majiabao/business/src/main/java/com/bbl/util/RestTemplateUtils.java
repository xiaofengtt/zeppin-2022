package com.bbl.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * restTemplateUtils 高性能请求http https
 */
@Slf4j
public class RestTemplateUtils {

    /**
     * http 请求 GET
     *
     * @param url           地址
     * @param params        参数
     * @param connecTimeout 连接时间
     * @param readTimeout   读取时间
     * @param retryCount    重试机制
     * @return String 类型
     */
    public static String getHttp(String url, JSONObject params, int connecTimeout, int readTimeout, int retryCount) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connecTimeout);
        requestFactory.setReadTimeout(readTimeout);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 设置编码集
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler()); // 异常处理
        url = expandURL(url, params);
        String result = null; // 返回值类型;
        for (int i = 1; i <= retryCount; i++) {
            try {
                result = restTemplate.getForObject(url, String.class, params);
                return result;
            } catch (Exception e) {
                log.error("-----------开始-----------重试count:{} " , i);
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * https 请求 GET
     *
     * @param url           地址
     * @param params        参数
     * @param connecTimeout 连接时间
     * @param readTimeout   读取时间
     * @param retryCount    重试机制
     * @return String 类型
     */
    public static String getHttps(String url, JSONObject params, int connecTimeout, int readTimeout, int retryCount) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connecTimeout);
        requestFactory.setReadTimeout(readTimeout);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 设置编码集
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler()); //error处理
        restTemplate.setRequestFactory(new HttpsClientRequestFactory()); // 绕过https
        url = expandURL(url, params);
        String result = null; // 返回值类型;
        for (int i = 1; i <= retryCount; i++) {
            try {
                result = restTemplate.getForObject(url, String.class, params);
                return result;
            } catch (Exception e) {
                log.error("-----------开始-----------重试count:{} " , i);
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * http 请求 post
     *
     * @param url           地址
     * @param params        参数
     * @param headersMap    header
     * @param connecTimeout 连接时间
     * @param readTimeout   读取时间
     * @param retryCount    重试机制
     * @return String 类型
     */
    public static String postHttp(String url, JSONObject params, Map headersMap, int connecTimeout, int readTimeout, int retryCount) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory(); // 时间函数
        requestFactory.setConnectTimeout(connecTimeout);
        requestFactory.setReadTimeout(readTimeout);
        //内部实际实现为 HttpClient
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 设置编码集
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler()); // 异常处理的headers error 处理
        // 设置·header信息
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAll(headersMap);
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(params, requestHeaders); // josn utf-8 格式
        String result = null; // 返回值类型;
        for (int i = 1; i <= retryCount; i++) {
            try {
                result = restTemplate.postForObject(url, requestEntity, String.class);
                return result;
            } catch (Exception e) {
                log.error("-----------开始-----------重试count:{} " , i);
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * http 普通请求 post
     * @param url           地址
     * @param params         MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
     * @param headersMap    header
     * @param connecTimeout 连接时间
     * @param readTimeout   读取时间
     * @param retryCount    重试机制
     * @return String 类型
     */
    public static String postHttp(String url, MultiValueMap params, Map headersMap, int connecTimeout, int readTimeout, int retryCount) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory(); // 时间函数
        requestFactory.setConnectTimeout(connecTimeout);
        requestFactory.setReadTimeout(readTimeout);
        //内部实际实现为 HttpClient
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 设置编码集
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler()); // 异常处理的headers error 处理
        // 设置·header信息
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAll(headersMap);

        HttpEntity<Map> requestEntity = new HttpEntity<Map>(params, requestHeaders); // josn utf-8 格式
        String result = null; // 返回值类型;
        for (int i = 1; i <= retryCount; i++) {
            try {
                result = restTemplate.postForObject(url, requestEntity, String.class);
                return result;
            } catch (Exception e) {
                log.error("-----------开始-----------重试count:{} " , i);
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * @param url        请求地址
     * @param params     请求 josn 格式参数
     * @param headersMap headers 头部需要参数
     * @param retryCount 重试机制
     * @return 返回string类型返回值
     */
    public static String postHttps(String url, JSONObject params, Map headersMap, int connecTimeout, int readTimeout, int retryCount) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory(); // 时间函数
        requestFactory.setConnectTimeout(connecTimeout);
        requestFactory.setReadTimeout(readTimeout);
        //内部实际实现为 HttpClient
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 设置编码集
        restTemplate.setRequestFactory(new HttpsClientRequestFactory()); // 绕过https
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler()); // 异常处理的headers error 处理
        // 设置·header信息
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAll(headersMap);
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(params, requestHeaders); // josn utf-8 格式
        String result = null; // 返回值类型;
        for (int i = 1; i <= retryCount; i++) {
            try {
                result = restTemplate.postForObject(url, requestEntity, String.class);
                return result;
            } catch (Exception e) {
                log.error("-----------开始-----------重试count:{} " , i);
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 加密参数类型请求  application/x-www-form-urlencoded
     * MultiValueMap<String, Object>
     * 采用 HttpEntity<MultiValueMap<String, Object>> 构造
     * http 请求 post
     *
     * @param url           地址
     * @param  postParameters 普通post请求需要的参数
     * @param headersMap    header
     * @param connecTimeout 连接时间
     * @param readTimeout   读取时间
     * @param retryCount    重试机制
     * @return String 类型
     */
    public static String postHttpEncryption(String url, MultiValueMap<String, Object> postParameters, Map headersMap, int connecTimeout, int readTimeout, int retryCount) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory(); // 时间函数
        requestFactory.setConnectTimeout(connecTimeout);
        requestFactory.setReadTimeout(readTimeout);
        //内部实际实现为 HttpClient
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 设置编码集
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler()); // 异常处理的headers error 处理
        // 设置·header信息
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAll(headersMap);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(postParameters, requestHeaders);
        String result = null; // 返回值类型;
        for (int i = 1; i <= retryCount; i++) {
            try {
                result = restTemplate.postForObject(url, requestEntity, String.class);
                return result;
            } catch (Exception e) {
                log.error("-----------开始-----------重试count:{} " , i);
                e.printStackTrace();
            }
        }
        return result;
    }


    private static String expandURL(String url,JSONObject jsonObject) {
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        Set<String> keys = jsonObject.keySet();
        for (String key : keys) {
            sb.append(key).append("=").append(jsonObject.getString(key)).append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }


    /**
     * 出现异常，可自定义
     */
    private static class DefaultResponseErrorHandler implements ResponseErrorHandler {


        /**
         * 对response进行判断，如果是异常情况，返回true
         */
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().value() != HttpServletResponse.SC_OK;
        }

        /**
         * 异常情况时的处理方法
         */
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()));
            StringBuilder sb = new StringBuilder();
            String str = null;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            try {
                throw new Exception(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

