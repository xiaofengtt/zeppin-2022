/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IFundRateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IFundRateService;
import cn.zeppin.product.ntb.core.entity.FundRate;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class FundRateService extends BaseService implements IFundRateService {

	@Autowired
	private IFundRateDAO fundRateDAO;
	
	@Override
	public FundRate insert(FundRate fundRate) {
		return fundRateDAO.insert(fundRate);
	}

	@Override
	public FundRate delete(FundRate fundRate) {
		return fundRateDAO.delete(fundRate);
	}

	@Override
	public FundRate update(FundRate fundRate) {
		return fundRateDAO.update(fundRate);
	}

	@Override
	public FundRate get(String uuid) {
		return fundRateDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询FundRate结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return fundRateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}
}
