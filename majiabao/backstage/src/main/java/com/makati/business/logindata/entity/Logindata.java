package com.makati.business.logindata.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author code
 * @since 2019-10-25
 */
public class Logindata extends Model<Logindata> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer isauditor;

	private transient  String remaker;


	private String channel;
	private String platformtype;
	private String text;
	private String ip;
	private String createtime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return channel;
	}

	public void setUserid(String channel) {
		this.channel = channel;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getIsauditor() {
		return isauditor;
	}

	public void setIsauditor(Integer isauditor) {
		this.isauditor = isauditor;
	}

	public String getRemaker() {
		return remaker;
	}

	public void setRemaker(String remaker) {
		this.remaker = remaker;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Logindata{" +
			", id=" + id +
			", channel=" + channel +
			", platformtype=" + platformtype +
			", text=" + text +
			"}";
	}
}
