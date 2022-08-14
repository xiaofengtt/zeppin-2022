/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITAreaDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITAreaService;
import cn.zeppin.product.itic.core.entity.TArea;
import cn.zeppin.product.itic.core.service.base.BaseService;

@Service
public class TAreaService extends BaseService implements ITAreaService {
	
	@Autowired
	private ITAreaDAO  TAreaDAO;
	
	@Override
	public TArea get(String id) {
		return TAreaDAO.get(id);
	}
	
	@Override
	public TArea insert(TArea t) {
		return TAreaDAO.insert(t);
	}

	@Override
	public TArea delete(TArea t) {
		return TAreaDAO.delete(t);
	}

	@Override
	public TArea update(TArea t) {
		return TAreaDAO.update(t);
	}

	@Override
	public List<TArea> getList(Map<String, String> inputParams) {
		return TAreaDAO.getList(inputParams);
	}

}
