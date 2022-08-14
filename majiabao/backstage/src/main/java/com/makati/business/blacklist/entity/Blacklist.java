package com.makati.business.blacklist.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code
 * @since 2019-12-05
 */
public class Blacklist extends Model<Blacklist> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String ip;
	private String remaker;
	private String createtime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRemaker() {
		return remaker;
	}

	public void setRemaker(String remaker) {
		this.remaker = remaker;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Blacklist{" +
			", id=" + id +
			", ip=" + ip +
			", remaker=" + remaker +
			", createtime=" + createtime +
			"}";
	}
}
