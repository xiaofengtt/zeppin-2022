package org.linlinjava.litemall.wx.web;

import com.alipay.api.*;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundApplyModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.linlinjava.litemall.core.util.HttpUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.config.sign.AuthInterceptor;
import org.linlinjava.litemall.wx.util.AlipayConfig;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@RestController
@RequestMapping("/wx/alipay")
@Validated
@Slf4j
@Api(value = "支付",description = "支付接口")
public class ApyController  {
	private final Log logger = LogFactory.getLog(ApyController.class);


	@GetMapping("waypay")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name="out_trade_no" ,value = "订单编号", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="subject" ,value = "订单标题", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="return_url" ,value = "返回地址", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="total_amount" ,value = "订单金额", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="body" ,value = "订单描述信息", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="quit_url" ,value = "用户付款中途退出返回商户网站的地址", required = true, dataType = "String")
	})
	@ApiOperation(value="手机网页支付", notes="手机网页支付(登入验证)")
	@AuthInterceptor(needAuthTokenVerify = true)
	public Object waypay(String out_trade_no,String subject,String return_url,String total_amount,String body,String quit_url,HttpServletResponse httpResponse) throws ServletException, IOException {
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient (AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
		AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest (); //手机网页支付

		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel ();
		model.setBody (body);
		model.setSubject (subject);
		model.setOutTradeNo (out_trade_no);
		model.setTimeoutExpress ("30m");
		model.setTotalAmount (total_amount);
		model.setProductCode ("QUICK_MSECURITY_PAY");
		model.setQuitUrl (quit_url);
		request.setBizModel (model);
//		request.setReturnUrl (return_url);
//		request.setNotifyUrl (AlipayConfig.notify_url);
//
//		request.setBizContent("{" +
//				" \"out_trade_no\":\"20150320010101002\"," +
//				" \"total_amount\":\"0.11\"," +
//				" \"subject\":\"Iphone6 16G\"," +
//				" \"product_code\":\"QUICK_WAP_PAY\"" +
//				" }");//填充业务参数



		String form = "";
		try {
			//这里和普通的接口调用不同，使用的是sdkExecute
			java.security.Security.addProvider(
					new org.bouncycastle.jce.provider.BouncyCastleProvider()
			);
			form = alipayClient.pageExecute (request).getBody (); //手机网页支付
//			int end= form.indexOf ("\">");
//			int start1= form.indexOf ("{");
//			int end1= form.indexOf ("}");
//			String url=form.substring (49,end);
//			String  biz=form.substring (start1,end1+1);
//
//			String sendPost = HttpUtil.sendPost (url+"&biz_content="+biz, null);
			return ResponseUtil.ok(form);

		} catch (AlipayApiException e) {
			return ResponseUtil.serious ();
		}catch (Exception ex){
			ex.printStackTrace ();
			return ResponseUtil.serious ();
		}


	}



	@GetMapping("apppay")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name="out_trade_no" ,value = "订单编号", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="subject" ,value = "订单标题", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="return_url" ,value = "返回地址", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="total_amount" ,value = "订单金额", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="body" ,value = "订单描述信息", required = true, dataType = "String")
	})
	@ApiOperation(value="app支付", notes="app支付(登入验证)")
	@AuthInterceptor(needAuthTokenVerify = true)
	public Object alipai(String out_trade_no,String subject,String return_url,String total_amount,String body) {
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient (AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();//app支付

		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel ();
		model.setBody (body);
		model.setSubject (subject);
		model.setOutTradeNo (out_trade_no);
		model.setTimeoutExpress ("30m");
		model.setTotalAmount (total_amount);
		model.setProductCode ("QUICK_MSECURITY_PAY");
		request.setBizModel (model);
		request.setReturnUrl (return_url);
		request.setNotifyUrl (AlipayConfig.notify_url);

//		request.setReturnUrl(AlipayConfig.return_url);
//		request.setReturnUrl(return_url);
//		request.setNotifyUrl(AlipayConfig.notify_url);
//		request.setBizContent("{" +
//				"    \"total_amount\":\"0.01\"," +
//				"    \"subject\":\"大乐透\"," +
//				"    \"out_trade_no\":\"70501111111S001111119\"," +
//				"    \"body\":\"body\"" +
//				"    \"timeout_express\":\"30m\"" +
//				"    \"product_code\":\"QUICK_WAP_PAY\"" +
//				"  }");//设置业务参数


		String form = "";
		try {
			//这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);  //app支付
			if(response.isSuccess()){
				return ResponseUtil.ok(response.getBody());
			} else {
				return ResponseUtil.serious();
			}


		} catch (AlipayApiException e) {
			return ResponseUtil.serious ();
		}

	}


	public String alipaipri(String out_trade_no,String subject,String return_url,String total_amount,String body) {
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

		model.setBody(body);
		model.setSubject(subject);
		model.setOutTradeNo(out_trade_no);
		model.setTimeoutExpress("30m");
		model.setTotalAmount(total_amount);
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setReturnUrl(return_url);
		request.setNotifyUrl(AlipayConfig.notify_url);

//		request.setReturnUrl(AlipayConfig.return_url);
//		request.setReturnUrl(return_url);
//		request.setNotifyUrl(AlipayConfig.notify_url);
//		request.setBizContent("{" +
//				"    \"total_amount\":\"0.01\"," +
//				"    \"subject\":\"大乐透\"," +
//				"    \"out_trade_no\":\"70501111111S001111119\"," +
//				"    \"body\":\"body\"" +
//				"    \"timeout_express\":\"30m\"" +
//				"    \"product_code\":\"QUICK_WAP_PAY\"" +
//				"  }");//设置业务参数


		try {
			//这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			return response.getBody();
//			if(response.isSuccess()){
//				return response;
//			} else {
//				return ResponseUtil.serious();
//			}

		} catch (AlipayApiException e) {
			log.error(e.getErrCode(),e.getErrMsg());
//			e.printStackTrace();
			return e.getErrMsg();
		}

	}





	public String apiwaypay(String out_trade_no,String subject,String return_url,String total_amount,String body,String quit_url) throws ServletException, IOException {
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient (AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
		AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest (); //手机网页支付

		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel ();
		model.setBody (body);
		model.setSubject (subject);
		model.setOutTradeNo (out_trade_no);
		model.setTimeoutExpress ("30m");
		model.setTotalAmount (total_amount);
		model.setProductCode ("QUICK_MSECURITY_PAY");
		model.setQuitUrl (quit_url);
		request.setBizModel (model);
//		request.setReturnUrl (return_url);
//		request.setNotifyUrl (AlipayConfig.notify_url);
//
//		request.setBizContent("{" +
//				" \"out_trade_no\":\"20150320010101002\"," +
//				" \"total_amount\":\"0.11\"," +
//				" \"subject\":\"Iphone6 16G\"," +
//				" \"product_code\":\"QUICK_WAP_PAY\"" +
//				" }");//填充业务参数



		String form = "";
		try {
			//这里和普通的接口调用不同，使用的是sdkExecute
			java.security.Security.addProvider(
					new org.bouncycastle.jce.provider.BouncyCastleProvider()
			);
			form = alipayClient.pageExecute (request).getBody (); //手机网页支付
//			int end= form.indexOf ("\">");
//			int start1= form.indexOf ("{");
//			int end1= form.indexOf ("}");
//			String url=form.substring (49,end);
//			String  biz=form.substring (start1,end1+1);
//
//			String sendPost = HttpUtil.sendPost (url+"&biz_content="+biz, null);
			return form;

		} catch (AlipayApiException e) {
			return form;
		}catch (Exception ex){
			ex.printStackTrace ();
			return form;
		}


	}



	@GetMapping("refund")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name="out_trade_no" ,value = "订单支付时传入的商户订单号,不能和 trade_no同时为空。", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="trade_no" ,value = "支付宝交易号，和商户订单号不能同时为空", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="out_request_no" ,value = "标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="refund_reason" ,value = "退款的原因说明", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name="refund_amount" ,value = "需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数", required = true, dataType = "String")
	})
	@ApiOperation(value="退款", notes="退款(登入验证)")
	@AuthInterceptor(needAuthTokenVerify = true)
	public Object refund(String out_trade_no,String trade_no,String out_request_no,String refund_amount,String refund_reason) {
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT,AlipayConfig.CHARSET,  AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE); //获得初始化的AlipayClient
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();//创建API对应的request类

		AlipayTradeRefundApplyModel model=new AlipayTradeRefundApplyModel();
		model.setOutTradeNo(out_trade_no);
		model.setOutRequestNo(out_request_no);
		model.setTradeNo(trade_no);
		model.setRefundAmount(refund_amount);
		model.setRefundReason(refund_reason);

//
//		request.setBizContent("{" +
//				"    \"out_trade_no\":\"20150320010101001\"," +
//				"    \"trade_no\":\"2014112611001004680073956707\"," +
//				"    \"out_request_no\":\"1000001\"," +
//				"    \"refund_amount\":\"1\"" +
//				"  }");//设置业务参数

		try {
			AlipayTradeRefundResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
			if(response.isSuccess()){
				return ResponseUtil.ok(response.getBody());
			} else {
				return ResponseUtil.serious();
			}

		} catch (AlipayApiException e) {
			return ResponseUtil.serious();
		}

	}


	/**
	 * 支付宝异步 通知页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/alipayNotifyNotice")
	@ResponseBody
	@ApiOperation(value="", hidden = true)
	public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

		log.info("支付成功, 进入异步通知接口...");

		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE); //调用SDK验证签名
		//——请在这里编写您的程序（以下代码仅作参考）——
        /* 实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
        4、验证app_id是否为该商户本身。
        */
		if(signVerified) {//验证成功
			log.info("验证成功");
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			//付款金额 到这里获取到这些信息就可以了，下面的不用看
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序

				//注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
			}else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序

				//注意：
				//付款完成后，支付宝系统发送该交易状态通知

				int result=0;
				//这里根据自身的业务写代码，我这里删掉了

				if(result==0){
					log.info("resultinfo","更新订单失败！请业务员联系后台管理员！");
				}else {
					log.info("resultinfo","出租工具成功！");
				}

				log.info("********************** 支付成功(支付宝同步通知) **********************");
				log.info("* 订单号: {}", out_trade_no);
				log.info("* 支付宝交易号: {}", trade_no);
				log.info("* 实付金额: {}", total_amount);
				log.info("***************************************************************");
			}
			logger.info("支付成功...");
		}else {//验证失败
			logger.info("支付, 验签失败...");
		}
		return "success";

	}



	/**
	 * 支付宝同步通知页面,成功返回首页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/alipayReturnNotice")
	@ApiOperation(value="", hidden = true)
	public String alipayReturnNotice(Model model, HttpServletRequest request, HttpServletRequest response) throws Exception {

//		log.info("支付成功, 进入同步通知接口...");

		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}


		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE); //调用SDK验证签名
		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//付款金额，这里获取到三个参数就可以了，后面逻辑代码自己创作
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

			int result=0;
			//这里根据自身的业务写代码，我这里删掉了

			if(result==0){
				model.addAttribute("resultinfo","更新订单失败！请业务员联系后台管理员！");
			}else {
				model.addAttribute("resultinfo","出租工具成功！");
			}
			log.info("********************** 支付成功(支付宝同步通知) **********************");
			log.info("* 订单号: {}", out_trade_no);
			log.info("* 支付宝交易号: {}", trade_no);
			log.info("* 实付金额: {}", total_amount);
			log.info("***************************************************************");
		}else {
			log.info("支付, 验签失败...");
		}
		return "/business/index";//成功返回首页
	}


}