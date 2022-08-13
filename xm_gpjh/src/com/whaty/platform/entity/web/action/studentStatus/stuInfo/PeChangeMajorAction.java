package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrStuChangeMajor;
import com.whaty.platform.entity.service.studentStatas.PeChangeMajorService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeChangeMajorAction extends MyBaseAction<PrStuChangeMajor> {

	private static final long serialVersionUID = 1132443434213001829L;
	
	private PeChangeMajorService peChangeMajorService;
	
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
		this.setBean(new PrStuChangeMajor());
				this.getBean().setPeStudent(student);
				this.getBean().setPeMajorByFkOldMajorId(student.getPeMajor());
				return "turntochange";
//			}
//		}catch(Exception e){
//			this.setMsg("请输入正确学号！");
//			return "turntoSearch";
//		}
	}
	
	public String executechange() {
		PrStuChangeMajor prStuChangeMajor = this.getBean();
		try{
			this.setBean(this.getPeChangeMajorService().save(this.getBean()));
			if(this.getBean()!=null){
				this.setMsg("学生转专业成功");	
				this.setTogo("/entity/studentStatus/studentStatus.action");
				return "msg";
			}else{
				this.setBean(prStuChangeMajor);
				this.setMsg("学生转专业失败");	
			}
		}catch(Exception e){
			this.setBean(prStuChangeMajor);
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		return "turntochange";
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,false,true);
		this.getGridConfig().setTitle(this.getText("学生变动专业列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("原专业"),"peMajorByFkOldMajorId.name");
		this.getGridConfig().addColumn(this.getText("新专业"),"peMajorByFkNewMajorId.name");
		this.getGridConfig().addColumn(this.getText("异动时间"),"CDate");
//		this.getGridConfig().addColumn(this.getText("原因"),"note",false,true,true,"TextArea",true,500);
		this.getGridConfig().addRenderFunction(this.getText("变动专业原因"),
				"<a href=\"/entity/studentStatus/peChangeMajor_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看原因</a>",
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
		this.entityClass = PrStuChangeMajor.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/peChangeMajor";
	}
	public void setBean(PrStuChangeMajor instance) {
		super.superSetBean(instance);
	}
	
	public PrStuChangeMajor getBean(){
		return super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrStuChangeMajor.class);
		dc.createAlias("peMajorByFkNewMajorId", "peMajorByFkNewMajorId")
			.createAlias("peMajorByFkOldMajorId", "peMajorByFkOldMajorId")
			.createAlias("peStudent", "peStudent");
		return dc;
	}

	public PeChangeMajorService getPeChangeMajorService() {
		return peChangeMajorService;
	}

	public void setPeChangeMajorService(PeChangeMajorService peChangeMajorService) {
		this.peChangeMajorService = peChangeMajorService;
	}
}
