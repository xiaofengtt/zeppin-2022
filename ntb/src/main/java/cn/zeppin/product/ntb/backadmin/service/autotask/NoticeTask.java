package cn.zeppin.product.ntb.backadmin.service.autotask;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.ntb.qcb.utility.QcbMessageUtil;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.reapal.ReapalUtlity;

@Component
public class NoticeTask {
	
	@Scheduled(cron="0 0 9  * * ? ")
	public void realpayBalanceNotice() {
		try{
			Map<String, Object> resultBalance = ReapalUtlity.get_balance_Query();
			String balance = resultBalance.get("balance") == null ? "0" : resultBalance.get("balance").toString();
			BigDecimal accountTotal = BigDecimal.valueOf(Double.valueOf(balance));
			if(accountTotal.compareTo(BigDecimal.valueOf(100000.0)) < 0){
				SendSms.sendSms4Qcb("融宝后台余额不足十万元，请及时充值，当前余额为:"+Utlity.numFormat4UnitDetail(accountTotal) +"元。", "18611920344,18600581016");
				QcbMessageUtil.balanceNoticeToAdmin(Utlity.numFormat4UnitDetail(accountTotal));
			}
		} catch (Exception e){
			System.out.println(e);
		}
	}
}