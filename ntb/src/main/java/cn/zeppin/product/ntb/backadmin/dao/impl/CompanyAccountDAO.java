/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class CompanyAccountDAO extends BaseDAO<CompanyAccount, String> implements ICompanyAccountDAO {


	/**
	 * 向数据库添加一条CompanyAccount数据
	 * 向缓存companyAccounts添加一条CompanyAccount记录，key值为uuid
	 * 清空缓存listCompanyAccounts的所有记录
	 * @param companyAccount
	 * @return CompanyAccount
	 */
	@Override
	public CompanyAccount insert(CompanyAccount companyAccount) {
		return super.insert(companyAccount);
	}

	/**
	 * 数据库删除一条CompanyAccount数据
	 * 在缓存companyAccounts中删除一条CompanyAccount记录，key值为uuid
	 * 清空缓存listCompanyAccounts的所有记录
	 * @param companyAccount
	 * @return CompanyAccount
	 */
	@Override
	public CompanyAccount delete(CompanyAccount companyAccount) {
		return super.delete(companyAccount);
	}

	/**
	 * 向数据库更新一条CompanyAccount数据
	 * 在缓存companyAccounts中更新一条CompanyAccount记录，key值为uuid
	 * 清空缓存listCompanyAccounts的所有记录
	 * @param companyAccount
	 * @return CompanyAccount
	 */
	@Override
	public CompanyAccount update(CompanyAccount companyAccount) {
		return super.update(companyAccount);
	}

	/**
	 * 根据uuid得到一个CompanyAccount信息
	 * 向缓存companyAccounts添加一条CompanyAccount记录，key值为uuid
	 * 清空缓存listCompanyAccounts的所有记录
	 * @param uuid
	 * @return CompanyAccount
	 */
	@Override
	public CompanyAccount get(String uuid) {
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
		builder.append(" select c.uuid,c.account_name as accountName,c.account_num as accountNum,c.company_name as companyName,"
				+ "c.type,c.bank,c.branch_bank as branchBank,c.investment,c.account_balance as accountBalance,"
				+ "c.total_redeem as totalRedeem,c.total_return as totalReturn,c.status"
				+ " from company_account c where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and c.account_name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("accountName") != null && !"".equals(inputParams.get("accountName"))) {
			builder.append(" and c.account_name = '" + inputParams.get("accountName") + "' ");
		}
		
		if (inputParams.get("accountNum") != null && !"".equals(inputParams.get("accountNum"))) {
			builder.append(" and c.account_num = '" + inputParams.get("accountNum") + "' ");
		}
		//是否为银行
		if (inputParams.get("isBank") != null && "1".equals(inputParams.get("isBank"))) {
			builder.append(" and c.type in ('invest','collect','redeem') ");
		}
		//开户行
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and c.bank = '" + inputParams.get("bank") + "' ");
		}
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and c.type = '" + inputParams.get("type") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and c.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and c.status in ('normal','disable') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("c.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by c.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from company_account c where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and c.account_name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("accountName") != null && !"".equals(inputParams.get("accountName"))) {
			builder.append(" and c.account_name = '" + inputParams.get("accountName") + "' ");
		}
		
		if (inputParams.get("accountNum") != null && !"".equals(inputParams.get("accountNum"))) {
			builder.append(" and c.account_num = '" + inputParams.get("accountNum") + "' ");
		}
		//开户行
		if (inputParams.get("bank") != null && !"".equals(inputParams.get("bank"))) {
			builder.append(" and c.bank = '" + inputParams.get("bank") + "' ");
		}
		//是否为银行
		if (inputParams.get("isBank") != null && "1".equals(inputParams.get("isBank"))) {
			builder.append(" and c.type in ('invest','collect','redeem') ");
		}
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and c.type = '" + inputParams.get("type") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and c.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and c.status in ('normal','disable') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select ca.type as status, count(*) as count from company_account ca where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ca.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and ca.status in ('normal','disable') ");//全部
		}
		builder.append(" group by ca.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 验证企业账户号是否已经存在
	 * @param accountNum
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistAccountNum(String accountNum, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from company_account where account_num=?0 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {accountNum,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
}
