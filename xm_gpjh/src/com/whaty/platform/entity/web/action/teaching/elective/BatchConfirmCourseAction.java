package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.ManualOpenCourseService;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 批量开课
 * @author Administrator
 *
 */
public class BatchConfirmCourseAction extends MyBaseAction {
	
	private String sitename;//学习中心
	private String edutypename;//层次
	private String majorname;//专业
	private String gradename;//年级
	private ManualOpenCourseService manualOpenCourseService;


	@Override
	public void initGrid() {

	}


	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/batchConfirmCourse";
	}
	
	/**
	 * 跳转页面到确认执行页面
	 * @return
	 */
	
	public String result1() {
		return "result";
	}
	/**
	 * 执行批量开课动作
	 *
	 */
	public String  openCourseExe() {
		List<String> peSiteList = null;
		String sql_site = "select t.id from pe_site t";
		if(!this.getSitename().equals("所有学习中心")){
			sql_site += " where t.name = '"+this.getSitename() + "' ";
		}
		try {
			peSiteList = this.getGeneralService().getBySQL(sql_site);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		List<String> peEdutypeList = null;
		String sql_edutype = "select t.id from pe_edutype t";
		if(!this.getEdutypename().equals("所有层次")){
			sql_edutype += " where t.name = '"+this.getEdutypename() + "' ";
		}
		try {
			peEdutypeList = this.getGeneralService().getBySQL(sql_edutype);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		List<String> peMajorList = null;
		String sql_major = "select t.id from pe_major t";
		if(!this.getMajorname().equals("所有专业")){
			sql_major += " where t.name = '"+this.getMajorname() + "' ";
		}
		try {
			peMajorList = this.getGeneralService().getBySQL(sql_major);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		List<String> peGradeList = null;
		String sql_grade = "select t.id from pe_grade t";
		if(!this.getGradename().equals("所有年级")){
			sql_grade += " where t.name = '"+this.getGradename() + "' ";
		}
		try {
			peGradeList = this.getGeneralService().getBySQL(sql_grade);
		} catch (EntityException e) {
			e.printStackTrace();
		}

		
		this.setTogo("back");
		try {
			this.setMsg(this.manualOpenCourseService.saveOpenCourseBatch(peSiteList, peEdutypeList, peMajorList, peGradeList));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		return "msg";
	}


	public String getSitename() {
		return sitename;
	}


	public void setSitename(String sitename) {
		this.sitename = sitename;
	}


	public String getEdutypename() {
		return edutypename;
	}


	public void setEdutypename(String edutypename) {
		this.edutypename = edutypename;
	}


	public String getMajorname() {
		return majorname;
	}


	public void setMajorname(String majorname) {
		this.majorname = majorname;
	}


	public String getGradename() {
		return gradename;
	}


	public void setGradename(String gradename) {
		this.gradename = gradename;
	}
	
	public ManualOpenCourseService getManualOpenCourseService() {
		return manualOpenCourseService;
	}


	public void setManualOpenCourseService(
			ManualOpenCourseService manualOpenCourseService) {
		this.manualOpenCourseService = manualOpenCourseService;
	}
}
