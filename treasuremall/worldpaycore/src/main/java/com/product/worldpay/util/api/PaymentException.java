package com.product.worldpay.util.api;

public class PaymentException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 887834714294371549L;
	
	private String message;

    public PaymentException() {
        super();
    }

    public PaymentException(String message) {
        this.message = message;
    }
    
    public PaymentException(Throwable cause) {
        super(cause);
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}