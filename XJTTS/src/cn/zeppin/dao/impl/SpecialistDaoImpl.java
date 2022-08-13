package cn.zeppin.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.ISpecialistDao;
import cn.zeppin.entity.Specialist;

public class SpecialistDaoImpl extends BaseDaoImpl<Specialist, Integer> implements ISpecialistDao {
	public Integer getSpecialistCount(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Specialist where 1=1");
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")) {
			sb.append(" and name like '%").append(searchMap.get("name")).append("%'");
		}
		if (searchMap.get("idcard") != null && !searchMap.get("idcard").equals("")) {
			sb.append(" and idcard like '%").append(searchMap.get("idcard")).append("%'");
		}
		if (searchMap.get("organization") != null && !searchMap.get("organization").equals("0")) {
			sb.append(" and organization=").append(searchMap.get("organization"));
		}
		Object obj = this.getObjectBySql(sb.toString(), null);
		return Integer.valueOf(obj.toString());
	}
	
	public List<Specialist> getSpecialistList(HashMap<String,String> searchMap , String sortname,  String sorttype, int offset, int length){
		StringBuilder sb = new StringBuilder();
		sb.append("from Specialist where 1=1");
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")) {
			sb.append(" and name like '%").append(searchMap.get("name")).append("%'");
		}
		if (searchMap.get("idcard") != null && !searchMap.get("idcard").equals("")) {
			sb.append(" and idcard like '%").append(searchMap.get("idcard")).append("%'");
		}
		if (searchMap.get("organization") != null && !searchMap.get("organization").equals("0")) {
			sb.append(" and organization=").append(searchMap.get("organization"));
		}
		sb.append( " order by ").append(sortname).append(" ").append(sorttype);
		return getListForPage(sb.toString(), offset, length);
	}
	
	public int checkUserInfo(Object[] pars) {
		String sql = "select * from specialist t where 1=1 and (t.idcard=? or t.mobile=? or t.email=?)";

		Query query = this.getCurrentSession().createSQLQuery(sql);

		for (int i = 0; i < pars.length; i++) {
			query.setParameter(i, pars[i]);
		}
		List<?> li = query.list();

		if (li != null && li.size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
