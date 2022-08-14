/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeBankcardDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbEmployeeBankcardDAO extends BaseDAO<QcbEmployeeBankcard, String> implements IQcbEmployeeBankcardDAO {


	/**
	 * 向数据库添加一条qcbEmployeeBankcard数据
	 * @param qcbEmployeeBankcard
	 * @return qcbEmployeeBankcard
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployeeBankcards", key = "'qcbEmployeeBankcards:' + #qcbEmployeeBankcard.uuid")})
	public QcbEmployeeBankcard insert(QcbEmployeeBankcard qcbEmployeeBankcard) {
		return super.insert(qcbEmployeeBankcard);
	}

	/**
	 * 向数据库删除一条qcbEmployeeBankcard数据
	 * @param qcbEmployeeBankcard
	 * @return qcbEmployeeBankcard
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbEmployeeBankcards", key = "'qcbEmployeeBankcards:' + #qcbEmployeeBankcard.uuid")})
	public QcbEmployeeBankcard delete(QcbEmployeeBankcard qcbEmployeeBankcard) {
		return super.delete(qcbEmployeeBankcard);
	}

	/**
	 * 向数据库更新一条QcbEmployeeBankcards数据
	 * @param qcbEmployeeBankcards
	 * @return QcbEmployeeBankcards
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployeeBankcards", key = "'qcbEmployeeBankcards:' + #qcbEmployeeBankcard.uuid")})
	public QcbEmployeeBankcard update(QcbEmployeeBankcard qcbEmployeeBankcard) {
		return super.update(qcbEmployeeBankcard);
	}

	/**
	 * 根据uuid得到一个QcbEmployeeBankcards信息
	 * @param uuid
	 * @return QcbEmployeeBankcards
	 */
	@Override
	@Cacheable(value = "qcbEmployeeBankcards", key = "'qcbEmployeeBankcards:' + #uuid")
	public QcbEmployeeBankcard get(String uuid) {
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
		builder.append(" select qeb.uuid,qeb.qcb_employee as qcbEmployee,qeb.bank,qeb.branch_bank as branchBank,qeb.type,"
				+ "qeb.bank_account_name as bankAccountName,qeb.binding_bank_card as bindingBankCard,qeb.status,qeb.createtime,"
				+ "qeb.binding_card_phone as bindingCardPhone,qeb.binding_card_cardholder as bindingCardCardholder,"
				+ "qeb.flag_remind as flagRemind, qeb.remind_time as remindTime"
				+ " from qcb_employee_bankcard qeb  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qeb.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qeb.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}

		if (inputParams.get("bindingBankCard") != null && !"".equals(inputParams.get("bindingBankCard"))) {
			builder.append(" and qeb.binding_bank_card = '" + inputParams.get("bindingBankCard") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qeb.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and qeb.bank = '" + inputParams.get("bank") + "' ");
		}
		
		if (inputParams.get("flagRemind") != null && !"".equals(inputParams.get("flagRemind"))) {
			builder.append(" and qeb.flag_remind = '" + inputParams.get("flagRemind") + "' ");
		}
		
		if (inputParams.get("remindTime") != null && !"".equals(inputParams.get("remindTime"))) {
			builder.append(" and qeb.remind_time = '" + inputParams.get("remindTime") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qeb.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qeb.status in ('normal','disable') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qeb.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qeb.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_employee_bankcard qeb where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qeb.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qeb.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("bindingBankCard") != null && !"".equals(inputParams.get("bindingBankCard"))) {
			builder.append(" and qeb.binding_bank_card = '" + inputParams.get("bindingBankCard") + "' ");
		}
		
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and qeb.bank = '" + inputParams.get("bank") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qeb.type = '" + inputParams.get("type") + "' ");
		}

		if (inputParams.get("flagRemind") != null && !"".equals(inputParams.get("flagRemind"))) {
			builder.append(" and qeb.flag_remind = '" + inputParams.get("flagRemind") + "' ");
		}
		
		if (inputParams.get("remindTime") != null && !"".equals(inputParams.get("remindTime"))) {
			builder.append(" and qeb.remind_time = '" + inputParams.get("remindTime") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qeb.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qeb.status in ('normal','disable') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
