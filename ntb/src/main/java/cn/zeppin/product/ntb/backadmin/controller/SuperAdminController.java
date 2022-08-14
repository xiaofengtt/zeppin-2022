package cn.zeppin.product.ntb.backadmin.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.backadmin.security.SecurityByNtbRealm;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorRoleService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.vo.OperatorVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.BkOperator.BkOperatorStatus;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole.BkOperatorRoleUuid;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.PasswordCreator;
import cn.zeppin.product.utility.SendSms;

/**
 * 后台管理员接口
 * 
 **/

@Controller
@RequestMapping(value = "/backadmin/superAdmin")
public class SuperAdminController extends BaseController{
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IBkOperatorRoleService bkOperatorRoleService;
	
	/**
	 * 获取一条管理员信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//获取管理员信息
		BkOperator operator = bkOperatorService.get(uuid);
		
		if (operator == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		//验证角色
		if (!BkOperatorRoleUuid.SUPER_ADMIN.equals(operator.getRole())){
			return ResultManager.createFailResult("管理员角色错误！");
		}
		
		OperatorVO operatorVO = new OperatorVO(operator);
		
		//角色名
		BkOperatorRole role = bkOperatorRoleService.get(operator.getRole());
		if(role != null){
			operatorVO.setRoleName(role.getDescription());
		}
		
		//创建人
		BkOperator creator = bkOperatorService.get(operator.getCreator());
		if(creator != null){
			operatorVO.setCreatorName(creator.getRealname());
		}
		return ResultManager.createDataResult(operatorVO);
	}  
	
	/**
	 * 根据条件查询系统管理员信息 
	 * @param name
	 * @param role
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		String role = "'"+BkOperatorRoleUuid.SUPER_ADMIN+"'";
		searchMap.put("role", role);
		searchMap.put("status", status);
		
		//查询符合条件的管理员信息的总数
		Integer totalResultCount = bkOperatorService.getCount(searchMap);
		//查询符合条件的管理员信息列表
		List<Entity> list = bkOperatorService.getListForPage(searchMap, pageNum, pageSize, sorts, OperatorVO.class);
		for(Entity entity : list){
			OperatorVO vo = (OperatorVO)entity;
			if(vo.getCreator() != null){
				BkOperator bo = this.bkOperatorService.get(vo.getCreator());
				if(bo!=null){
					vo.setCreatorName(bo.getRealname());
				}else{
					vo.setCreatorName("无");
				}
			}else{
				vo.setCreatorName("无");
			}
			
		}
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message = "用户名", type = DataType.STRING, required = true, minLength = 6, maxLength = 22)
	@ActionParam(key = "realname", message = "真实姓名", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "mobile", message = "手机", type = DataType.PHONE, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "email", message = "邮箱", type = DataType.EMAIL)
	@ResponseBody
	public Result add(String name, String realname, String mobile, String email) {
		
		//验证是否有重名的情况
		if (bkOperatorService.isExistOperatorByName(name, null)) {
			return ResultManager.createFailResult("用户名已存在！");
		}
		
		//验证是否有重手机号的情况
		if (bkOperatorService.isExistOperatorByMobile(mobile, null)) {
			return ResultManager.createFailResult("手机号码已存在！");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//构造管理员
		BkOperator operator = new BkOperator();
		//以手机号为标识，检查管理员是否之前创建过，如果创建过，更新信息重新启用
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("status", BkOperatorStatus.DELETED);
		inputParams.put("mobile", mobile);
		List<Entity> list = this.bkOperatorService.getListForPage(inputParams, -1, -1, null, OperatorVO.class);
		boolean flag = true;
		if(list != null && !list.isEmpty()){
			OperatorVO operat = (OperatorVO) list.get(0);
			operator = this.bkOperatorService.get(operat.getUuid());
			if(operator != null){
				flag = false;
			}
		} else {
			operator.setUuid(UUID.randomUUID().toString());
		}
		
		operator.setRole(BkOperatorRoleUuid.SUPER_ADMIN);
		operator.setCreator(currentOperator.getUuid());
		operator.setEmail(email);
		operator.setMobile(mobile);
		//密码加密
		String password = PasswordCreator.createPassword(8);
		String content = "您的管理后台的账号已开通，登录名为手机号，初始密码为" + password + "，为保障账号安全，首次登录后台请修改登录密码";
		SendSms.sendSms(content, mobile);
		String encryptPassword = SecurityByNtbRealm.encrypt(password, ByteSource.Util.bytes(operator.getUuid()));
		operator.setPassword(encryptPassword);
		operator.setStatus(BkOperatorStatus.UNOPEN);
		
		operator.setName(name);
		operator.setRealname(realname);
		operator.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		String message = "";
		if(flag){
			operator = bkOperatorService.insert(operator);
			message = "管理员"+realname+"创建成功，初始密码已发送至"+mobile+"的手机！";
		} else {
			operator = bkOperatorService.update(operator);
			message = "管理员"+realname+"已存在，账号重新激活成功，信息已更新，初始密码已发送至"+mobile+"的手机！";
		}
		return ResultManager.createDataResult(operator,message);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message = "用户名", type = DataType.STRING, required = true, minLength = 6, maxLength = 22)
	@ActionParam(key = "realname", message = "真实姓名", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "mobile", message = "手机", type = DataType.PHONE, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "email", message = "邮箱", type = DataType.EMAIL)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ResponseBody
	public Result edit(String uuid, String name, String realname, String mobile, String email, String status) {
		
		//获取管理员信息
		BkOperator operator = bkOperatorService.get(uuid);
		if(operator != null && uuid.equals(operator.getUuid())){
			//验证角色
			if (!BkOperatorRoleUuid.SUPER_ADMIN.equals(operator.getRole())){
				return ResultManager.createFailResult("管理员角色错误！");
			}
			
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
			operator.setStatus(status);
			
			operator = bkOperatorService.update(operator);
			return ResultManager.createDataResult(operator,"修改管理员信息成功");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
  
	@RequestMapping(value = "/password", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result password(String uuid) {
		
		//获取管理员信息
		BkOperator operator = bkOperatorService.get(uuid);
		if(operator != null && uuid.equals(operator.getUuid())){
			//验证角色
			if (!BkOperatorRoleUuid.SUPER_ADMIN.equals(operator.getRole())){
				return ResultManager.createFailResult("管理员角色错误！");
			}
			
			//密码加密
			String password = PasswordCreator.createPassword(8);
			SendSms.sendSms(password, operator.getMobile());
			String encryptPassword = SecurityByNtbRealm.encrypt(password, ByteSource.Util.bytes(operator.getUuid()));
			operator.setPassword(encryptPassword);
			operator.setStatus(BkOperatorStatus.UNOPEN);
			
			operator = bkOperatorService.update(operator);
			return ResultManager.createDataResult(operator,"重置密码成功");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条管理员信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//获取管理员信息
		BkOperator operator = bkOperatorService.get(uuid);
		if(operator != null && uuid.equals(operator.getUuid())){
			//验证角色
			if (!BkOperatorRoleUuid.SUPER_ADMIN.equals(operator.getRole())){
				return ResultManager.createFailResult("管理员角色错误！");
			}
			
			//删除管理员信息
			bkOperatorService.delete(operator);
			return ResultManager.createSuccessResult("删除成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
}
