/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsMenuDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSsMenuService;
import cn.zeppin.product.itic.core.entity.TSsMenu;
import cn.zeppin.product.itic.core.service.base.BaseService;

/**
 *
 */
@Service
public class TSsMenuService extends BaseService implements ITSsMenuService {

	@Autowired
	private ITSsMenuDAO tSsMenuDAO;

	@Override
	public TSsMenu insert(TSsMenu tSsMenu) {
		return tSsMenuDAO.insert(tSsMenu);
	}

	@Override
	public TSsMenu delete(TSsMenu tSsMenu) {
		return tSsMenuDAO.delete(tSsMenu);
	}

	@Override
	public TSsMenu update(TSsMenu tSsMenu) {
		return tSsMenuDAO.update(tSsMenu);
	}

	@Override
	public TSsMenu get(String uuid) {
		return tSsMenuDAO.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<TSsMenu> getAll() {
		return tSsMenuDAO.getAll();
	}

	@Override
	public List<TSsMenu> getListForOperator(Map<String, String> inputParams) {
		return tSsMenuDAO.getListForOperator(inputParams);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return tSsMenuDAO.getCount(inputParams);
	}

	@Override
	public List<TSsMenu> getList(Map<String, String> inputParams) {
		return tSsMenuDAO.getList(inputParams);
	}
}
