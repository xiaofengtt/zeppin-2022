/** 
 * Project Name:CETV_TEST 
 * File Name:ITestPaperSectionDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TestPaperSection;

/**
 * ClassName: ITestPaperSectionDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午11:00:30 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface ITestPaperSectionDAO extends IBaseDAO<TestPaperSection, Integer> {

	/**
	 * 计算试卷目录个数
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public int searchTestPaperSectionsCount(Map<String, Object> map);

	/**
	 * 计算试卷目录列表
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public List<TestPaperSection> searchTestPaperSections(Map<String, Object> map,String sorts,int offset,int length);

}
