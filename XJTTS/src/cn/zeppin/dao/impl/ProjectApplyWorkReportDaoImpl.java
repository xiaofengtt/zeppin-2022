package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IProjectApplyWorkReportDao;
import cn.zeppin.entity.ProjectApplyWorkReport;

@SuppressWarnings("rawtypes")
public class ProjectApplyWorkReportDaoImpl extends BaseDaoImpl<ProjectApplyWorkReport, Integer> implements IProjectApplyWorkReportDao {
	public List<ProjectApplyWorkReport> getListByProjectApply(Integer projectApplyId) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectApplyWorkReport where projectApply=").append(projectApplyId);
		return this.getListByHSQL(sb.toString());
	}

	@Override
	public int getWorkReportCountByParams(Map<String, Object> params,
			Map<String, String> sortParams) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(distinct d.title) from ProjectApplyWorkReport t,ProjectApply pa,Project p,Document d where t.projectApply=pa.id and pa.project=p.id and t.document=d.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append(" and pa.project = " + params.get("projectId"));
			}
			if(params.get("titles") != null){
				sb.append(" and d.title like '%" + params.get("titles") + "%'");
			}
			if(params.get("status") != null){
				sb.append(" and pa.status = " + params.get("status"));
				sb.append(" and p.status = " + params.get("status"));
			}
			if(params.get("trainingCollege") != null){
				sb.append(" and pa.trainingCollege = " + params.get("trainingCollege"));
			}
			
			sb.append(" and p.projectType.projectLevel<3");
			if(params.get("projecttype") != null){
				sb.append(" and p.projectType.scode like '" + params.get("projecttype")+"%'");
			}
		}
		
//		sb.append(" group by d.title");
//				if(params.get("group") != null){
//					sb.append(" group by " + params.get("group"));
//				}
		sb.append(" order by d.createtime desc");
		


		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}

	@Override
	public List getWorkReportByParams(Map<String, Object> params,
			Map<String, String> sortParams, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select d.id,d.title,d.resourcePath,d.createtime from ProjectApplyWorkReport t,ProjectApply pa,Project p,Document d where t.projectApply=pa.id and pa.project=p.id and t.document=d.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append(" and pa.project = " + params.get("projectId"));
			}
			if(params.get("titles") != null){
				sb.append(" and d.title like '%" + params.get("titles") + "%'");
			}
			if(params.get("status") != null){
				sb.append(" and pa.status = " + params.get("status"));
				sb.append(" and p.status = " + params.get("status"));
			}
			if(params.get("trainingCollege") != null){
				sb.append(" and pa.trainingCollege = " + params.get("trainingCollege"));
			}
			
			sb.append(" and p.projectType.projectLevel<3");
			if(params.get("projecttype") != null){
				sb.append(" and p.projectType.scode like '" + params.get("projecttype")+"%'");
			}
		}
		
		sb.append(" group by d.title");
//		if(params.get("group") != null){
//			sb.append(" group by " + params.get("group"));
//		}
		sb.append(" order by d.createtime desc");
		
//		Query query = getCurrentSession().createQuery(sb.toString());
//		
//		List result = query.list();
//		return result;
		return getListForPage(sb.toString(), offset, length);
	}
}
