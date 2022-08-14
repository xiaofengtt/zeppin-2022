package com.product.worldpay.util.paypal;

public class PaypalOrderDetail {
    private String productName;
    private float amount;
    private String currency;
    private String returnUrl;
 
    public PaypalOrderDetail(String productName, String amount, String currency, String returnUrl) {
        this.productName = productName;
        this.amount = Float.parseFloat(amount);
        this.currency = currency;
        this.returnUrl = returnUrl;
    }
 
    public String getProductName() {
        return productName;
    }
    
    public String getAmount() {
        return String.format("%.2f", amount);
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public String getReturnUrl(){
    	return returnUrl;
    }
}