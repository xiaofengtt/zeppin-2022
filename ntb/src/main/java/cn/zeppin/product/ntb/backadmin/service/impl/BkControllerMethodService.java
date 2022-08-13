/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkControllerMethodDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkControllerMethodService;
import cn.zeppin.product.ntb.core.entity.BkControllerMethod;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author elegantclack
 *
 */

@Service
public class BkControllerMethodService extends BaseService implements IBkControllerMethodService {

	@Autowired
	private IBkControllerMethodDAO bkControllerMethodDAO;
	

	@Override
	public BkControllerMethod get(String uuid) {
		return bkControllerMethodDAO.get(uuid);
	}

	/**
	 * 向表中插入一条ControllerMethod数据
	 * @param method
	 * @return
	 */
	@Override
	public BkControllerMethod insert(BkControllerMethod method) {
		return bkControllerMethodDAO.insert(method);
	}

	@Override
	public BkControllerMethod delete(BkControllerMethod method) {
		return bkControllerMethodDAO.delete(method);
	}

	@Override
	public BkControllerMethod update(BkControllerMethod method) {
		return bkControllerMethodDAO.update(method);
	}

	/**
	 * 获取所有功能信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return bkControllerMethodDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return bkControllerMethodDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return bkControllerMethodDAO.getCount(inputParams);
	}

}
