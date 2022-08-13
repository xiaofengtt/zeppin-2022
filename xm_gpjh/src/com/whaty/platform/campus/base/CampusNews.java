/*
 * news.java
 *
 * Created on 2004年9月30日, 下午6:20
 */

package com.whaty.platform.campus.base;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.user.Docent;

/**
 * 新闻对象
 * 
 * @author 陈健
 */
public abstract class CampusNews implements com.whaty.platform.Items {

	private String id;

	private String title;

	private String shortTitle;

	private String reporter;

	private String reportDate;

	private String body;

	private Date submitDate;

	private String submitManagerId;
	
	private String submitManagerName;


	private CampusNewsStatus status;

	private String propertyString;

	private String confirm;

	private String picLink;

	private String confirmManagerId;

	private String confirmManagerName;

	private int readCount;
	
	private Docent decentInfo;
	
	private String docent_Key;
	
	private String course_Link;

	public String getConfirmManagerId() {
		if (confirmManagerId == null || confirmManagerId.equals("null"))
			return "";
		return confirmManagerId;
	}

	public void setConfirmManagerId(String confirmManagerId) {
		this.confirmManagerId = confirmManagerId;
	}

	public String getConfirmManagerName() {
		if (confirmManagerName == null || confirmManagerName.equals("null"))
			return "";
		return confirmManagerName;
	}

	public void setConfirmManagerName(String confirmManagerName) {
		this.confirmManagerName = confirmManagerName;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getPicLink() {
		return picLink;
	}

	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getSubmitManagerId() {
		return submitManagerId;
	}

	public void setSubmitManagerId(String submitManagerId) {
		this.submitManagerId = submitManagerId;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public CampusNewsStatus getStatus() {
		return status;
	}

	public void setStatus(CampusNewsStatus status) {
		this.status = status;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPropertyString() {
		return propertyString;
	}

	public void setPropertyString(String propertyString) {
		this.propertyString = propertyString;
	}

	/**
	 * 新闻状态锁定
	 * 
	 * @return 1代表成功，0代表失败
	 */
	public abstract void doLock() throws PlatformException;

	/**
	 * 新闻状态解锁
	 * 
	 * @return 1代表成功，0代表失败
	 */
	public abstract void undoLock() throws PlatformException;

	/**
	 * 新闻至顶,sequence为置顶的顺序
	 * 
	 * @return 1代表成功，0代表失败
	 */
	public abstract void putTop(int sequence) throws PlatformException;

	public void putTop() throws PlatformException {
		putTop(1);
	}

	public abstract int addReadCount() throws PlatformException;

	/**
	 * 新闻取消至顶
	 * 
	 * @return 1代表成功，0代表失败
	 */
	public abstract void cancelTop() throws PlatformException;

	public abstract void setPop() throws PlatformException;

	public abstract void cancelPop() throws PlatformException;

	public abstract void confirmNews(String confirm, String confirm_manager_id,
			String confirm_manager_name) throws PlatformException;


	public String getShortTitleByLenthLimit(int limitLenth) {
		return parse(this.getShortTitle(), limitLenth);
	}

	public String getShortTitleByLenthLimit(int limitLenth, String suffix) {
		return parse(this.getShortTitle(), limitLenth, suffix);
	}

	public String getTitleByLenthLimit(int limitLenth) {
		return parse(this.getTitle(), limitLenth);
	}

	public String getTitleByLenthLimit(int limitLenth, String suffix) {
		return parse(this.getTitle(), limitLenth, suffix);
	}

	private String parse(String s, int limitLenth) {
		if (s == null || s.length() < 1)
			return "";
		String col = "", tit = "";
		String regex = "<font[\\s]+color[\\s]*=[\\s]*([\\w\\W]*)[\\s]*>([\\s\\S]+)</font>";
		Matcher matcher = Pattern.compile(regex).matcher(s);
		if (matcher.find()) {
			col = matcher.group(1);
			tit = matcher.group(2);
		} else {
			col = "";
			tit = s;
		}
		if (tit == null || tit.length() < 1)
			return "";
		else {
			int len = Math.min(limitLenth, tit.length());
			if (col != null && col.length() > 0) {
				return "<font color=" + col + ">" + limit(tit, len) + "</font>";
			} else
				return limit(title, len);
		}
	}

	private String parse(String s, int limitLenth, String suffix) {
		if (s == null || s.length() < 1)
			return "";
		String col = "", tit = "";
		String regex = "<font[\\s]+color[\\s]*=[\\s]*([\\w\\W]*)[\\s]*>([\\s\\S]+)</font>";
		Matcher matcher = Pattern.compile(regex).matcher(s);
		if (matcher.find()) {
			col = matcher.group(1);
			tit = matcher.group(2);
		} else {
			col = "";
			tit = s;
		}
		if (tit == null || tit.length() < 1)
			return "";
		else {
			int len = Math.min(limitLenth, tit.length());
			String temp = limit(tit, len);
			if (temp != null && temp.length() >= tit.length())
				suffix = "";
			if (col != null && col.length() > 0) {
				return "<font color=" + col + ">" + temp + suffix + "</font>";
			} else
				return limit(tit, len) + suffix;
		}
	}

	private String limit(String str, int len) {
		if (str == null) {
			return "";
		}
		str = str.trim();
		StringBuffer r = new StringBuffer();
		int l = str.length();
		float count = 0;
		for (int i = 0; i < l; ++i) {
			char c = str.charAt(i);
			if (c > 255 || c < 0) {
				++count;
				r.append(c);
			} else {
				count += 0.5;
				r.append(c);
			}
			if (count >= len) {
				if (i < l - 1) {
					r.append("");
				}
				break;
			}
		}
		return r.toString();
	}

	public String getSubmitManagerName() {
		if (submitManagerName == null || submitManagerName.equals("null"))
			return "";		
		return submitManagerName;
	}

	public void setSubmitManagerName(String submitManagerName) {
		this.submitManagerName = submitManagerName;
	}

	public Docent getDecentInfo() {
		return decentInfo;
	}

	public void setDecentInfo(Docent decentInfo) {
		this.decentInfo = decentInfo;
	}

	public String getDocent_Key() {
		return docent_Key;
	}

	public void setDocent_Key(String docent_Key) {
		this.docent_Key = docent_Key;
	}

	public String getCourse_Link() {
		return course_Link;
	}

	public void setCourse_Link(String course_Link) {
		this.course_Link = course_Link;
	}

}
