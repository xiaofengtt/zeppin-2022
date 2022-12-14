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
	 * ?????????????????????????????????????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/getNotActiveList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="????????????", required = true)
	@ResponseBody
	public Result getNotActiveList(String uuid){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("????????????????????????");
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
				iivo.setCouponName("????????????");
				iivo.setCouponType("redPacket");
				listRedVO.add(iivo);
			}
		}
		result.put("redPacket", listRedVO);
		return ResultManager.createDataResult(result);
	}
	
	/**
	 * ????????????????????????????????????????????????
	 * @param uuid
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param price base64??????
	 * @param status use-????????? history-?????????+?????????
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="????????????", required = true)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "status", message="???????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "price", message="????????????", type = DataType.STRING)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result getList(String uuid, Integer pageNum, Integer pageSize, String name, String status, String price, String sorts){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("????????????????????????");
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
					return ResultManager.createFailResult("?????????????????????");
				}
			}
		}
		
		if(!Utlity.checkStringNull(price)){
			price = Base64Util.getFromBase64(price);
			if (!Utlity.isPositiveCurrency4Web(price)) {
				return ResultManager.createFailResult("????????????????????????!");
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
	 * ??????????????????????????????
	 * @param uuid
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/getUseList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="????????????", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message="????????????", required = true)
	@ResponseBody
	public Result getUseList(String uuid, String price){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("????????????????????????!");
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
					if(paydivide.compareTo(coupon.getMinInvestAccount()) == -1){//?????????????????????????????????
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
	 * ??????????????????????????????
	 * @param uuid
	 * @param coupons
	 * @param redPacket
	 * @param flagShare
	 * @return
	 */
	@RequestMapping(value = "/activate", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="????????????", required = true)
	@ActionParam(key = "coupons", type = DataType.STRING_ARRAY, message="???????????????")
	@ActionParam(key = "flagShare", type = DataType.BOOLEAN, message = "????????????")
	@ActionParam(key = "redPackets", type = DataType.STRING_ARRAY, message = "????????????")
	@ResponseBody
	public Result activate(String uuid, String[] coupons, String[] redPackets, Boolean flagShare){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		List<InvestorCoupon> list = new ArrayList<InvestorCoupon>();
		for(String couopn : coupons){
			InvestorCoupon ic = this.investorCouponService.get(couopn);
			if(ic == null){
				return ResultManager.createFailResult("??????????????????");
			}
			ic.setStatus(InvestorCouponStatus.UNUSE);
			list.add(ic);
		}
		List<InvestorRedPacket> listRed = new ArrayList<InvestorRedPacket>();
		if(redPackets != null){
			for(String red : redPackets){
				InvestorRedPacket irp = this.investorRedPacketService.get(red);
				if(irp == null){
					return ResultManager.createFailResult("?????????????????????");
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
			return ResultManager.createFailResult("????????????!");
		}
		
		return ResultManager.createSuccessResult("????????????!");
	}
}
