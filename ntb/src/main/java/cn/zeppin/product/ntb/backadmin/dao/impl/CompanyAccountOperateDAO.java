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

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.CompanyAccountOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class CompanyAccountOperateDAO extends BaseDAO<CompanyAccountOperate, String> implements ICompanyAccountOperateDAO {


	/**
	 * 向数据库添加一条CompanyAccountOperate数据
	 * 向缓存companyAccountOperates添加一条CompanyAccountOperate记录，key值为uuid
	 * 清空缓存listCompanyAccountOperates的所有记录
	 * @param companyAccountOperate
	 * @return CompanyAccountOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "companyAccountOperates", key = "'companyAccountOperate:' + #companyAccountOperate.uuid")}, evict={@CacheEvict(value = "listCompanyAccountOperates", allEntries = true)})
	public CompanyAccountOperate insert(CompanyAccountOperate companyAccountOperate) {
		return super.insert(companyAccountOperate);
	}

	/**
	 * 数据库删除一条CompanyAccountOperate数据
	 * 在缓存companyAccountOperates中删除一条CompanyAccountOperate记录，key值为uuid
	 * 清空缓存listCompanyAccountOperates的所有记录
	 * @param companyAccountOperate
	 * @return CompanyAccountOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "companyAccountOperates", key = "'companyAccountOperate:' + #companyAccountOperate.uuid"), @CacheEvict(value = "listCompanyAccountOperates", allEntries = true)})
	public CompanyAccountOperate delete(CompanyAccountOperate companyAccountOperate) {
		return super.delete(companyAccountOperate);
	}

	/**
	 * 向数据库更新一条CompanyAccountOperate数据
	 * 在缓存companyAccountOperates中更新一条CompanyAccountOperate记录，key值为uuid
	 * 清空缓存listCompanyAccountOperates的所有记录
	 * @param companyAccountOperate
	 * @return CompanyAccountOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "companyAccountOperates", key = "'companyAccountOperate:' + #companyAccountOperate.uuid")}, evict={@CacheEvict(value = "listCompanyAccountOperates", allEntries = true)})
	public CompanyAccountOperate update(CompanyAccountOperate companyAccountOperate) {
		return super.update(companyAccountOperate);
	}

	/**
	 * 根据uuid得到一个CompanyAccountOperate信息
	 * 向缓存companyAccountOperates添加一条CompanyAccountOperate记录，key值为uuid
	 * 清空缓存listCompanyAccountOperates的所有记录
	 * @param uuid
	 * @return CompanyAccountOperate
	 */
	@Override
	@Cacheable(cacheNames = "companyAccountOperates", key = "'companyAccountOperate:' + #uuid")
	public CompanyAccountOperate get(String uuid) {
		return super.get(uuid);
	}

	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select cao.uuid,cao.company_account as companyAccount,cao.type,cao.value,"
				+ "cao.reason,cao.status,cao.checker,cao.checktime,cao.creator,cao.createtime,cao.submittime");
		builder.append(" from company_account_operate cao left join company_account ca on cao.company_account = ca.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (ca.account_name like '%" + inputParams.get("name") + "%' ");
			builder.append(" or cao.value like '%\"accountName\":\"%" + inputParams.get("name") + "%\",\"accountNum\"%') ");
		}
		//企业账户
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and cao.company_account = '" + inputParams.get("companyAccount") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and cao.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and cao.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and cao.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and cao.status in ('draft','unchecked','checked','unpassed') ");
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and cao.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cao.creator = '" + inputParams.get("creator") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by cao.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by cao.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	/**
	 * 获取总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from company_account_operate cao left join company_account ca on cao.company_account = ca.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (ca.account_name like '%" + inputParams.get("name") + "%' ");
			builder.append(" or cao.value like '%\"accountName\":\"%" + inputParams.get("name") + "%\",\"accountNum\"%') ");
		}
		//企业账户
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and cao.company_account = '" + inputParams.get("companyAccount") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and cao.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and cao.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and cao.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and cao.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and cao.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cao.creator = '" + inputParams.get("creator") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select cao.status, count(*) as count from company_account_operate cao where 1=1");
		builder.append(" and cao.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
		//状态
		if (inputParams.get("status") != null && "all".equals(inputParams.get("status"))) {
			builder.append(" and cao.status in ('unchecked','checked','unpassed') ");//全部-去掉已删除的(审核-去掉草稿)
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cao.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by cao.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select cao.type as status, count(*) as count from company_account_operate cao where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and cao.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and cao.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and cao.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cao.creator = '" + inputParams.get("creator") + "' ");
		}		
		builder.append(" group by cao.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
