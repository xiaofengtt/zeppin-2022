package cn.product.payment.controller.system;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.entity.Admin;
import cn.product.payment.shiro.CustomizedToken;
import cn.product.payment.shiro.LoginType;

@Controller
@RequestMapping(value = "/loginSystem")
public class SystemLoginController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1251689449115291065L;

	/**
	 * 管理员登录
	 * @param response
	 * @param admin
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
    @ActionParam(key = "username", message="用户名", type = DataType.STRING, required = true)
    @ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result login(HttpServletResponse response, Admin admin) throws IOException{
        if (StringUtils.isEmpty(admin.getUsername()) || StringUtils.isEmpty(admin.getPassword())) {
    		return ResultManager.createFailResult("用户名或密码不能为空！");
        }
        
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        CustomizedToken token=new CustomizedToken(admin.getUsername(),admin.getPassword(), LoginType.SYSTEM.toString());
        try {
            subject.login(token);
            // 当验证都通过后，把管理员信息放在session里
            InputParams params = new InputParams("systemAdminService", "getByUsername");
    		params.addParams("username", null, admin.getUsername());
            DataResult<Object> result = (DataResult<Object>) this.execute(params);
            Admin a = null;
            if(result.getData() != null) {
            	a = (Admin) result.getData();
            } 
            session.setAttribute("systemAdmin", a);
            
    		return ResultManager.createSuccessResult("登录成功！");
        }catch (LockedAccountException lae) {
            token.clear();
            return ResultManager.createFailResult("用户已经被锁定不能登录，请与管理员联系！");
        } catch (AuthenticationException e) {
            token.clear();
            return ResultManager.createFailResult("用户或密码不正确！");
        }
    }
    
	/**
	 * 管理员登出
	 * @param response
	 * @return
	 */
    @RequestMapping(value="/logout",method=RequestMethod.GET)
    @ResponseBody
    public Result logout(HttpServletResponse response){
    	Subject subject = SecurityUtils.getSubject();
    	Session session = subject.getSession();
        session.removeAttribute("systemAdmin");
        subject.logout();
		return ResultManager.createSuccessResult("退出登录成功！");
    }
}
