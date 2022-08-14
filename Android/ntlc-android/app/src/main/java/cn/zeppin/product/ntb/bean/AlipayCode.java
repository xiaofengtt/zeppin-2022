package cn.zeppin.product.ntb.bean;

/**
 * 描述：支付宝固定收款码
 * 开发人: geng
 * 创建时间: 17/11/22
 */

public class AlipayCode {
    private String uuid;
    private int priceIndex;
    private String priceCN;
    private String priceUrlCode;
    private String priceUrlcodeCN;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getPriceIndex() {
        return priceIndex;
    }

    public void setPriceIndex(int priceIndex) {
        this.priceIndex = priceIndex;
    }

    public String getPriceCN() {
        return priceCN;
    }

    public void setPriceCN(String priceCN) {
        this.priceCN = priceCN;
    }

    public String getPriceUrlCode() {
        return priceUrlCode;
    }

    public void setPriceUrlCode(String priceUrlCode) {
        this.priceUrlCode = priceUrlCode;
    }

    public String getPriceUrlcodeCN() {
        return priceUrlcodeCN;
    }

    public void setPriceUrlcodeCN(String priceUrlcodeCN) {
        this.priceUrlcodeCN = priceUrlcodeCN;
    }
}
