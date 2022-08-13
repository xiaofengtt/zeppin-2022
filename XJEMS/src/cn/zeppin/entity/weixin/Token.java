package cn.zeppin.entity.weixin;

/**
 * <p>
 * Title:AccessToken
 * </p>
 * <p>
 * Description: 获取access_token,access_token是公众号的全局唯一接口调用凭据，
 * 公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。access_token
 * 的存储至少要保留512个字符空间。access_token的有效期目前为2个小时，需定时刷新， 重复获取将导致上次获取的access_token失效。
 * </p>
 * 
 * @author geng
 * @date 2017年8月3日 下午1:58:06
 */
public class Token {
	// 获取到的凭证
	private String accessToken;
	// 凭证有效时间，单位：秒
	private int expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
