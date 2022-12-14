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
 * ????????????
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
	 * ????????????
	 * 
	 * @param loginname
	 * @param password
	 * @param kaptcha
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ActionParam(key = "loginname", message="?????????", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="??????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result login(String loginname, String password) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
	    TSsOperator tSsOperator = null;
		try {
	        // ???????????????????????????
	        tSsOperator =  tSsOperatorService.getByLoginname(loginname);
	        
	        if (tSsOperator == null) {
	            throw new UnknownAccountException();
	        }  else if (TSsOperatorStatus.DISABLE.equals(tSsOperator.getStatus())) {  
	            // ?????? ??????????????????  
	            throw new AuthenticationException("??????????????????!");  
	        }
//	        System.out.println(SecurityRealm.encrypt("111111", ByteSource.Util.bytes("204ec48c-c9c9-0e2a-315b-e40dc0603244")));
		
			// ????????????
	        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginname, password);
			subject.login(usernamePasswordToken);
		} 
		catch (UnknownAccountException e) {  
			//???????????????
			return ResultManager.createErrorResult("001", "?????????????????????!");
		}
		catch (LockedAccountException e) {
			//???????????????
			return ResultManager.createErrorResult("002", "??????????????????????????????????????????????????????!");
		}
		catch (IncorrectCredentialsException e) {  
			//?????????,????????????  
			return ResultManager.createErrorResult("003", e.getMessage());
        } 
		catch (ExcessiveAttemptsException e) {
            //?????????????????????????????????
			tSsOperator.setStatus(TSsOperatorStatus.DISABLE);
			this.tSsOperatorService.update(tSsOperator);
			return ResultManager.createErrorResult("004", "??????????????????????????????????????????????????????!");
		}
		catch (AuthenticationException e) {
			// ??????????????????
			e.printStackTrace();
			return ResultManager.createErrorResult("005", e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createErrorResult("006", "????????????!???????????????????????????");
		}
		//????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		TSsOperatorVO operatorVO = new TSsOperatorVO(tSsOperator);
		session.setAttribute("currentOperator", operatorVO);
		
		List<TSsOperatorMethod> permissions = tSsOperatorMethodService.getByOperator(tSsOperator);
		List<String> methodList = new ArrayList<String>();
		for (TSsOperatorMethod permission : permissions) {
        	TSsController controller = tSsControllerService.get(permission.getController());
        	TSsControllerMethod method = tSsControllerMethodService.get(permission.getMethod());
        	String strPermission = controller.getName() + ":" + method.getName();
        	//????????????????????????????????????????????????
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
	 * ????????????
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		// ????????????
		Subject subject = SecurityUtils.getSubject();
		Session shiroSession = subject.getSession();
		shiroSession.removeAttribute("methodList");
		shiroSession.removeAttribute("currentOperator");
		subject.logout();
		return "redirect:../../../views/login.jsp";
	}
	
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get() {
		//?????????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		if (operator == null) {
			return ResultManager.createFailResult("?????????????????????!");
		}
		
		return ResultManager.createDataResult(operator);
	} 
	
	
	/**
	 * ????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	@ActionParam(key = "password", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordNew", type = DataType.STRING, required = true)
	@ResponseBody
	public Result password(String password, String passwordNew) {
		//?????????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		TSsOperatorVO op = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TSsOperator operator = tSsOperatorService.get(op.getId());
		if(operator != null){
			
			//????????????
			String encryptPassword = SecurityRealm.encrypt(password, ByteSource.Util.bytes(operator.getId()));
			if(!operator.getPassword().equals(encryptPassword)){
				return ResultManager.createFailResult("?????????????????????!");
			}
			encryptPassword = SecurityRealm.encrypt(passwordNew, ByteSource.Util.bytes(operator.getId()));
			operator.setPassword(encryptPassword);
			
			operator = tSsOperatorService.update(operator);
			
			//??????
			session.removeAttribute("methodList");
			session.removeAttribute("productMap");
			session.removeAttribute("currentOperator");
			subject.logout();
			
			return ResultManager.createDataResult(operator,"??????????????????");
		}else{
			return ResultManager.createFailResult("?????????????????????!");
		}
	}
}
