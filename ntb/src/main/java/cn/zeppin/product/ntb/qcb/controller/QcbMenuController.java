/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbMenuService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbMenuLessVO;

/**
 * @author hehe
 *
 * 企财宝权限信息管理
 */

@Controller
@RequestMapping(value = "/qcb/menu")
public class QcbMenuController extends BaseController {
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbMenuService qcbMenuService;
	
	/**
	 * 权限信息列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result list() {
		
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
		
		//查询权限
		Map<String, String> inputParams = new HashMap<String, String>();
		List<Entity> list = this.qcbMenuService.getListForPage(inputParams, -1, -1, null, QcbMenu.class);
		Map<String, QcbMenuLessVO> mapMenu = new HashMap<String, QcbMenuLessVO>();
		for(Entity entity : list){
			QcbMenu qm = (QcbMenu)entity;
			QcbMenuLessVO qmvo = new QcbMenuLessVO(qm);
			mapMenu.put(qm.getUuid(), qmvo);
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
		return ResultManager.createDataResult(mapMenuVO);
	}
}
