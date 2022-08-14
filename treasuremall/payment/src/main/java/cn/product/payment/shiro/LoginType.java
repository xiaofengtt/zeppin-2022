package cn.product.payment.shiro;

public enum LoginType {
	SYSTEM("System"),  COMPANY("Company");

    private String type;

    private LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}
