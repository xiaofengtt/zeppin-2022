package cn.product.payment.controller.notice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.util.wechat.XMLUtils;

@Controller
@RequestMapping(value = "/notice/wechat")
public class NoticeWechatController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6333036736208660706L;

	/**
	 * 微信对公充值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/companyNotice", method = RequestMethod.POST)
	@ResponseBody
	public void companyNotice(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> apiResult = new HashMap<String, Object>();
		
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));  
	        String line = null;  
	        StringBuilder sb = new StringBuilder();  
	        while((line = br.readLine()) != null){  
	            sb.append(line);  
	        }  
	        //sb为微信返回的xml  
	        String notityXml = sb.toString();  
	        System.out.println("接收到的报文：" + notityXml);  
	        Map<String, Object> map = XMLUtils.doXMLParse(notityXml);
	        String returnCode = (String) map.get("return_code");
			if("SUCCESS".equals(returnCode)){
				try {
					InputParams iparams = new InputParams("noticeWechatService", "companyNotice");
					iparams.addParams("params", null, map);
					DataResult<Object> result = (DataResult<Object>) this.execute(iparams);
					if(result.getData() != null) {
						Map<String, Object> resultMap = (Map<String, Object>) result.getData();
						if((Boolean)resultMap.get("result")){
							apiResult.put("return_code", "SUCCESS");
							apiResult.put("return_msg",  "操作成功！");
						}else{
							apiResult.put("return_code", "FAIL");
							apiResult.put("return_msg", "报文内容错误");
						}
						response.getWriter().write(XMLUtils.getRequestXml(apiResult));
					}else{
						apiResult.put("return_code", "FAIL");
						apiResult.put("return_msg", "报文内容错误");
						response.getWriter().write(XMLUtils.getRequestXml(apiResult));
					}
				} catch (Exception e) {
					e.printStackTrace();
					apiResult.put("return_code", "FAIL");
					apiResult.put("return_msg", "报文内容错误");
					response.getWriter().write(XMLUtils.getRequestXml(apiResult));
				}
			} else {
				apiResult.put("return_code", "FAIL");
				apiResult.put("return_msg", "报文状态错误");
				response.getWriter().write(XMLUtils.getRequestXml(apiResult));
			}
		}  catch (Exception e) {
			e.printStackTrace();
			apiResult.put("return_code", "FAIL");
			apiResult.put("return_msg", "报文参数错误");
			try {
				response.getWriter().write(XMLUtils.getRequestXml(apiResult));
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
