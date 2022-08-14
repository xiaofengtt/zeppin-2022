package cn.zeppin.product.ntb.bean;

/**
 * 描述：可用的支付方式
 * 开发人: geng
 * 创建时间: 17/11/22
 */

public class PaymentList {
    private String uuid;
    private String payment;
    private boolean flagSwitch;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public boolean isFlagSwitch() {
        return flagSwitch;
    }

    public void setFlagSwitch(boolean flagSwitch) {
        this.flagSwitch = flagSwitch;
    }
}
