/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITinNumberDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITinNumberService;
import cn.zeppin.product.itic.core.entity.TinNumber;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.service.base.BaseService;

/**
 * @author L
 *
 */
@Service
public class TinNumberService extends BaseService implements ITinNumberService {

	@Autowired
	private ITinNumberDAO  tinNumberDAO;
	
	
	@Override
	public TinNumber get(Integer uuid) {
		return tinNumberDAO.get(uuid);
	}
	
	/**
	 * 向表中插入一条Controller数据
	 */
	@Override
	public TinNumber insert(TinNumber area) {
		return tinNumberDAO.insert(area);
	}

	@Override
	public TinNumber delete(TinNumber area) {
		return tinNumberDAO.delete(area);
	}

	@Override
	public TinNumber update(TinNumber area) {
		return tinNumberDAO.update(area);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return tinNumberDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return tinNumberDAO.getCount(inputParams);
	}

	@Override
	public List<TinNumber> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		// TODO Auto-generated method stub
		return tinNumberDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}

}
