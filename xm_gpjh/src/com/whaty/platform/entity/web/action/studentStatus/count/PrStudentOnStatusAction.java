package com.whaty.platform.entity.web.action.studentStatus.count;

import java.util.HashMap;
import java.util.Map;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.studentStatas.StudentService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrStudentOnStatusAction extends MyBaseAction {
	
	private StudentService studentService;
	Map selectItemMap = null;
	private String checkEdutype;
	private String checkMajor;
	private String checkSite;
	private String checkGrade;
	

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public Map getSelectItemMap() {
		return selectItemMap;
	}

	public void setSelectItemMap(Map selectItemMap) {
		this.selectItemMap = selectItemMap;
	}

	public String getCheckEdutype() {
		return checkEdutype;
	}

	public void setCheckEdutype(String checkEdutype) {
		this.checkEdutype = checkEdutype;
	}

	public String getCheckMajor() {
		return checkMajor;
	}

	public void setCheckMajor(String checkMajor) {
		this.checkMajor = checkMajor;
	}

	public String getCheckSite() {
		return checkSite;
	}

	public void setCheckSite(String checkSite) {
		this.checkSite = checkSite;
	}

	public String getCheckGrade() {
		return checkGrade;
	}

	public void setCheckGrade(String checkGrade) {
		this.checkGrade = checkGrade;
	}

	@Override
	public void initGrid() {
//		setMap();
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().setTitle(this.getText("在籍学生统计"));
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn("id", "id",false,false,false,"");
		if("on".equals(checkEdutype)){
			this.getGridConfig().addColumn("层次", "combobox_peEdutype.e_name");
		}
		if("on".equals(checkMajor)){
			this.getGridConfig().addColumn("专业", "combobox_peMajor.m_name");
		}
		if("on".equals(checkGrade)){
			this.getGridConfig().addColumn("年级", "combobox_peGrade.g_name");
		}
		if("on".equals(checkSite)){
			this.getGridConfig().addColumn("站点", "combobox_peSite.s_name");
		}
		this.getGridConfig().addColumn("人数", "totalNum", false, true, true, "", false, 0);
		
	}
	
//	private void setMap() {
//		selectItemMap = new HashMap<String, String>();
//		selectItemMap.put("pageSize", this.getLimit());
//		selectItemMap.put("startIndex", this.getStart());
//		selectItemMap.put("sort", this.getSort());
//		selectItemMap.put("dir", this.getDir());
//		
//		if("on".equals(checkEdutype)){
//			selectItemMap.put("checkEdutype", checkEdutype);
//		}
//		if("on".equals(checkMajor)){
//			selectItemMap.put("checkMajor", checkMajor);
//		}
//		if("on".equals(checkGrade)){
//			selectItemMap.put("checkGrade", checkGrade);
//		}
//		if("on".equals(checkSite)){
//			selectItemMap.put("checkSite", checkSite);
//		}
//	}

	@Override
	public Page list(){
		String sql = "";
		String select = "";
		String table = "";
		String reference = "";
		String groupBy = "";
		String sort = "";
		if("on".equals(checkEdutype)){
			select += "e.name e_name, ";
			table += " , pe_edutype e ";
			reference += " and st.fk_edutype_id=e.id ";
			groupBy += ",e.name ";
		}
		if("on".equals(checkMajor)){
			select += "m.name m_name, ";
			table += " , pe_major m ";
			reference += " and st.fk_major_id=m.id ";
			groupBy += ",m.name ";
		}
		if("on".equals(checkGrade)){
			select += "g.name g_name, ";
			table += " , pe_grade g ";
			reference += " and st.fk_grade_id=g.id ";
			groupBy += ",g.name ";
		}
		if("on".equals(checkSite)){
			select += "s.name s_name, ";
			table += " , pe_site s ";
			reference += " and st.fk_site_id=s.id ";
			groupBy += ",s.name ";
		}
		
		 PeStudent peStudent = this.getBean();
		 
		if(peStudent !=null && peStudent.getPeEdutype() != null && !"".equals(peStudent.getPeEdutype().getName())){
			reference += " and e.name='"+peStudent.getPeEdutype().getName()+"' ";
		}
		if(peStudent !=null && peStudent.getPeMajor() != null && !"".equals(peStudent.getPeMajor().getName())){
			reference += " and m.name='"+peStudent.getPeMajor().getName()+"' ";
		}
		if(peStudent !=null && peStudent.getPeGrade() != null && !"".equals(peStudent.getPeGrade().getName())){
			reference += " and g.name='"+peStudent.getPeGrade().getName()+"' ";
		}
		if(peStudent !=null && peStudent.getPeSite() != null && !"".equals(peStudent.getPeSite().getName())){
			reference += " and s.name='"+peStudent.getPeSite().getName()+"' ";
		}
		if(!select.equals("")){
			groupBy = " group by "+groupBy.substring(1);
		}
		sql = "select rownum as id, one.*  from( select "+select+" count(1) totalNum from pe_student st,enum_const ec "
			+table+" where st.flag_student_status=ec.id and ec.code='4' "+reference+groupBy +" ) one   ";
		 StringBuffer sql_temp = new StringBuffer(sql);
		 this.setSqlCondition(sql_temp);
		Page page = null;
		try {
//			page = this.studentService.statisticStuOnStatus(selectItemMap,this.getBean());
            page = getGeneralService().getByPageSQL(sql_temp.toString(),
                    Integer.parseInt(this.getLimit()),
                    Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	public void setBean(PeStudent instance) {
		super.superSetBean(instance);
	}
	
	public PeStudent getBean(){
		return (PeStudent)super.superGetBean();
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/prStudentOnStatus";
	}
}
