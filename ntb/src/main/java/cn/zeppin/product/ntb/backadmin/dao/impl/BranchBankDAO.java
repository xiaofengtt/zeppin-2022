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

import cn.zeppin.product.ntb.backadmin.dao.api.IBranchBankDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class BranchBankDAO extends BaseDAO<BranchBank, String> implements IBranchBankDAO {


	/**
	 * 向数据库添加一条BranchBank数据
	 * 向缓存branchBanks添加一条BranchBank记录，key值为uuid
	 * 清空缓存listPageBranchBanks,listBranchBanks的所有记录
	 * @param branchBank
	 * @return BranchBank
	 */
	@Override
	@Caching(put={@CachePut(value = "branchBanks", key = "'branchBank:' + #branchBank.uuid")},
	evict={@CacheEvict(value = "listPageBranchBanks", allEntries = true), @CacheEvict(value = "listBranchBanks", allEntries = true)})
	public BranchBank insert(BranchBank branchBank) {
		return super.insert(branchBank);
	}

	/**
	 * 向数据库删除一条BranchBank数据
	 * 在缓存banks中删除一条BranchBank记录，key值为uuid
	 * 清空缓存listPageBranchBanks,listBranchBanks的所有记录
	 * @param branchBank
	 * @return BranchBank
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "branchBanks", key = "'branchBanks:' + #branchBank.uuid"),
			@CacheEvict(value = "listPageBranchBanks", allEntries = true),@CacheEvict(value = "listBranchBanks", allEntries = true)})
	public BranchBank delete(BranchBank branchBank) {
		return super.delete(branchBank);
	}

	/**
	 * 向数据库更新一条BranchBank数据
	 * 在缓存branchBanks中更新一条BranchBank记录，key值为uuid
	 * 清空缓存listPageBranchBanks,listBranchBanks的所有记录
	 * @param branchBank
	 * @return BranchBank
	 */
	@Override
	@Caching(put={@CachePut(value = "branchBanks", key = "'branchBanks:' + #branchBank.uuid")},
	evict={@CacheEvict(value = "listPageBranchBanks", allEntries = true),@CacheEvict(value = "listBranchBanks", allEntries = true)})
	public BranchBank update(BranchBank branchBank) {
		return super.update(branchBank);
	}

	/**
	 * 根据uuid得到一个BranchBank信息
	 * 向缓存branchBanks添加一条BranchBank记录，key值为uuid
	 * @param uuid
	 * @return BranchBank
	 */
	@Override
	@Cacheable(value = "branchBanks", key = "'branchBanks:' + #uuid")
	public BranchBank get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表,
	 * 将查询结果存入缓存listBranchBanks，key为所有参数名+参数值
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listBranchBanks")
	public List<Entity> getList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select bb.* from branch_bank bb where 1=1 ");
		//银行
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and bb.bank = '" + inputParams.get("bank") + "' ");
		}
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bb.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and bb.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and bb.status in ('normal','disable') ");
		}
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPageBranchBanks，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageBranchBanks")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select bb.uuid, bb.name, bb.address, b.name as bankName, bb.status from branch_bank bb, bank b where bb.bank=b.uuid ");
		//银行
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and bb.bank = '" + inputParams.get("bank") + "' ");
		}
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bb.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and bb.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and bb.status in ('normal','disable') ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("bb.").append(sort);
				comma = ",";
			}
		}else {
			builder.append(" order by bb.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from branch_bank b where 1=1 ");
		//银行
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and b.bank = '" + inputParams.get("bank") + "' ");
		}
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and b.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and b.status in ('normal','disable') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	/**
	 * 验证同名的支行信息是否已经存在
	 * @param bank
	 * @param name
	 * @return
	 */
	@Override
	public boolean isExistBranchBank(String bank, String name, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from branch_bank where bank=?0 and name=?1 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?2");
		}
		Object[] paras = {bank, name, uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
}
