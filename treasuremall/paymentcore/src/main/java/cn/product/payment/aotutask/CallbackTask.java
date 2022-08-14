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

import cn.product.payment.dao.CallbackDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.dao.UserRechargeDao;
import cn.product.payment.dao.UserWithdrawDao;
import cn.product.payment.entity.Callback;
import cn.product.payment.entity.Callback.CallbackStatus;
import cn.product.payment.entity.Callback.CallbackType;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.entity.UserWithdraw;
import cn.product.payment.rabbetmq.RabbitService;
import cn.product.payment.util.HttpClientUtil;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.PaymentUtil;

@Component
@Order(value = Integer.MAX_VALUE)
public class CallbackTask implements ApplicationRunner{
	
	@Autowired
	private CallbackDao callbackDao;
	
	@Autowired
	private UserRechargeDao userRechargeDao;
	
	@Autowired
	private UserWithdrawDao userWithdrawDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private RabbitService rabbitService;
	
	@Override
	/**
	 * 启动回调
	 */
	public void run(ApplicationArguments args) throws Exception {
		//查询回调信息 处理后放入列表
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", CallbackStatus.NORMAL);
		List<Callback> list = this.callbackDao.getListByParams(searchMap);
		List<Map<String,Object>> senderList = new ArrayList<Map<String,Object>>();
		for(Callback callback : list){
			Long interval = getNextInterval(callback);
			
			Map<String,Object> sender = new HashMap<String,Object>();
			sender.put("interval", interval);
			sender.put("callback", callback);
			senderList.add(sender);
		}
		//根据发送时间排序
		senderList.sort(new Comparator<Map<String,Object>>(){
			public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
				return Long.valueOf(arg0.get("interval").toString()).compareTo(Long.valueOf(arg1.get("interval").toString()));
			}
		});
		
		//顺序将回调放入回调池
		for(Map<String, Object> sender : senderList){
			this.rabbitService.sendInitCallback((Callback)sender.get("callback"), Long.valueOf(sender.get("interval").toString()));
		}
	}
	
	/**
	 * 对商户回调
	 * @param callback
	 */
	@RabbitListener(queues = {"payment.callback.main.queue"},containerFactory = "multiListenerContainer")
	public void callback(Callback callback) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		try{
			if(CallbackType.USER_RECHARGE.equals(callback.getType())){
				//充值订单
				UserRecharge ur = this.userRechargeDao.get(callback.getOrderInfo());
				Company c = this.companyDao.get(ur.getCompany());
				
				Map<String, String> result = JSONUtils.json2strmap(callback.getBody());
				result.put("timestamp", System.currentTimeMillis() + "");
				
				result.put("sign", PaymentUtil.getApisSign(result, c.getSystemPublic()));
				String status = HttpClientUtil.post(callback.getUrl(), result);
				
				if("success".equals(status)){
					//回调成功，更新状态
					callback.setStatus(CallbackStatus.SUCCESS);
					callback.setLasttime(now);
					callback.setTimes(callback.getTimes() + 1);
					callback.setProcesstime((callback.getProcesstime() == null ? "" : callback.getProcesstime()) + ";" + now.getTime());
					this.callbackDao.update(callback);
				}else{
					//回调未成功
					callback.setLasttime(now);
					callback.setTimes(callback.getTimes() + 1);
					callback.setProcesstime((callback.getProcesstime() == null ? "" : callback.getProcesstime()) + ";" + now.getTime());
					if(callback.getTimes() <= 7){
						//将下一次回调放入回调池
						this.callbackDao.update(callback);
						this.rabbitService.sendCallback(callback);
					}else{
						//最后一次回调 更新回调状态
						callback.setStatus(CallbackStatus.FAIL);
						this.callbackDao.update(callback);
					}
				}
			}else if(CallbackType.USER_WITHDRAW.equals(callback.getType())){
				//提现订单
				UserWithdraw uw = this.userWithdrawDao.get(callback.getOrderInfo());
				Company c = this.companyDao.get(uw.getCompany());
				
				Map<String, String> result = JSONUtils.json2strmap(callback.getBody());
				result.put("timestamp", System.currentTimeMillis() + "");
				
				result.put("sign", PaymentUtil.getApisSign(result, c.getSystemPublic()));
				String status = HttpClientUtil.post(callback.getUrl(), result);
				
				if("success".equals(status)){
					//回调成功，更新状态
					callback.setStatus(CallbackStatus.SUCCESS);
					callback.setLasttime(now);
					callback.setTimes(callback.getTimes() + 1);
					callback.setProcesstime((callback.getProcesstime() == null ? "" : callback.getProcesstime()) + ";" + now.getTime());
					this.callbackDao.update(callback);
				}else{
					//回调未成功
					callback.setLasttime(now);
					callback.setTimes(callback.getTimes() + 1);
					callback.setProcesstime((callback.getProcesstime() == null ? "" : callback.getProcesstime()) + ";" + now.getTime());
					if(callback.getTimes() <= 7){
						//将下一次回调放入回调池
						this.callbackDao.update(callback);
						this.rabbitService.sendCallback(callback);
					}else{
						//最后一次回调 更新回调状态
						callback.setStatus(CallbackStatus.FAIL);
						this.callbackDao.update(callback);
					}
				}
			}
		}catch (Exception e){
			//回调未成功
			callback.setLasttime(now);
			callback.setTimes(callback.getTimes() + 1);
			callback.setProcesstime((callback.getProcesstime() == null ? "" : callback.getProcesstime()) + ";" + now.getTime());
			
			if(callback.getTimes() <= 7){
				//将下一次回调放入回调池
				this.callbackDao.update(callback);
				this.rabbitService.sendCallback(callback);
			}else{
				//最后一次回调 更新回调状态
				callback.setStatus(CallbackStatus.FAIL);
				this.callbackDao.update(callback);
			}
		}
	}
	
	/**
	 * 获取下一次回调时间 1M/5M/10M/30M/1H/10H/23H
	 * @param callback
	 * @return
	 */
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
						timesplit = 9 * 60 * 60 * 1000L;
						break;
					case 7:
						timesplit = 13 * 60 * 60 * 1000L;
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