/** 
 * Project Name:ItemDatabase 
 * File Name:IPublisherDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Publisher;

/**
 * ClassName: IPublisherDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午11:04:58 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IPublisherDAO extends IBaseDAO<Publisher, Integer>
{
	boolean isExist(String name , String fcode , Integer id);
	
	Integer getPublisherCount(Map<String, Object> searchMap);
	
	List<Publisher> getPublisherList(Map<String, Object> searchMap , String sorts, int offset, int pagesize);
}
