package cn.zeppin.product.ntb.qcb.service.autotask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard.QcbEmployeeBankcardStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard.QcbEmployeeBankcardType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeBankcardService;

@Component
public class QcbEmployeeCreditcardTask extends BaseService{
	
	@Autowired
	private IQcbEmployeeBankcardService qcbEmployeeBankcardService;
	
	/**
	 * 信用卡还款提醒
	 */
	@Scheduled(cron="0 0 10  * * ? ")
	public void qcbEmployeeCreditcard() {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", QcbEmployeeBankcardStatus.NORMAL);
		searchMap.put("type", QcbEmployeeBankcardType.CREDIT);
		searchMap.put("flagRemind", "1");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 3);
		searchMap.put("remindTime", c.get(Calendar.DATE) + "");
		List<Entity> list = this.qcbEmployeeBankcardService.getListForPage(searchMap, -1, -1, null, QcbEmployeeBankcard.class);
		
		for(Entity en : list){
			QcbEmployeeBankcard qeb = (QcbEmployeeBankcard) en;
			try{
				this.qcbEmployeeBankcardService.sendCreditRemind(qeb);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
}