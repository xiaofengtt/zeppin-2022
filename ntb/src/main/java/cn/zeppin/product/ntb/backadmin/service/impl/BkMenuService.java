/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkMenuDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkMenuService;
import cn.zeppin.product.ntb.core.entity.BkMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class BkMenuService extends BaseService implements IBkMenuService {

	@Autowired
	private IBkMenuDAO bkMenuDAO;

	@Override
	public BkMenu insert(BkMenu bkMenu) {
		return bkMenuDAO.insert(bkMenu);
	}

	@Override
	public BkMenu delete(BkMenu bkMenu) {
		return bkMenuDAO.delete(bkMenu);
	}

	@Override
	public BkMenu update(BkMenu bkMenu) {
		return bkMenuDAO.update(bkMenu);
	}

	@Override
	public BkMenu get(String uuid) {
		return bkMenuDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bkMenuDAO.getList(inputParams, resultClass);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return bkMenuDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return bkMenuDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return bkMenuDAO.getCount(inputParams);
	}
}
