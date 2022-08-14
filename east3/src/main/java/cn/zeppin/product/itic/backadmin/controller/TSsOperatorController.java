/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.security.SecurityRealm;
import cn.zeppin.product.itic.backadmin.service.api.ITSsControllerMethodService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsControllerService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorMethodService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorProductService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsRoleService;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.TSsController;
import cn.zeppin.product.itic.core.entity.TSsControllerMethod;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsOperator.TSsOperatorStatus;
import cn.zeppin.product.itic.core.entity.TSsOperatorMethod;
import cn.zeppin.product.itic.core.entity.TSsOperatorProduct;
import cn.zeppin.product.itic.core.entity.TSsRole.RoleId;

/**
 * 用户管理
 */

@Controller
@RequestMapping(value = "/backadmin/operator")
public class TSsOperatorController extends BaseController {

	@Autowired
	private ITSsOperatorService tSsOperatorService;
	
	@Autowired
	private ITSsOperatorMethodService tSsOperatorMethodService;
	
	@Autowired
	private ITSsOperatorProductService tSsOperatorProductService;
	
	@Autowired
	private ITSsControllerService tSsControllerService;
	
	@Autowired
	private ITSsControllerMethodService tSsControllerMethodService;
	
	@Autowired
	private ITSsRoleService tSsRoleService;
	
	/**
	 * 用户登录
	 * 
	 * @param loginname
	 * @param password
	 * @param kaptcha
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ActionParam(key = "loginname", message="用户名", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result login(String loginname, String password) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
	    TSsOperator tSsOperator = null;
		try {
	        // 通过数据库进行验证
	        tSsOperator =  tSsOperatorService.getByLoginname(loginname);
	        
	        if (tSsOperator == null) {
	            throw new UnknownAccountException();
	        }  else if (TSsOperatorStatus.DISABLE.equals(tSsOperator.getStatus())) {  
	            // 抛出 帐号锁定异常  
	            throw new AuthenticationException("该账户已停用!");  
	        }
//	        System.out.println(SecurityRealm.encrypt("111111", ByteSource.Util.bytes("204ec48c-c9c9-0e2a-315b-e40dc0603244")));
		
			// 身份验证
	        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginname, password);
			subject.login(usernamePasswordToken);
		} 
		catch (UnknownAccountException e) {  
			//没有此账户
			return ResultManager.createErrorResult("001", "没有此账户信息!");
		}
		catch (LockedAccountException e) {
			//账户已锁定
			return ResultManager.createErrorResult("002", "密码输入错误超过次数限制，账户已锁定!");
		}
		catch (IncorrectCredentialsException e) {  
			//用户名,密码错误  
			return ResultManager.createErrorResult("003", e.getMessage());
        } 
		catch (ExcessiveAttemptsException e) {
            //登录失败多次，账户禁用
			tSsOperator.setStatus(TSsOperatorStatus.DISABLE);
			this.tSsOperatorService.update(tSsOperator);
			return ResultManager.createErrorResult("004", "密码输入错误超过次数限制，账户已锁定!");
		}
		catch (AuthenticationException e) {
			// 身份验证失败
			e.printStackTrace();
			return ResultManager.createErrorResult("005", e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createErrorResult("006", "登录失败!请刷新页面重新登录");
		}
		//实际实现的时候只将用户的基本信息返回，不要返回用户带密码、联系方式等的全信息对象
		TSsOperatorVO operatorVO = new TSsOperatorVO(tSsOperator);
		session.setAttribute("currentOperator", operatorVO);
		
		List<TSsOperatorMethod> permissions = tSsOperatorMethodService.getByOperator(tSsOperator);
		List<String> methodList = new ArrayList<String>();
		for (TSsOperatorMethod permission : permissions) {
        	TSsController controller = tSsControllerService.get(permission.getController());
        	TSsControllerMethod method = tSsControllerMethodService.get(permission.getMethod());
        	String strPermission = controller.getName() + ":" + method.getName();
        	//向已登录用户添加角色功能权限授权
        	methodList.add(strPermission);
	    }
		session.setAttribute("methodList", methodList);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		if(RoleId.user.equals(operatorVO.getRole())){
			inputParams.put("opcode", operatorVO.getCode());
		}
		List<TSsOperatorProduct> productList = this.tSsOperatorProductService.getList(inputParams);
		Map<String, String> productMap = new HashMap<String, String>();
		for(TSsOperatorProduct product: productList){
			productMap.put(product.getProductcode(), product.getProductname());
		}
		session.setAttribute("productMap", productMap);
		
		return ResultManager.createDataResult(tSsOperator);
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
		shiroSession.removeAttribute("methodList");
		shiroSession.removeAttribute("currentOperator");
		subject.logout();
		return "redirect:../../../views/login.jsp";
	}
	
	/**
	 * 获取当前管理员信息
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get() {
		//获取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		if (operator == null) {
			return ResultManager.createFailResult("该条数据不存在!");
		}
		
		return ResultManager.createDataResult(operator);
	} 
	
	
	/**
	 * 修改密码
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	@ActionParam(key = "password", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordNew", type = DataType.STRING, required = true)
	@ResponseBody
	public Result password(String password, String passwordNew) {
		//获取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		TSsOperatorVO op = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TSsOperator operator = tSsOperatorService.get(op.getId());
		if(operator != null){
			
			//密码加密
			String encryptPassword = SecurityRealm.encrypt(password, ByteSource.Util.bytes(operator.getId()));
			if(!operator.getPassword().equals(encryptPassword)){
				return ResultManager.createFailResult("原密码输入错误!");
			}
			encryptPassword = SecurityRealm.encrypt(passwordNew, ByteSource.Util.bytes(operator.getId()));
			operator.setPassword(encryptPassword);
			
			operator = tSsOperatorService.update(operator);
			
			//登出
			session.removeAttribute("methodList");
			session.removeAttribute("productMap");
			session.removeAttribute("currentOperator");
			subject.logout();
			
			return ResultManager.createDataResult(operator,"重置密码成功");
		}else{
			return ResultManager.createFailResult("该条数据不存在!");
		}
	}
}
