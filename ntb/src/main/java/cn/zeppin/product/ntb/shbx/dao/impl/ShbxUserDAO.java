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
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxUserDAO;

/**
 * @author hehe
 *
 */

@Repository
public class ShbxUserDAO extends BaseDAO<ShbxUser, String> implements IShbxUserDAO {


	/**
	 * 向数据库添加一条shbxUser数据
	 * @param shbxUser
	 * @return shbxUser
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxUsers", key = "'shbxUsers:' + #shbxUser.uuid")})
	public ShbxUser insert(ShbxUser shbxUser) {
		return super.insert(shbxUser);
	}

	/**
	 * 向数据库删除一条shbxUser数据
	 * @param shbxUser
	 * @return shbxUser
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "shbxUsers", key = "'shbxUsers:' + #shbxUser.uuid")})
	public ShbxUser delete(ShbxUser shbxUser) {
		return super.delete(shbxUser);
	}

	/**
	 * 向数据库更新一条ShbxUsers数据
	 * @param shbxUsers
	 * @return ShbxUsers
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxUsers", key = "'shbxUsers:' + #shbxUser.uuid")})
	public ShbxUser update(ShbxUser shbxUser) {
		return super.update(shbxUser);
	}

	/**
	 * 根据uuid得到一个ShbxUsers信息
	 * @param uuid
	 * @return ShbxUsers
	 */
	@Override
	@Cacheable(value = "shbxUsers", key = "'shbxUsers:' + #uuid")
	public ShbxUser get(String uuid) {
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
		builder.append(" select su.uuid,su.realname,su.idcard,su.mobile,su.email,su.login_password as loginPassword,su.secret_password as secretPassword,"
				+ "su.secret_password_flag as secretPasswordFlag,su.realname_auth_flag as realnameAuthFlag,su.account_balance as accountBalance,su.total_invest as totalInvest,"
				+ "su.current_account as currentAccount,su.current_account_yesterday as currentAccountYesterday,su.flag_current as flagCurrent,"
				+ "su.total_return as totalReturn,su.openid,su.sex,su.status,su.last_login_time as lastLoginTime,su.last_login_ip as lastLoginIp,su.createtime"
				+ " from shbx_user su  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and su.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		//名称
		if (inputParams.get("realname") != null && !"".equals(inputParams.get("realname"))) {
			builder.append(" and su.realname like '%" + inputParams.get("realname") + "%' ");
		}
		
		if (inputParams.get("idcard") != null && !"".equals(inputParams.get("idcard"))) {
			builder.append(" and su.idcard = '" + inputParams.get("idcard") + "' ");
		}
		
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and su.mobile = '" + inputParams.get("mobile") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and su.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and su.status in ('normal','disable') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("su.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by su.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from shbx_user su where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and su.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		//名称
		if (inputParams.get("realname") != null && !"".equals(inputParams.get("realname"))) {
			builder.append(" and su.realname like '%" + inputParams.get("realname") + "%' ");
		}
		
		if (inputParams.get("idcard") != null && !"".equals(inputParams.get("idcard"))) {
			builder.append(" and su.idcard = '" + inputParams.get("idcard") + "' ");
		}
		
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and su.mobile = '" + inputParams.get("mobile") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and su.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and su.status in ('normal','disable') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	

	@Override
	public ShbxUser getByMobile(String mobile) {
		StringBuilder builder = new StringBuilder();
		builder.append(" from ShbxUser su where 1=1");
		builder.append(" and su.mobile='"+mobile+"'");
		List<ShbxUser> operators = this.getByHQL(builder.toString());
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
	public boolean isExistShbxUserByMobile(String mobile, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from shbx_user su where mobile=?0");
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

	@Override
	public ShbxUser getByOpenId(String openid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" from ShbxUser su where 1=1");
		builder.append(" and su.openid='"+openid+"'");
		List<ShbxUser> operators = this.getByHQL(builder.toString());
		if (operators != null && operators.size() > 0) {
			return operators.get(0);
		}
		return null;
	}

	@Override
	public boolean isExistShbxUserByIdcard(String idcard, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from shbx_user su where idcard=?0");
		//编辑时检测uuid
		if(uuid != null && !"".equals(uuid)){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {idcard,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
}
