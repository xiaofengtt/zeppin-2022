package com.makati.business.redenvelopes.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code
 * @since 2019-10-24
 */
public class Redenvelopes extends Model<Redenvelopes> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	private String userid;
	private String redelicon;
	private String redelmoney;
	private String redeltype;
    /**
     * 1:使用0使用
     */
	private Integer isuse;
	private Date createtime;
	private Date edittime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRedelicon() {
		return redelicon;
	}

	public void setRedelicon(String redelicon) {
		this.redelicon = redelicon;
	}

	public String getRedelmoney() {
		return redelmoney;
	}

	public void setRedelmoney(String redelmoney) {
		this.redelmoney = redelmoney;
	}

	public String getRedeltype() {
		return redeltype;
	}

	public void setRedeltype(String redeltype) {
		this.redeltype = redeltype;
	}

	public Integer getIsuse() {
		return isuse;
	}

	public void setIsuse(Integer isuse) {
		this.isuse = isuse;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Redenvelopes{" +
			", id=" + id +
			", userId=" + userid +
			", redelicon=" + redelicon +
			", redelmoney=" + redelmoney +
			", redeltype=" + redeltype +
			", isuse=" + isuse +
			", createtime=" + createtime +
			", edittime=" + edittime +
			"}";
	}
}
