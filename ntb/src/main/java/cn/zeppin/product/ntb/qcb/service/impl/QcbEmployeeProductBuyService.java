/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeProductBuyDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyService;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 *
 */
@Service
public class QcbEmployeeProductBuyService extends BaseService implements IQcbEmployeeProductBuyService {

	@Autowired
	private IQcbEmployeeProductBuyDAO qcbEmployeeProductBuyDAO;
	
	@Override
	public QcbEmployeeProductBuy insert(QcbEmployeeProductBuy qcbEmployeeProductBuy) {
		return qcbEmployeeProductBuyDAO.insert(qcbEmployeeProductBuy);
	}

	@Override
	public QcbEmployeeProductBuy delete(QcbEmployeeProductBuy qcbEmployeeProductBuy) {
		return qcbEmployeeProductBuyDAO.delete(qcbEmployeeProductBuy);
	}

	@Override
	public QcbEmployeeProductBuy update(QcbEmployeeProductBuy qcbEmployeeProductBuy) {
		return qcbEmployeeProductBuyDAO.update(qcbEmployeeProductBuy);
	}

	@Override
	public QcbEmployeeProductBuy get(String uuid) {
		return qcbEmployeeProductBuyDAO.get(uuid);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbEmployeeProductBuyDAO.getCount(inputParams);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return qcbEmployeeProductBuyDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public void updateBatch(List<QcbEmployeeProductBuy> listUpdate, List<QcbEmployeeProductBuy> listInsert) {
		if(!listUpdate.isEmpty()){
			for(QcbEmployeeProductBuy ipb : listUpdate){
				this.qcbEmployeeProductBuyDAO.update(ipb);
			}
		}
		
		if(!listInsert.isEmpty()){
			for(QcbEmployeeProductBuy ipb : listInsert){
				this.qcbEmployeeProductBuyDAO.insert(ipb);
			}
		}
	}

	@Override
	public void updateStage() {
		this.qcbEmployeeProductBuyDAO.updateConfirmStage();
		this.qcbEmployeeProductBuyDAO.updatePofitStage();
		this.qcbEmployeeProductBuyDAO.updateBalanceStage();
	}

	@Override
	public List<Entity> getListForCountPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return this.qcbEmployeeProductBuyDAO.getListForCountPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}
}
