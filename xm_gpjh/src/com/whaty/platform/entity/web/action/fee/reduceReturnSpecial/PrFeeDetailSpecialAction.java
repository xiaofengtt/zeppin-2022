package com.whaty.platform.entity.web.action.fee.reduceReturnSpecial;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PrFeeDetailSpecialAction extends MyBaseAction<PrFeeDetail> {

	@SuppressWarnings("unchecked")
	@Override
	public void setGeneralService(GeneralService generalService) {
	}
	
	@SuppressWarnings("unchecked")
	public void setPrFeeDetailSpecialService(GeneralService generalService) {
		super.setGeneralService(generalService);
		this.getGeneralService().getGeneralDao().setEntityClass(entityClass);
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, true,true);
		this.getGridConfig().setTitle(this.getText("人工特殊费用操作明细"));
		if(this.getGridConfig().checkBeforeAddMenu("/entity/fee/prFeeDetailSpecial_addone.action")){
		this.getGridConfig().addMenuScript(this.getText("添加"), "{window.navigate('/entity/fee/prFeeDetailSpecial_addone.action');}");
		}
			
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("peStudent.regNo"),"peStudent.regNo",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peStudent.peGrade.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peStudent.peSite.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peStudent.peEdutype.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peStudent.peMajor.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("学生状态"), "peStudent.enumConstByFlagStudentStatus.name",true,false,true,"");
//		this.getGridConfig().addColumn(this.getText("费用类型"), "enumConstByFlagFeeType.name",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("日期"), "inputDate",true,true,true,"");
		this.getGridConfig().addColumn(this.getText("金额"), "feeAmount",true,true,true,"regex:new RegExp(/"+Const._fee+"/),regexText:'"+Const.feeMessage+"',");
		this.getGridConfig().addColumn(this.getText("备注"), "note",true,true,true,"TextArea",true,500);
	
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		String[] cds = {"2"};
		DetachedCriteria dc = DetachedCriteria.forClass(PrFeeDetail.class);
		dc.createAlias("enumConstByFlagFeeType", "enumConstByFlagFeeType")
			.add(Restrictions.in("enumConstByFlagFeeType.code",cds))
			.createCriteria("peStudent", "peStudent")
			.createAlias("peRecStudent", "peRecStudent",DetachedCriteria.LEFT_JOIN)
			.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			//.add(Restrictions.lt("enumConstByFlagStudentStatus.code", "4"))
			;
		return dc;
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PrFeeDetail.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/prFeeDetailSpecial";
	}

	public void setBean(PrFeeDetail instance) {
		super.superSetBean(instance);
	}
	
	public PrFeeDetail getBean(){
		return super.superGetBean();
	}
	
	public String addone(){
		return "addone";
	}
	
	public String addexe(){
		try {
			this.checkBeforeOperate();
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			return "addone";
		}
		if(super.add().get("success").equals("true")){
			this.setMsg("添加学生特殊费用信息成功！");
		}else{
			this.setMsg("添加学生特殊费用信息失败！");
		}
		return "addone";
	}
	public void checkBeforeOperate() throws EntityException {
		String regNo = this.getBean().getPeStudent().getRegNo();
		String name = this.getBean().getPeStudent().getTrueName();
		if(regNo==null||regNo.trim().length()<=0){
			throw new EntityException("学生的学号不能为空...");
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.add(Restrictions.eq("regNo",regNo.trim()));
		dc.add(Restrictions.eq("trueName",name.trim()));
		dc.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus");//.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		java.util.List list = this.getGeneralService().getList(dc);
		if(list==null||list.size()<=0){
			throw new EntityException("操作失败！没有找到学号为"+regNo.trim()+",姓名为“"+name+"”的学生...");
		}else{
			try{
				if(!((PeStudent)list.remove(0)).getEnumConstByFlagStudentStatus().getCode().equals("4"))
					throw new EntityException("操作失败！学号为"+regNo.trim()+"的学生不是在籍的学生...");
			}catch(Throwable e){
				throw new EntityException("操作失败...");
			}
		}
	}
}
