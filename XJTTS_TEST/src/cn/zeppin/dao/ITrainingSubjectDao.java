package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.TrainingSubject;

public interface ITrainingSubjectDao extends IBaseDao<TrainingSubject, Short> {

	/**
	 * @param value
	 * @return
	 */
	List<TrainingSubject> findByName(String value);
	
	public List<TrainingSubject> getTrainingSubjectList();

}
