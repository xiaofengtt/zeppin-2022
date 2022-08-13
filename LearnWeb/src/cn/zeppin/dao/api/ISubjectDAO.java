/** 
 * Project Name:CETV_TEST 
 * File Name:ISubjectDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Subject;

/**
 * ClassName: ISubjectDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午10:07:10 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface ISubjectDAO extends IBaseDAO<Subject, Integer> {

	/**
	 * 获取全部Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:04:58 <br/>
	 * @return
	 */
	List<Subject> getAllSubject();
	
	/**
	 * 根据名称获取Subject
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:18:29 <br/> 
	 * @param name
	 * @return
	 */
	Subject getSubjectByName(String name);
	
	/**
	 * 获取学科Count
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:13 <br/> 
	 * @param params
	 * @return
	 */
	int getSubjectCountByParams(HashMap<String, Object> params);

	/**
	 * 获取学科列表
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:24 <br/> 
	 * @param params
	 * @return
	 */
	List<Subject> getSubjectByParams(HashMap<String, Object> params,int offset, int length);
	
}
