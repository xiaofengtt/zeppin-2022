package com.listener;

import org.springframework.context.ApplicationEvent;

public class AppEvent extends ApplicationEvent{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String msg ;

    public AppEvent(Object source,String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}