package com.fengkuangchoujiang.www.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by geng on 17/9/7.
 * class:封装服务器返回数据
 */

public class ResultData<T> implements Serializable {
    private String message;
    private String status;
    private List<T> data;
    private int pageNum;
    private int pageSize;
    private int totalPageCount;
    private int totalResultCount;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(int totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    public boolean success() {
        return "SUCCESS".equals(status);
    }


    @Override
    public String toString() {
        return "ResultData{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalPageCount=" + totalPageCount +
                ", totalResultCount=" + totalResultCount +
                '}';
    }
}
