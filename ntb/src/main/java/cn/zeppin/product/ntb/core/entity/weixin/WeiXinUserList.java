package cn.zeppin.product.ntb.core.entity.weixin;

public class WeiXinUserList {
	// 总关注用户数
	private int total;
	// 获取的OpenId个数
	private int count;
	// OpenId列表
	private WeiXinUserData data;
	// 最后一个用户的openid
	private String next_openid;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public WeiXinUserData getData() {
		return data;
	}

	public void setData(WeiXinUserData data) {
		this.data = data;
	}

	public String getNext_openid() {
		return next_openid;
	}

	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}

}
