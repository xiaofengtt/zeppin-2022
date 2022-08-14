package com.product.worldpay.controller.store;

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

import com.product.worldpay.controller.BaseController;
import com.product.worldpay.controller.base.ActionParam;
import com.product.worldpay.controller.base.ActionParam.DataType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;
import com.product.worldpay.controller.base.ResultManager;
import com.product.worldpay.entity.CompanyAdmin;
import com.product.worldpay.shiro.CustomizedToken;
import com.product.worldpay.shiro.LoginType;

@Controller
@RequestMapping(value = "/loginStore")
public class StoreLoginController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1251689449115291065L;

	@RequestMapping(value="/login",method=RequestMethod.POST)
    @ActionParam(key = "mobile", message="mobile number", type = DataType.STRING, required = true)
    @ActionParam(key = "password", message="password", type = DataType.STRING, required = true)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result login(HttpServletResponse response, CompanyAdmin companyAdmin) throws IOException{
        if (StringUtils.isEmpty(companyAdmin.getMobile()) || StringUtils.isEmpty(companyAdmin.getPassword())) {
    		return ResultManager.createFailResult("username and password can not be null");
        }
        
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        CustomizedToken token=new CustomizedToken(companyAdmin.getMobile(),companyAdmin.getPassword(), LoginType.STORE.toString());
        try {
            subject.login(token);
            // 当验证都通过后，把管理员信息放在session里
            InputParams params = new InputParams("storeCompanyAdminService", "getByMobile");
    		params.addParams("mobile", null, companyAdmin.getMobile());
            DataResult<Object> result = (DataResult<Object>) this.execute(params);
            CompanyAdmin ca = null;
            if(result.getData() != null) {
            	ca = (CompanyAdmin) result.getData();
            } 
            session.setAttribute("companyAdmin", ca);
            
    		return ResultManager.createSuccessResult("Login success!");
        }catch (LockedAccountException lae) {
            token.clear();
            return ResultManager.createFailResult("account locked!");
        } catch (AuthenticationException e) {
            token.clear();
            return ResultManager.createFailResult("username or password error!");
        }
    }
    
    @RequestMapping(value="/logout",method=RequestMethod.GET)
    @ResponseBody
    public Result logout(HttpServletResponse response){
    	Subject subject = SecurityUtils.getSubject();
    	Session session = subject.getSession();
        session.removeAttribute("companyAdmin");
        subject.logout();
		return ResultManager.createSuccessResult("logout success!");
    }
}
