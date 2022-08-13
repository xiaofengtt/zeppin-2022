package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTestItemCount;
import cn.zeppin.entity.Subject;

public interface ISsoUserTestItemCountDAO extends IBaseDAO<SsoUserTestItemCount, Integer> {

	/**
	 * 根据主键获取用户做题数据
	 * @param userId
	 * @param itemId
	 * @param blankInx
	 * @return
	 */
	public SsoUserTestItemCount getByKey(Integer userId, Integer itemId, Integer blankInx);

	/**
	 * 获取用户错题本中的试题
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	public List<Map<String,Object>> searchAllWrongItem(SsoUser currentUser, Subject subject);
	
}
