/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsMenuDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsMenu;

@Repository
public class TSsMenuDAO extends BaseDAO<TSsMenu, String> implements ITSsMenuDAO {

	
	@Override
	public TSsMenu insert(TSsMenu menu) {
		return super.insert(menu);
	}
	
	@Override
	public TSsMenu delete(TSsMenu menu) {
		return super.delete(menu);
	}

	
	@Override
	public TSsMenu update(TSsMenu menu) {
		return super.update(menu);
	}

	
	@Override
	public TSsMenu get(String id) {
		return super.get(id);
	}
	
	/**
	 * 获取所有页面信息
	 * @return
	 */
	public List<TSsMenu> getAll(){
		return super.getByHQL(" from TSsMenu m order by m.menulevel");
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from T_SS_MENU b where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and b.name = '" + inputParams.get("name") + "' ");
		}
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and b.title like '%" + inputParams.get("title") + "%' ");
		}
		
		if (inputParams.get("scodes") != null && !"".equals(inputParams.get("scodes"))) {
			builder.append(" and b.scode like '" + inputParams.get("scodes") + "%' ");
		}
		
		if (inputParams.get("menulevel") != null && !"".equals(inputParams.get("menulevel"))) {
			builder.append(" and b.menulevel = " + inputParams.get("menulevel"));
		}
		
		if (inputParams.get("pid") != null && !"".equals(inputParams.get("pid"))) {
			builder.append(" and b.pid = '" + inputParams.get("pid") + "' ");
		}
		//名称
		if (inputParams.get("names") != null && inputParams.get("names").length() > 0) {
			builder.append(" and b.name in " + inputParams.get("names"));
		}
		//id
		if (inputParams.get("ids") != null && inputParams.get("ids").length() > 0) {
			builder.append(" and b.id in " + inputParams.get("ids"));
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<TSsMenu> getList(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b from TSsMenu b where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and b.name = '" + inputParams.get("name") + "' ");
		}
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and b.title like '%" + inputParams.get("title") + "%' ");
		}
		
		if (inputParams.get("scodes") != null && !"".equals(inputParams.get("scodes"))) {
			builder.append(" and b.scode like '" + inputParams.get("scodes") + "%' ");
		}
		
		if (inputParams.get("menulevel") != null && !"".equals(inputParams.get("menulevel"))) {
			builder.append(" and b.menulevel = " + inputParams.get("menulevel"));
		}
		
		if (inputParams.get("pid") != null && !"".equals(inputParams.get("pid"))) {
			builder.append(" and b.pid = '" + inputParams.get("pid") + "' ");
		}
		//名称
		if (inputParams.get("names") != null && inputParams.get("names").length() > 0) {
			builder.append(" and b.name in " + inputParams.get("names"));
		}
		//id
		if (inputParams.get("ids") != null && inputParams.get("ids").length() > 0) {
			builder.append(" and b.id in " + inputParams.get("ids"));
		}
		//排序
		builder.append(" order by b.menulevel, b.sort");
		return super.getByHQL(builder.toString());
	}
	
	@Override
	public List<TSsMenu> getListForOperator(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b from TSsMenu b, TSsOperatorMenu rm where b.id=rm.menu ");
		//角色
		if (inputParams.get("operator") != null && inputParams.get("operator").length() > 0) {
			builder.append(" and rm.operator='").append(inputParams.get("operator")).append("' ");
		}
		//scode
		if (inputParams.get("scode") != null && inputParams.get("scode").length() > 0) {
			builder.append(" and b.scode like '" + inputParams.get("scode") + "%' ");
		}
		//级别
		if (inputParams.get("level") != null && inputParams.get("level").length() > 0) {
			builder.append(" and b.menulevel=" + inputParams.get("level"));
		}
		//排序
		builder.append(" order by b.menulevel, b.sort");
		return super.getByHQL(builder.toString());
	}
}
