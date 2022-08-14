package cn.product.score.shiro;

public enum LoginType {
	BACK("Back"),  FRONT("Front");

    private String type;

    private LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}
