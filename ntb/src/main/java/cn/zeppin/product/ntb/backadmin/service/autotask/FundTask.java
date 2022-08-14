package cn.zeppin.product.ntb.backadmin.service.autotask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;

@Component
public class FundTask {
	
	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	/**
	 * 余额自动转入活期理财
	 */
	@Scheduled(cron="0 59 23  * * ? ")
//	@Scheduled(cron="5/10 * *  * * ? ")
	public void userCurrentAutoBuy() {
		this.investorService.updateCurrentAccount();
		this.qcbEmployeeService.updateCurrentAccount();
		this.investorService.updateYesterdayAccount();
		this.qcbEmployeeService.updateYesterdayAccount();
	}
	
	/**
	 * 更新发布产品的净值字段
	 */
	@Scheduled(cron="0 0 0  * * ? ")
	public void currentNetvalueUpdate(){
		this.fundPublishService.netvalueUpdate();
	}
	
	/**
	 * 第二天没净值时短信提醒
	 */
//	@Scheduled(cron="0 0 10  * * ? ")
	public void currentNetvalueMessage(){
		this.fundPublishService.netvalueMessage();
	}
}