/** 
 * Project Name:CETV_TEST 
 * File Name:ITextbookDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Textbook;

/**
 * ClassName: ITextbookDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午11:04:58 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface ITextbookDAO extends IBaseDAO<Textbook, Integer>
{
	
	/**
	 * @param offset
	 * @param pageSize
	 * @param sorts
	 * @param isAdmin
	 * @param paras
	 * @return
	 * @author suijing 2014年7月30日 下午4:31:18
	 */
	List<Textbook> getAllTextBooks(int offset, int pageSize, String sorts, boolean isAdmin, HashMap<String, Object> paras);
	
	/**
	 * @param paras
	 * @return
	 * @author suijing 2014年7月30日 下午4:14:57
	 */
	List<Textbook> getTextbookByParam(Map<String, Object> searchMap);
	
	/**
	 * @param paras
	 * @param isAdmin
	 * @return
	 * @author suijing 2014年7月30日 下午4:31:25
	 */
	int getCountByParas(HashMap<String, Object> paras, boolean isAdmin);
	
	/**
	 * @param name
	 * @return
	 * @author suijing 2014年7月30日 下午5:54:30
	 */
	int getCountByName(String name, Integer id);
	
}
