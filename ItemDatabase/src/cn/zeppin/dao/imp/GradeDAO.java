/**
 * Project Name:ItemDatabase File Name:GradeDAO.java Package Name:cn.zeppin.dao.imp
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * 
 */
package cn.zeppin.dao.imp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IGradeDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Grade;

/**
 * ClassName: GradeDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:14:35 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class GradeDAO extends HibernateTemplateDAO<Grade, Integer> implements IGradeDAO {

	/**
	 * @category 获取状态为正常的当前级别科目
	 * @author sj
	 * @date: 2014年7月15日 下午4:52:39 <br/>
	 * @param level
	 * @return
	 */
	@Override
	public List<Grade> getAllGradesByLevel(int level) {
		return null;
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @author sj
	 * @date: 2014年7月17日 下午4:02:29 <br/>
	 * @param level
	 * @param status
	 * @param pageStart
	 * @param pageSize
	 * @param orderPara
	 * @return
	 */

	public List<Grade> getGradeByParam(Map<String,Object> searchMap){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(" from Grade where 1=1");
		if (searchMap.containsKey("id")) {
			sBuilder.append(" and id=" + searchMap.get("id"));
		}
		if (searchMap.containsKey("name")) {
			sBuilder.append(" and name like '%" + searchMap.get("name") + "%'");
		}
		if (searchMap.containsKey("grade")) {
			sBuilder.append(" and grade=" + searchMap.get("grade"));
		}
		if (searchMap.containsKey("level")) {
			sBuilder.append(" and level=" + searchMap.get("level"));
		}
		if (searchMap.containsKey("scode")) {
			sBuilder.append(" and scode like '").append(searchMap.get("scode")).append("%'");
		}
		return this.getByHQL(sBuilder.toString());
	}
	
	public List<Grade> getAllGradesByLevel(int level, int status, int pageStart, int pageSize, String orderPara) {
		String hqlsString = "from Grade where status=" + status + " and level=" + level;
		if (orderPara != null && !orderPara.equals("")) {
			hqlsString += " order by " + orderPara;
		}
		List<Grade> lstGrades = new ArrayList<>();
		lstGrades = getByHQL(hqlsString, pageStart, pageSize);
		return lstGrades;
	}

	@Override
	public List<Grade> getAllGradesByLevel(int level, int pageStart, int pageSize) {
		// TODO Auto-generated method stub
		return getAllGradesByLevel(level, 1, pageStart, pageSize, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.IGradeDAO#getAllGradesByLevel(int, int, int,
	 * java.lang.String)
	 */
	@Override
	public List<Grade> getAllGradesByLevel(int level, int offset, int pagesize, String sorts) {
		// TODO Auto-generated method stub
		String hqlsString = "from Grade where  level=" + level;
		if (sorts != null && !sorts.equals("")) {
			hqlsString += " order by " + sorts;
		}
		List<Grade> lstGrades = new ArrayList<>();
		lstGrades = getByHQL(hqlsString, offset, pagesize);
		return lstGrades;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.IGradeDAO#getCountByLevel(int)
	 */
	@Override
	public int getCountByLevel(int level) {
		// TODO Auto-generated method stub
		return getCountByLevel(level, null);
	}

	public int getCountByLevel(int level, String status) {
		int result = 0;
		String hqlString = "select count(*) from Grade where level=" + level;
		if (status != null) {
			hqlString += " and status=" + status;
		}
		result = Integer.parseInt(getResultByHQL(hqlString).toString());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.IGradeDAO#getChildes(int, int, int,
	 * java.lang.String)
	 */
	@Override
	public List<Grade> getChildes(int pid, int offset, int pagesize, String sorts) {
		// TODO Auto-generated method stub
		String hqlsString = "from Grade where  pid=" + pid;
		if (sorts != null && !sorts.equals("")) {
			hqlsString += " order by " + sorts;
		}
		List<Grade> lstGrades = new ArrayList<>();
		lstGrades = getByHQL(hqlsString, offset, pagesize);
		return lstGrades;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.IGradeDAO#checkNameExist(java.lang.String)
	 */
	@Override
	public boolean checkNameExist(String string) {
		// TODO Auto-generated method stub
		String hqlString = "select count(*) from Grade where name='" + string + "'";
		int result = Integer.parseInt(getResultByHQL(hqlString).toString());
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.IGradeDAO#getChildCount(int)
	 */
	@Override
	public int getChildCount(int id) {
		// TODO Auto-generated method stub
		String hqlString = "select count(*) from Grade where grade='" + id + "'";
		int result = Integer.parseInt(getResultByHQL(hqlString).toString());
		return result;
	}

	@Override
	public Grade save(Grade grade) {
		Grade result = super.save(grade);
		String format = "0000000000";
		DecimalFormat df = new DecimalFormat(format);
		String scode = df.format(result.getId());
		if (result.getGrade() != null) {
			scode = result.getGrade().getScode() + scode;
		}
		result.setScode(scode);
		result = this.update(result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.IGradeDAO#getAllGrades(java.lang.Integer, int,
	 * int, java.lang.String, boolean)
	 */
	@Override
	public List<Grade> getAllGrades(Integer pid, int offset, int pageSize, String sorts, boolean isAdmin) {

		// TODO Auto-generated method stub
		List<Grade> lstGrades = new ArrayList<Grade>();
		String hqlString = "from Grade where 1=1";
		if (pid == null) {
			hqlString += " and level=1";
		} else {
			hqlString += " and grade=" + pid;
		}
		if (!isAdmin) {
			hqlString += " and status=1";
		}
		if (!sorts.equals("")) {
			hqlString += " order by " + sorts;
		} else {
			hqlString += " order by id desc";
		}
		lstGrades = this.getByHQL(hqlString, offset, pageSize);
		return lstGrades;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.IGradeDAO#getAllGrades(int, int, java.lang.String,
	 * boolean, java.util.HashMap)
	 */
	@Override
	public List<Grade> getAllGrades(int offset, int pageSize, String sorts, boolean isAdmin, HashMap<String, Object> paras) {
		// TODO Auto-generated method stub
		List<Grade> lstGrades = new ArrayList<Grade>();
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(" from Grade where 1=1");
		if (paras.containsKey("id")) {
			sBuilder.append(" and id=" + paras.get("id"));
		}
		if (paras.containsKey("name")) {
			sBuilder.append(" and name like '%" + paras.get("name") + "%'");
		}
		if (paras.containsKey("grade")) {
			sBuilder.append(" and grade=" + paras.get("grade"));
		}
		if (paras.containsKey("level")) {
			sBuilder.append(" and level=" + paras.get("level"));
		}
		if (paras.containsKey("parent")) {
			sBuilder.append(" and parent=" + paras.get("parent"));
		}
		if (!isAdmin) {
			sBuilder.append(" and status=1");
		}
		else{
			if (paras.containsKey("status")) {
				sBuilder.append(" and status=" + paras.get("status"));
			}
		}
		if (!sorts.equals("")) {
			sBuilder.append(" order by " + sorts);
		} else {
			sBuilder.append(" order by id");
		}

		lstGrades = this.getByHQL(sBuilder.toString(), offset, pageSize);
		return lstGrades;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.IGradeDAO#getChildCount(java.util.HashMap)
	 */
	@Override
	public int getChildCount(HashMap<String, Object> paras, boolean isAdmin) {
		// TODO Auto-generated method stub
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select count(*) from Grade where 1=1");
		if (paras.containsKey("id")) {
			sBuilder.append(" and id=" + paras.get("id"));
		}
		if (paras.containsKey("name")) {
			sBuilder.append(" and name like '%" + paras.get("name") + "%'");
		}
		if (paras.containsKey("pid")) {
			sBuilder.append(" and grade=" + paras.get("pid"));
		}
		if (paras.containsKey("grade")) {
			sBuilder.append(" and grade=" + paras.get("grade"));
		}
		if (paras.containsKey("level")) {
			sBuilder.append(" and level=" + paras.get("level"));
		}

		if (!isAdmin) {
			sBuilder.append(" and status=1");
		}

		int result = Integer.parseInt(getResultByHQL(sBuilder.toString()).toString());
		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.IGradeDAO#getChildCount(java.util.HashMap)
	 */
	@Override
	public int getChildCount(HashMap<String, Object> paras) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.IGradeDAO#getLevelByPid(int)
	 */
	@Override
	public int getLevelByPid(int pid) {
		// TODO Auto-generated method stub
		String hqlString = "select level from Grade where id=" + pid;
		int level = Integer.parseInt(getResultByHQL(hqlString).toString());
		return level;
	}
}
