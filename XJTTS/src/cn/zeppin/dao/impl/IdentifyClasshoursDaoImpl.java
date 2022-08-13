package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IIdentifyClasshoursDao;
import cn.zeppin.entity.IdentifyClasshours;

public class IdentifyClasshoursDaoImpl extends
		BaseDaoImpl<IdentifyClasshours, Integer> implements IIdentifyClasshoursDao {

	@Override
	public List<IdentifyClasshours> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from IdentifyClasshours ic where 1=1");
		if(params != null){
			
			if(params.containsKey("projecttype")){
				sb.append(" and ic.projecttype=");
				sb.append(params.get("projecttype"));
			}
			
			if(params.containsKey("status")){
				sb.append(" and ic.status=");
				sb.append(params.get("status"));
			}
			
		}
		
		sb.append("order by ic.");
		sb.append(sort);
		
		return this.getListForPage(sb.toString(), start, limit);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from IdentifyClasshours ic where 1=1");
		if(params != null){
			if(params.containsKey("projecttype")){
				sb.append(" and ic.projecttype=");
				sb.append(params.get("projecttype"));
			}
			
			if(params.containsKey("status")){
				sb.append(" and ic.status=");
				sb.append(params.get("status"));
			}
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

}
