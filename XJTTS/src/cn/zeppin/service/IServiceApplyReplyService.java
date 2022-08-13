package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ServiceApplyReply;

public interface IServiceApplyReplyService extends
		IBaseService<ServiceApplyReply, Integer> {
	/**
	 * 根据参数，查询下级申请记录中对应的回复内容
	 * 
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	List<ServiceApplyReply> getReplyListByServiceApplyID(String serviceApplyId,
			int offset, int length);

	/**
	 * 添加数据
	 */
	public void addServiceApply(ServiceApplyReply serviceApplyReply);

	/**
	 * 修改数据 注：如果是删除则只修改状态“status”为“-1”
	 */
	public void updateServiceApply(ServiceApplyReply serviceApplyReply);
	
	List<ServiceApplyReply> getReplyListByServiceApplyID(Map<String, Object> params,
			int offset, int length);
	
	int getReplyCountByServiceApplyID(Map<String, Object> params);
}
