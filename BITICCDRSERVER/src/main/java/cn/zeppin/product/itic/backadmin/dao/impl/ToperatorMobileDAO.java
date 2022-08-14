/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.IToperatorMobileDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.ToperatorMobile;
import cn.zeppin.product.itic.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class ToperatorMobileDAO extends BaseDAO<ToperatorMobile, Integer> implements IToperatorMobileDAO {
	
	/**
	 * 新增Controller信息
	 */
	@Override
//	@Caching(put={@CachePut(value = "operatorMobiles", key = "'operatorMobiles:' + #operatorMobile.uuid")}, evict={@CacheEvict(value = "alloperatorMobiles", allEntries = true)})
	public ToperatorMobile insert(ToperatorMobile operatorMobile){
		return super.insert(operatorMobile);
	}
	
	/**
	 * 删除Controller信息
	 */
	@Override
//	@Caching(evict={@CacheEvict(value = "operatorMobiles", key = "'operatorMobiles:' + #operatorMobile.uuid"), @CacheEvict(value = "alloperatorMobiles", allEntries = true)})
	public ToperatorMobile delete(ToperatorMobile operatorMobile){
		return super.delete(operatorMobile);
	}
	
	/**
	 * 修改Controller信息
	 */
	@Override
//	@Caching(put={@CachePut(value = "operatorMobiles", key = "'operatorMobiles:' + #operatorMobile.uuid")}, evict={@CacheEvict(value = "alloperatorMobiles", allEntries = true)})
	public ToperatorMobile update(ToperatorMobile operatorMobile){
		return super.update(operatorMobile);
	}
	
	@Override
//	@Cacheable(value = "operatorMobiles", key = "'operatorMobiles:' + #uuid")
	public ToperatorMobile get(Integer id) {
		
		return super.get(id);
	}
	
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.ID as id,tp.FK_TOPERATOR as fkToperator,tp.TO_MOBILE as toMobile,tp.TO_TEL as toTel "
				+ " from TOPERATOR_MOBILE tp where 1=1 ");
		//名称
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID <> " + inputParams.get("id"));
		}
		if (inputParams.get("fkToperator") != null && !"".equals(inputParams.get("fkToperator"))) {
			builder.append(" and tp.FK_TOPERATOR = " + inputParams.get("fkToperator"));
		}
		//
		if (inputParams.get("toMobile") != null && !"".equals(inputParams.get("toMobile"))) {
			builder.append(" and tp.TO_MOBILE = '" + inputParams.get("toMobile") + "'");
		}
		//
		if (inputParams.get("toTel") != null && !"".equals(inputParams.get("toTel"))) {
			builder.append(" and tp.TO_TEL = '" + inputParams.get("toTel") + "'");
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.ID desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from TOPERATOR_MOBILE tp where 1=1 ");
		
		//名称
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID <> " + inputParams.get("id"));
		}
		if (inputParams.get("fkToperator") != null && !"".equals(inputParams.get("fkToperator"))) {
			builder.append(" and tp.FK_TOPERATOR = " + inputParams.get("fkToperator"));
		}
		//
		if (inputParams.get("toMobile") != null && !"".equals(inputParams.get("toMobile"))) {
			builder.append(" and tp.TO_MOBILE = '" + inputParams.get("toMobile") + "'");
		}
		//
		if (inputParams.get("toTel") != null && !"".equals(inputParams.get("toTel"))) {
			builder.append(" and tp.TO_TEL = '" + inputParams.get("toTel") + "'");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<ToperatorMobile> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		StringBuilder builder = new StringBuilder();
		builder.append("from ToperatorMobile tp where 1=1 ");
		
		//名称
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.id <> " + inputParams.get("id"));
		}
		if (inputParams.get("fkToperator") != null && !"".equals(inputParams.get("fkToperator"))) {
			builder.append(" and tp.fkToperator = " + inputParams.get("fkToperator"));
		}
		//
		if (inputParams.get("toMobile") != null && !"".equals(inputParams.get("toMobile"))) {
			builder.append(" and tp.toMobile = '" + inputParams.get("toMobile") + "'");
		}
		//
		if (inputParams.get("toTel") != null && !"".equals(inputParams.get("toTel"))) {
			builder.append(" and tp.toTel = '" + inputParams.get("toTel") + "'");
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.id desc ");
		}
		return super.getByHQL(builder.toString(), pageNum, pageSize);
	}

	/**
	  * 日结删除客户经理(删除客户经理)
	  * 3
	  */
	public void dailyDelete(){
		String sql = "delete tm from TOPERATOR_MOBILE tm "
				+ "left join TOPERATOR t on tm.FK_TOPERATOR=t.OP_CODE and t.STATUS = 1 "
				+ "where t.OP_CODE is null";
		super.executeSQL(sql);
	}
	
	/**
	  * 日结新增客户经理(新增客户经理)
	  */
	public void dailyInsert(){
		//新增客户经理
		String sql = "insert into TOPERATOR_MOBILE "
				+ "select t.op_code FK_TOPERATOR, null as TO_MOBILE, null as TO_TEL "
				+ "from TOPERATOR t "
				+ "left join TOPERATOR_MOBILE tm on t.OP_CODE=tm.FK_TOPERATOR "
				+ "where t.STATUS = 1 and tm.ID is null";
		super.executeSQL(sql);
	}

	@Override
	public List<Entity> getListForNumPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.ID as id,tp.FK_TOPERATOR as fkToperator,tp.TO_MOBILE as toMobile,tp.TO_TEL as toTel "
				+ " from TOPERATOR_MOBILE tp left join TOPERATOR tpo on tp.FK_TOPERATOR=tpo.OP_CODE where 1=1 ");
		//名称
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID <> " + inputParams.get("id"));
		}
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and tpo.OP_NAME like '%" + inputParams.get("name") + "%'");
		}
		if (inputParams.get("fkToperator") != null && !"".equals(inputParams.get("fkToperator"))) {
			builder.append(" and tp.FK_TOPERATOR = " + inputParams.get("fkToperator"));
		}
		//
		if (inputParams.get("toMobile") != null && !"".equals(inputParams.get("toMobile"))) {
			builder.append(" and tp.TO_MOBILE = '" + inputParams.get("toMobile") + "'");
		}
		//
		if (inputParams.get("toTel") != null && !"".equals(inputParams.get("toTel"))) {
			builder.append(" and tp.TO_TEL = '" + inputParams.get("toTel") + "'");
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.ID desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCountForNumPage(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from TOPERATOR_MOBILE tp left join TOPERATOR tpo on tp.FK_TOPERATOR=tpo.OP_CODE where 1=1 ");
		
		//名称
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID <> " + inputParams.get("id"));
		}
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and tpo.OP_NAME like '%" + inputParams.get("name") + "%'");
		}
		if (inputParams.get("fkToperator") != null && !"".equals(inputParams.get("fkToperator"))) {
			builder.append(" and tp.FK_TOPERATOR = " + inputParams.get("fkToperator"));
		}
		//
		if (inputParams.get("toMobile") != null && !"".equals(inputParams.get("toMobile"))) {
			builder.append(" and tp.TO_MOBILE = '" + inputParams.get("toMobile") + "'");
		}
		//
		if (inputParams.get("toTel") != null && !"".equals(inputParams.get("toTel"))) {
			builder.append(" and tp.TO_TEL = '" + inputParams.get("toTel") + "'");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public void updateTm(ToperatorMobile tm) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" update TOPERATOR_MOBILE set FK_TOPERATOR="+tm.getFkToperator());
		builder.append(", TO_MOBILE='"+tm.getToMobile()+"'");
		builder.append(", TO_TEL='"+tm.getToTel()+"'");
		builder.append(" where ID="+tm.getId());
		super.executeSQL(builder.toString());
	}
}
