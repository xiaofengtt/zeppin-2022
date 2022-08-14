/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorCouponHistoryDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorCouponHistoryService;
import cn.zeppin.product.ntb.core.entity.InvestorCouponHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class InvestorCouponHistoryService extends BaseService implements IInvestorCouponHistoryService {

	@Autowired
	private IInvestorCouponHistoryDAO investorCouponHistoryDAO;

	@Override
	public InvestorCouponHistory insert(InvestorCouponHistory investorCouponHistory) {
		return investorCouponHistoryDAO.insert(investorCouponHistory);
	}

	@Override
	public InvestorCouponHistory delete(InvestorCouponHistory investorCouponHistory) {
		return investorCouponHistoryDAO.delete(investorCouponHistory);
	}

	@Override
	public InvestorCouponHistory update(InvestorCouponHistory investorCouponHistory) {
		return investorCouponHistoryDAO.update(investorCouponHistory);
	}

	@Override
	public InvestorCouponHistory get(String uuid) {
		return investorCouponHistoryDAO.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return investorCouponHistoryDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return investorCouponHistoryDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return investorCouponHistoryDAO.getCount(inputParams);
	}
}
