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

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountTransferOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.CompanyAccountTransferOperate;
import cn.zeppin.product.ntb.core.entity.CompanyAccountTransferOperate.CompanyAccountTransferOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class CompanyAccountTransferOperateDAO extends BaseDAO<CompanyAccountTransferOperate, String> implements ICompanyAccountTransferOperateDAO {


	/**
	 * 向数据库添加一条CompanyAccountTransferOperate数据
	 * 向缓存companyAccountTransferOperates添加一条CompanyAccountTransferOperate记录，key值为uuid
	 * 清空缓存listCompanyAccountTransferOperates的所有记录
	 * @param companyAccountTransferOperate
	 * @return CompanyAccountTransferOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "companyAccountTransferOperates", key = "'companyAccountTransferOperate:' + #companyAccountTransferOperate.uuid")}, evict={@CacheEvict(value = "listCompanyAccountTransferOperates", allEntries = true)})
	public CompanyAccountTransferOperate insert(CompanyAccountTransferOperate companyAccountTransferOperate) {
		return super.insert(companyAccountTransferOperate);
	}

	/**
	 * 数据库删除一条CompanyAccountTransferOperate数据
	 * 在缓存companyAccountTransferOperates中删除一条CompanyAccountTransferOperate记录，key值为uuid
	 * 清空缓存listCompanyAccountTransferOperates的所有记录
	 * @param companyAccountTransferOperate
	 * @return CompanyAccountTransferOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "companyAccountTransferOperates", key = "'companyAccountTransferOperate:' + #companyAccountTransferOperate.uuid"), @CacheEvict(value = "listCompanyAccountTransferOperates", allEntries = true)})
	public CompanyAccountTransferOperate delete(CompanyAccountTransferOperate companyAccountTransferOperate) {
		return super.delete(companyAccountTransferOperate);
	}

	/**
	 * 向数据库更新一条CompanyAccountTransferOperate数据
	 * 在缓存companyAccountTransferOperates中更新一条CompanyAccountTransferOperate记录，key值为uuid
	 * 清空缓存listCompanyAccountTransferOperates的所有记录
	 * @param companyAccountTransferOperate
	 * @return CompanyAccountTransferOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "companyAccountTransferOperates", key = "'companyAccountTransferOperate:' + #companyAccountTransferOperate.uuid")}, evict={@CacheEvict(value = "listCompanyAccountTransferOperates", allEntries = true)})
	public CompanyAccountTransferOperate update(CompanyAccountTransferOperate companyAccountTransferOperate) {
		return super.update(companyAccountTransferOperate);
	}

	/**
	 * 根据uuid得到一个CompanyAccountTransferOperate信息
	 * 向缓存companyAccountTransferOperates添加一条CompanyAccountTransferOperate记录，key值为uuid
	 * 清空缓存listCompanyAccountTransferOperates的所有记录
	 * @param uuid
	 * @return CompanyAccountTransferOperate
	 */
	@Override
	@Cacheable(cacheNames = "companyAccountTransferOperates", key = "'companyAccountTransferOperate:' + #uuid")
	public CompanyAccountTransferOperate get(String uuid) {
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
		builder.append(" select cato.uuid, cato.company_account_history as companyAccountHistory, "
				+ "cato.type, cato.value, cato.reason,cato.status,cato.checker,cato.checktime,cato.creator,cato.createtime,cato.submittime");
		builder.append(" from company_account_transfer_operate cato where 1=1 ");
		//交易类型
		if (!"all".equals(inputParams.get("transferType"))) {
			String transferType = "('')";
			if("outside".equals(inputParams.get("transferType"))){
				transferType = "('" + CompanyAccountTransferOperateType.RECHARGE + "','" + CompanyAccountTransferOperateType.EXPEND + "')";
			} else if("inside".equals(inputParams.get("transferType"))){
				transferType = "('" + CompanyAccountTransferOperateType.TRANSFER + "')";
			}
			builder.append(" and cato.type in " + transferType + " ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and cato.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and cato.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and cato.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and cato.status in ('draft','unchecked','checked','unpassed') ");
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and cato.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cato.creator = '" + inputParams.get("creator") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by cato.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by cato.createtime desc ");
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
		builder.append(" select count(*) from company_account_transfer_operate cato where 1=1 ");
		//交易类型
		if (!"all".equals(inputParams.get("transferType"))) {
			String transferType = "('')";
			if("outside".equals(inputParams.get("transferType"))){
				transferType = "('" + CompanyAccountTransferOperateType.RECHARGE + "','" + CompanyAccountTransferOperateType.EXPEND + "')";
			} else if("inside".equals(inputParams.get("transferType"))){
				transferType = "('" + CompanyAccountTransferOperateType.TRANSFER + "')";
			}
			builder.append(" and cato.type in " + transferType + " ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and cato.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and cato.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and cato.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and cato.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and cato.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cato.creator = '" + inputParams.get("creator") + "' ");
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
		builder.append("select cato.status, count(*) as count from company_account_transfer_operate cato where 1=1");
		builder.append(" and cato.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
		//交易类型
		if (!"all".equals(inputParams.get("transferType"))) {
			String transferType = "('')";
			if("outside".equals(inputParams.get("transferType"))){
				transferType = "('" + CompanyAccountTransferOperateType.RECHARGE + "','" + CompanyAccountTransferOperateType.EXPEND + "')";
			} else if("inside".equals(inputParams.get("transferType"))){
				transferType = "('" + CompanyAccountTransferOperateType.TRANSFER + "')";
			}
			builder.append(" and cato.type in " + transferType + " ");
		}
		//状态
		if (inputParams.get("status") != null && "all".equals(inputParams.get("status"))) {
			builder.append(" and cato.status in ('unchecked','checked','unpassed') ");//全部-去掉已删除的(审核-去掉草稿)
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cato.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by cato.status");
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
		builder.append("select cato.type as status, count(*) as count from company_account_transfer_operate cato where 1=1");
		//交易类型
		if (!"all".equals(inputParams.get("transferType"))) {
			String transferType = "('')";
			if("outside".equals(inputParams.get("transferType"))){
				transferType = "('" + CompanyAccountTransferOperateType.RECHARGE + "','" + CompanyAccountTransferOperateType.EXPEND + "')";
			} else if("inside".equals(inputParams.get("transferType"))){
				transferType = "('" + CompanyAccountTransferOperateType.TRANSFER + "')";
			}
			builder.append(" and cato.type in " + transferType + " ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and cato.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and cato.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and cato.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cato.creator = '" + inputParams.get("creator") + "' ");
		}		
		builder.append(" group by cato.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
