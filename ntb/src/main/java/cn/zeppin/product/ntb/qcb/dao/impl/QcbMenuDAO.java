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

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbMenuDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbMenuDAO extends BaseDAO<QcbMenu, String> implements IQcbMenuDAO {


	/**
	 * 向数据库添加一条qcbMenu数据
	 * @param qcbMenu
	 * @return qcbMenu
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbMenus", key = "'qcbMenus:' + #qcbMenu.uuid")})
	public QcbMenu insert(QcbMenu qcbMenu) {
		return super.insert(qcbMenu);
	}

	/**
	 * 向数据库删除一条qcbMenu数据
	 * @param qcbMenu
	 * @return qcbMenu
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbMenus", key = "'qcbMenus:' + #qcbMenu.uuid")})
	public QcbMenu delete(QcbMenu qcbMenu) {
		return super.delete(qcbMenu);
	}

	/**
	 * 向数据库更新一条QcbMenus数据
	 * @param qcbMenus
	 * @return QcbMenus
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbMenus", key = "'qcbMenus:' + #qcbMenu.uuid")})
	public QcbMenu update(QcbMenu qcbMenu) {
		return super.update(qcbMenu);
	}

	/**
	 * 根据uuid得到一个QcbMenus信息
	 * @param uuid
	 * @return QcbMenus
	 */
	@Override
	@Cacheable(value = "qcbMenus", key = "'qcbMenus:' + #uuid")
	public QcbMenu get(String uuid) {
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
		builder.append(" select qm.uuid,qm.name,qm.title,qm.level,qm.scode,qm.pid,qm.sort"
				+ " from qcb_menu qm  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qm.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qm.name like '%" + inputParams.get("name") + "%' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qm.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qm.sort");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_menu qm where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qm.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qm.name like '%" + inputParams.get("name") + "%' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
