package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ProjectApplyWorkReport;

@SuppressWarnings("rawtypes")
public interface IProjectApplyWorkReportService extends IBaseService<ProjectApplyWorkReport, Integer> {
	public int deleteByProjectApply(int id);
	public List<ProjectApplyWorkReport> getListByProjectApply(Integer projectApplyId);
	
	public int getWorkReportCountByParams(Map<String, Object> params,Map<String, String> sortParams);
	

	public List getWorkReportByParams(Map<String, Object> params,Map<String, String> sortParams, int offset, int length);
}
