package cn.zeppin.dao;

import cn.zeppin.entity.ProjectApplyExpert;

public interface IProjectApplyExpertDao extends IBaseDao<ProjectApplyExpert, Integer> {
	public void deleteByProjectApply(int id);
	public int checkProjectApplyExpert(int paid,int expertid);
	public int getCountForExpert (int expertid);
	public ProjectApplyExpert getProjectApplyExpertByAll(int paid,int expertid);

}
