package cn.zeppin.service.imp;

import cn.zeppin.dao.api.ISsoUserTestItemCountDAO;
import cn.zeppin.entity.SsoUserTestItemCount;
import cn.zeppin.service.api.ISsoUserTestItemCountService;

public class SsoUserTestItemCountService implements ISsoUserTestItemCountService {
	
	private ISsoUserTestItemCountDAO ssoUserTestItemCountDAO;
	
	public ISsoUserTestItemCountDAO getSsoUserTestItemCountDAO() {
		return ssoUserTestItemCountDAO;
	}

	public void setSsoUserTestItemCountDAO(ISsoUserTestItemCountDAO ssoUserTestItemCountDAO) {
		this.ssoUserTestItemCountDAO = ssoUserTestItemCountDAO;
	}

	/**
	 * 根据主键获取用户做题数据
	 * @param userId
	 * @param itemId
	 * @param blankInx
	 * @return
	 */
	@Override
	public SsoUserTestItemCount getByKey(Integer userId, Integer itemId, Integer blankInx) {
		// TODO Auto-generated method stub
		return this.getSsoUserTestItemCountDAO().getByKey(userId, itemId, blankInx);
	}

	/**
	 * 更新用户错题本中的试题
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	@Override
	public SsoUserTestItemCount update(SsoUserTestItemCount ssoUserTestItemCount) {
		// TODO Auto-generated method stub
		return this.getSsoUserTestItemCountDAO().update(ssoUserTestItemCount);
	}
	
	/**
	 * 创建用户错题本中的试题
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	@Override
	public SsoUserTestItemCount save(SsoUserTestItemCount ssoUserTestItemCount) {
		// TODO Auto-generated method stub
		return this.getSsoUserTestItemCountDAO().save(ssoUserTestItemCount);
	}

}
