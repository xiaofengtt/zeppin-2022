package cn.zeppin.product.ntb.backadmin.controller;

import java.awt.image.BufferedImage;
import java.sql.Timestamp;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.zeppin.product.ntb.backadmin.security.CustomizedToken;
import cn.zeppin.product.ntb.backadmin.security.LoginType;
import cn.zeppin.product.ntb.backadmin.security.RoleSign;
import cn.zeppin.product.ntb.backadmin.security.SecurityByNtbRealm;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorRoleService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.vo.OperatorVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BkOperator.BkOperatorStatus;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 后台用户接口
 * 
 * @author Clark.R
 * @since 2016年3月29日 下午3:54:00
 **/
@Controller
@RequestMapping(value = "/backadmin/operator")
public class BkOperatorController extends BaseController{
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IBkOperatorRoleService bkOperatorRoleService;
	
	@Autowired
    private Producer captchaProducer;  
    
	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ActionParam(key = "loginname", message="用户名", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ActionParam(key = "kaptcha", message="验证码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result login(String loginname, String password, String kaptcha) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
	    BkOperator bkOperator = null;
		try {
			
			// 验证码验证
			String sessionAuthCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY); 

			if (!kaptcha.equalsIgnoreCase(sessionAuthCode)) {
				return ResultManager.createErrorResult("001", "验证码输入错误！");
			}
			
	        // 通过数据库进行验证
	        bkOperator =  bkOperatorService.getByLoginname(loginname);
	        
	        if (bkOperator == null) {
	            throw new UnknownAccountException();
	        }  
	        
	        // 判断帐号是否锁定  
	        else if (BkOperatorStatus.LOCKED.equals(bkOperator.getStatus())) {  
	            // 抛出 帐号锁定异常  
	            throw new LockedAccountException();  
	        }  
	        else if (BkOperatorStatus.DISABLE.equals(bkOperator.getStatus())) {  
	            // 抛出 帐号锁定异常  
	            throw new AuthenticationException("该账户已停用！");  
	        }
			
			// 身份验证
//		    UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginname, password);
	        CustomizedToken usernamePasswordToken = new CustomizedToken(loginname, password, LoginType.NTB.toString());
			subject.login(usernamePasswordToken);
			
			// 验证成功 
			// final User authUser = userService.getByName(user.getName());

		} 
		catch (UnknownAccountException e) {  
			//没有此账户
			return ResultManager.createErrorResult("001", "没有此账户信息！");
		}
		catch (LockedAccountException e) {
			//账户已锁定
			return ResultManager.createErrorResult("002", "密码输入错误超过次数限制，账户已锁定！");
		}
		catch (IncorrectCredentialsException e) {  
			//用户名,密码错误  
			return ResultManager.createErrorResult("003", e.getMessage());
        } 
		catch (ExcessiveAttemptsException e) {
            //登录失败多次，账户锁定10分钟
			if (bkOperator.getStatus().equals(BkOperatorStatus.NORMAL)) {
				bkOperator.setStatus(BkOperatorStatus.LOCKED);
	        	bkOperator.setLockedtime(new Timestamp(System.currentTimeMillis()));
	        	bkOperatorService.update(bkOperator);
			}
			return ResultManager.createErrorResult("004", "密码输入错误超过次数限制，账户已锁定！");
		}
		catch (AuthenticationException e) {
			// 身份验证失败
			e.printStackTrace();
			return ResultManager.createErrorResult("005", e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//实际实现的时候只将用户的基本信息返回，不要返回用户带密码、联系方式等的全信息对象
		session.setAttribute("currentOperator", bkOperator);
		
		if (BkOperatorStatus.UNOPEN.equals(bkOperator.getStatus())) {  
            // 账号未开通  
        	return ResultManager.createDataResult(bkOperator, "unopen");
        }  
		
		return ResultManager.createDataResult(bkOperator);
	}
	
	/**
	 * 用户登出
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		// 登出操作
		Subject subject = SecurityUtils.getSubject();
		Session shiroSession = subject.getSession();
		shiroSession.removeAttribute("currentOperator");
		subject.logout();
		return "redirect:../../../views/login.jsp";
	}


	/**
	 * 用户开通
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/open", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "password", message="新密码", type = DataType.STRING, required = true, minLength = 6, maxLength = 22)
	@ActionParam(key = "confirmPassword", message="确认密码", type = DataType.STRING, required = true, minLength = 6, maxLength = 22)
	@ResponseBody
	public Result open(String uuid, String password, String confirmPassword) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator operator = (BkOperator) session.getAttribute("currentOperator");
		
		//验证密码是否相等
		if(!password.equals(confirmPassword)){
			return ResultManager.createFailResult("两次输入密码不同！");
		}
		//验证用户
		if(!uuid.equals(operator.getUuid())){
			return ResultManager.createFailResult("用户信息有误，请重新登陆！");
		}
		//验证状态
		if(!BkOperatorStatus.UNOPEN.equals(operator.getStatus())){
			return ResultManager.createFailResult("用户不需开通！");
		}
		
		BkOperator op = bkOperatorService.get(uuid);
		String encryptPassword = SecurityByNtbRealm.encrypt(password, ByteSource.Util.bytes(op.getUuid()));
		op.setPassword(encryptPassword);
		op.setStatus(BkOperatorStatus.NORMAL);
		op = bkOperatorService.update(op);
		
		session.setAttribute("currentOperator", op);
		return ResultManager.createDataResult(op);
	}
	
	/**
	 * 基于角色 标识的权限控制案例
	 */
	@RequestMapping(value = "/admin")
	@ResponseBody
	@RequiresRoles(value = RoleSign.ADMIN)
	public String admin() {
		return "拥有admin角色,能访问";
	}  
	
    @RequestMapping("/kaptchaImage")  
    public ModelAndView captchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
        response.setDateHeader("Expires", 0);  
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");  
        // return a jpeg  
        response.setContentType("image/jpeg");  
        // create the text for the image  
        String capText = captchaProducer.createText();  
        // store the text in the session  
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
        // create the image with the text  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        // write the data out  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } 
        finally {  
            out.close();  
        }  
        return null;  
    }  
    
	/**
	 * 获取一条管理员信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//获取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator operator = (BkOperator) session.getAttribute("currentOperator");
		
		if (operator == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		OperatorVO operatorVO = new OperatorVO(operator);
		
		return ResultManager.createDataResult(operatorVO);
	} 
	
	/**
	 * 编辑管理员信息
	 * @param uuid
	 * @param name
	 * @param realname
	 * @param mobile
	 * @param email
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true, minLength = 6, maxLength = 22)
	@ActionParam(key = "realname", message="真实姓名", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "mobile", message="手机号", type = DataType.PHONE, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "email", message="邮箱", type = DataType.EMAIL)
	@ResponseBody
	public Result edit(String uuid, String name, String realname, String mobile, String email) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		//获取管理员信息
		BkOperator operator = bkOperatorService.get(uuid);
		if(operator != null && uuid.equals(operator.getUuid())){
			
			//验证是否有重名的情况
			if (bkOperatorService.isExistOperatorByName(name, uuid)) {
				return ResultManager.createFailResult("用户名已存在！");
			}
			
			//验证是否有重手机号的情况
			if (bkOperatorService.isExistOperatorByMobile(mobile, uuid)) {
				return ResultManager.createFailResult("手机号码已存在！");
			}
			
			//修改属性值
			operator.setEmail(email);
			operator.setMobile(mobile);
			operator.setName(name);
			operator.setRealname(realname);
			
			operator = bkOperatorService.update(operator);
			session.setAttribute("currentOperator", operator);
			return ResultManager.createDataResult(operator,"修改个人信息成功");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 修改密码
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "password", message="原密码", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordNew", message="新密码", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordNewCheck", message="确认密码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result password(String uuid,String password, String passwordNew, String passwordNewCheck) {
		
		//获取管理员信息60b474258188bbf0b48249e04bc8f4ef
		BkOperator operator = bkOperatorService.get(uuid);
		if(operator != null && uuid.equals(operator.getUuid())){
			
			//密码加密
			String encryptPassword = SecurityByNtbRealm.encrypt(password, ByteSource.Util.bytes(operator.getUuid()));
			if(!operator.getPassword().equals(encryptPassword)){
				return ResultManager.createFailResult("原密码输入错误！");
			}
			if(!passwordNew.equals(passwordNewCheck)){
				return ResultManager.createFailResult("确认密码与新密码不一致，请修改！");
			}
			encryptPassword = SecurityByNtbRealm.encrypt(passwordNew, ByteSource.Util.bytes(operator.getUuid()));
			operator.setPassword(encryptPassword);
			
			operator = bkOperatorService.update(operator);
			
			//登出
			Subject subject = SecurityUtils.getSubject();
			Session shiroSession = subject.getSession();
			shiroSession.removeAttribute("currentOperator");
			subject.logout();
			
			return ResultManager.createDataResult(operator,"重置密码成功");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
}
