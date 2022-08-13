package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.ProjectApplyExpert;

public interface IProjectApplyExpertService extends IBaseService<ProjectApplyExpert, Integer> {
	public void deleteByProjectApply(int id);
	public int checkProjectApplyExpert(int paid,int expertid);
	public int getCountForExpert (int expertid);
	public ProjectApplyExpert getProjectApplyExpertByAll(int paid,int expertid);

}
