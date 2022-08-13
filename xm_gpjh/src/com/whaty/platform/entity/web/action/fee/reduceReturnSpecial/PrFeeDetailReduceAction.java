package com.whaty.platform.entity.web.action.fee.reduceReturnSpecial;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.fee.PrFeeDetailReduceService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.action.fee.pay.PrFeeDetailInAction;
import com.whaty.platform.util.Const;

public class PrFeeDetailReduceAction extends MyBaseAction<PrFeeDetail> {

	private static final long serialVersionUID = 8871369685261726869L;
	private File upload;
	private PrFeeDetailReduceService prFeeDetailReduceService;
	
	
	/**
	 * 转向条件设置页面
	 * 
	 * @return
	 */
	public String batch() {
		return "batch";
	}
	
	public String batchexe() {
		this.setTogo("back");
		int count = 0;
		try {
			count = this.getPrFeeDetailReduceService().saveBatch(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "msg";
	}
	
	public String addone() {
		return "addone";
	}
	
	public String addexe() {
		this.setTogo("back");
		try {
			checkBeforeOperate();
			PrFeeDetail prFeeDetail = this.getPrFeeDetailReduceService().save(this.getBean());
			if(prFeeDetail!=null){
					this.setMsg("添加学生减免费用信息成功！");
					this.setTogo("/entity/fee/prFeeDetailReduce_addone.action");
			}else{
				this.setMsg("添加学生减免费用信息失败！");
			}
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
		}		
		return "msg";
	}
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true,true);
		this.getGridConfig().setTitle(this.getText("学生减免费用"));
		
		this.getGridConfig().addMenuFunction(this.getText("审核通过"), "updateforcheck", "");
		this.getGridConfig().addMenuFunction(this.getText("取消审核通过"), "cancelforcheck", "");
		this.getGridConfig().addMenuFunction(this.getText("审核不通过"), "updateforreject", "");
		this.getGridConfig().addMenuFunction(this.getText("取消审核不通过"), "cancelforreject", "");
		
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("peStudent.regNo"),"peStudent.regNo",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peStudent.peGrade.name",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peStudent.peSite.name",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peStudent.peEdutype.name",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peStudent.peMajor.name",true, false, true, "");
//		this.getGridConfig().addColumn(this.getText("减免类型"), "enumConstByFlagFeeType.name",true, true, true, "");
		ColumnConfig column = new ColumnConfig(this.getText("减免类型"),"enumConstByFlagFeeType.name");
		//20090413 去掉教师减免费用类型。 (龚妮娜)
		column.setComboSQL("select id,name from enum_const where namespace='FlagFeeType' and code in ('8','9')");
		this.getGridConfig().addColumn(column);
//		this.getGridConfig().addColumn(this.getText("减免状态"), "enumConstByFlagFeeCheck.name",true, false, true, "");
		ColumnConfig column2 = new ColumnConfig(this.getText("减免状态"),"enumConstByFlagFeeCheck.name", true, false, true, "TextField", true, 25, "");
		column2.setComboSQL("select id,name from enum_const where namespace='FlagFeeCheck' and code in ('2','3','4')");
		this.getGridConfig().addColumn(column2);
		this.getGridConfig().addColumn(this.getText("减免费用额"), "feeAmount",true, true, true, Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("备注"),"note",false, true, true, "TextArea",true,500);
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/prFeeDetailReduce";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		String[] cds={"8","9"};
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
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		checkBeforeOperate();
		DetachedCriteria dc = DetachedCriteria.forClass(PrFeeDetail.class);
		dc.add(Restrictions.eq("id", this.getBean().getId()))
			.createAlias("enumConstByFlagFeeCheck", "enumConstByFlagFeeCheck").add(Restrictions.eq("enumConstByFlagFeeCheck.code", "3"));
		java.util.List list = this.getGeneralService().getList(dc);
		if(list==null||list.size()<=0){
			throw new EntityException("修改失败！该费用已经被审核！");
		}
	}

	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {			
			String[] ids = getIds().split(",");
			List idList = new ArrayList();			
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			
			try {
				if(this.getColumn().equals("updateforcheck"))
					count = this.getPrFeeDetailReduceService().updateForCheck(idList,true);
				else if(this.getColumn().equals("cancelforcheck"))
					count = this.getPrFeeDetailReduceService().updateForCancel(idList,true);
				else if(this.getColumn().equals("updateforreject"))
					count = this.getPrFeeDetailReduceService().updateForCheck(idList,false);
				else if(this.getColumn().equals("cancelforreject"))
					count = this.getPrFeeDetailReduceService().updateForCancel(idList,false);
				else
					count = 0 ;
				if(count>0){
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"条记录操作成功"));
				}else{
					map.put("success", "false");
					map.put("info", "操作失败");
				}
			} catch (EntityException e) {
				e.printStackTrace();
				map.put("success", "false");
				map.put("info", e.getMessage());
			}	

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}
	public void setBean(PrFeeDetail instance) {
		super.superSetBean(instance);
	}
	
	public PrFeeDetail getBean(){
		return super.superGetBean();
	}
	public PrFeeDetailReduceService getPrFeeDetailReduceService() {
		return prFeeDetailReduceService;
	}

	public void setPrFeeDetailReduceService(
			PrFeeDetailReduceService prFeeDetailReduceService) {
		this.prFeeDetailReduceService = prFeeDetailReduceService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrFeeDetail.class;		
	}

}
