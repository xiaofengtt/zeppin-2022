package com.cmos.chinamobile.media.action.base;

public class ActionResult<T> extends BaseResult {

    private static final long serialVersionUID = 7880907731807860636L;

    /**
     * 数据
     */
    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ActionResult() {
        super();
    }

    /**
     * 自定义返回的结果
     *
     * @param data
     * @param message
     * @param success
     */
    public ActionResult(T data, String message, boolean success) {
        this.data = data;
        super.setMessage(message);
    }

    /**
     * 成功返回数据和消息
     *
     * @param data
     * @param message
     */
    public ActionResult(T data, String message) {
        this.data = data;
        super.setMessage(message);
    }

    /**
     * 成功返回数据
     *
     * @param data
     */
    public ActionResult(T data) {
        this.data = data;
    }
}