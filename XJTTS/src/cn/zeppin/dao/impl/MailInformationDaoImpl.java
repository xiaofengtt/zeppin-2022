package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IMailInformationDao;
import cn.zeppin.entity.MailInformation;

public class MailInformationDaoImpl extends
		BaseDaoImpl<MailInformation, Integer> implements IMailInformationDao {

	@Override
	public List<MailInformation> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from MailInformation mi where 1=1");
		if(params != null){
			if(params.containsKey("paId")){
				sb.append(" and mi.creator=");
				sb.append(params.get("paId"));
			}
			if(params.containsKey("role")){
				sb.append(" and mi.creatorRole=");
				sb.append(params.get("role"));
			}
			
			if(params.containsKey("status")){
				sb.append(" and mi.sendStatus=");
				sb.append(params.get("status"));
			}else{
				sb.append(" and mi.sendStatus>0");
			}
			
			if(params.containsKey("type")){
				sb.append(" and mi.type=");
				sb.append(params.get("type"));
			}
			
			if(params.containsKey("content")){
				sb.append(" and (mi.text like'%");
				sb.append(params.get("content"));
				sb.append("%' or mi.title like'%");
				sb.append(params.get("content"));
				sb.append("%')");
			}
		}
		
		sb.append("order by mi.");
		sb.append(sort);
		
		return this.getListForPage(sb.toString(), start, limit);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from MailInformation mi where 1=1");
		if(params != null){
			if(params.containsKey("paId")){
				sb.append(" and mi.creator=");
				sb.append(params.get("paId"));
			}
			if(params.containsKey("role")){
				sb.append(" and mi.creatorRole=");
				sb.append(params.get("role"));
			}
			
			if(params.containsKey("status")){
				sb.append(" and mi.sendStatus=");
				sb.append(params.get("status"));
			}else{
				sb.append(" and mi.sendStatus>0");
			}
			
			if(params.containsKey("type")){
				sb.append(" and mi.type=");
				sb.append(params.get("type"));
			}
			
			if(params.containsKey("content")){
				sb.append(" and (mi.text like'%");
				sb.append(params.get("content"));
				sb.append("%' or mi.title like'%");
				sb.append(params.get("content"));
				sb.append("%')");
			}
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

}
