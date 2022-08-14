/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsOperatorProductDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorProductService;
import cn.zeppin.product.itic.core.entity.TSsOperatorProduct;
import cn.zeppin.product.itic.core.service.base.BaseService;

@Service
public class TSsOperatorProductService extends BaseService implements ITSsOperatorProductService {
	
	@Autowired
	private ITSsOperatorProductDAO  TSsOperatorProductDAO;
	
	@Override
	public TSsOperatorProduct get(String id) {
		return TSsOperatorProductDAO.get(id);
	}
	
	@Override
	public TSsOperatorProduct insert(TSsOperatorProduct t) {
		return TSsOperatorProductDAO.insert(t);
	}

	@Override
	public TSsOperatorProduct delete(TSsOperatorProduct t) {
		return TSsOperatorProductDAO.delete(t);
	}

	@Override
	public TSsOperatorProduct update(TSsOperatorProduct t) {
		return TSsOperatorProductDAO.update(t);
	}

	@Override
	public List<TSsOperatorProduct> getList(Map<String, String> inputParams) {
		return TSsOperatorProductDAO.getList(inputParams);
	}
}
