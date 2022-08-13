package com.whaty.platform.entity.web.action.fee.feeStandard;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeFeeLevel;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.web.action.studentStatus.stuInfo.PeStudentInfoAction;

public class ListStudentForFeeSetAction extends PeStudentInfoAction {

	private static final long serialVersionUID = -279522129803609828L;
	
	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction(this.getText("listStudentForFeeSet.change"), "/entity/fee/stuFeeStandardSet.action", false, false);
	
		this.getGridConfig().setCapability(false, false, true,true);
		this.getGridConfig().setTitle(this.getText("学生费用标准列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("peStudent.regNo"), "regNo",true, false, true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "trueName",true, false, true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peGrade.name",true, false, true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peSite.name",true, false, true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peEdutype.name",true, false, true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peMajor.name",true, false, true,"");
		this.getGridConfig().addColumn(this.getText("学生状态"), "enumConstByFlagStudentStatus.name",true, false, true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.feeStandard.name"), "peFeeLevel.name");
		this.getGridConfig().addColumn(this.getText("feeStandard.feePercredit"), "peFeeLevel.feePercredit" ,false, false, true,"");
		this.getGridConfig().addColumn(this.getText("feeStandard.oweFeeLimit"), "peFeeLevel.oweFeeLimit",false, false, true,"");
		
	}
	
	@Override
	public void setServletPath() {
		this.servletPath="/entity/fee/listStudentForFeeSet";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo")
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("peFeeLevel", "peFeeLevel")
			.createAlias("enumConstByFlagStudentStatus","enumConstByFlagStudentStatus")//TODO 学生状态
			.add(Restrictions.lt("enumConstByFlagStudentStatus.code","5"))
			;
		return dc;
	}

	public String updateStudentFeeLevelByBatch() {
		ActionContext axt = ActionContext.getContext();
		String str = this.getIds();
		String[] ids = str.split(",");
		java.util.List idList = new java.util.ArrayList();

		for (int i = 0; i < ids.length; i++) {
			idList.add(ids[i]);
		}
		
		if(idList.size()>1) {
			this.setMsg("<font color='red'>一次只能选择一个收费标准！</font>");
			this.setTogo("back");
		} else {
		    str = (String)axt.getSession().get("studentIds");
			ids = str.split(",");
			java.util.List idList2 = new java.util.ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList2.add(ids[i]);			}

			DetachedCriteria dcPeFeeLevel = DetachedCriteria.forClass(PeFeeLevel.class);
			dcPeFeeLevel.add(Restrictions.eq("id", (String)idList.get(0)));
			try{
				PeFeeLevel peFeeLevel = null;
				java.util.List feeList = this.getGeneralService().getList(dcPeFeeLevel);			
				if(feeList!=null&&feeList.size()>0){
					peFeeLevel = (PeFeeLevel)feeList.remove(0);
				}
				int i=this.getGeneralService().updateColumnByIds(idList2, "peFeeLevel", peFeeLevel.getId());
				if(i>0){
					this.setMsg("<font color='red'>成功为"+i+"名学生重新设置了学分标准！</font>");
					this.setTogo("/entity/fee/listStudentForFeeSet.action");
				}else{
					this.setMsg("<font color='red'>为学生设置学分标准失败！</font>");
					this.setTogo("back");
				}
			}catch(Exception e){
				e.printStackTrace();
				this.setMsg("<font color='red'>操作失败，为学生设置学分标准失败！</font>");
				this.setTogo("back");
			}
		}
		return "msg";
	}
	
}
