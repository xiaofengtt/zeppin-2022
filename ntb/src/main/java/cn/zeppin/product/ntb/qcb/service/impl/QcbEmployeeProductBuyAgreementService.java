/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeProductBuyAgreementDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyAgreementService;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class QcbEmployeeProductBuyAgreementService extends BaseService implements IQcbEmployeeProductBuyAgreementService {

	@Autowired
	private IQcbEmployeeProductBuyAgreementDAO qcbEmployeeProductBuyAgreementDAO;
	
	@Override
	public QcbEmployeeProductBuyAgreement insert(QcbEmployeeProductBuyAgreement qcbEmployeeProductBuyAgreement) {
		return qcbEmployeeProductBuyAgreementDAO.insert(qcbEmployeeProductBuyAgreement);
	}

	@Override
	public QcbEmployeeProductBuyAgreement delete(QcbEmployeeProductBuyAgreement qcbEmployeeProductBuyAgreement) {
		return qcbEmployeeProductBuyAgreementDAO.delete(qcbEmployeeProductBuyAgreement);
	}

	@Override
	public QcbEmployeeProductBuyAgreement update(QcbEmployeeProductBuyAgreement qcbEmployeeProductBuyAgreement) {
		return qcbEmployeeProductBuyAgreementDAO.update(qcbEmployeeProductBuyAgreement);
	}

	@Override
	public QcbEmployeeProductBuyAgreement get(String uuid) {
		return qcbEmployeeProductBuyAgreementDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询qcbEmployeeProductBuyAgreement结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return qcbEmployeeProductBuyAgreementDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbEmployeeProductBuyAgreementDAO.getCount(inputParams);
	}

	@Override
	public Boolean getCheckScode(String scode) {
		return qcbEmployeeProductBuyAgreementDAO.getCheckScode(scode);
	}

	@Override
	public void updateBatch(List<QcbEmployeeProductBuyAgreement> listUpdate,
			List<QcbEmployeeProductBuyAgreement> listInsert) {
		if(!listUpdate.isEmpty()){
			for(QcbEmployeeProductBuyAgreement qepba : listUpdate){
				this.qcbEmployeeProductBuyAgreementDAO.update(qepba);
			}
		}
		
		if(!listInsert.isEmpty()){
			for(QcbEmployeeProductBuyAgreement qepba : listInsert){
				this.qcbEmployeeProductBuyAgreementDAO.insert(qepba);
			}
		}
	}
}
