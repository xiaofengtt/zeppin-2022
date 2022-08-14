/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.entity.CapitalPlatform;
import cn.zeppin.product.score.entity.CapitalPlatform.CapitalPlatformType;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.CapitalPlatformService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.util.alipay.AliUtlity;

/**
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/userRecharge")
public class FrontUserRechargeController extends BaseController{
	
	@Autowired
	private FrontUserAccountService frontUserAccountService;
	
	@Autowired
	private FrontUserHistoryService frontUserHistoryService;
	
	@Autowired
	private CapitalPlatformService capitalPlatformService;
	
	@Autowired
	private CapitalAccountService capitalAccountService;
	
	/**
	 * 银行卡充值
	 * @return
	 */
	@RequestMapping(value = "/byBankcard", method = RequestMethod.POST)
	@ActionParam(key = "capitalAccount", message = "渠道账户", type = DataType.STRING, required = true)
	@ActionParam(key = "fee", message = "金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "transData", message = "交易信息", type = DataType.STRING, required = true)
	@ResponseBody
	public Result byBankcard(HttpServletRequest request, String capitalAccount, BigDecimal fee, String transData) {
		FrontUser fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
		}
		CapitalAccount ca = this.capitalAccountService.get(capitalAccount);
		if(ca == null){
			return ResultManager.createFailResult("渠道账户不存在！");
		}
		
		CapitalPlatform cp = this.capitalPlatformService.get(ca.getCapitalPlatform());
		if(cp == null){
			return ResultManager.createFailResult("渠道账户有误，请选择其他充值方式！");
		}
		
		if(!CapitalPlatformType.COMPANY_BANKCARD.equals(cp.getType()) && !CapitalPlatformType.PERSONAL_BANKCARD.equals(cp.getType())){
			return ResultManager.createFailResult("充值渠道有误，请选择其他充值方式！");
		}
		
		FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(fu.getUuid());
		if(fua == null){
			return ResultManager.createFailResult("用户账户状态异常， 请联系管理员！");
		}
		
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setUuid(UUID.randomUUID().toString());
		fuh.setFrontUser(fu.getUuid());
		fuh.setFrontUserAccount(fua.getUuid());
		fuh.setIncome(fee);
		fuh.setPay(BigDecimal.ZERO);
		fuh.setPoundage(fee.multiply(ca.getPoundageRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
		fuh.setType(FrontUserHistoryType.USER_RECHARGE);
		fuh.setTransData(transData);
		fuh.setCapitalAccount(capitalAccount);
		fuh.setStatus(FrontUserHistoryStatus.NORMAL);
		fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.frontUserHistoryService.recharge(fuh, cp.getType());
		return ResultManager.createSuccessResult("操作成功！");
	}
	
	/**
	 * 支付宝对公充值
	 * @return
	 */
	@RequestMapping(value = "/byAlipay", method = RequestMethod.POST)
	@ActionParam(key = "capitalAccount", message = "渠道账户", type = DataType.STRING, required = true)
	@ActionParam(key = "fee", message = "金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "transData", message = "交易信息", type = DataType.STRING, required = true)
	@ResponseBody
	public Result byAlipay(HttpServletRequest request, String capitalAccount, BigDecimal fee, String transData) {
		FrontUser fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
		}
		CapitalAccount ca = this.capitalAccountService.get(capitalAccount);
		if(ca == null){
			return ResultManager.createFailResult("渠道账户不存在！");
		}
		
		CapitalPlatform cp = this.capitalPlatformService.get(ca.getCapitalPlatform());
		if(cp == null){
			return ResultManager.createFailResult("渠道账户有误，请选择其他充值方式！");
		}
		
		if(!CapitalPlatformType.COMPANY_ALIPAY.equals(cp.getType())){
			return ResultManager.createFailResult("充值渠道有误，请选择其他充值方式！");
		}
		
		FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(fu.getUuid());
		if(fua == null){
			return ResultManager.createFailResult("用户账户状态异常， 请联系管理员！");
		}
		
		//生成订单号
		String orderNum = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_APP,Utlity.getOrderTypeByPlatformType(cp.getType()),Utlity.BILL_PURPOSE_RECHARGE);
		
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setUuid(UUID.randomUUID().toString());
		fuh.setFrontUser(fu.getUuid());
		fuh.setFrontUserAccount(fua.getUuid());
		fuh.setIncome(fee);
		fuh.setPay(BigDecimal.ZERO);
		fuh.setPoundage(fee.multiply(ca.getPoundageRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
		fuh.setType(FrontUserHistoryType.USER_RECHARGE);
		fuh.setTransData(transData);
		fuh.setCapitalAccount(capitalAccount);
		fuh.setStatus(FrontUserHistoryStatus.NORMAL);
		fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fuh.setOrderNum(orderNum);
		
		//调用支付宝wap支付接口 返回form表单数据
		Map<String, Object> params = new HashMap<String, Object>();//封装参数
		try {
			params.put("passback_params", URLEncoder.encode(fuh.getUuid(), "UTF-8"));
			params.put("time_expire", Utlity.timeSpanToStringLess(new Timestamp(fuh.getCreatetime().getTime()+5*60*1000)));//订单超时时间5分钟
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		params.put("out_trade_no", fuh.getOrderNum());
		params.put("total_amount", fee);
		
		Map<String, Object> result = AliUtlity.createOrder4wap(params);
		if ((boolean)result.get("request")) {
			if((boolean)result.get("result")) {
				this.frontUserHistoryService.recharge(fuh, cp.getType());
				return ResultManager.createDataResult(result.get("response"), "操作成功！");
			} else {
				return ResultManager.createFailResult(result.get("message").toString());
			}
		} else {
			return ResultManager.createFailResult("操作失败！");
		}
	}
}
