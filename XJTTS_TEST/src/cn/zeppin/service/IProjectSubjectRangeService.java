package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.ProjectCollegeRange;
import cn.zeppin.entity.ProjectSubjectRange;

public interface IProjectSubjectRangeService extends IBaseService<ProjectSubjectRange, Integer> {
	
	public void deleteByProject(int id);
	
	 public List<ProjectSubjectRange> getListByTrainingSubject(Integer subjectId);
	 public List<ProjectSubjectRange> getListByProject(int projectid);
}
