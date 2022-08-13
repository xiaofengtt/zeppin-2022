package cn.zeppin.dao.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IUserDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.User;

public class UserDAO extends HibernateTemplateDAO<User, Integer> implements IUserDAO{
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from User where 1=1");
		if (searchMap.get("phone") != null && !searchMap.get("phone").equals("")){
			sb.append(" and phone=").append(searchMap.get("phone"));
		}
		if (searchMap.get("role") != null && !searchMap.get("role").equals("")){
			sb.append(" and role=").append(searchMap.get("role"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			sb.append(" and status=").append(searchMap.get("status"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			sb.append(" and name like'%").append(searchMap.get("name")).append("%'");
		}
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}
	
	/**
	 * 通过参数取列表
	 */
	public List<User> getListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length){
		StringBuilder sb = new StringBuilder();
		sb.append("from User where 1=1");
		if (searchMap.get("phone") != null && !searchMap.get("phone").equals("")){
			sb.append(" and phone='").append(searchMap.get("phone")).append("'");
		}
		if (searchMap.get("role") != null && !searchMap.get("role").equals("")){
			sb.append(" and role=").append(searchMap.get("role"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			sb.append(" and status=").append(searchMap.get("status"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			sb.append(" and name like'%").append(searchMap.get("name")).append("%'");
		}
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append(" ").append(sort);
				comma = ",";
			}
		}
		if(offset!=null && length!=null){
			return this.getByHQL(sb.toString(), offset, length);
		}else{
			return this.getByHQL(sb.toString());
		}
	}
}
