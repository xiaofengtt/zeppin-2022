/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkOperatorDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author Clark.R 2016年9月21日
 *
 */

@Repository
public class BkOperatorDAO extends BaseDAO<BkOperator, String> implements IBkOperatorDAO {

	/**
	 * 通过登录账号得到Operator对象，登录账号可能是账号、手机号或邮箱
	 * @param loginname
	 * @return 
	 */
	@Override
	public BkOperator getByLoginname(String loginname) {
		String hql = "from BkOperator where name=?0 or mobile=?1";
		Object[] paras = {loginname, loginname};
		List<BkOperator> operators = this.getByHQL(hql, paras);
		if (operators != null && operators.size() > 0) {
			return operators.get(0);
		}
		return null;
	}


	/**
	 * 根据参数查询结果列表(带分页、排序)
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
		builder.append(" select b.uuid, b.role, r.description as roleName, b.name, b.realname, b.mobile, b.email, b.status, b.lockedtime, b.creator, b.createtime ");
		builder.append(" from bk_operator b, bk_operator_role r where b.role=r.uuid ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (b.name like '%" + inputParams.get("name") + "%' or b.realname like '%" + inputParams.get("name") + "%') ");
		}
		//角色
		if (inputParams.get("role") != null && !"".equals(inputParams.get("role"))) {
			builder.append(" and b.role in (" + inputParams.get("role") + ") ");
		}
		//手机号
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and b.mobile like '" + inputParams.get("mobile") + "_#%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('normal','disable','locked','unopen') ");
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
			builder.append(" order by r.description, b.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bk_operator b where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (b.name like '%" + inputParams.get("name") + "%' or b.realname like '%" + inputParams.get("name") + "%') ");
		}
		//角色
		if (inputParams.get("role") != null && !"".equals(inputParams.get("role"))) {
			builder.append(" and b.role in (" + inputParams.get("role") + ") ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and b.status in ('normal','disable','locked','unopen') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	
	/**
	 * 测试
	 * @param user
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getRoleCount(BkOperator user, String sorts, int offset, int pagesize, Class<? extends Entity> resultClass) {
		String sql = " select role, count(*) as count from bk_operator group by role ";
		List<Entity> results = this.getBySQL(sql, offset, pagesize, null, resultClass);
		return results;
	}
	
	/**
	 * 验证同名的管理员是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistOperatorByName(String name, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from bk_operator b where (mobile=?0 or name=?0) ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {name,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证同手机号的管理员是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistOperatorByMobile(String mobile, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from bk_operator b where (mobile=?0 or name=?0)");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {mobile,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 新增一个后台操作员账号信息
	 */
	@Override
	public BkOperator insert(BkOperator operator) {
		return super.insert(operator);
	}
	
	/**
	 * 删除一个后台操作员账号信息
	 */
	@Override
	public BkOperator delete(BkOperator operator) {
		return super.delete(operator);
	}
	
	/**
	 * 修改一个后台操作员账号信息
	 */
	@Override
	public BkOperator update(BkOperator operator) {
		return super.update(operator);
	}
	
	/**
	 * 通过唯一标识UUID得到一个后台操作员账号信息
	 */
	@Override
	public BkOperator get(String uuid) {
		return super.get(uuid);
	}

}
