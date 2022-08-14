package cn.zeppin.product.ntb.backadmin.service.autotask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.ntb.backadmin.service.api.ICouponService;
import cn.zeppin.product.ntb.backadmin.service.api.ICouponStrategyService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorCouponService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeCouponService;

@Component
public class CouponTask {
	
	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private ICouponStrategyService couponStrategyService;

	@Autowired
	private IInvestorCouponService investorCouponService;
	
	@Autowired
	private IQcbEmployeeCouponService qcbEmployeeCouponService;
	/**
	 * 定期过期用户优惠券
	 */
	@Scheduled(cron="1 0/5 *  * * ? ")
	public void InvestorCouponExpiryDateUpdate() {
		this.investorCouponService.updateExpiryDate();
		this.qcbEmployeeCouponService.updateExpiryDate();
	}
	
}