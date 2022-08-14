/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.vo.ShbxInsuredVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.ShbxInsured;
import cn.zeppin.product.ntb.core.entity.ShbxInsured.ShbxInsuredStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.shbx.service.api.IShbxInsuredService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserService;

/**
 * @author hehe
 *
 * 社保熊用户
 */

@Controller
@RequestMapping(value = "/backadmin/shbxUser")
public class ShbxUserController extends BaseController {
	
	@Autowired
	private IShbxUserService shbxUserService;
	
	@Autowired
	private IShbxInsuredService shbxInsuredService;
	
	@Autowired
	private IBkAreaService bkAreaService;
	/**
	 * 获取社保熊用户列表
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("realname", name);
		
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		Integer totalResultCount = this.shbxUserService.getCount(searchMap);
		List<Entity> list = this.shbxUserService.getListForPage(searchMap, pageNum, pageSize, sorts, ShbxUser.class);
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取社保熊用户信息
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//获取企业账户信息
		ShbxUser su = shbxUserService.get(uuid);
		if (su == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		return ResultManager.createDataResult(su);
	}
	
	/**
	 * 获取用户关联参保人列表
	 * @param shbxUser
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/insuredList", method = RequestMethod.GET)
	@ActionParam(key = "shbxUser", message="用户ID", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result insuredList(String shbxUser, Integer pageNum, Integer pageSize, String sorts) {
		
		//校验用户是否存在
		ShbxUser su = this.shbxUserService.get(shbxUser);
		if(su == null){
			return ResultManager.createFailResult("用户不存在！");
		}
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", ShbxInsuredStatus.NORMAL);
		searchMap.put("shbxUser", su.getUuid());
		
		Integer totalRecordsCount = this.shbxInsuredService.getCountForShbx(searchMap);
		List<Entity> list = this.shbxInsuredService.getListForShbxPage(searchMap, pageNum, pageSize, sorts, ShbxInsured.class);
		List<ShbxInsuredVO> voList = new ArrayList<ShbxInsuredVO>();
		
		for(Entity e : list){
			ShbxInsured si = (ShbxInsured) e;
			ShbxInsuredVO vo = new ShbxInsuredVO(si);
			if(si.getHouseholdarea() != null){
				List<String> areaNames = this.bkAreaService.getFullName(si.getHouseholdarea());
				if(areaNames != null && areaNames.size() >= 2){
					vo.setHouseholdareaName(areaNames.get(0) + " " + areaNames.get(1));
				}
			}
			voList.add(vo);
		}
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalRecordsCount);
	}
	
	/**
	 * 获取参保人详细信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/insuredGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result insuredGet(String uuid) {
		ShbxInsured si = this.shbxInsuredService.get(uuid);
		if(si != null){
			ShbxInsuredVO vo = new ShbxInsuredVO(si);
			if(si.getHouseholdarea() != null){
				List<String> areaNames = this.bkAreaService.getFullName(si.getHouseholdarea());
				if(areaNames != null && areaNames.size() >= 2){
					vo.setHouseholdareaName(areaNames.get(0) + " " + areaNames.get(1));
				}
			}
			return ResultManager.createDataResult(vo);
		} else {
			return ResultManager.createFailResult("参保人不存在！");
		}
	}
}
