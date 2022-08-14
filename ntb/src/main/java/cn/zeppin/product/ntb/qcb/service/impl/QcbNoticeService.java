/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.core.entity.QcbNotice;
import cn.zeppin.product.ntb.core.entity.QcbNotice.QcbNoticeStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbNoticeDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbNoticeService;

/**
 * @author hehe
 *
 */
@Service
public class QcbNoticeService extends BaseService implements IQcbNoticeService {

	@Autowired
	private IQcbNoticeDAO qcbNoticeDAO;
	
	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Override
	public QcbNotice insert(QcbNotice qcbNotice) {
		return qcbNoticeDAO.insert(qcbNotice);
	}

	@Override
	public QcbNotice delete(QcbNotice qcbNotice) {
		qcbNotice.setStatus(QcbNoticeStatus.DELETED);
		return qcbNoticeDAO.update(qcbNotice);
	}

	@Override
	public QcbNotice update(QcbNotice qcbNotice) {
		return qcbNoticeDAO.update(qcbNotice);
	}

	@Override
	public QcbNotice get(String uuid) {
		return qcbNoticeDAO.get(uuid);
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
		return qcbNoticeDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbNoticeDAO.getCount(inputParams);
	}

	@Override
	public List<Entity> getListForWebPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return qcbNoticeDAO.getListForWebPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return qcbNoticeDAO.getStatusList(resultClass);
	}

}
