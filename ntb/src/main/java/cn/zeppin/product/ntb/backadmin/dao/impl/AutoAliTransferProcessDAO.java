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

import cn.zeppin.product.ntb.backadmin.dao.api.IAutoAliTransferProcessDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.AutoAliTransferProcess;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class AutoAliTransferProcessDAO extends BaseDAO<AutoAliTransferProcess, String> implements IAutoAliTransferProcessDAO {


	/**
	 * 向数据库添加一条autoAliTransferProcess数据
	 * 向缓存autoAliTransferProcesss添加一条autoAliTransferProcess记录，key值为uuid
	 * 清空缓存listPageAutoAliTransferProcesss,listAutoAliTransferProcesss的所有记录
	 * @param autoAliTransferProcess
	 * @return AutoAliTransferProcess
	 */
	@Override
	@Caching(put={@CachePut(value = "autoAliTransferProcesss", key = "'autoAliTransferProcesss:' + #autoAliTransferProcess.uuid")},
	evict={@CacheEvict(value = "listPageAutoAliTransferProcesss", allEntries = true), @CacheEvict(value = "listAutoAliTransferProcesss", allEntries = true)})
	public AutoAliTransferProcess insert(AutoAliTransferProcess autoAliTransferProcess) {
		return super.insert(autoAliTransferProcess);
	}

	/**
	 * 向数据库删除一条autoAliTransferProcess数据
	 * 在缓存banks中删除一条autoAliTransferProcess记录，key值为uuid
	 * 清空缓存listPageautoAliTransferProcesss,listautoAliTransferProcesss的所有记录
	 * @param autoAliTransferProcess
	 * @return autoAliTransferProcess
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "autoAliTransferProcesss", key = "'autoAliTransferProcesss:' + #autoAliTransferProcess.uuid"),
			@CacheEvict(value = "listPageAutoAliTransferProcesss", allEntries = true),@CacheEvict(value = "listAutoAliTransferProcesss", allEntries = true)})
	public AutoAliTransferProcess delete(AutoAliTransferProcess autoAliTransferProcess) {
		return super.delete(autoAliTransferProcess);
	}

	/**
	 * 向数据库更新一条autoAliTransferProcess数据
	 * 在缓存autoAliTransferProcesss中更新一条autoAliTransferProcess记录，key值为uuid
	 * 清空缓存listPageautoAliTransferProcesss,listautoAliTransferProcesss的所有记录
	 * @param autoAliTransferProcess
	 * @return autoAliTransferProcess
	 */
	@Override
	@Caching(put={@CachePut(value = "autoAliTransferProcesss", key = "'autoAliTransferProcesss:' + #autoAliTransferProcess.uuid")},
	evict={@CacheEvict(value = "listPageAutoAliTransferProcesss", allEntries = true),@CacheEvict(value = "listAutoAliTransferProcesss", allEntries = true)})
	public AutoAliTransferProcess update(AutoAliTransferProcess autoAliTransferProcess) {
		return super.update(autoAliTransferProcess);
	}

	/**
	 * 根据uuid得到一个autoAliTransferProcess信息
	 * 向缓存autoAliTransferProcesss添加一条autoAliTransferProcess记录，key值为uuid
	 * @param uuid
	 * @return autoAliTransferProcess
	 */
	@Override
	@Cacheable(value = "autoAliTransferProcesss", key = "'autoAliTransferProcesss:' + #uuid")
	public AutoAliTransferProcess get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表,
	 * 将查询结果存入缓存listautoAliTransferProcesss，key为所有参数名+参数值
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listAutoAliTransferProcesss")
	public List<Entity> getList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ac.uuid, ac.process_time as processtime, ac.process_count as processcount, ac.status, ac.createtime, ac.process_index as processindex from auto_ali_transfer_process ac where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ac.uuid = '" + inputParams.get("uuid") + "' ");
		}
		if (inputParams.get("processtime") != null && !"".equals(inputParams.get("processtime"))) {
			builder.append(" and ac.process_time = '" + inputParams.get("process_time") + "' ");
		}
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and ac.createtime = '" + inputParams.get("createtime") + "' ");
		}
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ac.status = '" + inputParams.get("status") + "' ");
		}
		if (inputParams.get("processcount") != null && !"".equals(inputParams.get("processcount"))) {
			builder.append(" and ac.process_count = " + inputParams.get("processcount"));
		}
		
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPageautoAliTransferProcesss，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageAutoAliTransferProcesss")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ac.uuid, ac.process_time as processtime, ac.process_count as processcount, ac.status, ac.createtime, ac.process_index as processindex from auto_ali_transfer_process ac where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ac.uuid = '" + inputParams.get("uuid") + "' ");
		}
		if (inputParams.get("processtime") != null && !"".equals(inputParams.get("processtime"))) {
			builder.append(" and ac.process_time = '" + inputParams.get("process_time") + "' ");
		}
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and ac.createtime = '" + inputParams.get("createtime") + "' ");
		}
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ac.status = '" + inputParams.get("status") + "' ");
		}
		if (inputParams.get("processcount") != null && !"".equals(inputParams.get("processcount"))) {
			builder.append(" and ac.process_count = " + inputParams.get("processcount"));
		}
		if (inputParams.get("processindex") != null && !"".equals(inputParams.get("processindex"))) {
			builder.append(" and ac.process_index = " + inputParams.get("processindex"));
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("ac.").append(sort);
				comma = ",";
			}
		}else {
			builder.append(" order by ac.createtime asc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from auto_ali_transfer_process ac where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ac.uuid = '" + inputParams.get("uuid") + "' ");
		}
		if (inputParams.get("processtime") != null && !"".equals(inputParams.get("processtime"))) {
			builder.append(" and ac.process_time = '" + inputParams.get("process_time") + "' ");
		}
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and ac.createtime = '" + inputParams.get("createtime") + "' ");
		}
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ac.status = '" + inputParams.get("status") + "' ");
		}
		if (inputParams.get("processcount") != null && !"".equals(inputParams.get("processcount"))) {
			builder.append(" and ac.process_count = " + inputParams.get("processcount"));
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

}
