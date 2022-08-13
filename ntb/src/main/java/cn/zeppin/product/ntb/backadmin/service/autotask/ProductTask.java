package cn.zeppin.product.ntb.backadmin.service.autotask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductService;

@Component
public class ProductTask {
	
	@Autowired
	private IBankFinancialProductService bankFinancialProductService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Scheduled(cron="1 0/5 *  * * ? ")
	public void bankFinancialProductStageUpdate() {
		bankFinancialProductService.updateStage();
		bankFinancialProductPublishService.updateStage();
	}
	
}