package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IMailConnectionDao;
import cn.zeppin.entity.MailConnection;

@SuppressWarnings({ "rawtypes" })
public class MailConnectionDaoImpl extends BaseDaoImpl<MailConnection, Integer>
		implements IMailConnectionDao {

	@Override
	public List getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from MailConnection mc, MailInformation mi where 1=1 and mc.mailInformation=mi.id");
		getHqlWhere(params, sb);

		sb.append("order by mi.");
		sb.append(sort);

		return this.getListForPage(sb.toString(), start, limit);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from MailConnection mc, MailInformation mi where 1=1 and mc.mailInformation=mi.id");
		getHqlWhere(params, sb);

		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;
	}

	private void getHqlWhere(Map<String, Object> params, StringBuilder sb) {
		if (params != null) {
			if (params.containsKey("paId")) {
				sb.append(" and mc.addressee=");
				sb.append(params.get("paId"));
			}
			if (params.containsKey("role")) {
				sb.append(" and mc.addresseeRole=");
				sb.append(params.get("role"));
			}

			if (params.containsKey("status")) {
				sb.append(" and mc.status=");
				sb.append(params.get("status"));
			} else {
				sb.append(" and mc.status>0");//0-未发送 1-未读 2-已读 -1-删除
			}
			if (params.containsKey("content")) {
				sb.append(" and (mi.text like'%");
				sb.append(params.get("content"));
				sb.append("%' or mi.title like'%");
				sb.append(params.get("content"));
				sb.append("%')");
			}
			if (params.containsKey("creator")) {
				sb.append(" and mi.creator=");
				sb.append(params.get("creator"));
			}
			if (params.containsKey("creatorRole")) {
				sb.append(" and mi.creatorRole=");
				sb.append(params.get("creatorRole"));
			}

			if (params.containsKey("status")) {
				sb.append(" and mi.sendStatus=");
				sb.append(params.get("status"));
			} else {
				sb.append(" and mi.sendStatus>0");
			}

			if (params.containsKey("type")) {
				sb.append(" and mi.type=");
				sb.append(params.get("type"));
			}

//			sb.append(" and mi.type in(1,3) ");
		}
	}

}
