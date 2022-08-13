package com.zixueku.entity;

import java.io.Serializable;

/**
 * 类说明 更新版本信息
 * "Records":{
		"hasNew":true,
		"newVersion":{
			"downloadPath":"",
			"forced":false,
			"version":"1.3.0"
		},
		"status":1
	},
	"Status":"success"
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年9月7日 下午5:41:21
 */
public class VersionInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean hasNew;
	private int status;
	private NewVersion newVersion;

	public boolean isHasNew() {
		return hasNew;
	}

	public void setHasNew(boolean hasNew) {
		this.hasNew = hasNew;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public NewVersion getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(NewVersion newVersion) {
		this.newVersion = newVersion;
	}

	@Override
	public String toString() {
		return "VersionInfo [hasNew=" + hasNew + ", status=" + status + ", newVersion=" + newVersion + "]";
	}
}
