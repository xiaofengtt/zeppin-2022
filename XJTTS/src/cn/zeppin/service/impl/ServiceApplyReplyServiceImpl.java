package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IServiceApplyReplyDao;
import cn.zeppin.entity.ServiceApplyReply;
import cn.zeppin.service.IServiceApplyReplyService;

public class ServiceApplyReplyServiceImpl extends
		BaseServiceImpl<ServiceApplyReply, Integer> implements
		IServiceApplyReplyService {
	private IServiceApplyReplyDao serviceApplyReplyDao;

	@Override
	public List<ServiceApplyReply> getReplyListByServiceApplyID(
			String serviceApplyId, int offset, int length) {
		return this.serviceApplyReplyDao.getReplyListByServiceApplyID(
				serviceApplyId, offset, length);
	}

	@Override
	public void addServiceApply(ServiceApplyReply serviceApplyReply) {
		this.serviceApplyReplyDao.add(serviceApplyReply);
	}

	@Override
	public void updateServiceApply(ServiceApplyReply serviceApplyReply) {
		this.serviceApplyReplyDao.update(serviceApplyReply);
	}

	public IServiceApplyReplyDao getServiceApplyReplyDao() {
		return serviceApplyReplyDao;
	}

	public void setServiceApplyReplyDao(
			IServiceApplyReplyDao serviceApplyReplyDao) {
		this.serviceApplyReplyDao = serviceApplyReplyDao;
	}

	@Override
	public List<ServiceApplyReply> getReplyListByServiceApplyID(
			Map<String, Object> params, int offset, int length) {
		// TODO Auto-generated method stub
		return this.serviceApplyReplyDao.getReplyListByServiceApplyID(params, offset, length);
	}

	@Override
	public int getReplyCountByServiceApplyID(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.serviceApplyReplyDao.getReplyCountByServiceApplyID(params);
	}

}
