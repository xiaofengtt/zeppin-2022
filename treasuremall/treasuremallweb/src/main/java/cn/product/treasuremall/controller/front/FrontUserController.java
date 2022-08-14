/**
 * 
 */
package cn.product.treasuremall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.api.base.ResultManager;
import cn.product.treasuremall.controller.BaseController;
import cn.product.treasuremall.vo.front.FrontUserVO;
import io.swagger.annotations.Api;

/**
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/user")
@Api(tags= {"user"})
public class FrontUserController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8868788789286553051L;

	/**
	 * 获取手续费
	 * @return
	 */
	@RequestMapping(value = "/rate", method = RequestMethod.GET)
	@ResponseBody
	protected Result rate(){
		InputParams params = new InputParams("frontUserService", "rate");
		return this.execute(params);
	}
	
	/**
	 * 获取用户信息
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get(HttpServletRequest request) {
		FrontUserVO fu = getFrontUser(request);
		return ResultManager.createDataResult(fu);
	}
	
	/**
	 * 修改昵称
	 * @param nickname
	 * @return
	 */
	@RequestMapping(value = "/editNickname", method = RequestMethod.POST)
	@ActionParam(key = "nickname", type = DataType.STRING, message="昵称", required = true)
	@ResponseBody
	public Result editNickname(HttpServletRequest request, String nickname) {
		InputParams params = new InputParams("frontUserService", "editNickname");
		params.addParams("nickname", null, nickname);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 更换头像
	 * @param image
	 * @return
	 */
	@RequestMapping(value = "/editImage", method = RequestMethod.POST)
	@ActionParam(key = "image", type = DataType.STRING, message="头像", required = true)
	@ResponseBody
	public Result editImage(HttpServletRequest request, String image) {
		InputParams params = new InputParams("frontUserService", "editImage");
		params.addParams("image", null, image);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 修改密码
	 * @param pwd base64(DES(pwd))
	 * @param newPwd base64(DES(newPwd))
	 * @return
	 */
	@RequestMapping(value = "/editPwd", method = RequestMethod.POST)
	@ActionParam(key = "pwd", type = DataType.STRING, message="原密码", required = true)
	@ActionParam(key = "newPwd", type = DataType.STRING, message="新密码", required = true)
	@ResponseBody
	public Result editPwd(HttpServletRequest request, String pwd, String newPwd) {
		InputParams params = new InputParams("frontUserService", "editPwd");
		params.addParams("pwd", null, pwd);
		params.addParams("newPwd", null, newPwd);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 认证手机号码
	 * @param code base64(code)
	 * @return
	 */
	@RequestMapping(value = "/checkMobile", method = RequestMethod.POST)
	@ActionParam(key = "code", type = DataType.STRING, message="验证码", required = true)
	@ResponseBody
	public Result checkMobile(HttpServletRequest request, String code) {
		InputParams params = new InputParams("frontUserService", "checkMobile");
		params.addParams("code", null, code);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 更换手机号码
	 * @param code base64(code)
	 * @param mobile base64(mobile)
	 * @param newCode base64(newCode)
	 * @return
	 */
	@RequestMapping(value = "/editMobile", method = RequestMethod.POST)
	@ActionParam(key = "code", type = DataType.STRING, message="验证码", required = true)
	@ActionParam(key = "mobile", type = DataType.STRING, message="新手机号", required = true)
	@ActionParam(key = "newCode", type = DataType.STRING, message="新验证码", required = true)
	@ResponseBody
	public Result editMobile(HttpServletRequest request, String code, String mobile, String newCode) {
		InputParams params = new InputParams("frontUserService", "editMobile");
		params.addParams("code", null, code);
		params.addParams("mobile", null, mobile);
		params.addParams("newCode", null, newCode);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 实名认证
	 * @param name
	 * @param idcard
	 * @return
	 */
	@RequestMapping(value = "/certification", method = RequestMethod.POST)
	@ActionParam(key = "name", type = DataType.STRING, message="用户姓名", required = true)
	@ActionParam(key = "idcard", type = DataType.STRING, message="用户身份证号", required = true)
	@ResponseBody
	public Result certification(String name, String idcard, HttpServletRequest request){
		InputParams params = new InputParams("frontUserService", "certification");
		params.addParams("name", null, name);
		params.addParams("idcard", null, idcard);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
}
