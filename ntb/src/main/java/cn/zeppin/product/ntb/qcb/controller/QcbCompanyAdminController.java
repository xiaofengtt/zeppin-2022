/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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

import cn.zeppin.product.ntb.backadmin.security.SecurityByNtbRealm;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbAdmin.QcbAdminStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin.QcbCompanyAdminStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdminMenu;
import cn.zeppin.product.ntb.core.entity.QcbMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAdminMenuService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyBankcardService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbMenuService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyAdminDetailVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyAdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbMenuLessVO;
import cn.zeppin.product.utility.PasswordCreator;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 企财宝管理员信息管理
 */

@Controller
@RequestMapping(value = "/qcb/companyAdmin")
public class QcbCompanyAdminController extends BaseController {
	
	@Autowired
	private IQcbCompanyBankcardService qcbCompanyBankcardService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbCompanyAdminService qcbCompanyAdminService;
	
	@Autowired
	private IQcbAdminService qcbAdminService; 
	
	@Autowired
	private IQcbMenuService qcbMenuService;
	
	@Autowired
	private IQcbCompanyAdminMenuService qcbCompanyAdminMenuService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	/**
	 * 根据条件查询企业管理员信息列表
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权查看该企业管理员信息！");
		}
		
		QcbCompanyAccount ca = this.qcbCompanyAccountService.get(company);
		if(ca == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompany", company);
		searchMap.put("name", name);
		searchMap.put("status", status);
		
		//查询总数
		Integer totalResultCount = this.qcbCompanyAdminService.getCount(searchMap);
		//查询列表
		List<Entity> list = this.qcbCompanyAdminService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyAdmin.class);
		
		List<QcbCompanyAdminVO> listvo = new ArrayList<QcbCompanyAdminVO>();
		if(list != null && list.size() > 0){
			Map<String, String> inputParams  = new HashMap<String, String>();
			Integer countMenu = this.qcbMenuService.getCount(inputParams);
			for(Entity entity : list){
				
				QcbCompanyAdmin qca = (QcbCompanyAdmin)entity;
				QcbAdmin qa = this.qcbAdminService.get(qca.getQcbAdmin());
				if(qa == null){
					continue;
				}
				
				if(QcbAdminStatus.DELETED.equals(qa.getStatus())){
					continue;
				}
				QcbCompanyAdminVO vo = new QcbCompanyAdminVO(qa);
				vo.setUuid(qca.getUuid());
				vo.setRole(qca.getRole());
				if(ca.getAccreditMobile().equals(qa.getMobile())){
					vo.setFlagAdmin(true);
				}
				
				//查询权限
				inputParams.put("qcbAdmin", qa.getUuid());
				inputParams.put("qcbCompany", company);
				List<Entity> listMenu = this.qcbCompanyAdminMenuService.getListForPage(inputParams, -1, -1, null, QcbCompanyAdminMenu.class);
				if(listMenu != null && listMenu.size() > 0){
					StringBuffer menuStr = new StringBuffer();
					List<QcbMenu> listMenuInfo = new ArrayList<QcbMenu>();
					for(Entity e : listMenu){
						QcbCompanyAdminMenu qcam = (QcbCompanyAdminMenu)e;
						QcbMenu qm = this.qcbMenuService.get(qcam.getQcbMenu());
						if(qm != null){
							listMenuInfo.add(qm);
							if(qm.getLevel() > 1){
								menuStr.append(qm.getTitle()).append(";");
							}
						}
					}
					if(listMenuInfo.size() > 0){
						menuStr.delete(menuStr.length() - 1, menuStr.length());
					} else {
						menuStr.delete(0, menuStr.length());
						menuStr.append("无");	
					}
					
					if(countMenu == listMenuInfo.size()){
						menuStr.delete(0, menuStr.length());
						menuStr.append("全部");					
					}
					vo.setMenu(menuStr.toString());
				} 
				listvo.add(vo);
			}
		}
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取一条企业管理员信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权查看该企业管理员信息！");
		}
		
		QcbCompanyAccount ca = this.qcbCompanyAccountService.get(company);
		if(ca == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		QcbCompanyAdmin qca = this.qcbCompanyAdminService.get(uuid);
		if(qca == null){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		
		if(QcbCompanyAdminStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		
		if(!company.equals(qca.getQcbCompany())){
			return ResultManager.createFailResult("无权查看该企业管理员信息！");
		}
		
		QcbAdmin qa = this.qcbAdminService.get(qca.getQcbAdmin());
		if(qa == null){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		
		if(QcbAdminStatus.DELETED.equals(qa.getStatus())){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		QcbCompanyAdminDetailVO vo = new QcbCompanyAdminDetailVO(qa);
		vo.setUuid(qca.getUuid());
		vo.setRole(qca.getRole());
		if(ca.getAccreditMobile().equals(qa.getMobile())){
			vo.setFlagAdmin(true);
		}
		//查询权限
		Map<String, String> inputParams = new HashMap<String, String>();
		List<Entity> list = this.qcbMenuService.getListForPage(inputParams, -1, -1, null, QcbMenu.class);
		Map<String, QcbMenuLessVO> mapMenu = new HashMap<String, QcbMenuLessVO>();
		for(Entity entity : list){
			QcbMenu qm = (QcbMenu)entity;
			QcbMenuLessVO qmvo = new QcbMenuLessVO(qm);
			mapMenu.put(qm.getUuid(), qmvo);
		}
		
		//查询拥有权限，并设置对应关系
		inputParams.clear();
		inputParams.put("qcbAdmin", qa.getUuid());
		inputParams.put("qcbCompany", company);
		List<Entity> listMenu = this.qcbCompanyAdminMenuService.getListForPage(inputParams, -1, -1, null, QcbCompanyAdminMenu.class);
		if(listMenu != null && listMenu.size() > 0){
			for(Entity e : listMenu){
				QcbCompanyAdminMenu qcam = (QcbCompanyAdminMenu)e;
				QcbMenu qm = this.qcbMenuService.get(qcam.getQcbMenu());
				
				if(qm != null){
//					listMenuInfo.add(qm);
					QcbMenuLessVO qmvo = new QcbMenuLessVO(qm);
					qmvo.setFlagOwn(true);
					mapMenu.put(qmvo.getUuid(), qmvo);
				}
			}
		}
		Map<String, QcbMenuLessVO> mapMenuVO = new HashMap<String, QcbMenuLessVO>();
		for (Map.Entry<String, QcbMenuLessVO> entry : mapMenu.entrySet()) {//遍历出1级菜单
			QcbMenuLessVO qmvo = entry.getValue();
			if(qmvo.getLevel() == 1){
				mapMenuVO.put(qmvo.getUuid(), qmvo);
			}
		}
		for (Map.Entry<String, QcbMenuLessVO> entry : mapMenu.entrySet()) {//遍历2级菜单并加入树
			QcbMenuLessVO qmvo = entry.getValue();
			if(qmvo.getLevel() == 2){
				QcbMenuLessVO pvo = mapMenuVO.get(qmvo.getPid());
				Map<String, QcbMenuLessVO> childMenu = pvo.getChildMenu();
				childMenu.put(qmvo.getUuid(), qmvo);
				pvo.setChildMenu(childMenu);
			}
		}
		vo.setListMenu(mapMenuVO);
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 添加一条企业管理员信息
	 * 验证管理员是否存在
	 * 验证管理员与企业关系是否存在
	 * 
	 * 添加完管理员需要发送一条密码短信通知
	 * 
	 * @param name
	 * @param mobile
	 * @param role
	 * @param authority
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message="姓名", type = DataType.STRING, required = true)
	@ActionParam(key = "mobile", message="手机号", type = DataType.STRING, required = true)
	@ActionParam(key = "role", message="角色", type = DataType.STRING, required = true)
	@ActionParam(key = "authority", message="权限", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String name, String mobile, String role, String authority) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权操作该企业管理员信息！");
		}
		
		QcbCompanyAccount companyAccount = this.qcbCompanyAccountService.get(company);
		if(companyAccount == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		//验证手机号正确
		if(!Utlity.isMobileNO(mobile)){
			return ResultManager.createFailResult("手机号码非法！");
		}
		
		//验证手机号码是否存在
		boolean flag = true;
		if(this.qcbAdminService.isExistQcbAdminByMobile(mobile, null)){//存在
			flag = false;
		}
		QcbAdmin qa = new QcbAdmin();
		String content = "";
		if(!flag){//如果存在 判断是否存在企业与账户关联关系
			qa = this.qcbAdminService.getByMobile(mobile);
			Map<String, String> inputParams = new HashMap<String, String>();
			
			inputParams.put("qcbAdmin", qa.getUuid());
			inputParams.put("qcbCompany", company);
			Integer count = this.qcbCompanyAdminService.getCount(inputParams);
			if(count > 0){
				return ResultManager.createFailResult("管理员账号已存在！");
			}
			content = "您的企财宝账号已被企财宝企业" + qcbAdmin.getQcbCompanyName() + "添加为新管理员，请使用原账号登录后查看！";
			
		} else {
			//不存在 添加新号
			qa.setUuid(UUID.randomUUID().toString());
			qa.setCreatetime(new Timestamp(System.currentTimeMillis()));
			qa.setMobile(mobile);
			qa.setName(name);
			
			//密码加密
			String password = PasswordCreator.createPassword(8);
			content = "您的企财宝管理后台的账号已开通，登录名为手机号，初始密码为" + password + "，为保障账号安全，首次登录后台请修改登录密码";
			
			String encryptPassword = SecurityByNtbRealm.encrypt(password, ByteSource.Util.bytes(qa.getUuid()));
			qa.setPassword(encryptPassword);
			qa.setPasswordLevel(3);
			qa.setStatus(QcbAdminStatus.NORMAL);
			qa.setWechatFlag(false);
		}
		MobileCode mc = new MobileCode();
		String codeInfo = Utlity.getCaptcha();
		mc.setCode(codeInfo);
		mc.setContent(content);
		mc.setMobile(mobile);
		mc.setCreator(qcbAdmin.getUuid());
		mc.setStatus(MobileCodeStatus.DISABLE);
		mc.setType(MobileCodeTypes.NOTICE);
		mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		mc.setCreatorType(MobileCodeCreatorType.QCB_ADMIN);
		mc.setUuid(UUID.randomUUID().toString());
		
		//添加一个企业与管理员关系
		QcbCompanyAdmin qcAdmin = new QcbCompanyAdmin();
		qcAdmin.setUuid(UUID.randomUUID().toString());
		qcAdmin.setQcbAdmin(qa.getUuid());
		qcAdmin.setQcbCompany(companyAccount.getUuid());
		qcAdmin.setRole(role);
		qcAdmin.setStatus(QcbCompanyAdminStatus.NORMAL);
		qcAdmin.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qcAdmin.setCreator(qcbAdmin.getUuid());
		
		//添加一组企业与管理员的关系权限
		String[] menuArr = authority.split(",");
		List<QcbCompanyAdminMenu> listMenu = new ArrayList<QcbCompanyAdminMenu>();
		for(String str : menuArr){
			QcbMenu qm = this.qcbMenuService.get(str);
			if(qm == null){
				return ResultManager.createFailResult("权限信息错误！");
			}
			QcbCompanyAdminMenu qcam = new QcbCompanyAdminMenu();
			qcam.setUuid(UUID.randomUUID().toString());
			qcam.setCreator(qcbAdmin.getUuid());
			qcam.setCreatetime(new Timestamp(System.currentTimeMillis()));
			qcam.setQcbAdmin(qa.getUuid());
			qcam.setQcbCompany(companyAccount.getUuid());
			qcam.setQcbCompanyAdmin(qcAdmin.getUuid());
			qcam.setQcbMenu(qm.getUuid());
			listMenu.add(qcam);
		}
		
		try {
			this.qcbCompanyAdminService.insertCompanyAdmin(flag, qa, qcAdmin, listMenu, mc);
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常");
		}
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 修改编辑
	 * @param uuid
	 * @param role
	 * @param authority
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "role", message="角色", type = DataType.STRING, required = true)
	@ActionParam(key = "authority", message="权限", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String role, String authority) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权查看该企业管理员信息！");
		}
		
		//判断是否是总管理员 如果是 不让删除
		QcbCompanyAccount ca = this.qcbCompanyAccountService.get(company);
		if(ca == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		
		QcbCompanyAdmin qca = this.qcbCompanyAdminService.get(uuid);
		if(qca == null){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		
		if(QcbCompanyAdminStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		
		if(!company.equals(qca.getQcbCompany())){
			return ResultManager.createFailResult("无权操作该企业管理员信息！");
		}
		

		QcbAdmin qa = this.qcbAdminService.get(qca.getQcbAdmin());
		if(qa == null){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		
		if(QcbAdminStatus.DELETED.equals(qa.getStatus())){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		
		if(ca.getAccreditMobile().equals(qa.getMobile())){
			return ResultManager.createFailResult("无权操作总管理员信息");
		}
		
		//添加一组企业与管理员的关系权限
		String[] menuArr = authority.split(",");
		List<QcbCompanyAdminMenu> listMenu = new ArrayList<QcbCompanyAdminMenu>();
		for(String str : menuArr){
			QcbMenu qm = this.qcbMenuService.get(str);
			if(qm == null){
				return ResultManager.createFailResult("权限信息错误！");
			}
			QcbCompanyAdminMenu qcam = new QcbCompanyAdminMenu();
			qcam.setUuid(UUID.randomUUID().toString());
			qcam.setCreator(qcbAdmin.getUuid());
			qcam.setCreatetime(new Timestamp(System.currentTimeMillis()));
			qcam.setQcbAdmin(qca.getQcbAdmin());
			qcam.setQcbCompany(qca.getQcbCompany());
			qcam.setQcbCompanyAdmin(qca.getUuid());
			qcam.setQcbMenu(qm.getUuid());
			listMenu.add(qcam);
		}
		
		qca.setRole(role);
		
		try {
			this.qcbCompanyAdminService.update(qca, listMenu);
			return ResultManager.createSuccessResult("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常");
		}
		
	}
	
	/**
	 * 删除一条企业管理员信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result delete(String uuid) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权查看该企业管理员信息！");
		}
		
		QcbCompanyAdmin qca = this.qcbCompanyAdminService.get(uuid);
		if(qca == null){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		
		if(QcbCompanyAdminStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		
		if(!company.equals(qca.getQcbCompany())){
			return ResultManager.createFailResult("无权操作该企业管理员信息！");
		}
		
		//判断是否是总管理员 如果是 不让删除
		QcbCompanyAccount ca = this.qcbCompanyAccountService.get(company);
		if(ca == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		QcbAdmin qa = this.qcbAdminService.get(qca.getQcbAdmin());
		if(qa == null){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		
		if(QcbAdminStatus.DELETED.equals(qa.getStatus())){
			return ResultManager.createFailResult("该企业管理员信息不存在！");
		}
		
		if(ca.getAccreditMobile().equals(qa.getMobile())){
			return ResultManager.createFailResult("无权操作总管理员信息");
		}
		
		//查询拥有权限，一并删除
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.clear();
		inputParams.put("qcbAdmin", qca.getQcbAdmin());
		inputParams.put("qcbCompany", company);
		List<Entity> listMenuInfo = this.qcbCompanyAdminMenuService.getListForPage(inputParams, -1, -1, null, QcbCompanyAdminMenu.class);
		List<QcbCompanyAdminMenu> listMenu = new ArrayList<QcbCompanyAdminMenu>();
		if(listMenuInfo != null && !listMenuInfo.isEmpty()){
			for(Entity e : listMenuInfo){
				QcbCompanyAdminMenu qcam = (QcbCompanyAdminMenu)e;
				listMenu.add(qcam);
			}
		}
		
		try {
			this.qcbCompanyAdminService.delete(qca, listMenu);
			return ResultManager.createSuccessResult("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常！");
		}
	}
}
