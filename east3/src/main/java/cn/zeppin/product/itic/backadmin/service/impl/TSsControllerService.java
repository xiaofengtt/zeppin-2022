/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsControllerDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSsControllerService;
import cn.zeppin.product.itic.core.entity.TSsController;
import cn.zeppin.product.itic.core.service.base.BaseService;


@Service
public class TSsControllerService extends BaseService implements ITSsControllerService {

	@Autowired
	private ITSsControllerDAO tSsControllerDAO;
	
	
	@Override
	public TSsController insert(TSsController t) {
		return tSsControllerDAO.insert(t);
	}

	@Override
	public TSsController delete(TSsController t) {
		return tSsControllerDAO.delete(t);
	}

	@Override
	public TSsController update(TSsController t) {
		return tSsControllerDAO.update(t);
	}

	@Override
	public TSsController get(String uuid) {
		return tSsControllerDAO.get(uuid);
	}

	@Override
	public List<TSsController> getAll() {
		return this.tSsControllerDAO.getAll();
	}
}
