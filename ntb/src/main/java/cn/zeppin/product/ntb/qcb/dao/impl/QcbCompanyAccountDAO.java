/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyAccountDAO extends BaseDAO<QcbCompanyAccount, String> implements IQcbCompanyAccountDAO {


	/**
	 * 向数据库添加一条qcbCompanyAccount数据
	 * @param qcbCompanyAccount
	 * @return qcbCompanyAccount
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyAccounts", key = "'qcbCompanyAccounts:' + #qcbCompanyAccount.uuid")})
	public QcbCompanyAccount insert(QcbCompanyAccount qcbCompanyAccount) {
		return super.insert(qcbCompanyAccount);
	}

	/**
	 * 向数据库删除一条qcbCompanyAccount数据
	 * @param qcbCompanyAccount
	 * @return qcbCompanyAccount
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyAccounts", key = "'qcbCompanyAccounts:' + #qcbCompanyAccount.uuid")})
	public QcbCompanyAccount delete(QcbCompanyAccount qcbCompanyAccount) {
		return super.delete(qcbCompanyAccount);
	}

	/**
	 * 向数据库更新一条QcbCompanyAccounts数据
	 * @param qcbCompanyAccounts
	 * @return QcbCompanyAccounts
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyAccounts", key = "'qcbCompanyAccounts:' + #qcbCompanyAccount.uuid")})
	public QcbCompanyAccount update(QcbCompanyAccount qcbCompanyAccount) {
		return super.update(qcbCompanyAccount);
	}

	/**
	 * 根据uuid得到一个QcbCompanyAccounts信息
	 * @param uuid
	 * @return QcbCompanyAccounts
	 */
	@Override
	@Cacheable(value = "qcbCompanyAccounts", key = "'qcbCompanyAccounts:' + #uuid")
	public QcbCompanyAccount get(String uuid) {
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
		builder.append(" select qa.uuid,qa.merchant_id as merchantId,qa.name,qa.bk_area as bkArea,qa.address,qa.phone,qa.corporation,"
				+ " qa.connection_company as connectionCompany,qa.tax_identification_num as taxIdentificationNum,qa.secret_password as secretPassword,"
				+ " qa.open_bank as openBank,qa.open_bank_cardnum as openBankCardnum, qa.contacts,qa.contacts_mobile as contactsMobile,"
				+ " qa.contacts_idcard as contactsIdcard,qa.contacts_email as contactsEmail,qa.account_balance as accountBalance,"
				+ " qa.account_pay as accountPay,qa.total_invest as totalInvest, qa.total_return as totalReturn, qa.virtual_account_type as virtualAccountType,"
				+ " qa.qcb_virtual_accounts as qcbVirtualAccounts, qa.logo,qa.finance_status as financeStatus,qa.status,qa.createtime, qa.accredit,qa.accredit_mobile as accreditMobile,"
				+ " qa.tax_company as taxCompany, qa.tax_phone as taxPhone, qa.tax_address as taxAddress, qa.auth_status as authStatus,"
				+ " qa.business_licence as businessLicence,qa.evidence,qa.idcard_face as idcardFace, qa.idcard_back as idcardBack, qa.fee_ticket as feeTicket "
				+ " from qcb_company_account qa  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qa.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qa.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("merchantId") != null && !"".equals(inputParams.get("merchantId"))) {
			builder.append(" and qa.merchantId = '" + inputParams.get("merchantId") + "' ");
		}
		
		//财税服务开通状态
		if (inputParams.get("financeStatus") != null && !"".equals(inputParams.get("financeStatus"))) {
			builder.append(" and qa.finance_status = '" + inputParams.get("financeStatus") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qa.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qa.status in ('normal','uncheck','unauth','nopass') ");
		}
		
		//状态
		if (inputParams.get("authStatus") != null && !"".equals(inputParams.get("authStatus"))) {
			builder.append(" and qa.auth_status = '" + inputParams.get("authStatus") + "' ");
		}
		else{
			builder.append(" and qa.auth_status in ('normal','uncheck','unauth','nopass') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qa.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qa.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_account qa where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qa.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qa.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qa.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("merchantId") != null && !"".equals(inputParams.get("merchantId"))) {
			builder.append(" and qa.merchantId = '" + inputParams.get("merchantId") + "' ");
		}
		
		//财税服务开通状态
		if (inputParams.get("financeStatus") != null && !"".equals(inputParams.get("financeStatus"))) {
			builder.append(" and qa.finance_status = '" + inputParams.get("financeStatus") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qa.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qa.status in ('normal','uncheck','unauth','nopass') ");
		}
		
		//状态
		if (inputParams.get("authStatus") != null && !"".equals(inputParams.get("authStatus"))) {
			builder.append(" and qa.auth_status = '" + inputParams.get("authStatus") + "' ");
		}
		else{
			builder.append(" and qa.auth_status in ('normal','uncheck','unauth','nopass') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	

	@Override
	public QcbCompanyAccount getByMerchantId(String merchantId) {
		StringBuilder builder = new StringBuilder();
		builder.append(" from QcbCompanyAccount qa where 1=1");
		builder.append(" and qa.merchantId='"+merchantId+"'");
//		Object[] paras = {mobile, mobile};
		List<QcbCompanyAccount> operators = this.getByHQL(builder.toString());
		if (operators != null && operators.size() > 0) {
			return operators.get(0);
		}
		return null;
	}
	
	/**
	 * 验证同手机号的企财宝管理员是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistQcbCompanyAccountByMerchantId(String merchantId, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from qcb_company_account qa where merchant_id=?0");
		//编辑时检测uuid
		if(uuid != null && !"".equals(uuid)){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {merchantId,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistQcbCompanyAccountByName(String name, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from qcb_company_account qa where name=?0");
		//编辑时检测uuid
		if(uuid != null && !"".equals(uuid)){
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
	public List<Entity> getStatusList(
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select qa.status as status, count(*) as count from qcb_company_account qa group by qa.status");
		return super.getBySQL(builder.toString(), resultClass);
	}

	@Override
	public List<Entity> getCheckStatusList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select qa.auth_status as status, count(*) as count from qcb_company_account qa group by qa.auth_status");
		return super.getBySQL(builder.toString(), resultClass);
	}

	@Override
	public BigDecimal getTotalBalacneByEmp(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(q.account_balance) as count1 from qcb_employee q ");
		builder.append(" left join qcb_company_employee qce on q.uuid=qce.qcb_employee");
		builder.append(" left join qcb_company_account qca on qce.qcb_company=qca.uuid");
		builder.append(" where 1=1 ");
		
		
		//企业
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qca.uuid = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and q.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and q.status in ('normal') ");
		}
		
		builder.append(" group by qca.uuid");
		return BigDecimal.valueOf(Double.valueOf(super.getResultBySQL(builder.toString()) == null ? "0":super.getResultBySQL(builder.toString()).toString()));
	}
}
