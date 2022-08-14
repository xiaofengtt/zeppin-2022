/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITnumberRelationLogDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog;
import cn.zeppin.product.itic.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class TnumberRelationLogDAO extends BaseDAO<TnumberRelationLog, Integer> implements ITnumberRelationLogDAO {
	
	/**
	 * 新增Controller信息
	 */
	@Override
//	@Caching(put={@CachePut(value = "numberRelations", key = "'numberRelations:' + #numberRelation.uuid")}, evict={@CacheEvict(value = "allnumberRelations", allEntries = true)})
	public TnumberRelationLog insert(TnumberRelationLog numberRelation){
		return super.insert(numberRelation);
	}
	
	/**
	 * 删除Controller信息
	 */
	@Override
//	@Caching(evict={@CacheEvict(value = "numberRelations", key = "'numberRelations:' + #numberRelation.uuid"), @CacheEvict(value = "allnumberRelations", allEntries = true)})
	public TnumberRelationLog delete(TnumberRelationLog numberRelation){
		return super.delete(numberRelation);
	}
	
	/**
	 * 修改Controller信息
	 */
	@Override
//	@Caching(put={@CachePut(value = "numberRelations", key = "'numberRelations:' + #numberRelation.uuid")}, evict={@CacheEvict(value = "allnumberRelations", allEntries = true)})
	public TnumberRelationLog update(TnumberRelationLog numberRelation){
		return super.update(numberRelation);
	}
	
	@Override
//	@Cacheable(value = "numberRelations", key = "'numberRelations:' + #uuid")
	public TnumberRelationLog get(Integer id) {
		
		return super.get(id);
	}
	
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.ID as id,tp.STATUS as status,tp.CREATETIME as createtime,tp.FK_TCUSTOMERS as fkTcustomers, "
				+ " tp.FK_TOPERATOR as fkToperator, tp.TYPE as type, tp.TC_PHONE as tcPhone, tp.TC_TEL as tcTel, "
				+ " tp.FK_TNUMBER_RELATION as fkTnumberRelation,tp.TO_TEL as toTel, tp.TO_MOBILE as toMobile "
				+ " from TNUMBER_RELATION_LOG tp "
				+ " left join Tcustomers tc on tp.FK_TCUSTOMERS = tc.CUST_ID "
				+ " left join TOPERATOR op on tp.FK_TOPERATOR = op.OP_CODE where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tc.MOBILE like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.PinYinSimple like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.PinYinWhole like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.CUST_NAME like '%" + inputParams.get("name") + "%') ");
		}
		
		//名称fkToperator
		if (inputParams.get("oname") != null && !"".equals(inputParams.get("oname"))) {
			builder.append(" and (op.MOBILE like '%" + inputParams.get("oname") + "%' ");
			builder.append(" or op.OP_NAME like '%" + inputParams.get("oname") + "%') ");
		}
		
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID <> " + inputParams.get("id"));
		}
		
		//
		if (inputParams.get("fkTcustomers") != null && !"".equals(inputParams.get("fkTcustomers"))) {
			builder.append(" and tp.FK_TCUSTOMERS = '" + inputParams.get("fkTcustomers") + "'");
		}
		
		//
		if (inputParams.get("fkToperator") != null && !"".equals(inputParams.get("fkToperator"))) {
			builder.append(" and tp.FK_TOPERATOR = '" + inputParams.get("fkToperator") + "'");
		}
		
		//
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.[STATUS] = '" + inputParams.get("status") + "'");
		}
		//
		if (inputParams.get("custName") != null && !"".equals(inputParams.get("custName"))) {
			builder.append(" and tc.CUST_NAME like '%" + inputParams.get("custName") + "%'");
		}
		
		//
		if (inputParams.get("opName") != null && !"".equals(inputParams.get("opName"))) {
			builder.append(" and op.OP_NAME like '%" + inputParams.get("opName") + "%'");
		}
		
		if (inputParams.get("pinYinSimple") != null && !"".equals(inputParams.get("pinYinSimple"))) {
			builder.append(" and tc.PinYinSimple like '%" + inputParams.get("pinYinSimple") + "%'");
		}
		
		if (inputParams.get("tcPhone") != null && !"".equals(inputParams.get("tcPhone"))) {
			builder.append(" and tp.TC_PHONE = '" + inputParams.get("tcPhone") + "'");
		}
		
		if (inputParams.get("tcTel") != null && !"".equals(inputParams.get("tcTel"))) {
			builder.append(" and tp.TC_TEL = '" + inputParams.get("tcTel") + "'");
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.CREATETIME desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) "
				+ " from TNUMBER_RELATION_LOG tp "
				+ " left join Tcustomers tc on tp.FK_TCUSTOMERS = tc.CUST_ID "
				+ " left join TOPERATOR op on tp.FK_TOPERATOR = op.OP_CODE where 1=1 ");
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tc.MOBILE like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.PinYinSimple like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.PinYinWhole like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.CUST_NAME like '%" + inputParams.get("name") + "%') ");
		}
		
		//名称fkToperator
		if (inputParams.get("oname") != null && !"".equals(inputParams.get("oname"))) {
			builder.append(" and (op.MOBILE like '%" + inputParams.get("oname") + "%' ");
			builder.append(" or op.OP_NAME like '%" + inputParams.get("oname") + "%') ");
		}
		
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID <> " + inputParams.get("id"));
		}
		
		//
		if (inputParams.get("fkTcustomers") != null && !"".equals(inputParams.get("fkTcustomers"))) {
			builder.append(" and tp.FK_TCUSTOMERS = '" + inputParams.get("fkTcustomers") + "'");
		}
		
		//
		if (inputParams.get("fkToperator") != null && !"".equals(inputParams.get("fkToperator"))) {
			builder.append(" and tp.FK_TOPERATOR = '" + inputParams.get("fkToperator") + "'");
		}
		
		//
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.[STATUS] = '" + inputParams.get("status") + "'");
		}
		//
		if (inputParams.get("custName") != null && !"".equals(inputParams.get("custName"))) {
			builder.append(" and tc.CUST_NAME like '%" + inputParams.get("custName") + "%'");
		}
		
		//
		if (inputParams.get("opName") != null && !"".equals(inputParams.get("opName"))) {
			builder.append(" and op.OP_NAME like '%" + inputParams.get("opName") + "%'");
		}
		
		if (inputParams.get("pinYinSimple") != null && !"".equals(inputParams.get("pinYinSimple"))) {
			builder.append(" and tc.PinYinSimple like '%" + inputParams.get("pinYinSimple") + "%'");
		}
		
		if (inputParams.get("tcPhone") != null && !"".equals(inputParams.get("tcPhone"))) {
			builder.append(" and tp.TC_PHONE = '" + inputParams.get("tcPhone") + "'");
		}
		
		if (inputParams.get("tcTel") != null && !"".equals(inputParams.get("tcTel"))) {
			builder.append(" and tp.TC_TEL = '" + inputParams.get("tcTel") + "'");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
