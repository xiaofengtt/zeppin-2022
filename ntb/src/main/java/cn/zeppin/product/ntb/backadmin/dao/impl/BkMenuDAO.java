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

import cn.zeppin.product.ntb.backadmin.dao.api.IBkMenuDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BkMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class BkMenuDAO extends BaseDAO<BkMenu, String> implements IBkMenuDAO {
	/**
	 * 向数据库添加一条Menu数据
	 * 向缓存menus添加一条Menu记录，key值为uuid
	 * @param menu
	 * @return Menu
	 */
	@Override
	@Caching(put={@CachePut(value = "menus", key = "'menus:' + #menu.uuid")})
	public BkMenu insert(BkMenu menu) {
		return super.insert(menu);
	}

	/**
	 * 向数据库删除一条Menu数据
	 * 在缓存menus中删除一条Menu记录，key值为uuid
	 * @param menu
	 * @return Menu
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "menus", key = "'menus:' + #menu.uuid")})
	public BkMenu delete(BkMenu menu) {
		return super.delete(menu);
	}

	/**
	 * 向数据库更新一条Menu数据
	 * 在缓存menus中更新一条Menu记录，key值为uuid
	 * @param menu
	 * @return Menu
	 */
	@Override
	@Caching(put={@CachePut(value = "menus", key = "'menus:' + #menu.uuid")})
	public BkMenu update(BkMenu menu) {
		return super.update(menu);
	}

	/**
	 * 根据uuid得到一个Menu信息
	 * 向缓存menus添加一条Menu记录，key值为uuid
	 * @param uuid
	 * @return Menu
	 */
	@Override
	@Cacheable(value = "menus", key = "'menus:' + #uuid")
	public BkMenu get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.name, b.title, b.level, b.scode, b.url, b.pid, b.icon");
		builder.append(" from bk_menu b, bk_role_menu_permission rm where b.uuid=rm.menu ");
		//角色
		if (inputParams.get("role") != null && inputParams.get("role").length() > 0) {
			builder.append(" and rm.role='").append(inputParams.get("role")).append("' ");
		}
		//scode
		if (inputParams.get("scode") != null && inputParams.get("scode").length() > 0) {
			builder.append(" and b.scode like '" + inputParams.get("scode") + "%' ");
		}
		//级别
		if (inputParams.get("level") != null && inputParams.get("level").length() > 0) {
			builder.append(" and b.level=" + inputParams.get("level"));
		}
		//排序
		builder.append(" order by b.level, rm.sort");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select b.* from bk_menu b order by b.level , b.sort", resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.name, b.title, b.level, b.scode, b.url, b.pid, b.icon, b.sort from bk_menu b where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and b.name = '" + inputParams.get("name") + "' ");
		}
		
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and b.title like '%" + inputParams.get("title") + "%' ");
		}
		
		if (inputParams.get("scode") != null && !"".equals(inputParams.get("scode"))) {
			builder.append(" and b.scode like '%" + inputParams.get("scode") + "%' ");
		}
		
		if (inputParams.get("scode") != null && !"".equals(inputParams.get("scode"))) {
			builder.append(" and b.scode = '" + inputParams.get("scode") + "' ");
		}
		
		if (inputParams.get("level") != null && !"".equals(inputParams.get("level"))) {
			builder.append(" and b.level = " + inputParams.get("level"));
		}
		
		if (inputParams.get("pid") != null && !"".equals(inputParams.get("pid"))) {
			builder.append(" and b.pid = '" + inputParams.get("pid") + "' ");
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
			builder.append(" order by b.sort ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bk_menu b where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and b.name = '" + inputParams.get("name") + "' ");
		}
		
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and b.title like '%" + inputParams.get("title") + "%' ");
		}
		
		if (inputParams.get("scodes") != null && !"".equals(inputParams.get("scodes"))) {
			builder.append(" and b.scode like '%" + inputParams.get("scodes") + "%' ");
		}
		
		if (inputParams.get("scode") != null && !"".equals(inputParams.get("scode"))) {
			builder.append(" and b.scode = '" + inputParams.get("scode") + "' ");
		}
		
		if (inputParams.get("level") != null && !"".equals(inputParams.get("level"))) {
			builder.append(" and b.level = " + inputParams.get("level"));
		}
		
		if (inputParams.get("pid") != null && !"".equals(inputParams.get("pid"))) {
			builder.append(" and b.pid = '" + inputParams.get("pid") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
