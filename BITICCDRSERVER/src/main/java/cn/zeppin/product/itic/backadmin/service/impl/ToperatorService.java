/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.IToperatorDAO;
import cn.zeppin.product.itic.backadmin.service.api.IToperatorService;
import cn.zeppin.product.itic.core.entity.Toperator;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.service.base.BaseService;

/**
 * @author L
 *
 */
@Service
public class ToperatorService extends BaseService implements IToperatorService {

	@Autowired
	private IToperatorDAO  toperatorDAO;

	
	@Override
	public Toperator get(Integer uuid) {
		return toperatorDAO.get(uuid);
	}
	
	/**
	 * 向表中插入一条Controller数据
	 */
	@Override
	public Toperator insert(Toperator area) {
		return toperatorDAO.insert(area);
	}

	@Override
	public Toperator delete(Toperator area) {
		return toperatorDAO.delete(area);
	}

	@Override
	public Toperator update(Toperator area) {
		return toperatorDAO.update(area);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return toperatorDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return toperatorDAO.getCount(inputParams);
	}

	@Override
	public List<Toperator> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		// TODO Auto-generated method stub
		return toperatorDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}

	@Override
	public List<Entity> getListForNumPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return toperatorDAO.getListForNumPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public List<Entity> getListForSearchPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return toperatorDAO.getListForSearchPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCountForSearchPage(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return toperatorDAO.getCountForSearchPage(inputParams);
	}
}
