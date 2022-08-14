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

import cn.zeppin.product.ntb.backadmin.dao.api.IInformationDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Information;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class InformationDAO extends BaseDAO<Information, String> implements IInformationDAO {
	/**
	 * 向数据库添加一条Information数据
	 * 向缓存informations添加一条Information记录，key值为uuid
	 * @param information
	 * @return information
	 */
	@Override
	@Caching(put={@CachePut(value = "informations", key = "'informations:' + #information.uuid")})
	public Information insert(Information information) {
		return super.insert(information);
	}

	/**
	 * 向数据库删除一条Information数据
	 * 在缓存informations中删除一条Information记录，key值为uuid
	 * @param information
	 * @return information
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "informations", key = "'informations:' + #information.uuid")})
	public Information delete(Information information) {
		return super.delete(information);
	}

	/**
	 * 向数据库更新一条Information数据
	 * 在缓存informations中更新一条Information记录，key值为uuid
	 * @param information
	 * @return information
	 */
	@Override
	@Caching(put={@CachePut(value = "informations", key = "'informations:' + #information.uuid")})
	public Information update(Information information) {
		return super.update(information);
	}

	/**
	 * 根据uuid得到一个Information信息
	 * 向缓存informations添加一条Information记录，key值为uuid
	 * @param uuid
	 * @return information
	 */
	@Override
	@Cacheable(value = "informations", key = "'informations:' + #uuid")
	public Information get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select b.* from information b order by b.createtime", resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.payment, b.flag_switch as flagSwitch, b.createtime, b.creator, b.status, b.payment_des as paymentDes "
				+ "from information b where 1=1 ");
		//名称
		if (inputParams.get("payment") != null && !"".equals(inputParams.get("payment"))) {
			builder.append(" and b.payment = '" + inputParams.get("payment") + "' ");
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (b.payment like '%" + inputParams.get("name") + "%' ");
			builder.append(" or b.payment_des like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("flagSwitch") != null && !"".equals(inputParams.get("flagSwitch"))) {
			builder.append(" and b.flag_switch = " + inputParams.get("flagSwitch"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and b.status = 'normal' ");
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
		builder.append(" select count(*) from information b where 1=1 ");
		//名称
		if (inputParams.get("payment") != null && !"".equals(inputParams.get("payment"))) {
			builder.append(" and b.payment = '" + inputParams.get("payment") + "' ");
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (b.payment like '%" + inputParams.get("name") + "%' ");
			builder.append(" or b.payment_des like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("flagSwitch") != null && !"".equals(inputParams.get("flagSwitch"))) {
			builder.append(" and b.flag_switch = " + inputParams.get("flagSwitch"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and b.status = 'normal' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

//	@Override
//	public boolean isExistInformation(String name, String uuid) {
//		// TODO Auto-generated method stub
//		StringBuilder builder = new StringBuilder();
//		builder.append(" select 1 from information b where payment=?0 ");
//		//编辑时检测uuid
//		if(uuid != null){
//			builder.append(" and uuid != ?1");
//		}
//		Object[] paras = {name,uuid};
//		Object result = super.getResultBySQL(builder.toString(), paras);
//		if (result != null) {
//			return true;
//		}
//		return false;
//	}
}
