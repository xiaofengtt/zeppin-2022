/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkControllerDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkControllerService;
import cn.zeppin.product.ntb.core.entity.BkController;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author elegantclack
 *
 */
@Service
public class BkControllerService extends BaseService implements IBkControllerService {

	@Autowired
	private IBkControllerDAO  bkControllerDAO;

	
	@Override
	public BkController get(String uuid) {
		return bkControllerDAO.get(uuid);
	}
	
	/**
	 * 向表中插入一条Controller数据
	 */
	@Override
	public BkController insert(BkController controller) {
		return bkControllerDAO.insert(controller);
	}

	@Override
	public BkController delete(BkController controller) {
		return bkControllerDAO.delete(controller);
	}

	@Override
	public BkController update(BkController controller) {
		return bkControllerDAO.update(controller);
	}

	/**
	 * 获取所有功能信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return bkControllerDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return bkControllerDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return bkControllerDAO.getCount(inputParams);
	}
}
