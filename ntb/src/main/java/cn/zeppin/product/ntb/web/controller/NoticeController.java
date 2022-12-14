package cn.zeppin.product.ntb.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorBankcardService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyAgreementService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.backadmin.service.api.IOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.backadmin.service.api.IRealpalNoticeInfoService;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty.OrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.RealpalNoticeInfo.RealpalNoticeInfoPayType;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.WxPayUtlity;
import cn.zeppin.product.utility.XMLUtils;
import cn.zeppin.product.utility.chanpay.ChanPayUtil;
import cn.zeppin.product.utility.chanpay.RSA;
import cn.zeppin.product.utility.fuqianla.FuqianlaUtlity;
import cn.zeppin.product.utility.fuqianla.XmlUtil;
import cn.zeppin.product.utility.fuqianla.json.SinglePaymentJson;
import cn.zeppin.product.utility.reapal.Md5Utils;
import cn.zeppin.product.utility.reapal.ReapalUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/web/notice")
public class NoticeController extends BaseController{
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IOrderinfoByThirdpartyService orderinfoByThirdpartyService;
	
	@Autowired
	private IInvestorAccountHistoryService investorAccountHistoryService;
	
	@Autowired
	private IInvestorBankcardService investorBankcardService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IInvestorProductBuyService investorProductBuyService;
	
	@Autowired
	private IInvestorProductBuyAgreementService investorProductBuyAgreementService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private ICompanyAccountHistoryService companyAccountHistoryService;
	
	@Autowired
	private IQcbEmployeeHistoryService qcbEmployeeHistoryService;
	
	@Autowired
	private IQcbCompanyAccountHistoryService qcbCompanyAccountHistoryService;
	
	@Autowired
	private IRealpalNoticeInfoService realpalNoticeInfoService;
	
	@RequestMapping(value = "/notice", method = RequestMethod.POST)
	@ResponseBody
	public void notice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));  
        String line = null;  
        StringBuilder sb = new StringBuilder();  
        while((line = br.readLine()) != null){  
            sb.append(line);  
        }  
        //sb??????????????????xml  
        String notityXml = sb.toString();  
        System.out.println("?????????????????????" + notityXml);  
        Map<String, Object> map = XMLUtils.doXMLParse(notityXml);
        System.out.println(map);
        String returnCode = (String) map.get("return_code");
        String sign = map.get("sign").toString();
        map = WxPayUtlity.paraFilter(map);
        String linkString = WxPayUtlity.createLinkString(map);
        System.out.println(linkString);
		Map<String, Object> result = new HashMap<String, Object>();
		if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(returnCode)){
			try {
				if(WxPayUtlity.verify(linkString, sign, Utlity.WX_KEY, "utf-8")){//??????
					this.investorAccountHistoryService.insertWechartNotice(map);
					result.put("return_code", "SUCCESS");
					result.put("return_msg", result.get("message") == null ? "" : result.get("message").toString());
					response.getWriter().write(XMLUtils.getRequestXml(result));
					return;
		        } else {
					System.out.println("??????????????????");
					result.put("return_code", "FAIL");
					result.put("return_msg", "????????????");
					try {
						response.getWriter().write(XMLUtils.getRequestXml(result));
						return;
					} catch (IOException e1) {
						super.flushAll();
						e1.printStackTrace();
					}
				}
			} catch (TransactionException te) {
				result.put("return_code", "FAIL");
				result.put("return_msg", te.getMessage());
				super.flushAll();
				try {
					response.getWriter().write(XMLUtils.getRequestXml(result));
					return;
				} catch (IOException e1) {
					super.flushAll();
					e1.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				result.put("return_code", "FAIL");
				result.put("return_msg", "????????????");
				try {
					response.getWriter().write(XMLUtils.getRequestXml(result));
					return;
				} catch (IOException e1) {
					super.flushAll();
					e1.printStackTrace();
				}
			}
		} else {
			result.put("return_code", "FAIL");
			result.put("return_msg", "??????????????????");
			try {
				response.getWriter().write(XMLUtils.getRequestXml(result));
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
				super.flushAll();
			}
		}

	}
	
	@RequestMapping(value = "/buyNotice", method = RequestMethod.POST)
	@ResponseBody
	public void buyNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));  
        String line = null;  
        StringBuilder sb = new StringBuilder();  
        while((line = br.readLine()) != null){  
            sb.append(line);  
        }  
        //sb??????????????????xml  
        String notityXml = sb.toString();  
        System.out.println("?????????????????????" + notityXml);  
        Map<String, Object> map = XMLUtils.doXMLParse(notityXml);
        System.out.println(map);
        String returnCode = (String) map.get("return_code");
        String sign = map.get("sign").toString();
        map = WxPayUtlity.paraFilter(map);
        String linkString = WxPayUtlity.createLinkString(map);
        System.out.println(linkString);
		Map<String, Object> result = new HashMap<String, Object>();
		if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(returnCode)){
			try {
				if(WxPayUtlity.verify(linkString, sign, Utlity.WX_KEY, "utf-8")){//??????
					HashMap<String, Object> resultMap = this.investorAccountHistoryService.insertWechartNotice4Buy(map);
					if((Boolean)resultMap.get("result")){
						result.put("return_code", "SUCCESS");
						result.put("return_msg", result.get("message") == null ? "???????????????" : result.get("message").toString());
						
					} else {
						result.put("return_code", "FAIL");
						result.put("return_msg", result.get("message") == null ? "???????????????" : result.get("message").toString());
					}
					response.getWriter().write(XMLUtils.getRequestXml(result));
					return;
		        } else {
					System.out.println("??????????????????");
					result.put("return_code", "FAIL");
					result.put("return_msg", "????????????");
					try {
						response.getWriter().write(XMLUtils.getRequestXml(result));
						return;
					} catch (IOException e1) {
						e1.printStackTrace();
						super.flushAll();
					}
				}
			} catch (TransactionException te) {
				result.put("return_code", "FAIL");
				result.put("return_msg", te.getMessage());
				try {
					response.getWriter().write(XMLUtils.getRequestXml(result));
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
					super.flushAll();
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				result.put("return_code", "FAIL");
				result.put("return_msg", "????????????????????????");
				try {
					response.getWriter().write(XMLUtils.getRequestXml(result));
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
					super.flushAll();
				}
			}
		} else {
			result.put("return_code", "FAIL");
			result.put("return_msg", "??????????????????");
			try {
				response.getWriter().write(XMLUtils.getRequestXml(result));
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
				super.flushAll();
			}
		}

	}
	
	/**
	 * ????????????????????????????????????????????????
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/chanpinNotice", method = RequestMethod.POST)
	@ResponseBody
	public void chanpinNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> mapStr = request.getParameterMap();
        System.out.println(mapStr);
        Set<Entry<String, String[]>> keSet=mapStr.entrySet();
        for(Iterator<Entry<String, String[]>> itr=keSet.iterator();itr.hasNext();){
            Map.Entry me=(Map.Entry)itr.next();
            Object ok=me.getKey();
            Object ov=me.getValue();
            String[] value=new String[1];
            if(ov instanceof String[]){
                value=(String[])ov;
            }else{
                value[0]=ov.toString();
            }
            map.put((String) ok, value[0]);
          }
        System.out.println(map);
        String inputCharset = map.get("_input_charset");
        String sign = map.get("sign").toString();
        map = ChanPayUtil.paraFilter(map);
        String linkString = ChanPayUtil.createLinkString(map,true);
        String signStr = ChanPayUtil.buildRequestByRSA(map, ChanPayUtil.MERCHANT_PRIVATE_KEY, inputCharset);
        System.out.println(linkString);
        System.out.println(signStr);
        if(!signStr.equals(sign)){//??????
        	try {
    			response.getWriter().write("fail");
    			return;
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
        }
        
//		Map<String, Object> result = new HashMap<String, Object>();
//		String returnCode = map.get("auth_status");
		try {
			response.getWriter().write("success");
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * ?????????????????????????????????????????????--?????????
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/chanpinBuyNotice", method = RequestMethod.POST)
	@ResponseBody
	public void chanpinBuyNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> mapStr = request.getParameterMap();
        Set<Entry<String, String[]>> keSet=mapStr.entrySet();
        for(Iterator<Entry<String, String[]>> itr=keSet.iterator();itr.hasNext();){
            Map.Entry me=(Map.Entry)itr.next();
            Object ok=me.getKey();
            Object ov=me.getValue();
            String[] value=new String[1];
            if(ov instanceof String[]){
                value=(String[])ov;
            }else{
                value[0]=ov.toString();
            }
            map.put((String) ok, value[0]);
          }
        System.out.println(map);
        String sign = map.get("sign").toString();
        map = ChanPayUtil.paraFilter(map);
        System.out.println(map);
        if(map.containsKey("extension")){
        	String value = map.get("extension");
        	if(value.indexOf("???") > -1){
        		value = value.replaceAll("???", "{");
        	}
        	if(value.indexOf("???") > -1){
        		value = value.replaceAll("???", "}");
        	}
        	map.put("extension", value);
        }
//        map.put("extension", "{}");
        String linkString = ChanPayUtil.createLinkString(map,false);
//        String signStr = RSA.sign(linkString, ChanPayUtil.MERCHANT_PRIVATE_KEY, inputCharset);
        //??????
		try {
			Boolean isTrue = RSA.verify(linkString, sign, ChanPayUtil.MERCHANT_PUBLIC_KEY,ChanPayUtil.charset);
			if(!isTrue){
	        	try {
	        		System.out.println("fail");
	    			response.getWriter().write("fail");
	    			return;
	    		} catch (IOException e1) {
	    			e1.printStackTrace();
	    			super.flushAll();
	    		}
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		}
		
		try {
			System.out.println("success");
			HashMap<String, Object> resultMap = this.investorAccountHistoryService.insertChanPayNotice4Buy(map);
			if((Boolean)resultMap.get("result")){
				response.getWriter().write("success");
				
			} else {
				response.getWriter().write("fail");
			}
			return;
		} catch (TransactionException e) {
			e.printStackTrace();
			response.getWriter().write("fail");
			super.flushAll();
		} catch (ParseException e) {
			e.printStackTrace();
			response.getWriter().write("fail");
			super.flushAll();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.getWriter().write("fail");
			super.flushAll();
		} catch (IOException e1) {
			e1.printStackTrace();
			super.flushAll();
		}
	}
	
	/**
	 * ?????????????????????????????????????????????--?????????
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/chanpinRechargeNotice", method = RequestMethod.POST)
	@ResponseBody
	public void chanpinRechargeNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> mapStr = request.getParameterMap();
        Set<Entry<String, String[]>> keSet=mapStr.entrySet();
        for(Iterator<Entry<String, String[]>> itr=keSet.iterator();itr.hasNext();){
            Map.Entry me=(Map.Entry)itr.next();
            Object ok=me.getKey();
            Object ov=me.getValue();
            String[] value=new String[1];
            if(ov instanceof String[]){
                value=(String[])ov;
            }else{
                value[0]=ov.toString();
            }
            map.put((String) ok, value[0]);
          }
        System.out.println(map);
        String sign = map.get("sign").toString();
        map = ChanPayUtil.paraFilter(map);
        System.out.println(map);
        if(map.containsKey("extension")){
        	String value = map.get("extension");
        	if(value.indexOf("???") > -1){
        		value = value.replaceAll("???", "{");
        	}
        	if(value.indexOf("???") > -1){
        		value = value.replaceAll("???", "}");
        	}
        	map.put("extension", value);
        }
//        map.put("extension", "{}");
        String linkString = ChanPayUtil.createLinkString(map,false);
//        String signStr = RSA.sign(linkString, ChanPayUtil.MERCHANT_PRIVATE_KEY, inputCharset);
        //??????
		try {
			Boolean isTrue = RSA.verify(linkString, sign, ChanPayUtil.MERCHANT_PUBLIC_KEY,ChanPayUtil.charset);
			if(!isTrue){
	        	try {
	        		System.out.println("fail");
	    			response.getWriter().write("fail");
	    			return;
	    		} catch (IOException e1) {
	    			e1.printStackTrace();
	    			super.flushAll();
	    		}
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		}
		
		try {
			System.out.println("success");
			HashMap<String, Object> resultMap = this.investorAccountHistoryService.insertChanPayNotice4Recharge(map);
			if((Boolean)resultMap.get("result")){
				response.getWriter().write("success");
				
			} else {
				response.getWriter().write("fail");
			}
			return;
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (IOException e1) {
			e1.printStackTrace();
			super.flushAll();
		}
	}
	
	/**
	 * ??????????????????????????????????????????????????????
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/chanpinPayNotice", method = RequestMethod.POST)
	@ResponseBody
	public void chanpinPayNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> mapStr = request.getParameterMap();
        Set<Entry<String, String[]>> keSet=mapStr.entrySet();
        for(Iterator<Entry<String, String[]>> itr=keSet.iterator();itr.hasNext();){
            Map.Entry me=(Map.Entry)itr.next();
            Object ok=me.getKey();
            Object ov=me.getValue();
            String[] value=new String[1];
            if(ov instanceof String[]){
                value=(String[])ov;
            }else{
                value[0]=ov.toString();
            }
            map.put((String) ok, value[0]);
          }
        System.out.println(map);
        String sign = map.get("sign").toString();
        map = ChanPayUtil.paraFilter(map);
        String linkString = ChanPayUtil.createLinkString(map,false);
//        String signStr = RSA.sign(linkString, ChanPayUtil.MERCHANT_PRIVATE_KEY, inputCharset);
        //??????
		try {
			Boolean isTrue = RSA.verify(linkString, sign, ChanPayUtil.MERCHANT_PUBLIC_KEY,ChanPayUtil.charset);
			if(!isTrue){
	        	try {
	        		System.out.println("fail");
	    			response.getWriter().write("fail");
	    			return;
	    		} catch (IOException e1) {
	    			e1.printStackTrace();
	    			super.flushAll();
	    		}
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		}
		
		try {
			System.out.println("success");
			HashMap<String, Object> resultMap = this.investorAccountHistoryService.insertChanPayNotice4Pay(map);
			if((Boolean)resultMap.get("result")){
				response.getWriter().write("success");
				
			} else {
				response.getWriter().write("fail");
			}
			return;
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (IOException e1) {
			e1.printStackTrace();
			super.flushAll();
		}
	}
	
	/**
	 * ?????????????????????????????????????????????--?????????
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/fuqianlaRechargeNotice", method = RequestMethod.POST)
	@ResponseBody
	public void fuqianlaRechargeNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> mapStr = request.getParameterMap();
        Set<Entry<String, String[]>> keSet=mapStr.entrySet();
        for(Iterator<Entry<String, String[]>> itr=keSet.iterator();itr.hasNext();){
            Map.Entry me=(Map.Entry)itr.next();
            Object ok=me.getKey();
            Object ov=me.getValue();
            String[] value=new String[1];
            if(ov instanceof String[]){
                value=(String[])ov;
            }else{
                value[0]=ov.toString();
            }
            map.put((String) ok, value[0]);
          }
        System.out.println(map);
        String sign = map.get("sign").toString();
        map = ChanPayUtil.paraFilter(map);
        System.out.println(map);
        if(map.containsKey("extension")){
        	String value = map.get("extension");
        	if(value.indexOf("???") > -1){
        		value = value.replaceAll("???", "{");
        	}
        	if(value.indexOf("???") > -1){
        		value = value.replaceAll("???", "}");
        	}
        	map.put("extension", value);
        }
//        map.put("extension", "{}");
        String linkString = ChanPayUtil.createLinkString(map,false);
//        String signStr = RSA.sign(linkString, ChanPayUtil.MERCHANT_PRIVATE_KEY, inputCharset);
        //??????
		try {
			Boolean isTrue = RSA.verify(linkString, sign, ChanPayUtil.MERCHANT_PUBLIC_KEY,ChanPayUtil.charset);
			if(!isTrue){
	        	try {
	        		System.out.println("fail");
	    			response.getWriter().write("fail");
	    			return;
	    		} catch (IOException e1) {
	    			e1.printStackTrace();
	    			super.flushAll();
	    		}
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		}
		
		try {
			System.out.println("success");
			HashMap<String, Object> resultMap = this.investorAccountHistoryService.insertChanPayNotice4Recharge(map);
			if((Boolean)resultMap.get("result")){
				response.getWriter().write("success");
				
			} else {
				response.getWriter().write("fail");
			}
			return;
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (IOException e1) {
			e1.printStackTrace();
			super.flushAll();
		}
	}
	
	/**
	 * ???????????????????????????????????????????????????
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/fuqianlaWithdrawNotice", method = RequestMethod.POST)
	@ResponseBody
	public void fuqianlaWithdrawNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));  
        String line = null;  
        StringBuilder sb = new StringBuilder();  
        while((line = br.readLine()) != null){  
            sb.append(line);  
        }  
        //sb?????????????????????xml  
        String notityXml = sb.toString();  
        System.out.println("?????????????????????" + notityXml); 
        JSONArray json = XmlUtil.parseXML(notityXml);
		JSONObject jsObt = json.getJSONObject(0);
		// ???????????????JSON??????
		SinglePaymentJson sps = (SinglePaymentJson) JSONObject.toJavaObject(
				jsObt, SinglePaymentJson.class);
		Map<String, Object> mapResult = JSONUtils.json2map(jsObt.toJSONString());
		String sign = sps.getSignInfo();
		mapResult = FuqianlaUtlity.paraFilter(mapResult);
        String linkString = FuqianlaUtlity.createLinkString(mapResult);
//        System.out.println(linkString);
        //??????
		try {
			Boolean isTrue = FuqianlaUtlity.verify(linkString, sign, FuqianlaUtlity.SEED,ChanPayUtil.charset);
			if(!isTrue){
	        	try {
	        		System.out.println("fail");
	    			response.getWriter().write("fail");
	    			return;
	    		} catch (IOException e1) {
	    			e1.printStackTrace();
	    			super.flushAll();
	    		}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		}
		
		try {
			System.out.println("success");
//			HashMap<String, Object> resultMap = this.investorAccountHistoryService.insertChanPayNotice4Pay(mapResult);
//			if((Boolean)resultMap.get("result")){
//				response.getWriter().write("success");
//				
//			} else {
//				response.getWriter().write("fail");
//			}
			return;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		}
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @param request
	 * @param encryptkey
	 * @param data
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/reapalWithdrawNotice", method = RequestMethod.POST)
	@ResponseBody
	public void reapalWithdrawNotice(HttpServletRequest request,String encryptkey, String data,HttpServletResponse response) throws Exception{
		String result = null;
        System.out.println("??????????????????encryptkey==========>" + encryptkey);
        System.out.println("??????????????????data==========>" + data);
        try {
        	result = ReapalUtil.pubkey(encryptkey,data);
        	System.out.println("??????????????????payback_result:"+result);
        	/*
        	 * ??????????????????result???????????????
        	 * */
        	Map<String, Object> map = JSONUtils.json2map(result);
        	System.out.println(map.get("data"));
//        	HashMap<String, Object> resultMap = this.investorAccountHistoryService.insertReapalNotice4Pay(map);
        	HashMap<String, Object> resultMap = this.realpalNoticeInfoService.insertReapalNotice4Pay(map, RealpalNoticeInfoPayType.TAKEOUT);
        	if((Boolean)resultMap.get("result")){
				response.getWriter().write("success");
				
			} else {
				response.getWriter().write("fail");
			}
			return;
        }  catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
            e.printStackTrace();
            super.flushAll();
            response.getWriter().write("fail");
        }
	}
	
	/**
	 * ???????????????????????????????????????????????????
	 * @param request
	 * @param encryptkey
	 * @param data
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/reapalQcbWithdrawNotice", method = RequestMethod.POST)
	@ResponseBody
	public void reapalQcbWithdrawNotice(HttpServletRequest request,String encryptkey, String data,HttpServletResponse response) throws Exception{
		String result = null;
        System.out.println("??????????????????encryptkey==========>" + encryptkey);
        System.out.println("??????????????????data==========>" + data);
        try {
        	result = ReapalUtil.pubkey(encryptkey,data);
        	System.out.println("??????????????????payback_result:"+result);
        	/*
        	 * ??????????????????result???????????????
        	 * */
        	Map<String, Object> map = JSONUtils.json2map(result);
        	System.out.println(map.get("data"));
//        	HashMap<String, Object> resultMap = this.qcbCompanyAccountHistoryService.insertReapalNotice4Pay(map);
        	HashMap<String, Object> resultMap = this.realpalNoticeInfoService.insertReapalNotice4Pay(map, RealpalNoticeInfoPayType.QCB_TAKEOUT);
        	if((Boolean)resultMap.get("result")){
				response.getWriter().write("success");
				
			} else {
				response.getWriter().write("fail");
			}
			return;
        }  catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
            e.printStackTrace();
            super.flushAll();
            response.getWriter().write("fail");
        }
	}
	
	/**
	 * ?????????????????????????????????????????????????????????
	 * @param request
	 * @param encryptkey
	 * @param data
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/reapalQcbEmployeeWithdrawNotice", method = RequestMethod.POST)
	@ResponseBody
	public void reapalQcbEmployeeWithdrawNotice(HttpServletRequest request,String encryptkey, String data,HttpServletResponse response) throws Exception{
		String result = null;
        System.out.println("??????????????????encryptkey==========>" + encryptkey);
        System.out.println("??????????????????data==========>" + data);
        try {
        	result = ReapalUtil.pubkey(encryptkey,data);
        	System.out.println("??????????????????payback_result:"+result);
        	/*
        	 * ??????????????????result???????????????
        	 * */
        	Map<String, Object> map = JSONUtils.json2map(result);
        	System.out.println(map.get("data"));
//        	HashMap<String, Object> resultMap = this.qcbEmployeeHistoryService.insertReapalNotice4Pay(map);
        	HashMap<String, Object> resultMap = this.realpalNoticeInfoService.insertReapalNotice4Pay(map, RealpalNoticeInfoPayType.EMP_TAKEOUT);
        	if((Boolean)resultMap.get("result")){
				response.getWriter().write("success");
				
			} else {
				response.getWriter().write("fail");
			}
			return;
        }  catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
            e.printStackTrace();
            super.flushAll();
            response.getWriter().write("fail");
        }
	}
	
	/**
	 * ?????????????????????????????????????????????????????????
	 * @param request
	 * @param encryptkey
	 * @param data
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/reapalQcbEmployeeRepaymentNotice", method = RequestMethod.POST)
	@ResponseBody
	public void reapalQcbEmployeeRepaymentNotice(HttpServletRequest request,String encryptkey, String data,HttpServletResponse response) throws Exception{
		String result = null;
        System.out.println("??????????????????encryptkey==========>" + encryptkey);
        System.out.println("??????????????????data==========>" + data);
        try {
        	result = ReapalUtil.pubkey(encryptkey,data);
        	System.out.println("??????????????????payback_result:"+result);
        	/*
        	 * ??????????????????result???????????????
        	 * */
        	Map<String, Object> map = JSONUtils.json2map(result);
        	System.out.println(map.get("data"));
//        	HashMap<String, Object> resultMap = this.qcbEmployeeHistoryService.insertReapalNotice4Pay(map);
        	HashMap<String, Object> resultMap = this.realpalNoticeInfoService.insertReapalNotice4Pay(map, RealpalNoticeInfoPayType.EMP_TAKEOUT);
        	if((Boolean)resultMap.get("result")){
				response.getWriter().write("success");
				
			} else {
				response.getWriter().write("fail");
			}
			return;
        }  catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
            e.printStackTrace();
            super.flushAll();
            response.getWriter().write("fail");
        }
	}
	
	/**
	 * ?????????????????????????????????????????????--?????????
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/reapalRechargeNotice", method = RequestMethod.POST)
	@ResponseBody
	public void reapalRechargeNotice(HttpServletRequest request,String encryptkey, String data,HttpServletResponse response) throws Exception{
		String result = null;
        System.out.println("??????????????????encryptkey==========>" + encryptkey);
        System.out.println("??????????????????data==========>" + data);
        try {
        	//??????????????????
        	result = ReapalUtil.pubkey(encryptkey,data);
        	System.out.println("??????????????????payback_result:"+result);
        	/*
        	 * ??????????????????result???????????????
        	 * */
        	Map<String, Object> map = JSONUtils.json2map(result);
        	String sign = map.get("sign").toString();
        	Map<String, String> mapResult = new HashMap<String, String>();
        	for (Map.Entry<String, Object> entry : map.entrySet()) { 
        		String value = entry.getValue() == null ? "" : entry.getValue().toString();
        		mapResult.put(entry.getKey(), value);
        	}
        	//????????????
        	String mysign = Md5Utils.BuildMysign(mapResult, ReapalUtil.getKey());
        	String verifyStatus = "";
        	
        	//????????????responseTxt?????????????????????????????????????????????
        	if(mysign.equals(sign) ){
//        		HashMap<String, Object> resultMap = this.investorAccountHistoryService.insertReapalNotice4Recharge(map);
        		HashMap<String, Object> resultMap = this.realpalNoticeInfoService.insertReapalNotice4Recharge(map, RealpalNoticeInfoPayType.FILLIN);
        		//????????????????????????????????????????????????????????????????????????????????????	
//        		if(status.equals("TRADE_FINISHED")){
//        		//????????????????????????????????????????????????????????????out_trade_no???????????????total_fee????????????????????????????????????????????????????????????????????????????????????????????????
//        		//???????????????????????????????????????????????????????????????????????????????????????
//        		//??????????????????????????????????????????????????????
//        		}else{
//        		
//        	//??????????????????????????????
//        		}
        		if((Boolean)resultMap.get("result")){
        			verifyStatus = "success";
    				
    			} else {
    				verifyStatus = "fail";
    			}
        		
        	}else{
        		verifyStatus = "fail";
        	}
        	response.getWriter().write(verifyStatus);
        }  catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
            e.printStackTrace();
            super.flushAll();
            response.getWriter().write("fail");
        }
        return;
	}
	
	/**
	 * ?????????????????????????????????????????????--????????????????????????
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/reapalQcbRechargeNotice", method = RequestMethod.POST)
	@ResponseBody
	public void reapalQcbRechargeNotice(HttpServletRequest request,String encryptkey, String data,HttpServletResponse response) throws Exception{
		String result = null;
        System.out.println("??????????????????encryptkey==========>" + encryptkey);
        System.out.println("??????????????????data==========>" + data);
        try {
        	//??????????????????
        	result = ReapalUtil.pubkey(encryptkey,data);
        	System.out.println("??????????????????payback_result:"+result);
        	/*
        	 * ??????????????????result???????????????
        	 * */
        	Map<String, Object> map = JSONUtils.json2map(result);
        	String sign = map.get("sign").toString();
        	Map<String, String> mapResult = new HashMap<String, String>();
        	for (Map.Entry<String, Object> entry : map.entrySet()) { 
        		String value = entry.getValue() == null ? "" : entry.getValue().toString();
        		mapResult.put(entry.getKey(), value);
        	}
        	//????????????
        	String mysign = Md5Utils.BuildMysign(mapResult, ReapalUtil.getKey());
        	String verifyStatus = "";
        	
        	//????????????responseTxt?????????????????????????????????????????????
        	if(mysign.equals(sign) ){
//        		HashMap<String, Object> resultMap = this.qcbEmployeeHistoryService.insertReapalNotice4Recharge(map);
        		HashMap<String, Object> resultMap = this.realpalNoticeInfoService.insertReapalNotice4Recharge(map, RealpalNoticeInfoPayType.EMP_FILLIN);
        		//????????????????????????????????????????????????????????????????????????????????????	
//        		if(status.equals("TRADE_FINISHED")){
//        		//????????????????????????????????????????????????????????????out_trade_no???????????????total_fee????????????????????????????????????????????????????????????????????????????????????????????????
//        		//???????????????????????????????????????????????????????????????????????????????????????
//        		//??????????????????????????????????????????????????????
//        		}else{
//        		
//        	//??????????????????????????????
//        		}
        		if((Boolean)resultMap.get("result")){
        			verifyStatus = "success";
    				
    			} else {
    				verifyStatus = "fail";
    			}
        		
        	}else{
        		verifyStatus = "fail";
        	}
        	response.getWriter().write(verifyStatus);
        }  catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
            e.printStackTrace();
            super.flushAll();
            response.getWriter().write("fail");
        }
        return;
	}
	
	/**
	 * ?????????????????????????????????????????????--????????????????????????
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/reapalShbxRechargeNotice", method = RequestMethod.POST)
	@ResponseBody
	public void reapalShbxRechargeNotice(HttpServletRequest request,String encryptkey, String data,HttpServletResponse response) throws Exception{
		String result = null;
        System.out.println("??????????????????encryptkey==========>" + encryptkey);
        System.out.println("??????????????????data==========>" + data);
        try {
        	//??????????????????
        	result = ReapalUtil.pubkey(encryptkey,data);
        	System.out.println("??????????????????payback_result:"+result);
        	/*
        	 * ??????????????????result???????????????
        	 * */
        	Map<String, Object> map = JSONUtils.json2map(result);
        	String sign = map.get("sign").toString();
        	Map<String, String> mapResult = new HashMap<String, String>();
        	for (Map.Entry<String, Object> entry : map.entrySet()) { 
        		String value = entry.getValue() == null ? "" : entry.getValue().toString();
        		mapResult.put(entry.getKey(), value);
        	}
        	//????????????
        	String mysign = Md5Utils.BuildMysign(mapResult, ReapalUtil.getKey());
        	String verifyStatus = "";
        	
        	//????????????responseTxt?????????????????????????????????????????????
        	if(mysign.equals(sign) ){
//        		HashMap<String, Object> resultMap = this.qcbEmployeeHistoryService.insertReapalNotice4Recharge(map);
        		HashMap<String, Object> resultMap = this.realpalNoticeInfoService.insertReapalNotice4Recharge(map, RealpalNoticeInfoPayType.SHBX_PAY_SHEBAO);
        		//????????????????????????????????????????????????????????????????????????????????????	
//        		if(status.equals("TRADE_FINISHED")){
//        		//????????????????????????????????????????????????????????????out_trade_no???????????????total_fee????????????????????????????????????????????????????????????????????????????????????????????????
//        		//???????????????????????????????????????????????????????????????????????????????????????
//        		//??????????????????????????????????????????????????????
//        		}else{
//        		
//        	//??????????????????????????????
//        		}
        		if((Boolean)resultMap.get("result")){
        			verifyStatus = "success";
    				
    			} else {
    				verifyStatus = "fail";
    			}
        		
        	}else{
        		verifyStatus = "fail";
        	}
        	response.getWriter().write(verifyStatus);
        }  catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.flushAll();
			response.getWriter().write("fail");
		} catch (Exception e) {
            e.printStackTrace();
            super.flushAll();
            response.getWriter().write("fail");
        }
        return;
	}
}
