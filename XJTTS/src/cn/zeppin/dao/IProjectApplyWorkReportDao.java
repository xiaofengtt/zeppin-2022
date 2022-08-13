package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ProjectApplyWorkReport;

@SuppressWarnings("rawtypes")
public interface IProjectApplyWorkReportDao extends IBaseDao<ProjectApplyWorkReport, Integer> {
	public List<ProjectApplyWorkReport> getListByProjectApply(Integer projectApplyId);
	
	public int getWorkReportCountByParams(Map<String, Object> params,Map<String, String> sortParams);
	
	public List getWorkReportByParams(Map<String, Object> params,Map<String, String> sortParams, int offset, int length);
	
}
