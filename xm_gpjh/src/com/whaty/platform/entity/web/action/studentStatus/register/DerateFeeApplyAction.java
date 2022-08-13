package com.whaty.platform.entity.web.action.studentStatus.register;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class DerateFeeApplyAction extends MyBaseAction {

	private static final long serialVersionUID = 6378557661818050095L;
	private String examCardNum;
	private String applyNote;

	@Override
	public void initGrid() {
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass=SystemApply.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/derateFeeApply";
	}
	
	public void setBean(SystemApply instance) {
		super.superSetBean(instance);
	}
	
	public SystemApply getBean(){
		return (SystemApply)super.superGetBean();
	}
	
	public String turnto(){
		return "turnto";
	}
	public String apply(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createCriteria("peRecStudent", "peRecStudent", DetachedCriteria.LEFT_JOIN)
			.add(Restrictions.eq("peRecStudent.examCardNum", examCardNum));
		try {
			List tempList0 = this.getGeneralService().getList(dc);
			if(tempList0==null||tempList0.size()!=1){
				this.setMsg("没有查找到准考证号为："+examCardNum+"的学生!");
			}else{
				DetachedCriteria dc0 = DetachedCriteria.forClass(SystemApply.class);
				dc0.createCriteria("peStudent", "peStudent")
					.add(Restrictions.eq("peStudent.id", ((PeStudent)tempList0.get(0)).getId()));
				List list = this.getGeneralService().getList(dc0);
				if(list==null||list.size()>0){
					this.setMsg("学生已经申请过了，不需要再申请！");
				}else{
					this.setBean(new SystemApply());
					this.getBean().setPeStudent((PeStudent)tempList0.remove(0));
					this.getBean().setEnumConstByApplyType(this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "0"));
					this.getBean().setEnumConstByFlagApplyStatus(this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
					this.getBean().setApplyNote(applyNote);
					this.getBean().setApplyDate(new java.util.Date());
					this.getGeneralService().save(this.getBean());
					this.setMsg("准考证号为："+examCardNum+"的学生申请减免预交费成功！");
					this.setTogo("/entity/studentStatus/derateFeeApply_turnto.action");
					return "msg";
				}
			}				
		} catch (EntityException e) {
			this.setMsg("学生申请失败");// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "turnto";
	}

	public String getExamCardNum() {
		return examCardNum;
	}

	public void setExamCardNum(String examCardNum) {
		this.examCardNum = examCardNum;
	}

	public String getApplyNote() {
		return applyNote;
	}

	public void setApplyNote(String applyNote) {
		this.applyNote = applyNote;
	}

}
