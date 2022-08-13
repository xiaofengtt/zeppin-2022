/** 
 * Project Name:ItemDatabase 
 * File Name:ICategoryDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Knowledge;

/**
 * ClassName: IKnowladgeDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:33:57 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IKnowledgeDAO extends IBaseDAO<Knowledge, Integer> {

	/**
	 * 按条件查询知识点数量
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:07 <br/> 
	 * @param searchMap
	 * @return count
	 */
	public int searchKnowledgeCount(Map<String, Object> searchMap);

	/**
	 * 按条件查询知识点列表
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:44 <br/> 
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<Knowledge>
	 */
	public List<Knowledge> searchKnowledge(Map<String, Object> searchMap,
			String sorts, int offset, int length);

}
