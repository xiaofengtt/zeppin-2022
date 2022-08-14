/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeCouponDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeCouponService;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCoupon;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class QcbEmployeeCouponService extends BaseService implements IQcbEmployeeCouponService {

	@Autowired
	private IQcbEmployeeCouponDAO qcbEmployeeCouponDAO;

	@Override
	public QcbEmployeeCoupon insert(QcbEmployeeCoupon qcbEmployeeCoupon) {
		return qcbEmployeeCouponDAO.insert(qcbEmployeeCoupon);
	}

	@Override
	public QcbEmployeeCoupon delete(QcbEmployeeCoupon qcbEmployeeCoupon) {
		return qcbEmployeeCouponDAO.delete(qcbEmployeeCoupon);
	}

	@Override
	public QcbEmployeeCoupon update(QcbEmployeeCoupon qcbEmployeeCoupon) {
		return qcbEmployeeCouponDAO.update(qcbEmployeeCoupon);
	}

	@Override
	public QcbEmployeeCoupon get(String uuid) {
		return qcbEmployeeCouponDAO.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return qcbEmployeeCouponDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		return qcbEmployeeCouponDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbEmployeeCouponDAO.getCount(inputParams);
	}

	@Override
	public void insertBatch(List<QcbEmployeeCoupon> qecList) {
		for(QcbEmployeeCoupon qec : qecList){
			this.qcbEmployeeCouponDAO.insert(qec);
		}
	}

	@Override
	public void updateExpiryDate() {
		this.qcbEmployeeCouponDAO.updateExpiryDate();
	}
}
