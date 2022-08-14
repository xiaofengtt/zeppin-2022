/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbVirtualAccountsDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbVirtualAccountsDAO extends BaseDAO<QcbVirtualAccounts, String> implements IQcbVirtualAccountsDAO {


	/**
	 * 向数据库添加一条qcbVirtualAccounts数据
	 * @param qcbVirtualAccounts
	 * @return qcbVirtualAccounts
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbVirtualAccountss", key = "'qcbVirtualAccountss:' + #qcbVirtualAccounts.uuid")})
	public QcbVirtualAccounts insert(QcbVirtualAccounts qcbVirtualAccounts) {
		return super.insert(qcbVirtualAccounts);
	}

	/**
	 * 向数据库删除一条qcbVirtualAccounts数据
	 * @param qcbVirtualAccounts
	 * @return qcbVirtualAccounts
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbVirtualAccountss", key = "'qcbVirtualAccountss:' + #qcbVirtualAccounts.uuid")})
	public QcbVirtualAccounts delete(QcbVirtualAccounts qcbVirtualAccounts) {
		return super.delete(qcbVirtualAccounts);
	}

	/**
	 * 向数据库更新一条QcbVirtualAccountss数据
	 * @param qcbVirtualAccountss
	 * @return QcbVirtualAccountss
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbVirtualAccountss", key = "'qcbVirtualAccountss:' + #qcbVirtualAccounts.uuid")})
	public QcbVirtualAccounts update(QcbVirtualAccounts qcbVirtualAccounts) {
		return super.update(qcbVirtualAccounts);
	}

	/**
	 * 根据uuid得到一个QcbVirtualAccountss信息
	 * @param uuid
	 * @return QcbVirtualAccountss
	 */
	@Override
	@Cacheable(value = "qcbVirtualAccountss", key = "'qcbVirtualAccountss:' + #uuid")
	public QcbVirtualAccounts get(String uuid) {
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
		builder.append(" select qva.uuid,qva.qcb_company as qcbCompany,qva.company_account as companyAccount,"
				+ "qva.account_num as accountNum,qva.status,qva.createtime,qva.creator"
				+ " from qcb_virtual_accounts qva  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qva.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and qva.company_account = '" + inputParams.get("companyAccount") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qva.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qva.status in ('normal','used') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qva.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qva.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_virtual_accounts qva where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qva.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and qva.company_account = '" + inputParams.get("companyAccount") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qva.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qva.status in ('normal','used') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select qva.status, count(*) as count from qcb_virtual_accounts qva where 1=1");
		builder.append(" and qva.status in ('normal','used') ");
		builder.append(" group by qva.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 批量添加
	 * @param paras
	 */
	@Override
	public void batchAdd(List<Object[]> paras) {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into qcb_virtual_accounts(uuid,account_num,company_account,status,creator,createtime)"
				+ " values(?,?,?,?,?,?)");
		super.insert(builder.toString(), paras);
	}
	
	/**
	 * 批量删除
	 * @param companyAccount
	 * @param start
	 * @param end
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbVirtualAccountss", allEntries=true)})
	public void batchDelete(String companyAccount, String start, String end) {
		StringBuilder builder = new StringBuilder();
		builder.append("update qcb_virtual_accounts set status='delete', account_num=concat(account_num,'_#',uuid)");
		builder.append(" where status='normal' and company_account='").append(companyAccount).append("' ");
		builder.append(" and length(account_num) = ").append(start.length()).append(" and account_num >= ").append(start);
		builder.append(" and account_num <= ").append(end);
		super.executeSQL(builder.toString());
	}
	
	/**
	 * 根据条件查询Uuid列表
	 * @param inputParams
	 * @return
	 */
	public List<String> getAccountNumByParams(Map<String, String> inputParams){
		StringBuilder builder = new StringBuilder();
		builder.append(" select account_num from qcb_virtual_accounts where 1=1");
		
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and company_account = '" + inputParams.get("companyAccount") + "' ");
		}
		
		if (inputParams.get("start") != null && !"".equals(inputParams.get("start"))) {
			builder.append(" and length(account_num) = " + inputParams.get("start").length());
			builder.append(" and account_num >= '" + inputParams.get("start") + "' ");
		}
		
		if (inputParams.get("end") != null && !"".equals(inputParams.get("end"))) {
			builder.append(" and account_num <= '" + inputParams.get("end") + "' ");
		}
		builder.append(" and status in ('normal','used')");
		List<Object> objList = super.getObjectBySQL(builder.toString(), -1, -1, null);
		
		List<String> list = new ArrayList<String>();
		for(Object obj :objList){
			list.add(obj.toString());
		}
		
		return list;
	}
}
