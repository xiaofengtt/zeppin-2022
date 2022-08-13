package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.TrainingAdmin;

public interface ITrainingAdminService extends IBaseService<TrainingAdmin, Integer> {
	public int checkUserInfo(Object[] pars);

	public List<TrainingAdmin> getTrainingAdminByStatus(short status);
	
	public List<TrainingAdmin> getTrainingAdminByTrainingCollege(Integer trainingCollegeId);
}
