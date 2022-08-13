package com.whaty.platform.entity.web.action.teaching.process;

import java.util.Map;

import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 课程表导入列出查询等
 * @author Administrator
 *
 */
public class PeSyllabusManagerAction extends MyBaseAction {

	private static final long serialVersionUID = 2722670144452552814L;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public void initGrid() {
		//（学期，专业、序号、科目、教师、联系电话、次数、直播、日期（最大12次）、时间、校本部上课地点、备注）
		
		this.getGridConfig().setTitle(this.getText("课程表信息"));
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("序号"), "name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("学期"), "sem_name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("专业"), "major", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("科目"), "course", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("教师"), "teacher", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("联系电话"), "phone", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("直播"), "type", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("日期"), "type", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("时间"), "type", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("上课地点"), "address", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("备注"), "note", false, true, true, "TextField", false, 50);
		
	}

	public String batch(){
		return "batch";
	}
	public String batchexe(){
		//TODO 批量上传课程信息

		msg = "课程信息导入成功！";
		return "batch";
	}


	@Override
	public void setEntityClass() {
		
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/syllabus";
		
	}
}
