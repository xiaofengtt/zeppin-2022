
package cn.zeppin.dao;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.ProjectExpert;

public interface IProjectExpertDao extends IBaseDao<ProjectExpert, Integer> {
	
	public int checkUserInfo(Object[] pars);
	
	public int getProjectExpertCount(String searchName, String searchType, String sortName, String sortType);
	
	public List getProjectExpert(String searchName, String searchType, String sortName, String sortType,int offset, int length);
	
	public List<ProjectExpert> getProjectExpertList();

	public Integer getExpertByPapperCount(Integer pid , Integer tsid , Integer tcid);
	
	public List<ProjectExpert> getProjectExpertListByParams(HashMap<String,String> searchMap);
}
