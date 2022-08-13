/** 
 * Project Name:Self_Cool 
 * File Name:IRoleDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Retrieve;

/** 
 * ClassName: IRoleDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年6月10日 下午9:48:28 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface IRetrieveDAO extends IBaseDAO<Retrieve,Integer>{
	/**
	 * 获得分类科目检索范围的数量
	 * @author Clark
	 * @date: 2014年6月22日 下午4:36:57 <br/> 
	 * @param  searchMap
	 * @return int
	 */
	public int searchRetrieveCount(Map<String, Object> searchMap);

	/**
	 * 获取分类科目检索范围列表
	 * @author Clark
	 * @date: 2014年7月20日 下午7:53:47 <br/> 
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<Retrieve>
	 */
	public List<Retrieve> searchRetrieves(Map<String, Object> searchMap, String sorts, int offset, int pagesize);

}
