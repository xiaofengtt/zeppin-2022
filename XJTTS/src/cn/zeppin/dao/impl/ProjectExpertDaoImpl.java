package cn.zeppin.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.IProjectExpertDao;
import cn.zeppin.entity.ProjectExpert;

public class ProjectExpertDaoImpl extends BaseDaoImpl<ProjectExpert, Integer> implements IProjectExpertDao {

	public int getProjectExpertCount(String searchName, String searchType, String sortName, String sortType){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from ProjectExpert t where 1=1 ");
		if (searchName != null && searchName.trim().length() > 0 && searchType != null && searchType.trim().length() > 0) {
			sb.append(" and (t." + searchType + " like '%" + searchName + "%') ");
		}
		if (sortName != null && !sortName.equals("") && sortType != null && !sortType.equals("")) {
			sb.append(" order by t." + sortName + " " + sortType);
		}

		Object obj = this.getObjectByHql(sb.toString(), null);
		return Integer.valueOf(obj.toString());
	}
	
	public List getProjectExpert(String searchName, String searchType, String sortName, String sortType,int offset, int length) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectExpert t where 1=1 ");
		if (searchName != null && searchName.trim().length() > 0 && searchType != null && searchType.trim().length() > 0) {			
			sb.append(" and (t." + searchType + " like '%" + searchName + "%') ");
		}
		if (sortName != null && !sortName.equals("") && sortType != null && !sortType.equals("")) {
			sb.append(" order by t." + sortName + " " + sortType);
		}

		return this.getListForPageByParams(sb.toString(), offset, length, null);
	}

	public List<ProjectExpert> getProjectExpertList(){
		String hql="from ProjectExpert where status = 1";
		List<ProjectExpert> list=this.getListByHSQL(hql);
		return list;
	}
	
	public int checkUserInfo(Object[] pars) {
		String sql = "select * from sys_user t where 1=1 and (t.idcard=? or t.mobile=? or t.email=?) and t.role=4 ";

		Query query = this.getCurrentSession().createSQLQuery(sql);

		for (int i = 0; i < pars.length; i++) {
			query.setParameter(i, pars[i]);
		}
		List li = query.list();

		if (li != null && li.size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	public Integer getExpertByPapperCount(Integer pid , Integer tsid , Integer tcid){
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from project_expert pe , project_apply_expert pae , project_apply pa where pe.id = pae.project_expert and pae.project_apply = pa.id ");
		sb.append(" and pa.proejct=").append(pid);
		sb.append(" and pa.subject=").append(tsid);
		sb.append(" and pa.training_college=").append(tcid);
		Object obj = this.getObjectBySql(sb.toString(), null);
		return Integer.valueOf(obj.toString());
	}
	
	public List<ProjectExpert> getProjectExpertListByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectExpert t where 1=1");
		if(searchMap!=null){
			if (searchMap.get("ids")!=null && !searchMap.get("ids").equals("")){
				sb.append(" and t.id in (").append(searchMap.get("ids")).append(")");
			}
		}
		List<ProjectExpert> list=this.getListByHSQL(sb.toString());
		return list;
	}
}
