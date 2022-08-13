package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ServiceApplyReply;

public interface IServiceApplyReplyDao extends IBaseDao<ServiceApplyReply, Integer> {

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
	
	List<ServiceApplyReply> getReplyListByServiceApplyID(Map<String, Object> params,
			int offset, int length);
	
	int getReplyCountByServiceApplyID(Map<String, Object> params);
}
