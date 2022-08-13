/**
 * Project Name:ItemDatabase File Name:GradeService.java Package
 * Name:cn.zeppin.service.imp Copyright (c) 2014, Zeppin All Rights Reserved.
 * 
 */
package cn.zeppin.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IGradeDAO;
import cn.zeppin.entity.Grade;
import cn.zeppin.service.api.IGradeService;

/**
 * ClassName: GradeService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午7:33:47 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class GradeService implements IGradeService
{
	
	private IGradeDAO gradeDAO;
	
	/**
	 * 递归得到学段全称
	 * 
	 * @author Clark
	 * @date: 2014年7月14日 下午7:32:20 <br/>
	 * @param grade
	 * @return
	 */
	@Override
	public String getGradeFullName(Grade grade)
	{
		// TODO Auto-generated method stub
		if (grade.getGrade() == null)
		{
			return grade.getName();
		}
		else
		{
			return getGradeFullName(grade.getGrade()) + ">>" + grade.getName();
		}
	}
	
	public IGradeDAO getGradeDAO()
	{
		return gradeDAO;
	}
	
	public void setGradeDAO(IGradeDAO gradeDAO)
	{
		this.gradeDAO = gradeDAO;
	}
	
	/**
	 * @category 获取所有学段
	 */
	@Override
	public List<Grade> getAllGrades()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @category 通过参数获取学段
	 */	
	public List<Grade> getGradeByParam(Map<String,Object> searchMap){
		return this.getGradeDAO().getGradeByParam(searchMap);
	}
	
	@Override
	public List<Grade> getAllGradesByLevel(int level)
	{
		// TODO Auto-generated method stub
		return this.gradeDAO.getAllGradesByLevel(level);
	}
	
	@Override
	public List<Grade> getAllGradesByLevel(int level, int pageStart, int pageSize)
	{
		// TODO Auto-generated method stub
		return this.gradeDAO.getAllGradesByLevel(level, pageStart, pageSize);
	}
	
	@Override
	public Grade add(Grade grade)
	{
		return this.gradeDAO.save(grade);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(Grade grade)
	{
		// TODO Auto-generated method stub
		this.gradeDAO.update(grade);
	}
	
	@Override
	public void delete(int id)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getChildCount(int id)
	{
		// TODO Auto-generated method stub
		return this.getGradeDAO().getChildCount(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.IGradeService#getById(int)
	 */
	@Override
	public Grade getById(int id)
	{
		// TODO Auto-generated method stub
		return this.getGradeDAO().get(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.IGradeService#getAllGradesByLevel(int, int,
	 * int, java.lang.String)
	 */
	@Override
	public List<Grade> getAllGradesByLevel(int level, int offset, int pagesize, String sorts)
	{
		// TODO Auto-generated method stub
		return this.getGradeDAO().getAllGradesByLevel(level, offset, pagesize, sorts);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.IGradeService#getCountByLevel(int)
	 */
	@Override
	public int getCountByLevel(int level)
	{
		// TODO Auto-generated method stub
		return this.getGradeDAO().getCountByLevel(level);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.IGradeService#getChildes(int, int, int,
	 * java.lang.String)
	 */
	@Override
	public List<Grade> getChildes(int pid, int offset, int pagesize, String sorts)
	{
		// TODO Auto-generated method stub
		return this.getGradeDAO().getChildes(pid, offset, pagesize, sorts);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.IGradeService#checkNameExist(java.lang.String)
	 */
	@Override
	public boolean checkNameExist(String string)
	{
		// TODO Auto-generated method stub
		return this.getGradeDAO().checkNameExist(string);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.IGradeService#delete(cn.zeppin.entity.Grade)
	 */
	@Override
	public void delete(Grade grade)
	{
		// TODO Auto-generated method stub
		grade.setStatus((short) 0);
		this.getGradeDAO().update(grade);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.IGradeService#getAllGradesByAdmin(java.lang.Integer
	 * , int, int, java.lang.String)
	 */
	@Override
	public List<Grade> getAllGradesByAdmin(int offset, int pageSize, String sorts, HashMap<String, Object> paras)
	{
		// TODO Auto-generated method stub
		return this.getAllGrades(offset, pageSize, sorts, true, paras);
	}
	
	public List<Grade> getAllGrades(int offset, int pageSize, String sorts, boolean isAdmin, HashMap<String, Object> paras)
	{
		List<Grade> lstGrades = new ArrayList<Grade>();
		
		lstGrades = this.getGradeDAO().getAllGrades(offset, pageSize, sorts, isAdmin, paras);
		return lstGrades;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.IGradeService#getAllGradesByUser(int, int,
	 * java.lang.String, java.util.HashMap)
	 */
	@Override
	public List<Grade> getAllGradesByUser(int offset, int pageSize, String sorts, HashMap<String, Object> paras)
	{
		// TODO Auto-generated method stub
		return this.getAllGrades(offset, pageSize, sorts, false, paras);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.IGradeService#getCountByParas(java.util.HashMap)
	 */
	@Override
	public int getCountByParas(HashMap<String, Object> paras, boolean isAdmin)
	{
		// TODO Auto-generated method stub
		return this.getGradeDAO().getChildCount(paras, isAdmin);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.IGradeService#getCountByParas(java.util.HashMap)
	 */
	@Override
	public int getCountByParas(HashMap<String, Object> paras)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.IGradeService#getLevelByPid(int)
	 */
	@Override
	public int getLevelByPid(int pid)
	{
		// TODO Auto-generated method stub
		return this.getGradeDAO().getLevelByPid(pid);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.IGradeService#hasChild(java.lang.Integer)
	 */
	@Override
	public boolean hasChild(Integer id)
	{
		// TODO Auto-generated method stub
		int count = this.getGradeDAO().getChildCount(id);
		if (count > 0)
		{
			return true;
		}
		return false;
	}
}
