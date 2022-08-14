/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsLogDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsLog;

@Repository
public class TSsLogDAO extends BaseDAO<TSsLog, String> implements ITSsLogDAO {
	
	@Override
	public TSsLog insert(TSsLog t){
		return super.insert(t);
	}
	
	@Override
	public TSsLog delete(TSsLog t){
		return super.delete(t);
	}
	
	@Override
	public TSsLog update(TSsLog t){
		return super.update(t);
	}
	
	@Override
	public TSsLog get(String id) {
		return super.get(id);
	}
	
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from T_SS_LOG t where 1=1");
		
		//数据类型
		if(inputParams.get("datatype") != null && !"".equals(inputParams.get("datatype"))){
			builder.append(" and t.datatype='").append(inputParams.get("datatype")).append("' ");
		}
		
		//操作人
		if(inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))){
			builder.append(" and t.creator='").append(inputParams.get("creator")).append("' ");
		}
		
		//开始时间
		if(inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))){
			builder.append(" and t.createtime >= to_date('").append(inputParams.get("starttime").toString() + " 00:00:00").append("','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		//结束时间
		if(inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))){
			builder.append(" and t.createtime <= to_date('").append(inputParams.get("endtime").toString() + " 23:59:59").append("','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		//项目ID
		if(inputParams.get("dataproduct") != null && !"".equals(inputParams.get("dataproduct"))){
			builder.append(" and (t.dataproduct = '").append(inputParams.get("dataproduct")).append("' or t.dataproduct is null)");
		}
		
		//项目ID
		if(inputParams.get("products") != null && !"".equals(inputParams.get("products"))){
			builder.append(" and (t.dataproduct in ").append(inputParams.get("products")).append(" or t.dataproduct is null)");
		}
		
		//数据类型集合
		if(inputParams.get("datatypes") != null && !"".equals(inputParams.get("datatypes"))){
			builder.append(" and t.datatype in ").append(inputParams.get("datatypes")).append(" ");
		}
		
		//数据类型
		if(inputParams.get("datatype") != null && !"".equals(inputParams.get("datatype"))){
			builder.append(" and t.datatype='").append(inputParams.get("datatype")).append("' ");
		}
		
		//类型
		if(inputParams.get("type") != null && !"".equals(inputParams.get("type"))){
			builder.append(" and t.type='").append(inputParams.get("type")).append("' ");
		}
		
		//状态
		if(inputParams.get("status") != null && !"".equals(inputParams.get("status"))){
			builder.append(" and t.status='").append(inputParams.get("status")).append("' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<TSsLog> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts) {
		StringBuilder builder = new StringBuilder();
		builder.append("from TSsLog t where 1=1 ");
		
		//数据类型
		if(inputParams.get("datatype") != null && !"".equals(inputParams.get("datatype"))){
			builder.append(" and t.datatype='").append(inputParams.get("datatype")).append("' ");
		}
		
		//操作人
		if(inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))){
			builder.append(" and t.creator='").append(inputParams.get("creator")).append("' ");
		}
		
		//开始时间
		if(inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))){
			builder.append(" and t.createtime >= to_date('").append(inputParams.get("starttime").toString() + " 00:00:00").append("','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		//结束时间
		if(inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))){
			builder.append(" and t.createtime <= to_date('").append(inputParams.get("endtime").toString() + " 23:59:59").append("','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		//项目ID
		if(inputParams.get("dataproduct") != null && !"".equals(inputParams.get("dataproduct"))){
			builder.append(" and (t.dataproduct = '").append(inputParams.get("dataproduct")).append("' or t.dataproduct is null)");
		}
		
		//项目ID
		if(inputParams.get("products") != null && !"".equals(inputParams.get("products"))){
			builder.append(" and (t.dataproduct in ").append(inputParams.get("products")).append(" or t.dataproduct is null)");
		}
		
		//数据类型
		if(inputParams.get("datatype") != null && !"".equals(inputParams.get("datatype"))){
			builder.append(" and t.datatype='").append(inputParams.get("datatype")).append("' ");
		}
		
		//数据类型集合
		if(inputParams.get("datatypes") != null && !"".equals(inputParams.get("datatypes"))){
			builder.append(" and t.datatype in ").append(inputParams.get("datatypes")).append(" ");
		}
		//类型
		if(inputParams.get("type") != null && !"".equals(inputParams.get("type"))){
			builder.append(" and t.type='").append(inputParams.get("type")).append("' ");
		}
		
		//状态
		if(inputParams.get("status") != null && !"".equals(inputParams.get("status"))){
			builder.append(" and t.status='").append(inputParams.get("status")).append("' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by t.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}else{
			builder.append(" order by t.createtime desc");
		}
		return super.getByHQL(builder.toString(), pageNum, pageSize);
	}
}
