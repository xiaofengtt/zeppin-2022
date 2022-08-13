/** 
 * Project Name:ItemDatabase 
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

}
