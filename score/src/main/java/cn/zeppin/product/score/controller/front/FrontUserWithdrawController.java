/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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
import cn.zeppin.product.score.entity.Bank;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.entity.FrontUserBankcard;
import cn.zeppin.product.score.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.zeppin.product.score.entity.MobileCode;
import cn.zeppin.product.score.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.score.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.score.entity.SystemParam;
import cn.zeppin.product.score.entity.SystemParam.SystemParamKey;
import cn.zeppin.product.score.service.BankService;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.CapitalPlatformService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserBankcardService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.MobileCodeService;
import cn.zeppin.product.score.service.SystemParamService;
import cn.zeppin.product.score.util.Base64Util;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.Utlity;

/**
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/userWithdraw")
public class FrontUserWithdrawController extends BaseController{
	
	@Autowired
	private FrontUserAccountService frontUserAccountService;
	
	@Autowired
	private FrontUserHistoryService frontUserHistoryService;
	
	@Autowired
	private CapitalPlatformService capitalPlatformService;
	
	@Autowired
	private CapitalAccountService capitalAccountService;
	
	@Autowired
	private FrontUserBankcardService frontUserBankcardService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private MobileCodeService mobileCodeService;
	
	@Autowired
	private SystemParamService systemParamService;
	
	/**
	 * 获取手续费
	 * @return
	 */
	@RequestMapping(value = "/poundage", method = RequestMethod.GET)
	@ResponseBody
	protected Result poundage(){
		SystemParam sp = this.systemParamService.get(SystemParamKey.WITHDRAW_POUNDAGE);
		Map<String,Object> resultMap = JSONUtils.json2map(sp.getParamValue());
		return ResultManager.createDataResult(resultMap);
	}
	/**
	 * 用户提现
	 * @param price
	 * @param bankcard
	 * @param mobileCode
	 * @return
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	@ActionParam(key = "price", type = DataType.STRING, message = "提现金额", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message = "提现银行卡编号", required = true)
	@ActionParam(key = "mobileCode", type = DataType.STRING, message = "手机验证码", required = true)
	@ResponseBody
	protected Result withdraw(String bankcard, String mobileCode, String price, HttpServletRequest request){
		
		synchronized(this){
			FrontUser fu = getFrontUser(request);
			if(fu == null) {
				return ResultManager.createFailResult("用户未登录！");
			}
			
			FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(fu.getUuid());
			if(fua == null){
				return ResultManager.createFailResult("用户账户状态异常， 请联系管理员！");
			}
			FrontUserBankcard fub = this.frontUserBankcardService.get(bankcard);
			if(fub == null){
				return ResultManager.createFailResult("未绑定该银行卡!");
			}
			if(!fu.getUuid().equals(fub.getFrontUser())){
				return ResultManager.createFailResult("提现银行卡信息错误!");
			}
			if(!FrontUserBankcardStatus.NORMAL.equals(fub.getStatus())){
				return ResultManager.createFailResult("提现银行卡信息错误!");
			}
			
			Bank bank = this.bankService.get(fub.getBank());
			if(bank == null){
				return ResultManager.createFailResult("提现银行错误!");
			}
			mobileCode = Base64Util.getFromBase64(mobileCode);
			
			price = Base64Util.getFromBase64(price);
			if (!Utlity.isPositiveCurrency(price)) {
				return ResultManager.createFailResult("提现金额输入错误!");
			}
			BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
			if(pay.compareTo(Utlity.REAPAL_MAX_WITHDRAW) == 1){
				return ResultManager.createFailResult("提现金额不能高于500w!");
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", fu.getMobile());
			params.put("status", MobileCodeStatus.NORMAL);
			params.put("type", MobileCodeTypes.FUNDCODE);
			List<MobileCode> lstMobileCode = this.mobileCodeService.getListByParams(params);
			MobileCode mc = null;
			if(lstMobileCode != null && lstMobileCode.size() > 0){
				mc = (MobileCode) lstMobileCode.get(0);
			}
			if(mc == null){
				return ResultManager.createFailResult("请先获取验证码");
			}
			if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
				return ResultManager.createFailResult("验证码输入错误！");
			}
			
			if(!mobileCode.equals(mc.getCode())){
				return ResultManager.createFailResult("验证码输入错误！");
			}
			
			//验证账户余额是否充足
			BigDecimal total = fua.getBalanceFree();
			if(total.compareTo(pay.add(Utlity.WITHDRAW_POUNDAGE)) == -1){
				return ResultManager.createFailResult("超出账户余额!");
			}
			
			BigDecimal poundage = BigDecimal.ZERO;
			SystemParam sp = this.systemParamService.get(SystemParamKey.WITHDRAW_POUNDAGE);
			Map<String, Object> poundageMap = JSONUtils.json2map(sp.getParamValue());
			for(String key : poundageMap.keySet()){
				String[] keys = key.split("-");
				String value = poundageMap.get(key).toString();
				BigDecimal min = BigDecimal.valueOf(Double.valueOf(keys[0]));
				BigDecimal max = BigDecimal.valueOf(Double.valueOf(keys[1]));
				if(min.compareTo(pay) <= 0 && max.compareTo(pay) >= 0){
					if(value.indexOf("%") > -1){
						String rateStr = value.replace("%", "");
						BigDecimal poundageRate = BigDecimal.valueOf(Double.valueOf(rateStr)).multiply(BigDecimal.valueOf(0.01));
						poundage = pay.multiply(poundageRate).setScale(2, BigDecimal.ROUND_HALF_UP);
					}else{
						poundage = BigDecimal.valueOf(Double.valueOf(value));
					}
					break;
				}
			}
			
			FrontUserHistory fuh = new FrontUserHistory();
			fuh.setUuid(UUID.randomUUID().toString());
			fuh.setFrontUser(fu.getUuid());
			fuh.setFrontUserAccount(fua.getUuid());
			fuh.setIncome(BigDecimal.ZERO);
			fuh.setPay(pay);
			fuh.setPoundage(poundage);
			fuh.setType(FrontUserHistoryType.USER_WITHDRAW);
			fuh.setStatus(FrontUserHistoryStatus.NORMAL);
			fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
			this.frontUserHistoryService.withdraw(fuh);
		}
		return ResultManager.createSuccessResult("提现成功！");
	}
}
