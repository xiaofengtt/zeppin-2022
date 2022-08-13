package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.io.File;
import java.math.BigDecimal;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchCourseService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PeCourseManagerAction extends MyBaseAction<PeTchCourse> {

	private static final long serialVersionUID = 9078813946660029710L;
	private String msg;
	private File upload;
	private PeTchCourseService peTchCourseService;

	public PeTchCourseService getPeTchCourseService() {
		return peTchCourseService;
	}

	public void setPeTchCourseService(PeTchCourseService peTchCourseService) {
		this.peTchCourseService = peTchCourseService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchCourse.class);
		dc.createCriteria("enumConstByFlagIsvalid","enumConstByFlagIsvalid");
		return dc;
	}
	
	public void setBean(PeTchCourse instance) {
		super.superSetBean(instance);
		
	}
	
	public PeTchCourse getBean(){
		return super.superGetBean();
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		BigDecimal b1 = new BigDecimal(this.getBean().getScoreHomeworkRate().toString());
		BigDecimal b2 = new BigDecimal(this.getBean().getScoreUsualRate().toString());
		BigDecimal b3 = new BigDecimal(this.getBean().getScoreExamRate().toString());
		if (b1.add(b2).add(b3).doubleValue() != 1) {
			throw new EntityException("三种比例之和应该等于 1 。");
		}
	}
	
	@Override
	public void checkBeforeAdd() throws EntityException {
		this.checkBeforeUpdate();
	}

	public String batch(){
		return "batch";
	}
	public String uploadCourse(){
		int count = 0;
		try{
			count = this.getPeTchCourseService().save_uploadCourse(this.getUpload());
		}catch(Exception e){
			this.setMsg(e.getMessage());
			return "uploadCourse_result";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "uploadCourse_result";
	}
	

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("课程列表"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		this.getGridConfig().addColumn(this.getText("课程代码"), "code");
		this.getGridConfig().addColumn(this.getText("考试成绩比例"),"scoreExamRate", false, true, true,Const.scale_for_extjs);
		this.getGridConfig().addColumn(this.getText("平时成绩比例"),"scoreUsualRate", false, true, true,Const.scale_for_extjs);
		this.getGridConfig().addColumn(this.getText("作业成绩比例"),"scoreHomeworkRate", false, true, true,Const.scale_for_extjs);
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("课程简介"), "note", false, true, true, "TextArea", true, 1000);
		this.getGridConfig().addRenderFunction(this.getText("设置课程教师"), "<a href=\"prTchCourseTeacher.action?bean.peTchCourse.id=${value}\"  target='_self'>设置课程教师</a>","id");
		this.getGridConfig().addRenderFunction(this.getText("查看历史教材"), "<a href=\"courseBookHis.action?ids=${value}\"  target='_blank'>查看历史教材</a>","id");
		this.getGridConfig().addRenderFunction(this.getText("查看历史课件"), "<a href=\"courseCoursewareHis.action?ids=${value}\"  target='_blank'>查看历史课件</a>","id");
	}


	@Override
	public void setEntityClass() {
		this.entityClass = PeTchCourse.class;
		
	}


	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peCourseManager";
		
	}
}
