/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeCouponHistoryDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeCouponHistoryService;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCouponHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class QcbEmployeeCouponHistoryService extends BaseService implements IQcbEmployeeCouponHistoryService {

	@Autowired
	private IQcbEmployeeCouponHistoryDAO qcbEmployeeCouponHistoryDAO;

	@Override
	public QcbEmployeeCouponHistory insert(QcbEmployeeCouponHistory qcbEmployeeCouponHistory) {
		return qcbEmployeeCouponHistoryDAO.insert(qcbEmployeeCouponHistory);
	}

	@Override
	public QcbEmployeeCouponHistory delete(QcbEmployeeCouponHistory qcbEmployeeCouponHistory) {
		return qcbEmployeeCouponHistoryDAO.delete(qcbEmployeeCouponHistory);
	}

	@Override
	public QcbEmployeeCouponHistory update(QcbEmployeeCouponHistory qcbEmployeeCouponHistory) {
		return qcbEmployeeCouponHistoryDAO.update(qcbEmployeeCouponHistory);
	}

	@Override
	public QcbEmployeeCouponHistory get(String uuid) {
		return qcbEmployeeCouponHistoryDAO.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return qcbEmployeeCouponHistoryDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		return qcbEmployeeCouponHistoryDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbEmployeeCouponHistoryDAO.getCount(inputParams);
	}
}
