package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IIdentifyClasshoursSubjectDao;
import cn.zeppin.entity.IdentifyClasshoursSubject;

public class IdentifyClasshoursSubjectDaoImpl extends
		BaseDaoImpl<IdentifyClasshoursSubject, Integer> implements
		IIdentifyClasshoursSubjectDao {

	@Override
	public List<IdentifyClasshoursSubject> getListByParams(
			Map<String, Object> params, int start, int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from IdentifyClasshoursSubject ic where 1=1");
		getHqlWhere(params, sb);

		sb.append("order by ic.");
		sb.append(sort);

		return this.getListForPage(sb.toString(), start, limit);
	}

	/**
	 * 条件
	 * 
	 * @param params
	 * @param sb
	 */
	private void getHqlWhere(Map<String, Object> params, StringBuilder sb) {
		if (params != null) {

			if (params.containsKey("projecttype")) {
				sb.append(" and ic.projecttype=");
				sb.append(params.get("projecttype"));
			}

			if (params.containsKey("trainingsubject")) {
				sb.append(" and ic.trainingsubject=");
				sb.append(params.get("trainingsubject"));
			}

			if (params.containsKey("status")) {
				sb.append(" and ic.status=");
				sb.append(params.get("status"));
			}

		}
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from IdentifyClasshoursSubject ic where 1=1");
		getHqlWhere(params, sb);
		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;
	}

}
