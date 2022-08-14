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

import cn.zeppin.product.ntb.qcb.dao.api.IQcbNoticeEmployeeDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbNoticeEmployee;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class QcbNoticeEmployeeDAO extends BaseDAO<QcbNoticeEmployee, String> implements IQcbNoticeEmployeeDAO {


	/**
	 * 向数据库添加一条QcbNoticeEmployee数据
	 * 向缓存QcbNoticeEmployees添加一条QcbNoticeEmployee记录，key值为uuid
	 * 清空缓存listPageQcbNoticeEmployees的所有记录
	 * @param qcbNoticeEmployee
	 * @return QcbNoticeEmployee
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbNoticeEmployees", key = "'qcbNoticeEmployees:' + #qcbNoticeEmployee.uuid")}, evict={@CacheEvict(value = "listPageQcbNoticeEmployees", allEntries = true)})
	public QcbNoticeEmployee insert(QcbNoticeEmployee qcbNoticeEmployee) {
		return super.insert(qcbNoticeEmployee);
	}

	/**
	 * 向数据库删除一条QcbNoticeEmployee数据
	 * 在缓存qcbNoticeEmployees中删除一条QcbNoticeEmployee记录，key值为uuid
	 * 清空缓存listPageQcbNoticeEmployees的所有记录
	 * @param qcbNoticeEmployee
	 * @return QcbNoticeEmployee
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbNoticeEmployees", key = "'qcbNoticeEmployees:' + #qcbNoticeEmployee.uuid"), @CacheEvict(value = "listPageQcbNoticeEmployees", allEntries = true), 
			@CacheEvict(value = "listPageBranchQcbNoticeEmployees", allEntries = true)})
	public QcbNoticeEmployee delete(QcbNoticeEmployee qcbNoticeEmployee) {
		return super.delete(qcbNoticeEmployee);
	}

	/**
	 * 向数据库更新一条QcbNoticeEmployee数据
	 * 在缓存qcbNoticeEmployees中更新一条QcbNoticeEmployee记录，key值为uuid
	 * 清空缓存listPageQcbNoticeEmployees的所有记录
	 * @param qcbNoticeEmployee
	 * @return QcbNoticeEmployee
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbNoticeEmployees", key = "'qcbNoticeEmployees:' + #qcbNoticeEmployee.uuid")}, evict={@CacheEvict(value = "listPageQcbNoticeEmployees", allEntries = true),
			@CacheEvict(value = "listPageBranchQcbNoticeEmployees", allEntries = true)})
	public QcbNoticeEmployee update(QcbNoticeEmployee qcbNoticeEmployee) {
		return super.update(qcbNoticeEmployee);
	}

	/**
	 * 根据uuid得到一个QcbNoticeEmployee信息
	 * 向缓存qcbNoticeEmployees添加一条QcbNoticeEmployee记录，key值为uuid
	 * @param uuid
	 * @return QcbNoticeEmployee
	 */
	@Override
	@Cacheable(value = "qcbNoticeEmployees", key = "'qcbNoticeEmployees:' + #uuid")
	public QcbNoticeEmployee get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPageQcbNoticeEmployees，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageQcbNoticeEmployees")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select r.uuid, b.uuid as noticeUuid, b.title, b.content, b.starttime, b.endtime, b.createtime, r.is_show, r.is_read "
				+ " from qcb_notice b left join qcb_notice_employee r on b.uuid=r.qcb_notice where 1=1 ");
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and b.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		//名称
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and b.title like '%" + inputParams.get("title") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('normal','disable') ");
		}
		
		if (inputParams.get("isShow") != null && !"".equals(inputParams.get("isShow"))) {
			builder.append(" and b.is_show = '" + inputParams.get("isShow") + "' ");
		}
		
		if (inputParams.get("isRead") != null && !"".equals(inputParams.get("isRead"))) {
			builder.append(" and b.is_read = '" + inputParams.get("isRead") + "' ");
		}
		
		//名称
		if (inputParams.get("content") != null && !"".equals(inputParams.get("content"))) {
			builder.append(" and b.content like '%" + inputParams.get("content") + "%' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and b.starttime = " + inputParams.get("starttime") + " ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and b.endtime = " + inputParams.get("endtime") + " ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = " + inputParams.get("createtime") + " ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("b.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by b.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_notice b left join qcb_notice_employee r on b.uuid=r.qcb_notice where 1=1 ");
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and b.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		//名称
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and b.title like '%" + inputParams.get("title") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('normal','disable') ");
		}
		
		
		if (inputParams.get("isShow") != null && !"".equals(inputParams.get("isShow"))) {
			builder.append(" and b.is_show = '" + inputParams.get("isShow") + "' ");
		}
		
		if (inputParams.get("isRead") != null && !"".equals(inputParams.get("isRead"))) {
			builder.append(" and b.is_read = '" + inputParams.get("isRead") + "' ");
		}
		
		//名称
		if (inputParams.get("content") != null && !"".equals(inputParams.get("content"))) {
			builder.append(" and b.content like '%" + inputParams.get("content") + "%' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and b.starttime = " + inputParams.get("starttime") + " ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and b.endtime = " + inputParams.get("endtime") + " ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = " + inputParams.get("createtime") + " ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}


	@Override
	public List<Entity> getListForWebPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select r.uuid, b.title, b.content, b.starttime, b.endtime, b.createtime, r.is_show, r.is_read "
				+ " from qcb_notice b left join qcb_notice_employee r on b.uuid=r.qcb_notice where 1=1 ");
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and b.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		//名称
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and b.title like '%" + inputParams.get("title") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('normal','disable') ");
		}
		
		if (inputParams.get("isShow") != null && !"".equals(inputParams.get("isShow"))) {
			builder.append(" and b.is_show = '" + inputParams.get("isShow") + "' ");
		}
		
		if (inputParams.get("isRead") != null && !"".equals(inputParams.get("isRead"))) {
			builder.append(" and b.is_read = '" + inputParams.get("isRead") + "' ");
		}
		
		//名称
		if (inputParams.get("content") != null && !"".equals(inputParams.get("content"))) {
			builder.append(" and b.content like '%" + inputParams.get("content") + "%' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and b.starttime = " + inputParams.get("starttime") + " ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and b.endtime = " + inputParams.get("endtime") + " ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = " + inputParams.get("createtime") + " ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("b.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by b.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	@Override
	public Integer getCountForWeb(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_notice b left join qcb_notice_employee r on b.uuid=r.qcb_notice where 1=1 ");
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and b.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		//名称
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and b.title like '%" + inputParams.get("title") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('normal','disable') ");
		}
		
		
		if (inputParams.get("isShow") != null && !"".equals(inputParams.get("isShow"))) {
			builder.append(" and b.is_show = '" + inputParams.get("isShow") + "' ");
		}
		
		if (inputParams.get("isRead") != null && !"".equals(inputParams.get("isRead"))) {
			builder.append(" and b.is_read = '" + inputParams.get("isRead") + "' ");
		}
		
		//名称
		if (inputParams.get("content") != null && !"".equals(inputParams.get("content"))) {
			builder.append(" and b.content like '%" + inputParams.get("content") + "%' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and b.starttime = " + inputParams.get("starttime") + " ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and b.endtime = " + inputParams.get("endtime") + " ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = " + inputParams.get("createtime") + " ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public void updateBatchIsShow(List<String> list) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" update qcb_notice_employee b set b.is_show=1 where 1=1 ");
		builder.append(" and b.is_show=0 ");
		if(list != null && list.size() > 0){
			builder.append(" and b.qcb_employee in (");
			for(String uuid : list){
				builder.append("'"+uuid+"',");
			}
			builder.delete(builder.length() -1, builder.length());
			builder.append(")");
		}
		super.executeSQL(builder.toString());
	}

	@Override
	public void updateBatchIsRead(List<String> list) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" update qcb_notice_employee b set b.is_read=1 where 1=1 ");
		builder.append(" and b.is_read=0 ");
		if(list != null && list.size() > 0){
			builder.append(" and b.qcb_employee in (");
			for(String uuid : list){
				builder.append("'"+uuid+"',");
			}
			builder.delete(builder.length() -1, builder.length());
			builder.append(")");
		}
		super.executeSQL(builder.toString());
	}
}
