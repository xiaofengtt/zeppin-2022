package com.whaty.platform.entity.web.action.information;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class InfoDownloadAction extends MyBaseAction {

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 转向下载页面
	 * @return
	 */
	public String turntoInfoDownload() {
		return "infodown";
	}

}
