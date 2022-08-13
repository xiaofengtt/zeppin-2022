/** 
 * Project Name:CETV_TEST 
 * File Name:ITestDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.List;

import cn.zeppin.entity.Test;

/** 
 * ClassName: ITestDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年6月10日 下午10:12:15 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface ITestDAO {
	
	public Test save(Test test);
	
	public Test delete(Test test);
	
	public Test update(Test test);
	
	public Test merge(Test test);
	
	public Test get(Integer id);
	
	public Test load(Integer id);
	
	public List<Test> getAll();

}
