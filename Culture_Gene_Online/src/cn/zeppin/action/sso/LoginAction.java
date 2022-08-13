package cn.zeppin.action.sso;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

//import org.apache.commons.lang3.RandomStringUtils;




import org.apache.commons.lang.RandomStringUtils;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.User;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Java2D;
import cn.zeppin.utility.Utlity;

public class LoginAction extends BaseAction {

	private static final long serialVersionUID = -3909927948294558147L;

	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * 后台登录
	 */
	public void Login() {
		String sessionCode = session.getAttribute("authcode") == null ? "" : session.getAttribute("authcode").toString();
		ActionResult result = new ActionResult();
		if (!this.containsParameter("loginname", "password", "role", "authcode")) {
			result.init(FAIL_STATUS, "接口参数传递不正确！", null);
		} else {
			String loginName = request.getParameter("loginname");
			String passWord = request.getParameter("password");
			String role = request.getParameter("role");
			String authCode = request.getParameter("authcode");
			// 验证用户名为空
			if (loginName == null || loginName.equals("")) {
				result.init(FAIL_STATUS, "没有输入用户名！", null);
			}
			// 密码为空
			else if (passWord == null || passWord.equals("")) {
				result.init(FAIL_STATUS, "没有输入密码！", null);
			}
			// 验证验证码为空
			else if (authCode == null || authCode.equals("")) {
				result.init(FAIL_STATUS, "没有输入验证码！", null);
			}
			// 验证码输入不正确
			else if (sessionCode == null || !authCode.equals(sessionCode)) {
				result.init(FAIL_STATUS, "验证码输入不正确！", null);
			} else {
				HashMap<String,String> searchMap = new HashMap<String,String>();
				searchMap.put("phone", loginName);
				searchMap.put("role", role);
				List<User> userList = this.userService.getListByParams(searchMap);
				
				if(userList.size() > 0){
					User currentUser = userList.get(0);
					if (!currentUser.getPassword().equals(passWord)) {
						result.init(FAIL_STATUS, "用户密码输入错误！", null);
					} else if (currentUser.getStatus() != Dictionary.USER_STATUS_NOMAL) {
						result.init(FAIL_STATUS, "该账户已停用！", null);
					} else {
						// 保存会话状态
						session.setAttribute("usersession", currentUser);
	
						Map<String, Object> data = SerializeEntity.user2Map(currentUser);
						result.init(SUCCESS_STATUS, "登录成功，正在努力为您加载页面！", data);
					}
				}else{
					result.init(FAIL_STATUS, "此用户不存在！", null);
				}
			}
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 生成验证码
	 */
	public void AuthImg() {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("image/jpeg");
			response.addHeader("expires", "0");

			String authCode = RandomStringUtils.randomNumeric(5);
			OutputStream os = response.getOutputStream();

			// 指定图形验证码图片的大小
			BufferedImage image = Java2D.drawAuthCodeImg(authCode);

			// 将验证码保存到seesion中
			session.setAttribute("authcode", authCode);

			ImageIO.write(image, "jpeg", os);
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 修改密码
	 */
	@ActionParam(key = "password", type = ValueType.STRING)
	@ActionParam(key = "newpassword", type = ValueType.STRING)
	@ActionParam(key = "confimpassword", type = ValueType.STRING)
	public void Edit() {
		ActionResult result = new ActionResult();
		String password=request.getParameter("password");
		String newpassword=request.getParameter("newpassword");
		String confimpassword=request.getParameter("confimpassword");
		if(!password.equals("")&&!newpassword.equals("")&&!confimpassword.equals("")){
			if(confimpassword.equals(newpassword)){
				User user = (User) session.getAttribute("usersession");
				if(password.equals(user.getPassword())){
					user.setPassword(newpassword);
					this.getUserService().updateUser(user);
					result.init(SUCCESS_STATUS, "修改成功", null);
				}else{
					result.init(FAIL_STATUS, "原密码错误", null);
				}
			}else{
				result.init(FAIL_STATUS, "两次输入的新密码不一致", null);
			}
		}else{
			result.init(FAIL_STATUS, "密码不能为空", null);
		}
		Utlity.ResponseWrite(result, dataType, response);

	}
}
