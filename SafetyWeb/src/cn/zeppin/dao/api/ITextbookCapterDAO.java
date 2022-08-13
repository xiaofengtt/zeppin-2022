/** 
 * Project Name:CETV_TEST 
 * File Name:ITextbookCapterDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.TextbookCapter;

/**
 * ClassName: ITextbookCapterDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午11:02:58 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface ITextbookCapterDAO extends IBaseDAO<TextbookCapter, Integer>
{
	
	/**
	 * @param offset
	 * @param pageSize
	 * @param sorts
	 * @param paras
	 * @return
	 * @author suijing 2014年8月1日 上午11:36:14
	 */
	List<TextbookCapter> getAllTextbookCapter(int offset, int pageSize, String sorts, HashMap<String, Object> paras);
	
	/**
	 * @param id
	 * @return
	 * @author suijing 2014年8月1日 上午11:37:45
	 */
	int getChild(Integer id);
	
	/**
	 * @param paras
	 * @return
	 * @author suijing 2014年8月1日 上午11:38:51
	 */
	int getCountByParas(HashMap<String, Object> paras);
	
}
