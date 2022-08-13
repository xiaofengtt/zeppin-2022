package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrStuChangeSite;
import com.whaty.platform.entity.service.studentStatas.PeChangeSiteService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeChangeSiteAction extends MyBaseAction<PrStuChangeSite> {

	private static final long serialVersionUID = -3744409701619164813L;
	
	private PeChangeSiteService peChangeSiteService;
	
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
			this.setBean(new PrStuChangeSite());
				this.getBean().setPeStudent(student);
				this.getBean().setPeSiteByFkOldSiteId(student.getPeSite());
				return "turntochange";
//			}
//		}catch(Exception e){
//			this.setMsg("请输入正确学号！");
//			return "turntoSearch";
//		}
	}
	
	public String executechange() {
		PrStuChangeSite prStuChangeSite = this.getBean();
		try{
			this.setBean(this.getPeChangeSiteService().save(this.getBean()));
			if(this.getBean()!=null){
				this.setMsg("学生转学习中心成功");	
				this.setTogo("/entity/studentStatus/studentStatus.action");
				return "msg";
			}else{
				this.setBean(prStuChangeSite);
				this.setMsg("学生转学习中心失败");	
			}
		}catch(Exception e){
			this.setBean(prStuChangeSite);
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		return "turntochange";
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,false,true);
		this.getGridConfig().setTitle(this.getText("学生变动学习中心列表"));
		//去掉查询条件的id(20090424 龚妮娜)
		this.getGridConfig().addColumn(this.getText("id"), "id",false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("原学习中心"),"peSiteByFkOldSiteId.name");
		this.getGridConfig().addColumn(this.getText("新学习中心"),"peSiteByFkNewSiteId.name");
		this.getGridConfig().addColumn(this.getText("异动时间"),"CDate");
//		this.getGridConfig().addColumn(this.getText("原因"),"note",false,true,true,"TextArea",true,500);
		this.getGridConfig().addRenderFunction(this.getText("变动学习中心原因"),
				"<a href=\"/entity/studentStatus/peChangeSite_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看原因</a>",
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
		this.entityClass = PrStuChangeSite.class;		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/peChangeSite";		
	}
	public void setBean(PrStuChangeSite instance) {
		super.superSetBean(instance);
	}
	
	public PrStuChangeSite getBean(){
		return super.superGetBean();
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrStuChangeSite.class);
		dc.createAlias("peSiteByFkNewSiteId", "peSiteByFkNewSiteId")
			.createAlias("peSiteByFkOldSiteId", "peSiteByFkOldSiteId")
			.createAlias("peStudent", "peStudent");
		return dc;
	}

	public PeChangeSiteService getPeChangeSiteService() {
		return peChangeSiteService;
	}

	public void setPeChangeSiteService(PeChangeSiteService peChangeSiteService) {
		this.peChangeSiteService = peChangeSiteService;
	}
}
