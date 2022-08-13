/** 
 * Project Name:Self_Cool 
 * File Name:ISubjectService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Subject;

/**
 * ClassName: ISubjectService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月20日 下午5:04:08 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public interface ISubjectService {

	/**
	 * 根据id来获取Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:04:58 <br/>
	 * @param id
	 * @return
	 */
	Subject getSubjectById(int id);

	/**
	 * 获取全部Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:04:58 <br/>
	 * @return
	 */
	List<Subject> getAllSubject();

	/**
	 * 根据Name来获取Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:05:22 <br/>
	 * @param name
	 * @return
	 */
	Subject getSubjectByName(String name);

	/**
	 * 添加学科
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:11:15 <br/>
	 * @param subject
	 */
	void addSubject(Subject subject);

	/**
	 * 删除
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/>
	 * @param category
	 */
	public void deleteSubject(Subject subject);
	
	/**
	 * 更新 学科
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:09:46 <br/>
	 * @param subject
	 */
	void updateSubject(Subject subject);

	/**
	 * 获取学科Count
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:13 <br/>
	 * @param params
	 * @return
	 */
	int getSubjectCountByParams(HashMap<String, Object> params);

	/**
	 * 获取学科列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:24 <br/>
	 * @param params
	 * @return
	 */
	List<Subject> getSubjectByParams(HashMap<String, Object> params,String sorts, int offset, int length);


}
