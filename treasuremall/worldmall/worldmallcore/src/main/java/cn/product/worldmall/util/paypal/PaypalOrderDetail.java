package cn.product.worldmall.util.paypal;

public class PaypalOrderDetail {
    private String productName;
    private String amount;
    private String currency;
    private String returnUrl;
 
    public PaypalOrderDetail(String productName, String amount, String currency, String returnUrl) {
        this.productName = productName;
        this.amount = amount;
        this.currency = currency;
        this.returnUrl = returnUrl;
    }
 
    public String getProductName() {
        return productName;
    }
    
    public String getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public String getReturnUrl(){
    	return returnUrl;
    }
}