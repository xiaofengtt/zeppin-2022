package cn.zeppin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ProjectApplyEvaluate;

public interface IProjectApplyEvaluateDao extends IBaseDao<ProjectApplyEvaluate, Integer> {
	public void deleteByProjectApply(int id);
	public int checkProjectApplyEvaluate(int paid,int expertid);
	public int getCountForExpert (Map<String,Object> searchMap);
	public ProjectApplyEvaluate getProjectApplyEvaluateByAll(int paid,int expertid);
	public ProjectApplyEvaluate getProjectApplyEvaluateByParams(int project , int subject , int trainingCollege,int expert);
	public List<ProjectApplyEvaluate> getProjectApplyEvaluateListByParams(HashMap<String,String> searchMap);
}
