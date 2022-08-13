/** 
 * Project Name:Self_Cool 
 * File Name:CategoryService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IAdvertDAO;
import cn.zeppin.entity.Advert;
import cn.zeppin.service.api.IAdvertService;
import cn.zeppin.utility.Dictionary;

/**
 * ClassName: CategoryService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午4:42:39 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class AdvertService implements IAdvertService {

	private IAdvertDAO advertDAO;

	public IAdvertDAO getAdvertDAO() {
		return advertDAO;
	}

	public void setAdvertDAO(IAdvertDAO advertDAO) {
		this.advertDAO = advertDAO;
	}

	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param advert
	 */
	@Override
	public void addAdvert(Advert advert) {
		this.getAdvertDAO().save(advert);
	}
	
	/**
	 * 修改
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param advert
	 */
	@Override
	public void updateAdvert(Advert advert) {
		this.getAdvertDAO().update(advert);
	}

	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/> 
	 * @param advert
	 */
	@Override
	public void deleteAdvert(Advert advert) {
		advert.setStatus(Dictionary.ADVERT_STATUS_CLOSED);
		this.getAdvertDAO().update(advert);
	}

	/**
	 * 根据id获取
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:12:39 <br/>
	 * @param id
	 * @return
	 */
	@Override
	public Advert getAdvertById(Integer id) {
		return this.getAdvertDAO().get(id);
	}

	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public int getAdvertCountByParams(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getAdvertDAO().getAdvertCountByParams(params);
	}

	/**
	 * 获取分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	@Override
	public List<Advert> getAdvertListByParams(HashMap<String, Object> params, String sorts, int offset, int length) {
		// TODO Auto-generated method stub
		return this.getAdvertDAO().getAdvertListByParams(params, sorts, offset, length);
	}

}
