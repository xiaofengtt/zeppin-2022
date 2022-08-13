package cn.zeppin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ProjectApplyExpert;

public interface IProjectApplyExpertDao extends IBaseDao<ProjectApplyExpert, Integer> {
	public void deleteByProjectApply(int id);
	public int checkProjectApplyExpert(int paid,int expertid);
	public int getCountForExpert (Map<String,Object> searchMap);
	public ProjectApplyExpert getProjectApplyExpertByAll(int paid,int expertid);
	public ProjectApplyExpert getProjectApplyExpertByParams(int project , int subject , int trainingCollege,int expert);
	public List<ProjectApplyExpert> getProjectApplyExpertListByParams(HashMap<String,String> searchMap);
}
