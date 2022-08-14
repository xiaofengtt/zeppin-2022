package cn.zeppin.product.itic.backadmin.service.autotask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class DailyTask {
	
	@Scheduled(cron="0 0/5 *  * * ? ")
	public void cur() {
		
	}
}