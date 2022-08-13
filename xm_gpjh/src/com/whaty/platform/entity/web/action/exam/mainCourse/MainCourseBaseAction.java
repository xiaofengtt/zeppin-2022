package com.whaty.platform.entity.web.action.exam.mainCourse;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 主干课程考试基类action，主干课程考试模块所用到的公用方法都在此类中
 * @author zqf
 *
 */
public class MainCourseBaseAction extends MyBaseAction {

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
	}
	
	/**
	 * 获取当前活动考试学期的名称，用于标题显示
	 * @return
	 */
	protected String getSemesterName(){
		String name = "";
		List list = new ArrayList();
		try {
			String sql = " select t.name from pe_semester t where t.flag_active = '1' ";
			list = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size() > 0){
			name = list.get(0).toString();
		}
		if(!"".equals(name)){
			name += "-";
		}
		return name;
	}

}
