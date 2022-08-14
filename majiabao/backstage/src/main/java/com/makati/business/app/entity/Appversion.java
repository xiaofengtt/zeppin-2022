package com.makati.business.app.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code
 * @since 2019-09-06
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("马甲包")
public class Appversion  extends Model<Appversion>  implements Serializable {

    private static final long serialVersionUID = 1L;

	private Integer id;
	@ApiModelProperty(name = "appid",value = "APP-ID",notes = "APP-ID")
	private String appid;
	@ApiModelProperty(name = "version",value = "版本",notes = "版本信息")
	private String version;
	@ApiModelProperty("常规开关,1:开、0:关")
	private Integer onoff;
	@ApiModelProperty("强制开关,1:开、0:关")
	private Integer qzonoff;

	private Integer isshow;
	@ApiModelProperty(name = "url",value = "地址",notes = "域名链接")
	private String url;
	@ApiModelProperty(name = "qd",value = "渠道",notes = "渠道名称")
	private String qd;
	private String parentid;
	@ApiModelProperty("自定义字段")
	private String text;

	@ApiModelProperty("主包url")
	private String zburl;
	@ApiModelProperty("马甲包url")
	private String mjurl;
	@ApiModelProperty("状态:1主包、2马甲包、3本地马甲包")
	private Integer state;
	@ApiModelProperty("下载地址")
	private String downurl;



	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getOnoff() {
		return onoff;
	}

	public void setOnoff(Integer onoff) {
		this.onoff = onoff;
	}

	public Integer getQzonoff() {
		return qzonoff;
	}

	public void setQzonoff(Integer qzonoff) {
		this.qzonoff = qzonoff;
	}

	@JsonIgnore
	public Integer getIsshow() {
		return isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getQd() {
		return qd;
	}

	public void setQd(String qd) {
		this.qd = qd;
	}

	@JsonIgnore
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Appversion{" +
				"id=" + id +
				", appid=" + appid +
				", version='" + version + '\'' +
				", onoff=" + onoff +
				", parentid=" + parentid +
				", url1='" + url + '\'' +
				", qd='" + qd + '\'' +
				'}';
	}
}
