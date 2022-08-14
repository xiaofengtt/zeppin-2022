/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IFundInvestDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IFundInvestService;
import cn.zeppin.product.ntb.core.entity.FundInvest;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class FundInvestService extends BaseService implements IFundInvestService {

	@Autowired
	private IFundInvestDAO fundInvestDAO;
	
	@Override
	public FundInvest insert(FundInvest fundInvest) {
		return fundInvestDAO.insert(fundInvest);
	}

	@Override
	public FundInvest delete(FundInvest fundInvest) {
		return fundInvestDAO.delete(fundInvest);
	}

	@Override
	public FundInvest update(FundInvest fundInvest) {
		return fundInvestDAO.update(fundInvest);
	}

	@Override
	public FundInvest get(String uuid) {
		return fundInvestDAO.get(uuid);
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
		return fundInvestDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return fundInvestDAO.getCount(inputParams);
	}
}