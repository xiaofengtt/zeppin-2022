/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsControllerMethodDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSsControllerMethodService;
import cn.zeppin.product.itic.core.entity.TSsControllerMethod;
import cn.zeppin.product.itic.core.service.base.BaseService;


@Service
public class TSsControllerMethodService extends BaseService implements ITSsControllerMethodService {

	@Autowired
	private ITSsControllerMethodDAO tSsControllerMethodDAO;
	
	@Override
	public TSsControllerMethod insert(TSsControllerMethod t) {
		return tSsControllerMethodDAO.insert(t);
	}

	@Override
	public TSsControllerMethod delete(TSsControllerMethod t) {
		return tSsControllerMethodDAO.delete(t);
	}

	@Override
	public TSsControllerMethod update(TSsControllerMethod t) {
		return tSsControllerMethodDAO.update(t);
	}

	@Override
	public TSsControllerMethod get(String uuid) {
		return tSsControllerMethodDAO.get(uuid);
	}

	@Override
	public List<TSsControllerMethod> getList(Map<String, String> inputParams) {
		return this.tSsControllerMethodDAO.getList(inputParams);
	}
}
