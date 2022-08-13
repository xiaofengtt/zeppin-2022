/** 
 * Project Name:Self_Cool  
 * File Name:IResourceDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Advert;

/**
 * ClassName: IAdvertisementDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:45:10 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IAdvertDAO extends IBaseDAO<Advert, Integer> {
	/**
	 * 获取分类个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	public int getAdvertCountByParams(HashMap<String, Object> params);

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
	public List<Advert> getAdvertListByParams(HashMap<String, Object> params, String sorts, int offset, int length);
	
}
