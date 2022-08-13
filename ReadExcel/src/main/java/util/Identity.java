package util;
/**
 * 存放能唯一标识用户信息的数据结构
 * 其中任意一个属性即可唯一识别该用户
 * @author Huangweidong
 *
 */
public class Identity {
	private String idcard;
	private String email;
	private String mobile;

	public Identity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Identity(String idCard, String email, String mobile) {
		super();
		this.idcard = idCard;
		this.email = email;
		this.mobile = mobile;
	}

	public String getIdCard() {
		return idcard;
	}

	public void setIdCard(String idCard) {
		this.idcard = idCard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "Identity [idCard=" + idcard + ", email=" + email + ", mobile=" + mobile + "]";
	}

}
