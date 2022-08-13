package com.whaty.platform.entity.web.action.teaching.elective;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.SemesterOpenCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PrTchOpenCourseAction extends MyBaseAction<PrTchOpencourse> {
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
		this.getGridConfig().setTitle("学期开课查看");
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"), "peSemester.name");
		//20090413 选择有效的课程，无效的课程不加入教学计划中。 (龚妮娜)
		ColumnConfig column = new ColumnConfig(this.getText("课程"),"peTchCourse.name");		
		column.setComboSQL("select t.id,t.name from PE_TCH_COURSE t ,enum_const m where  m.namespace = 'FlagIsvalid' and t.flag_isvalid=m.id and m.code=1 ");
		this.getGridConfig().addColumn(column);
		//this.getGridConfig().addColumn(this.getText("课程"),"peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("建议考试场次"), "adviceExamNo", true, true, false,Const.oneNum_for_extjs);
		this.getGridConfig().addRenderScript("建议考试场次", "{if (${value}=='') return '未录入';return ${value}}", "adviceExamNo");
		this.getGridConfig().addColumn(this.getText("上课次数"), "courseTime",true,false,false,Const.scoreLine_for_extjs);
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
		this.servletPath = "/entity/teaching/prTchOpencourse";
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
	
	/**
	 * 检查如果有已经选课开课的课程 不能删除
	 */
	public void checkBeforeDelete(List idList) throws EntityException{
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission").add(Restrictions.eq("code", "1"));
		dc.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peTchCourse", "peTchCourse").add(Restrictions.in("id", idList));
		
		List<PrTchStuElective> list = null;
		list = this.getGeneralService().getList(dc);
		if(list!=null&&list.size()>0){
			Set<String> course = new HashSet<String>();
			String str = "课程：";
			for (PrTchStuElective elective : list) {
				course.add(elective.getPrTchOpencourse().getPeTchCourse().getName());
			}
			for (String string : course) {
				str += string + "，";
			}
			str += "已经有学生选课开课，无法删除!";
			throw new EntityException(str);
		}
		
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
