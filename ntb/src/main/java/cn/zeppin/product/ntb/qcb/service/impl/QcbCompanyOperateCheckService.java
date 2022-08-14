/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyOperateCheckDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyOperateCheckService;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperateCheck;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class QcbCompanyOperateCheckService extends BaseService implements IQcbCompanyOperateCheckService {

	@Autowired
	private IQcbCompanyOperateCheckDAO qcbCompanyOperateCheckDAO;
	
	@Override
	public QcbCompanyOperateCheck insert(QcbCompanyOperateCheck qcbCompanyOperateCheck) {
		return qcbCompanyOperateCheckDAO.insert(qcbCompanyOperateCheck);
	}

	@Override
	public QcbCompanyOperateCheck delete(QcbCompanyOperateCheck qcbCompanyOperateCheck) {
		return qcbCompanyOperateCheckDAO.delete(qcbCompanyOperateCheck);
	}

	@Override
	public QcbCompanyOperateCheck update(QcbCompanyOperateCheck qcbCompanyOperateCheck) {
		return qcbCompanyOperateCheckDAO.update(qcbCompanyOperateCheck);
	}

	@Override
	public QcbCompanyOperateCheck get(String uuid) {
		return qcbCompanyOperateCheckDAO.get(uuid);
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
		return qcbCompanyOperateCheckDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyOperateCheckDAO.getCount(inputParams);
	}
}
