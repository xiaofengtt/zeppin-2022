/** 
 * Project Name:CETV_TEST 
 * File Name:ITestPaperItemDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TestPaperItem;

/**
 * ClassName: ITestPaperItemDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午10:18:19 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface ITestPaperItemDAO extends IBaseDAO<TestPaperItem, Integer> {
	
	/**
	 * 计算试卷题数个数
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public int searchTestPaperItemsCount(Map<String, Object> map);

	/**
	 * 计算试卷题数列表
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public List<TestPaperItem> searchTestPaperItems(Map<String, Object> map, String sorts, int offset, int length);

	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年9月24日 下午12:51:55 <br/> 
	 * @param map
	 */
	public void deleteTestPaperItems(Map<String, Object> map);
	
}
