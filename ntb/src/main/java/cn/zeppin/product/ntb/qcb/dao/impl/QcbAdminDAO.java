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
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbAdminDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbAdminDAO extends BaseDAO<QcbAdmin, String> implements IQcbAdminDAO {


	/**
	 * 向数据库添加一条qcbAdmin数据
	 * @param qcbAdmin
	 * @return qcbAdmin
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbAdmins", key = "'qcbAdmins:' + #qcbAdmin.uuid")})
	public QcbAdmin insert(QcbAdmin qcbAdmin) {
		return super.insert(qcbAdmin);
	}

	/**
	 * 向数据库删除一条qcbAdmin数据
	 * @param qcbAdmin
	 * @return qcbAdmin
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbAdmins", key = "'qcbAdmins:' + #qcbAdmin.uuid")})
	public QcbAdmin delete(QcbAdmin qcbAdmin) {
		return super.delete(qcbAdmin);
	}

	/**
	 * 向数据库更新一条QcbAdmins数据
	 * @param qcbAdmins
	 * @return QcbAdmins
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbAdmins", key = "'qcbAdmins:' + #qcbAdmin.uuid")})
	public QcbAdmin update(QcbAdmin qcbAdmin) {
		return super.update(qcbAdmin);
	}

	/**
	 * 根据uuid得到一个QcbAdmins信息
	 * @param uuid
	 * @return QcbAdmins
	 */
	@Override
	@Cacheable(value = "qcbAdmins", key = "'qcbAdmins:' + #uuid")
	public QcbAdmin get(String uuid) {
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
		builder.append(" select qa.uuid,qa.name,qa.mobile,qa.password,qa.status,qa.createtime,qa.wechat_num as wechatNum,qa.wechat_flag as wechatFlag,"
				+ "qa.last_login_time as lastLoginTime,qa.last_login_ip as lastLoginIp,qa.password_level as passwordLevel "
				+ " from qcb_admin qa  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qa.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qa.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and qa.mobile = '" + inputParams.get("mobile") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qa.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qa.status in ('normal','disable') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qa.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qa.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_admin qa where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qa.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qa.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and qa.mobile = '" + inputParams.get("mobile") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qa.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qa.status in ('normal','disable') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	

	@Override
	public QcbAdmin getByMobile(String mobile) {
		StringBuilder builder = new StringBuilder();
		builder.append(" from QcbAdmin qa where 1=1");
		builder.append(" and qa.mobile='"+mobile+"'");
//		Object[] paras = {mobile, mobile};
		List<QcbAdmin> operators = this.getByHQL(builder.toString());
		if (operators != null && operators.size() > 0) {
			return operators.get(0);
		}
		return null;
	}
	
	/**
	 * 验证同手机号的企财宝管理员是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistQcbAdminByMobile(String mobile, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from qcb_admin qa where mobile=?0");
		//编辑时检测uuid
		if(uuid != null && !"".equals(uuid)){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {mobile,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
}
