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

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdminMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAdminMenuDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyAdminMenuDAO extends BaseDAO<QcbCompanyAdminMenu, String> implements IQcbCompanyAdminMenuDAO {


	/**
	 * 向数据库添加一条qcbCompanyAdminMenu数据
	 * @param qcbCompanyAdminMenu
	 * @return qcbCompanyAdminMenu
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyAdminMenus", key = "'qcbCompanyAdminMenus:' + #qcbCompanyAdminMenu.uuid")})
	public QcbCompanyAdminMenu insert(QcbCompanyAdminMenu qcbCompanyAdminMenu) {
		return super.insert(qcbCompanyAdminMenu);
	}

	/**
	 * 向数据库删除一条qcbCompanyAdminMenu数据
	 * @param qcbCompanyAdminMenu
	 * @return qcbCompanyAdminMenu
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyAdminMenus", key = "'qcbCompanyAdminMenus:' + #qcbCompanyAdminMenu.uuid")})
	public QcbCompanyAdminMenu delete(QcbCompanyAdminMenu qcbCompanyAdminMenu) {
		return super.delete(qcbCompanyAdminMenu);
	}

	/**
	 * 向数据库更新一条QcbCompanyAdminMenus数据
	 * @param qcbCompanyAdminMenus
	 * @return QcbCompanyAdminMenus
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyAdminMenus", key = "'qcbCompanyAdminMenus:' + #qcbCompanyAdminMenu.uuid")})
	public QcbCompanyAdminMenu update(QcbCompanyAdminMenu qcbCompanyAdminMenu) {
		return super.update(qcbCompanyAdminMenu);
	}

	/**
	 * 根据uuid得到一个QcbCompanyAdminMenus信息
	 * @param uuid
	 * @return QcbCompanyAdminMenus
	 */
	@Override
	@Cacheable(value = "qcbCompanyAdminMenus", key = "'qcbCompanyAdminMenus:' + #uuid")
	public QcbCompanyAdminMenu get(String uuid) {
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
		builder.append(" select qcam.uuid,qcam.qcb_company as qcbCompany,qcam.qcb_company_admin as qcbCompanyAdmin,qcam.qcb_menu as qcbMenu,"
				+ " qcam.qcb_admin as qcbAdmin,qcam.createtime,qcam.creator"
				+ " from qcb_company_admin_menu qcam  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcam.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcam.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("qcbAdmin") != null && !"".equals(inputParams.get("qcbAdmin"))) {
			builder.append(" and qcam.qcb_admin = '" + inputParams.get("qcbAdmin") + "' ");
		}
		
		if (inputParams.get("qcbCompanyAdmin") != null && !"".equals(inputParams.get("qcbCompanyAdmin"))) {
			builder.append(" and qcam.qcb_company_admin = '" + inputParams.get("qcbCompanyAdmin") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and qcam.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcam.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcam.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcam.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_admin_menu qcam where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcam.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcam.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("qcbAdmin") != null && !"".equals(inputParams.get("qcbAdmin"))) {
			builder.append(" and qcam.qcb_admin = '" + inputParams.get("qcbAdmin") + "' ");
		}
		
		if (inputParams.get("qcbCompanyAdmin") != null && !"".equals(inputParams.get("qcbCompanyAdmin"))) {
			builder.append(" and qcam.qcb_company_admin = '" + inputParams.get("qcbCompanyAdmin") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and qcam.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcam.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyAdminMenus", allEntries=true)})
	public void delete(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" delete from qcb_company_admin_menu where 1=1 ");
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("qcbAdmin") != null && !"".equals(inputParams.get("qcbAdmin"))) {
			builder.append(" and qcb_admin = '" + inputParams.get("qcbAdmin") + "' ");
		}
		
		super.executeSQL(builder.toString());
	}
}
