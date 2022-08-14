/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITcustomersDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.Tcustomers;
import cn.zeppin.product.itic.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class TcustomersDAO extends BaseDAO<Tcustomers, Integer> implements ITcustomersDAO {
	
	/**
	 * 新增Controller信息
	 */
	@Override
	public Tcustomers insert(Tcustomers customers){
		return super.insert(customers);
	}
	
	/**
	 * 删除Controller信息
	 */
	@Override
	public Tcustomers delete(Tcustomers customers){
		return super.delete(customers);
	}
	
	/**
	 * 修改Controller信息
	 */
	@Override
	public Tcustomers update(Tcustomers customers){
		return super.update(customers);
	}
	
	@Override
	public Tcustomers get(Integer id) {
		
		return super.get(id);
	}
	
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.CUST_ID as custId,tp.CUST_NO as custNo,tp.CUST_NAME as custName,tp.CUST_TEL as custTel, "
				+ " tp.O_TEL as oTel, tp.H_TEL as hTel, tp.MOBILE as mobile, tp.PinYinSimple as pinYinSimple, tp.SERVICE_MAN as serviceMan "
				+ " from Tcustomers tp where 1=1 ");
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tp.MOBILE like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.PinYinSimple like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.PinYinWhole like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.CUST_NAME like '%" + inputParams.get("name") + "%') ");
		}
		//
		if (inputParams.get("custId") != null && !"".equals(inputParams.get("custId"))) {
			builder.append(" and tp.CUST_ID = " + inputParams.get("custId"));
		}
		//
		if (inputParams.get("custNo") != null && !"".equals(inputParams.get("custNo"))) {
			builder.append(" and tp.CUST_NO = " + inputParams.get("custNo"));
		}
		//状态
		if (inputParams.get("custName") != null && !"".equals(inputParams.get("custName"))) {
			builder.append(" and tp.CUST_NAME = '" + inputParams.get("custName") + "'");
		}
		
		if (inputParams.get("pinYinSimple") != null && !"".equals(inputParams.get("pinYinSimple"))) {
			builder.append(" and tp.PinYinSimple = '" + inputParams.get("pinYinSimple") + "'");
		}
		
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and tp.MOBILE = '" + inputParams.get("mobile") + "'");
		}
		
		if (inputParams.get("serviceMan") != null && !"".equals(inputParams.get("serviceMan"))) {
			builder.append(" and tp.SERVICE_MAN = '" + inputParams.get("serviceMan") + "'");
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.CUST_NO desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from Tcustomers tp where 1=1");
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tp.MOBILE like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.PinYinSimple like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.PinYinWhole like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.CUST_NAME like '%" + inputParams.get("name") + "%') ");
		}
		if (inputParams.get("custId") != null && !"".equals(inputParams.get("custId"))) {
			builder.append(" and tp.CUST_ID = " + inputParams.get("custId"));
		}
		if (inputParams.get("custType") != null && !"".equals(inputParams.get("custType"))) {
			builder.append(" and tp.CUST_TYPE = " + inputParams.get("custType"));
		}
		//
		if (inputParams.get("custNo") != null && !"".equals(inputParams.get("custNo"))) {
			builder.append(" and tp.CUST_NO = " + inputParams.get("custNo"));
		}
		//状态
		if (inputParams.get("custName") != null && !"".equals(inputParams.get("custName"))) {
			builder.append(" and tp.CUST_NAME = '" + inputParams.get("custName") + "'");
		}
		
		if (inputParams.get("pinYinSimple") != null && !"".equals(inputParams.get("pinYinSimple"))) {
			builder.append(" and tp.PinYinSimple = '" + inputParams.get("pinYinSimple") + "'");
		}
		
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and tp.MOBILE = '" + inputParams.get("mobile") + "'");
		}
		
		if (inputParams.get("serviceMan") != null && !"".equals(inputParams.get("serviceMan"))) {
			builder.append(" and tp.SERVICE_MAN = " + inputParams.get("serviceMan"));
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Tcustomers> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("from Tcustomers tp where 1=1 ");
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tp.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.pinYinSimple like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.pinYinWhole like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tp.custName like '%" + inputParams.get("name") + "%') ");
		}
		if (inputParams.get("custId") != null && !"".equals(inputParams.get("custId"))) {
			builder.append(" and tp.custId = " + inputParams.get("custId"));
		}
		if (inputParams.get("custType") != null && !"".equals(inputParams.get("custType"))) {
			builder.append(" and tp.custType = " + inputParams.get("custType"));
		}
		//
		if (inputParams.get("custNo") != null && !"".equals(inputParams.get("custNo"))) {
			builder.append(" and tp.custNo = " + inputParams.get("custNo"));
		}
		//状态
		if (inputParams.get("custName") != null && !"".equals(inputParams.get("custName"))) {
			builder.append(" and tp.custName = '" + inputParams.get("custName") + "'");
		}
		
		if (inputParams.get("pinYinSimple") != null && !"".equals(inputParams.get("pinYinSimple"))) {
			builder.append(" and tp.pinYinSimple = '" + inputParams.get("pinYinSimple") + "'");
		}
		
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and tp.mobile = '" + inputParams.get("mobile") + "'");
		}
		
		if (inputParams.get("serviceMan") != null && !"".equals(inputParams.get("serviceMan"))) {
			builder.append(" and tp.serviceMan = " + inputParams.get("serviceMan"));
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.custNo desc ");
		}
		return super.getByHQL(builder.toString(), pageNum, pageSize);
	}

}
