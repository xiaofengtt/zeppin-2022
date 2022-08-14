/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITCheckInfoDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TCheckInfo;

@Repository
public class TCheckInfoDAO extends BaseDAO<TCheckInfo, String> implements ITCheckInfoDAO {
	
	@Override
	public TCheckInfo insert(TCheckInfo t){
		return super.insert(t);
	}
	
	@Override
	public TCheckInfo delete(TCheckInfo t){
		return super.delete(t);
	}
	
	@Override
	public TCheckInfo update(TCheckInfo t){
		return super.update(t);
	}
	
	@Override
	public TCheckInfo get(String id) {
		return super.get(id);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from T_CHECK_INFO t where 1=1");
		//数据类型
		if(inputParams.get("datatype") != null && !"".equals(inputParams.get("datatype"))){
			builder.append(" and t.datatype='").append(inputParams.get("datatype")).append("' ");
		}
		
		//数据ID
		if(inputParams.get("dataid") != null && !"".equals(inputParams.get("dataid"))){
			builder.append(" and t.dataid='").append(inputParams.get("dataid")).append("' ");
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
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<TCheckInfo> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts) {
		StringBuilder builder = new StringBuilder();
		builder.append("from TCheckInfo t where 1=1 ");
		//数据类型
		if(inputParams.get("datatype") != null && !"".equals(inputParams.get("datatype"))){
			builder.append(" and t.datatype='").append(inputParams.get("datatype")).append("' ");
		}
		
		//数据ID
		if(inputParams.get("dataid") != null && !"".equals(inputParams.get("dataid"))){
			builder.append(" and t.dataid='").append(inputParams.get("dataid")).append("' ");
		}

		//项目ID
		if(inputParams.get("dataproduct") != null && !"".equals(inputParams.get("dataproduct"))){
			builder.append(" and (t.dataproduct = '").append(inputParams.get("dataproduct")).append("' or t.dataproduct is null)");
		}
		
		//项目ID集合
		if(inputParams.get("products") != null && !"".equals(inputParams.get("products"))){
			builder.append(" and (t.dataproduct in ").append(inputParams.get("products")).append(" or t.dataproduct is null)");
		}
			
		//数据类型集合
		if(inputParams.get("datatypes") != null && !"".equals(inputParams.get("datatypes"))){
			builder.append(" and t.datatype in ").append(inputParams.get("datatypes")).append(" ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by t.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}else{
			builder.append(" order by t.updatetime desc");
		}
		return super.getByHQL(builder.toString(), pageNum, pageSize);
	}

	@Override
	public List<Object[]> getCountListForUser(Map<String, String> inputParams) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select c.name, c.description, c.sort, count(*) from t_Check_Info t ");
		sql.append(" left join t_ss_controller c on t.datatype=c.name where 1=1 ");

		//项目ID
		if(inputParams.get("dataproduct") != null && !"".equals(inputParams.get("dataproduct"))){
			sql.append(" and (t.dataproduct = '").append(inputParams.get("dataproduct")).append("' or t.dataproduct is null)");
		}
		
		//项目ID集合
		if(inputParams.get("products") != null && !"".equals(inputParams.get("products"))){
			sql.append(" and (t.dataproduct in ").append(inputParams.get("products")).append(" or t.dataproduct is null)");
		}
			
		//数据类型集合
		if(inputParams.get("datatypes") != null && !"".equals(inputParams.get("datatypes"))){
			sql.append(" and t.datatype in ").append(inputParams.get("datatypes")).append(" ");
		}
		sql.append(" group by c.name, c.description, c.sort order by c.sort ");
		return super.getBySQL(sql.toString());
	}

	@Override
	public void deleteByData(String datatype, String dataid) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from T_CHECK_INFO t where 1=1 ");
		sql.append(" and t.datatype='").append(datatype).append("' ");
		sql.append(" and t.dataid='").append(dataid).append("'");
		this.executeSQL(sql.toString());
	}

}
