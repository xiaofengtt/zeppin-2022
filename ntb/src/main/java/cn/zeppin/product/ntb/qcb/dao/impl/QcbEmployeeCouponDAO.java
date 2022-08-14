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

import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeCouponDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCoupon;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class QcbEmployeeCouponDAO extends BaseDAO<QcbEmployeeCoupon, String> implements IQcbEmployeeCouponDAO {
	/**
	 * 向数据库添加一条QcbEmployeeCoupon数据
	 * 向缓存qcbEmployeeCoupons添加一条QcbEmployeeCoupon记录，key值为uuid
	 * @param qcbEmployeeCoupon
	 * @return qcbEmployeeCoupon
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployeeCoupons", key = "'qcbEmployeeCoupons:' + #qcbEmployeeCoupon.uuid")}, evict={@CacheEvict(value = "listQcbEmployeeCoupons", allEntries = true)})
	public QcbEmployeeCoupon insert(QcbEmployeeCoupon qcbEmployeeCoupon) {
		return super.insert(qcbEmployeeCoupon);
	}

	/**
	 * 向数据库删除一条QcbEmployeeCoupon数据
	 * 在缓存qcbEmployeeCoupons中删除一条QcbEmployeeCoupon记录，key值为uuid
	 * @param qcbEmployeeCoupon
	 * @return qcbEmployeeCoupon
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbEmployeeCoupons", key = "'qcbEmployeeCoupons:' + #qcbEmployeeCoupon.uuid"), @CacheEvict(value = "listQcbEmployeeCoupons", allEntries = true)})
	public QcbEmployeeCoupon delete(QcbEmployeeCoupon qcbEmployeeCoupon) {
		return super.delete(qcbEmployeeCoupon);
	}

	/**
	 * 向数据库更新一条QcbEmployeeCoupon数据
	 * 在缓存qcbEmployeeCoupons中更新一条QcbEmployeeCoupon记录，key值为uuid
	 * @param qcbEmployeeCoupon
	 * @return qcbEmployeeCoupon
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployeeCoupons", key = "'qcbEmployeeCoupons:' + #qcbEmployeeCoupon.uuid")}, evict={@CacheEvict(value = "listQcbEmployeeCoupons", allEntries = true)})
	public QcbEmployeeCoupon update(QcbEmployeeCoupon qcbEmployeeCoupon) {
		return super.update(qcbEmployeeCoupon);
	}

	/**
	 * 根据uuid得到一个QcbEmployeeCoupon信息
	 * 向缓存qcbEmployeeCoupons添加一条QcbEmployeeCoupon记录，key值为uuid
	 * @param uuid
	 * @return qcbEmployeeCoupon
	 */
	@Override
	@Cacheable(value = "qcbEmployeeCoupons", key = "'qcbEmployeeCoupons:' + #uuid")
	public QcbEmployeeCoupon get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select b.* from qcb_employee_coupon b order by b.createtime", resultClass);
	}

	@Override
	@Cacheable(value = "listQcbEmployeeCoupons")
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.coupon, b.qcb_employee as qcbEmployee, b.createtime, b.creator, b.status, b.expiry_date as expiryDate"
				+ " from qcb_employee_coupon b left join coupon c on b.coupon=c.uuid where 1=1 ");
		//名称
		if (inputParams.get("coupon") != null && !"".equals(inputParams.get("coupon"))) {
			builder.append(" and b.coupon = '" + inputParams.get("coupon") + "' ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and b.qcb_employee = '" + inputParams.get("qcbEmployee") + "'");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status in (" + inputParams.get("status") + ") ");
		} else {
			builder.append(" and b.status in ('unuse','used','expired') ");//不包括删除和未激活的
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and c.coupon_name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("minInvestAccount") != null && !"".equals(inputParams.get("minInvestAccount"))) {
			builder.append(" and c.min_invest_account <= " + inputParams.get("minInvestAccount"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("expiryDate") != null && !"".equals(inputParams.get("expiryDate"))) {
			builder.append(" and b.expiry_date = '" + inputParams.get("expiryDate") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
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
		builder.append(" select count(*) from qcb_employee_coupon b left join coupon c on b.coupon=c.uuid where 1=1 ");
		//名称
		if (inputParams.get("coupon") != null && !"".equals(inputParams.get("coupon"))) {
			builder.append(" and b.coupon = '" + inputParams.get("coupon") + "' ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and b.qcb_employee = '" + inputParams.get("qcbEmployee") + "'");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status in (" + inputParams.get("status") + ") ");
		} else {
			builder.append(" and b.status in ('unuse','used','expired') ");//不包括删除和未激活的
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and c.coupon_name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("minInvestAccount") != null && !"".equals(inputParams.get("minInvestAccount"))) {
			builder.append(" and c.min_invest_account <= " + inputParams.get("minInvestAccount"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("expiryDate") != null && !"".equals(inputParams.get("expiryDate"))) {
			builder.append(" and b.expiry_date = '" + inputParams.get("expiryDate") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public void insert(List<Object[]> paras) {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into qcb_employee_coupon(uuid,coupon,qcb_employee,createtime,creator,status,expiry_date) values(?,?,?,?,?,?,?)");
		super.insert(builder.toString(), paras);
	}

	@Override
	public void updateExpiryDate() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update qcb_employee_coupon set status='expired' where status<>'expired' and ");
		builder.append(" unix_timestamp(expiry_date) < unix_timestamp('").append(time).append("') ");
		super.executeSQL(builder.toString());
	}

}
