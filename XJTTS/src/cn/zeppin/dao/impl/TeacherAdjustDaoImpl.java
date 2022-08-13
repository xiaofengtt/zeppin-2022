package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherAdjustDao;
import cn.zeppin.entity.TeacherAdjust;
@SuppressWarnings("rawtypes")
public class TeacherAdjustDaoImpl extends BaseDaoImpl<TeacherAdjust, Integer> implements
		ITeacherAdjustDao {

	@Override
	public List getRecordsListByParams(Map<String, Object> params,
			Map<String, Object> sortMap, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		sb.append(" from TeacherAdjust ttre,Teacher t,Organization o where 1=1 and ttre.teacher = t.id and ttre.oorganization=o.id ");
		
		
		if(params != null && params.size()>0){
			if(params.containsKey("teacher") && params.get("teacher") != null){
				sb.append(" and (t.name like'%" + params.get("teacher") + "%' or t.idcard like'%" + params.get("teacher") + "%') ");
			}
			if(params.containsKey("oldOrg") && params.get("oldOrg") != null && Integer.parseInt(params.get("oldOrg").toString()) > 0){
				sb.append(" and (ttre.oorganization=");
				sb.append(params.get("oldOrg"));
//				sb.append(" or ttre.norganization=");
//				sb.append(params.get("oldOrg"));
				sb.append(")");
			}
			if(params.containsKey("newOrg") && params.get("newOrg") != null && Integer.parseInt(params.get("newOrg").toString()) > 0){
				sb.append(" and ttre.oorganization=");
				sb.append(params.get("newOrg"));
			}
			if(params.containsKey("status") && params.get("status") != null && Integer.parseInt(params.get("status").toString()) > -1){
				sb.append(" and ttre.status=");
				sb.append(params.get("status"));
			}
			
			if(params.containsKey("organization") && params.get("organization") != null){
//				sb.append(" and (ttre.oorganization in (");
//				sb.append(params.get("organization"));
//				sb.append(") or ttre.norganization in (");
//				sb.append(params.get("organization"));
//				sb.append("))");
				sb.append(" and (o.scode like '");
				sb.append(params.get("organization"));
				sb.append("%')");
//				sb.append(" and (o.isschool=1 or oo.isschool=1)");
			}
		}
		
		if (sortMap != null && sortMap.size() > 0) {
			sb.append(" order by ");
			for (String key : sortMap.keySet()) {
				String value = (String)sortMap.get(key);
				sb.append("ttre." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		
		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getRecordsListByParams(Map<String, Object> params,
			Map<String, Object> sortMap) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		sb.append("select count(*) from TeacherAdjust ttre,Teacher t,Organization o where 1=1 and ttre.teacher = t.id and ttre.oorganization=o.id ");
		
		
		if(params != null && params.size()>0){
			if(params.containsKey("teacher") && params.get("teacher") != null){
				sb.append(" and (t.name like'%" + params.get("teacher") + "%' or t.idcard like'%" + params.get("teacher") + "%') ");
			}
			if(params.containsKey("oldOrg") && params.get("oldOrg") != null && Integer.parseInt(params.get("oldOrg").toString()) > 0){
				sb.append(" and (ttre.oorganization=");
				sb.append(params.get("oldOrg"));
//				sb.append(" or ttre.norganization=");
//				sb.append(params.get("oldOrg"));
				sb.append(")");
			}
			if(params.containsKey("newOrg") && params.get("newOrg") != null && Integer.parseInt(params.get("newOrg").toString()) > 0){
				sb.append(" and ttre.oorganization=");
				sb.append(params.get("newOrg"));
			}
			if(params.containsKey("status") && params.get("status") != null && Integer.parseInt(params.get("status").toString()) > -1){
				sb.append(" and ttre.status=");
				sb.append(params.get("status"));
			}
			
			if(params.containsKey("organization") && params.get("organization") != null){
//				sb.append(" and (ttre.oorganization in (");
//				sb.append(params.get("organization"));
//				sb.append(") or ttre.norganization in (");
//				sb.append(params.get("organization"));
//				sb.append("))");
				sb.append(" and (o.scode like '");
				sb.append(params.get("organization"));
				sb.append("%')");
//				sb.append(" and (o.isschool=1 or oo.isschool=1)");
			}
		}
		
		if (sortMap != null && sortMap.size() > 0) {
			sb.append(" order by ");
			for (String key : sortMap.keySet()) {
				String value = (String)sortMap.get(key);
				sb.append("ttre." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

}
