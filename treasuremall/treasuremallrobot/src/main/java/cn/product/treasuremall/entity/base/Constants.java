package cn.product.treasuremall.entity.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统常量
 * @author Administrator
 *
 */
public class Constants {

	public final static String ORDER_TYPE_DEMO = "demo";
	
	public final static String GAME_TYPE_TRADITION = "tradition";//传统玩法
	public final static String GAME_TYPE_ONEVONE = "onevone";//1v1PK
	public final static String GAME_TYPE_GROUP2 = "group2";//两群PK
	public final static String GAME_TYPE_GROUP3 = "group3";//三群PK
	
	public final static String ORDER_TYPE_SYSTEM_ADD = "system_add";
	public final static String ORDER_TYPE_SYSTEM_SUB = "system_sub";
	public final static String ORDER_TYPE_SYSTEM_GIVE = "system_give";
	public final static String ORDER_TYPE_USER_RECHARGE = "user_recharge";
	public final static String ORDER_TYPE_USER_WITHDRAW = "user_withdraw";
	public final static String ORDER_TYPE_USER_PAYMENT = "user_payment";
	public final static String ORDER_TYPE_USER_EXCHANGE = "user_exchange";
	public final static String ORDER_TYPE_USER_REGISTER = "user_register";//用户注册
	public final static String ORDER_TYPE_USER_SCORELOTTERY = "user_scorelottery";//用户参加积分抽奖活动活动
	public final static String ORDER_TYPE_USER_BUYFREE = "user_buyfree";//用户参加0元购活动活动
	public final static String ORDER_TYPE_USER_CHECKIN = "user_checkin";//用户参加签到活动活动
	public final static String ORDER_TYPE_USER_DELIVERY = "user_delivery";
	public final static String ORDER_TYPE_USER_RECOMMEND = "user_recommend";
	
	
	public final static String ORDER_REASON_SYSTEM_ADD = "系统发放";
	public final static String ORDER_REASON_SYSTEM_SUB = "系统扣减";
	public final static String ORDER_REASON_SYSTEM_GIVE = "系统赠送";
	public final static String ORDER_REASON_USER_RECHARGE = "用户充值";
	public final static String ORDER_REASON_USER_WITHDRAW = "用户提现";
	public final static String ORDER_REASON_USER_PAYMENT = "用户支付";
	public final static String ORDER_REASON_USER_EXCHANGE = "用户兑换";
	public final static String ORDER_REASON_USER_REGISTER = "用户注册";
	public final static String ORDER_REASON_USER_SCORELOTTERY = "用户参加积分抽奖活动";
	public final static String ORDER_REASON_USER_BUYFREE = "用户参加0元购活动";
	public final static String ORDER_REASON_USER_CHECKIN = "用户参加签到活动";
	public final static String ORDER_REASON_USER_DELIVERY = "用户领取实物奖品";
	public final static String ORDER_REASON_USER_RECOMMEND = "用户邀请新人获利";
	
	public static final Map<String, String> orderTypeTemplateInfoMap = new HashMap<String, String> () {
		private static final long serialVersionUID = 1L;

		{
			put(ORDER_TYPE_SYSTEM_ADD,ORDER_REASON_SYSTEM_ADD);
			put(ORDER_TYPE_SYSTEM_SUB,ORDER_REASON_SYSTEM_SUB);
			put(ORDER_TYPE_SYSTEM_GIVE,ORDER_REASON_SYSTEM_GIVE);
			put(ORDER_TYPE_USER_RECHARGE,ORDER_REASON_USER_RECHARGE);
			put(ORDER_TYPE_USER_WITHDRAW,ORDER_REASON_USER_WITHDRAW);
			put(ORDER_TYPE_USER_PAYMENT,ORDER_REASON_USER_PAYMENT);
			put(ORDER_TYPE_USER_EXCHANGE,ORDER_REASON_USER_EXCHANGE);
			put(ORDER_TYPE_USER_REGISTER,ORDER_REASON_USER_REGISTER);
			put(ORDER_TYPE_USER_SCORELOTTERY,ORDER_REASON_USER_SCORELOTTERY);
			put(ORDER_TYPE_USER_BUYFREE,ORDER_REASON_USER_BUYFREE);
			put(ORDER_TYPE_USER_CHECKIN,ORDER_REASON_USER_CHECKIN);
			put(ORDER_TYPE_USER_DELIVERY,ORDER_REASON_USER_DELIVERY);
			put(ORDER_TYPE_USER_RECOMMEND,ORDER_REASON_USER_RECOMMEND);
		}
	};

	public final static long ZK_LOCK_TIMEOUT = 10L;//zookeeper同步锁超时时间,单位：秒
    
    /*
     * 声明根节点
     */
    public final static String ZK_LOCK_PATHPREFIX="/treasuremall/zkLock/";
    
    /**
     * 推送数据类型
     */
    public final static String SOCKET_DATA_TYPE_LOTTERYLIST = "lotteryList";
    public final static String SOCKET_DATA_TYPE_LOTTERYINFO = "lotteryInfo";
    public final static String SOCKET_DATA_TYPE_WININFO = "winInfo";
    public final static String SOCKET_DATA_TYPE_WINILIST = "winList";
    
    public static final Map<String, String> companyInfoMap = new HashMap<String, String> () {
		private static final long serialVersionUID = 1L;

		{
			put("shunfeng","顺丰速运");
			put("yuantong","圆通快递");
			put("zhongtong","中通快递");
			put("jingdong","京东快递");
			put("yihaodian","一号店");
			put("youzhengems","邮政EMS");
			put("yunda","韵达快递");
			put("baishi","百世快递");
			put("shentong","申通快递");
			put("zhaijisong","宅急送");
			put("tiantian","天天快递");
			put("debang","德邦快递");
			put("yousu","优速快递");
			put("kuaijie","快捷快递");
		}
	};
    
	public final static String ACTIVITY_INFO_PRIZE_TYPE_FIRSTCHARGE_VOUCHER = "firstcharge_voucher";
	public final static String ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_VOUCHER = "recharge_voucher";
	public final static String ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_GOLD = "recharge_gold";
}
