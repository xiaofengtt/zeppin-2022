package cn.zeppin.product.ntb.bean;

/**
 * 描述： 获取用户账户信息
 * 开发人: geng
 * 创建时间: 17/10/13
 */

public class UserFinance {

    /**
     * uuid : d84216b7-b856-4223-9a5b-7fb3dee80743
     * accountBalance : 950,000.71
     * totalAmount : 1,999,999.91
     * totalInvest : 1,049,999.20
     * totalReturnBuyDay : 0.00
     * totalReturnBuyMonth : 0.00
     * totalReturnBuyYear : 0.00
     */

    private String uuid;
    private String accountBalance;
    private String totalAmount;//总资产
    private String totalInvest;
    private String totalReturnBuyDay;
    private String totalReturnBuyMonth;
    private String totalReturnBuyYear;
    private String totalReturn;//总收益

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalInvest() {
        return totalInvest;
    }

    public void setTotalInvest(String totalInvest) {
        this.totalInvest = totalInvest;
    }

    public String getTotalReturnBuyDay() {
        return totalReturnBuyDay;
    }

    public void setTotalReturnBuyDay(String totalReturnBuyDay) {
        this.totalReturnBuyDay = totalReturnBuyDay;
    }

    public String getTotalReturnBuyMonth() {
        return totalReturnBuyMonth;
    }

    public void setTotalReturnBuyMonth(String totalReturnBuyMonth) {
        this.totalReturnBuyMonth = totalReturnBuyMonth;
    }

    public String getTotalReturnBuyYear() {
        return totalReturnBuyYear;
    }

    public void setTotalReturnBuyYear(String totalReturnBuyYear) {
        this.totalReturnBuyYear = totalReturnBuyYear;
    }

    public String getTotalReturn() {
        return totalReturn;
    }

    public void setTotalReturn(String totalReturn) {
        this.totalReturn = totalReturn;
    }
}
