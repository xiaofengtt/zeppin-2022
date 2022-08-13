package com.whaty.platform.entity.web.action.fee.reduceReturnSpecial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.fee.FeeRefundService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

public class FeeRefundAction extends MyBaseAction<PrFeeDetail> {

	private static final long serialVersionUID = 2718618766059781918L;
	
	private FeeRefundService feeRefundService;	
	
	public FeeRefundService getFeeRefundService() {
		return feeRefundService;
	}

	public void setFeeRefundService(FeeRefundService feeRefundService) {
		this.feeRefundService = feeRefundService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true,true);
		this.getGridConfig().setTitle(this.getText("退费明细"));
		
		this.getGridConfig().addMenuFunction(this.getText("取消退费"), "/entity/fee/feeRefund_cancelRefund.action",false,true);
		
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("peStudent.regNo"),"peStudent.regNo",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peStudent.peGrade.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peStudent.peSite.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peStudent.peEdutype.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peStudent.peMajor.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("退费日期"), "inputDate",true,true,true,"");
		this.getGridConfig().addColumn(this.getText("退费金额"), "feeAmount",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("备注"), "note",true,true,true,"TextArea",true,500);
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrFeeDetail.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/feeRefund";
	}
	
	public void setBean(PrFeeDetail instance) {
		super.superSetBean(instance);
	}
	
	public PrFeeDetail getBean(){
		return super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrFeeDetail.class);
		dc.createAlias("enumConstByFlagFeeType", "enumConstByFlagFeeType")
			.add(Restrictions.eq("enumConstByFlagFeeType.code","6"))//交费信息
			.createCriteria("peStudent", "peStudent")
			.createAlias("peRecStudent", "peRecStudent",DetachedCriteria.LEFT_JOIN)
			.createAlias("prStudentInfo", "prStudentInfo")
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			;
		return dc;
	}
	
	public String turntorefund(){
		return "turntorefund";
	}
	public String refundexe(){
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				try{
					int count = this.getFeeRefundService().saveForRefund(idList, this.getBean());
					this.setMsg(count+"名学生退费成功...");					
				}catch(EntityException e){
					e.printStackTrace();
					this.setMsg(e.getMessage());					
				}
			}else{
				this.setMsg("没有学生信息，退费失败...");
			}
		}else{
			this.setMsg("请选择要退费的学生...");
		}
		return "turntorefund";
	}
	public String cancelRefund(){
		Map<String, String> map = new HashMap<String, String>();
		int count = 0 ;
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}				
				try{
					count = this.getFeeRefundService().delRefund(idList);
					map.put("success", "true");
					map.put("info", count+"个学生的退费取消成功!");
				}catch(Exception e){
					e.printStackTrace();
					map.put("success", "false");
					map.put("info", e.getMessage());
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项...");
			}
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
}
