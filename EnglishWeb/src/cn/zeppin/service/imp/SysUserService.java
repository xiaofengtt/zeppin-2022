/** 
 * Project Name:CETV_TEST 
 * File Name:SysUserService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISysUserDAO;
import cn.zeppin.dao.api.ISysUserGradeDAO;
import cn.zeppin.dao.api.ISysUserSubjectDAO;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.SysUserGrade;
import cn.zeppin.entity.SysUserSubject;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.utility.Dictionary;

/**
 * ClassName: SysUserService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月17日 下午5:01:52 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class SysUserService implements ISysUserService {

	private ISysUserDAO sysUserDAO;
	private ISysUserGradeDAO sysUserGradeDAO;
	private ISysUserSubjectDAO sysUserSubjectDAO;

	/**
	 * @return the sysUserDAO
	 */
	public ISysUserDAO getSysUserDAO() {
		return sysUserDAO;
	}

	/**
	 * @param sysUserDAO
	 *            the sysUserDAO to set
	 */
	public void setSysUserDAO(ISysUserDAO sysUserDAO) {
		this.sysUserDAO = sysUserDAO;
	}

	/**
	 * @return the sysUserGradeDAO
	 */
	public ISysUserGradeDAO getSysUserGradeDAO() {
		return sysUserGradeDAO;
	}

	/**
	 * @param sysUserGradeDAO
	 *            the sysUserGradeDAO to set
	 */
	public void setSysUserGradeDAO(ISysUserGradeDAO sysUserGradeDAO) {
		this.sysUserGradeDAO = sysUserGradeDAO;
	}

	/**
	 * @return the sysUserSubjectDAO
	 */
	public ISysUserSubjectDAO getSysUserSubjectDAO() {
		return sysUserSubjectDAO;
	}

	/**
	 * @param sysUserSubjectDAO
	 *            the sysUserSubjectDAO to set
	 */
	public void setSysUserSubjectDAO(ISysUserSubjectDAO sysUserSubjectDAO) {
		this.sysUserSubjectDAO = sysUserSubjectDAO;
	}

	/**
	 * 通过ID获取用户对象
	 * 
	 * @author Clark
	 * @date: 2014年7月14日 下午5:58:07 <br/>
	 * @param userID
	 * @return SysUser
	 */
	@Override
	public SysUser getSysUser(Integer id) {
		// TODO Auto-generated method stub
		return this.getSysUserDAO().get(id);
	}

	/**
	 * 通过用户名密码获取用户Entity
	 * 
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/>
	 * @param loginname
	 * @param password
	 * @return SysUser
	 */
	@Override
	public SysUser getSysUser(String loginname, String password) {
		// TODO Auto-generated method stub
		return this.getSysUserDAO().getSysUser(loginname, password);
	}

	/**
	 * 通过用户名获取用户Entity
	 * 
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/>
	 * @param loginname
	 * @return SysUser
	 */
	@Override
	public SysUser getSysUser(String loginname) {
		// TODO Auto-generated method stub
		return this.getSysUserDAO().getSysUser(loginname);
	}

	/**
	 * 通过用户名密码和用户状态获取用户SysUser
	 * 
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/>
	 * @param loginname
	 * @param password
	 */
	@Override
	public SysUser getSysUser(String loginname, String password, int status) {
		// TODO Auto-generated method stub
		return this.getSysUserDAO().getSysUser(loginname, password, status);
	}

	/**
	 * 添加用户
	 * 
	 * @author Clark
	 * @date: 2014年6月24日 下午2:28:30 <br/>
	 * @param sysUser
	 * @return SysUser
	 */
	@Override
	public SysUser addSysUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		return getSysUserDAO().save(sysUser);
	}

	/**
	 * 添加用户(含赋权)
	 * 
	 * @author Clark
	 * @date: 2014年6月24日 下午2:28:30 <br/>
	 * @param sysUser
	 * @param subjectList
	 * @param gradeList
	 * @return SysUser
	 */
	@Override
	public SysUser addSysUser(SysUser sysUser, List<Subject> subjectList, List<Grade> gradeList) {
		// TODO Auto-generated method stub

		// 添加系统用户
		SysUser result = this.getSysUserDAO().save(sysUser);

		// 添加编辑学段权限
		if (gradeList != null) {
			for (Grade grade : gradeList) {
				SysUserGrade sysUserGrade = new SysUserGrade();
				sysUserGrade.setGrade(grade);
				sysUserGrade.setSysUser(result);
				this.getSysUserGradeDAO().save(sysUserGrade);
			}
		}
		// 添加编辑学科权限
		if (subjectList != null) {
			for (Subject subject : subjectList) {
				SysUserSubject sysUserSubject = new SysUserSubject();
				sysUserSubject.setSubject(subject);
				sysUserSubject.setSysUser(result);
				this.getSysUserSubjectDAO().save(sysUserSubject);

			}
		}
		return result;
	}

	/**
	 * 删除用户，这里对用户仅做逻辑删除
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 下午8:14:30 <br/>
	 * @param userID
	 * @return SysUser
	 */
	@Override
	public SysUser deleteSysUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		// 停用，不真删
		sysUser.setStatus(Dictionary.USER_STATUS_CLOSED);
		return this.getSysUserDAO().update(sysUser);
	}

	/**
	 * 修改用户
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午5:48:37 <br/>
	 * @param sysUser
	 * @return SysUser
	 */
	@Override
	public SysUser updateSysUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		return this.getSysUserDAO().update(sysUser);
	}

	/**
	 * 修改用户(含赋权)
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午5:48:37 <br/>
	 * @param sysUser
	 * @param subjectList
	 * @param gradeList
	 * @return SysUser
	 */
	@Override
	public SysUser updateSysUser(SysUser sysUser, List<Subject> subjectList, List<Grade> gradeList) {
		// TODO Auto-generated method stub

		// 更新用户信息
		SysUser result = this.getSysUserDAO().update(sysUser);

		// 删除原来的学科权限
		this.getSysUserSubjectDAO().deleteBySysUser(sysUser);

		// 删除原来的学段权限
		this.getSysUserGradeDAO().deleteBySysUser(sysUser);

		// 添加编辑学科权限
		if (subjectList != null) {
			for (Subject subject : subjectList) {
				SysUserSubject sysUserSubject = new SysUserSubject();
				sysUserSubject.setSubject(subject);
				sysUserSubject.setSysUser(result);
				this.getSysUserSubjectDAO().save(sysUserSubject);

			}
		}
		// 添加编辑学段权限
		if (gradeList != null) {
			for (Grade grade : gradeList) {
				SysUserGrade sysUserGrade = new SysUserGrade();
				sysUserGrade.setGrade(grade);
				sysUserGrade.setSysUser(result);
				this.getSysUserGradeDAO().save(sysUserGrade);
			}
		}
		return result;
	}

	/**
	 * 获得编辑用户的数量
	 * 
	 * @author Clark
	 * @date: 2014年6月22日 下午4:36:57 <br/>
	 * @param currentUser
	 * @return count
	 */
	@Override
	public int getEditorCount(SysUser currentUser) {
		// TODO Auto-generated method stub
		return getSysUserDAO().getSysUserCountByRole(currentUser, Dictionary.USER_ROLE_EDITOR);
	}

	/**
	 * 获取编辑用户的列表
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午7:53:47 <br/>
	 * @param currentUser
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<SysUser>
	 */
	@Override
	public List<SysUser> getEditors(SysUser currentUser, String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		return getSysUserDAO().getSysUsersByRole(currentUser, sorts, offset, pagesize, Dictionary.USER_ROLE_EDITOR);
	}

	/**
	 * 搜索符合条件的编辑数量
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午7:43:05 <br/>
	 * @param currentUser
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchEditorCount(SysUser currentUser, Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return getSysUserDAO().searchSysUserCount(currentUser, searchMap, Dictionary.USER_ROLE_EDITOR);
	}

	/**
	 * 搜索符合条件的编辑列表
	 * 
	 * @author Clark
	 * @param currentUser
	 * @date: 2014年7月20日 下午7:43:13 <br/>
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<SysUser>
	 */
	@Override
	public List<SysUser> searchEditor(SysUser currentUser, Map<String, Object> searchMap, String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		List<SysUser> result = getSysUserDAO().searchSysUser(currentUser, searchMap, sorts, offset, pagesize, Dictionary.USER_ROLE_EDITOR);
		return result;
	}

	/**
	 * 获得运营管理者用户的数量
	 * 
	 * @author Clark
	 * @date: 2014年6月22日 下午4:36:57 <br/>
	 * @param currentUser
	 * @return int
	 */
	@Override
	public int getManagerCount(SysUser currentUser) {
		// TODO Auto-generated method stub
		return getSysUserDAO().getSysUserCountByRole(currentUser, Dictionary.USER_ROLE_MANAGER);
	}

	/**
	 * 获取运营管理者的列表
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午7:53:47 <br/>
	 * @param currentUser
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<SysUser>
	 */
	@Override
	public List<SysUser> getManagers(SysUser currentUser, String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		return getSysUserDAO().getSysUsersByRole(currentUser, sorts, offset, pagesize, Dictionary.USER_ROLE_MANAGER);
	}

	/**
	 * 获得合作方用户的数量
	 * 
	 * @author Clark
	 * @date: 2014年6月22日 下午4:36:57 <br/>
	 * @param currentUser
	 * @return int
	 */
	@Override
	public int getParterCount(SysUser currentUser) {
		// TODO Auto-generated method stub
		int ex_managers_count = getSysUserDAO().getSysUserCountByRole(currentUser, Dictionary.USER_ROLE_EX_MANAGER);
		int ex_editors_count = getSysUserDAO().getSysUserCountByRole(currentUser, Dictionary.USER_ROLE_EX_EDITOR);
		return ex_managers_count + ex_editors_count;
	}

	/**
	 * 获取合作方用户的列表
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午7:53:47 <br/>
	 * @param currentUser
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<SysUser>
	 */
	@Override
	public List<SysUser> getParters(SysUser currentUser, String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		List<SysUser> result = new ArrayList<>();
		List<SysUser> ex_managers = getSysUserDAO().getSysUsersByRole(currentUser, sorts, offset, pagesize, Dictionary.USER_ROLE_EX_MANAGER);
		List<SysUser> ex_editors = getSysUserDAO().getSysUsersByRole(currentUser, sorts, offset, pagesize, Dictionary.USER_ROLE_EX_EDITOR);
		result.addAll(ex_managers);
		result.addAll(ex_editors);
		return result;
	}

	/**
	 * 搜索符合条件的运营管理者列表
	 * 
	 * @author Clark
	 * @date: 2014年7月21日 下午7:16:39 <br/>
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	@Override
	public List<SysUser> searchManager(SysUser currentUser, Map<String, Object> searchMap, String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		return getSysUserDAO().searchSysUser(currentUser, searchMap, sorts, offset, pagesize, Dictionary.USER_ROLE_MANAGER);
	}

	/**
	 * 搜索符合条件的运营管理者数量
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午7:43:05 <br/>
	 * @param currentUser
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchManagerCount(SysUser currentUser, Map<String, Object> searchMap) {
		return getSysUserDAO().searchSysUserCount(currentUser, searchMap, Dictionary.USER_ROLE_MANAGER);
	}

	/**
	 * 搜索符合条件的合作机构用户列表
	 * 
	 * @author Clark
	 * @date: 2014年7月21日 下午7:38:34 <br/>
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	@Override
	public List<SysUser> searchParter(SysUser currentUser, Map<String, Object> searchMap, String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		List<SysUser> result = new ArrayList<>();
		result = getSysUserDAO().searchSysUser(currentUser, searchMap, sorts, offset, pagesize, null);
		return result;
	}

	/**
	 * 搜索符合条件的合作机构用户数量
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午7:43:05 <br/>
	 * @param currentUser
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchParterCount(SysUser currentUser, Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		int ex_managers_count = getSysUserDAO().searchSysUserCount(currentUser, searchMap, Dictionary.USER_ROLE_EX_MANAGER);
		int ex_editors_count = getSysUserDAO().searchSysUserCount(currentUser, searchMap, Dictionary.USER_ROLE_EX_EDITOR);
		return ex_managers_count + ex_editors_count;
	}

	/**
	 * 用户是否有某学段的操作权限
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 上午11:35:18 <br/>
	 * @param currentUser
	 * @param grade
	 * @return boolean
	 */
	@Override
	public boolean isCanOpt(SysUser currentUser, Grade grade) {
		// TODO Auto-generated method stub

		List<SysUserGrade> sysUserGrades = this.getSysUserGradeDAO().getSysUserGrades(currentUser);
		if (sysUserGrades == null || sysUserGrades.size() == 0) {
			return true;
		}
		// 从首位包含字符串
		for (SysUserGrade sysUserGrade : sysUserGrades) {
			if (grade.getScode().indexOf(sysUserGrade.getGrade().getScode()) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用户是否有某学科的操作权限
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 上午11:35:18 <br/>
	 * @param currentUser
	 * @param grade
	 * @return boolean
	 */
	@Override
	public boolean isCanOpt(SysUser currentUser, Subject subject) {
		// TODO Auto-generated method stub
		List<SysUserSubject> sysUserSubjects = getSysUserSubjectDAO().getSysUserSubjects(currentUser);
		if (sysUserSubjects == null || sysUserSubjects.size() == 0) {
			return true;
		}
		for (SysUserSubject sus : sysUserSubjects) {
			if (sus.getSubject().getId() == subject.getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取用户的学科权限
	 * 
	 * @author Administrator
	 * @date: 2014年8月27日 上午10:30:26 <br/>
	 * @param currentUser
	 * @return
	 */
	@Override
	public List<Subject> getSubjectBySysuser(SysUser currentUser) {

		List<SysUserSubject> sysUserSubjects = getSysUserSubjectDAO().getSysUserSubjects(currentUser);

		if (sysUserSubjects == null || sysUserSubjects.size() == 0) {

			return null;

		} else {

			List<Subject> retSubjects = new ArrayList<Subject>();

			for (SysUserSubject sus : sysUserSubjects) {
				retSubjects.add(sus.getSubject());
			}

			return retSubjects;
		}
	}

	/**
	 * 获取用户学段权限
	 * 
	 * @author Administrator
	 * @date: 2014年8月27日 上午10:30:45 <br/>
	 * @param currentUser
	 * @return
	 */
	@Override
	public List<Grade> getGradeBySysuser(SysUser currentUser) {

		List<SysUserGrade> sysUserGrades = this.getSysUserGradeDAO().getSysUserGrades(currentUser);
		if (sysUserGrades == null || sysUserGrades.size() == 0) {
			return null;
		} else {
			List<Grade> retGrades = new ArrayList<Grade>();

			for (SysUserGrade sysUserGrade : sysUserGrades) {
				retGrades.add(sysUserGrade.getGrade());
			}
			return retGrades;
		}
	}

}
