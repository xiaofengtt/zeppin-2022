/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkPaymentDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkPaymentService;
import cn.zeppin.product.ntb.core.entity.BkPayment;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class BkPaymentService extends BaseService implements IBkPaymentService {

	@Autowired
	private IBkPaymentDAO bkPaymentDAO;

	@Override
	public BkPayment insert(BkPayment bkPayment) {
		return bkPaymentDAO.insert(bkPayment);
	}

	@Override
	public BkPayment delete(BkPayment bkPayment) {
		return bkPaymentDAO.delete(bkPayment);
	}

	@Override
	public BkPayment update(BkPayment bkPayment) {
		return bkPaymentDAO.update(bkPayment);
	}

	@Override
	public BkPayment get(String uuid) {
		return bkPaymentDAO.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return bkPaymentDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return bkPaymentDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return bkPaymentDAO.getCount(inputParams);
	}

	@Override
	public boolean isExistBkPayment(String name, String uuid) {
		// TODO Auto-generated method stub
		return bkPaymentDAO.isExistBkPayment(name, uuid);
	}
}
