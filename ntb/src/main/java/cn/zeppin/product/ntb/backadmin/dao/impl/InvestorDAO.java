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

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.InvestorIdcardImg.InvestorIdcardImgStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class InvestorDAO extends BaseDAO<Investor, String> implements IInvestorDAO {


	/**
	 * 向数据库添加一条investor数据
	 * @param investor
	 * @return investor
	 */
	@Override
	@Caching(put={@CachePut(value = "investors", key = "'investors:' + #investor.uuid")})
	public Investor insert(Investor investor) {
		return super.insert(investor);
	}

	/**
	 * 向数据库删除一条investor数据
	 * @param investor
	 * @return investor
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investors", key = "'investors:' + #investor.uuid")})
	public Investor delete(Investor investor) {
		return super.delete(investor);
	}

	/**
	 * 向数据库更新一条BankFinancialProduct数据
	 * @param bankFinancialProduct
	 * @return BankFinancialProduct
	 */
	@Override
	@Caching(put={@CachePut(value = "investors", key = "'investors:' + #investor.uuid")})
	public Investor update(Investor investor) {
		return super.update(investor);
	}

	/**
	 * 根据uuid得到一个BankFinancialProduct信息
	 * @param uuid
	 * @return BankFinancialProduct
	 */
	@Override
	@Cacheable(value = "investors", key = "'investors:' + #uuid")
	public Investor get(String uuid) {
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
		builder.append(" select i.uuid,i.nickname,i.realname,i.idcard,i.mobile,i.email,i.status,i.total_invest as totalInvest,"
				+ "i.total_return as totalReturn,i.account_balance as accountBalance,i.current_account as currentAccount,"
				+ "i.flag_current as flagCurrent,i.createtime,i.referrer,i.regist_source as registSource,"
				+ "i.last_login_time as lastLoginTime,i.last_login_ip as lastLoginIp,i.binding_mobile_flag as bindingMobileFlag,"
				+ "i.binding_email_flag as bindingEmailFlag,i.realname_auth_flag as realnameAuthFlag,"
				+ "i.secret_password_flag as secretPasswordFlag,i.idcard_img as idcardImg,i.sex "
				+ "from investor i left join investor_idcard_img iii on i.idcard_img=iii.uuid where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and i.uuid like '%" + inputParams.get("uuid") + "%' ");
		}
		
		//名称
		if (inputParams.get("realname") != null && !"".equals(inputParams.get("realname"))) {
			builder.append(" and i.realname like '%" + inputParams.get("realname") + "%' ");
		}
		
		if (inputParams.get("nickname") != null && !"".equals(inputParams.get("nickname"))) {
			builder.append(" and i.nickname like '%" + inputParams.get("nickname") + "%' ");
		}
		
		if (inputParams.get("idcard") != null && !"".equals(inputParams.get("idcard"))) {
			builder.append(" and i.idcard = '" + inputParams.get("idcard") + "' ");
		}
		
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and i.mobile = '" + inputParams.get("mobile") + "' ");
		}
		
		if (inputParams.get("flagCurrent") != null && !"".equals(inputParams.get("flagCurrent"))) {
			builder.append(" and i.flag_current = '" + inputParams.get("flagCurrent") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && "1".equals(inputParams.get("accountBalance"))) {
			builder.append(" and i.account_balance > 0 ");
		}
		
		if (inputParams.get("email") != null && !"".equals(inputParams.get("email"))) {
			builder.append(" and i.email = '" + inputParams.get("email") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and i.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and i.status in ('normal','disable','locked','unopen') ");
		}
		
		//证件照审核状态
		if (inputParams.get("checkstatus") != null && !"".equals(inputParams.get("checkstatus"))) {
			if(InvestorIdcardImgStatus.NOTUPLOAD.equals(inputParams.get("checkstatus"))){
				builder.append(" and i.idcard_img is null");
			} else {
				builder.append(" and iii.status = '" + inputParams.get("checkstatus") + "' ");
			}
		}
		
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("i.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by i.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from investor i where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and i.uuid like '%" + inputParams.get("uuid") + "%' ");
		}
		
		//名称
		if (inputParams.get("realname") != null && !"".equals(inputParams.get("realname"))) {
			builder.append(" and i.realname like '%" + inputParams.get("realname") + "%' ");
		}
		
		if (inputParams.get("nickname") != null && !"".equals(inputParams.get("nickname"))) {
			builder.append(" and i.nickname like '%" + inputParams.get("nickname") + "%' ");
		}
		
		if (inputParams.get("idcard") != null && !"".equals(inputParams.get("idcard"))) {
			builder.append(" and i.idcard = '" + inputParams.get("idcard") + "' ");
		}
		
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and i.mobile = '" + inputParams.get("mobile") + "' ");
		}
		
		if (inputParams.get("email") != null && !"".equals(inputParams.get("email"))) {
			builder.append(" and i.email = '" + inputParams.get("email") + "' ");
		}
		
		if (inputParams.get("flagCurrent") != null && !"".equals(inputParams.get("flagCurrent"))) {
			builder.append(" and i.flag_current = '" + inputParams.get("flagCurrent") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && "1".equals(inputParams.get("accountBalance"))) {
			builder.append(" and i.account_balance > 0' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and i.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and i.status in ('normal','disable','locked','unopen') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取银行理财产品分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select i.status, count(*) as count from investor i group by i.status");
		return super.getBySQL(builder.toString(), resultClass);
	}

	@Override
	public Investor getByOpenID(String openID, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select i.uuid from investor i where 1=1");
		builder.append(" and i.openid='"+openID+"'");
		return (Investor) super.getResultBySQL(builder.toString(), resultClass);
	}

	@Override
	public Investor getByMobile(String mobile, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select i.uuid,i.mobile,i.login_password as loginPassword,i.openid,i.status from investor i where 1=1");
		builder.append(" and i.mobile='"+mobile+"'");
		return (Investor) super.getResultBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 验证同手机号的用户是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistInvestorByMobile(String mobile, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from investor i where mobile=?0");
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
	public List<Entity> getNotuploadCount(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select 'notupload' as status, count(*) as count from investor i where 1=1 and i.idcard_img is null");
		return super.getBySQL(builder.toString(), resultClass);
	}

	@Override
	public Investor getByAliUserid(String userid, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select i.uuid from investor i where 1=1");
		builder.append(" and i.ali_userid='"+userid+"'");
		return (Investor) super.getResultBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 更新昨日余额
	 */
	@Caching(evict={@CacheEvict(value = "investors", allEntries = true)})
	public void updateYesterdayAccount(){
		StringBuilder builder = new StringBuilder();
		builder.append("update investor set current_account_yesterday=current_account");
		super.executeSQL(builder.toString());
	}
	
	/**
	 * 获取活期募集总额
	 */
	public Double getTotalCurrentAccount(){
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(current_account) from investor");
		Object obj = super.getResultBySQL(builder.toString());
		if(obj != null){
			return Double.valueOf(obj.toString());
		}else{
			return 0.0;
		}
	}
}
