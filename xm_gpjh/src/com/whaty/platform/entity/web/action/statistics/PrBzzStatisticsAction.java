package com.whaty.platform.entity.web.action.statistics;

import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * @param
 * @version 创建时间：2009-7-2 下午08:40:28
 * @return
 * @throws PlatformException
 * 类说明
 */
/**
 * @author gy
 *
 */
public class PrBzzStatisticsAction extends MyBaseAction {

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
		this.servletPath = "/entity/statistics/prBzzStatistics";
	}
	
	/**
	 * 转向学生统计页面
	 * @return
	 */
	public String turntoStu() {
		return "stu";
	}
	
	/**
	 * 转向资源统计页面
	 * @return
	 */
	public String turntoRes(){
		return "res";
	}
	
	/**
	 * 转向选课统计页面
	 * @return
	 */
	public String turntoEle(){
		return "ele";
	}
	
	/**
	 * 转向学习进度统计页面
	 * @return
	 */
	public String turntoPro(){
		return "pro";
	}

}
