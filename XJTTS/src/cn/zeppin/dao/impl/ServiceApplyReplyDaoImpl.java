package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IServiceApplyReplyDao;
import cn.zeppin.entity.ServiceApplyReply;

/**
 * 需要处理的下级申请
 */
public class ServiceApplyReplyDaoImpl extends BaseDaoImpl<ServiceApplyReply, Integer>
		implements IServiceApplyReplyDao {

	@Override
	public List<ServiceApplyReply> getReplyListByServiceApplyID(
			String serviceApplyId, int offset, int length) {
		StringBuilder builder = new StringBuilder();
		builder.append("from ServiceApplyReply sar where sar.serviceApply ="
				+ serviceApplyId);
		builder.append("order by sa.createtime desc");
		return this.getListForPage(builder.toString(), offset, length);
	}

	@Override
	public List<ServiceApplyReply> getReplyListByServiceApplyID(
			Map<String, Object> params, int offset, int length) {
		StringBuilder sb = new StringBuilder();
		sb.append(" from ServiceApplyReply sar where 1=1 ");
		if(params != null){
			if(params.containsKey("id")){
				sb.append(" and sar.id="+params.get("id"));
				
			}
			if(params.containsKey("mid")){
				sb.append(" and sar.serviceApply="+params.get("mid"));
				
			}
			if(params.containsKey("status")){
				sb.append(" and sar.status="+params.get("status"));
				
			}
			if(params.containsKey("creator")){
				sb.append(" and sar.creator="+params.get("creator"));
				
			}
			if(params.containsKey("role")){
				sb.append(" and sar.creatorRole="+params.get("role"));
			}
		}
		
		sb.append(" order by sar.createtime desc");
		
		// TODO Auto-generated method stub
		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getReplyCountByServiceApplyID(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from ServiceApplyReply sar where 1=1 ");
		if(params != null){
			if(params.containsKey("id")){
				sb.append(" and sar.id="+params.get("id"));
				
			}
			if(params.containsKey("mid")){
				sb.append(" and sar.serviceApply="+params.get("mid"));
				
			}
			if(params.containsKey("status")){
				sb.append(" and sar.status="+params.get("status"));
				
			}
			if(params.containsKey("creator")){
				sb.append(" and sar.creator="+params.get("creator"));
				
			}
			if(params.containsKey("role")){
				sb.append(" and sar.creatorRole="+params.get("role"));
			}
		}
		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}
}
