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
 * ??????????????????
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
	 * ??????????????????????????????
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//????????????????????????????????????????????????
		Integer totalResultCount = bkPaymentService.getCount(searchMap);
		//?????????????????????????????????????????????
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
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//????????????????????????
		BkPayment payment = bkPaymentService.get(uuid);
		if (payment == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		
		//????????????
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
	 * ??????????????????????????????
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param type
	 * @param bank
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "paymentName", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "paymentDes", message="??????????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String paymentName, String paymentDes) {
		//??????????????????
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
		
		//??????payment?????????
		Boolean flag = this.bkPaymentService.isExistBkPayment(paymentName, null);
		if(flag){
			return ResultManager.createFailResult("?????????????????????????????????????????????");
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
		return ResultManager.createSuccessResult("???????????????????????????");
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "paymentName", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "paymentDes", message="??????????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String paymentName, String paymentDes) {
		
		//????????????????????????
		BkPayment payment  = bkPaymentService.get(uuid);
		if(payment != null && uuid.equals(payment.getUuid())){
			
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("bkPayment", uuid);
			searchMap.put("type", BkPaymentOperateType.EDIT);
			searchMap.put("status", BkPaymentOperateStatus.UNCHECKED);
			
			Integer count = this.bkPaymentOperateService.getCount(searchMap);
			if(count > 0 ){
				return ResultManager.createFailResult("??????????????????????????????????????????????????????");
			}
			
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			payment.setPaymentDes(paymentDes);
			
			//??????payment?????????
			Boolean flag = this.bkPaymentService.isExistBkPayment(paymentName, uuid);
			if(flag){
				return ResultManager.createFailResult("????????????????????????????????????");
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
			return ResultManager.createSuccessResult("???????????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "switchFlag", message="??????", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result change(String uuid, Boolean switchFlag) {
		
		//????????????????????????
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
				return ResultManager.createFailResult("??????????????????????????????????????????????????????");
			}
			
			//??????????????????
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
			return ResultManager.createSuccessResult("???????????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		//????????????????????????
		BkPayment payment  = bkPaymentService.get(uuid);
		if(payment != null){
			//??????????????????
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
			return ResultManager.createSuccessResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????(??????)
	 * @param status
	 * @param type
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		//????????????????????????????????????????????????
		Integer totalResultCount = bkPaymentOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = bkPaymentOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BkPaymentOperate.class);
		
		//???????????????????????????List
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
	 * ????????????????????????(?????????)
	 * @param status
	 * @param type
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		
		//????????????????????????????????????????????????
		Integer totalResultCount = bkPaymentOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = bkPaymentOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BkPaymentOperate.class);
		
		//???????????????????????????List
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
	
	/** ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//????????????????????????
		BkPaymentOperate cao = bkPaymentOperateService.get(uuid);
		if (cao == null) {
			return ResultManager.createFailResult("????????????????????????");
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
	 * ????????????????????????????????????
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "paymentName", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "paymentDes", message="??????????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateEdit(String uuid, String paymentName, String paymentDes){
		//??????????????????????????????
		BkPaymentOperate cao = bkPaymentOperateService.get(uuid);
		if (cao != null) {
			BkPayment ca = JSONUtils.json2obj(cao.getValue(), BkPayment.class);
			
			ca.setPaymentDes(paymentDes);
			
			//??????payment?????????
			Boolean flag = this.bkPaymentService.isExistBkPayment(paymentName, uuid);
			if(flag){
				return ResultManager.createFailResult("????????????????????????????????????");
			}
			ca.setPayment(paymentName);
			
			//?????????????????????
			cao.setValue(JSONUtils.obj2json(ca));
			cao.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bkPaymentOperateService.update(cao);
			return ResultManager.createDataResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//??????????????????????????????
		BkPaymentOperate cao = bkPaymentOperateService.get(uuid);
		if(cao != null){
			if(!BkPaymentOperateStatus.DRAFT.equals(cao.getStatus()) && !BkPaymentOperateStatus.UNPASSED.equals(cao.getStatus())){
				return ResultManager.createFailResult("??????????????????");
			}
			cao.setStatus(BkPaymentOperateStatus.DELETED);
			bkPaymentOperateService.update(cao);
			return ResultManager.createSuccessResult("???????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}

	/**
	 *???????????????-????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//??????????????????????????????
		BkPaymentOperate cao = bkPaymentOperateService.get(uuid);
		if(cao != null){
			if(BkPaymentOperateStatus.CHECKED.equals(cao.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			if(BkPaymentOperateType.EDIT.equals(cao.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("bkPayment", uuid);
				searchMap.put("type", BkPaymentOperateType.EDIT);
				searchMap.put("status", BkPaymentOperateStatus.UNCHECKED);
				
				Integer count = this.bkPaymentOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("??????????????????????????????????????????????????????");
				}
			} else if(BkPaymentOperateType.OPEN.equals(cao.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("bkPayment", uuid);
				searchMap.put("type", BkPaymentOperateType.OPEN);
				searchMap.put("status", BkPaymentOperateStatus.UNCHECKED);
				
				Integer count = this.bkPaymentOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("??????????????????????????????????????????????????????");
				}
			} else if(BkPaymentOperateType.CLOSED.equals(cao.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("bkPayment", uuid);
				searchMap.put("type", BkPaymentOperateType.CLOSED);
				searchMap.put("status", BkPaymentOperateStatus.UNCHECKED);
				
				Integer count = this.bkPaymentOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("??????????????????????????????????????????????????????");
				}
			}
			cao.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cao.setStatus(BkPaymentOperateStatus.UNCHECKED);
			bkPaymentOperateService.update(cao);
			return ResultManager.createSuccessResult("?????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/operateCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "reason", message="????????????", type = DataType.STRING, maxLength = 500)
	@ResponseBody
	public Result operateCheck(String uuid, String status, String reason) {
		if(!BkPaymentOperateStatus.CHECKED.equals(status) && !BkPaymentOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("??????????????????");
		}
		
		//??????????????????????????????
		BkPaymentOperate cao = bkPaymentOperateService.get(uuid);
		if(cao != null){
			if(!BkPaymentOperateStatus.UNCHECKED.equals(cao.getStatus())){
				return ResultManager.createFailResult("???????????????????????????");
			}
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(cao.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("???????????????????????????????????????");
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
				return ResultManager.createFailResult("?????????????????????");
			}
			return ResultManager.createSuccessResult("?????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????????????????(??????)
	 * @return
	 */
	@RequestMapping(value = "/operateStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateStatusList() {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		//?????????????????????????????????
		List<Entity> list = bkPaymentOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//?????????????????????????????????
		List<Entity> list = bkPaymentOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????(??????)
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateTypeList(String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		List<Entity> list = bkPaymentOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", status);
		
		List<Entity> list = bkPaymentOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
