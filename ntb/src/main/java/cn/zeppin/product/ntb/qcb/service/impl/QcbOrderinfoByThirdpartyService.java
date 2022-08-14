/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbOrderinfoByThirdpartyDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class QcbOrderinfoByThirdpartyService extends BaseService implements IQcbOrderinfoByThirdpartyService {

	@Autowired
	private IQcbOrderinfoByThirdpartyDAO qcbOrderinfoByThirdpartyDAO;
	
	@Override
	public QcbOrderinfoByThirdparty insert(QcbOrderinfoByThirdparty qcbOrderinfoByThirdparty) {
		return qcbOrderinfoByThirdpartyDAO.insert(qcbOrderinfoByThirdparty);
	}

	@Override
	public QcbOrderinfoByThirdparty delete(QcbOrderinfoByThirdparty qcbOrderinfoByThirdparty) {
		return qcbOrderinfoByThirdpartyDAO.delete(qcbOrderinfoByThirdparty);
	}

	@Override
	public QcbOrderinfoByThirdparty update(QcbOrderinfoByThirdparty qcbOrderinfoByThirdparty) {
		return qcbOrderinfoByThirdpartyDAO.update(qcbOrderinfoByThirdparty);
	}

	@Override
	public QcbOrderinfoByThirdparty get(String uuid) {
		return qcbOrderinfoByThirdpartyDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询QcbOrderinfoByThirdparty结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return qcbOrderinfoByThirdpartyDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbOrderinfoByThirdpartyDAO.getCount(inputParams);
	}

	@Override
	public void insertBatch(List<QcbOrderinfoByThirdparty> listOrder) {
		for(QcbOrderinfoByThirdparty obt : listOrder){
			this.qcbOrderinfoByThirdpartyDAO.insert(obt);
		}
	}
}
