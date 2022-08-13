package com.whaty.platform.entity.web.action.fee.pay;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.fee.PrFeeDetailService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PrFeeDetailInAction extends MyBaseAction<PrFeeDetail> {

	private static final long serialVersionUID = -4235814681895978255L;
	private String studentNo;
	private PrFeeDetailService prFeeDetailService;
	
	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setGeneralService(GeneralService generalService) {
//		this.generalService = generalService;
//		this.generalService.getGeneralDao().setEntityClass(entityClass);
	}
	
	@SuppressWarnings("unchecked")
	public void setFeeDetailService(GeneralService generalService) {
		super.setGeneralService(generalService);
		this.getGeneralService().getGeneralDao().setEntityClass(entityClass);
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, true,true);
		this.getGridConfig().setTitle(this.getText("学生交费明细"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("入学准考证号"),"peStudent.peRecStudent.examCardNum",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.regNo"),"peStudent.regNo",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peStudent.peGrade.name",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peStudent.peSite.name",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peStudent.peEdutype.name",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peStudent.peMajor.name",true, false, true, "");
		ColumnConfig column = new ColumnConfig(this.getText("prFeeDetail.enumConstByFlagFeeType.name"),"enumConstByFlagFeeType.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagFeeType' and code in('0','1')");
		column.setAdd(false);
		this.getGridConfig().addColumn(column);
		//this.getGridConfig().addColumn(this.getText("prFeeDetail.enumConstByFlagFeeType.name"), "enumConstByFlagFeeType.name",true, false, true, "");
		ColumnConfig column2 = new ColumnConfig(this.getText("费用状态"),"enumConstByFlagFeeCheck.name");
		column2.setComboSQL("select id,name from enum_const where namespace='FlagFeeCheck' and code in('0','1','2')");
		column2.setAdd(false);
		this.getGridConfig().addColumn(column2);
		//this.getGridConfig().addColumn(this.getText("费用状态"), "enumConstByFlagFeeCheck.name",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("交费日期"), "inputDate",true, true, true, "");
		this.getGridConfig().addColumn(this.getText("交费金额"), "feeAmount",true, true, true, Const.fee_for_extjs);
		//this.getGridConfig().addColumn(this.getText("当时余额"), "creditAmount",false, false, true, Const.fee_for_extjs);
		//this.getGridConfig().addColumn(this.getText("现在余额"), "peStudent.feeBalance",false, false, true, Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("备注"),"note",false, true, true, "TextArea",true,500);
	}


	@Override
	public void setEntityClass() {
		this.entityClass = PrFeeDetail.class;
	}


	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/prFeeDetailIn";
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
		dc.createAlias("enumConstByFlagFeeType", "enumConstByFlagFeeType")
			.add(Restrictions.in("enumConstByFlagFeeType.code",cds))
			.createAlias("enumConstByFlagFeeCheck", "enumConstByFlagFeeCheck")
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
	
	public String batch() {
		return "batch";
	}
	
	private EnumConst getFeeType(){
		EnumConst feeType = this.getBean().getEnumConstByFlagFeeType();
		try{
			feeType = (EnumConst)this.getMyListService().getById(EnumConst.class, this.getBean().getEnumConstByFlagFeeType().getId());
		}catch(Throwable e){
			e.printStackTrace();
		}
		return feeType;
	}
	
	public String batchexe() {		
		try {
			int i = this.getPrFeeDetailService().save_upload(this.get_upload(), getFeeType());
			this.setMsg("共"+i+"条数据导入成功!");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		this.setTogo("back");
		return "msg";
	}
	
	public String addOne() {
		return "addone";
	}
	@SuppressWarnings("unchecked")
	public String addexe() {
		java.util.List list = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		this.getBean().setEnumConstByFlagFeeType(getFeeType());
		this.getBean().setEnumConstByFlagFeeCheck(this.getMyListService().getEnumConstByNamespaceCode("FlagFeeCheck", "0"));
		String code= this.getBean().getEnumConstByFlagFeeType().getCode();
		if(code.equals("0")){
			dc.createAlias("peRecStudent", "peRecStudent")
			.add(Restrictions.eq("peRecStudent.examCardNum", this.getStudentNo()));	
			this.setMsg("准考证号");
		}else{
			dc.add(Restrictions.eq("regNo", studentNo));
			this.setMsg("学号");
		}

		try {
			list = this.getGeneralService().getList(dc);
			if(list==null||list.size()<=0){
				this.setMsg("添加学生交费失败，没有"+this.getMsg()+"为："+this.getStudentNo()+"的学生！");
			}else { 
				// 加一个判断预交费只能录入一次(龚妮娜 20090413)
				if (code.equals("0")) {
					java.util.List yjflist = null;
					DetachedCriteria dcPrFeeDetail = DetachedCriteria.forClass(PrFeeDetail.class);
					dcPrFeeDetail.add(Restrictions.eq("peStudent", list.get(0)));

					dcPrFeeDetail.createAlias("enumConstByFlagFeeType","enumConstByFlagFeeType").add(Restrictions.eq("enumConstByFlagFeeType.code", "0"));
					yjflist = this.getGeneralService().getList(dcPrFeeDetail);

					if (yjflist != null && yjflist.size() > 0){
						this.setMsg("添加学生交费失败，预交费只能交一次！");
					return "addone";
					}
				}
			
				//this.getBean().setCreditAmount(((PeStudent)list.get(0)).getFeeBalance()+this.getBean().getFeeAmount());
				this.getBean().setPeStudent((PeStudent)list.remove(0));
//				if(super.add().get("success").equals("true")){
//					this.setMsg("添加学生交费信息成功！");
//				}else{
//					this.setMsg("添加学生交费信息失败！");
//				}				
				this.superSetBean((PrFeeDetail)setSubIds(this.getBean()));
				try {
					this.getGeneralService().save(this.getBean());
					this.setMsg("添加学生交费信息成功！");
				} catch (EntityException e) {
					this.setMsg(e.getMessage());
				} catch (Exception e1) {
					this.setMsg("添加学生交费信息失败！");
				}
			}
		} catch (EntityException e) {
			this.setMsg("添加学生交费信息失败！"+ e.getMessage());
		}
		return "addone";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void checkBeforeUpdate() throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(PrFeeDetail.class);
		dc.add(Restrictions.eq("id", this.getBean().getId()))
			.createAlias("enumConstByFlagFeeCheck", "enumConstByFlagFeeCheck").add(Restrictions.eq("enumConstByFlagFeeCheck.code", "0"));
		java.util.List list = this.getGeneralService().getList(dc);
		if(list==null||list.size()<=0){
			throw new EntityException("修改失败！该费用不是未上报状态！");
		}
	}

	public void checkBeforeDelete(List idList) throws EntityException{
		for(Object id:idList){
			PrFeeDetail prFeeDetail = (PrFeeDetail)this.getGeneralService().getById(id.toString());
			if(prFeeDetail.getPeFeeBatch()!=null){
				throw new EntityException("该交费信息已经添加到"+prFeeDetail.getPeFeeBatch().getName()+"交费批次中了，不能删除！");
			}
			if(prFeeDetail.getEnumConstByFlagFeeType().equals(this.getMyListService().getEnumConstByNamespaceCode("FlagFeeType", "0"))){
				if(!prFeeDetail.getPeStudent().getEnumConstByFlagStudentStatus().getCode().equals("1")){
					throw new EntityException("学生"+prFeeDetail.getPeStudent().getTrueName()+"不是已交费状态，不能删除！");
				}
					
			}
			if(!prFeeDetail.getEnumConstByFlagFeeCheck().equals(this.getMyListService().getEnumConstByNamespaceCode("FlagFeeCheck", "0"))){
				if(!prFeeDetail.getEnumConstByFlagFeeCheck().equals(this.getMyListService().getEnumConstByNamespaceCode("FlagFeeCheck", "3"))){
					throw new EntityException("学生"+prFeeDetail.getPeStudent().getTrueName()+"费用信息不是未上报未审核状态，不能删除！");						
				}
			}
		}
	}
	public Map delete() {
		
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				try{
					checkBeforeDelete(idList);
				}catch(EntityException e){
					map.put("success", "false");
					map.put("info", e.getMessage());
					return map;
				}
				map.put("success", "true");
				map.put("info", "删除成功");
				try{
					this.getGeneralService().deleteByIds(idList);
				}catch(EntityException e){
					map.clear();
					map.put("success", "false");
					map.put("info", e.getMessage());
				}catch(RuntimeException e){
					return this.checkForeignKey(e);
				}catch(Exception e1){
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}
	public PrFeeDetailService getPrFeeDetailService() {
		return prFeeDetailService;
	}

	public void setPrFeeDetailService(PrFeeDetailService prFeeDetailService) {
		this.prFeeDetailService = prFeeDetailService;
	}
}
