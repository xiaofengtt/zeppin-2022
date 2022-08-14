/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IAutoAliTransferProcessDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IAutoAliTransferProcessService;
import cn.zeppin.product.ntb.core.entity.AutoAliTransferProcess;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class AutoAliTransferProcessService extends BaseService implements IAutoAliTransferProcessService {

	@Autowired
	private IAutoAliTransferProcessDAO autoAliTransferProcessDao;

	
	@Override
	public AutoAliTransferProcess insert(AutoAliTransferProcess investorIdcardImg) {
		return autoAliTransferProcessDao.insert(investorIdcardImg);
	}

	@Override
	public AutoAliTransferProcess delete(AutoAliTransferProcess investorIdcardImg) {
//		investorIdcardImg.setStatus(InvestorIdcardImgStatus.DELETED);
		return autoAliTransferProcessDao.delete(investorIdcardImg);
	}

	@Override
	public AutoAliTransferProcess update(AutoAliTransferProcess investorIdcardImg) {
		return autoAliTransferProcessDao.update(investorIdcardImg);
	}

	@Override
	public AutoAliTransferProcess get(String uuid) {
		return autoAliTransferProcessDao.get(uuid);
	}
	
	/**
	 * 根据参数查询autoAliTransferProcess结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return autoAliTransferProcessDao.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return autoAliTransferProcessDao.getCount(inputParams);
	}

	@Override
	public List<Entity> getList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return autoAliTransferProcessDao.getList(inputParams, resultClass);
	}

}
