/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.core.entity.QcbNoticeEmployee;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbNoticeEmployeeDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbNoticeEmployeeService;

/**
 * @author hehe
 *
 */
@Service
public class QcbNoticeEmployeeService extends BaseService implements IQcbNoticeEmployeeService {

	@Autowired
	private IQcbNoticeEmployeeDAO qcbNoticeEmployeeDAO;
	
	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Override
	public QcbNoticeEmployee insert(QcbNoticeEmployee qcbNoticeEmployee) {
		return qcbNoticeEmployeeDAO.insert(qcbNoticeEmployee);
	}

	@Override
	public QcbNoticeEmployee delete(QcbNoticeEmployee qcbNoticeEmployee) {
		return qcbNoticeEmployeeDAO.delete(qcbNoticeEmployee);
	}

	@Override
	public QcbNoticeEmployee update(QcbNoticeEmployee qcbNoticeEmployee) {
		return qcbNoticeEmployeeDAO.update(qcbNoticeEmployee);
	}

	@Override
	public QcbNoticeEmployee get(String uuid) {
		return qcbNoticeEmployeeDAO.get(uuid);
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
		return qcbNoticeEmployeeDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbNoticeEmployeeDAO.getCount(inputParams);
	}

	@Override
	public List<Entity> getListForWebPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return qcbNoticeEmployeeDAO.getListForWebPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCountForWeb(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return qcbNoticeEmployeeDAO.getCountForWeb(inputParams);
	}

	@Override
	public void updateBatchIsShow(List<String> list) {
		// TODO Auto-generated method stub
		qcbNoticeEmployeeDAO.updateBatchIsShow(list);
	}

	@Override
	public void updateBatchIsRead(List<String> list) {
		// TODO Auto-generated method stub
		qcbNoticeEmployeeDAO.updateBatchIsRead(list);
	}

}
