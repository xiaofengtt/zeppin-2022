package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IIdentifyClasshoursSubjectYearDao;
import cn.zeppin.entity.IdentifyClasshoursSubjectYear;

public class IdentifyClasshoursSubjectYearDaoImpl extends
		BaseDaoImpl<IdentifyClasshoursSubjectYear, Integer> implements
		IIdentifyClasshoursSubjectYearDao {

	@Override
	public List<IdentifyClasshoursSubjectYear> getListByParams(
			Map<String, Object> params, int start, int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from IdentifyClasshoursSubjectYear ic where 1=1");
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

			if (params.containsKey("year")) {
				sb.append(" and ic.year='");
				sb.append(params.get("year")+"'");
			}

		}
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from IdentifyClasshoursSubjectYear ic where 1=1");
		getHqlWhere(params, sb);
		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;
	}

}
