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

import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeProductBuyDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class QcbEmployeeProductBuyDAO extends BaseDAO<QcbEmployeeProductBuy, String> implements IQcbEmployeeProductBuyDAO {


	/**
	 * 向数据库添加一条QcbEmployeeProductBuy数据
	 * 向缓存qcbEmployeeProductBuys添加一条QcbEmployeeProductBuy记录，key值为uuid
	 * 清空缓存listQcbEmployeeProductBuys的所有记录
	 * @param qcbEmployeeProductBuy
	 * @return QcbEmployeeProductBuy
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployeeProductBuys", key = "'qcbEmployeeProductBuy:' + #qcbEmployeeProductBuy.uuid")}, evict={@CacheEvict(value = "listQcbEmployeeProductBuys", allEntries = true)})
	public QcbEmployeeProductBuy insert(QcbEmployeeProductBuy qcbEmployeeProductBuy) {
		return super.insert(qcbEmployeeProductBuy);
	}

	/**
	 * 数据库删除一条QcbEmployeeProductBuy数据
	 * 在缓存qcbEmployeeProductBuys中删除一条QcbEmployeeProductBuy记录，key值为uuid
	 * 清空缓存listQcbEmployeeProductBuys的所有记录
	 * @param qcbEmployeeProductBuy
	 * @return QcbEmployeeProductBuy
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbEmployeeProductBuys", key = "'qcbEmployeeProductBuy:' + #qcbEmployeeProductBuy.uuid"), @CacheEvict(value = "listQcbEmployeeProductBuys", allEntries = true)})
	public QcbEmployeeProductBuy delete(QcbEmployeeProductBuy qcbEmployeeProductBuy) {
		return super.delete(qcbEmployeeProductBuy);
	}

	/**
	 * 向数据库更新一条QcbEmployeeProductBuy数据
	 * 在缓存qcbEmployeeProductBuys中更新一条QcbEmployeeProductBuy记录，key值为uuid
	 * 清空缓存listQcbEmployeeProductBuys的所有记录
	 * @param qcbEmployeeProductBuy
	 * @return QcbEmployeeProductBuy
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployeeProductBuys", key = "'qcbEmployeeProductBuy:' + #qcbEmployeeProductBuy.uuid")}, evict={@CacheEvict(value = "listQcbEmployeeProductBuys", allEntries = true)})
	public QcbEmployeeProductBuy update(QcbEmployeeProductBuy qcbEmployeeProductBuy) {
		return super.update(qcbEmployeeProductBuy);
	}

	/**
	 * 根据uuid得到一个QcbEmployeeProductBuy信息
	 * 向缓存qcbEmployeeProductBuys添加一条QcbEmployeeProductBuy记录，key值为uuid
	 * 清空缓存listQcbEmployeeProductBuys的所有记录
	 * @param uuid
	 * @return QcbEmployeeProductBuy
	 */
	@Override
	@Cacheable(cacheNames = "qcbEmployeeProductBuys", key = "'qcbEmployeeProductBuy:' + #uuid")
	public QcbEmployeeProductBuy get(String uuid) {
		return super.get(uuid);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_employee_product_buy qepb where 1=1 ");
		//名称
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qepb.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qepb.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("totalAmount") != null && !"".equals(inputParams.get("totalAmount"))) {
			builder.append(" and qepb.total_amount = " + inputParams.get("totalAmount") + " ");
		}
		
		if (inputParams.get("totalReturn") != null && !"".equals(inputParams.get("totalReturn"))) {
			builder.append(" and qepb.total_return = " + inputParams.get("totalReturn") + " ");
		}
		
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			if("transaction".equals(inputParams.get("stage"))){
				builder.append(" and qepb.stage in ('confirming','balance') ");
			} else {
				builder.append(" and qepb.stage = '" + inputParams.get("stage") + "' ");
			}
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qepb.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Entity> getList(Map<String, String> inputParams, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select qepb.uuid,qepb.qcb_employee as qcbEmployee,qepb.product,qepb.total_amount as totalAmount,qepb.total_return as totalReturn,qepb.stage, "
				+ "qepb.createtime,qepb.total_redeem as totalRedeem,qepb.account_balance as accountBalance, qepb.type from qcb_employee_product_buy qepb where 1=1 ");
		//名称
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qepb.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qepb.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("totalAmount") != null && !"".equals(inputParams.get("totalAmount"))) {
			builder.append(" and qepb.total_amount = " + inputParams.get("totalAmount") + " ");
		}
		
		if (inputParams.get("totalReturn") != null && !"".equals(inputParams.get("totalReturn"))) {
			builder.append(" and qepb.total_return = " + inputParams.get("totalReturn") + " ");
		}
		
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			if("transaction".equals(inputParams.get("stage"))){
				builder.append(" and qepb.stage in ('confirming','balance') ");
			} else {
				builder.append(" and qepb.stage = '" + inputParams.get("stage") + "' ");
			}
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qepb.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qepb.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qepb.createtime desc ");
		}
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select qepb.uuid,qepb.qcb_employee as qcbEmployee,qepb.product,qepb.total_amount as totalAmount,qepb.total_return as totalReturn,qepb.stage, "
				+ "qepb.createtime,qepb.total_redeem as totalRedeem,qepb.account_balance as accountBalance, qepb.type from qcb_employee_product_buy qepb where 1=1 ");
		//名称
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qepb.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qepb.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("totalAmount") != null && !"".equals(inputParams.get("totalAmount"))) {
			builder.append(" and qepb.total_amount = " + inputParams.get("totalAmount") + " ");
		}
		
		if (inputParams.get("totalReturn") != null && !"".equals(inputParams.get("totalReturn"))) {
			builder.append(" and qepb.total_return = " + inputParams.get("totalReturn") + " ");
		}
		
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			if("transaction".equals(inputParams.get("stage"))){
				builder.append(" and qepb.stage in ('confirming','balance') ");
			} else {
				builder.append(" and qepb.stage = '" + inputParams.get("stage") + "' ");
			}
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qepb.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qepb.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qepb.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "listQcbEmployeeProductBuys")})
	public void updateConfirmStage() {
		StringBuilder builder = new StringBuilder();
//		builder.append("update qcb_employee_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='profit' where i.type='bank_product' and i.stage='confirming' and b.stage in ('uninvest','invested','profit') and b.flag_buy=0 ");
		builder.append("update qcb_employee_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='profit' where i.type='bank_product' and i.stage<>'finished' and b.stage in ('uninvest','invested','profit') and b.flag_buy=0 ");//测试使用，上线时改回去
		super.executeSQL(builder.toString());
	}

	@Override
	@Caching(evict={@CacheEvict(value = "listQcbEmployeeProductBuys")})
	public void updatePofitStage() {
		StringBuilder builder = new StringBuilder();
//		builder.append("update qcb_employee_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='balance' where i.type='bank_product' and i.stage='profit' and b.stage in ('balance')");
		builder.append("update qcb_employee_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='balance' where i.type='bank_product' and i.stage<>'finished' and b.stage in ('balance')");//测试使用，上线时改回去
		super.executeSQL(builder.toString());
	}

	@Override
	@Caching(evict={@CacheEvict(value = "listQcbEmployeeProductBuys")})
	public void updateBalanceStage() {
		StringBuilder builder = new StringBuilder();
//		builder.append("update qcb_employee_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='finished' where i.type='bank_product' and i.stage='balance' and b.stage in ('finished')");
		builder.append("update qcb_employee_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='finished' where i.type='bank_product' and i.stage<>'finished' and b.stage in ('finished')");//测试使用，上线时改回去
		super.executeSQL(builder.toString());
	}

	@Override
	public List<Entity> getListForCountPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select qepb.product as product, qepb.type as type, bfpp.term as term, bfpp.target_annualized_return_rate returnRate, date_format(bfpp.maturity_date,'%Y-%m-%d') as createtime,sum(qepb.total_amount) as price "
				+ "from qcb_employee_product_buy qepb left join bank_financial_product_publish bfpp on qepb.product=bfpp.uuid "
				+ "where 1=1 "
//				+ "and qepb.stage<>'finished'"//是否需要查询了历史预期提现金额
				+ " ");
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qepb.type in (" + inputParams.get("type") + ") ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and bfpp.maturity_date >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and bfpp.maturity_date <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		builder.append(" group by qepb.product,date_format(bfpp.maturity_date,'%Y%m%d')");
		
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

}
