package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IServiceApplyDao;
import cn.zeppin.entity.ServiceApply;
import cn.zeppin.service.IServiceApplyService;

public class ServiceApplyServiceImpl extends
		BaseServiceImpl<ServiceApply, Integer> implements IServiceApplyService {
	private IServiceApplyDao serviceApplyDao;

	/**
	 * 根据条件，查询所有对应记录总数
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public int getCountByParams(Map<String, Object> params) {
		return serviceApplyDao.getCountByParams(params);
	}

	/**
	 * 根据条件，查询所有对应的记录List
	 * 
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	@Override
	public List<ServiceApply> getListByParams(Map<String, Object> params,
			int offset, int length) {
		return serviceApplyDao.getListByParams(params, offset, length);
	}

	public IServiceApplyDao getServiceApplyDao() {
		return serviceApplyDao;
	}

	public void setServiceApplyDao(IServiceApplyDao serviceApplyDao) {
		this.serviceApplyDao = serviceApplyDao;
	}

	@Override
	public void addServiceApply(ServiceApply serviceApply) {
		this.serviceApplyDao.add(serviceApply);
	}

	@Override
	public void updateServiceApply(ServiceApply serviceApply) {
		this.serviceApplyDao.update(serviceApply);
	}

	@Override
	public ServiceApply get(Integer id) {
		return this.serviceApplyDao.get(id);
	}
}
