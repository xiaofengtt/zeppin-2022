/** 
 * Project Name:CETV_TEST 
 * File Name:IPaperDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Paper;

/**
 * ClassName: IPaperDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:42:59 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IPaperDAO extends IBaseDAO<Paper, Integer> {

	/**
	 * 试卷个数
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:45 <br/>
	 * @param searchMap
	 * @return
	 */
	public int searchPaperCount(Map<String, Object> searchMap);

	/**
	 * 试卷平均分
	 * 
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:45 <br/>
	 * @param paperId
	 * @return
	 */
	public float getPaperAverage(int paperId);
	
	/**
	 * 试卷列表
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:53 <br/>
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public List<Paper> searchPaper(Map<String, Object> searchMap, String sorts, int offset, int pagesize);
	
	public void updatePaper(Paper paper);
	
	/**
	 * 编辑试卷时更新试卷下所有试题
	 * 
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:45 <br/>
	 * @param paper
	 * @return
	 */
	public void updatePaperItem(Paper paper);

	
	/**
	 * 查询不同试卷类型学科试卷总数
	 * @param List<Map<String,Object>>
	 * @param searchMap
	 */
	public List<Map<String,Object>> searchPaperCountListByType(Map<String, Object> searchMap);

	/**
	 * 查询试卷某部分下的所有试题
	 * @param List<Map<String,Object>>
	 * @param sectionId
	 */
	public List<Map<String, Object>> searchTestPaperSectionItemMap(Integer sectionId);
}
