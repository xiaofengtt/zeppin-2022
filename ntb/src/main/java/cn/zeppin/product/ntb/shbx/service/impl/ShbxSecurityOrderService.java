/**
 * 
 */
package cn.zeppin.product.ntb.shbx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxSecurityOrderDAO;
import cn.zeppin.product.ntb.shbx.service.api.IShbxSecurityOrderService;

/**
 * @author hehe
 *
 */
@Service
public class ShbxSecurityOrderService extends BaseService implements IShbxSecurityOrderService {

	@Autowired
	private IShbxSecurityOrderDAO shbxSecurityOrderDAO;
	
	@Override
	public ShbxSecurityOrder insert(ShbxSecurityOrder shbxSecurityOrder) {
		return shbxSecurityOrderDAO.insert(shbxSecurityOrder);
	}

	@Override
	public ShbxSecurityOrder delete(ShbxSecurityOrder shbxSecurityOrder) {
		return shbxSecurityOrderDAO.delete(shbxSecurityOrder);
	}

	@Override
	public ShbxSecurityOrder update(ShbxSecurityOrder shbxSecurityOrder) {
		return shbxSecurityOrderDAO.update(shbxSecurityOrder);
	}

	@Override
	public ShbxSecurityOrder get(String uuid) {
		return shbxSecurityOrderDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询ShbxSecurityOrder结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return shbxSecurityOrderDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return shbxSecurityOrderDAO.getCount(inputParams);
	}

	@Override
	public void insertBatch(List<ShbxSecurityOrder> listOrder) {
		for(ShbxSecurityOrder obt : listOrder){
			this.shbxSecurityOrderDAO.insert(obt);
		}
	}

	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.shbxSecurityOrderDAO.getStatusList(resultClass);
	}

	@Override
	public void updateBatch(List<ShbxSecurityOrder> listUpdate) {
		// TODO Auto-generated method stub
		for(ShbxSecurityOrder sso : listUpdate){
			this.shbxSecurityOrderDAO.update(sso);
		}
	}
}
