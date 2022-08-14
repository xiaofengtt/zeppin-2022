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

import cn.zeppin.product.ntb.backadmin.dao.api.IQcbCompanyHistoryOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class QcbCompanyHistoryOperateDAO extends BaseDAO<QcbCompanyHistoryOperate, String> implements IQcbCompanyHistoryOperateDAO {


	/**
	 * 向数据库添加一条QcbCompanyHistoryOperate数据
	 * 向缓存qcbCompanyHistoryOperates添加一条QcbCompanyHistoryOperate记录，key值为uuid
	 * 清空缓存listQcbCompanyHistoryOperates的所有记录
	 * @param qcbCompanyHistoryOperate
	 * @return QcbCompanyHistoryOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyHistoryOperates", key = "'qcbCompanyHistoryOperate:' + #qcbCompanyHistoryOperate.uuid")}, evict={@CacheEvict(value = "listQcbCompanyHistoryOperates", allEntries = true)})
	public QcbCompanyHistoryOperate insert(QcbCompanyHistoryOperate qcbCompanyHistoryOperate) {
		return super.insert(qcbCompanyHistoryOperate);
	}

	/**
	 * 数据库删除一条QcbCompanyHistoryOperate数据
	 * 在缓存qcbCompanyHistoryOperates中删除一条QcbCompanyHistoryOperate记录，key值为uuid
	 * 清空缓存listQcbCompanyHistoryOperates的所有记录
	 * @param qcbCompanyHistoryOperate
	 * @return QcbCompanyHistoryOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyHistoryOperates", key = "'qcbCompanyHistoryOperate:' + #qcbCompanyHistoryOperate.uuid"), @CacheEvict(value = "listQcbCompanyHistoryOperates", allEntries = true)})
	public QcbCompanyHistoryOperate delete(QcbCompanyHistoryOperate qcbCompanyHistoryOperate) {
		return super.delete(qcbCompanyHistoryOperate);
	}

	/**
	 * 向数据库更新一条QcbCompanyHistoryOperate数据
	 * 在缓存qcbCompanyHistoryOperates中更新一条QcbCompanyHistoryOperate记录，key值为uuid
	 * 清空缓存listQcbCompanyHistoryOperates的所有记录
	 * @param qcbCompanyHistoryOperate
	 * @return QcbCompanyHistoryOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyHistoryOperates", key = "'qcbCompanyHistoryOperate:' + #qcbCompanyHistoryOperate.uuid")}, evict={@CacheEvict(value = "listQcbCompanyHistoryOperates", allEntries = true)})
	public QcbCompanyHistoryOperate update(QcbCompanyHistoryOperate qcbCompanyHistoryOperate) {
		return super.update(qcbCompanyHistoryOperate);
	}

	/**
	 * 根据uuid得到一个QcbCompanyHistoryOperate信息
	 * 向缓存qcbCompanyHistoryOperates添加一条QcbCompanyHistoryOperate记录，key值为uuid
	 * 清空缓存listQcbCompanyHistoryOperates的所有记录
	 * @param uuid
	 * @return QcbCompanyHistoryOperate
	 */
	@Override
	@Cacheable(cacheNames = "qcbCompanyHistoryOperates", key = "'qcbCompanyHistoryOperate:' + #uuid")
	public QcbCompanyHistoryOperate get(String uuid) {
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
		builder.append(" select qcho.uuid, qcho.qcb_company_history as qcbCompanyHistory, "
				+ "qcho.type, qcho.value, qcho.reason,qcho.status,qcho.checker,qcho.checktime,"
				+ "qcho.creator,qcho.createtime,qcho.submittime,qcho.receipt");
		builder.append(" from qcb_company_history_operate qcho where 1=1 ");
//		//交易类型
//		if (!"all".equals(inputParams.get("transferType"))) {
//			String transferType = "('')";
//			if("outside".equals(inputParams.get("transferType"))){
//				transferType = "('" + QcbCompanyHistoryOperateType.RECHARGE + "','" + QcbCompanyHistoryOperateType.EXPEND + "')";
//			} else if("inside".equals(inputParams.get("transferType"))){
//				transferType = "('" + QcbCompanyHistoryOperateType.TRANSFER + "')";
//			}
//			builder.append(" and qcho.type in " + transferType + " ");
//		}
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
		builder.append(" select count(*) from qcb_company_history_operate qcho where 1=1 ");
//		//交易类型
//		if (!"all".equals(inputParams.get("transferType"))) {
//			String transferType = "('')";
//			if("outside".equals(inputParams.get("transferType"))){
//				transferType = "('" + QcbCompanyHistoryOperateType.RECHARGE + "','" + QcbCompanyHistoryOperateType.EXPEND + "')";
//			} else if("inside".equals(inputParams.get("transferType"))){
//				transferType = "('" + QcbCompanyHistoryOperateType.TRANSFER + "')";
//			}
//			builder.append(" and qcho.type in " + transferType + " ");
//		}
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
		builder.append("select qcho.status, count(*) as count from qcb_company_history_operate qcho where 1=1");
		builder.append(" and qcho.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
//		//交易类型
//		if (!"all".equals(inputParams.get("transferType"))) {
//			String transferType = "('')";
//			if("outside".equals(inputParams.get("transferType"))){
//				transferType = "('" + QcbCompanyHistoryOperateType.RECHARGE + "','" + QcbCompanyHistoryOperateType.EXPEND + "')";
//			} else if("inside".equals(inputParams.get("transferType"))){
//				transferType = "('" + QcbCompanyHistoryOperateType.TRANSFER + "')";
//			}
//			builder.append(" and qcho.type in " + transferType + " ");
//		}
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
		builder.append("select qcho.type as status, count(*) as count from qcb_company_history_operate qcho where 1=1");
//		//交易类型
//		if (!"all".equals(inputParams.get("transferType"))) {
//			String transferType = "('')";
//			if("outside".equals(inputParams.get("transferType"))){
//				transferType = "('" + QcbCompanyHistoryOperateType.RECHARGE + "','" + QcbCompanyHistoryOperateType.EXPEND + "')";
//			} else if("inside".equals(inputParams.get("transferType"))){
//				transferType = "('" + QcbCompanyHistoryOperateType.TRANSFER + "')";
//			}
//			builder.append(" and qcho.type in " + transferType + " ");
//		}
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
