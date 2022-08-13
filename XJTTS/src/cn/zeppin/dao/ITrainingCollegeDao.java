package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.TrainingCollege;

public interface ITrainingCollegeDao extends IBaseDao<TrainingCollege, Integer> {

	/**
	 * @param pars
	 * @return
	 */
	int checkUserInfo(Object[] pars);

	public List<TrainingCollege> getTrainingCollegeListForRange();
	/**
	 * @param value
	 * @return
	 */
	List<TrainingCollege> findByName(String value);

	public List<TrainingCollege> getTrainingCollegeByName(String name);
	
	/**
	 * 根据登录令牌查询承训单位记录
	 * @param token
	 * @return
	 */
	public List<TrainingCollege> getTrainingCollegeByToken(String token);

}
