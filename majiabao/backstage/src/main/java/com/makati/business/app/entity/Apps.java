package com.makati.business.app.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code
 * @since 2019-09-13
 */
public class Apps extends Model<Apps> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	private String appname;
	private String info;
	private Integer isshow;
	private Integer onoff;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getIsshow() {
		return isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}

	public Integer getOnoff() {
		return onoff;
	}

	public void setOnoff(Integer onoff) {
		this.onoff = onoff;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Apps{" +
			", id=" + id +
			", appname=" + appname +
			", info=" + info +
			", isshow=" + isshow +
			", onoff=" + onoff +
			"}";
	}
}
