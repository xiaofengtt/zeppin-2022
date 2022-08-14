package cn.product.treasuremall.util.pay;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.dao.VoucherDao;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoName;
import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserRechargeOrder;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.entity.Voucher;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderStatus;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderType;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.FrontUserVoucher.FrontUserVoucherStatus;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;

/**
 * 活动数据封装
 * @author user
 *
 */
@Component("activityPayUtil")
public class ActivityPayUtil {

    
    @Autowired
    private VoucherDao voucherDao;
    
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
    
	/**
	 * 进行首充活动和充值返利活动的条件判断
	 * 首充活动要重复验证是否为真实首次参与首充活动，避免用户多次下单，到账多笔
	 * @param furo
	 * @param fua
	 * @param fuvList
	 * @param fuhAct
	 * @param aoo
	 * @param resultMap
	 * @throws ParseException
	 */
	public void isActivity(FrontUserRechargeOrder furo, FrontUserAccount fua, List<FrontUserVoucher> fuvList, FrontUserHistory fuhAct, AdminOffsetOrder aoo, Map<String, Object> resultMap) throws ParseException {
		if(furo.getIsActivity() != null && furo.getIsActivity()) {//参与活动
			
			//直接获取prize字段的活动信息，进行计算
			if(!Utlity.checkStringNull(furo.getPrize())) {
				Map<String, Object> prize = JSONUtils.json2map(furo.getPrize());
				if(prize != null && prize.size() > 0) {
					boolean isFirst = true;
					if(!Utlity.checkStringNull(furo.getActivityId())) {
						if(furo.getActivityId().indexOf(ActivityInfoName.FIRSTCHARGE) >= 0) {//参与首充了
							//再次判断是否以参与首充活动
							Boolean isPartakeFirstcharge = this.frontUserRechargeOrderDao.isPartakeFirstcharge(fua.getFrontUser());
							if(isPartakeFirstcharge) {
								isFirst = false;
								prize.remove(Constants.ACTIVITY_INFO_PRIZE_TYPE_FIRSTCHARGE_VOUCHER);
								String[] activities = furo.getActivityId().split(",");
								if(activities.length > 0) {
									List<String> arr = Arrays.asList(activities);
									List<String> arrList = new ArrayList<String>(arr);
									arrList.remove(ActivityInfoName.FIRSTCHARGE);
									if(arrList.size() <= 0) {
										furo.setIsActivity(false);
										furo.setActivityId("");
										furo.setPrize("");
									} else {
										StringBuilder sb = new StringBuilder();
										for(String active : arrList) {
											sb.append(active);
											sb.append(",");
										}
										sb.delete(sb.length() - 1, sb.length());
										furo.setActivityId(sb.toString());
										furo.setPrize(JSONUtils.obj2json(prize));
									}
								}
							}
						}
					}
					
					if(prize.containsKey(Constants.ACTIVITY_INFO_PRIZE_TYPE_FIRSTCHARGE_VOUCHER)) {//首充活动
						if(isFirst) {
							String vouchers = prize.get(Constants.ACTIVITY_INFO_PRIZE_TYPE_FIRSTCHARGE_VOUCHER).toString();
							for(String voucher : vouchers.split(",")) {
								Voucher v = this.voucherDao.get(voucher);
								if(v != null) {
									FrontUserVoucher fuv = new FrontUserVoucher();
									fuv.setUuid(UUID.randomUUID().toString());
									fuv.setFrontUser(furo.getFrontUser());
									fuv.setVoucher(voucher);
									fuv.setTitle(v.getTitle());
									fuv.setDiscription(v.getDiscription());
									fuv.setdAmount(v.getdAmount());
									fuv.setPayMin(v.getPayMin());
									fuv.setGoodsType(v.getGoodsType());
									fuv.setGoods(v.getGoods());
									
									//按照voucher设定的起止时间，计算初始时间和结束时间
									fuv.setStarttime(Utlity.getTime(v.getStarttime()) == null ? new Timestamp(System.currentTimeMillis()) : Utlity.getTime(v.getStarttime()));//未设置代表当前时间生效
									fuv.setEndtime(Utlity.getTime(v.getEndtime()));
									fuv.setCreatetime(new Timestamp(System.currentTimeMillis()));
									fuv.setStatus(FrontUserVoucherStatus.UNSTART);
									fuv.setOperattime(fuv.getCreatetime());
									
									fuvList.add(fuv);
								}
							}
						}
					} 
					
					if (prize.containsKey(Constants.ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_VOUCHER)) {//充值返利活动红包
						String vouchers = prize.get(Constants.ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_VOUCHER).toString();
						for(String voucher : vouchers.split(",")) {
							Voucher v = this.voucherDao.get(voucher);
							if(v != null) {
								FrontUserVoucher fuv = new FrontUserVoucher();
								fuv.setUuid(UUID.randomUUID().toString());
								fuv.setFrontUser(furo.getFrontUser());
								fuv.setVoucher(voucher);
								fuv.setTitle(v.getTitle());
								fuv.setDiscription(v.getDiscription());
								fuv.setdAmount(v.getdAmount());
								fuv.setPayMin(v.getPayMin());
								fuv.setGoodsType(v.getGoodsType());
								fuv.setGoods(v.getGoods());
								
								//按照voucher设定的起止时间，计算初始时间和结束时间
								fuv.setStarttime(Utlity.getTime(v.getStarttime()) == null ? new Timestamp(System.currentTimeMillis()) : Utlity.getTime(v.getStarttime()));//未设置代表当前时间生效
								fuv.setEndtime(Utlity.getTime(v.getEndtime()));
								fuv.setCreatetime(new Timestamp(System.currentTimeMillis()));
								fuv.setStatus(FrontUserVoucherStatus.UNSTART);
								fuv.setOperattime(fuv.getCreatetime());
								
								fuvList.add(fuv);
							}
						}
					} else if (prize.containsKey(Constants.ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_GOLD)) {//充值返利活动金币
						//计算返利率
						String rateStr = prize.get(Constants.ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_GOLD).toString();
						BigDecimal ratePrize = BigDecimal.valueOf(Double.valueOf(rateStr)).divide(BigDecimal.valueOf(100));
						//计算赠送金币数 保留两位小数 向下取整
						BigDecimal dAmount = furo.getIncreaseDAmount().multiply(ratePrize).setScale(2, BigDecimal.ROUND_DOWN);
						
						String content = "充值返利活动赠送" + rateStr + "%金币";
	    				
						aoo = new AdminOffsetOrder();
	    				aoo.setUuid(UUID.randomUUID().toString());
	    				aoo.setdAmount(dAmount);
	    				aoo.setFrontUser(furo.getFrontUser());
						aoo.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
	    				aoo.setOrderNum(Utlity.getOrderNum());
						aoo.setReason(content);
						aoo.setRemark(content);
						aoo.setStatus(AdminOffsetOrderStatus.CHECKED);
						aoo.setType(AdminOffsetOrderType.ADMIN_ADD);
	    				aoo.setCreatetime(new Timestamp(System.currentTimeMillis()));
	    				
	    				fuhAct = new FrontUserHistory();
	    				fuhAct.setUuid(UUID.randomUUID().toString());
	    				fuhAct.setFrontUser(aoo.getFrontUser());
	    				fuhAct.setOrderNum(aoo.getOrderNum());
	    				fuhAct.setOrderId(aoo.getUuid());
	    				fuhAct.setdAmount(aoo.getdAmount());
	    				fuhAct.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
	    				fuhAct.setCreatetime(aoo.getCreatetime());
	    				fuhAct.setRemark(content);
	    				
	    				fuhAct.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
						fuhAct.setType(FrontUserHistoryType.USER_ADD);
						fuhAct.setReason(content);
						
						fua.setBalance(fua.getBalance().add(aoo.getdAmount()));
						fuhAct.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
					}
					resultMap.put("fuvList", fuvList);
				}
			}
		}
		resultMap.put("fuhAct", fuhAct);
		resultMap.put("aoo", aoo);
		resultMap.put("fua", fua);
		resultMap.put("furo", furo);
	}
}
