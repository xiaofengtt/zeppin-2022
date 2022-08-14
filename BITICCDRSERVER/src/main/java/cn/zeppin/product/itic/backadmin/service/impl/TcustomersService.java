/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITcustomersDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITcustomersService;
import cn.zeppin.product.itic.core.entity.Tcustomers;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.service.base.BaseService;

/**
 * @author L
 *
 */
@Service
public class TcustomersService extends BaseService implements ITcustomersService {

	@Autowired
	private ITcustomersDAO  tcustomersDAO;

	
	@Override
	public Tcustomers get(Integer uuid) {
		return tcustomersDAO.get(uuid);
	}
	
	/**
	 * 向表中插入一条Controller数据
	 */
	@Override
	public Tcustomers insert(Tcustomers area) {
		return tcustomersDAO.insert(area);
	}

	@Override
	public Tcustomers delete(Tcustomers area) {
		return tcustomersDAO.delete(area);
	}

	@Override
	public Tcustomers update(Tcustomers area) {
		return tcustomersDAO.update(area);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return tcustomersDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return tcustomersDAO.getCount(inputParams);
	}

	@Override
	public List<Tcustomers> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		// TODO Auto-generated method stub
		return tcustomersDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}
}
