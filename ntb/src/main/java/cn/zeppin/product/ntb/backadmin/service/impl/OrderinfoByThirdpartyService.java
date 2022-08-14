/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IOrderinfoByThirdpartyDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class OrderinfoByThirdpartyService extends BaseService implements IOrderinfoByThirdpartyService {

	@Autowired
	private IOrderinfoByThirdpartyDAO orderinfoByThirdpartyDAO;
	
	@Override
	public OrderinfoByThirdparty insert(OrderinfoByThirdparty orderinfoByThirdparty) {
		return orderinfoByThirdpartyDAO.insert(orderinfoByThirdparty);
	}

	@Override
	public OrderinfoByThirdparty delete(OrderinfoByThirdparty orderinfoByThirdparty) {
		return orderinfoByThirdpartyDAO.delete(orderinfoByThirdparty);
	}

	@Override
	public OrderinfoByThirdparty update(OrderinfoByThirdparty orderinfoByThirdparty) {
		return orderinfoByThirdpartyDAO.update(orderinfoByThirdparty);
	}

	@Override
	public OrderinfoByThirdparty get(String uuid) {
		return orderinfoByThirdpartyDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询OrderinfoByThirdparty结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return orderinfoByThirdpartyDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return orderinfoByThirdpartyDAO.getCount(inputParams);
	}

	@Override
	public void insertBatch(List<OrderinfoByThirdparty> listOrder) {
		// TODO Auto-generated method stub
		for(OrderinfoByThirdparty obt : listOrder){
			this.orderinfoByThirdpartyDAO.insert(obt);
		}
	}
}
