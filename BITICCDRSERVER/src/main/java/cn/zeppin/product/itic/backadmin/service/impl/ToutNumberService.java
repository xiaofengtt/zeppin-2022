/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.IToutNumberDAO;
import cn.zeppin.product.itic.backadmin.service.api.IToutNumberService;
import cn.zeppin.product.itic.core.entity.ToutNumber;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.service.base.BaseService;

/**
 * @author L
 *
 */
@Service
public class ToutNumberService extends BaseService implements IToutNumberService {

	@Autowired
	private IToutNumberDAO  toutNumberDAO;
	
	
	@Override
	public ToutNumber get(Integer uuid) {
		return toutNumberDAO.get(uuid);
	}
	
	/**
	 * 向表中插入一条Controller数据
	 */
	@Override
	public ToutNumber insert(ToutNumber area) {
		return toutNumberDAO.insert(area);
	}

	@Override
	public ToutNumber delete(ToutNumber area) {
		return toutNumberDAO.delete(area);
	}

	@Override
	public ToutNumber update(ToutNumber area) {
		return toutNumberDAO.update(area);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return toutNumberDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return toutNumberDAO.getCount(inputParams);
	}

	@Override
	public List<ToutNumber> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		// TODO Auto-generated method stub
		return toutNumberDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}

}
