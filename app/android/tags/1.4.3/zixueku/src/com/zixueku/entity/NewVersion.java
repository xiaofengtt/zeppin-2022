package com.zixueku.entity;

import java.io.Serializable;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年9月7日 下午5:43:00
 */
public class NewVersion implements Serializable {
	private static final long serialVersionUID = 1L;
	private String downloadPath; // 下载地址
	private Boolean forced;// 是否强制更新
	private String version;// 版本名称

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public Boolean getForced() {
		return forced;
	}

	public void setForced(Boolean forced) {
		this.forced = forced;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "NewVersion [downloadPath=" + downloadPath + ", forced=" + forced + ", version=" + version + "]";
	}
}
