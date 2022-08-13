package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import cn.zeppin.dao.IOrgaCateMapDao;
import cn.zeppin.entity.OrgaCateMap;

public class OrgaCateMapDaoImpl extends BaseDaoImpl<OrgaCateMap, Integer> implements IOrgaCateMapDao {

	/* (non-Javadoc)
	 * @see cn.zeppin.dao.IOrgaCateMapDao#findByRoleId(short, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrgaCateMap> findByRoleId(short roleId, int level) {
		// TODO Auto-generated method stub
		String hql = "from OrgaCateMap t where 1=1 and t.status=1 and t.roleid=?";

		if (level == 0) {

		} else {
			hql += " and t.organizationLevel=" + level;
		}

		hql += " order by t.funCategory.weight";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter(0, roleId);

		return query.list();
	}

	@Override
	public List<OrgaCateMap> getListByParams(Map<String, Object> params,
			String sort, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from OrgaCateMap ocm where 1=1");
		if(params != null){
			if(params.containsKey("status") && params.get("status") != null){
				if(Integer.parseInt(params.get("status").toString()) == -2){
					//查看全部
				}else{
					sb.append(" and ocm.status=");
					sb.append(params.get("status"));
				}
				
			}else{
				sb.append(" and ocm.status>-1");
			}
			
			if(params.containsKey("role") && params.get("role") != null){
				sb.append(" and ocm.roleid=");
				sb.append(params.get("role"));
			}
			
			if(params.containsKey("level") && params.get("level") != null){
				if(Integer.parseInt(params.get("level").toString())>0){
					sb.append(" and ocm.organizationLevel=");
					sb.append(params.get("level"));
				}
			}
			
			if(params.containsKey("funCategory") && params.get("funCategory") != null){
				sb.append(" and ocm.funCategory=");
				sb.append(params.get("funCategory"));
			}
			
			if(params.containsKey("parent") && params.get("parent") != null){
				sb.append(" and ocm.funCategory.funCategory=");
				sb.append(params.get("parent"));
			}
		}
		
		if(sort != null){
			sb.append(" order by ocm.");
			sb.append(sort);
		}
		
		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from OrgaCateMap ocm where 1=1");
		if(params != null){
			if(params.containsKey("status") && params.get("status") != null){
				sb.append(" and ocm.status=");
				sb.append(params.get("status"));
			}else{
				sb.append(" and ocm.status>-1");
			}
			
			if(params.containsKey("role") && params.get("role") != null){
				sb.append(" and ocm.role=");
				sb.append(params.get("role"));
			}
			
			if(params.containsKey("level") && params.get("level") != null){
				sb.append(" and ocm.organizationLevel=");
				sb.append(params.get("level"));
			}
			
			if(params.containsKey("funCategory") && params.get("funCategory") != null){
				sb.append(" and ocm.funCategory=");
				sb.append(params.get("funCategory"));
			}
			
			if(params.containsKey("parent") && params.get("parent") != null){
				sb.append(" and ocm.funCategory.funCategory=");
				sb.append(params.get("parent"));
			}
		}
		
		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}

}
