package cn.zeppin.dao.imp;

import java.util.HashMap;

import cn.zeppin.dao.api.IUserDownloadResourceDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserDownloadResource;

public class UserDownloadResourceDAO extends HibernateTemplateDAO<UserDownloadResource, Integer> implements IUserDownloadResourceDAO{

	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from UserDownloadResource where 1=1");
		if (searchMap.get("resource") != null && !searchMap.get("resource").equals("")){
			sb.append(" and resource=").append(searchMap.get("resource"));
		}
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}

}