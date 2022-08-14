package com.product.worldpay.util.paypal;

public class PaypalAccount {
    private String clientId;
    private String secret;
    private String transferUrl;
 
    public PaypalAccount(String clientId, String secret, String transferUrl) {
        this.clientId = clientId;
        this.secret = secret;
        this.transferUrl = transferUrl;
    }

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getTransferUrl() {
		return transferUrl;
	}

	public void setTransferUrl(String transferUrl) {
		this.transferUrl = transferUrl;
	}
	
}