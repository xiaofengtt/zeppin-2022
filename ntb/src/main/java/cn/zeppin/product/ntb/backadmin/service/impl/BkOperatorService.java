/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkOperatorDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BkOperator.BkOperatorStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author Clark.R 2016年9月21日
 *
 */
@Service
public class BkOperatorService extends BaseService implements IBkOperatorService {
	
	@Autowired
	private IBkOperatorDAO bkOperatorDAO;
	
	
	@Override
	public BkOperator insert(BkOperator operator) {
		return bkOperatorDAO.insert(operator);
	}

	@Override
	public BkOperator delete(BkOperator operator) {
		operator.setStatus(BkOperatorStatus.DELETED);
		operator.setName(operator.getName() + "_#" + operator.getUuid());
		operator.setMobile(operator.getMobile() + "_#" + operator.getUuid());
		return bkOperatorDAO.update(operator);
	}

	@Override
	public BkOperator update(BkOperator operator) {
		return bkOperatorDAO.update(operator);
	}

	@Override
	public BkOperator get(String uuid) {
		return bkOperatorDAO.get(uuid);
	}
	
	/**
	 * 通过账号得到BkOperator对象
	 * @param loginname
	 * @return BkOperator
	 */
	@Override
	public BkOperator getByLoginname(String loginname) {
		return bkOperatorDAO.getByLoginname(loginname);
	}

	/**
	 * 根据参数查询Bank结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return bkOperatorDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bkOperatorDAO.getCount(inputParams);
	}
	
	/**
	 * 验证同名的管理员是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistOperatorByName(String name, String uuid) {
		return bkOperatorDAO.isExistOperatorByName(name, uuid);
	}
	
	/**
	 * 验证同手机号的管理员是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistOperatorByMobile(String mobile, String uuid) {
		return bkOperatorDAO.isExistOperatorByMobile(mobile, uuid);
	}
}
