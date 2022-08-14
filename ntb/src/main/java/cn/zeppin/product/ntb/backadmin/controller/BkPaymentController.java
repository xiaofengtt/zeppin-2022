/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import cn.zeppin.product.ntb.backadmin.service.api.IBkPaymentOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkPaymentService;
import cn.zeppin.product.ntb.backadmin.vo.BkPaymentOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.BkPaymentOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.BkPaymentVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BkPayment;
import cn.zeppin.product.ntb.core.entity.BkPayment.BkPaymentStatus;
import cn.zeppin.product.ntb.core.entity.BkPaymentOperate;
import cn.zeppin.product.ntb.core.entity.BkPaymentOperate.BkPaymentOperateStatus;
import cn.zeppin.product.ntb.core.entity.BkPaymentOperate.BkPaymentOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author elegantclack
 * 支付方式管理
 */

@Controller
@RequestMapping(value = "/backadmin/bkpayment")
public class BkPaymentController extends BaseController {

	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IBkPaymentService bkPaymentService;
	
	@Autowired
	private IBkPaymentOperateService bkPaymentOperateService;
	
	
	/**
	 * 根据条件查询支付方式
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
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
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//查询符合条件的支付方式信息的总数
		Integer totalResultCount = bkPaymentService.getCount(searchMap);
		//查询符合条件的支付方式信息列表
		List<Entity> list = bkPaymentService.getListForPage(searchMap, pageNum, pageSize, sorts, BkPayment.class);
		
		List<BkPaymentVO> listvo = new ArrayList<BkPaymentVO>();
		
		for(Entity e : list){
			BkPayment payment = (BkPayment) e;
			BkPaymentVO bkvo = new BkPaymentVO(payment);
			if(bkvo.getCreator() != null){
				BkOperator op = this.bkOperatorService.get(bkvo.getCreator());
				if(op != null){
					bkvo.setCreatorCN(op.getRealname());
				}
			}
			listvo.add(bkvo);
		}
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	
	/**
	 * 获取一条支付类型信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取支付方式信息
		BkPayment payment = bkPaymentService.get(uuid);
		if (payment == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		//封装对象
		BkPaymentVO bkvo = new BkPaymentVO(payment);
		if(bkvo.getCreator() != null){
			BkOperator op = this.bkOperatorService.get(bkvo.getCreator());
			if(op != null){
				bkvo.setCreatorCN(op.getRealname());
			}
		}
		return ResultManager.createDataResult(bkvo);
	}
	
	/**
	 * 添加一条支付方式信息
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param type
	 * @param bank
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "paymentName", message="支付方式名称", type = DataType.STRING, required = true)
	@ActionParam(key = "paymentDes", message="支付方式描述", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String paymentName, String paymentDes) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		BkPayment payment  = new BkPayment();
		payment.setUuid(UUID.randomUUID().toString());
		payment.setCreatetime(new Timestamp(System.currentTimeMillis()));
		payment.setCreator(currentOperator.getUuid());
		payment.setFlagSwitch(false);
		
		payment.setPaymentDes(paymentDes);
		payment.setStatus(BkPaymentStatus.NORMAL);
		
		//校验payment唯一性
		Boolean flag = this.bkPaymentService.isExistBkPayment(paymentName, null);
		if(flag){
			return ResultManager.createFailResult("支付方式已存在，请勿重复添加！");
		}
		payment.setPayment(paymentName);
		
		BkPaymentOperate bpo = new BkPaymentOperate();
		bpo.setUuid(UUID.randomUUID().toString());
		bpo.setType(BkPaymentOperateType.ADD);
		bpo.setValue(JSONUtils.obj2json(payment));
		bpo.setStatus(BkPaymentOperateStatus.UNCHECKED);
		bpo.setCreator(currentOperator.getUuid());
		bpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
		bpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		bkPaymentOperateService.insert(bpo);
		return ResultManager.createSuccessResult("添加待审记录成功！");
	}
	
	/**
	 * 编辑一条支付方式信息
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "paymentName", message="支付方式名称", type = DataType.STRING, required = true)
	@ActionParam(key = "paymentDes", message="支付方式描述", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String paymentName, String paymentDes) {
		
		//获取支付方式信息
		BkPayment payment  = bkPaymentService.get(uuid);
		if(payment != null && uuid.equals(payment.getUuid())){
			
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("bkPayment", uuid);
			searchMap.put("type", BkPaymentOperateType.EDIT);
			searchMap.put("status", BkPaymentOperateStatus.UNCHECKED);
			
			Integer count = this.bkPaymentOperateService.getCount(searchMap);
			if(count > 0 ){
				return ResultManager.createFailResult("该条数据有其他修改操作正在等待审核！");
			}
			
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			payment.setPaymentDes(paymentDes);
			
			//校验payment唯一性
			Boolean flag = this.bkPaymentService.isExistBkPayment(paymentName, uuid);
			if(flag){
				return ResultManager.createFailResult("支付方式已存在，请修改！");
			}
			payment.setPayment(paymentName);
			
			BkPaymentOperate bpo = new BkPaymentOperate();
			bpo.setBkPayment(payment.getUuid());
			bpo.setUuid(UUID.randomUUID().toString());
			bpo.setType(BkPaymentOperateType.EDIT);
			bpo.setValue(JSONUtils.obj2json(payment));
			bpo.setStatus(BkPaymentOperateStatus.UNCHECKED);
			bpo.setCreator(currentOperator.getUuid());
			bpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bkPaymentOperateService.insert(bpo);
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 编辑一条支付方式信息
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "switchFlag", message="开关", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result change(String uuid, Boolean switchFlag) {
		
		//获取支付方式信息
		BkPayment payment  = bkPaymentService.get(uuid);
		if(payment != null && uuid.equals(payment.getUuid())){
			
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("bkPayment", uuid);
			String operateType = "";
			if(switchFlag){
				operateType = BkPaymentOperateType.OPEN;
			} else {
				operateType = BkPaymentOperateType.CLOSED;
			}
			searchMap.put("type", operateType);
			searchMap.put("status", BkPaymentOperateStatus.UNCHECKED);
			
			Integer count = this.bkPaymentOperateService.getCount(searchMap);
			if(count > 0 ){
				return ResultManager.createFailResult("该条数据有其他修改操作正在等待审核！");
			}
			
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			payment.setFlagSwitch(switchFlag);
			
			BkPaymentOperate bpo = new BkPaymentOperate();
			bpo.setUuid(UUID.randomUUID().toString());
			bpo.setBkPayment(payment.getUuid());
			bpo.setType(operateType);
			bpo.setValue(JSONUtils.obj2json(payment));
			bpo.setStatus(BkPaymentOperateStatus.UNCHECKED);
			bpo.setCreator(currentOperator.getUuid());
			bpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bkPaymentOperateService.insert(bpo);
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条支付方式信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		//获取支付方式信息
		BkPayment payment  = bkPaymentService.get(uuid);
		if(payment != null){
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			BkPaymentOperate bpo = new BkPaymentOperate();
			bpo.setUuid(UUID.randomUUID().toString());
			bpo.setBkPayment(payment.getUuid());
			bpo.setType(BkPaymentOperateType.DELETE);
			bpo.setValue(JSONUtils.obj2json(payment));
			bpo.setStatus(BkPaymentOperateStatus.UNCHECKED);
			bpo.setCreator(currentOperator.getUuid());
			bpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bkPaymentOperateService.insert(bpo);
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 支付方式操作列表(编辑)
	 * @param status
	 * @param type
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		//查询符合条件的支付方式信息的总数
		Integer totalResultCount = bkPaymentOperateService.getCount(searchMap);
		//查询符合条件的支付方式信息列表
		List<Entity> list = bkPaymentOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BkPaymentOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				BkPaymentOperate cao = (BkPaymentOperate)e;
				BkPaymentOperateVO caoVO = new BkPaymentOperateVO(cao);
				if(BkPaymentOperateType.ADD.equals(cao.getType())){
					BkPayment ca = JSONUtils.json2obj(cao.getValue(), BkPayment.class);
					caoVO.setBkPayment(ca.getUuid());
					caoVO.setBkPaymentName(ca.getPayment());
					caoVO.setBkPaymentDes(ca.getPaymentDes());
				}
				if(caoVO.getBkPayment() != null && !"".equals(caoVO.getBkPayment())){
					BkPayment ca = this.bkPaymentService.get(caoVO.getBkPayment());
					if(ca != null){
						caoVO.setBkPaymentName(ca.getPayment());
						caoVO.setBkPaymentDes(ca.getPaymentDes());
					}
				}
				BkOperator creator = this.bkOperatorService.get(caoVO.getCreator());
				if(creator != null){
					caoVO.setCreatorName(creator.getRealname());
				}
				if(caoVO.getChecker() != null && !"".equals(caoVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(caoVO.getChecker());
					if(checker != null){
						caoVO.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(caoVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 支付方式操作列表(管理员)
	 * @param status
	 * @param type
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		
		//查询符合条件的支付方式信息的总数
		Integer totalResultCount = bkPaymentOperateService.getCount(searchMap);
		//查询符合条件的支付方式信息列表
		List<Entity> list = bkPaymentOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BkPaymentOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				BkPaymentOperate cao = (BkPaymentOperate)e;
				BkPaymentOperateVO caoVO = new BkPaymentOperateVO(cao);
				if(BkPaymentOperateType.ADD.equals(cao.getType())){
					BkPayment ca = JSONUtils.json2obj(cao.getValue(), BkPayment.class);
					caoVO.setBkPayment(ca.getUuid());
					caoVO.setBkPaymentName(ca.getPayment());
					caoVO.setBkPaymentDes(ca.getPaymentDes());
				}
				if(caoVO.getBkPayment() != null && !"".equals(caoVO.getBkPayment())){
					BkPayment ca = this.bkPaymentService.get(caoVO.getBkPayment());
					if(ca != null){
						caoVO.setBkPaymentName(ca.getPayment());
						caoVO.setBkPaymentDes(ca.getPaymentDes());
					}
				}
				BkOperator creator = this.bkOperatorService.get(caoVO.getCreator());
				if(creator != null){
					caoVO.setCreatorName(creator.getRealname());
				}
				if(caoVO.getChecker() != null && !"".equals(caoVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(caoVO.getChecker());
					if(checker != null){
						caoVO.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(caoVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/** 获取支付方式操作审核信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//获取支付方式信息
		BkPaymentOperate cao = bkPaymentOperateService.get(uuid);
		if (cao == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		BkPaymentOperateDetailVO caodVO = new BkPaymentOperateDetailVO(cao);
		BkOperator creator = this.bkOperatorService.get(cao.getCreator());
		if(creator != null){
			caodVO.setCreatorName(creator.getRealname());
		}
		
		if(!BkPaymentOperateType.ADD.equals(cao.getType())){
			if(BkPaymentOperateType.EDIT.equals(cao.getType()) && BkPaymentOperateStatus.CHECKED.equals(cao.getStatus()) 
					&& cao.getOld() != null && !"".equals(cao.getOld())){
				
				BkPayment ca = JSONUtils.json2obj(cao.getOld(), BkPayment.class);	
				BkPaymentVO cadVO = new BkPaymentVO(ca);
				if(cadVO.getCreator() != null){
					BkOperator op = this.bkOperatorService.get(cadVO.getCreator());
					if(op != null){
						cadVO.setCreatorCN(op.getRealname());
					}
				}
				caodVO.setOldData(cadVO);
			}else{
				BkPayment ca = bkPaymentService.get(cao.getBkPayment());
				BkPaymentVO cadVO = new BkPaymentVO(ca);
				if(cadVO.getCreator() != null){
					BkOperator op = this.bkOperatorService.get(cadVO.getCreator());
					if(op != null){
						cadVO.setCreatorCN(op.getRealname());
					}
				}
				caodVO.setOldData(cadVO);
			}
		}
		if(BkPaymentOperateType.ADD.equals(cao.getType()) || BkPaymentOperateType.EDIT.equals(cao.getType())
				 || BkPaymentOperateType.CLOSED.equals(cao.getType()) || BkPaymentOperateType.OPEN.equals(cao.getType())){
			BkPayment ca = JSONUtils.json2obj(cao.getValue(), BkPayment.class);	
			BkPaymentVO cadVO = new BkPaymentVO(ca);
			if(cadVO.getCreator() != null){
				BkOperator op = this.bkOperatorService.get(cadVO.getCreator());
				if(op != null){
					cadVO.setCreatorCN(op.getRealname());
				}
			}
			caodVO.setNewData(cadVO);
		}
		return ResultManager.createDataResult(caodVO);
	}
	
	/**
	 * 编辑一条支付方式操作信息
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "paymentName", message="支付方式名称", type = DataType.STRING, required = true)
	@ActionParam(key = "paymentDes", message="支付方式描述", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateEdit(String uuid, String paymentName, String paymentDes){
		//获取支付方式操作信息
		BkPaymentOperate cao = bkPaymentOperateService.get(uuid);
		if (cao != null) {
			BkPayment ca = JSONUtils.json2obj(cao.getValue(), BkPayment.class);
			
			ca.setPaymentDes(paymentDes);
			
			//校验payment唯一性
			Boolean flag = this.bkPaymentService.isExistBkPayment(paymentName, uuid);
			if(flag){
				return ResultManager.createFailResult("支付方式已存在，请修改！");
			}
			ca.setPayment(paymentName);
			
			//修改待审核记录
			cao.setValue(JSONUtils.obj2json(ca));
			cao.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bkPaymentOperateService.update(cao);
			return ResultManager.createDataResult("修改待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条支付方式操作信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//获取支付方式操作信息
		BkPaymentOperate cao = bkPaymentOperateService.get(uuid);
		if(cao != null){
			if(!BkPaymentOperateStatus.DRAFT.equals(cao.getStatus()) && !BkPaymentOperateStatus.UNPASSED.equals(cao.getStatus())){
				return ResultManager.createFailResult("审核状态错误");
			}
			cao.setStatus(BkPaymentOperateStatus.DELETED);
			bkPaymentOperateService.update(cao);
			return ResultManager.createSuccessResult("操作成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}

	/**
	 *待审核草稿-提交审核
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//获取支付方式操作信息
		BkPaymentOperate cao = bkPaymentOperateService.get(uuid);
		if(cao != null){
			if(BkPaymentOperateStatus.CHECKED.equals(cao.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			if(BkPaymentOperateType.EDIT.equals(cao.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("bkPayment", uuid);
				searchMap.put("type", BkPaymentOperateType.EDIT);
				searchMap.put("status", BkPaymentOperateStatus.UNCHECKED);
				
				Integer count = this.bkPaymentOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("该条数据有其他修改操作正在等待审核！");
				}
			} else if(BkPaymentOperateType.OPEN.equals(cao.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("bkPayment", uuid);
				searchMap.put("type", BkPaymentOperateType.OPEN);
				searchMap.put("status", BkPaymentOperateStatus.UNCHECKED);
				
				Integer count = this.bkPaymentOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("该条数据有其他修改操作正在等待审核！");
				}
			} else if(BkPaymentOperateType.CLOSED.equals(cao.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("bkPayment", uuid);
				searchMap.put("type", BkPaymentOperateType.CLOSED);
				searchMap.put("status", BkPaymentOperateStatus.UNCHECKED);
				
				Integer count = this.bkPaymentOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("该条数据有其他修改操作正在等待审核！");
				}
			}
			cao.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cao.setStatus(BkPaymentOperateStatus.UNCHECKED);
			bkPaymentOperateService.update(cao);
			return ResultManager.createSuccessResult("提交审核成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 审核支付方式操作修改操作
	 * @param uuid
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/operateCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "reason", message="审核原因", type = DataType.STRING, maxLength = 500)
	@ResponseBody
	public Result operateCheck(String uuid, String status, String reason) {
		if(!BkPaymentOperateStatus.CHECKED.equals(status) && !BkPaymentOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("审核状态错误");
		}
		
		//获取支付方式操作信息
		BkPaymentOperate cao = bkPaymentOperateService.get(uuid);
		if(cao != null){
			if(!BkPaymentOperateStatus.UNCHECKED.equals(cao.getStatus())){
				return ResultManager.createFailResult("该记录审核状态错误");
			}
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(cao.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("无法审核自己提交的操作记录");
			}
			
			cao.setChecker(currentOperator.getUuid());
			cao.setChecktime(new Timestamp(System.currentTimeMillis()));
			cao.setStatus(status);
			cao.setReason(reason);
			try {
				bkPaymentOperateService.check(cao);
			} catch (TransactionException e) {
				super.flushAll();
				return ResultManager.createFailResult(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("数据操作出错！");
			}
			return ResultManager.createSuccessResult("审核记录成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 获取支付方式操作分状态列表(编辑)
	 * @return
	 */
	@RequestMapping(value = "/operateStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateStatusList() {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		//获取支付方式分状态信息
		List<Entity> list = bkPaymentOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取支付方式操作分状态列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//获取支付方式分状态信息
		List<Entity> list = bkPaymentOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取支付方式操作分类型列表(编辑)
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateTypeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		List<Entity> list = bkPaymentOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取支付方式操作分类型列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", status);
		
		List<Entity> list = bkPaymentOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
