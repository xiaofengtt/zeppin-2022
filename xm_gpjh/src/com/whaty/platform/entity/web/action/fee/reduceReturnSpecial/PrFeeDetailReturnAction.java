package com.whaty.platform.entity.web.action.fee.reduceReturnSpecial;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.studentStatus.stuInfo.PeStudentInfoAction;

public class PrFeeDetailReturnAction extends PeStudentInfoAction {
	/**
	 * 转向条件设置页面
	 * 
	 * @return
	 */
	public String batch() {
		return "batch";
	}
	
	public String feeReturn() {
		
		/*EnumConst enumConst1=new EnumConst();
		EnumConst enumConst2=new EnumConst();
		enumConst1.setId(super.getMyListService().getIdByName("EnumConstByFlagFeeCheck", "自动审核"));
		enumConst2.setId(super.getMyListService().getIdByName("EnumConstByFlagFeeType", "退费"));
		
		
		PeStudent peStudent = this.getPeStudentService().getById(this.getStudentId());
		
		if(peStudent.getFeeBalance()>=0) {
			PrFeeDetail prFeeDetail = new PrFeeDetail();
			prFeeDetail.setPeStudent(peStudent);
			prFeeDetail.setCreditAmount(Double.valueOf(0));
			prFeeDetail.setFeeAmount(-peStudent.getFeeBalance());
			prFeeDetail.setEnumConstByFlagFeeType(enumConst2);
			prFeeDetail.setEnumConstByFlagFeeCheck(enumConst1);
			prFeeDetail.setInputDate(new Date());
			
			PrFeeDetail instance1 = null;
			PeStudent instance2= null;
			
			try {
				instance1 = this.getPrFeeDetailService().save(prFeeDetail);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			
			peStudent.setFeeBalance(Double.valueOf(0));
			
			try {
				instance2 = this.getPeStudentService().save(peStudent);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			
			if(instance1 != null && instance2 != null) {
				this.setOperationInfo("退费成功！");
				try {
					this.getLogManage().insertLog("为学生”"+instance2.getName()+"“退费成功");
				} catch (PlatformException e) {
					e.printStackTrace();
				}
				return "feeReturn";
			} else {
				this.setOperationInfo("退费失败！");
				try {
					this.getLogManage().insertLog("退费失败");
				} catch (PlatformException e) {
					e.printStackTrace();
				}
				return "feeReturn";
			}			
		}else {
			this.setOperationInfo("退费失败！该生费用为负,不能退费！");
			*/
			return "";		
		}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("学生退费"));
		
		this.getGridConfig().addMenuFunction("退费", "/entity/fee/feeRefund_turntorefund.action", false, false);
		
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
//		this.getGridConfig().addColumn(this.getText("学生状态"), "enumConstByFlagStudentStatus.name");
		ColumnConfig column = new ColumnConfig(this.getText("学生状态"),"enumConstByFlagStudentStatus.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagStudentStatus' and code>'4'");
		this.getGridConfig().addColumn(column);
		this.getGridConfig().addColumn(this.getText("帐户余额"), "feeBalance");
		this.getGridConfig().addColumn(this.getText(""), "feeInactive",false,false,false,"");
		this.getGridConfig().addRenderScript(this.getText("可退余额"), "{var feeInactive=record.data['feeInactive']; var feeBalance=record.data['feeBalance']; if(feeInactive<=0) return feeBalance;else if(feeBalance>0) return (feeBalance-feeInactive);else return feeBalance;}", "");
		
	}


	@Override
	public void setServletPath() {
		this.servletPath="/entity/fee/prFeeDetailReturn";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo")
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.or(Restrictions.ge("enumConstByFlagStudentStatus.code", "5"), Restrictions.ge("enumConstByFlagStudentStatus.code", "7")));
		return dc;
	}
	
}
