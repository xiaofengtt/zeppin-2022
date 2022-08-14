package cn.zeppin.product.ntb.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.ICouponService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorCouponService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorInformationService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorRedPacketService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Coupon;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon.InvestorCouponStatus;
import cn.zeppin.product.ntb.core.entity.InvestorRedPacket;
import cn.zeppin.product.ntb.core.entity.InvestorRedPacket.InvestorRedPacketStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.web.vo.InvestorCouponVO;
import cn.zeppin.product.ntb.web.vo.InvestorRedPacketVO;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.Utlity;

@Controller
@RequestMapping(value = "/web/coupon")
public class UserCouponController extends BaseController{
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IInvestorInformationService investorInformationService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private IInvestorCouponService investorCouponService;
	
	@Autowired
	private IInvestorRedPacketService investorRedPacketService;
	
	@Autowired
	private IInvestorAccountHistoryService investorAccountHistoryService;
	
	
	/**
	 * 获取用户未激活的优惠券（外加未领取的现金红包）
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/getNotActiveList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ResponseBody
	public Result getNotActiveList(String uuid){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("investor", uuid);
		inputParams.put("status", "'"+InvestorCouponStatus.NOTACTIVE+"'");
		List<Entity> list = this.investorCouponService.getListForPage(inputParams, -1, -1, null, InvestorCoupon.class);
		List<InvestorCouponVO> listVO = new ArrayList<InvestorCouponVO>();
		if( list != null && !list.isEmpty()){
			for(Entity entity : list){
				InvestorCoupon ii = (InvestorCoupon)entity;
				InvestorCouponVO iivo = new InvestorCouponVO(ii);
				Coupon coupon = this.couponService.get(ii.getCoupon());
				iivo.setCouponName(coupon.getCouponName());
				iivo.setCouponPrice(coupon.getCouponPrice());
				iivo.setCouponType(coupon.getCouponType()); 
				iivo.setMinInvestAccount(coupon.getMinInvestAccount());
				listVO.add(iivo);
			}
		}
		result.put("coupon", listVO);
		inputParams.clear();
		inputParams.put("investor", uuid);
		inputParams.put("status", "'"+InvestorRedPacketStatus.NOTACTIVE+"'");
		List<Entity> listRed = this.investorRedPacketService.getListForPage(inputParams, -1, -1, null, InvestorRedPacket.class);
		List<InvestorRedPacketVO> listRedVO = new ArrayList<InvestorRedPacketVO>();
		if( listRed != null && !listRed.isEmpty()){
			for(Entity entity : listRed){
				InvestorRedPacket ii = (InvestorRedPacket)entity;
				InvestorRedPacketVO iivo = new InvestorRedPacketVO(ii);
				iivo.setCouponName("现金红包");
				iivo.setCouponType("redPacket");
				listRedVO.add(iivo);
			}
		}
		result.put("redPacket", listRedVO);
		return ResultManager.createDataResult(result);
	}
	
	/**
	 * 根据条件获取用户拥有的优惠券列表
	 * @param uuid
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param price base64加密
	 * @param status use-未使用 history-已使用+已过期
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "status", message="优惠券状态", type = DataType.STRING, required = true)
	@ActionParam(key = "price", message="买入金额", type = DataType.STRING)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result getList(String uuid, Integer pageNum, Integer pageSize, String name, String status, String price, String sorts){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("investor", uuid);
		inputParams.put("name", name);
		if(!Utlity.checkStringNull(status)){
			if(!"all".equals(status)){
				if("use".equals(status)){
					inputParams.put("status", "'unuse'");
				} else if ("history".equals(status)) {
					inputParams.put("status", "'used','expired'");
				} else if (InvestorCouponStatus.USED.equals(status) || InvestorCouponStatus.DELETED.equals(status) || InvestorCouponStatus.EXPIRED.equals(status)
						 || InvestorCouponStatus.UNUSE.equals(status) || InvestorCouponStatus.NOTACTIVE.equals(status)) {
					inputParams.put("status", "'"+status+"'");
				} else {
					return ResultManager.createFailResult("网络连接错误！");
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
		
		List<Entity> list = this.investorCouponService.getListForPage(inputParams, pageNum, pageSize, sorts, InvestorCoupon.class);
		List<InvestorCouponVO> listVO = new ArrayList<InvestorCouponVO>();
		if( list != null && !list.isEmpty()){
			for(Entity entity : list){
				InvestorCoupon ii = (InvestorCoupon)entity;
				InvestorCouponVO iivo = new InvestorCouponVO(ii);
				Coupon coupon = this.couponService.get(ii.getCoupon());
				iivo.setCouponName(coupon.getCouponName());
				iivo.setCouponPrice(coupon.getCouponPrice());
				iivo.setCouponType(coupon.getCouponType());
				iivo.setMinInvestAccount(coupon.getMinInvestAccount());
				listVO.add(iivo);
			}
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, listVO.size());
	}
	
	/**
	 * 获取用户可用的优惠券
	 * @param uuid
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/getUseList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message="买入金额", required = true)
	@ResponseBody
	public Result getUseList(String uuid, String price){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("买入金额输入错误!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		BigDecimal paydivide = pay.divide(BigDecimal.valueOf(100));
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("investor", uuid);
		inputParams.put("status", "'unuse'");
		List<Entity> list = this.investorCouponService.getListForPage(inputParams, -1, -1, null, InvestorCoupon.class);
		List<InvestorCouponVO> listVO = new ArrayList<InvestorCouponVO>();
		if( list != null && !list.isEmpty()){
			for(Entity entity : list){
				InvestorCoupon ii = (InvestorCoupon)entity;
				InvestorCouponVO iivo = new InvestorCouponVO(ii);
				Coupon coupon = this.couponService.get(ii.getCoupon());
				if(coupon.getMinInvestAccount() != null){
					if(paydivide.compareTo(coupon.getMinInvestAccount()) == -1){//过滤掉不能使用的优惠券
						continue;
					}
				}
				iivo.setCouponName(coupon.getCouponName());
				iivo.setCouponPrice(coupon.getCouponPrice());
				iivo.setCouponType(coupon.getCouponType());
				iivo.setMinInvestAccount(coupon.getMinInvestAccount());
				listVO.add(iivo);
			}
		}
		
		return ResultManager.createDataResult(listVO, -1, -1, listVO.size());
	}
	
	/**
	 * 激活优惠券和现金红包
	 * @param uuid
	 * @param coupons
	 * @param redPacket
	 * @param flagShare
	 * @return
	 */
	@RequestMapping(value = "/activate", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "coupons", type = DataType.STRING_ARRAY, message="优惠券编码")
	@ActionParam(key = "flagShare", type = DataType.BOOLEAN, message = "是否分享")
	@ActionParam(key = "redPackets", type = DataType.STRING_ARRAY, message = "红包编号")
	@ResponseBody
	public Result activate(String uuid, String[] coupons, String[] redPackets, Boolean flagShare){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		
		List<InvestorCoupon> list = new ArrayList<InvestorCoupon>();
		for(String couopn : coupons){
			InvestorCoupon ic = this.investorCouponService.get(couopn);
			if(ic == null){
				return ResultManager.createFailResult("优惠券异常！");
			}
			ic.setStatus(InvestorCouponStatus.UNUSE);
			list.add(ic);
		}
		List<InvestorRedPacket> listRed = new ArrayList<InvestorRedPacket>();
		if(redPackets != null){
			for(String red : redPackets){
				InvestorRedPacket irp = this.investorRedPacketService.get(red);
				if(irp == null){
					return ResultManager.createFailResult("现金红包异常！");
				}
				if(flagShare){
					irp.setPirce(irp.getPirce().add(BigDecimal.valueOf(2)));
				}
				irp.setStatus(InvestorRedPacketStatus.USED);
				listRed.add(irp);
			}
		}
		
		try {
			this.investorAccountHistoryService.insertActive(list, listRed, investor);
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常!");
		}
		
		return ResultManager.createSuccessResult("领取成功!");
	}
}
