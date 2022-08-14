/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorBankcardDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class InvestorBankcardDAO extends BaseDAO<InvestorBankcard, String> implements IInvestorBankcardDAO {


	/**
	 * 向数据库添加一条InvestorBankcard数据
	 * 向缓存investorBankcards添加一条InvestorBankcard记录，key值为uuid
	 * 清空缓存listPageInvestorBankcards,listInvestorBankcards的所有记录
	 * @param investorBankcards
	 * @return InvestorBankcards
	 */
	@Override
	@Caching(put={@CachePut(value = "investorBankcards", key = "'investorBankcards:' + #investorBankcard.uuid")},
	evict={@CacheEvict(value = "listPageInvestorBankcards", allEntries = true), @CacheEvict(value = "listInvestorBankcards", allEntries = true)})
	public InvestorBankcard insert(InvestorBankcard investorBankcard) {
		return super.insert(investorBankcard);
	}

	/**
	 * 向数据库删除一条investorBankcard数据
	 * 在缓存banks中删除一条investorBankcard记录，key值为uuid
	 * 清空缓存listPageinvestorBankcards,listinvestorBankcards的所有记录
	 * @param investorBankcard
	 * @return investorBankcard
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investorBankcards", key = "'investorBankcards:' + #investorBankcard.uuid"),
			@CacheEvict(value = "listPageInvestorBankcards", allEntries = true),@CacheEvict(value = "listInvestorBankcards", allEntries = true)})
	public InvestorBankcard delete(InvestorBankcard investorBankcard) {
		return super.delete(investorBankcard);
	}

	/**
	 * 向数据库更新一条investorBankcard数据
	 * 在缓存investorBankcards中更新一条investorBankcard记录，key值为uuid
	 * 清空缓存listPageinvestorBankcards,listinvestorBankcards的所有记录
	 * @param investorBankcard
	 * @return investorBankcard
	 */
	@Override
	@Caching(put={@CachePut(value = "investorBankcards", key = "'investorBankcards:' + #investorBankcard.uuid")},
	evict={@CacheEvict(value = "listPageInvestorBankcards", allEntries = true),@CacheEvict(value = "listInvestorBankcards", allEntries = true)})
	public InvestorBankcard update(InvestorBankcard investorBankcard) {
		return super.update(investorBankcard);
	}

	/**
	 * 根据uuid得到一个investorBankcard信息
	 * 向缓存investorBankcards添加一条investorBankcard记录，key值为uuid
	 * @param uuid
	 * @return investorBankcard
	 */
	@Override
	@Cacheable(value = "investorBankcards", key = "'investorBankcards:' + #uuid")
	public InvestorBankcard get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表,
	 * 将查询结果存入缓存listinvestorBankcards，key为所有参数名+参数值
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listInvestorBankcards")
	public List<Entity> getList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ii.* from investor_bankcard ii where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ii.uuid = '" + inputParams.get("uuid") + "' ");
		}
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and ii.investor = '" + inputParams.get("investor") + "' ");
		}
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and ii.bank = '" + inputParams.get("bank") + "' ");
		}
		if (inputParams.get("branchBank") != null && !"".equals(inputParams.get("branchBank"))) {
			builder.append(" and ii.branch_bank = '" + inputParams.get("branchBank") + "' ");
		}
		if (inputParams.get("bankAccountName") != null && !"".equals(inputParams.get("bankAccountName"))) {
			builder.append(" and ii.bank_account_name = '" + inputParams.get("bankAccountName") + "' ");
		}
		if (inputParams.get("bindingBankCard") != null && !"".equals(inputParams.get("bindingBankCard"))) {
			builder.append(" and ii.binding_bank_card = '" + inputParams.get("bindingBankCard") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ii.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and ii.status in ('normal','disable') ");
		}
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPageInvestorBankcards，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageInvestorBankcards")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ii.uuid, ii.investor, ii.bank, ii.branch_bank as branchBank, ii.bank_account_name as bankAccountName,"
				+ " ii.binding_bank_card as bindingBankCard, ii.binding_card_phone as bindingCardPhone, ii.status,"
				+ " ii.binding_id as bindingId from investor_bankcard ii where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ii.uuid = '" + inputParams.get("uuid") + "' ");
		}
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and ii.investor = '" + inputParams.get("investor") + "' ");
		}
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and ii.bank = '" + inputParams.get("bank") + "' ");
		}
		if (inputParams.get("branchBank") != null && !"".equals(inputParams.get("branchBank"))) {
			builder.append(" and ii.branch_bank = '" + inputParams.get("branchBank") + "' ");
		}
		if (inputParams.get("bankAccountName") != null && !"".equals(inputParams.get("bankAccountName"))) {
			builder.append(" and ii.bank_account_name = '" + inputParams.get("bankAccountName") + "' ");
		}
		if (inputParams.get("bindingBankCard") != null && !"".equals(inputParams.get("bindingBankCard"))) {
			builder.append(" and ii.binding_bank_card = '" + inputParams.get("bindingBankCard") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ii.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and ii.status in ('normal','disable') ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("ii.").append(sort);
				comma = ",";
			}
		}else {
			builder.append(" order by ii.bandingtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from investor_bankcard ii where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ii.uuid = '" + inputParams.get("uuid") + "' ");
		}
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and ii.investor = '" + inputParams.get("investor") + "' ");
		}
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and ii.bank = '" + inputParams.get("bank") + "' ");
		}
		if (inputParams.get("branchBank") != null && !"".equals(inputParams.get("branchBank"))) {
			builder.append(" and ii.branch_bank = '" + inputParams.get("branchBank") + "' ");
		}
		if (inputParams.get("bankAccountName") != null && !"".equals(inputParams.get("bankAccountName"))) {
			builder.append(" and ii.bank_account_name = '" + inputParams.get("bankAccountName") + "' ");
		}
		if (inputParams.get("bindingBankCard") != null && !"".equals(inputParams.get("bindingBankCard"))) {
			builder.append(" and ii.binding_bank_card = '" + inputParams.get("bindingBankCard") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ii.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and ii.status in ('normal','disable') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

}
