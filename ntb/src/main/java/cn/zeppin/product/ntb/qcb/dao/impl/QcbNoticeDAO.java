/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbNoticeDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbNotice;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class QcbNoticeDAO extends BaseDAO<QcbNotice, String> implements IQcbNoticeDAO {


	/**
	 * 向数据库添加一条QcbNotice数据
	 * 向缓存QcbNotices添加一条QcbNotice记录，key值为uuid
	 * 清空缓存listPageQcbNotices的所有记录
	 * @param qcbNotice
	 * @return QcbNotice
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbNotices", key = "'qcbNotices:' + #qcbNotice.uuid")}, evict={@CacheEvict(value = "listPageQcbNotices", allEntries = true)})
	public QcbNotice insert(QcbNotice qcbNotice) {
		return super.insert(qcbNotice);
	}

	/**
	 * 向数据库删除一条QcbNotice数据
	 * 在缓存qcbNotices中删除一条QcbNotice记录，key值为uuid
	 * 清空缓存listPageQcbNotices的所有记录
	 * @param qcbNotice
	 * @return QcbNotice
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbNotices", key = "'qcbNotices:' + #qcbNotice.uuid"), @CacheEvict(value = "listPageQcbNotices", allEntries = true), 
			@CacheEvict(value = "listPageBranchQcbNotices", allEntries = true)})
	public QcbNotice delete(QcbNotice qcbNotice) {
		return super.delete(qcbNotice);
	}

	/**
	 * 向数据库更新一条QcbNotice数据
	 * 在缓存qcbNotices中更新一条QcbNotice记录，key值为uuid
	 * 清空缓存listPageQcbNotices的所有记录
	 * @param qcbNotice
	 * @return QcbNotice
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbNotices", key = "'qcbNotices:' + #qcbNotice.uuid")}, evict={@CacheEvict(value = "listPageQcbNotices", allEntries = true),
			@CacheEvict(value = "listPageBranchQcbNotices", allEntries = true)})
	public QcbNotice update(QcbNotice qcbNotice) {
		return super.update(qcbNotice);
	}

	/**
	 * 根据uuid得到一个QcbNotice信息
	 * 向缓存qcbNotices添加一条QcbNotice记录，key值为uuid
	 * @param uuid
	 * @return QcbNotice
	 */
	@Override
	@Cacheable(value = "qcbNotices", key = "'qcbNotices:' + #uuid")
	public QcbNotice get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPageQcbNotices，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageQcbNotices")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select qn.uuid, qn.title, qn.content, qn.starttime, qn.endtime, qn.status, qn.creator, qn.createtime "
				+ " from qcb_notice qn where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qn.title like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and qn.title like '%" + inputParams.get("title") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qn.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qn.status in ('normal','disable') ");
		}
		
		//名称
		if (inputParams.get("content") != null && !"".equals(inputParams.get("content"))) {
			builder.append(" and qn.content like '%" + inputParams.get("content") + "%' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qn.starttime = " + inputParams.get("starttime") + " ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qn.endtime = " + inputParams.get("endtime") + " ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qn.createtime = " + inputParams.get("createtime") + " ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qn.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qn.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_notice qn where 1=1 ");
		//名称
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and qn.title like '%" + inputParams.get("title") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qn.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qn.status in ('normal','disable') ");
		}
		
		//名称
		if (inputParams.get("content") != null && !"".equals(inputParams.get("content"))) {
			builder.append(" and qn.content like '%" + inputParams.get("content") + "%' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qn.starttime = " + inputParams.get("starttime") + " ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qn.endtime = " + inputParams.get("endtime") + " ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qn.createtime = " + inputParams.get("createtime") + " ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}


	@Override
	public List<Entity> getListForWebPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select qn.uuid, qn.title, qn.content, qn.starttime, qn.endtime, qn.status, qn.creator, qn.createtime, r.is_show, r.is_read "
				+ " from qcb_notice qn left join qcb_notice_employee r on qn.uuid=r.qcb_notice where 1=1 ");
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qn.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		//名称
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and qn.title like '%" + inputParams.get("title") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qn.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qn.status in ('normal','disable') ");
		}
		
		//名称
		if (inputParams.get("content") != null && !"".equals(inputParams.get("content"))) {
			builder.append(" and qn.content like '%" + inputParams.get("content") + "%' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qn.starttime = " + inputParams.get("starttime") + " ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qn.endtime = " + inputParams.get("endtime") + " ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qn.createtime = " + inputParams.get("createtime") + " ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qn.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qn.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	/**
	 * 获取分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select qn.status, count(*) as count from qcb_notice qn group by qn.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
