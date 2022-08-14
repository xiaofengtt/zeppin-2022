package cn.zeppin.product.ntb.backadmin.security;

public enum LoginType {
	NTB("Ntb"),  QCB("Qcb");

    private String type;

    private LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}
