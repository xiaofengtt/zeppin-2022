package cn.zeppin.product.score.controller.back;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Admin;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.shiro.LoginType;

@Controller
@RequestMapping(value = "/loginBack")
public class LoginController extends BaseController{
	
	@Autowired
    private AdminService adminService;
	
    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ActionParam(key = "username", message="用户名", type = DataType.STRING, required = true)
    @ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
    @ActionParam(key = "kaptcha", message="验证码", type = DataType.STRING, required = true)
    @ResponseBody
    public Result login(HttpServletResponse response, Admin admin, String kaptcha) throws IOException{
        if (StringUtils.isEmpty(admin.getUsername()) || StringUtils.isEmpty(admin.getPassword())) {
    		return ResultManager.createFailResult("用户名或密码不能为空！");
        }
        
        Session session = SecurityUtils.getSubject().getSession();
        String sessionAuthCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY); 

		if (!kaptcha.equalsIgnoreCase(sessionAuthCode)) {
			return ResultManager.createErrorResult("001", "验证码输入错误！");
		}
		
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(admin.getUsername(),admin.getPassword(), LoginType.BACK.toString());
        try {
            subject.login(token);
            // 当验证都通过后，把用户信息放在session里
            Admin u = adminService.getByUsername(admin.getUsername());
            session.setAttribute("backAdmin", u);
            
    		return ResultManager.createSuccessResult("登录成功！");
        }catch (LockedAccountException lae) {
            token.clear();
            return ResultManager.createFailResult("用户已经被锁定不能登录，请与管理员联系！");
        } catch (AuthenticationException e) {
            token.clear();
            return ResultManager.createFailResult("用户或密码不正确！");
        }
    }
    
    @RequestMapping(value="/logout",method=RequestMethod.GET)
    @ResponseBody
    public Result logout(HttpServletResponse response){
    	Subject subject = SecurityUtils.getSubject();
    	Session session = subject.getSession();
        session.removeAttribute("backAdmin");
        subject.logout();
		return ResultManager.createSuccessResult("退出登录成功！");
    }
}
