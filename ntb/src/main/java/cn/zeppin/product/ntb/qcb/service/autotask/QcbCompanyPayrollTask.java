package cn.zeppin.product.ntb.qcb.service.autotask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll.QcbCompanyPayrollStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollService;

@Component
public class QcbCompanyPayrollTask extends BaseService{
	
	@Autowired
	private IQcbCompanyPayrollService qcbCompanyPayrollService;
	
	/**
	 * 定期发放福利金
	 */
	@Scheduled(cron="1 0/1 *  * * ? ")
	public void qcbCompanyPayrollPay() {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("payTime", "1");
		searchMap.put("status", QcbCompanyPayrollStatus.SUBMIT);
		
		List<Entity> list = this.qcbCompanyPayrollService.getListForPage(searchMap, -1, -1, null, QcbCompanyPayroll.class);
		
		for(Entity en : list){
			QcbCompanyPayroll qcp = (QcbCompanyPayroll) en;
			
			Boolean flag = true;
			try{
				this.qcbCompanyPayrollService.tryPay(qcp.getUuid());
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
				super.flushAll();
			}
			
			if(!flag){
				qcp = this.qcbCompanyPayrollService.get(qcp.getUuid());
				qcp.setStatus(QcbCompanyPayrollStatus.FAILED);
				this.qcbCompanyPayrollService.update(qcp);
			}
		}
	}
	
}