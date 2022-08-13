/** 
 * Project Name:Self_Cool 
 * File Name:ResourceDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IAdvertDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Advert;

/**
 * ClassName: AdvertisementDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:44:20 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class AdvertDAO extends HibernateTemplateDAO<Advert, Integer> implements IAdvertDAO {
	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	public int getAdvertCountByParams(HashMap<String, Object> params) {
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from Advert a where 1=1 ");
		if (params.get("id") != null  && !params.get("id").equals("")){
			hql.append(" and a.id=").append(params.get("id"));
		}
		if (params.get("name") != null && !params.get("name").equals("")){
			hql.append(" and a.name like '%").append(params.get("name")).append("%' ");
		}
		if (params.get("sysUser.id") != null && !params.get("sysUser.id").equals("")){
			hql.append(" and a.sysUser.id=").append(params.get("sysUser.id"));
		}
		if (params.get("status") != null && !params.get("status").equals("")){
			hql.append(" and a.status=").append(params.get("status"));
		}
		Object result = this.getResultByHQL(hql.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}else{
			return 0;
		}
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
	public List<Advert> getAdvertListByParams(HashMap<String, Object> params, String sorts, int offset, int length) {

		StringBuilder hql = new StringBuilder();
		hql.append(" from Advert a where 1=1 ");
		if (params.get("id") != null  && !params.get("id").equals("")){
			hql.append(" and a.id=").append(params.get("id"));
		}
		if (params.get("name") != null && !params.get("name").equals("")){
			hql.append(" and a.name like '%").append(params.get("name")).append("%' ");
		}
		if (params.get("sysUser.id") != null && !params.get("sysUser.id").equals("")){
			hql.append(" and a.sysUser.id=").append(params.get("sysUser.id"));
		}
		if (params.get("status") != null && !params.get("status").equals("")){
			hql.append(" and a.status=").append(params.get("status"));
		}
		if (params.get("push") != null && !params.get("push").equals("")){
			hql.append(" and a.push=").append(params.get("push"));
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				hql.append(comma);
				hql.append("a.").append(sort);
				comma = ",";
			}
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

}
