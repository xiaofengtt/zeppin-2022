package com.whaty.platform.entity.web.action.fee.pay;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

public class PrFeeDetailPiciAction extends MyBaseAction<PrFeeDetail> {

	private static final long serialVersionUID = 8697572567335356179L;
	private String onlyRead;
	private String peFeeBatch_id;
	
	public String getPeFeeBatch_id() {
		return peFeeBatch_id;
	}

	public void setPeFeeBatch_id(String peFeeBatch_id) {
		this.peFeeBatch_id = peFeeBatch_id;
	}

	@Override
	public void initGrid() {
		if(this.getOnlyRead()!=null&&this.getOnlyRead().equals("false")){
			this.getGridConfig().addMenuFunction("添加到批次里面", "peFeeBatch",
					this.getPeFeeBatch_id());
			this.getGridConfig().addMenuFunction("从批次中移除", "peFeeBatch",
					"del_"+this.getPeFeeBatch_id());
		}
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("交费批次明细"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("批次号"),"peFeeBatch.name");
		this.getGridConfig().addColumn(this.getText("peStudent.regNo"),"peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peStudent.peMajor.name");
//		this.getGridConfig().addColumn(this.getText("prFeeDetail.enumConstByFlagFeeType.name"), "enumConstByFlagFeeType.name");
		ColumnConfig column = new ColumnConfig(this.getText("prFeeDetail.enumConstByFlagFeeType.name"),"enumConstByFlagFeeType.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagFeeType' and code in('0','1')");
		this.getGridConfig().addColumn(column);
		//this.getGridConfig().addColumn(this.getText("费用状态"), "enumConstByFlagFeeCheck.name");
		this.getGridConfig().addColumn(this.getText("交费日期"), "inputDate");
		this.getGridConfig().addColumn(this.getText("交费金额"), "feeAmount");
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrFeeDetail.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/prFeeDetailPici";
	}

	public String getOnlyRead() {
		return onlyRead;
	}

	public void setOnlyRead(String onlyRead) {
		this.onlyRead = onlyRead;
	}
	
	public void setBean(PrFeeDetail instance) {
		super.superSetBean(instance);
	}
	
	public PrFeeDetail getBean(){
		return super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		String[] cds = {"0","1"};
		DetachedCriteria dc = DetachedCriteria.forClass(PrFeeDetail.class);
		dc.createAlias("peFeeBatch", "peFeeBatch",DetachedCriteria.LEFT_JOIN)
			.add(Restrictions.in("enumConstByFlagFeeType.code",cds))//交费信息
			.createAlias("enumConstByFlagFeeType", "enumConstByFlagFeeType")
			//.add(Restrictions.and(Restrictions.eq("enumConstByFlagFeeType.namespace", "FlagFeeType"), Restrictions.eq("enumConstByFlagFeeType.code", "0")))
			.createAlias("enumConstByFlagFeeCheck", "enumConstByFlagFeeCheck")
			.createCriteria("peStudent", "peStudent")
			.createAlias("peRecStudent", "peRecStudent",DetachedCriteria.LEFT_JOIN)
			.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			;
		if(this.getOnlyRead()!=null&&this.getOnlyRead().equals("false")){
			dc.add(Restrictions.or(Restrictions.isNull("peFeeBatch.id"), Restrictions.eq("peFeeBatch.id", this.getPeFeeBatch_id())))//批次信息			
			  .add(Restrictions.eq("enumConstByFlagFeeCheck.code", "0"));
		}else{
			dc.add(Restrictions.eq("peFeeBatch.id", this.getPeFeeBatch_id()));//批次信息
		}
		return dc;
	}

	@Override
	public String abstractUpdateColumn() {
		String temp = this.getValue();
		if(temp!=null&&temp.startsWith("del_")){
			temp = temp.substring(4);
			this.setValue("");
		}
		java.util.Map map = updateColumn();
		if (map == null) {
			map = new HashMap();
			map.put("success", "false");
			map.put("info",this.getText(("UpdateColumn method is not implemented in Action")));
		}
		try {
			this.getGeneralService().executeBySQL("update pe_fee_batch fb set fb.fee_amount_total=(select nvl(sum(fd.fee_amount),0) from pr_fee_detail fd where fd.fk_fee_batch_id=fb.id) ," +
					" fb.fee_record_num = (select nvl(count(pfd.id),0)   from pr_fee_detail pfd   where pfd.fk_fee_batch_id = fb.id) where id='"+temp+"'");
		} catch (EntityException e) {			
		}


		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
}
