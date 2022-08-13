package com.whaty.platform.entity.service.studentStatas;

import java.util.Map;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;

public class StudentService {
	
	private GeneralDao generalDao;
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public Page statisticStuOnStatus(Map<String, String> map, PeStudent peStudent) throws EntityException{
		Page page = null;
		String sql = "";
		String select = "";
		String table = "";
		String reference = "";
		String groupBy = "";
		String sort = "";
		if("on".equals(map.get("checkEdutype"))){
			select += "e.name e_name, ";
			table += " , pe_edutype e ";
			reference += " and st.fk_edutype_id=e.id ";
			groupBy += ",e.name ";
		}
		if("on".equals(map.get("checkMajor"))){
			select += "m.name m_name, ";
			table += " , pe_major m ";
			reference += " and st.fk_major_id=m.id ";
			groupBy += ",m.name ";
		}
		if("on".equals(map.get("checkGrade"))){
			select += "g.name g_name, ";
			table += " , pe_grade g ";
			reference += " and st.fk_grade_id=g.id ";
			groupBy += ",g.name ";
		}
		if("on".equals(map.get("checkSite"))){
			select += "s.name s_name, ";
			table += " , pe_site s ";
			reference += " and st.fk_site_id=s.id ";
			groupBy += ",s.name ";
		}
		if(map.get("sort").equals("peMajor.name")){
			sort = " order by m_name "+map.get("dir");
		}else if(map.get("sort").equals("peEdutype.name")){
			sort = " order by e_name "+map.get("dir");
		}else if(map.get("sort").equals("peSite.name")){
			sort = " order by s_name "+map.get("dir");
		}else if(map.get("sort").equals("peGrade.name")){
			sort = " order by g_name "+map.get("dir");
		}else if(map.get("sort").equals("totalNum")){
			sort = " order by totalNum "+map.get("dir");
		}
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
		sql = "select "+select+" count(1) totalNum from pe_student st,enum_const ec "
			+table+" where st.flag_student_status=ec.id and ec.code='4' "+reference+groupBy+sort;
		try{
			page = this.generalDao.getByPageSQL(sql, Integer.parseInt(map.get("pageSize")), Integer.parseInt(map.get("startIndex")));
		}catch(Exception e){
			throw new EntityException(e);
		}
		return page;
	}

}
