/**
 * 
 */
package cn.zeppin.product.ntb.shbx.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.ShbxUserBankcard;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxUserBankcardDAO;

/**
 * @author hehe
 *
 */

@Repository
public class ShbxUserBankcardDAO extends BaseDAO<ShbxUserBankcard, String> implements IShbxUserBankcardDAO {


	/**
	 * 向数据库添加一条shbxUserBankcard数据
	 * @param shbxUserBankcard
	 * @return shbxUserBankcard
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxUserBankcards", key = "'shbxUserBankcards:' + #shbxUserBankcard.uuid")})
	public ShbxUserBankcard insert(ShbxUserBankcard shbxUserBankcard) {
		return super.insert(shbxUserBankcard);
	}

	/**
	 * 向数据库删除一条shbxUserBankcard数据
	 * @param shbxUserBankcard
	 * @return shbxUserBankcard
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "shbxUserBankcards", key = "'shbxUserBankcards:' + #shbxUserBankcard.uuid")})
	public ShbxUserBankcard delete(ShbxUserBankcard shbxUserBankcard) {
		return super.delete(shbxUserBankcard);
	}

	/**
	 * 向数据库更新一条ShbxUserBankcards数据
	 * @param shbxUserBankcards
	 * @return ShbxUserBankcards
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxUserBankcards", key = "'shbxUserBankcards:' + #shbxUserBankcard.uuid")})
	public ShbxUserBankcard update(ShbxUserBankcard shbxUserBankcard) {
		return super.update(shbxUserBankcard);
	}

	/**
	 * 根据uuid得到一个ShbxUserBankcards信息
	 * @param uuid
	 * @return ShbxUserBankcards
	 */
	@Override
	@Cacheable(value = "shbxUserBankcards", key = "'shbxUserBankcards:' + #uuid")
	public ShbxUserBankcard get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select sub.uuid,sub.shbx_user as shbxUser,sub.bank,sub.branch_bank as branchBank,sub.type,"
				+ "sub.bank_account_name as bankAccountName,sub.binding_bank_card as bindingBankCard,sub.status,sub.createtime,"
				+ "sub.binding_card_phone as bindingCardPhone,sub.binding_card_cardholder as bindingCardCardholder,"
				+ "sub.flag_remind as flagRemind, sub.remind_time as remindTime"
				+ " from shbx_user_bankcard sub  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and sub.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))) {
			builder.append(" and sub.shbx_user = '" + inputParams.get("shbxUser") + "' ");
		}

		if (inputParams.get("bindingBankCard") != null && !"".equals(inputParams.get("bindingBankCard"))) {
			builder.append(" and sub.binding_bank_card = '" + inputParams.get("bindingBankCard") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and sub.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and sub.bank = '" + inputParams.get("bank") + "' ");
		}
		
		if (inputParams.get("flagRemind") != null && !"".equals(inputParams.get("flagRemind"))) {
			builder.append(" and sub.flag_remind = '" + inputParams.get("flagRemind") + "' ");
		}
		
		if (inputParams.get("remindTime") != null && !"".equals(inputParams.get("remindTime"))) {
			builder.append(" and sub.remind_time = '" + inputParams.get("remindTime") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and sub.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and sub.status in ('normal','disable') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("sub.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by sub.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from shbx_user_bankcard sub where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and sub.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))) {
			builder.append(" and sub.shbx_user = '" + inputParams.get("shbxUser") + "' ");
		}
		
		if (inputParams.get("bindingBankCard") != null && !"".equals(inputParams.get("bindingBankCard"))) {
			builder.append(" and sub.binding_bank_card = '" + inputParams.get("bindingBankCard") + "' ");
		}
		
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and sub.bank = '" + inputParams.get("bank") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and sub.type = '" + inputParams.get("type") + "' ");
		}

		if (inputParams.get("flagRemind") != null && !"".equals(inputParams.get("flagRemind"))) {
			builder.append(" and sub.flag_remind = '" + inputParams.get("flagRemind") + "' ");
		}
		
		if (inputParams.get("remindTime") != null && !"".equals(inputParams.get("remindTime"))) {
			builder.append(" and sub.remind_time = '" + inputParams.get("remindTime") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and sub.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and sub.status in ('normal','disable') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
