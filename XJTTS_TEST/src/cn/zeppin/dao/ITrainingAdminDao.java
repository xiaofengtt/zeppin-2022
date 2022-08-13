package cn.zeppin.dao;

import cn.zeppin.entity.TrainingAdmin;

public interface ITrainingAdminDao extends IBaseDao<TrainingAdmin, Integer> {
	public int checkUserInfo(Object[] pars);
}
