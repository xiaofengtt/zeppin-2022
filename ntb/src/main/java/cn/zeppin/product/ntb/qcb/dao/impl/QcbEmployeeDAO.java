/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbEmployeeDAO extends BaseDAO<QcbEmployee, String> implements IQcbEmployeeDAO {


	/**
	 * 向数据库添加一条qcbEmployee数据
	 * @param qcbEmployee
	 * @return qcbEmployee
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployees", key = "'qcbEmployees:' + #qcbEmployee.uuid")})
	public QcbEmployee insert(QcbEmployee qcbEmployee) {
		return super.insert(qcbEmployee);
	}

	/**
	 * 向数据库删除一条qcbEmployee数据
	 * @param qcbEmployee
	 * @return qcbEmployee
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbEmployees", key = "'qcbEmployees:' + #qcbEmployee.uuid")})
	public QcbEmployee delete(QcbEmployee qcbEmployee) {
		return super.delete(qcbEmployee);
	}

	/**
	 * 向数据库更新一条QcbEmployees数据
	 * @param qcbEmployees
	 * @return QcbEmployees
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployees", key = "'qcbEmployees:' + #qcbEmployee.uuid")})
	public QcbEmployee update(QcbEmployee qcbEmployee) {
		return super.update(qcbEmployee);
	}

	/**
	 * 根据uuid得到一个QcbEmployees信息
	 * @param uuid
	 * @return QcbEmployees
	 */
	@Override
	@Cacheable(value = "qcbEmployees", key = "'qcbEmployees:' + #uuid")
	public QcbEmployee get(String uuid) {
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
		builder.append(" select qe.uuid,qe.realname,qe.idcard,qe.mobile,qe.email,qe.login_password as loginPassword,qe.secret_password as secretPassword,"
				+ "qe.secret_password_flag as secretPasswordFlag,qe.account_balance as accountBalance,qe.total_invest as totalInvest,"
				+ "qe.current_account as currentAccount,qe.current_account_yesterday as currentAccountYesterday,qe.flag_current as flagCurrent,"
				+ "qe.total_return as totalReturn,qe.openid,qe.sex,qe.status,qe.last_login_time as lastLoginTime,qe.last_login_ip as lastLoginIp,qe.createtime"
				+ " from qcb_employee qe  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qe.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and qe.mobile = '" + inputParams.get("mobile") + "' ");
		}

		if (inputParams.get("realname") != null && !"".equals(inputParams.get("realname"))) {
			builder.append(" and qe.realname like '%" + inputParams.get("realname") + "%' ");
		}
		
		if (inputParams.get("invested") != null && "1".equals(inputParams.get("invested"))) {
			builder.append(" and qe.current_account > 0 ");
		}
		
		//20180807
		if (inputParams.get("flagCurrent") != null && !"".equals(inputParams.get("flagCurrent"))) {
			builder.append(" and qe.flag_current = '" + inputParams.get("flagCurrent") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && "1".equals(inputParams.get("accountBalance"))) {
			builder.append(" and qe.account_balance > 0 ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qe.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qe.status in ('normal','disable') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qe.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qe.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_employee qe where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qe.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and qe.mobile = '" + inputParams.get("mobile") + "' ");
		}

		if (inputParams.get("realname") != null && !"".equals(inputParams.get("realname"))) {
			builder.append(" and qe.realname like '%" + inputParams.get("realname") + "%' ");
		}

		if (inputParams.get("invested") != null && "1".equals(inputParams.get("invested"))) {
			builder.append(" and qe.current_account > 0 ");
		}
		
		//20180807
		if (inputParams.get("flagCurrent") != null && !"".equals(inputParams.get("flagCurrent"))) {
			builder.append(" and qe.flag_current = '" + inputParams.get("flagCurrent") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && "1".equals(inputParams.get("accountBalance"))) {
			builder.append(" and qe.account_balance > 0 ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qe.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qe.status in ('normal','disable') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public QcbEmployee getByMobile(String mobile) {
		StringBuilder builder = new StringBuilder();
		builder.append(" from QcbEmployee qe where 1=1");
		builder.append(" and qe.mobile='"+mobile+"'");
		List<QcbEmployee> operators = this.getByHQL(builder.toString());
		if (operators != null && operators.size() > 0) {
			return operators.get(0);
		}
		return null;
	}

	@Override
	public QcbEmployee getByOpenId(String openid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" from QcbEmployee qe where 1=1");
		builder.append(" and qe.openid='"+openid+"'");
		List<QcbEmployee> operators = this.getByHQL(builder.toString());
		if (operators != null && operators.size() > 0) {
			return operators.get(0);
		}
		return null;
	}
	
	@Override
	public boolean isExistQcbEmployeeByMobile(String mobile, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from qcb_employee qe where mobile=?0");
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
	public boolean isExistQcbEmployeeByIdcard(String idcard, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from qcb_employee qe where idcard=?0");
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

	@Override
	public List<Entity> getListForNTBPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select qe.uuid,qe.realname,qe.idcard,qe.mobile,qe.email,qe.login_password as loginPassword,qe.secret_password as secretPassword,"
				+ "qe.secret_password_flag as secretPasswordFlag,qe.account_balance as accountBalance,qe.total_invest as totalInvest,"
				+ "qe.current_account as currentAccount,qe.current_account_yesterday as currentAccountYesterday,qe.flag_current as flagCurrent,"
				+ "qe.total_return as totalReturn,qe.openid,qe.sex,qe.status,qe.last_login_time as lastLoginTime,qe.last_login_ip as lastLoginIp,qe.createtime"
				+ " from qcb_employee qe left join qcb_company_employee qce on qe.uuid=qce.qcb_employee where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qe.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if(inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))){
			builder.append(" and qce.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}

		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and qe.mobile = '" + inputParams.get("mobile") + "' ");
		}

		if (inputParams.get("realname") != null && !"".equals(inputParams.get("realname"))) {
			builder.append(" and qe.realname like '%" + inputParams.get("realname") + "%' ");
		}
		
		//是否有余额
		if (inputParams.get("accountBalance") != null && "1".equals(inputParams.get("accountBalance"))) {
			builder.append(" and qe.account_balance>=0.01 ");
		}
				
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qe.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qe.status in ('normal','disable') ");
		}
		
		builder.append(" group by qe.uuid ");
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qe.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qe.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCountForNTB(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(distinct qe.uuid) from qcb_employee qe left join qcb_company_employee qce on qe.uuid=qce.qcb_employee where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qe.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if(inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))){
			builder.append(" and qce.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}

		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and qe.mobile = '" + inputParams.get("mobile") + "' ");
		}

		if (inputParams.get("realname") != null && !"".equals(inputParams.get("realname"))) {
			builder.append(" and qe.realname like '%" + inputParams.get("realname") + "%' ");
		}
		
		//是否有余额
		if (inputParams.get("accountBalance") != null && "1".equals(inputParams.get("accountBalance"))) {
			builder.append(" and qe.account_balance>=0.01 ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qe.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qe.status in ('normal','disable') ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Entity> getStatusListForNTB(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select qe.status, count(DISTINCT qe.uuid) as count from qcb_employee qe left join qcb_company_employee qce on qe.uuid=qce.qcb_employee where 1=1");
		builder.append(" and qe.status in ('normal','disable') ");
		
		if(inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))){
			builder.append(" and qce.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		builder.append(" group by qe.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 更新昨日余额
	 */
	@Caching(evict={@CacheEvict(value = "qcbEmployees", allEntries = true)})
	public void updateYesterdayAccount(){
		StringBuilder builder = new StringBuilder();
		builder.append("update qcb_employee set current_account_yesterday=current_account");
		super.executeSQL(builder.toString());
	}
	
	/**
	 * 获取活期募集总额
	 */
	public Double getTotalCurrentAccount(){
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(current_account) from qcb_employee");
		Object obj = super.getResultBySQL(builder.toString());
		if(obj != null){
			return Double.valueOf(obj.toString());
		}else{
			return 0.0;
		}
	}

	@Override
	public BigDecimal getTotalBalacne() {
		StringBuilder builder = new StringBuilder();
		builder.append(" select sum(q.account_balance) from qcb_employee q where q.`status`='normal' ");
		return BigDecimal.valueOf(Double.valueOf(super.getResultBySQL(builder.toString()).toString()));
	}
}
