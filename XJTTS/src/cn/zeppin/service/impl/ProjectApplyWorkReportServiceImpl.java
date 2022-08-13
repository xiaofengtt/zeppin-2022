package cn.zeppin.service.impl;

import java.util.List;

import java.util.Map;

import cn.zeppin.dao.IProjectApplyWorkReportDao;
import cn.zeppin.entity.ProjectApplyWorkReport;
import cn.zeppin.service.IProjectApplyWorkReportService;

@SuppressWarnings("rawtypes")
public class ProjectApplyWorkReportServiceImpl extends BaseServiceImpl<ProjectApplyWorkReport, Integer> implements IProjectApplyWorkReportService {

	private IProjectApplyWorkReportDao ProjectApplyWorkReportDao;

	public IProjectApplyWorkReportDao getProjectApplyWorkReportDao() {
		return ProjectApplyWorkReportDao;
	}

	public void setProjectApplyWorkReportDao(IProjectApplyWorkReportDao ProjectApplyWorkReportDao) {
		this.ProjectApplyWorkReportDao = ProjectApplyWorkReportDao;
	}

	@Override
	public ProjectApplyWorkReport add(ProjectApplyWorkReport t) {
		return ProjectApplyWorkReportDao.add(t);
	}

	@Override
	public ProjectApplyWorkReport update(ProjectApplyWorkReport t) {
		return ProjectApplyWorkReportDao.update(t);
	}

	@Override
	public void delete(ProjectApplyWorkReport t) {
		ProjectApplyWorkReportDao.delete(t);
	}

	@Override
	public ProjectApplyWorkReport get(Integer id) {
		return ProjectApplyWorkReportDao.get(id);
	}

	@Override
	public List<ProjectApplyWorkReport> findAll() {
		return ProjectApplyWorkReportDao.findAll();
	}

	@Override
	public int deleteByProjectApply(int id) {
		try {
			String hql = "delete ProjectApplyWorkReport t where t.projectApply=" + id;
			this.executeHSQL(hql);
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public List<ProjectApplyWorkReport> getListByProjectApply(Integer projectApplyId){
		return this.ProjectApplyWorkReportDao.getListByProjectApply(projectApplyId);
	}

	@Override
	public int getWorkReportCountByParams(Map<String, Object> params,
			Map<String, String> sortParams) {
		// TODO Auto-generated method stub
		return this.ProjectApplyWorkReportDao.getWorkReportCountByParams(params, sortParams);
	}

	@Override
	public List getWorkReportByParams(Map<String, Object> params,
			Map<String, String> sortParams, int offset, int length) {
		// TODO Auto-generated method stub
		return this.ProjectApplyWorkReportDao.getWorkReportByParams(params, sortParams, offset, length);
	}
}
