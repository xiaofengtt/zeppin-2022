package cn.zeppin.product.ntb.backadmin.service.autotask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyService;

@Component
public class ProductTask {
	
	@Autowired
	private IBankFinancialProductService bankFinancialProductService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IInvestorProductBuyService investorProductBuyService;
	
	@Autowired
	private IQcbEmployeeProductBuyService qcbEmployeeProductBuyService;
	
	@Scheduled(cron="1 0/5 *  * * ? ")
	public void bankFinancialProductStageUpdate() {
		bankFinancialProductService.updateStage();
		bankFinancialProductPublishService.updateStage();
	}
	
	@Scheduled(cron="2 0/5 *  * * ? ")
	public void investorProductBuyStageUpdate() {
		investorProductBuyService.updateStage();
		qcbEmployeeProductBuyService.updateStage();
	}
	
}