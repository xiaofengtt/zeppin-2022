package cn.product.payment.aotutask;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.product.payment.entity.Callback;
import cn.product.payment.entity.Callback.CallbackStatus;
import cn.product.payment.entity.Callback.CallbackType;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.entity.UserWithdraw;
import cn.product.payment.rabbetmq.RabbitService;
import cn.product.payment.service.CallbackService;
import cn.product.payment.service.CompanyService;
import cn.product.payment.service.UserRechargeService;
import cn.product.payment.service.UserWithdrawService;
import cn.product.payment.util.HttpClientUtil;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.PaymentUtil;

@Component
@Order(value = Integer.MAX_VALUE)
public class CallbackTask implements ApplicationRunner{
	
	@Autowired
	private CallbackService callbackService;
	
	@Autowired
	private UserRechargeService userRechargeService;
	
	@Autowired
	private UserWithdrawService userWithdrawService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private RabbitService rabbitService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", CallbackStatus.NORMAL);
		List<Callback> list = this.callbackService.getListByParams(searchMap);
		List<Map<String,Object>> senderList = new ArrayList<Map<String,Object>>();
		
		for(Callback callback : list){
			Long interval = getNextInterval(callback);
			
			Map<String,Object> sender = new HashMap<String,Object>();
			sender.put("interval", interval);
			sender.put("callback", callback);
			senderList.add(sender);
		}
		senderList.sort(new Comparator<Map<String,Object>>(){
			public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
				return Long.valueOf(arg0.get("interval").toString()).compareTo(Long.valueOf(arg1.get("interval").toString()));
			}
		});
		
		for(Map<String, Object> sender : senderList){
			this.rabbitService.sendInitCallback((Callback)sender.get("callback"), Long.valueOf(sender.get("interval").toString()));
		}
	}
	
	/**
	 * 对商户回调
	 */
	@RabbitListener(queues = {"payment.callback.main.queue"},containerFactory = "multiListenerContainer")
	public void callback(Callback callback) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		try{
			if(CallbackType.USER_RECHARGE.equals(callback.getType())){
				UserRecharge ur = this.userRechargeService.get(callback.getOrderInfo());
				Company c = this.companyService.get(ur.getCompany());
				
				Map<String, String> result = JSONUtils.json2strmap(callback.getBody());
				result.put("timestamp", System.currentTimeMillis() + "");
				
				result.put("sign", PaymentUtil.getSign(result, c.getSystemPrivate()));
				String status = HttpClientUtil.post(callback.getUrl(), result);
				
				if("success".equals(status)){
					callback.setStatus(CallbackStatus.SUCCESS);
					callback.setLasttime(now);
					callback.setTimes(callback.getTimes() + 1);
					callback.setProcesstime((callback.getProcesstime() == null ? "" : callback.getProcesstime()) + ";" + now.getTime());
					this.callbackService.update(callback);
				}else{
					callback.setLasttime(now);
					callback.setTimes(callback.getTimes() + 1);
					callback.setProcesstime((callback.getProcesstime() == null ? "" : callback.getProcesstime()) + ";" + now.getTime());
					if(callback.getTimes() <= 7){
						this.callbackService.update(callback);
						this.rabbitService.sendCallback(callback);
					}else{
						callback.setStatus(CallbackStatus.FAIL);
						this.callbackService.update(callback);
					}
				}
			}else if(CallbackType.USER_WITHDRAW.equals(callback.getType())){
				UserWithdraw uw = this.userWithdrawService.get(callback.getOrderInfo());
				Company c = this.companyService.get(uw.getCompany());
				
				Map<String, String> result = JSONUtils.json2strmap(callback.getBody());
				result.put("timestamp", System.currentTimeMillis() + "");
				
				result.put("sign", PaymentUtil.getSign(result, c.getSystemPrivate()));
				String status = HttpClientUtil.post(callback.getUrl(), result);
				
				if("success".equals(status)){
					callback.setStatus(CallbackStatus.SUCCESS);
					callback.setLasttime(now);
					callback.setTimes(callback.getTimes() + 1);
					callback.setProcesstime((callback.getProcesstime() == null ? "" : callback.getProcesstime()) + ";" + now.getTime());
					this.callbackService.update(callback);
				}else{
					callback.setLasttime(now);
					callback.setTimes(callback.getTimes() + 1);
					callback.setProcesstime((callback.getProcesstime() == null ? "" : callback.getProcesstime()) + ";" + now.getTime());
					if(callback.getTimes() <= 7){
						this.callbackService.update(callback);
						this.rabbitService.sendCallback(callback);
					}else{
						callback.setStatus(CallbackStatus.FAIL);
						this.callbackService.update(callback);
					}
				}
			}
		}catch (Exception e){
			callback.setLasttime(now);
			callback.setTimes(callback.getTimes() + 1);
			callback.setProcesstime((callback.getProcesstime() == null ? "" : callback.getProcesstime()) + ";" + now.getTime());
			if(callback.getTimes() <= 7){
				this.callbackService.update(callback);
				this.rabbitService.sendCallback(callback);
			}else{
				callback.setStatus(CallbackStatus.FAIL);
				this.callbackService.update(callback);
			}
		}
	}
	
	public Long getNextInterval(Callback callback){
		if(callback.getTimes() == 0){
			return 0L;
		}else{
			if(callback.getLasttime() == null){
				return 0L;
			}else{
				Long timesplit = 0L;
				switch (callback.getTimes()){
					case 1:
						timesplit = 60 * 1000L;
						break;
					case 2:
						timesplit = 4 * 60 * 1000L;
						break;
					case 3:
						timesplit = 5 * 60 * 1000L;
						break;
					case 4:
						timesplit = 20 * 60 * 1000L;
						break;
					case 5:
						timesplit = 30 * 60 * 1000L;
						break;
					case 6:
						timesplit = 8 * 60 * 60 * 1000L;
						break;
					case 7:
						timesplit = 23 * 60 * 60 * 1000L;
						break;
					default:
						timesplit = 0L;
						break;
				}
				Long now = System.currentTimeMillis();
				Long nextTime = callback.getLasttime().getTime() + timesplit;
				if(nextTime <= now){
					return 0L ;
				}else{
					return nextTime - now;
				}
			}
		}
	}
}