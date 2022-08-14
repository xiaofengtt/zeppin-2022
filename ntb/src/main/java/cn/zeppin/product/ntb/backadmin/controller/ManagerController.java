/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IManagerService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.ManagerVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Manager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.utility.IDCardUtil;
import cn.zeppin.product.utility.Utlity;

/**
 * @author elegantclack
 *
 * 后台主理人信息管理
 */

@Controller
@RequestMapping(value = "/backadmin/manager")
public class ManagerController extends BaseController {

	@Autowired
	private IManagerService managerService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	/**
	 * 根据条件查询主理人信息 
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
		searchMap.put("status", status);
		
		//查询符合条件的主理人信息的总数
		Integer totalResultCount = managerService.getCount(searchMap);
		//查询符合条件的主理人信息列表
		List<Entity> list = managerService.getListForPage(searchMap, pageNum, pageSize, sorts, ManagerVO.class);
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 编辑一条主理人信息
	 * @param name
	 * @param logo
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取主理人信息
		Manager manager = managerService.get(uuid);
		if(manager != null && !"".equals(manager.getUuid())){
			ManagerVO managerVO = new ManagerVO(manager);
			
			//获取照片信息
			Resource resource = resourceService.get(manager.getPhoto());
			if (resource != null){
				managerVO.setPhotoUrl(resource.getUrl());
			}
			
			//获取创建人信息
			BkOperator operator = bkOperatorService.get(manager.getCreator());
			if (resource != null){
				managerVO.setCreatorName(operator.getRealname());
			}
			//界面返回封装对象
			return ResultManager.createDataResult(managerVO);
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 添加一条主理人信息
	 * @param name
	 * @param type
	 * @param graduation
	 * @param education
	 * @param score
	 * @param resume
	 * @param workage
	 * @param mobile
	 * @param idcard
	 * @param email
	 * @param photo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message = "姓名", type = DataType.STRING, required = true, minLength = 1, maxLength = 30)
	@ActionParam(key = "type", message = "类型", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "graduation", message = "毕业院校", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "education", message = "学历", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "score", message = "积分", type = DataType.NUMBER)
	@ActionParam(key = "resume", message = "个人简历", type = DataType.STRING)
	@ActionParam(key = "workage", message = "从业年限", type = DataType.NUMBER)
	@ActionParam(key = "mobile", message = "手机号", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "idcard", message = "身份证号", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "email", message = "邮箱", type = DataType.EMAIL, maxLength = 50)
	@ActionParam(key = "photo", message = "头像", type = DataType.STRING, maxLength = 36)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ResponseBody
	public Result add(String name, String type, String graduation, String education, BigDecimal score, String resume,
			Integer workage, String mobile, String idcard, String email, String photo, String status) {
		
		//验证身份证是否合法
		String idcardValidResult = IDCardUtil.IDCardValidate(idcard);
		if (!"".equals(idcardValidResult)){
			return ResultManager.createFailResult(idcardValidResult);
		}
		
		//验证手机号是否合法
		if (!Utlity.isMobileNO(mobile)){
			return ResultManager.createFailResult("手机号码有误！");
		}
		
		//验证是否有重名的情况
		idcard = idcard.toUpperCase();
		if (managerService.isExistManagerByIdcard(idcard,null)) {
			return ResultManager.createFailResult("身份证已存在！");
		}
		
		//验证是否有重手机号的情况
		if (managerService.isExistManagerByMobile(mobile,null)) {
			return ResultManager.createFailResult("手机号已存在！");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//创建主理人信息
		Manager manager = new Manager();
		manager.setUuid(UUID.randomUUID().toString());
		manager.setName(name);
		manager.setType(type);
		manager.setGraduation(graduation);
		manager.setEducation(education);
		manager.setScore(score);
		manager.setResume(resume);
		manager.setWorkage(workage);
		manager.setMobile(mobile);
		manager.setIdcard(idcard);
		manager.setEmail(email);
		manager.setPhoto(photo);
		manager.setStatus(status);
		manager.setCreator(currentOperator.getUuid());
		manager.setCreatetime(new Timestamp(System.currentTimeMillis()));
		manager = managerService.insert(manager);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑一条主理人信息
	 * @param uuid
	 * @param name
	 * @param type
	 * @param graduation
	 * @param education
	 * @param score
	 * @param resume
	 * @param workage
	 * @param mobile
	 * @param idcard
	 * @param email
	 * @param photo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message = "姓名", type = DataType.STRING, required = true, minLength = 1, maxLength = 30)
	@ActionParam(key = "type", message = "类型", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "graduation", message = "毕业院校", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "education", message = "学历", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "score", message = "积分", type = DataType.NUMBER)
	@ActionParam(key = "resume", message = "个人简历", type = DataType.STRING)
	@ActionParam(key = "workage", message = "从业年限", type = DataType.NUMBER)
	@ActionParam(key = "mobile", message = "手机号", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "idcard", message = "身份证号", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "email", message = "邮箱", type = DataType.EMAIL, maxLength = 50)
	@ActionParam(key = "photo", message = "头像", type = DataType.STRING, maxLength = 36)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ResponseBody
	public Result edit(String uuid, String name, String type, String graduation, String education, BigDecimal score, 
			String resume, Integer workage, String mobile, String idcard, String email, String photo, String status) {
		
		//获取主理人信息
		Manager manager = managerService.get(uuid);
		if(manager != null && uuid.equals(manager.getUuid())){
			//验证身份证是否合法
			String idcardValidResult = IDCardUtil.IDCardValidate(idcard);
			if (!"".equals(idcardValidResult)){
				return ResultManager.createFailResult(idcardValidResult);
			}
			
			//验证手机号是否合法
			if (!Utlity.isMobileNO(mobile)){
				return ResultManager.createFailResult("手机号码有误！");
			}
			
			//验证是否有重名的情况
			idcard = idcard.toUpperCase();
			if (managerService.isExistManagerByIdcard(idcard,uuid)) {
				return ResultManager.createFailResult("身份证已存在！");
			}
			
			//验证是否有重手机号的情况
			if (!manager.getMobile().equals(mobile) && managerService.isExistManagerByMobile(mobile,null)) {
				return ResultManager.createFailResult("手机号已存在！");
			}
			
			//修改主理人信息
			manager.setName(name);
			manager.setType(type);
			manager.setGraduation(graduation);
			manager.setEducation(education);
			manager.setScore(score);
			manager.setResume(resume);
			manager.setWorkage(workage);
			manager.setMobile(mobile);
			manager.setIdcard(idcard);
			manager.setEmail(email);
			manager.setPhoto(photo);
			manager.setStatus(status);
			manager = managerService.update(manager);
			
			return ResultManager.createSuccessResult("保存成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条主理人信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//获取主理人信息
		Manager manager = managerService.get(uuid);
		if(manager != null && uuid.equals(manager.getUuid())){
			//删除主理人信息
			managerService.deleteManager(manager);
			return ResultManager.createSuccessResult("删除成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
}
