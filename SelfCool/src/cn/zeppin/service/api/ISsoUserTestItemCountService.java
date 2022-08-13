package cn.zeppin.service.api;

import cn.zeppin.entity.SsoUserTestItemCount;

public interface ISsoUserTestItemCountService {

	/**
	 * 通过唯一索引得到唯一的用户做题信息对象
	 * @param id
	 * @param itemId
	 * @param blankInx
	 * @return
	 */
	public SsoUserTestItemCount getByKey(Integer userId, Integer itemId, Integer blankInx);

	/**
	 * 更新用户做题信息记录
	 * @param ssoUserTestItemCount
	 * @return
	 */
	public SsoUserTestItemCount update(SsoUserTestItemCount ssoUserTestItemCount);
	
	
	/**
	 * 创建用户错题本中的试题
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	public SsoUserTestItemCount save(SsoUserTestItemCount ssoUserTestItemCount);

}
