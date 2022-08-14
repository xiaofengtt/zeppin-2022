package cn.zeppin.product.ntb.qcb.utility;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.weixin.TemplateMessage;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.weixin.BaseMessageUtil;
import cn.zeppin.product.utility.weixin.CommonUtil;
import cn.zeppin.product.utility.weixin.ConfigUtil;

/**
 * <p>Title:QcbMessageUtil</p>
 * <p>Description:企财宝模板消息</p>
 * @author geng
 * @date 2018年1月30日 下午5:56:20
 */
public class QcbMessageUtil extends BaseMessageUtil {
	//管理员标签的id
	private static final int ADMINTAG = 100;
	/**
	 * 提现申请成功通知
	 * 例：
	 * 恭喜，您的提现已转银行处理，具体到账时间以银行为准，请注意查收~~
     * 提现金额：￥1000.00
     * 处理时间：2016-08-27 20:20:58
     * 提现方式：中国农业银行(33**333)
     * 感谢您的使用！如有疑问请联系客服
     * 
     * @param openId 要发送消息的员工openid
	 * @param amount 提现金额
	 * @param dealTime 处理时间,格式：YYYY-MM-DD HH:mm:ss
	 * @param bank 提现方式,格式：银行名称(**后4位)
	 * @return
	 */
	public static void withdrawApplyTemplate(String openId, String amount,String dealTime,String bank) {
		Map<String,  Map<String, String>> params = new HashMap<>();
		String first = "恭喜，您的提现已转银行处理，具体到账时间以银行为准，请注意查收~~\n";
		String remark = "\n感谢您的使用！如有疑问请联系客服";
		params.put("first", TemplateMessage.item(first));
		params.put("keyword1", TemplateMessage.item("￥"+amount,"#56bb69"));
		params.put("keyword2", TemplateMessage.item(dealTime));
		params.put("keyword3", TemplateMessage.item(bank));
		params.put("remark", TemplateMessage.item(remark));
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTouser(openId);
		templateMessage.setTemplate_id(ConfigUtil.QcbTemplate.WITHDRAW_APPLY_ID);
		templateMessage.setUrl(null);
		templateMessage.setData(params);
		ConfigUtil.sendTemplate(CommonUtil.getAccessToken(ConfigUtil.QCB_APPID,ConfigUtil.QCB_APP_SECRECT).getAccessToken(),JSONUtils.obj2json(templateMessage));
	}
	
	/**
	 * 信用卡还款申请成功通知
	 * 例：
	 * 
	 * 您的信用卡还款已经成功
	 * 信用卡尾号：4123
	 * 还款金额：100元
	 * 如果您有任何问题，请致电：4000000
     * 
     * @param openId 要发送消息的员工openid
	 * @param amount 还款金额
	 * @param bankcard 信用卡尾号
	 * @return
	 */
	public static void repaymentApplyTemplate(String openId, String amount,String bankcard) {
		Map<String,  Map<String, String>> params = new HashMap<>();
		String first = "恭喜，您的信用卡还款已转银行处理，具体到账时间以银行为准，请注意查询~~\n";
		String remark = "\n感谢您的使用！如有疑问请联系客服";
		params.put("first", TemplateMessage.item(first));
		params.put("keyword1", TemplateMessage.item("￥"+amount,"#56bb69"));
		params.put("keyword2", TemplateMessage.item(bankcard));
		params.put("remark", TemplateMessage.item(remark));
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTouser(openId);
		templateMessage.setTemplate_id(ConfigUtil.QcbTemplate.REPAYMENT_APPLY_ID);
		templateMessage.setUrl(null);
		templateMessage.setData(params);
		ConfigUtil.sendTemplate(CommonUtil.getAccessToken(ConfigUtil.QCB_APPID,ConfigUtil.QCB_APP_SECRECT).getAccessToken(),JSONUtils.obj2json(templateMessage));
	}
	
	/**
	 * 到账提醒
	 * 例：
	 * 您有一笔福利发放到账了，请确认查收！
	 * 到账金额：100.00元
	 * 到账时间：2015-11-05 23:00:00
	 * 到账详情：张女士预约小米4手机的预约款（预约单号：27BD137CAF31E127）已汇入您的账号
	 * 感谢你的使用。
     * 
	 */
	public static void employeesPayTemplate(String openid, String price,String time,String title,String remark,String uuid) {
		Map<String,  Map<String, String>> params = new HashMap<>();
		params.put("first", TemplateMessage.item("您有一笔福利发放到账了，请确认查收！\n"));
		params.put("keyword1", TemplateMessage.item(price+"元","#56bb69"));
		params.put("keyword2", TemplateMessage.item(time));
		params.put("keyword3", TemplateMessage.item(title));
		params.put("remark", TemplateMessage.item("\n"+remark));
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTouser(openid);
		templateMessage.setTemplate_id(ConfigUtil.QcbTemplate.EMPLOYEES_PAY_ID);
		templateMessage.setUrl("https://wechat.qicaibao.cc/qcbWechat/wagesDetail.html?uuid="+uuid+"&openId="+openid);
//		templateMessage.setUrl("https://account.qicaibao.vip/qcbWechat/wagesDetail.html?uuid="+uuid+"&openId="+openid);
		templateMessage.setData(params);
		ConfigUtil.sendTemplate(getToken(),JSONUtils.obj2json(templateMessage));
	}
	
	/**
	 * 信用卡还款提醒
	 * 例：
	 * 尊敬的企财宝用户，您有一张信用卡该还款了！
	 * 还款信息：中国工商银行，尾号4444
	 * 还款时间：每月10日
	 * 感谢你的使用。
     * 
	 */
	public static void creditcardRemindTemplate(String openid, String creditcard, String bank, String time) {
		Map<String,  Map<String, String>> params = new HashMap<>();
		params.put("first", TemplateMessage.item("尊敬的企财宝用户，您有一张信用卡该还款了！\n"));
		params.put("keyword1", TemplateMessage.item(bank + ",尾号：" + creditcard));
		params.put("keyword2", TemplateMessage.item("每月" + time + "日"));
		params.put("remark", TemplateMessage.item("\n感谢您的使用！如有疑问请联系客服"));
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTouser(openid);
		templateMessage.setTemplate_id(ConfigUtil.QcbTemplate.CREDITCARD_REMIND_ID);
		templateMessage.setUrl(null);
		templateMessage.setData(params);
		ConfigUtil.sendTemplate(getToken(),JSONUtils.obj2json(templateMessage));
	}
	
	/**
	 * 提现申请通知管理员
	 * 例：
	 * 您有新的提现申请需要处理：
	 * 提现单号：4617888259202534518
	 * 申请用户：张小全
	 * 提现金额：800.00元
	 * 请及时处理。
	 */
	public static void withdrawNoticeTemplate(String token,String adminOpenid,String orderNum,String userName,String wPrice, String uuid, String type){
		Map<String,  Map<String, String>> params = new HashMap<>();
		params.put("first", TemplateMessage.item("您有新的提现申请需要处理：\n"));
		params.put("keyword1", TemplateMessage.item(orderNum));
		params.put("keyword2", TemplateMessage.item(userName));
		params.put("keyword3", TemplateMessage.item(wPrice+"元"));
		params.put("remark", TemplateMessage.item("\n请及时处理。"));
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTouser(adminOpenid);
		templateMessage.setTemplate_id(ConfigUtil.QcbTemplate.WITHDRAW_NOTICE_ID);
		templateMessage.setUrl("https://wechat.qicaibao.cc/qcbWechat/drawcashStatus.html?uuid=" + uuid + "&type=" + type);
//		templateMessage.setUrl("https://account.qicaibao.vip/qcbWechat/drawcashStatus.html?uuid=" + uuid + "&type=" + type);
		templateMessage.setData(params);
		ConfigUtil.sendTemplate(token,JSONUtils.obj2json(templateMessage));
	}
	
	/**
	 * 余额不足通知管理员
	 * 例：
	 * 融宝后台余额不足十万元。
	 * 账户余额：800.00元
	 * 截止时间： 2019年1月1日
	 * 请及时充值。
	 */
	public static void balanceNoticeTemplate(String token,String adminOpenid, String price){
		Map<String,  Map<String, String>> params = new HashMap<>();
		params.put("first", TemplateMessage.item("融宝后台余额不足十万元！\n"));
		params.put("keyword1", TemplateMessage.item(price+"元"));
		params.put("keyword2", TemplateMessage.item(Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis()))));
		params.put("remark", TemplateMessage.item("\n请及时充值。"));
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTouser(adminOpenid);
		templateMessage.setTemplate_id(ConfigUtil.QcbTemplate.BALANCE_NOTICE_ID);
		templateMessage.setData(params);
		ConfigUtil.sendTemplate(token,JSONUtils.obj2json(templateMessage));
	}
	
	/**
	 * 提现失败通知用户
	 * 例：
	 * 您在企财宝中进行的提现申请失败了：
	 * 提现单号：4617888259202534518
	 * 申请用户：张小全
	 * 提现金额：800.00元
	 * 失败原因
	 */
	public static void withdrawFailedTemplate(String openid,String orderNum,String userName,String wPrice,String remark){
		Map<String,  Map<String, String>> params = new HashMap<>();
		params.put("first", TemplateMessage.item("很抱歉，您的提现申请受理失败。资金已退回至您的账户余额，请及时核对余额，并确认您的提现银行卡信息是否正确。 \n"));
		params.put("keyword1", TemplateMessage.item(orderNum));
		params.put("keyword2", TemplateMessage.item(userName));
		params.put("keyword3", TemplateMessage.item(wPrice+"元"));
		params.put("remark", TemplateMessage.item("\n" + remark));
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTouser(openid);
		templateMessage.setTemplate_id(ConfigUtil.QcbTemplate.WITHDRAW_NOTICE_ID);
		templateMessage.setUrl(null);
		templateMessage.setData(params);
		ConfigUtil.sendTemplate(CommonUtil.getAccessToken(ConfigUtil.QCB_APPID,ConfigUtil.QCB_APP_SECRECT).getAccessToken(),JSONUtils.obj2json(templateMessage));
	}
	
	/**
	 * 通知所有管理员标签的用户，有新的提现申请
	 * @param orderNum
	 * @param userName
	 * @param wPrice
	 */
	public static void withdrawNoticeToAdmin(String orderNum,String userName,String wPrice,String uuid, String type){
		Map<String, Object> tag = new HashMap<>();
		tag.put("tagid", ADMINTAG);
		tag.put("next_openid", "");
		String token = getToken();
		String[] openid = ConfigUtil.getTagOpenid(token, JSONUtils.obj2json(tag));
		for (String id : openid) {
			withdrawNoticeTemplate(token,id, orderNum, userName, wPrice, uuid, type);
		}
//		withdrawNoticeTemplate(token,"o4TYt0lJce0_dVW1pEKN98V_CLdw", orderNum, userName, wPrice, uuid, type);//测试
//		withdrawNoticeTemplate(token,"o4TYt0iK0FPFH7L7sIVQ8Tvxs_-U", orderNum, userName, wPrice, uuid, type);//测试
	}
	
	/**
	 * 通知所有管理员融宝余额不足
	 * @param orderNum
	 * @param userName
	 * @param wPrice
	 */
	public static void balanceNoticeToAdmin(String price){
		Map<String, Object> tag = new HashMap<>();
		tag.put("tagid", ADMINTAG);
		tag.put("next_openid", "");
		String token = getToken();
		String[] openid = ConfigUtil.getTagOpenid(token, JSONUtils.obj2json(tag));
		for (String id : openid) {
			balanceNoticeTemplate(token,id,price);
		}
//		balanceNoticeTemplate(token,"odVZs1jQ8eN0NlPMQ83jC6WbfEik",price);//测试
//		balanceNoticeTemplate(token,"odVZs1oKD0NbVnc1H5MvtfPuA8IU",price);//测试
	}
	
	/**
	 *  企业认证
	 * @param companyName 公司名称
	 * @param adminOpenid 管理员openid
	 * @param token
	 */
	public static void companyCer(String companyName,String adminOpenid,String token){
		Map<String,  Map<String, String>> params = new HashMap<>();
		params.put("first", TemplateMessage.item("待审核通知：\n"));
		params.put("keyword1", TemplateMessage.item(companyName));
		params.put("keyword2", TemplateMessage.item("企业认证"));
		params.put("remark", TemplateMessage.item("\n请及时处理。"));
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTouser(adminOpenid);
		templateMessage.setTemplate_id(ConfigUtil.QcbTemplate.COMPANY_CER_ID);
		templateMessage.setUrl(null);
		templateMessage.setData(params);
		ConfigUtil.sendTemplate(token,JSONUtils.obj2json(templateMessage));
	}
	
	/**
	 * 通知管理员有待审核的企业认证
	 * @param companyName 公司名称
	 */
	public static void commpanyCerToAdmin(String companyName){
		Map<String, Object> tag = new HashMap<>();
		tag.put("tagid", ADMINTAG);//管理员标签的id
		tag.put("next_openid", "");
		String token = getToken();
		String[] openid = ConfigUtil.getTagOpenid(token, JSONUtils.obj2json(tag));
		for (String id : openid) {
			companyCer(companyName,id,token);
		}
	}
	
	private static String getToken(){
		return CommonUtil.getAccessToken(ConfigUtil.QCB_APPID,ConfigUtil.QCB_APP_SECRECT).getAccessToken();
	}
}
