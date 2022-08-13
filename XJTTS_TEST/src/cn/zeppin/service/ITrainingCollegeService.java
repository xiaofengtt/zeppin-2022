package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.TrainingCollege;

public interface ITrainingCollegeService extends IBaseService<TrainingCollege, Integer> {

	public int checkUserInfo(Object[] pars);

	public List<TrainingCollege> findByName(String value);

	public List<TrainingCollege> findByStatus(short status);

	public List<TrainingCollege> getTrainingCollegeListForRange();
	
	public List<TrainingCollege> getTrainingCollegeByName(String name);
	
	/**
	 * 校验第三封登录平台登录令牌是否正确存在
	 * @param token
	 * @return
	 */
	public List<TrainingCollege> checkTrainingCollegeToken(String token);

}
