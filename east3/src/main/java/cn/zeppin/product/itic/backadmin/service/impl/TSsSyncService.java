/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsSyncDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSsSyncService;
import cn.zeppin.product.itic.core.entity.TSsSync;
import cn.zeppin.product.itic.core.service.base.BaseService;

@Service
public class TSsSyncService extends BaseService implements ITSsSyncService {
	
	@Autowired
	private ITSsSyncDAO  TSsSyncDAO;
	
	@Override
	public TSsSync get(String id) {
		return TSsSyncDAO.get(id);
	}
	
	@Override
	public TSsSync insert(TSsSync t) {
		return TSsSyncDAO.insert(t);
	}

	@Override
	public TSsSync delete(TSsSync t) {
		return TSsSyncDAO.delete(t);
	}

	@Override
	public TSsSync update(TSsSync t) {
		return TSsSyncDAO.update(t);
	}

}
