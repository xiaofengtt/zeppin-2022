/**
 * 
 */
package com.whaty.platform.entity.user;

import com.whaty.platform.util.RedundanceData;

/**
 * @author chenjian
 * 
 */
public abstract class SiteManager extends EntityUser implements
		com.whaty.platform.Items {

	public SiteManager() {

	}

	private String site_id;

	private String site_name;

	private String group_id;

	private String group_name;
	

	private String mobilePhone;

	private int login_num;

	private String status;

	private RedundanceData redundace = new RedundanceData();

	private HumanPlatformInfo platformInfo = new HumanPlatformInfo();

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public int getLogin_num() {
		return login_num;
	}

	public void setLogin_num(int login_num) {
		this.login_num = login_num;
	}

	public HumanPlatformInfo getPlatformInfo() {
		return platformInfo;
	}

	public void setPlatformInfo(HumanPlatformInfo platformInfo) {
		this.platformInfo = platformInfo;
	}

	public RedundanceData getRedundace() {
		return redundace;
	}

	public void setRedundace(RedundanceData redundace) {
		this.redundace = redundace;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}

}
