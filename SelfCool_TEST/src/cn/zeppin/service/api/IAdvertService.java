/** 
 * Project Name:Self_Cool 
 * File Name:ICategoryService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Advert;

/**
 * ClassName: IAdvertService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午4:42:39 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public interface IAdvertService {

	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param category
	 */
	public void addAdvert(Advert advert);

	/**
	 * 更新
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 上午11:27:43 <br/>
	 * @param category
	 */
	public void updateAdvert(Advert advert);

	/**
	 * 删除
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/>
	 * @param category
	 */
	public void deleteAdvert(Advert advert);

	/**
	 * 根据 id获取
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:12:39 <br/>
	 * @param id
	 * @return
	 */
	public Advert getAdvertById(Integer id);

	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param searchMap
	 * @return
	 */
	public int getAdvertCountByParams(HashMap<String, Object> searchMap);

	/**
	 * 获取分页列表
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
