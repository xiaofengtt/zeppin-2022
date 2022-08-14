package cn.product.worldmall.controller.socket.io;

import io.swagger.annotations.ApiModelProperty;

public class PushMessage {
	
	@ApiModelProperty(value = "登录用户编号")
	private String loginUserNum;

	@ApiModelProperty(value = "推送内容")
	private Object content;

	public String getLoginUserNum() {
		return loginUserNum;
	}

	public void setLoginUserNum(String loginUserNum) {
		this.loginUserNum = loginUserNum;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	
}
