/** 
 * Project Name:Self_Cool 
 * File Name:IRoleDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;
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
	/**
	 * 获取分类个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	public int getVersionCountByParams(HashMap<String, Object> params);

	/**
	 * 获取分类分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param sorts 
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Version> getVersionListByParams(HashMap<String, Object> params, String sorts, int offset, int length);
	
	public Version getVersionByParams(Map<String,Object> map);
	
	public List<Version> getNewVersions(Map<String, Object> map);
}
