package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrStuChangeEdutype;
import com.whaty.platform.entity.bean.PrStuChangeSite;
import com.whaty.platform.entity.service.studentStatas.PeChangeTypeService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeChangeTypeAction extends MyBaseAction<PrStuChangeEdutype> {

	private static final long serialVersionUID = 1785012563923064402L;
	
	private PeChangeTypeService peChangeTypeService;
	
	public String turntoSearch() {
		return "turntoSearch";
	}
	
	public String turntochange() {
//		try{
//			if(this.getBean().getPeStudent().getRegNo()==null||this.getBean().getPeStudent().getRegNo().trim().length()<=0){
//				this.setMsg("请输入学号！");
//				return "turntoSearch";
//			}
//			DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
//			dc.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
//				.add(Restrictions.eq("enumConstByFlagStudentStatus", this.getMyListService().getEnumConstByNamespaceCode("FlagStudentStatus", "4")))
//				.add(Restrictions.eq("regNo", this.getBean().getPeStudent().getRegNo().trim()));
//			java.util.List studentList = this.getGeneralService().getList(dc);
//			if(studentList==null||studentList.size()<=0){
//				this.setMsg("没有查询到学号为"+this.getBean().getPeStudent().getRegNo().trim()+"的在籍学生,请确认学号正确！");
//				return "turntoSearch";
//			}else{
//				PeStudent student = (PeStudent)studentList.remove(0);
		String id = this.getIds().substring(0,this.getIds().indexOf(","));
		PeStudent student = (PeStudent)this.getMyListService().getById(PeStudent.class, id);
		this.setBean(new PrStuChangeEdutype());
				this.getBean().setPeStudent(student);
				this.getBean().setPeEdutypeByFkOldEdutypeId(student.getPeEdutype());
				return "turntochange";
//			}
//		}catch(Exception e){
//			this.setMsg("请输入正确学号！");
//			return "turntoSearch";
//		}
	}
	
	public String executechange() {
		PrStuChangeEdutype prStuChangeEdutype = this.getBean();
		try{
			this.setBean(this.getPeChangeTypeService().save(this.getBean()));
			if(this.getBean()!=null){
				this.setMsg("学生转层次成功");	
				this.setTogo("/entity/studentStatus/studentStatus.action");
				return "msg";
			}else{
				this.setBean(prStuChangeEdutype);
				this.setMsg("学生转层次失败");	
			}
		}catch(Exception e){
			this.setBean(prStuChangeEdutype);
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		return "turntochange";
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,false,true);
		this.getGridConfig().setTitle(this.getText("学生变动层次列表"));
//		去掉查询条件的id(20090424 龚妮娜)
		this.getGridConfig().addColumn(this.getText("id"), "id",false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("原层次"),"peEdutypeByFkOldEdutypeId.name");
		this.getGridConfig().addColumn(this.getText("新层次"),"peEdutypeByFkNewEdutypeId.name");
		this.getGridConfig().addColumn(this.getText("异动时间"),"CDate");
//		this.getGridConfig().addColumn(this.getText("原因"),"note",false,true,true,"TextArea",true,500);
		this.getGridConfig().addRenderFunction(this.getText("变动层次原因"),
				"<a href=\"/entity/studentStatus/peChangeType_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看原因</a>",
				"id");
	}
	/**
	 * 转向查看详细页面
	 * 
	 * @return
	 */
	public String viewDetail() {
		try {
		 this.setBean(this.getGeneralService().getById(this.getBean().getId()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "detail";
	}
	@Override
	public void setEntityClass() {
		this.entityClass=PrStuChangeEdutype.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/peChangeType";
	}
	public void setBean(PrStuChangeEdutype instance) {
		super.superSetBean(instance);
	}
	
	public PrStuChangeEdutype getBean(){
		return super.superGetBean();
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrStuChangeEdutype.class);
		dc.createAlias("peEdutypeByFkNewEdutypeId", "peEdutypeByFkNewEdutypeId")
			.createAlias("peEdutypeByFkOldEdutypeId", "peEdutypeByFkOldEdutypeId")
			.createAlias("peStudent", "peStudent");
		return dc;
	}

	public PeChangeTypeService getPeChangeTypeService() {
		return peChangeTypeService;
	}

	public void setPeChangeTypeService(PeChangeTypeService peChangeTypeService) {
		this.peChangeTypeService = peChangeTypeService;
	}
}
