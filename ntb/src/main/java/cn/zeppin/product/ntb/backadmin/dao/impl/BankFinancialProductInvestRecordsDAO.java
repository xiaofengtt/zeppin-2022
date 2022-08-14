/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductInvestRecordsDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestRecords;

/**
 *
 */

@Repository
public class BankFinancialProductInvestRecordsDAO extends BaseDAO<BankFinancialProductInvestRecords, String> implements IBankFinancialProductInvestRecordsDAO {


	/**
	 * 向数据库添加一条BankFinancialProductInvestRecords数据
	 * 向缓存bankFinancialProductInvestRecordss添加一条BankFinancialProductInvestRecords记录，key值为uuid
	 * 清空缓存listBankFinancialProductInvestRecordss的所有记录
	 * @param bankFinancialProductInvestRecords
	 * @return BankFinancialProductInvestRecords
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductInvestRecordss", key = "'bankFinancialProductInvestRecords:' + #bankFinancialProductInvestRecords.uuid")}, evict={@CacheEvict(value = "listBankFinancialProductInvestRecordss", allEntries = true)})
	public BankFinancialProductInvestRecords insert(BankFinancialProductInvestRecords bankFinancialProductInvestRecords) {
		return super.insert(bankFinancialProductInvestRecords);
	}

	/**
	 * 数据库删除一条BankFinancialProductInvestRecords数据
	 * 在缓存bankFinancialProductInvestRecordss中删除一条BankFinancialProductInvestRecords记录，key值为uuid
	 * 清空缓存listBankFinancialProductInvestRecordss的所有记录
	 * @param bankFinancialProductInvestRecords
	 * @return BankFinancialProductInvestRecords
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductInvestRecordss", key = "'bankFinancialProductInvestRecords:' + #bankFinancialProductInvestRecords.uuid"), @CacheEvict(value = "listBankFinancialProductInvestRecordss", allEntries = true)})
	public BankFinancialProductInvestRecords delete(BankFinancialProductInvestRecords bankFinancialProductInvestRecords) {
		return super.delete(bankFinancialProductInvestRecords);
	}

	/**
	 * 向数据库更新一条BankFinancialProductInvestRecords数据
	 * 在缓存bankFinancialProductInvestRecordss中更新一条BankFinancialProductInvestRecords记录，key值为uuid
	 * 清空缓存listBankFinancialProductInvestRecordss的所有记录
	 * @param bankFinancialProductInvestRecords
	 * @return BankFinancialProductInvestRecords
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductInvestRecordss", key = "'bankFinancialProductInvestRecords:' + #bankFinancialProductInvestRecords.uuid")}, evict={@CacheEvict(value = "listBankFinancialProductInvestRecordss", allEntries = true)})
	public BankFinancialProductInvestRecords update(BankFinancialProductInvestRecords bankFinancialProductInvestRecords) {
		return super.update(bankFinancialProductInvestRecords);
	}

	/**
	 * 根据uuid得到一个BankFinancialProductInvestRecords信息
	 * 向缓存bankFinancialProductInvestRecordss添加一条BankFinancialProductInvestRecords记录，key值为uuid
	 * 清空缓存listBankFinancialProductInvestRecordss的所有记录
	 * @param uuid
	 * @return BankFinancialProductInvestRecords
	 */
	@Override
	@Cacheable(cacheNames = "bankFinancialProductInvestRecordss", key = "'bankFinancialProductInvestRecords:' + #uuid")
	public BankFinancialProductInvestRecords get(String uuid) {
		return super.get(uuid);
	}

}
