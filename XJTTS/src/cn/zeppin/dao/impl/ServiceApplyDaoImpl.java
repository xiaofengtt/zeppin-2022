package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IServiceApplyDao;
import cn.zeppin.dao.IServiceApplyReplyDao;
import cn.zeppin.entity.ServiceApply;
import cn.zeppin.entity.ServiceApplyReply;

/**
 * 需要处理的下级申请回复
 */
public class ServiceApplyDaoImpl extends
		BaseDaoImpl<ServiceApply, Integer> implements IServiceApplyDao {

	/**
	 * 根据条件，查询所有对应记录总数
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public int getCountByParams(Map<String, Object> params) {
		StringBuilder builder = new StringBuilder();
		builder.append("select count(*) from ServiceApply sa where 1=1");
		builder.append(addSqlWhere(params));
		Object result = this.getObjectByHql(builder.toString(), null);
		return Integer.parseInt(result.toString());
	}

	/**
	 * 根据条件，查询所有对应的记录List
	 * 
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<ServiceApply> getListByParams(Map<String, Object> params,
			int offset, int length) {
		StringBuilder builder = new StringBuilder();
		builder.append("from ServiceApply sa where 1=1 ");
		builder.append(addSqlWhere(params));
		builder.append("order by sa.createtime desc");
		return this.getListForPage(builder.toString(), offset, length);
	}

	/**
	 * 附加查询条件
	 * 
	 * @param params
	 *            条件
	 * @param builder
	 *            拼接字符串
	 * @return List
	 */
	private String addSqlWhere(Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			StringBuilder builder = new StringBuilder();
			if (params.get("id") != null) {
				builder.append(" and sa.id = " + params.get("id"));
			}
			if (params.get("countent") != null) {
				builder.append(" and sa.countent = " + params.get("countent"));
			}
			if (params.get("replyText") != null) {
				builder.append(" and sa.replyText = " + params.get("replyText"));
			}
			if (params.get("creator") != null) {
				builder.append(" and sa.creator = " + params.get("creator"));
			}
			if (params.get("cretorType") != null) {
				builder.append(" and sa.cretorType = "
						+ params.get("cretorType"));
			}
			if (params.get("createtime") != null) {
				builder.append(" and sa.createtime = "
						+ params.get("createtime"));
			}
			if (params.get("checker") != null) {
				builder.append(" and sa.checker = " + params.get("checker"));
			}
			if (params.get("checktime") != null) {
				builder.append(" and sa.checktime = " + params.get("checktime"));
			}
			if (params.get("status") != null
					&& !params.get("status").equals("")) {// 处理状态0-未回复 1-已回复
															// -1已删除
				builder.append(" and sa.status = " + params.get("status"));
			} else {
				builder.append(" and sa.status <> -1 ");
			}
			if (params.get("type") != null && !params.get("type").equals("-1")
					&& !params.get("type").equals("")) {// 申请类型 1-添加承训单位申请
														// 2-添加培训学科申请
				// 3-其他申请
				builder.append(" and sa.type = " + params.get("type"));
			}
			return builder.toString();
		} else {
			return " and sa.status <> -1 ";
		}
	}
}
