/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.IToperatorDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.Toperator;
import cn.zeppin.product.itic.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class ToperatorDAO extends BaseDAO<Toperator, Integer> implements IToperatorDAO {
	
	/**
	 * 新增Controller信息
	 */
	@Override
	public Toperator insert(Toperator operator){
		return super.insert(operator);
	}
	
	/**
	 * 删除Controller信息
	 */
	@Override
	public Toperator delete(Toperator operator){
		return super.delete(operator);
	}
	
	/**
	 * 修改Controller信息
	 */
	@Override
	public Toperator update(Toperator operator){
		return super.update(operator);
	}
	
	@Override
	public Toperator get(Integer id) {
		
		return super.get(id);
	}
	
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.OP_CODE as opCode,tp.OP_NAME as opName,tp.DEPART_ID as departId,tp.ROLE_ID as roleId, "
				+ " tp.STATUS as status, tp.ONLINE as online, tp.PAGESIZE as pagesize, tp.MOBILE as mobile, tp.O_TEL as oTel"
				+ " from TOPERATOR tp where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tp.OP_NAME like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.MOBILE like '%" + inputParams.get("name") + "%') ");
			
		}
		if (inputParams.get("opCode") != null && !"".equals(inputParams.get("opCode"))) {
			builder.append(" and tp.OP_CODE like '%" + inputParams.get("opCode") + "%' ");
		}
		if (inputParams.get("opName") != null && !"".equals(inputParams.get("opName"))) {
			builder.append(" and tp.OP_NAME like '%" + inputParams.get("opName") + "%' ");
		}
		//
		if (inputParams.get("departId") != null && !"".equals(inputParams.get("departId"))) {
			builder.append(" and tp.DEPART_ID = " + inputParams.get("departId"));
		}
		//
		if (inputParams.get("roleId") != null && !"".equals(inputParams.get("roleId"))) {
			builder.append(" and tp.ROLE_ID = " + inputParams.get("roleId"));
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.STATUS = " + inputParams.get("status"));
		}
		
		if (inputParams.get("online") != null && !"".equals(inputParams.get("online"))) {
			builder.append(" and tp.ONLINE = " + inputParams.get("online"));
		}
		
		if (inputParams.get("pagesize") != null && !"".equals(inputParams.get("pagesize"))) {
			builder.append(" and tp.PAGESIZE = " + inputParams.get("pagesize"));
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.DEPART_ID,tp.OP_CODE desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from TOPERATOR tp where 1=1 ");
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tp.OP_NAME like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.MOBILE like '%" + inputParams.get("name") + "%') ");
			
		}
		
		//名称
		if (inputParams.get("opCode") != null && !"".equals(inputParams.get("opCode"))) {
			builder.append(" and tp.OP_CODE like '%" + inputParams.get("opCode") + "%' ");
		}
		if (inputParams.get("opName") != null && !"".equals(inputParams.get("opName"))) {
			builder.append(" and tp.OP_NAME like '%" + inputParams.get("opName") + "%' ");
		}
		//
		if (inputParams.get("departId") != null && !"".equals(inputParams.get("departId"))) {
			builder.append(" and tp.DEPART_ID = " + inputParams.get("departId"));
		}
		//
		if (inputParams.get("roleId") != null && !"".equals(inputParams.get("roleId"))) {
			builder.append(" and tp.ROLE_ID = " + inputParams.get("roleId"));
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.STATUS = " + inputParams.get("status"));
		}
		
		if (inputParams.get("online") != null && !"".equals(inputParams.get("online"))) {
			builder.append(" and tp.ONLINE = " + inputParams.get("online"));
		}
		
		if (inputParams.get("pagesize") != null && !"".equals(inputParams.get("pagesize"))) {
			builder.append(" and tp.PAGESIZE = " + inputParams.get("pagesize"));
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Toperator> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("from Toperator tp where 1=1 ");
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tp.opName like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.mobile like '%" + inputParams.get("name") + "%') ");
			
		}
		//名称
		if (inputParams.get("opCode") != null && !"".equals(inputParams.get("opCode"))) {
			builder.append(" and tp.opCode like '%" + inputParams.get("opCode") + "%' ");
		}
		if (inputParams.get("opName") != null && !"".equals(inputParams.get("opName"))) {
			builder.append(" and tp.opName like '%" + inputParams.get("opName") + "%' ");
		}
		//
		if (inputParams.get("departId") != null && !"".equals(inputParams.get("departId"))) {
			builder.append(" and tp.departId = " + inputParams.get("departId"));
		}
		//
		if (inputParams.get("roleId") != null && !"".equals(inputParams.get("roleId"))) {
			builder.append(" and tp.roleId = " + inputParams.get("roleId"));
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.status = " + inputParams.get("status"));
		}
		
		if (inputParams.get("online") != null && !"".equals(inputParams.get("online"))) {
			builder.append(" and tp.online = " + inputParams.get("online"));
		}
		
		if (inputParams.get("pagesize") != null && !"".equals(inputParams.get("pagesize"))) {
			builder.append(" and tp.pagesize = " + inputParams.get("pagesize"));
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.departId,tp.opCode desc ");
		}
		return super.getByHQL(builder.toString(), pageNum, pageSize);
	}

	@Override
	public List<Entity> getListForNumPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.OP_CODE as opCode,tp.OP_NAME as opName,tp.DEPART_ID as departId,tp.ROLE_ID as roleId, "
				+ " tp.STATUS as status, tp.ONLINE as online, tp.PAGESIZE as pagesize, tp.MOBILE as mobile, tp.O_TEL as oTel"
				+ " from TOPERATOR_MOBILE tm left join TOPERATOR tp on tm.FK_TOPERATOR=tp.OP_CODE where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tp.OP_NAME like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.MOBILE like '%" + inputParams.get("name") + "%') ");
			
		}
		if (inputParams.get("opCode") != null && !"".equals(inputParams.get("opCode"))) {
			builder.append(" and tp.OP_CODE like '%" + inputParams.get("opCode") + "%' ");
		}
		if (inputParams.get("opName") != null && !"".equals(inputParams.get("opName"))) {
			builder.append(" and tp.OP_NAME like '%" + inputParams.get("opName") + "%' ");
		}
		//
		if (inputParams.get("departId") != null && !"".equals(inputParams.get("departId"))) {
			builder.append(" and tp.DEPART_ID = " + inputParams.get("departId"));
		}
		//
		if (inputParams.get("roleId") != null && !"".equals(inputParams.get("roleId"))) {
			builder.append(" and tp.ROLE_ID = " + inputParams.get("roleId"));
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.STATUS = " + inputParams.get("status"));
		}
		
		if (inputParams.get("online") != null && !"".equals(inputParams.get("online"))) {
			builder.append(" and tp.ONLINE = " + inputParams.get("online"));
		}
		
		if (inputParams.get("pagesize") != null && !"".equals(inputParams.get("pagesize"))) {
			builder.append(" and tp.PAGESIZE = " + inputParams.get("pagesize"));
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.DEPART_ID,tp.OP_CODE desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public List<Entity> getListForSearchPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.OP_CODE as opCode,tp.OP_NAME as opName,tp.DEPART_ID as departId,tp.ROLE_ID as roleId, "
				+ " tp.STATUS as status, tp.ONLINE as online, tp.PAGESIZE as pagesize, tp.MOBILE as mobile, tp.O_TEL as oTel"
				+ " from TOPERATOR tp left join TOPERATOR_MOBILE tm on tp.OP_CODE=tm.FK_TOPERATOR where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tp.OP_NAME like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.MOBILE like '%" + inputParams.get("name") + "%') ");
			
		}
		
		if (inputParams.get("opCode") != null && !"".equals(inputParams.get("opCode"))) {
			builder.append(" and tp.OP_CODE like '%" + inputParams.get("opCode") + "%' ");
		}
		if (inputParams.get("opName") != null && !"".equals(inputParams.get("opName"))) {
			builder.append(" and tp.OP_NAME like '%" + inputParams.get("opName") + "%' ");
		}
		//
		if (inputParams.get("departId") != null && !"".equals(inputParams.get("departId"))) {
			builder.append(" and tp.DEPART_ID = " + inputParams.get("departId"));
		}
		//
		if (inputParams.get("roleId") != null && !"".equals(inputParams.get("roleId"))) {
			builder.append(" and tp.ROLE_ID = " + inputParams.get("roleId"));
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.STATUS = " + inputParams.get("status"));
		}
		
		if (inputParams.get("online") != null && !"".equals(inputParams.get("online"))) {
			builder.append(" and tp.ONLINE = " + inputParams.get("online"));
		}
		
		if (inputParams.get("pagesize") != null && !"".equals(inputParams.get("pagesize"))) {
			builder.append(" and tp.PAGESIZE = " + inputParams.get("pagesize"));
		}
		
		//其他搜索条件
		if (inputParams.get("isnull") != null && !"".equals(inputParams.get("isnull"))) {
			builder.append(" and tm.FK_TOPERATOR " + inputParams.get("isnull"));
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.DEPART_ID,tp.OP_CODE desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCountForSearchPage(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from TOPERATOR tp left join TOPERATOR_MOBILE tm on tp.OP_CODE=tm.FK_TOPERATOR where 1=1 ");
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tp.OP_NAME like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.MOBILE like '%" + inputParams.get("name") + "%') ");
			
		}
		
		//名称
		if (inputParams.get("opCode") != null && !"".equals(inputParams.get("opCode"))) {
			builder.append(" and tp.OP_CODE like '%" + inputParams.get("opCode") + "%' ");
		}
		if (inputParams.get("opName") != null && !"".equals(inputParams.get("opName"))) {
			builder.append(" and tp.OP_NAME like '%" + inputParams.get("opName") + "%' ");
		}
		//
		if (inputParams.get("departId") != null && !"".equals(inputParams.get("departId"))) {
			builder.append(" and tp.DEPART_ID = " + inputParams.get("departId"));
		}
		//
		if (inputParams.get("roleId") != null && !"".equals(inputParams.get("roleId"))) {
			builder.append(" and tp.ROLE_ID = " + inputParams.get("roleId"));
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.STATUS = " + inputParams.get("status"));
		}
		
		if (inputParams.get("online") != null && !"".equals(inputParams.get("online"))) {
			builder.append(" and tp.ONLINE = " + inputParams.get("online"));
		}
		
		if (inputParams.get("pagesize") != null && !"".equals(inputParams.get("pagesize"))) {
			builder.append(" and tp.PAGESIZE = " + inputParams.get("pagesize"));
		}
		
		//其他搜索条件
		if (inputParams.get("isnull") != null && !"".equals(inputParams.get("isnull"))) {
			builder.append(" and tm.FK_TOPERATOR " + inputParams.get("isnull"));
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

}
