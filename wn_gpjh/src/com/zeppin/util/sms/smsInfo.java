package com.zeppin.util.sms;

public class smsInfo
{
	private String uid;// 用户id
	private String pwd;// 用户密码
	private String url;// 短信发送url
	private String content;// 短信内容
	private String mobile;// 手机号码
	private String extend;// 扩展码
	private String sendDate;// 发送时间，0为立即发送

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getExtend()
	{
		return extend;
	}

	public void setExtend(String extend)
	{
		this.extend = extend;
	}

	public String getSendDate()
	{
		return sendDate;
	}

	public void setSendDate(String sendDate)
	{
		this.sendDate = sendDate;
	}

	

}
