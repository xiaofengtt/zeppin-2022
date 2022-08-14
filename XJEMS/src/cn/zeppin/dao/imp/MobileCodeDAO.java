package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IMobileCodeDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.MobileCode;

public class MobileCodeDAO extends HibernateTemplateDAO<MobileCode, Integer>
		implements IMobileCodeDAO {

	@Override
	public MobileCode getRecordByUuid(String uuid) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		
		hql.append(" from MobileCode mc where 1 = 1 ");
		if(!"".equals(uuid) && uuid != null){
			hql.append(" and mc.uuid = '");
			hql.append(uuid + "'");
		}
		
		List<MobileCode> list = this.getByHQL(hql.toString());
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int getMobileCodeCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
		StringBuilder hql = new StringBuilder();
		
		hql.append("select count(*) from MobileCode mc where 1 = 1 ");
		
		if(params.get("user") != null && !"".equals(params.get("user"))){
			hql.append(" and mc.user = ");
			hql.append(params.get("user"));
		}
		
		if(params.get("mobile") != null && !"".equals(params.get("mobile"))){
			hql.append(" and mc.mobile = '");
			hql.append(params.get("mobile")+"'");
		}
		
		if(params.get("uuid") != null && !"".equals(params.get("uuid"))){
			hql.append(" and mc.uuid = '");
			hql.append(params.get("uuid") + "'");
		}
		
		if(params.get("status") != null && !"".equals(params.get("status"))){
			hql.append(" and mc.status = ");
			hql.append(params.get("status"));
		}
		
		if(params.get("starttime") != null && !"".equals(params.get("starttime"))){
			hql.append(" and mc.createtime > '").append(params.get("starttime")).append("'");
		}
		
		Object result = this.getResultByHQL(hql.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}else{
			return 0;
		}
	}

	@Override
	public List<MobileCode> getMobileCodeByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
		StringBuilder hql = new StringBuilder();
		
		hql.append(" from MobileCode mc where 1 = 1 ");
		
		if(params.get("user") != null && !"".equals(params.get("user"))){
			hql.append(" and mc.user = ");
			hql.append(params.get("user"));
		}
		
		if(params.get("mobile") != null && !"".equals(params.get("mobile"))){
			hql.append(" and mc.mobile = '");
			hql.append(params.get("mobile")+"'");
		}
		
		if(params.get("uuid") != null && !"".equals(params.get("uuid"))){
			hql.append(" and mc.uuid = '");
			hql.append(params.get("uuid") + "'");
		}
		
		if(params.get("status") != null && !"".equals(params.get("status"))){
			hql.append(" and mc.status = ");
			hql.append(params.get("status"));
		}
		
		if(params.get("starttime") != null && !"".equals(params.get("starttime"))){
			hql.append(" and mc.createtime > '").append(params.get("starttime")).append("'");
		}
		return this.getByHQL(hql.toString());
	}

}
