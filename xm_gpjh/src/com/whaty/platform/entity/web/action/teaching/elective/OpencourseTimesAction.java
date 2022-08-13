package com.whaty.platform.entity.web.action.teaching.elective;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.SemesterOpenCourseService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class OpencourseTimesAction extends MyBaseAction<PrTchOpencourse> {
	private File upload;
	private SemesterOpenCourseService semesterOpenCourseService;
	
	public SemesterOpenCourseService getSemesterOpenCourseService() {
		return semesterOpenCourseService;
	}

	public void setSemesterOpenCourseService(
			SemesterOpenCourseService semesterOpenCourseService) {
		this.semesterOpenCourseService = semesterOpenCourseService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("上课次数设置");
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"), "peSemester.name",true, false, true,"");
		this.getGridConfig().addColumn(this.getText("课程"),"peTchCourse.name",true, false, true,"");
		this.getGridConfig().addColumn(this.getText("建议考试场次"), "adviceExamNo", true, false, false,Const.scoreLine_for_extjs);
		this.getGridConfig().addColumn(this.getText("上课次数"), "courseTime",true,true,false,Const.scoreLine_for_extjs);
		
		this.getGridConfig().addRenderScript("建议考试场次", "{if (${value}=='') return '未录入';return ${value}}", "adviceExamNo");
		this.getGridConfig().addRenderScript(this.getText("上课次数"), "{if (${value}=='') return '未录入';return ${value}}", "courseTime");

	}
	
	/**
	 * 批量导入课程建议考试场次
	 * @return
	 */
	public String openCourseExe(){
		this.setTogo("back");
		try {
			//先进行学期开课操作
			this.getSemesterOpenCourseService().saveSemesterOpenCourse();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			int count = this.getSemesterOpenCourseService().saveOpenCourseBatch(this.getUpload(),"examNo");
			this.setMsg("成功上传"+count+"条数据");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		return "msg";
	}
	/**
	 * 批量导入课程上课次数
	 * @return
	 */
	public String openCourseTime(){
		this.setTogo("back");
		try {
			//先进行学期开课操作
			this.getSemesterOpenCourseService().saveSemesterOpenCourse();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			int count = this.getSemesterOpenCourseService().saveOpenCourseBatch(this.getUpload(),"courseTime");
			this.setMsg("成功上传"+count+"条数据");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		return "msg";
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PrTchOpencourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/opencourseTimes";
	}

	public void setBean(PrTchOpencourse instance) {
		super.superSetBean(instance);
	}

	public PrTchOpencourse getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchOpencourse.class);
		dc.createCriteria("peSemester", "peSemester").addOrder(Order.desc("flagNextActive"));
		dc.createCriteria("peTchCourse", "peTchCourse");
		
		return dc;
	}

	@Override
	public Map add() {
		Map map = new HashMap();
		this.setBean((PrTchOpencourse)super.setSubIds(this.getBean()));
		
		PrTchOpencourse instance = null;

		try {
			instance = this.getSemesterOpenCourseService().savePrTchOpencourse(this.getBean());
		} catch (EntityException e) {
			map.put("success", "false");
			map.put("info", e.getMessage());
			return map;
		} catch (Exception e) {
		return super.checkAlternateKey(e, "添加");
		}
		map.put("success", "true");
		map.put("info", "添加成功");
		
		return map;
	}
	
	public String semesterOpenCourse() {
		int result = 0;
		try {
			result = this.getSemesterOpenCourseService().saveSemesterOpenCourse();
			this.setMsg("已经开课" + result + "门课程。");
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
		} catch(RuntimeException e1){
			this.setMsg("课程已开");
		}
		return "msg";
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	//删除学期开课同时删除选该课的选课记录 张羽：2008-12-26 使用hibernate级联删除完成

}
