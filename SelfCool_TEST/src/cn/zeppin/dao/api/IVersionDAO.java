/** 
 * Project Name:Self_Cool 
 * File Name:IRoleDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.Map;

import cn.zeppin.entity.Version;

/** 
 * ClassName: IVersionDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2015年6月10日 下午9:48:28 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface IVersionDAO extends IBaseDAO<Version,Integer>{
	Version getVersionByParams(Map<String,Object> map);
}
