/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard;
import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard.QcbCompanyBankcardStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyBankcardDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyBankcardService;

/**
 * @author hehe
 *
 */
@Service
public class QcbCompanyBankcardService extends BaseService implements IQcbCompanyBankcardService {

	@Autowired
	private IQcbCompanyBankcardDAO qcbCompanyBankcardDAO;
	
	@Override
	public QcbCompanyBankcard insert(QcbCompanyBankcard qcbCompanyBankcard) {
		return qcbCompanyBankcardDAO.insert(qcbCompanyBankcard);
	}

	@Override
	public QcbCompanyBankcard delete(QcbCompanyBankcard qcbCompanyBankcard) {
		qcbCompanyBankcard.setStatus(QcbCompanyBankcardStatus.DELETED);
		qcbCompanyBankcard.setBindingBankCard(qcbCompanyBankcard.getBindingBankCard() + "_#" + qcbCompanyBankcard.getUuid());
		return qcbCompanyBankcardDAO.update(qcbCompanyBankcard);
	}

	@Override
	public QcbCompanyBankcard update(QcbCompanyBankcard qcbCompanyBankcard) {
		return qcbCompanyBankcardDAO.update(qcbCompanyBankcard);
	}

	@Override
	public QcbCompanyBankcard get(String uuid) {
		return qcbCompanyBankcardDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return qcbCompanyBankcardDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyBankcardDAO.getCount(inputParams);
	}
}
