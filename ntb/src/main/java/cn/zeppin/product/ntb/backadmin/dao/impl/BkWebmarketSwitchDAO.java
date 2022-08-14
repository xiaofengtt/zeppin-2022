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

import cn.zeppin.product.ntb.backadmin.dao.api.IBkWebmarketSwitchDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BkWebmarketSwitch;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class BkWebmarketSwitchDAO extends BaseDAO<BkWebmarketSwitch, String> implements IBkWebmarketSwitchDAO {
	/**
	 * 向数据库添加一条WebmarketSwitch数据
	 * 向缓存WebmarketSwitchs添加一条WebmarketSwitch记录，key值为uuid
	 * @param WebmarketSwitch
	 * @return WebmarketSwitch
	 */
	@Override
	@Caching(put={@CachePut(value = "WebmarketSwitchs", key = "'WebmarketSwitchs:' + #WebmarketSwitch.uuid")})
	public BkWebmarketSwitch insert(BkWebmarketSwitch WebmarketSwitch) {
		return super.insert(WebmarketSwitch);
	}

	/**
	 * 向数据库删除一条WebmarketSwitch数据
	 * 在缓存WebmarketSwitchs中删除一条WebmarketSwitch记录，key值为uuid
	 * @param WebmarketSwitch
	 * @return WebmarketSwitch
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "WebmarketSwitchs", key = "'WebmarketSwitchs:' + #WebmarketSwitch.uuid")})
	public BkWebmarketSwitch delete(BkWebmarketSwitch WebmarketSwitch) {
		return super.delete(WebmarketSwitch);
	}

	/**
	 * 向数据库更新一条WebmarketSwitch数据
	 * 在缓存WebmarketSwitchs中更新一条WebmarketSwitch记录，key值为uuid
	 * @param WebmarketSwitch
	 * @return WebmarketSwitch
	 */
	@Override
	@Caching(put={@CachePut(value = "WebmarketSwitchs", key = "'WebmarketSwitchs:' + #WebmarketSwitch.uuid")})
	public BkWebmarketSwitch update(BkWebmarketSwitch WebmarketSwitch) {
		return super.update(WebmarketSwitch);
	}

	/**
	 * 根据uuid得到一个WebmarketSwitch信息
	 * 向缓存WebmarketSwitchs添加一条WebmarketSwitch记录，key值为uuid
	 * @param uuid
	 * @return WebmarketSwitch
	 */
	@Override
	@Cacheable(value = "WebmarketSwitchs", key = "'WebmarketSwitchs:' + #uuid")
	public BkWebmarketSwitch get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select b.* from bk_webmarket_switch b order by b.createtime", resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.web_market as webMarket, b.flag_switch as flagSwitch, b.createtime, b.creator, b.status,"
				+ " b.web_market_name as webMarketName, b.version, v.version as versionNum, v.version_name as versionName "
				+ " from bk_webmarket_switch b left join version v on b.version=v.uuid where 1=1 ");
		//名称
		if (inputParams.get("webMarket") != null && !"".equals(inputParams.get("webMarket"))) {
			builder.append(" and b.web_market = '" + inputParams.get("webMarket") + "' ");
		}
		
		if (inputParams.get("webMarketName") != null && !"".equals(inputParams.get("webMarketName"))) {
			builder.append(" and b.web_market_name = '" + inputParams.get("webMarketName") + "' ");
		}
		
		if (inputParams.get("version") != null && !"".equals(inputParams.get("version"))) {
			builder.append(" and b.version = '" + inputParams.get("version") + "' ");
		}
		
		if (inputParams.get("versionNum") != null && !"".equals(inputParams.get("versionNum"))) {
			builder.append(" and v.device='02' and v.status='published' and v.version = " + inputParams.get("versionNum"));
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (b.web_market like '%" + inputParams.get("name") + "%' ");
			builder.append(" or b.web_market_name like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("flagSwitch") != null && !"".equals(inputParams.get("flagSwitch"))) {
			builder.append(" and b.flag_switch = " + inputParams.get("flagSwitch"));
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
			builder.append(" and b.status = 'normal' ");
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
		builder.append(" select count(*) from bk_webmarket_switch b left join version v on b.version=v.uuid where 1=1 ");
		//名称
		if (inputParams.get("webMarket") != null && !"".equals(inputParams.get("webMarket"))) {
			builder.append(" and b.web_market = '" + inputParams.get("webMarket") + "' ");
		}
		
		if (inputParams.get("webMarketName") != null && !"".equals(inputParams.get("webMarketName"))) {
			builder.append(" and b.web_market_name = '" + inputParams.get("webMarketName") + "' ");
		}
		
		if (inputParams.get("version") != null && !"".equals(inputParams.get("version"))) {
			builder.append(" and b.version = '" + inputParams.get("version") + "' ");
		}
		
		if (inputParams.get("versionNum") != null && !"".equals(inputParams.get("versionNum"))) {
			builder.append(" and v.device='02' and v.status='published' and v.version = " + inputParams.get("versionNum"));
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (b.web_market like '%" + inputParams.get("name") + "%' ");
			builder.append(" or b.web_market_name like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("flagSwitch") != null && !"".equals(inputParams.get("flagSwitch"))) {
			builder.append(" and b.flag_switch = " + inputParams.get("flagSwitch"));
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
			builder.append(" and b.status = 'normal' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public boolean isExistBkWebmarketSwitch(String name, String version, String uuid) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from bk_webmarket_switch b where web_market=?0 and version=?1 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?2");
		}
		Object[] paras = {name,version,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
}
