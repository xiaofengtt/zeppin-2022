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

import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeCouponHistoryDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCouponHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class QcbEmployeeCouponHistoryDAO extends BaseDAO<QcbEmployeeCouponHistory, String> implements IQcbEmployeeCouponHistoryDAO {
	/**
	 * 向数据库添加一条QcbEmployeeCouponHistory数据
	 * 向缓存qcbEmployeeCouponHistorys添加一条QcbEmployeeCouponHistory记录，key值为uuid
	 * @param qcbEmployeeCouponHistory
	 * @return qcbEmployeeCouponHistory
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployeeCouponHistorys", key = "'qcbEmployeeCouponHistorys:' + #qcbEmployeeCouponHistory.uuid")}, evict={@CacheEvict(value = "listQcbEmployeeCouponHistorys", allEntries = true)})
	public QcbEmployeeCouponHistory insert(QcbEmployeeCouponHistory qcbEmployeeCouponHistory) {
		return super.insert(qcbEmployeeCouponHistory);
	}

	/**
	 * 向数据库删除一条QcbEmployeeCouponHistory数据
	 * 在缓存qcbEmployeeCouponHistorys中删除一条QcbEmployeeCouponHistory记录，key值为uuid
	 * @param qcbEmployeeCouponHistory
	 * @return qcbEmployeeCouponHistory
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbEmployeeCouponHistorys", key = "'qcbEmployeeCouponHistorys:' + #qcbEmployeeCouponHistory.uuid"), @CacheEvict(value = "listQcbEmployeeCouponHistorys", allEntries = true)})
	public QcbEmployeeCouponHistory delete(QcbEmployeeCouponHistory qcbEmployeeCouponHistory) {
		return super.delete(qcbEmployeeCouponHistory);
	}

	/**
	 * 向数据库更新一条QcbEmployeeCouponHistory数据
	 * 在缓存qcbEmployeeCouponHistorys中更新一条QcbEmployeeCouponHistory记录，key值为uuid
	 * @param qcbEmployeeCouponHistory
	 * @return qcbEmployeeCouponHistory
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployeeCouponHistorys", key = "'qcbEmployeeCouponHistorys:' + #qcbEmployeeCouponHistory.uuid")}, evict={@CacheEvict(value = "listQcbEmployeeCouponHistorys", allEntries = true)})
	public QcbEmployeeCouponHistory update(QcbEmployeeCouponHistory qcbEmployeeCouponHistory) {
		return super.update(qcbEmployeeCouponHistory);
	}

	/**
	 * 根据uuid得到一个QcbEmployeeCouponHistory信息
	 * 向缓存qcbEmployeeCouponHistorys添加一条QcbEmployeeCouponHistory记录，key值为uuid
	 * @param uuid
	 * @return qcbEmployeeCouponHistory
	 */
	@Override
	@Cacheable(value = "qcbEmployeeCouponHistorys", key = "'qcbEmployeeCouponHistorys:' + #uuid")
	public QcbEmployeeCouponHistory get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select b.* from qcb_employee_coupon_history b order by b.createtime", resultClass);
	}

	@Override
	@Cacheable(value = "listQcbEmployeeCouponHistorys")
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.qcb_employee_product_buy as qcbEmployeeProductBuy, b.coupon, b.price, b.createtime, b.creator, b.status,"
				+ " c.coupon_price as couponPrice, c.coupon_name as couponName, c.coupon_type as couponType "
				+ " from qcb_employee_coupon_history b left join coupon c on b.coupon=c.uuid where 1=1 ");
		//名称
		if (inputParams.get("qcbEmployeeProductBuy") != null && !"".equals(inputParams.get("qcbEmployeeProductBuy"))) {
			builder.append(" and b.qcb_employee_product_buy = '" + inputParams.get("qcbEmployeeProductBuy") + "' ");
		}
		
		if (inputParams.get("coupon") != null && !"".equals(inputParams.get("coupon"))) {
			builder.append(" and b.coupon = '" + inputParams.get("coupon") + "'");
		}
		
		if (inputParams.get("price") != null && !"".equals(inputParams.get("price"))) {
			builder.append(" and b.price = " + inputParams.get("price"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("qcbEmployeeHistory") != null && !"".equals(inputParams.get("qcbEmployeeHistory"))) {
			builder.append(" and b.qcb_employee_history = '" + inputParams.get("qcbEmployeeHistory") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and b.status in ('normal') ");
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
			builder.append(" order by b.createtime desc");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_employee_coupon_history b where 1=1 ");
		
		//名称
		if (inputParams.get("qcbEmployeeProductBuy") != null && !"".equals(inputParams.get("qcbEmployeeProductBuy"))) {
			builder.append(" and b.qcb_employee_product_buy = '" + inputParams.get("qcbEmployeeProductBuy") + "' ");
		}
		
		if (inputParams.get("coupon") != null && !"".equals(inputParams.get("coupon"))) {
			builder.append(" and b.coupon = '" + inputParams.get("coupon") + "'");
		}
		
		if (inputParams.get("price") != null && !"".equals(inputParams.get("price"))) {
			builder.append(" and b.price = " + inputParams.get("price"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("qcbEmployeeHistory") != null && !"".equals(inputParams.get("qcbEmployeeHistory"))) {
			builder.append(" and b.qcb_employee_history = '" + inputParams.get("qcbEmployeeHistory") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and b.status in ('normal') ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	@Caching(evict={@CacheEvict(value = "listQcbEmployeeCouponHistorys", allEntries = true)})
	public void insert(List<Object[]> paras) {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into qcb_employee_coupon_history(uuid,qcb_employee_product_buy,coupon,price,createtime,creator,status,qcb_employee_history) values(?,?,?,?,?,?,?,?)");
		super.insert(builder.toString(), paras);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "qcbEmployeeCouponHistorys", allEntries = true), @CacheEvict(value = "listQcbEmployeeCouponHistorys", allEntries = true)})
	public void update(List<Object[]> paras) {
		StringBuilder builder = new StringBuilder();
		builder.append("update qcb_employee_coupon_history set dividend=? where uuid=? ");
		super.insert(builder.toString(), paras);
	}

}
