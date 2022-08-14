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

import cn.zeppin.product.ntb.backadmin.dao.api.IOrderinfoByThirdpartyDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class OrderinfoByThirdpartyDAO extends BaseDAO<OrderinfoByThirdparty, String> implements IOrderinfoByThirdpartyDAO {


	/**
	 * 向数据库添加一条OrderinfoByThirdparty数据
	 * @param OrderinfoByThirdparty
	 * @return OrderinfoByThirdparty
	 */
	@Override
	@Caching(put={@CachePut(value = "orderinfoByThirdpartys", key = "'orderinfoByThirdpartys:' + #orderinfoByThirdparty.uuid")})
	public OrderinfoByThirdparty insert(OrderinfoByThirdparty orderinfoByThirdparty) {
		return super.insert(orderinfoByThirdparty);
	}

	/**
	 * 向数据库删除一条OrderinfoByThirdparty数据
	 * @param OrderinfoByThirdparty
	 * @return OrderinfoByThirdparty
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "orderinfoByThirdpartys", key = "'orderinfoByThirdpartys:' + #orderinfoByThirdparty.uuid")})
	public OrderinfoByThirdparty delete(OrderinfoByThirdparty orderinfoByThirdparty) {
		return super.delete(orderinfoByThirdparty);
	}

	/**
	 * 向数据库更新一条OrderinfoByThirdparty数据
	 * @param OrderinfoByThirdparty
	 * @return OrderinfoByThirdparty
	 */
	@Override
	@Caching(put={@CachePut(value = "orderinfoByThirdpartys", key = "'orderinfoByThirdpartys:' + #orderinfoByThirdparty.uuid")})
	public OrderinfoByThirdparty update(OrderinfoByThirdparty orderinfoByThirdparty) {
		return super.update(orderinfoByThirdparty);
	}

	/**
	 * 根据uuid得到一个OrderinfoByThirdparty信息
	 * @param uuid
	 * @return OrderinfoByThirdparty
	 */
	@Override
	@Cacheable(value = "orderinfoByThirdpartys", key = "'orderinfoByThirdpartys:' + #uuid")
	public OrderinfoByThirdparty get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询OrderinfoByThirdparty总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams){
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from orderinfo_by_thirdparty obt where 1=1 ");
		//名称
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and obt.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("orderNum") != null && !"".equals(inputParams.get("orderNum"))) {
			builder.append(" and obt.order_num = '" + inputParams.get("orderNum") + "' ");
		}
		
		if (inputParams.get("payNum") != null && !"".equals(inputParams.get("payNum"))) {
			builder.append(" and obt.pay_num = '" + inputParams.get("payNum") + "' ");
		}
		
		if (inputParams.get("paySource") != null && !"".equals(inputParams.get("paySource"))) {
			builder.append(" and obt.pay_source = '" + inputParams.get("paySource") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and obt.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and obt.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("paytime") != null && !"".equals(inputParams.get("paytime"))) {
			builder.append(" and obt.pay_time <= '" + inputParams.get("paytime") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
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
		builder.append(" select obt.uuid,obt.type,obt.order_num as orderNum,obt.pay_num as payNum,obt.prepay_id as prepayId,obt.body,"
				+ " obt.total_fee as totalFee, obt.pay_source as paySource, obt.pay_time as paytime, obt.status, obt.message,"
				+ " obt.createtime, obt.err_code_des as errCodeDes, obt.err_code as errCode, obt.investor "
				+ " from orderinfo_by_thirdparty obt where 1=1 ");
		//名称
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and obt.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("orderNum") != null && !"".equals(inputParams.get("orderNum"))) {
			builder.append(" and obt.order_num = '" + inputParams.get("orderNum") + "' ");
		}
		
		if (inputParams.get("payNum") != null && !"".equals(inputParams.get("payNum"))) {
			builder.append(" and obt.pay_num = '" + inputParams.get("payNum") + "' ");
		}
		
		if (inputParams.get("paySource") != null && !"".equals(inputParams.get("paySource"))) {
			builder.append(" and obt.pay_source = '" + inputParams.get("paySource") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and obt.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and obt.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("paytime") != null && !"".equals(inputParams.get("paytime"))) {
			builder.append(" and obt.pay_time <= '" + inputParams.get("paytime") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("obt.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by obt.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
}
