package cn.zeppin.product.itic.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.zeppin.product.itic.backadmin.service.api.ITmsListService;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.utility.CdrUtlity;
import cn.zeppin.product.utility.itic.IPUtils;

@Controller
@RequestMapping(value = "/CDR")
public class NoticeController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private ITmsListService tmsListService;

	/**
	 * 异步通知（通话结束后回传数据）
	 * {
	"header": {
		"serviceName": "callACRRequest"
	},
	"body": {
		"messageId": "S201506171252170C8A208838020512",
		"serviceKey":"900007",
		"callId": "C201502121700123D69744B0009476426",
		"callerNum": "15613655010",
		"calledNum": "15613655011",
		"middleNumber": "0755*****",
		"callerDisplayNumber": "0755*****",
		"calledDisplayNumber": "0755*****"		
		"callerStreamNo": "201502121700258056413D69744B00094702",
		"startCallTime": "20150212170021",
		"stopCallTime": "20150212170025",
		"callerDuration": "5",
		"callerCost": "10",
		"callerRelCause": "1",
		"callerOriRescode": "", 
		"callerRelReason": "",
		"calledStreamNo": "",
		"startCalledTime": "S201506171252170C8A208838020512",
		"calledDuration": "0",
		"calledCost": "0",
		"calledRelCause": "2", 
		"calledOriRescode": "",
		"calledRelReason": "",
		"srfmsgid": "",
		"chargeNumber": "0755*****",
		"msServer": "",
		"middleStartTime": "20150212170025",
		"middleCallTime": "20150212170025",
		"duration": "5",
		"costCount": "1"
	}
}

	 * @param request
	 * @param response
	 * @param Jsonmap
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/PushRecords", method = RequestMethod.POST)
	@ResponseBody
	public Object callACRRequest(HttpServletRequest request, HttpServletResponse response,
			@RequestBody JSONObject Jsonmap) {
		JSONObject JsonReturn = new JSONObject();
		JSONObject JsonResult = new JSONObject();
		Map<String, Object> resultMap = JSONObject.parseObject(Jsonmap.toJSONString());
		Map<String, Object> map = new HashMap<>();
		map = IPUtils.GetIpFromUrl(request);// ip 
		String ipAdd = (String) map.get("IpAddress");
		// String requestUrl = request.getRequestURL().toString();
		if (!Arrays.asList(CdrUtlity.iplist).contains(ipAdd) && CdrUtlity.ipenable == "false") {
			JsonResult.put("STATE_CODE", "0002");
			JsonResult.put("STATE_NAME", "IP禁止");
			JsonResult.put("REMARK", "");
			JsonReturn.put("header", JsonResult);
			logger.info("============Ip is forbidden!============");
			logger.info("=============================================================");
			return JsonReturn;
		} else {
			try {
				this.tmsListService.noticeProcess(resultMap, Jsonmap, map);
				JsonResult.put("STATE_CODE", "0000");
				JsonResult.put("STATE_NAME", "成功");
				JsonResult.put("REMARK", "");
				JsonReturn.put("header", JsonResult);
			} catch (Exception e) {
				e.printStackTrace();
				JsonResult.put("STATE_CODE", "9999");
				JsonResult.put("STATE_NAME", "其他错误");
				JsonResult.put("REMARK", "");
				JsonReturn.put("header", JsonResult);
				logger.error("System Error! ", e.getCause());
			} finally {
				logger.info("=============================================================");
				return JsonReturn;
			}
		}
	}
}
