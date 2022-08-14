package cn.zeppin.product.ntb.bean;

import java.util.List;

/**
 * 描述：交易记录
 * 开发人: geng
 * 创建时间: 17/10/13
 */

public class TradingRecordList {
    private List<TradingRecord> dataList;
    private String time;

    public List<TradingRecord> getDataList() {
        return dataList;
    }

    public void setDataList(List<TradingRecord> dataList) {
        this.dataList = dataList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
