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
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxSecurityOrderDAO;

/**
 * @author hehe
 *
 */

@Repository
public class ShbxSecurityOrderDAO extends BaseDAO<ShbxSecurityOrder, String> implements IShbxSecurityOrderDAO {


	/**
	 * 向数据库添加一条ShbxSecurityOrder数据
	 * @param ShbxSecurityOrderInfoVO
	 * @return ShbxSecurityOrder
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxSecurityOrders", key = "'shbxSecurityOrders:' + #shbxSecurityOrder.uuid")})
	public ShbxSecurityOrder insert(ShbxSecurityOrder shbxSecurityOrder) {
		return super.insert(shbxSecurityOrder);
	}

	/**
	 * 向数据库删除一条ShbxSecurityOrder数据
	 * @param ShbxSecurityOrderInfoVO
	 * @return ShbxSecurityOrder
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "shbxSecurityOrders", key = "'shbxSecurityOrders:' + #shbxSecurityOrder.uuid")})
	public ShbxSecurityOrder delete(ShbxSecurityOrder shbxSecurityOrder) {
		return super.delete(shbxSecurityOrder);
	}

	/**
	 * 向数据库更新一条ShbxSecurityOrder数据
	 * @param ShbxSecurityOrderInfoVO
	 * @return ShbxSecurityOrder
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxSecurityOrders", key = "'shbxSecurityOrders:' + #shbxSecurityOrder.uuid")})
	public ShbxSecurityOrder update(ShbxSecurityOrder shbxSecurityOrder) {
		return super.update(shbxSecurityOrder);
	}

	/**
	 * 根据uuid得到一个ShbxSecurityOrder信息
	 * @param uuid
	 * @return ShbxSecurityOrder
	 */
	@Override
	@Cacheable(value = "shbxSecurityOrders", key = "'shbxSecurityOrders:' + #uuid")
	public ShbxSecurityOrder get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams){
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from shbx_security_order shso "
				+ "left join shbx_insured si on shso.shbx_insured = si.uuid where 1=1 ");
		//uuid
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and shso.uuid = '" + inputParams.get("uuid") + "' ");
		}
		//name
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and si.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("orderNumber") != null && !"".equals(inputParams.get("orderNumber"))) {
			builder.append(" and shso.order_number = '" + inputParams.get("orderNumber") + "' ");
		}

		if (inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))) {
			builder.append(" and shso.creator = '" + inputParams.get("shbxUser") + "' ");
		}
		
		if (inputParams.get("shbxInsured") != null && !"".equals(inputParams.get("shbxInsured"))) {
			builder.append(" and shso.shbx_insured = '" + inputParams.get("shbxInsured") + "' ");
		}

		if (inputParams.get("shbxUserHistory") != null && !"".equals(inputParams.get("shbxUserHistory"))) {
			builder.append(" and shso.shbx_user_history = '" + inputParams.get("shbxUserHistory") + "' ");
		}

		if (inputParams.get("insuredType") != null && !"".equals(inputParams.get("insuredType"))) {
			builder.append(" and shso.insured_type = '" + inputParams.get("insuredType") + "' ");
		}
		
		if (inputParams.get("duration") != null && !"".equals(inputParams.get("duration"))) {
			builder.append(" and shso.duration = '" + inputParams.get("duration") + "' ");
		}
		
		if (inputParams.get("housingFund") != null && !"".equals(inputParams.get("housingFund"))) {
			builder.append(" and shso.housing_fund = " + inputParams.get("housingFund") + " ");
		}
		
		if (inputParams.get("cardinalNumber") != null && !"".equals(inputParams.get("cardinalNumber"))) {
			builder.append(" and shso.cardinal_number = " + inputParams.get("duration") + " ");
		}
		
		if (inputParams.get("benefits") != null && !"".equals(inputParams.get("benefits"))) {
			builder.append(" and shso.benefits = " + inputParams.get("benefits") + " ");
		}
		
		if (inputParams.get("serviceFee") != null && !"".equals(inputParams.get("serviceFee"))) {
			builder.append(" and shso.service_fee = " + inputParams.get("serviceFee") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and shso.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and shso.status in ('normal','unprocess','unpay','payed','fail') ");
		}

		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and shso.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and shso.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and shso.starttime <= '" + inputParams.get("starttime") + "' ");
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
		builder.append(" select shso.uuid,shso.order_number as orderNumber,shso.shbx_insured as shbxInsured,shso.starttime,shso.duration,"
				+ " shso.housing_fund as housingFund,"
				+ " shso.cardinal_number as cardinalNumber, shso.benefits, shso.service_fee as serviceFee, shso.shbx_user_history as shbxUserHistory,"
				+ " shso.createtime,"
				+ " shso.status,shso.creator,shso.company,shso.receipt,shso.insured_type as insuredType,"
				+ " shso.source_company as sourceCompany, shso.process_creator as processCreator , shso.process_createtime as processCreatetime"
				+ " from shbx_security_order shso "
				+ " left join shbx_insured si on shso.shbx_insured = si.uuid where 1=1 ");
		//uuid
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and shso.uuid = '" + inputParams.get("uuid") + "' ");
		}
		//name
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and si.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("orderNumber") != null && !"".equals(inputParams.get("orderNumber"))) {
			builder.append(" and shso.order_number = '" + inputParams.get("orderNumber") + "' ");
		}

		if (inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))) {
			builder.append(" and shso.creator = '" + inputParams.get("shbxUser") + "' ");
		}
		
		if (inputParams.get("shbxInsured") != null && !"".equals(inputParams.get("shbxInsured"))) {
			builder.append(" and shso.shbx_insured = '" + inputParams.get("shbxInsured") + "' ");
		}

		if (inputParams.get("shbxUserHistory") != null && !"".equals(inputParams.get("shbxUserHistory"))) {
			builder.append(" and shso.shbx_user_history = '" + inputParams.get("shbxUserHistory") + "' ");
		}

		if (inputParams.get("insuredType") != null && !"".equals(inputParams.get("insuredType"))) {
			builder.append(" and shso.insured_type = '" + inputParams.get("insuredType") + "' ");
		}
		
		if (inputParams.get("duration") != null && !"".equals(inputParams.get("duration"))) {
			builder.append(" and shso.duration = '" + inputParams.get("duration") + "' ");
		}
		
		if (inputParams.get("housingFund") != null && !"".equals(inputParams.get("housingFund"))) {
			builder.append(" and shso.housing_fund = " + inputParams.get("housingFund") + " ");
		}
		
		if (inputParams.get("cardinalNumber") != null && !"".equals(inputParams.get("cardinalNumber"))) {
			builder.append(" and shso.cardinal_number = " + inputParams.get("duration") + " ");
		}
		
		if (inputParams.get("benefits") != null && !"".equals(inputParams.get("benefits"))) {
			builder.append(" and shso.benefits = " + inputParams.get("benefits") + " ");
		}
		
		if (inputParams.get("serviceFee") != null && !"".equals(inputParams.get("serviceFee"))) {
			builder.append(" and shso.service_fee = " + inputParams.get("serviceFee") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and shso.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and shso.status in ('normal','unprocess','unpay','payed','fail') ");
		}

		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and shso.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and shso.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and shso.starttime <= '" + inputParams.get("starttime") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("shso.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by shso.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("select sso.status, count(*) as count from shbx_security_order sso group by sso.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
