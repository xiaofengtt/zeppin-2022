/**
 * 
 */
package cn.zeppin.product.ntb.shbx.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jgroups.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.AliCertification;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.ShbxInsured;
import cn.zeppin.product.ntb.core.entity.ShbxInsured.ShbxInsuredStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.ShbxUserInsured;
import cn.zeppin.product.ntb.core.entity.ShbxUserInsured.ShbxUserInsuredStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.shbx.service.api.IShbxInsuredService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserInsuredService;
import cn.zeppin.product.ntb.shbx.vo.ShbxInsuredVO;
import cn.zeppin.product.utility.JuHeUtility;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 社保熊参保人
 */

@Controller
@RequestMapping(value = "/shbxWeb/insured")
public class ShbxWebInsuredController extends BaseController {
	
	@Autowired
	private IShbxInsuredService shbxInsuredService;
	
	@Autowired
	private IShbxUserInsuredService shbxUserInsuredService;
	
	@Autowired
	private IBkAreaService bkAreaService;
	
	/**
	 * 获取用户关联参保人列表
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(Integer pageNum, Integer pageSize, String sorts) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser su = (ShbxUser) session.getAttribute("shbxUser");
		
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
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		ShbxInsured si = this.shbxInsuredService.get(uuid);
		if(si != null){
			ShbxInsuredVO vo = new ShbxInsuredVO(si);
			if(si.getHouseholdarea() != null){
				BkArea area = this.bkAreaService.get(si.getHouseholdarea());
				if(area != null){
					vo.setHouseholdareaParent(area.getPid());
					List<String> areaNames = this.bkAreaService.getFullName(si.getHouseholdarea());
					if(areaNames != null && areaNames.size() >= 2){
						vo.setHouseholdareaName(areaNames.get(0) + " " + areaNames.get(1));
					}
				}
			}
			return ResultManager.createDataResult(vo);
		} else {
			return ResultManager.createFailResult("参保人不存在！");
		}
	}
	
	/**
	 * 添加关联参保人
	 * @param name
	 * @param idcard
	 * @param mobile
	 * @param householdtype
	 * @param householdarea
	 * @param email
	 * @param education
	 * @param worktime
	 * @param nationality
	 * @param duty
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@ActionParam(key = "name", message="姓名", type = DataType.STRING, required = true)
	@ActionParam(key = "idcard", message="身份证号", type = DataType.STRING, required = true)
	@ActionParam(key = "mobile", message="电话号码", type = DataType.STRING, required = true)
	@ActionParam(key = "householdtype", message="户口性质", type = DataType.STRING, required = true)
	@ActionParam(key = "householdarea", message="户口所在地", type = DataType.STRING, required = true)
	@ActionParam(key = "email", message="电子邮箱", type = DataType.STRING)
	@ActionParam(key = "education", message="学历", type = DataType.STRING)
	@ActionParam(key = "worktime", message="参加工作时间", type = DataType.DATE)
	@ActionParam(key = "nationality", message="民族", type = DataType.STRING)
	@ActionParam(key = "duty", message="个人身份", type = DataType.STRING)
	@ResponseBody
	public Result add(String name, String idcard, String mobile, String householdtype, String householdarea, String email, 
			String education, String worktime, String nationality, String duty) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser su = (ShbxUser) session.getAttribute("shbxUser");
		
		//验证手机号正确
		if(!Utlity.isMobileNO(mobile)){
			return ResultManager.createFailResult("手机号码非法！");
		}
		
		//验证身份证号
		if(!Utlity.checkIdCard(idcard)){
			return ResultManager.createFailResult("身份证号非法！");
		}                                                                
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("idcard", idcard);
		List<Entity> list = this.shbxInsuredService.getListForPage(inputParams, -1, -1, null, ShbxInsured.class);
		ShbxInsured si = new ShbxInsured();
		
		if(list != null && list.size() > 0){
			si = (ShbxInsured)list.get(0);
			si = this.shbxInsuredService.get(si.getUuid());
			
			inputParams.clear();
			inputParams.put("shbxInsured", si.getUuid());
			Integer count = this.shbxUserInsuredService.getCount(inputParams);
			
			if(count == 0){
				if(!name.equals(si.getName())){
					//调用支付宝欺诈信息验证接口 进行实名认证
					Map<String, Object> result = JuHeUtility.certification(idcard, name);
					if(result.get("request") != null && (Boolean)result.get("request")){
						//信息入库
						Map<String, Object> response = (Map<String, Object>) result.get("response");
						if(response != null && !response.isEmpty()){
							AliCertification ac = new AliCertification();
							ac.setUuid(UUID.randomUUID().toString());
							ac.setCreatetime(new Timestamp(System.currentTimeMillis()));
							ac.setCreator(su.getUuid());
							
							ac.setBinNo(response.get("bizNo")==null ? "无":response.get("bizNo").toString());
							ac.setVerifyCode(response.get("verifyCode")==null ? "无":response.get("verifyCode").toString());
							ac.setProductCode(response.get("product_code")==null ? "无":response.get("product_code").toString());
							ac.setTranscationid(response.get("transAutoIncIdx")==null ? "无":response.get("transAutoIncIdx").toString());
							ac.setInscription("支付宝实名认证—欺诈信息验证");
							ac.setCode(response.get("code")==null ? "无":response.get("code").toString());
							ac.setMsg(response.get("msg")==null ? "无":response.get("msg").toString());
							ac.setSubCode(response.get("sub_code")==null ? "无":response.get("sub_code").toString());
							ac.setSubMsg(response.get("sub_msg")==null ? "无":response.get("sub_msg").toString());
						}
					} else {
						return ResultManager.createFailResult("实名认证失败，" + result.get("message").toString());
					}
					
					si.setName(name);
				}
				si.setMobile(mobile);
				si.setHouseholdarea(householdarea);
				si.setHouseholdtype(householdtype);
				si.setEmail(email);
				si.setEducation(education);
				if(worktime != null && !"".equals(worktime)){
					si.setWorktime(Timestamp.valueOf(Utlity.getFullTime(worktime)));
				}
				si.setNationality(nationality);
				si.setDuty(duty);
				si.setStatus(ShbxInsuredStatus.NORMAL);
				
				ShbxUserInsured sui = new ShbxUserInsured();
				sui.setUuid(UUID.randomUUID().toString());
				sui.setShbxInsured(si.getUuid());
				sui.setShbxUser(su.getUuid());
				sui.setStatus(ShbxUserInsuredStatus.NORMAL);
				sui.setCreator(su.getUuid());
				sui.setCreatetime(new Timestamp(System.currentTimeMillis()));
				
				this.shbxInsuredService.update(si);
				this.shbxUserInsuredService.insert(sui);
			}else{
				return ResultManager.createFailResult("参保人已存在！");
			}
		}else{
			//调用支付宝欺诈信息验证接口 进行实名认证
			Map<String, Object> result = JuHeUtility.certification(idcard, name);
			if(result.get("request") != null && (Boolean)result.get("request")){
				//信息入库
				Map<String, Object> response = (Map<String, Object>) result.get("response");
				if(response != null && !response.isEmpty()){
					AliCertification ac = new AliCertification();
					ac.setUuid(UUID.randomUUID().toString());
					ac.setCreatetime(new Timestamp(System.currentTimeMillis()));
					ac.setCreator(su.getUuid());
					
					ac.setBinNo(response.get("bizNo")==null ? "无":response.get("bizNo").toString());
					ac.setVerifyCode(response.get("verifyCode")==null ? "无":response.get("verifyCode").toString());
					ac.setProductCode(response.get("product_code")==null ? "无":response.get("product_code").toString());
					ac.setTranscationid(response.get("transAutoIncIdx")==null ? "无":response.get("transAutoIncIdx").toString());
					ac.setInscription("支付宝实名认证—欺诈信息验证");
					ac.setCode(response.get("code")==null ? "无":response.get("code").toString());
					ac.setMsg(response.get("msg")==null ? "无":response.get("msg").toString());
					ac.setSubCode(response.get("sub_code")==null ? "无":response.get("sub_code").toString());
					ac.setSubMsg(response.get("sub_msg")==null ? "无":response.get("sub_msg").toString());
				}
			} else {
				return ResultManager.createFailResult("实名认证失败，" + result.get("message").toString());
			}
			
			si.setUuid(UUID.randomUUID().toString());
			si.setName(name);
			si.setIdcard(idcard);
			si.setMobile(mobile);
			si.setHouseholdarea(householdarea);
			si.setHouseholdtype(householdtype);
			si.setEmail(email);
			si.setEducation(education);
			if(worktime != null && !"".equals(worktime)){
				si.setWorktime(Timestamp.valueOf(Utlity.getFullTime(worktime)));
			}
			si.setNationality(nationality);
			si.setDuty(duty);
			si.setStatus(ShbxInsuredStatus.NORMAL);
			si.setCreator(su.getUuid());
			si.setCreatetime(new Timestamp(System.currentTimeMillis()));
			this.shbxInsuredService.insertShbxInsured(si);
		}
		
		return ResultManager.createDataResult(si.getUuid());
	}
	
	/**
	 * 修改关联参保人
	 * @param uuid
	 * @param mobile
	 * @param householdtype
	 * @param householdarea
	 * @param email
	 * @param education
	 * @param worktime
	 * @param nationality
	 * @param duty
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="id", type = DataType.STRING, required = true)
	@ActionParam(key = "mobile", message="电话号码", type = DataType.STRING, required = true)
	@ActionParam(key = "householdtype", message="户口性质", type = DataType.STRING, required = true)
	@ActionParam(key = "householdarea", message="户口所在地", type = DataType.STRING, required = true)
	@ActionParam(key = "email", message="电子邮箱", type = DataType.STRING)
	@ActionParam(key = "education", message="学历", type = DataType.STRING)
	@ActionParam(key = "worktime", message="参加工作时间", type = DataType.DATE)
	@ActionParam(key = "nationality", message="民族", type = DataType.STRING)
	@ActionParam(key = "duty", message="个人身份", type = DataType.STRING)
	@ResponseBody
	public Result edit(String uuid, String mobile, String householdtype, String householdarea, String email, 
			String education, String worktime, String nationality, String duty) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser su = (ShbxUser) session.getAttribute("shbxUser");
		
		ShbxInsured si = this.shbxInsuredService.get(uuid);
		if(si == null){
			return ResultManager.createFailResult("参保人不存在！");
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("shbxUser", su.getUuid());
		inputParams.put("shbxInsured", si.getUuid());
		Integer count = this.shbxUserInsuredService.getCount(inputParams);
		if(count == 0){
			return ResultManager.createFailResult("您没有权限修改此参保人！");
		}
		
		si.setMobile(mobile);
		si.setHouseholdarea(householdarea);
		si.setHouseholdtype(householdtype);
		si.setEmail(email);
		si.setEducation(education);
		if(worktime != null && !"".equals(worktime)){
			si.setWorktime(Timestamp.valueOf(Utlity.getFullTime(worktime)));
		}
		si.setNationality(nationality);
		si.setDuty(duty);
		this.shbxInsuredService.update(si);
		
		return ResultManager.createSuccessResult("修改参保人成功！");
	}
	
	/**
	 * 删除关联参保人
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="id", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser su = (ShbxUser) session.getAttribute("shbxUser");
		
		ShbxInsured si = this.shbxInsuredService.get(uuid);
		if(si == null){
			return ResultManager.createFailResult("参保人不存在！");
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("shbxUser", su.getUuid());
		inputParams.put("shbxInsured", si.getUuid());
		List<Entity> list = this.shbxUserInsuredService.getListForPage(inputParams, -1, -1, null, ShbxUserInsured.class);
		if(list == null || list.size() == 0){
			return ResultManager.createFailResult("您没有权限删除此参保人！");
		}
		ShbxUserInsured sui = (ShbxUserInsured)list.get(0);
		sui = this.shbxUserInsuredService.get(sui.getUuid());
		
		this.shbxUserInsuredService.delete(sui);
		return ResultManager.createSuccessResult("删除参保人成功！");
	}
}
