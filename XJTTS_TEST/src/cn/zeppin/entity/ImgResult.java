package cn.zeppin.entity;

import java.util.ArrayList;


@SuppressWarnings("rawtypes")
public class ImgResult {

	
	/**
	* 表示图片是否已上传成功。
	*/
	public Boolean success;
	public String userid;
	public String username;
	/**
	* 自定义的附加消息。
	*/
	public String msg;
	/**
	* 表示原始图片的保存地址。
	*/
	public String sourceUrl;
	/**
	* 表示所有头像图片的保存地址，该变量为一个数组。
	*/
	public ArrayList avatarUrls;
	
	
	public ImgResult(Boolean success, String userid, String username,
			String msg, String sourceUrl, ArrayList avatarUrls) {
		super();
		this.success = success;
		this.userid = userid;
		this.username = username;
		this.msg = msg;
		this.sourceUrl = sourceUrl;
		this.avatarUrls = avatarUrls;
	}
	public ImgResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public ArrayList getAvatarUrls() {
		return avatarUrls;
	}
	public void setAvatarUrls(ArrayList avatarUrls) {
		this.avatarUrls = avatarUrls;
	}
	
	
	
}
