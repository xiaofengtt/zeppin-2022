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

import cn.zeppin.product.ntb.backadmin.dao.api.IQcbCompanyFeeOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFeeOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class QcbCompanyFeeOperateDAO extends BaseDAO<QcbCompanyFeeOperate, String> implements IQcbCompanyFeeOperateDAO {


	/**
	 * 向数据库添加一条QcbCompanyFeeOperate数据
	 * 向缓存qcbCompanyFeeOperates添加一条QcbCompanyFeeOperate记录，key值为uuid
	 * 清空缓存listQcbCompanyFeeOperates的所有记录
	 * @param qcbCompanyFeeOperate
	 * @return QcbCompanyFeeOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyFeeOperates", key = "'qcbCompanyFeeOperate:' + #qcbCompanyFeeOperate.uuid")}, evict={@CacheEvict(value = "listQcbCompanyFeeOperates", allEntries = true)})
	public QcbCompanyFeeOperate insert(QcbCompanyFeeOperate qcbCompanyFeeOperate) {
		return super.insert(qcbCompanyFeeOperate);
	}

	/**
	 * 数据库删除一条QcbCompanyFeeOperate数据
	 * 在缓存qcbCompanyFeeOperates中删除一条QcbCompanyFeeOperate记录，key值为uuid
	 * 清空缓存listQcbCompanyFeeOperates的所有记录
	 * @param qcbCompanyFeeOperate
	 * @return QcbCompanyFeeOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyFeeOperates", key = "'qcbCompanyFeeOperate:' + #qcbCompanyFeeOperate.uuid"), @CacheEvict(value = "listQcbCompanyFeeOperates", allEntries = true)})
	public QcbCompanyFeeOperate delete(QcbCompanyFeeOperate qcbCompanyFeeOperate) {
		return super.delete(qcbCompanyFeeOperate);
	}

	/**
	 * 向数据库更新一条QcbCompanyFeeOperate数据
	 * 在缓存qcbCompanyFeeOperates中更新一条QcbCompanyFeeOperate记录，key值为uuid
	 * 清空缓存listQcbCompanyFeeOperates的所有记录
	 * @param qcbCompanyFeeOperate
	 * @return QcbCompanyFeeOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyFeeOperates", key = "'qcbCompanyFeeOperate:' + #qcbCompanyFeeOperate.uuid")}, evict={@CacheEvict(value = "listQcbCompanyFeeOperates", allEntries = true)})
	public QcbCompanyFeeOperate update(QcbCompanyFeeOperate qcbCompanyFeeOperate) {
		return super.update(qcbCompanyFeeOperate);
	}

	/**
	 * 根据uuid得到一个QcbCompanyFeeOperate信息
	 * 向缓存qcbCompanyFeeOperates添加一条QcbCompanyFeeOperate记录，key值为uuid
	 * 清空缓存listQcbCompanyFeeOperates的所有记录
	 * @param uuid
	 * @return QcbCompanyFeeOperate
	 */
	@Override
	@Cacheable(cacheNames = "qcbCompanyFeeOperates", key = "'qcbCompanyFeeOperate:' + #uuid")
	public QcbCompanyFeeOperate get(String uuid) {
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
		builder.append(" select qcho.uuid, qcho.qcb_company_account as qcbCompanyAccount, "
				+ "qcho.type, qcho.value, qcho.reason,qcho.status,qcho.checker,qcho.checktime,qcho.creator,qcho.createtime,qcho.submittime");
		builder.append(" from qcb_company_fee_operate qcho where 1=1 ");
		
		//
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcho.qcb_company_account = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and qcho.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and qcho.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and qcho.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and qcho.status in ('draft','unchecked','checked','unpassed') ");
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qcho.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and qcho.creator = '" + inputParams.get("creator") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by qcho.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by qcho.createtime desc ");
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
		builder.append(" select count(*) from qcb_company_fee_operate qcho where 1=1 ");
		//
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcho.qcb_company_account = '" + inputParams.get("qcbCompany") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and qcho.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and qcho.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and qcho.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and qcho.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qcho.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and qcho.creator = '" + inputParams.get("creator") + "' ");
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
		builder.append("select qcho.status, count(*) as count from qcb_company_fee_operate qcho where 1=1");
		builder.append(" and qcho.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
		//状态
		if (inputParams.get("status") != null && "all".equals(inputParams.get("status"))) {
			builder.append(" and qcho.status in ('unchecked','checked','unpassed') ");//全部-去掉已删除的(审核-去掉草稿)
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and qcho.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by qcho.status");
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
		builder.append("select qcho.type as status, count(*) as count from qcb_company_fee_operate qcho where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and qcho.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and qcho.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and qcho.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and qcho.creator = '" + inputParams.get("creator") + "' ");
		}		
		builder.append(" group by qcho.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
