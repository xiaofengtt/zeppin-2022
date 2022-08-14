/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorProductBuyOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyOperateService;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyOperate;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 *
 */
@Service
public class InvestorProductBuyOperateService extends BaseService implements IInvestorProductBuyOperateService {

	@Autowired
	private IInvestorProductBuyOperateDAO investorProductBuyOperateDAO;
	
	@Override
	public InvestorProductBuyOperate insert(InvestorProductBuyOperate investorProductBuyOperate) {
		return investorProductBuyOperateDAO.insert(investorProductBuyOperate);
	}

	@Override
	public InvestorProductBuyOperate delete(InvestorProductBuyOperate investorProductBuyOperate) {
		return investorProductBuyOperateDAO.delete(investorProductBuyOperate);
	}

	@Override
	public InvestorProductBuyOperate update(InvestorProductBuyOperate investorProductBuyOperate) {
		return investorProductBuyOperateDAO.update(investorProductBuyOperate);
	}

	@Override
	public InvestorProductBuyOperate get(String uuid) {
		return investorProductBuyOperateDAO.get(uuid);
	}
}
