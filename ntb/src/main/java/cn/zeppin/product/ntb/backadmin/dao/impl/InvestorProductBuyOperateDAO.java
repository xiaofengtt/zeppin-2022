/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorProductBuyOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyOperate;

/**
 *
 */

@Repository
public class InvestorProductBuyOperateDAO extends BaseDAO<InvestorProductBuyOperate, String> implements IInvestorProductBuyOperateDAO {


	/**
	 * 向数据库添加一条InvestorProductBuyOperate数据
	 * 向缓存investorProductBuyOperates添加一条InvestorProductBuyOperate记录，key值为uuid
	 * 清空缓存listInvestorProductBuyOperates的所有记录
	 * @param investorProductBuyOperate
	 * @return InvestorProductBuyOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "investorProductBuyOperates", key = "'investorProductBuyOperate:' + #investorProductBuyOperate.uuid")}, evict={@CacheEvict(value = "listInvestorProductBuyOperates", allEntries = true)})
	public InvestorProductBuyOperate insert(InvestorProductBuyOperate investorProductBuyOperate) {
		return super.insert(investorProductBuyOperate);
	}

	/**
	 * 数据库删除一条InvestorProductBuyOperate数据
	 * 在缓存investorProductBuyOperates中删除一条InvestorProductBuyOperate记录，key值为uuid
	 * 清空缓存listInvestorProductBuyOperates的所有记录
	 * @param investorProductBuyOperate
	 * @return InvestorProductBuyOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investorProductBuyOperates", key = "'investorProductBuyOperate:' + #investorProductBuyOperate.uuid"), @CacheEvict(value = "listInvestorProductBuyOperates", allEntries = true)})
	public InvestorProductBuyOperate delete(InvestorProductBuyOperate investorProductBuyOperate) {
		return super.delete(investorProductBuyOperate);
	}

	/**
	 * 向数据库更新一条InvestorProductBuyOperate数据
	 * 在缓存investorProductBuyOperates中更新一条InvestorProductBuyOperate记录，key值为uuid
	 * 清空缓存listInvestorProductBuyOperates的所有记录
	 * @param investorProductBuyOperate
	 * @return InvestorProductBuyOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "investorProductBuyOperates", key = "'investorProductBuyOperate:' + #investorProductBuyOperate.uuid")}, evict={@CacheEvict(value = "listInvestorProductBuyOperates", allEntries = true)})
	public InvestorProductBuyOperate update(InvestorProductBuyOperate investorProductBuyOperate) {
		return super.update(investorProductBuyOperate);
	}

	/**
	 * 根据uuid得到一个InvestorProductBuyOperate信息
	 * 向缓存investorProductBuyOperates添加一条InvestorProductBuyOperate记录，key值为uuid
	 * 清空缓存listInvestorProductBuyOperates的所有记录
	 * @param uuid
	 * @return InvestorProductBuyOperate
	 */
	@Override
	@Cacheable(cacheNames = "investorProductBuyOperates", key = "'investorProductBuyOperate:' + #uuid")
	public InvestorProductBuyOperate get(String uuid) {
		return super.get(uuid);
	}

}
