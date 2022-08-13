package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.TrainingSubject;

public interface ITrainingSubjectService extends IBaseService<TrainingSubject, Short> {
	
	public List<TrainingSubject> findByName(String value);
	
	public List<TrainingSubject> getTrainingSubjectList();

}
