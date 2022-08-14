/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsFileDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSsFileService;
import cn.zeppin.product.itic.core.entity.TSsFile;
import cn.zeppin.product.itic.core.service.base.BaseService;

@Service
public class TSsFileService extends BaseService implements ITSsFileService {
	
	@Autowired
	private ITSsFileDAO  TSsFileDAO;
	
	@Override
	public TSsFile get(String id) {
		return TSsFileDAO.get(id);
	}
	
	@Override
	public TSsFile insert(TSsFile t) {
		return TSsFileDAO.insert(t);
	}

	@Override
	public TSsFile delete(TSsFile t) {
		return TSsFileDAO.delete(t);
	}

	@Override
	public TSsFile update(TSsFile t) {
		return TSsFileDAO.update(t);
	}

}
