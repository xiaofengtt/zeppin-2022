/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IRealpalNoticeInfoDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.RealpalNoticeInfo;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class RealpalNoticeInfoDAO extends BaseDAO<RealpalNoticeInfo, String> implements IRealpalNoticeInfoDAO {
	/**
	 * 向数据库添加一条RealpalNoticeInfo数据
	 * 向缓存realpalNoticeInfos添加一条RealpalNoticeInfo记录，key值为uuid
	 * @param realpalNoticeInfo
	 * @return realpalNoticeInfo
	 */
	@Override
//	@Caching(put={@CachePut(value = "realpalNoticeInfos", key = "'realpalNoticeInfos:' + #realpalNoticeInfo.uuid")})
	public RealpalNoticeInfo insert(RealpalNoticeInfo realpalNoticeInfo) {
		return super.insert(realpalNoticeInfo);
	}

	/**
	 * 向数据库删除一条RealpalNoticeInfo数据
	 * 在缓存realpalNoticeInfos中删除一条RealpalNoticeInfo记录，key值为uuid
	 * @param realpalNoticeInfo
	 * @return realpalNoticeInfo
	 */
	@Override
//	@Caching(evict={@CacheEvict(value = "realpalNoticeInfos", key = "'realpalNoticeInfos:' + #realpalNoticeInfo.uuid")})
	public RealpalNoticeInfo delete(RealpalNoticeInfo realpalNoticeInfo) {
		return super.delete(realpalNoticeInfo);
	}

	/**
	 * 向数据库更新一条RealpalNoticeInfo数据
	 * 在缓存realpalNoticeInfos中更新一条RealpalNoticeInfo记录，key值为uuid
	 * @param realpalNoticeInfo
	 * @return realpalNoticeInfo
	 */
	@Override
//	@Caching(put={@CachePut(value = "realpalNoticeInfos", key = "'realpalNoticeInfos:' + #realpalNoticeInfo.uuid")})
	public RealpalNoticeInfo update(RealpalNoticeInfo realpalNoticeInfo) {
		return super.update(realpalNoticeInfo);
	}

	/**
	 * 根据uuid得到一个RealpalNoticeInfo信息
	 * 向缓存realpalNoticeInfos添加一条RealpalNoticeInfo记录，key值为uuid
	 * @param uuid
	 * @return realpalNoticeInfo
	 */
	@Override
//	@Cacheable(value = "realpalNoticeInfos", key = "'realpalNoticeInfos:' + #uuid")
	public RealpalNoticeInfo get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select b.* from realpal_notice_info b order by b.createtime", resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.createtime, b.order_num as orderNum, b.batch_no as batchNo, b.status, b.source, b.pay_type as payType "
				+ "from realpal_notice_info b where 1=1 ");
		//名称
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and b.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		
		if (inputParams.get("orderNum") != null && !"".equals(inputParams.get("orderNum"))) {
			builder.append(" and b.order_num = " + inputParams.get("orderNum"));
		}
		
		if (inputParams.get("batchNo") != null && !"".equals(inputParams.get("batchNo"))) {
			builder.append(" and b.batch_no = " + inputParams.get("batchNo"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("payType") != null && !"".equals(inputParams.get("payType"))) {
			builder.append(" and b.pay_type = '" + inputParams.get("payType") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and b.status = 'success' ");
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
			builder.append(" order by b.createtime desc");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from realpal_notice_info b where 1=1 ");
		//名称
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and b.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		
		if (inputParams.get("orderNum") != null && !"".equals(inputParams.get("orderNum"))) {
			builder.append(" and b.order_num = " + inputParams.get("orderNum"));
		}
		
		if (inputParams.get("batchNo") != null && !"".equals(inputParams.get("batchNo"))) {
			builder.append(" and b.batch_no = " + inputParams.get("batchNo"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("payType") != null && !"".equals(inputParams.get("payType"))) {
			builder.append(" and b.pay_type = '" + inputParams.get("payType") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and b.status = 'success' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public boolean isExistRealpalNoticeInfo(String name, String uuid) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from realpal_notice_info b where order_num=?0 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {name,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
}
