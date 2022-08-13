/** 
 * Project Name:Self_Cool 
 * File Name:SubjectService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.ISubjectDAO;
import cn.zeppin.entity.Subject;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.utility.Dictionary;

/**
 * ClassName: ISubjectService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月20日 下午5:04:08 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class SubjectService implements ISubjectService {

	private ISubjectDAO subjectDAO;

	public ISubjectDAO getSubjectDAO() {
		return subjectDAO;
	}

	public void setSubjectDAO(ISubjectDAO subjectDAO) {
		this.subjectDAO = subjectDAO;
	}

	/**
	 * 获取全部Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:04:58 <br/>
	 * @return
	 */
	public List<Subject> getAllSubject() {
		return this.getSubjectDAO().getAllSubject();
	}

	/**
	 * 根据id来获取Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:04:58 <br/>
	 * @param id
	 * @return
	 */
	@Override
	public Subject getSubjectById(int id) {
		return this.getSubjectDAO().get(id);
	}

	/**
	 * 根据Name来获取Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:05:22 <br/>
	 * @param name
	 * @return
	 */
	@Override
	public Subject getSubjectByName(String name) {
		// TODO Auto-generated method stub
		return this.getSubjectDAO().getSubjectByName(name);
	}

	/**
	 * 添加学科
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:11:15 <br/>
	 * @param subject
	 */
	@Override
	public void addSubject(Subject subject) {
		this.getSubjectDAO().save(subject);
	}
	
	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/> 
	 * @param subject
	 */
	@Override
	public void deleteSubject(Subject subject) {
		subject.setStatus(Dictionary.SUBJECT_STATUS_CLOSED);
		this.getSubjectDAO().update(subject);
	}
	
	/**
	 * 更新 学科
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:09:46 <br/>
	 * @param subject
	 */
	@Override
	public void updateSubject(Subject subject) {
		this.getSubjectDAO().update(subject);
	}

	/**
	 * 获取学科Count
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:13 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public int getSubjectCountByParams(HashMap<String, Object> params) {
		return this.getSubjectDAO().getSubjectCountByParams(params);
	}

	/**
	 * 获取学科列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:24 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public List<Subject> getSubjectByParams(HashMap<String, Object> params, String sort ,int offset, int length) {
		return this.getSubjectDAO().getSubjectByParams(params,sort, offset, length);
	}

}
