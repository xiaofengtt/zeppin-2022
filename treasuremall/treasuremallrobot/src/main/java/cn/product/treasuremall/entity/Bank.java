/**
 * 
 */
package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

/**
 * @description 【数据对象】银行信息
 */
public class Bank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	@Id
	private String uuid;
	private String name;
	private String shortName;
	private String logo;
	
	private String status;
	private Timestamp createtime;
	
	private String color;
	private String icon;
	private String iconColor;
	
	public class BankStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
	}
	
	public Bank() {
		super();
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getIconColor() {
		return iconColor;
	}
	
	public void setIconColor(String iconColor) {
		this.iconColor = iconColor;
	}
}
