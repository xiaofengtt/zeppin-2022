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

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorRedPacketDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.InvestorRedPacket;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class InvestorRedPacketDAO extends BaseDAO<InvestorRedPacket, String> implements IInvestorRedPacketDAO {
	/**
	 * 向数据库添加一条InvestorRedPacket数据
	 * 向缓存investorRedPackets添加一条InvestorRedPacket记录，key值为uuid
	 * @param investorRedPacket
	 * @return investorRedPacket
	 */
	@Override
	@Caching(put={@CachePut(value = "investorRedPackets", key = "'investorRedPackets:' + #investorRedPacket.uuid")}, evict={@CacheEvict(value = "listInvestorRedPackets", allEntries = true)})
	public InvestorRedPacket insert(InvestorRedPacket investorRedPacket) {
		return super.insert(investorRedPacket);
	}

	/**
	 * 向数据库删除一条InvestorRedPacket数据
	 * 在缓存investorRedPackets中删除一条InvestorRedPacket记录，key值为uuid
	 * @param investorRedPacket
	 * @return investorRedPacket
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investorRedPackets", key = "'investorRedPackets:' + #investorRedPacket.uuid"), @CacheEvict(value = "listInvestorRedPackets", allEntries = true)})
	public InvestorRedPacket delete(InvestorRedPacket investorRedPacket) {
		return super.delete(investorRedPacket);
	}

	/**
	 * 向数据库更新一条InvestorRedPacket数据
	 * 在缓存investorRedPackets中更新一条InvestorRedPacket记录，key值为uuid
	 * @param investorRedPacket
	 * @return investorRedPacket
	 */
	@Override
	@Caching(put={@CachePut(value = "investorRedPackets", key = "'investorRedPackets:' + #investorRedPacket.uuid")}, evict={@CacheEvict(value = "listInvestorRedPackets", allEntries = true)})
	public InvestorRedPacket update(InvestorRedPacket investorRedPacket) {
		return super.update(investorRedPacket);
	}

	/**
	 * 根据uuid得到一个InvestorRedPacket信息
	 * 向缓存investorRedPackets添加一条InvestorRedPacket记录，key值为uuid
	 * @param uuid
	 * @return investorRedPacket
	 */
	@Override
	@Cacheable(value = "investorRedPackets", key = "'investorRedPackets:' + #uuid")
	public InvestorRedPacket get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select b.* from investor_red_packet b order by b.createtime", resultClass);
	}

	@Override
	@Cacheable(value = "listInvestorRedPackets")
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.price, b.investor, b.createtime, b.creator, b.status, b.expiry_date as expiryDate"
				+ " from investor_red_packet b where 1=1 ");
		//名称
		if (inputParams.get("price") != null && !"".equals(inputParams.get("price"))) {
			builder.append(" and b.price = " + inputParams.get("price"));
		}
		
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and b.investor = '" + inputParams.get("investor") + "'");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status in (" + inputParams.get("status") + ") ");
		} else {
			builder.append(" and b.status in ('unuse','used','expired') ");//不包括删除和未激活的
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("expiryDate") != null && !"".equals(inputParams.get("expiryDate"))) {
			builder.append(" and b.expiry_date = '" + inputParams.get("expiryDate") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
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
		builder.append(" select count(*) from investor_red_packet b where 1=1 ");
		//名称
		if (inputParams.get("price") != null && !"".equals(inputParams.get("price"))) {
			builder.append(" and b.price = " + inputParams.get("price"));
		}
		
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and b.investor = '" + inputParams.get("investor") + "'");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status in (" + inputParams.get("status") + ") ");
		} else {
			builder.append(" and b.status in ('unuse','used','expired') ");//不包括删除和未激活的
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("expiryDate") != null && !"".equals(inputParams.get("expiryDate"))) {
			builder.append(" and b.expiry_date = '" + inputParams.get("expiryDate") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public void insert(List<Object[]> paras) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("insert into investor_red_packet(uuid,price,investor,createtime,creator,status,expiry_date) values(?,?,?,?,?,?,?)");
		super.insert(builder.toString(), paras);
	}

}
