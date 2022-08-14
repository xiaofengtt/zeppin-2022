/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller.wechat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.ICouponService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishDailyService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.Coupon;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponType;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.FundPublishDaily;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployee.QcbEmployeeStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard.QcbEmployeeBankcardStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCoupon;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCoupon.QcbEmployeeCouponStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement.QcbEmployeeProductBuyAgreementType;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.entity.weixin.WechatUserInfo;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeBankcardService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeCouponHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeCouponService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyAgreementService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.qcb.vo.QcbEmployeeAccountVO;
import cn.zeppin.product.ntb.qcb.vo.QcbEmployeeCouponHistoryVO;
import cn.zeppin.product.ntb.qcb.vo.QcbEmployeeCouponVO;
import cn.zeppin.product.ntb.qcb.vo.QcbEmployeeHistoryVO;
import cn.zeppin.product.ntb.qcb.vo.QcbEmployeeProductBuyDetailsVO;
import cn.zeppin.product.ntb.qcb.vo.QcbEmployeeProductBuyHistoryVO;
import cn.zeppin.product.ntb.qcb.vo.QcbEmployeeProductBuyVO;
import cn.zeppin.product.ntb.qcb.vo.QcbEmployeeVO;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.weixin.CommonUtil;
import cn.zeppin.product.utility.weixin.ConfigUtil;

/**
 * @author hehe
 *
 * 微信企财宝员工接口
 */

@Controller
@RequestMapping(value = "/qcbWechat/employee")
public class WechatEmployeeController extends BaseController {
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IQcbEmployeeBankcardService qcbEmployeeBankcardService;
	
	@Autowired
	private IQcbEmployeeHistoryService qcbEmployeeHistoryService;
	
	@Autowired
	private IQcbEmployeeProductBuyService qcbEmployeeProductBuyService;
	
	@Autowired
	private IQcbEmployeeProductBuyAgreementService qcbEmployeeProductBuyAgreementService;
	
	@Autowired
	private IQcbEmployeeCouponService qcbEmployeeCouponService;
	
	@Autowired
	private IQcbEmployeeCouponHistoryService qcbEmployeeCouponHistoryService;
	
	@Autowired
	private IQcbOrderinfoByThirdpartyService qcbOrderinfoByThirdpartyService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IFundPublishDailyService fundPublishDailyService;
	
	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * 
	 * @param mobile base64编码
	 * @param mobileCode base64编码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号", required = true)
	@ActionParam(key = "mobileCode", type = DataType.STRING, message = "短信验证码", required = true)
	@ResponseBody
	public Result login(String mobile, String mobileCode, HttpServletRequest request) {
		String openID = "";
		//验证手机号格式
		mobile = Base64Util.getFromBase64(mobile);
		mobileCode = Base64Util.getFromBase64(mobileCode);
		
		if(!Utlity.isMobileNO(mobile)){
			return ResultManager.createFailResult("手机号码非法！");
		}
		
		//验证该手机号是否已注册
		QcbEmployee employee = this.qcbEmployeeService.getByMobile(mobile);
		if(employee != null){
			//校验短信验证码
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("mobile", mobile);
			inputParams.put("status", MobileCodeStatus.NORMAL);
			inputParams.put("type", MobileCodeTypes.CODE);
			List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
			MobileCode mc = null;
			if(lstMobileCode != null && lstMobileCode.size() > 0){
				mc = (MobileCode) lstMobileCode.get(0);
			}
			if(mc == null){
				return ResultManager.createFailResult("短信验证码输入错误！");
			}
			
			if(!mc.getMobile().equals(mobile)){
				return ResultManager.createFailResult("手机号输入错误！");
			}
			
			if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
				return ResultManager.createFailResult("验证码超时！");
			}
			
			if(!mobileCode.equals(mc.getCode())){
				return ResultManager.createFailResult("短信验证码输入错误！");
			}
			
			mc.setStatus(MobileCodeStatus.DISABLE);//需要更新状态
			//验证通过 需要更新OPENID
			if(!QcbEmployeeStatus.NORMAL.equals(employee.getStatus())){
				return ResultManager.createFailResult("用户已被企业删除或注销！");
			}
			//微信获取openid
			String code = request.getParameter("code");
			if(Utlity.checkStringNull(code)){
				return ResultManager.createFailResult("登录失败！");
			}
			
			openID = ConfigUtil.getOauthAccessOpenId(code,ConfigUtil.QCB_APPID,ConfigUtil.QCB_APP_SECRECT);
			//测试
//			String openID =  "oKT7-0BwQSfCLqbu2jgqj_unuiQY";
			if (openID == null || "".equals(openID)) {
				return ResultManager.createFailResult("登录失败！");
			}
			QcbEmployee qe = this.qcbEmployeeService.getByOpenId(openID);
			if(qe != null && !qe.getUuid().equals(employee.getUuid()) ){
				qe.setOpenid(null);
			} else {
				qe = null;
			}
			//获取用户微信端信息
		    WechatUserInfo wechatUserInfo = ConfigUtil.getUserInfo(CommonUtil.getAccessToken(ConfigUtil.QCB_APPID,ConfigUtil.QCB_APP_SECRECT).getAccessToken(), openID);
		    
			employee.setOpenid(openID);
			if(wechatUserInfo != null){
			    employee.setWechatIcon(wechatUserInfo.getHeadimgurl());
//		    	employee.setWechatName(wechatUserInfo.getNickname());
			}
			//登录成功 更新员工信息
			try {
				this.qcbEmployeeService.updateLogin(mc, qe, employee);
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("登录异常，请稍后重试！");
			}
			
		} else {
			return ResultManager.createFailResult("用户未开通！");
		}		
		Map<String, String> data = new HashMap<>();
		data.put("openid", openID);
		return ResultManager.createDataResult(data,"登录成功！");
	}
	
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get(HttpServletRequest request) {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		QcbEmployeeVO vo = new QcbEmployeeVO(employee);
		//判断是否绑卡
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", employee.getUuid());
		inputParams.put("status", QcbEmployeeBankcardStatus.NORMAL);
		Integer count = this.qcbEmployeeBankcardService.getCount(inputParams);
		vo.setBankcardCount(String.valueOf(count));
		
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 获取用户账户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAccountInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result getAccountInfo(HttpServletRequest request) {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		QcbEmployeeAccountVO vo = new QcbEmployeeAccountVO(employee);
		
		FundPublish fp = this.fundPublishService.get(FundPublishUuid.CURRENT);
		BigDecimal netValue = BigDecimal.ONE;
		if(fp != null && fp.getNetWorth() != null){
			netValue = fp.getNetWorth();
		}
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		HashMap<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("endtime", Utlity.timeSpanToString(now));
		List<Entity> list = this.fundPublishDailyService.getListForPage(inputParams, 1, 2, null, FundPublishDaily.class);
		if(list.size() == 2){
			BigDecimal today = ((FundPublishDaily)list.get(0)).getNetValue();
			BigDecimal yesterday = ((FundPublishDaily)list.get(1)).getNetValue();
			vo.setTotalReturnBuyDay(Utlity.numFormat4UnitDetail(employee.getCurrentAccountYesterday().multiply(today.subtract(yesterday))));
		}else{
			vo.setTotalReturnBuyDay("0.00");
		}
		
		vo.setCurrentAccount(Utlity.numFormat4UnitDetail(employee.getCurrentAccount().multiply(netValue)));
		//20180807计算总额
		String totalAmount = Utlity.numFormat4UnitDetail(employee.getTotalInvest().add(employee.getAccountBalance()).add(employee.getCurrentAccount().multiply(netValue)));
		vo.setTotalAmount(totalAmount);
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 交易记录列表
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getBill", method = RequestMethod.GET)
	@ActionParam(key = "type", type = DataType.STRING, message="类型")
	@ResponseBody
	public Result getBill(HttpServletRequest request, String type){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", employee.getUuid());
		if(type != null && "transfer".equals(type)){
			inputParams.put("transferType", type);
		}
		
		List<Entity> list = this.qcbEmployeeHistoryService.getListForPage(inputParams, -1, -1, null, QcbEmployeeHistory.class);
		List<Map<String, Object>> listMonth = new ArrayList<Map<String,Object>>();
		List<QcbEmployeeHistoryVO> listvo = new ArrayList<QcbEmployeeHistoryVO>();
		if(list != null && !list.isEmpty()){
			for(Entity entity : list){
				QcbEmployeeHistory qcpr = (QcbEmployeeHistory)entity;
				QcbEmployeeHistoryVO vo = new QcbEmployeeHistoryVO(qcpr);
				if(vo.getProduct() != null){
					BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(vo.getProduct());
					Bank bank = this.bankService.get(bfpp.getCustodian());
					String remark = "";
					if(bfpp != null && bank != null){
						remark = "【"+bank.getShortName()+"】"+bfpp.getShortname();
					}
					vo.setRemark(vo.getRemark()+remark);
				}
				listvo.add(vo);
			}
		}
		
		for(QcbEmployeeHistoryVO vo : listvo){
			if(listMonth.isEmpty()){
				Map<String, Object> data = new HashMap<String, Object>();
				List<QcbEmployeeHistoryVO> childlistvo = new ArrayList<QcbEmployeeHistoryVO>();
				childlistvo.add(vo);
				data.put("time", vo.getTime());
				data.put("dataList", childlistvo);
				listMonth.add(data);
			} else {
				boolean flag = false;
				for(Map<String, Object> map : listMonth){
					if(map.get("time") != null && vo.getTime().equals(map.get("time").toString())){
						flag = true;
						List<QcbEmployeeHistoryVO> childlistvo = (List<QcbEmployeeHistoryVO>) map.get("dataList");
						childlistvo.add(vo);
						map.put("dataList", childlistvo);
					}
				}
				if(!flag){
					Map<String, Object> data = new HashMap<String, Object>();
					List<QcbEmployeeHistoryVO> childlistvo = new ArrayList<QcbEmployeeHistoryVO>();
					childlistvo.add(vo);
					data.put("time", vo.getTime());
					data.put("dataList", childlistvo);
					listMonth.add(data);
				}
			}
		}
		
		return ResultManager.createDataResult(listMonth, listMonth.size());
	}

	/**
	 * 获取交易记录详情
	 * @param uuid
	 * @param billid
	 * @return
	 */
	@RequestMapping(value = "/getBillInfo", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="账单编号", required = true)
	@ResponseBody
	public Result getBillInfo(HttpServletRequest request, String uuid){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		
		QcbEmployeeHistory qcpr = this.qcbEmployeeHistoryService.get(uuid);
		if(qcpr == null){
			return ResultManager.createFailResult("福利金信息不存在！");
		}
		
		if(!qcpr.getQcbEmployee().equals(employee.getUuid())) {
			return ResultManager.createFailResult("交易记录信息错误！");
		}
		
		QcbEmployeeHistoryVO vo = new QcbEmployeeHistoryVO(qcpr);
		
		if(QcbEmployeeHistoryStatus.FAIL.equals(vo.getStatus())){
			if(!Utlity.checkStringNull(qcpr.getOrderId())){
				QcbOrderinfoByThirdparty order = this.qcbOrderinfoByThirdpartyService.get(qcpr.getOrderId());
				if(order != null){
					vo.setRemark(order.getMessage() == null ? "交易失败" : order.getMessage());
				}
			}
		}
		
		if(qcpr.getBankcard() != null){
			QcbEmployeeBankcard qeb = this.qcbEmployeeBankcardService.get(qcpr.getBankcard());
			if(qeb != null && qeb.getBank() != null){
				Bank bank = this.bankService.get(qeb.getBank());
				if(bank != null){
					vo.setBankName(bank.getName());
					if(QcbEmployeeBankcardStatus.NORMAL.equals(qeb.getStatus())){
						vo.setBankcard(qeb.getBindingBankCard().substring(qeb.getBindingBankCard().length()-4));
					}else{
						String[] strs = qeb.getBindingBankCard().split("_#");
						if(strs.length > 0){
							vo.setBankcard(strs[0].substring(strs[0].length() - 4));
						}
					}
				}
			}
		}
		
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 根据条件获取企财宝员工拥有的优惠券列表
	 * @param uuid
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param price base64加密
	 * @param status use-未使用 history-已使用+已过期
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/couponList", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "status", message="优惠券状态", type = DataType.STRING, required = true)
	@ActionParam(key = "price", message="买入金额", type = DataType.STRING)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result couponList(HttpServletRequest request, Integer pageNum, Integer pageSize, String name, String status, String price, String sorts){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", employee.getUuid());
		inputParams.put("name", name);
		if(!Utlity.checkStringNull(status)){
			if(!"all".equals(status)){
				if("use".equals(status)){
					inputParams.put("status", "'unuse'");
				} else if ("history".equals(status)) {
					inputParams.put("status", "'used','expired'");
				} else if (QcbEmployeeCouponStatus.USED.equals(status) || QcbEmployeeCouponStatus.DELETED.equals(status) || QcbEmployeeCouponStatus.EXPIRED.equals(status)
						 || QcbEmployeeCouponStatus.UNUSE.equals(status) || QcbEmployeeCouponStatus.NOTACTIVE.equals(status)) {
					inputParams.put("status", "'"+status+"'");
				} else {
					return ResultManager.createFailResult("优惠券状态错误！");
				}
			}
		}
		
		if(!Utlity.checkStringNull(price)){
			price = Base64Util.getFromBase64(price);
			if (!Utlity.isPositiveCurrency4Web(price)) {
				return ResultManager.createFailResult("买入金额输入错误!");
			}
			BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
			BigDecimal paydivide = pay.divide(BigDecimal.valueOf(100));
			inputParams.put("minInvestAccount", paydivide.toString());
		}
		
		List<Entity> list = this.qcbEmployeeCouponService.getListForPage(inputParams, pageNum, pageSize, sorts, QcbEmployeeCoupon.class);
		List<QcbEmployeeCouponVO> listVO = new ArrayList<QcbEmployeeCouponVO>();
		if( list != null && !list.isEmpty()){
			for(Entity entity : list){
				QcbEmployeeCoupon qec = (QcbEmployeeCoupon)entity;
				QcbEmployeeCouponVO qecvo = new QcbEmployeeCouponVO(qec);
				Coupon coupon = this.couponService.get(qec.getCoupon());
				qecvo.setCouponName(coupon.getCouponName());
				qecvo.setCouponPrice(coupon.getCouponPrice());
				qecvo.setCouponType(coupon.getCouponType());
				qecvo.setMinInvestAccount(coupon.getMinInvestAccount());
				listVO.add(qecvo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, listVO.size());
	}
	
	/**
	 * 获取企财宝员工持仓列表
	 * @param uuid
	 * @param stage 交易中-transaction
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ActionParam(key = "stage", type = DataType.STRING, message="持仓阶段", required = true)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result getList(HttpServletRequest request, String stage, Integer pageNum, Integer pageSize, String sorts){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", employee.getUuid());
		inputParams.put("stage", stage);
		List<Entity> list = this.qcbEmployeeProductBuyService.getListForPage(inputParams, pageNum, pageSize, null, QcbEmployeeProductBuy.class);
		
		List<QcbEmployeeProductBuyVO> listVO = new ArrayList<QcbEmployeeProductBuyVO>();
		
		if(list != null && list.size() > 0){
			for(Entity entity : list){
				QcbEmployeeProductBuy qepb = (QcbEmployeeProductBuy) entity;
				QcbEmployeeProductBuyVO qepbvo = new QcbEmployeeProductBuyVO(qepb);
				BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(qepb.getProduct());
				if(bfpp == null){
					return ResultManager.createFailResult("银行理财产品信息不存在！");
				}
				Bank bank = this.bankService.get(bfpp.getCustodian());
				if(bank == null){
					return ResultManager.createFailResult("银行信息不存在！");
				}
				qepbvo.setBankName(bank.getShortName());
				Resource iconColor = this.resourceService.get(bank.getIconColor());
				if(iconColor != null){
					qepbvo.setIconColorUrl(iconColor.getUrl());
				} else {
					qepbvo.setIconColorUrl("");
				}
				
				qepbvo.setProductName(bfpp.getShortname());
				qepbvo.setProductScode(bfpp.getScode());
				
				qepbvo.setMaturityDate(Utlity.timeSpanToDateString(bfpp.getMaturityDate()));
				qepbvo.setValueDate(Utlity.timeSpanToDateString(bfpp.getValueDate()));
				if(bfpp.getTargetAnnualizedReturnRate() != null){
					qepbvo.setTargetAnnualizedReturnRate(bfpp.getTargetAnnualizedReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 
				}else{
					qepbvo.setTargetAnnualizedReturnRate("0.00");
				}
				qepbvo.setRealReturnRate(bfpp.getRealReturnRate() == null ? "" : bfpp.getRealReturnRate().toString());
				qepbvo.setRealReturnRateCN(bfpp.getRealReturnRate() == null ? "" : bfpp.getRealReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				
				//计算预计到账时间--当前规则是到期日时间延后一天
				if(bfpp.getMaturityDate() != null){
					long oneday = 24*60*60*1000;
					Timestamp returntime = new Timestamp(bfpp.getMaturityDate().getTime()+oneday);
					qepbvo.setIncomeDate(Utlity.timeSpanToDateString(returntime));
				} else {
					qepbvo.setIncomeDate("--");
				}
				
				//20171220增加爱优惠券使用标识显示参数
				//需要查询是否使用过优惠券
				inputParams.clear();
				inputParams.put("qcbEmployeeProductBuy", qepb.getUuid());
				List<Entity> listCoupon = this.qcbEmployeeCouponHistoryService.getListForPage(inputParams, -1, -1, null, QcbEmployeeCouponHistoryVO.class);
				if(listCoupon != null && !listCoupon.isEmpty()){
					for(Entity e : listCoupon){
						QcbEmployeeCouponHistoryVO qechvo = (QcbEmployeeCouponHistoryVO)e;
						if(CouponType.CASH.equals(qechvo.getCouponType())){
							qepbvo.setFlagCashCoupon(true);
						} else if (CouponType.INTERESTS.equals(qechvo.getCouponType())) {
							qepbvo.setFlagInterestsCoupon(true);
						}
					}
				}
				
				listVO.add(qepbvo);
			}
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, listVO.size());
	}
	
	/**
	 * 获取用户持仓详情
	 * @param financial
	 * @return
	 */
	@RequestMapping(value = "/getInfo", method = RequestMethod.GET)
	@ActionParam(key = "financial", type = DataType.STRING, message="持仓记录编号", required = true)
	@ResponseBody
	public Result getInfo(HttpServletRequest request, String financial){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qcbEmployee = (QcbEmployee)request.getAttribute("employee");
		
		QcbEmployeeProductBuy qepb = this.qcbEmployeeProductBuyService.get(financial);
		if(qepb == null){
			return ResultManager.createFailResult("信息不存在");
		}
		
		if(!qcbEmployee.getUuid().equals(qepb.getQcbEmployee())){
			return ResultManager.createFailResult("持仓记录编号");
		}
		
		QcbEmployeeProductBuyDetailsVO qepbvo = new QcbEmployeeProductBuyDetailsVO(qepb);
		BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(qepb.getProduct());
		if(bfpp == null){
			return ResultManager.createFailResult("银行理财产品信息不存在！");
		}
		Bank bank = this.bankService.get(bfpp.getCustodian());
		if(bank == null){
			return ResultManager.createFailResult("银行信息不存在！");
		}
		qepbvo.setBankName(bank.getShortName());
		qepbvo.setProductName(bfpp.getShortname());
		qepbvo.setProductScode(bfpp.getScode());
		qepbvo.setFlagBuy(bfpp.getFlagBuy());
		if(bfpp.getTargetAnnualizedReturnRate() != null){
			qepbvo.setTargetAnnualizedReturnRate(bfpp.getTargetAnnualizedReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 
		}else{
			qepbvo.setTargetAnnualizedReturnRate("0.00");
		}
		qepbvo.setTerm(bfpp.getTerm()+"");
		
		qepbvo.setMaturityDate(Utlity.timeSpanToDateString(bfpp.getMaturityDate()));
		qepbvo.setValueDate(Utlity.timeSpanToDateString(bfpp.getValueDate()));
		qepbvo.setRealReturnRate(bfpp.getRealReturnRate() == null ? "" : bfpp.getRealReturnRate().toString());
		qepbvo.setRealReturnRateCN(bfpp.getRealReturnRate() == null ? "" : bfpp.getRealReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		
		//PDF
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", qcbEmployee.getUuid());
		inputParams.put("records", qepb.getProduct());
		inputParams.put("type", QcbEmployeeProductBuyAgreementType.BANKPRODUCT);
		List<Entity> list = this.qcbEmployeeProductBuyAgreementService.getListForPage(inputParams, -1, -1, null, QcbEmployeeProductBuyAgreement.class);
		if (list != null && list.size() > 0) {
			QcbEmployeeProductBuyAgreement ipba = (QcbEmployeeProductBuyAgreement) list.get(0);
			qepbvo.setAgreementName("《"+ipba.getName()+"》");
			qepbvo.setAgreementUrl(ipba.getUrl());
		} else {
			qepbvo.setAgreementName("生成中...");
			qepbvo.setAgreementUrl("");
		}
		//计算预计到账时间--当前规则是到期日时间延后一天
		if(bfpp.getMaturityDate() != null){
			long oneday = 24*60*60*1000;
			Timestamp returntime = new Timestamp(bfpp.getMaturityDate().getTime()+oneday);
			qepbvo.setIncomeDate(Utlity.timeSpanToDateString(returntime));
		} else {
			qepbvo.setIncomeDate("--");
		}
		
		//查询交易记录列表 若使用优惠券则增加优惠券使用信息
		inputParams.clear();
		inputParams.put("qcbEmployee", qcbEmployee.getUuid());
		inputParams.put("product", qepb.getProduct());
		inputParams.put("type", QcbEmployeeHistoryType.BUY);
		List<Entity> listIah = this.qcbEmployeeHistoryService.getListForPage(inputParams, -1, -1, "createtime asc", QcbEmployeeHistory.class);
		List<QcbEmployeeProductBuyHistoryVO> listIpbhvo = new ArrayList<QcbEmployeeProductBuyHistoryVO>();
		if(listIah != null && !listIah.isEmpty()){
			for(Entity e : listIah){
				QcbEmployeeHistory iah = (QcbEmployeeHistory)e;
				QcbEmployeeProductBuyHistoryVO qepbhvo = new QcbEmployeeProductBuyHistoryVO(iah);
				inputParams.clear();
				inputParams.put("qcbEmployeeProductBuy", qepb.getUuid());
				inputParams.put("qcbEmployeeHistory", iah.getUuid());
				List<Entity> listCoupon = this.qcbEmployeeCouponHistoryService.getListForPage(inputParams, -1, -1, null, QcbEmployeeCouponHistoryVO.class);
				if(listCoupon != null && !listCoupon.isEmpty()){
					QcbEmployeeCouponHistoryVO qechvo = (QcbEmployeeCouponHistoryVO)listCoupon.get(0);
					qepbhvo.setCoupon(qechvo.getCoupon());
					qepbhvo.setCouponName(qechvo.getCouponName());
					qepbhvo.setCouponPrice(qechvo.getCouponPrice());
					qepbhvo.setCouponType(qechvo.getCouponType());
				}
				listIpbhvo.add(qepbhvo);
			}
		}
		qepbvo.setAccountHistory(listIpbhvo);
		return ResultManager.createDataResult(qepbvo);
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Result logout(HttpServletRequest request) {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		employee.setOpenid(null);
		this.qcbEmployeeService.update(employee);
		
		return ResultManager.createSuccessResult("退出登录成功！");
	}
}
