package cn.product.treasuremall.controller.back;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.api.base.ResultManager;
import cn.product.treasuremall.entity.Admin;
import cn.product.treasuremall.controller.BaseController;
import cn.product.treasuremall.shiro.LoginType;

/**
 */
@Controller
@RequestMapping(value = "/loginBack")
public class LoginController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2225762373146598947L;
	
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/login",method=RequestMethod.POST)
    @ActionParam(key = "username", message="用户名", type = DataType.STRING, required = true)
    @ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
    @ResponseBody
    public Result login(HttpServletResponse response, Admin admin) throws IOException{
        if (StringUtils.isEmpty(admin.getUsername()) || StringUtils.isEmpty(admin.getPassword())) {
    		return ResultManager.createFailResult("用户名或密码不能为空！");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(admin.getUsername(),admin.getPassword(), LoginType.BACK.toString());
        try {
            subject.login(token);

            // 当验证都通过后，把用户信息放在session里
//            Admin u = adminService.getByUsername(admin.getUsername());
            InputParams params = new InputParams("adminService", "getByUsername");
    		params.addParams("username", null, admin.getUsername());
            DataResult<Object> result = (DataResult<Object>) this.execute(params);
            Admin u = null;
            if(result.getData() != null) {
            	u = (Admin) result.getData();
            } 
            if(u == null) {
            	return ResultManager.createFailResult("登录失败，用户信息错误！");
            }
            Session session = SecurityUtils.getSubject().getSession();
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
