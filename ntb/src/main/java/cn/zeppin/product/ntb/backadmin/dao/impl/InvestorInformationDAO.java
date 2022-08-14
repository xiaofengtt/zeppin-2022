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

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorInformationDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.InvestorInformation;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class InvestorInformationDAO extends BaseDAO<InvestorInformation, String> implements IInvestorInformationDAO {
	/**
	 * 向数据库添加一条InvestorInformation数据
	 * 向缓存investorInformations添加一条InvestorInformation记录，key值为uuid
	 * @param investorInformation
	 * @return investorInformation
	 */
	@Override
	@Caching(put={@CachePut(value = "investorInformations", key = "'investorInformations:' + #investorInformation.uuid")}, evict={@CacheEvict(value = "listInvestorInformations", allEntries = true)})
	public InvestorInformation insert(InvestorInformation investorInformation) {
		return super.insert(investorInformation);
	}

	/**
	 * 向数据库删除一条InvestorInformation数据
	 * 在缓存investorInformations中删除一条InvestorInformation记录，key值为uuid
	 * @param investorInformation
	 * @return investorInformation
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investorInformations", key = "'investorInformations:' + #investorInformation.uuid"), @CacheEvict(value = "listInvestorInformations", allEntries = true)})
	public InvestorInformation delete(InvestorInformation investorInformation) {
		return super.delete(investorInformation);
	}

	/**
	 * 向数据库更新一条InvestorInformation数据
	 * 在缓存investorInformations中更新一条InvestorInformation记录，key值为uuid
	 * @param investorInformation
	 * @return investorInformation
	 */
	@Override
	@Caching(put={@CachePut(value = "investorInformations", key = "'investorInformations:' + #investorInformation.uuid")}, evict={@CacheEvict(value = "listInvestorInformations", allEntries = true)})
	public InvestorInformation update(InvestorInformation investorInformation) {
		return super.update(investorInformation);
	}

	/**
	 * 根据uuid得到一个InvestorInformation信息
	 * 向缓存investorInformations添加一条InvestorInformation记录，key值为uuid
	 * @param uuid
	 * @return investorInformation
	 */
	@Override
	@Cacheable(value = "investorInformations", key = "'investorInformations:' + #uuid")
	public InvestorInformation get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select b.* from investor_information b order by b.createtime", resultClass);
	}

	@Override
	@Cacheable(value = "listInvestorInformations")
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.information, b.investor, b.createtime, b.creator, b.status, b.info_title as infoTitle,"
				+ " b.info_text as infoText"
				+ " from investor_information b where 1=1 ");
		//名称
		if (inputParams.get("information") != null && !"".equals(inputParams.get("information"))) {
			builder.append(" and b.information = '" + inputParams.get("information") + "' ");
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (b.info_title like '%" + inputParams.get("name") + "%' ");
			builder.append(" or b.info_text like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and b.investor = '" + inputParams.get("investor") + "'");
		}
		
		if (inputParams.get("infoTitle") != null && !"".equals(inputParams.get("infoTitle"))) {
			builder.append(" and b.info_title = '" + inputParams.get("infoTitle") + "'");
		}
		
		if (inputParams.get("infoText") != null && !"".equals(inputParams.get("infoText"))) {
			builder.append(" and b.info_text = '" + inputParams.get("infoText") + "'");
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
			builder.append(" and b.status in ('normal','unread') ");
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
		builder.append(" select count(*) from investor_information b where 1=1 ");
		
		//名称
		if (inputParams.get("information") != null && !"".equals(inputParams.get("information"))) {
			builder.append(" and b.information = '" + inputParams.get("information") + "' ");
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (b.info_title like '%" + inputParams.get("name") + "%' ");
			builder.append(" or b.info_text like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and b.investor = '" + inputParams.get("investor") + "'");
		}
		
		if (inputParams.get("infoTitle") != null && !"".equals(inputParams.get("infoTitle"))) {
			builder.append(" and b.info_title = '" + inputParams.get("infoTitle") + "'");
		}
		
		if (inputParams.get("infoText") != null && !"".equals(inputParams.get("infoText"))) {
			builder.append(" and b.info_text = '" + inputParams.get("infoText") + "'");
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
			builder.append(" and b.status in ('normal','unread') ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	@Caching(put={@CachePut(value = "investorInformations")}, evict={@CacheEvict(value = "listInvestorInformations", allEntries = true)})
	public void updateBatchRead(String investor) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" update investor_information b set b.status='normal' where 1=1 ");
		builder.append(" and b.status='unread' ");
		builder.append(" and b.investor='"+investor+"'");
		super.executeSQL(builder.toString());
	}

	@Override
	public void insert(List<Object[]> paras) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("insert into investor_information(uuid,information,info_title,info_text,investor,createtime,creator,status) values(?,?,?,?,?,?,?,?)");
		super.insert(builder.toString(), paras);
	}

}
