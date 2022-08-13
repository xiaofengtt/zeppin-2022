package com.whaty.platform.entity.web.action.exam.finalExam;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.SystemApplayAction;
/**
 * 期末考试成绩复查管理
 * @author zqf
 *
 */
public class ExamScoreApplayManageAction extends SystemApplayAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("期末考试成绩复查管理"));
		this.getGridConfig().setTitle(this.getText("申请表"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("学生姓名"),"peStudent.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("申请类型"),"enumConstByApplyType.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("申请状态"),"enumConstByFlagApplyStatus.name");
		this.getGridConfig().addColumn(this.getText("申请时间"),"applyDate",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("审核时间"),"checkDate");
		this.getGridConfig().addColumn(this.getText("申请内容"),"applyNote",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("审核备注"),"checkNote");
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/examScoreApplayManage";
	}

	public void checkBeforeUpdate() throws EntityException{
		if(this.getBean().getEnumConstByApplyType().getCode() != null
				&& !"3".equals(this.getBean().getEnumConstByApplyType().getCode())){
			throw new EntityException("只能操作期末考试成绩复查的申请信息");
		}
	}
	
}
