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

import cn.zeppin.product.ntb.backadmin.dao.api.IProductPublishBalanceOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.ProductPublishBalanceOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class ProductPublishBalanceOperateDAO extends BaseDAO<ProductPublishBalanceOperate, String> implements IProductPublishBalanceOperateDAO {
	
	/**
	 * 向数据库添加一条ProductPublishBalanceOperate数据
	 * @param productPublishBalanceOperate
	 * @return ProductPublishBalanceOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "productPublishBalanceOperates", key = "'productPublishBalanceOperates:' + #productPublishBalanceOperate.uuid")})
	public ProductPublishBalanceOperate insert(ProductPublishBalanceOperate productPublishBalanceOperate) {
		return super.insert(productPublishBalanceOperate);
	}

	/**
	 * 向数据库删除一条ProductPublishBalanceOperate数据
	 * @param productPublishBalanceOperate
	 * @return ProductPublishBalanceOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "productPublishBalanceOperates", key = "'productPublishBalanceOperates:' + #productPublishBalanceOperate.uuid")})
	public ProductPublishBalanceOperate delete(ProductPublishBalanceOperate productPublishBalanceOperate) {
		return super.delete(productPublishBalanceOperate);
	}

	/**
	 * 向数据库更新一条ProductPublishBalanceOperate数据
	 * @param productPublishBalanceOperate
	 * @return ProductPublishBalanceOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "productPublishBalanceOperates", key = "'productPublishBalanceOperates:' + #productPublishBalanceOperate.uuid")})
	public ProductPublishBalanceOperate update(ProductPublishBalanceOperate productPublishBalanceOperate) {
		return super.update(productPublishBalanceOperate);
	}

	/**
	 * 根据uuid得到一个ProductPublishBalanceOperate信息
	 * @param uuid
	 * @return ProductPublishBalanceOperate
	 */
	@Override
	@Cacheable(value = "productPublishBalanceOperates", key = "'productPublishBalanceOperates:' + #uuid")
	public ProductPublishBalanceOperate get(String uuid) {
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
		builder.append(" select ppbo.uuid,ppbo.bank_financial_product_publish as bankFinancialProductPublish,ppbo.value,"
				+ "ppbo.reason,ppbo.status,ppbo.checker,ppbo.checktime,ppbo.creator,ppbo.createtime,ppbo.submittime");
		builder.append(" from product_publish_balance_operate ppbo left join bank_financial_product_publish bfpp on ppbo.bank_financial_product_publish = bfpp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (bfpp.name like '%" + inputParams.get("name") + "%' ");
			builder.append(" or ppbo.value like '%\"name\":\"%" + inputParams.get("name") + "%\",\"networkFee\"%') ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and ppbo.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and ppbo.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and ppbo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and ppbo.status in ('draft','unchecked','checked','unpassed') ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ppbo.creator = '" + inputParams.get("creator") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by ppbo.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by ppbo.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	/**
	 * 获取募集产品操作总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from product_publish_balance_operate ppbo left join bank_financial_product_publish bfpp on ppbo.bank_financial_product_publish = bfpp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (bfpp.name like '%" + inputParams.get("name") + "%' ");
			builder.append(" or ppbo.value like '%\"name\":\"%" + inputParams.get("name") + "%\",\"networkFee\"%') ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and ppbo.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and ppbo.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and ppbo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and ppbo.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ppbo.creator = '" + inputParams.get("creator") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取募集产品操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select ppbo.status, count(*) as count from product_publish_balance_operate ppbo where 1=1");
		if (inputParams.get("status") != null && "all".equals(inputParams.get("status"))) {
			builder.append(" and ppbo.status in ('unchecked','checked','unpassed') ");//全部-去掉已删除的(审核-去掉草稿)
		} else {
			builder.append(" and ppbo.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ppbo.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by ppbo.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
}
