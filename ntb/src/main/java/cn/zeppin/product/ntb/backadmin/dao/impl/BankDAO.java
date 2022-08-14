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

import cn.zeppin.product.ntb.backadmin.dao.api.IBankDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class BankDAO extends BaseDAO<Bank, String> implements IBankDAO {


	/**
	 * 向数据库添加一条Bank数据
	 * 向缓存banks添加一条Bank记录，key值为uuid
	 * 清空缓存listPageBanks的所有记录
	 * @param bank
	 * @return Bank
	 */
	@Override
	@Caching(put={@CachePut(value = "banks", key = "'banks:' + #bank.uuid")}, evict={@CacheEvict(value = "listPageBanks", allEntries = true)})
	public Bank insert(Bank bank) {
		return super.insert(bank);
	}

	/**
	 * 向数据库删除一条Bank数据
	 * 在缓存banks中删除一条Bank记录，key值为uuid
	 * 清空缓存listPageBanks的所有记录
	 * @param bank
	 * @return Bank
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "banks", key = "'banks:' + #bank.uuid"), @CacheEvict(value = "listPageBanks", allEntries = true), 
			@CacheEvict(value = "listPageBranchBanks", allEntries = true)})
	public Bank delete(Bank bank) {
		return super.delete(bank);
	}

	/**
	 * 向数据库更新一条Bank数据
	 * 在缓存banks中更新一条Bank记录，key值为uuid
	 * 清空缓存listPageBanks的所有记录
	 * @param bank
	 * @return Bank
	 */
	@Override
	@Caching(put={@CachePut(value = "banks", key = "'banks:' + #bank.uuid")}, evict={@CacheEvict(value = "listPageBanks", allEntries = true),
			@CacheEvict(value = "listPageBranchBanks", allEntries = true)})
	public Bank update(Bank bank) {
		return super.update(bank);
	}

	/**
	 * 根据uuid得到一个Bank信息
	 * 向缓存banks添加一条Bank记录，key值为uuid
	 * @param uuid
	 * @return Bank
	 */
	@Override
	@Cacheable(value = "banks", key = "'banks:' + #uuid")
	public Bank get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPageBanks，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageBanks")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.name, b.short_name as shortName, b.logo, r.url as logoUrl, b.status, b.single_limit as singleLimit, b.daily_limit as dailyLimit, b.code,"
				+ " b.code_num as codeNum, b.flag_bank as flagBank, b.credit_inquiry_phone as creditInquiryPhone, b.credit_inquiry_command as creditInquiryCommand "
				+ " from bank b left join resource r on b.logo=r.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and b.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('normal','disable') ");
		}
		
		//名称
		if (inputParams.get("bankName") != null && !"".equals(inputParams.get("bankName"))) {
			builder.append(" and (b.name = '" + inputParams.get("bankName") + "' or b.short_name = '" + inputParams.get("bankName") + "') ");
		}
		
		if (inputParams.get("flagBinding") != null && !"".equals(inputParams.get("flagBinding"))) {
			builder.append(" and b.flag_binding = " + inputParams.get("flagBinding") + " ");
		}
		
		if (inputParams.get("flagBank") != null && !"".equals(inputParams.get("flagBank"))) {
			builder.append(" and b.flag_bank = " + inputParams.get("flagBank") + " ");
		}
		
		if (inputParams.get("codeNum") != null && !"".equals(inputParams.get("codeNum"))) {
			builder.append(" and b.code_num = '" + inputParams.get("codeNum") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("b.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by b.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bank b where 1=1 ");
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
		
		//名称
		if (inputParams.get("bankname") != null && !"".equals(inputParams.get("bankname"))) {
			builder.append(" and (b.name = '" + inputParams.get("bankname") + "' or b.short_name = '" + inputParams.get("bankname") + "') ");
		}
		
		if (inputParams.get("flagBinding") != null && !"".equals(inputParams.get("flagBinding"))) {
			builder.append(" and b.flag_binding = '" + inputParams.get("flagBinding") + "' ");
		}
		
		if (inputParams.get("flagBank") != null && !"".equals(inputParams.get("flagBank"))) {
			builder.append(" and b.flag_bank = " + inputParams.get("flagBank") + " ");
		}
		
		if (inputParams.get("codeNum") != null && !"".equals(inputParams.get("codeNum"))) {
			builder.append(" and b.code_num = '" + inputParams.get("codeNum") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	/**
	 * 验证同名的银行信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankByName(String name, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from bank b where name=?0 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {name,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Entity> getListForWebPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.name, b.short_name as shortName, b.icon_color as iconColor, r.url as iconColorUrl, b.status, b.single_limit as singleLimit, b.daily_limit as dailyLimit"
				+ " from bank b left join resource r on b.icon_color=r.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and b.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('normal','disable') ");
		}
		
		//名称
		if (inputParams.get("bankName") != null && !"".equals(inputParams.get("bankName"))) {
			builder.append(" and (b.name like '%" + inputParams.get("bankName") + "%' or b.short_name like '%" + inputParams.get("bankName") + "%') ");
		}
		
		if (inputParams.get("flagBinding") != null && !"".equals(inputParams.get("flagBinding"))) {
			builder.append(" and b.flag_binding = " + inputParams.get("flagBinding") + " ");
		}
		
		if (inputParams.get("flagBank") != null && !"".equals(inputParams.get("flagBank"))) {
			builder.append(" and b.flag_bank = " + inputParams.get("flagBank") + " ");
		}
		
		if (inputParams.get("codeNum") != null && !"".equals(inputParams.get("codeNum"))) {
			builder.append(" and b.code_num = '" + inputParams.get("codeNum") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("b.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by b.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
}
