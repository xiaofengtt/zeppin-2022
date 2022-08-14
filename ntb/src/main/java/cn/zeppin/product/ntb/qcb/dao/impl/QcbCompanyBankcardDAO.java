/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyBankcardDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyBankcardDAO extends BaseDAO<QcbCompanyBankcard, String> implements IQcbCompanyBankcardDAO {


	/**
	 * 向数据库添加一条qcbCompanyBankcard数据
	 * @param qcbCompanyBankcard
	 * @return qcbCompanyBankcard
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyBankcards", key = "'qcbCompanyBankcards:' + #qcbCompanyBankcard.uuid")})
	public QcbCompanyBankcard insert(QcbCompanyBankcard qcbCompanyBankcard) {
		return super.insert(qcbCompanyBankcard);
	}

	/**
	 * 向数据库删除一条qcbCompanyBankcard数据
	 * @param qcbCompanyBankcard
	 * @return qcbCompanyBankcard
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyBankcards", key = "'qcbCompanyBankcards:' + #qcbCompanyBankcard.uuid")})
	public QcbCompanyBankcard delete(QcbCompanyBankcard qcbCompanyBankcard) {
		return super.delete(qcbCompanyBankcard);
	}

	/**
	 * 向数据库更新一条QcbCompanyBankcards数据
	 * @param qcbCompanyBankcards
	 * @return QcbCompanyBankcards
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyBankcards", key = "'qcbCompanyBankcards:' + #qcbCompanyBankcard.uuid")})
	public QcbCompanyBankcard update(QcbCompanyBankcard qcbCompanyBankcard) {
		return super.update(qcbCompanyBankcard);
	}

	/**
	 * 根据uuid得到一个QcbCompanyBankcards信息
	 * @param uuid
	 * @return QcbCompanyBankcards
	 */
	@Override
	@Cacheable(value = "qcbCompanyBankcards", key = "'qcbCompanyBankcards:' + #uuid")
	public QcbCompanyBankcard get(String uuid) {
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
		builder.append(" select qcb.uuid,qcb.qcb_company as qcbCompany,qcb.bank,qcb.branch_bank as branchBank, qcb.bk_area as bkArea,"
				+ " qcb.binding_card_cardholder as bindingCardCardholder, qcb.binding_bank_card as bindingBankCard, qcb.binding_card_phone as bindingCardPhone,"
				+ " qcb.binding_card_type as bindingCardType,qcb.status,qcb.createtime,qcb.creator"
				+ " from qcb_company_bankcard qcb  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcb.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("bindingBankCard") != null && !"".equals(inputParams.get("bindingBankCard"))) {
			builder.append(" and qcb.binding_bank_card = '" + inputParams.get("bindingBankCard") + "' ");
		}
		
		if (inputParams.get("bindingCardCardholder") != null && !"".equals(inputParams.get("bindingCardCardholder"))) {
			builder.append(" and qcb.binding_card_cardholder = '" + inputParams.get("bindingCardCardholder") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcb.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and qcb.bank = '" + inputParams.get("bank") + "' ");
		}
		
		if (inputParams.get("branchBank") != null && !"".equals(inputParams.get("branchBank"))) {
			builder.append(" and qcb.branch_bank = '" + inputParams.get("branchBank") + "' ");
		}
		
		if (inputParams.get("bkArea") != null && !"".equals(inputParams.get("bkArea"))) {
			builder.append(" and qcb.bk_area = '" + inputParams.get("bkArea") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcb.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qcb.status in ('normal','disable') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcb.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcb.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcb.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_bankcard qcb where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcb.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("bindingBankCard") != null && !"".equals(inputParams.get("bindingBankCard"))) {
			builder.append(" and qcb.binding_bank_card = '" + inputParams.get("bindingBankCard") + "' ");
		}
		
		if (inputParams.get("bindingCardCardholder") != null && !"".equals(inputParams.get("bindingCardCardholder"))) {
			builder.append(" and qcb.binding_card_cardholder = '" + inputParams.get("bindingCardCardholder") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcb.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and qcb.bank = '" + inputParams.get("bank") + "' ");
		}
		
		if (inputParams.get("branchBank") != null && !"".equals(inputParams.get("branchBank"))) {
			builder.append(" and qcb.branch_bank = '" + inputParams.get("branchBank") + "' ");
		}
		
		if (inputParams.get("bkArea") != null && !"".equals(inputParams.get("bkArea"))) {
			builder.append(" and qcb.bk_area = '" + inputParams.get("bkArea") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcb.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qcb.status in ('normal','disable') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcb.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
