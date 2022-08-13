package cn.zeppin.dao.imp;

import java.util.HashMap;

import cn.zeppin.dao.api.IUserLoveResourceDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserLoveResource;

public class UserLoveResourceDAO extends HibernateTemplateDAO<UserLoveResource, Integer> implements IUserLoveResourceDAO{

	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from UserLoveResource where 1=1");
		if (searchMap.get("resource") != null && !searchMap.get("resource").equals("")){
			sb.append(" and resource=").append(searchMap.get("resource"));
		}
		
		if (searchMap.get("user") != null && !searchMap.get("user").equals("")){
			sb.append(" and user=").append(searchMap.get("user"));
		}
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}

}