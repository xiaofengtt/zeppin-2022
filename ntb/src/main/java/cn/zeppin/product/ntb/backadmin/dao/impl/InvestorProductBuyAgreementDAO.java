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

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorProductBuyAgreementDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class InvestorProductBuyAgreementDAO extends BaseDAO<InvestorProductBuyAgreement, String> implements IInvestorProductBuyAgreementDAO {


	/**
	 * 向数据库添加一条investorProductBuyAgreement数据
	 * @param investorProductBuyAgreement
	 * @return investorProductBuyAgreement
	 */
	@Override
	@Caching(put={@CachePut(value = "investorProductBuyAgreements", key = "'investorProductBuyAgreements:' + #investorProductBuyAgreement.uuid")})
	public InvestorProductBuyAgreement insert(InvestorProductBuyAgreement investorProductBuyAgreement) {
		return super.insert(investorProductBuyAgreement);
	}

	/**
	 * 向数据库删除一条investorProductBuyAgreement数据
	 * @param investorProductBuyAgreement
	 * @return investorProductBuyAgreement
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investorProductBuyAgreements", key = "'investorProductBuyAgreements:' + #investorProductBuyAgreement.uuid")})
	public InvestorProductBuyAgreement delete(InvestorProductBuyAgreement investorProductBuyAgreement) {
		return super.delete(investorProductBuyAgreement);
	}

	/**
	 * 向数据库更新一条investorProductBuyAgreement数据
	 * @param investorProductBuyAgreement
	 * @return investorProductBuyAgreement
	 */
	@Override
	@Caching(put={@CachePut(value = "investorProductBuyAgreements", key = "'investorProductBuyAgreements:' + #investorProductBuyAgreement.uuid")})
	public InvestorProductBuyAgreement update(InvestorProductBuyAgreement investorProductBuyAgreement) {
		return super.update(investorProductBuyAgreement);
	}

	/**
	 * 根据uuid得到一个investorProductBuyAgreement信息
	 * @param uuid
	 * @return investorProductBuyAgreement
	 */
	@Override
	@Cacheable(value = "investorProductBuyAgreements", key = "'investorProductBuyAgreements:' + #uuid")
	public InvestorProductBuyAgreement get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询investorProductBuyAgreement总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams){
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from investor_product_buy_agreement ipba where 1=1 ");
		//
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and ipba.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("records") != null && !"".equals(inputParams.get("records"))) {
			builder.append(" and ipba.records = '" + inputParams.get("records") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and ipba.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and ipba.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("scode") != null && !"".equals(inputParams.get("scode"))) {
			builder.append(" and ipba.scode = '" + inputParams.get("scode") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ipba.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and ipba.status in ('success','finished','confirming') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and ipba.createtime > '" + inputParams.get("createtime") + "' ");
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
		builder.append(" select ipba.uuid,ipba.investor,ipba.records,ipba.name,ipba.type,"
				+ "ipba.status,ipba.scode,ipba.url,ipba.createtime from investor_product_buy_agreement ipba where 1=1 ");
		//
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and ipba.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("records") != null && !"".equals(inputParams.get("records"))) {
			builder.append(" and ipba.records = '" + inputParams.get("records") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and ipba.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and ipba.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("scode") != null && !"".equals(inputParams.get("scode"))) {
			builder.append(" and ipba.scode = '" + inputParams.get("scode") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ipba.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and ipba.status in ('success','finished','confirming') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and ipba.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("ipba.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by ipba.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Boolean getCheckScode(String scode) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from investor_product_buy_agreement i where i.scode=?0");
		//编辑时检测uuid
		Object[] paras = {scode};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
}
