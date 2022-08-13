package cn.zeppin.product.ntb.web;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
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
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.WeixinDecrypt;

@Controller
@RequestMapping(value = "/web/login")
public class LoginController extends BaseController{
	/**
	 * 获取微信小程序用户信息 
	 * @param encryptedData
	 * @param iv
	 * @param sessionKey
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	@ActionParam(key = "encryptedData", type = DataType.STRING, required = true)
	@ActionParam(key = "iv", type = DataType.STRING, required = true)
	@ActionParam(key = "code", type = DataType.STRING, required = true)
	@ResponseBody
	public Result getUserInfo(String encryptedData, String iv, String code) {
		StringBuilder url = new StringBuilder(Utlity.WX_LOGIN_URL);
		url.append("?appid=").append(Utlity.WX_APPID);
		url.append("&secret=").append(Utlity.WX_APPSECRET);
		url.append("&js_code=").append(code);
		url.append("&grant_type=authorization_code");
		String json = HttpUtility.get(url.toString());
		Map<String, Object> dataMap = JSONUtils.json2map(json);
		String sessionKey = dataMap.get("session_key").toString();
		
		byte[] resultByte = WeixinDecrypt.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
		if(null != resultByte && resultByte.length > 0){
			String userInfo;
			try {
				userInfo = new String(resultByte, "UTF-8");
				return ResultManager.createDataResult(JSONUtils.json2map(userInfo));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return ResultManager.createFailResult("数据解密失败！");
			}
		}else{
			return ResultManager.createFailResult("数据解密失败！");
		}
	}
}
