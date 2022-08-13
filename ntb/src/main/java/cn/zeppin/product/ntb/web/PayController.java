package cn.zeppin.product.ntb.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.utility.HttpUtility;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.XMLUtils;

@Controller
@RequestMapping(value = "/web/pay")
public class PayController extends BaseController{
	/**
	 * 微信小程序创建订单
	 * @param openid
	 * @param totalFee
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/wechart", method = RequestMethod.GET)
	@ActionParam(key = "openid", type = DataType.STRING, required = true)
	@ActionParam(key = "totalFee", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result wechart(String openid, Integer totalFee) throws IOException, DocumentException {
		String url = Utlity.WX_PAY_URL;
		
		String nonceStr = UUID.randomUUID().toString().replace("-", "");
		StringBuilder signString = new StringBuilder();
		signString.append("appid=").append(Utlity.WX_APPID);
		signString.append("&body=").append("牛人理财-理财产品");
		signString.append("&mch_id=").append(Utlity.WX_MCH_ID);
		signString.append("&nonce_str=").append(nonceStr);
		signString.append("&key=").append(Utlity.WX_KEY);
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("appid", Utlity.WX_APPID);
		params.put("mch_id", Utlity.WX_MCH_ID);
		params.put("nonce_str", nonceStr);
		params.put("sign", MD5.getMD5(signString.toString()).toUpperCase());
		params.put("body", "牛人理财-理财产品");
		params.put("out_trade_no", UUID.randomUUID().toString().replace("-", ""));
		params.put("total_fee", totalFee+"");
		params.put("spbill_create_ip", Utlity.SERVER_IP);
		params.put("notify_url", Utlity.NOTIFY_URL);
		params.put("trade_type", "JSAPI");
		
		String xml = HttpUtility.post(url, params);
		Map<String, Object> dataMap = XMLUtils.doXMLParse(xml);
		
		return ResultManager.createDataResult(dataMap);
	}
}
