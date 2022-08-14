package com.xingyunduobao.www.bean;

import java.io.Serializable;

/**
 * class:封装服务器返回数据
 */

public class ResultData2<T> implements Serializable {
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean success() {
        return "200".equals(code);
    }


    @Override
    public String toString() {
        return "ResultData{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg=" + msg +
                '}';
    }
}
