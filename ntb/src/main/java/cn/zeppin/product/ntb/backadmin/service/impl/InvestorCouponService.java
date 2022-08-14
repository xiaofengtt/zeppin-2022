/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorCouponDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorCouponService;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class InvestorCouponService extends BaseService implements IInvestorCouponService {

	@Autowired
	private IInvestorCouponDAO investorCouponDAO;

	@Override
	public InvestorCoupon insert(InvestorCoupon investorCoupon) {
		return investorCouponDAO.insert(investorCoupon);
	}

	@Override
	public InvestorCoupon delete(InvestorCoupon investorCoupon) {
		return investorCouponDAO.delete(investorCoupon);
	}

	@Override
	public InvestorCoupon update(InvestorCoupon investorCoupon) {
		return investorCouponDAO.update(investorCoupon);
	}

	@Override
	public InvestorCoupon get(String uuid) {
		return investorCouponDAO.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return investorCouponDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return investorCouponDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return investorCouponDAO.getCount(inputParams);
	}

	@Override
	public void insertBatch(List<InvestorCoupon> icList) {
		// TODO Auto-generated method stub
		for(InvestorCoupon ic : icList){
			this.investorCouponDAO.insert(ic);
		}
	}

	@Override
	public void updateExpiryDate() {
		// TODO Auto-generated method stub
		this.investorCouponDAO.updateExpiryDate();
	}
}
