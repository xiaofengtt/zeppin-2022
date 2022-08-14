/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IPlatformAccountDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;

/**
 *
 */

@Repository
public class PlatformAccountDAO extends BaseDAO<PlatformAccount, String> implements IPlatformAccountDAO {


	/**
	 * 向数据库添加一条PlatformAccount数据
	 * 向缓存platformAccounts添加一条PlatformAccount记录，key值为uuid
	 * 清空缓存listPlatformAccounts的所有记录
	 * @param platformAccount
	 * @return PlatformAccount
	 */
	@Override
	public PlatformAccount insert(PlatformAccount platformAccount) {
		return super.insert(platformAccount);
	}

	/**
	 * 数据库删除一条PlatformAccount数据
	 * 在缓存platformAccounts中删除一条PlatformAccount记录，key值为uuid
	 * 清空缓存listPlatformAccounts的所有记录
	 * @param platformAccount
	 * @return PlatformAccount
	 */
	@Override
	public PlatformAccount delete(PlatformAccount platformAccount) {
		return super.delete(platformAccount);
	}

	/**
	 * 向数据库更新一条PlatformAccount数据
	 * 在缓存platformAccounts中更新一条PlatformAccount记录，key值为uuid
	 * 清空缓存listPlatformAccounts的所有记录
	 * @param platformAccount
	 * @return PlatformAccount
	 */
	@Override
	public PlatformAccount update(PlatformAccount platformAccount) {
		return super.update(platformAccount);
	}

	/**
	 * 根据uuid得到一个PlatformAccount信息
	 * 向缓存platformAccounts添加一条PlatformAccount记录，key值为uuid
	 * 清空缓存listPlatformAccounts的所有记录
	 * @param uuid
	 * @return PlatformAccount
	 */
	@Override
	public PlatformAccount get(String uuid) {
		return super.get(uuid);
	}

}
