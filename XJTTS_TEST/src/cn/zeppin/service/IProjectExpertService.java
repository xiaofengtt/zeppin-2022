package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.ProjectExpert;

public interface IProjectExpertService extends IBaseService<ProjectExpert, Integer> {
	
	public int checkUserInfo(Object[] pars);
	
	public int getProjectExpertCount(String searchName, String searchType, String sortName, String sortType);
	
	public List getProjectExpert(String searchName, String searchType, String sortName, String sortType,int offset, int length);
	
	public List<ProjectExpert> getProjectExpertList();
}
