package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISsoUserPayDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.SsoUserPay;

public class SsoUserPayDAO extends HibernateTemplateDAO<SsoUserPay, Integer> implements ISsoUserPayDAO{

	
	public Integer searchSsoUserPaySubject(Map<String, Object> searchMap) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from SsoUserPay where paper is null ");
		
		if (searchMap.containsKey("user.id") && searchMap.get("user.id") != null) {
			sb.append(" and ssoUser = ").append(searchMap.get("user.id"));
		}
		
		if (searchMap.containsKey("subject.id") && searchMap.get("subject.id") != null) {
			sb.append(" and subject = ").append(searchMap.get("subject.id"));
		}
		
		Object result = this.getResultByHQL(sb.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		} else {
			return 0;
		}
	}

	
	public List<SsoUserPay> searchSsoUserPayPaper(Map<String, Object> searchMap) {
		StringBuilder sb = new StringBuilder();
		sb.append(" from SsoUserPay where 1=1 ");
		
		if (searchMap.containsKey("user.id") && searchMap.get("user.id") != null) {
			sb.append(" and ssoUser = ").append(searchMap.get("user.id"));
		}
		
		if (searchMap.containsKey("subject.id") && searchMap.get("subject.id") != null) {
			sb.append(" and subject = ").append(searchMap.get("subject.id"));
		}
		
		if (searchMap.containsKey("type") && searchMap.get("type") != null) {
			sb.append(" and paper.type = ").append(searchMap.get("type"));
		}
		
		return this.getByHQL(sb.toString());
	}

}
