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
import cn.zeppin.product.ntb.backadmin.service.api.ICouponService;
import cn.zeppin.product.ntb.backadmin.service.api.ICouponStrategyOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.ICouponStrategyService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.CouponLessVO;
import cn.zeppin.product.ntb.backadmin.vo.CouponStrategyOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.CouponStrategyOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.CouponStrategyVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BkPaymentOperate.BkPaymentOperateStatus;
import cn.zeppin.product.ntb.core.entity.Coupon;
import cn.zeppin.product.ntb.core.entity.CouponStrategy;
import cn.zeppin.product.ntb.core.entity.CouponStrategy.CouponStrategyStatus;
import cn.zeppin.product.ntb.core.entity.CouponStrategyOperate;
import cn.zeppin.product.ntb.core.entity.CouponStrategyOperate.CouponStrategyOperateStatus;
import cn.zeppin.product.ntb.core.entity.CouponStrategyOperate.CouponStrategyOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author elegantclack
 * ?????????????????????????????????
 */

@Controller
@RequestMapping(value = "/backadmin/couponStrategy")
public class CouponStrategyController extends BaseController {

	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private ICouponStrategyService couponStrategyService;
	
	@Autowired
	private ICouponStrategyOperateService couponStrategyOperateService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	/**
	 * ??????????????????????????????????????????
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, maxLength = 20)
	@ResponseBody
	public Result statusList(String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		List<Entity> list = couponStrategyService.getStatusList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ??????????????????????????????????????? 
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
		
		//???????????????????????????????????????????????????
		Integer totalResultCount = couponStrategyService.getCount(searchMap);
		//????????????????????????????????????????????????
		List<Entity> list = couponStrategyService.getListForPage(searchMap, pageNum, pageSize, sorts, CouponStrategy.class);
		
		List<CouponStrategyVO> listvo = new ArrayList<CouponStrategyVO>();
		
		for(Entity e : list){
			CouponStrategy ca = (CouponStrategy) e;
			CouponStrategyVO cavo = new CouponStrategyVO(ca);
			
			BkOperator bo = this.bkOperatorService.get(ca.getCreator());
			if(bo != null){
				cavo.setCreatorCN(bo.getRealname());
			} else {
				cavo.setCreatorCN("--");
			}
			listvo.add(cavo);
		}
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	
	/**
	 * ?????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//???????????????????????????
		CouponStrategy couponStrategy = couponStrategyService.get(uuid);
		if (couponStrategy == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		
		//????????????
		CouponStrategyVO couponStrategyVO = new CouponStrategyVO(couponStrategy);
		
		BkOperator bo = this.bkOperatorService.get(couponStrategy.getCreator());
		if(bo != null){
			couponStrategyVO.setCreatorCN(bo.getRealname());
		} else {
			couponStrategyVO.setCreatorCN("--");
		}
		
		return ResultManager.createDataResult(couponStrategyVO);
	}
	
	/**
	 * ?????????????????????????????????
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param type
	 * @param bank
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "strategyIdentification", message="??????ID", type = DataType.STRING, required = true)
	@ActionParam(key = "expiryDate", message="????????????", type = DataType.DATE, required = true)
	@ActionParam(key = "coupon", message="???????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String strategyIdentification, String expiryDate, String coupon) {
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		if(this.couponStrategyService.isExistOperatorByStrategy(strategyIdentification, null)){
			return ResultManager.createFailResult("??????ID?????????");
		}
		try {
			CouponStrategy cs = new CouponStrategy();
			cs.setUuid(UUID.randomUUID().toString());
			cs.setCreator(currentOperator.getUuid());
			cs.setCreatetime(new Timestamp(System.currentTimeMillis()));
			cs.setStatus(CouponStrategyStatus.OPEN);
			
			cs.setStrategyIdentification(strategyIdentification);
			
			if(expiryDate != null){
				cs.setExpiryDate(Timestamp.valueOf(expiryDate));
			} else {
				cs.setExpiryDate(null);
			}
			
			Map<String, Object> couponMap = new HashMap<String, Object>();
			String[] couponArr = coupon.split(",");
			if(couponArr.length > 0){
				StringBuilder name = new StringBuilder();
				List<CouponLessVO> listvo = new ArrayList<CouponLessVO>();
				for(String couponStr : couponArr){
					String[] couponInfo = couponStr.split("_");
					if(couponInfo.length != 2){
						return ResultManager.createFailResult("??????????????????????????????????????????");
					}
					String couponUuid = couponInfo[0];
					String count = couponInfo[1];
					Coupon cp = this.couponService.get(couponUuid);
					if(cp != null){
						name.append(cp.getCouponName()+"+");
					} else {
						return ResultManager.createFailResult("?????????????????????????????????????????????");
					}
					CouponLessVO vo = new CouponLessVO(cp);
					vo.setCount(Integer.parseInt(count));
					listvo.add(vo);
				}
				name.delete(name.length() - 1, name.length());
				couponMap.put("name", name.toString());
				couponMap.put("couponList", listvo);
				
				//??????JSON?????????
				String couponJson = JSONUtils.obj2json(couponMap);
				if(couponJson != null){
					cs.setCoupon(couponJson);
				} else {
					return ResultManager.createFailResult("??????????????????????????????????????????");
				}
				
				//?????????????????????
				CouponStrategyOperate cso = new CouponStrategyOperate();
				cso.setUuid(UUID.randomUUID().toString());
				cso.setType(CouponStrategyOperateType.ADD);
				cso.setValue(JSONUtils.obj2json(cs));
				cso.setStatus(CouponStrategyOperateStatus.UNCHECKED);
				cso.setCreator(currentOperator.getUuid());
				cso.setSubmittime(new Timestamp(System.currentTimeMillis()));
				cso.setCreatetime(new Timestamp(System.currentTimeMillis()));
				couponStrategyOperateService.insert(cso);
				return ResultManager.createDataResult(cs, "???????????????????????????");
			} else {
				return ResultManager.createFailResult("??????????????????????????????????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????????????????");
		}
	}
	
	/**
	 * ?????????????????????????????????
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "strategyIdentification", message="??????ID", type = DataType.STRING, required = true)
	@ActionParam(key = "expiryDate", message="????????????", type = DataType.DATE, required = true)
	@ActionParam(key = "coupon", message="???????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String strategyIdentification, String expiryDate, String coupon) {
		
		//???????????????????????????
		CouponStrategy cs = couponStrategyService.get(uuid);
		if(cs != null && uuid.equals(cs.getUuid())){
			
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("couponStrategy", uuid);
			searchMap.put("type", CouponStrategyOperateType.EDIT);
			searchMap.put("status", CouponStrategyOperateStatus.UNCHECKED);
			
			Integer count = this.couponStrategyOperateService.getCount(searchMap);
			if(count > 0 ){
				return ResultManager.createFailResult("??????????????????????????????????????????????????????");
			}
			
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(this.couponStrategyService.isExistOperatorByStrategy(strategyIdentification, uuid)){
				return ResultManager.createFailResult("??????ID?????????");
			}
			try {
				cs.setStrategyIdentification(strategyIdentification);
				
				if(expiryDate != null){
					cs.setExpiryDate(Timestamp.valueOf(expiryDate));
				} else {
					cs.setExpiryDate(null);
				}
				
				Map<String, Object> couponMap = new HashMap<String, Object>();
				String[] couponArr = coupon.split(",");
				if(couponArr.length > 0){
					StringBuilder name = new StringBuilder();
					List<CouponLessVO> listvo = new ArrayList<CouponLessVO>();
					for(String couponStr : couponArr){
						String[] couponInfo = couponStr.split("_");
						if(couponInfo.length != 2){
							return ResultManager.createFailResult("??????????????????????????????????????????");
						}
						String couponUuid = couponInfo[0];
						String count1 = couponInfo[1];
						Coupon cp = this.couponService.get(couponUuid);
						if(cp != null){
							name.append(cp.getCouponName()+"+");
						} else {
							return ResultManager.createFailResult("?????????????????????????????????????????????");
						}
						CouponLessVO vo = new CouponLessVO(cp);
						vo.setCount(Integer.parseInt(count1));
						listvo.add(vo);
					}
					name.delete(name.length() - 1, name.length());
					couponMap.put("name", name.toString());
					couponMap.put("couponList", listvo);
					
					//??????JSON?????????
					String couponJson = JSONUtils.obj2json(couponMap);
					if(couponJson != null){
						cs.setCoupon(couponJson);
					} else {
						return ResultManager.createFailResult("??????????????????????????????????????????");
					}
					
					//?????????????????????
					CouponStrategyOperate cso = new CouponStrategyOperate();
					cso.setUuid(UUID.randomUUID().toString());
					cso.setCouponStrategy(cs.getUuid());
					cso.setType(CouponStrategyOperateType.EDIT);
					cso.setValue(JSONUtils.obj2json(cs));
					cso.setStatus(CouponStrategyOperateStatus.UNCHECKED);
					cso.setCreator(currentOperator.getUuid());
					cso.setSubmittime(new Timestamp(System.currentTimeMillis()));
					cso.setCreatetime(new Timestamp(System.currentTimeMillis()));
					couponStrategyOperateService.insert(cso);
					return ResultManager.createDataResult(cs, "???????????????????????????");
				} else {
					return ResultManager.createFailResult("??????????????????????????????????????????");
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("?????????????????????????????????");
			}
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	 
	
	/**
	 * ?????????????????????????????????
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
		
		//???????????????????????????
		CouponStrategy cs = couponStrategyService.get(uuid);
		if(cs != null && uuid.equals(cs.getUuid())){
			
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("couponStrategy", uuid);
			String operateType = "";
			if(switchFlag){
				cs.setStatus(CouponStrategyStatus.OPEN);
				operateType = CouponStrategyOperateType.OPEN;
			} else {
				cs.setStatus(CouponStrategyStatus.CLOSE);
				operateType = CouponStrategyOperateType.CLOSE;
			}
			searchMap.put("type", operateType);
			searchMap.put("status", CouponStrategyOperateStatus.UNCHECKED);
			
			Integer count = this.couponStrategyOperateService.getCount(searchMap);
			if(count > 0 ){
				return ResultManager.createFailResult("??????????????????????????????????????????????????????");
			}
			
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			
			
			CouponStrategyOperate cso = new CouponStrategyOperate();
			cso.setUuid(UUID.randomUUID().toString());
			cso.setCouponStrategy(cs.getUuid());
			cso.setType(operateType);
			cso.setValue(JSONUtils.obj2json(cs));
			cso.setStatus(CouponStrategyOperateStatus.UNCHECKED);
			cso.setCreator(currentOperator.getUuid());
			cso.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cso.setCreatetime(new Timestamp(System.currentTimeMillis()));
			couponStrategyOperateService.insert(cso);
			return ResultManager.createSuccessResult("???????????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ?????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		//???????????????????????????
		CouponStrategy cs = couponStrategyService.get(uuid);
		if(cs != null && uuid.equals(cs.getUuid())){
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			CouponStrategyOperate cso = new CouponStrategyOperate();
			cso.setUuid(UUID.randomUUID().toString());
			cso.setCouponStrategy(cs.getUuid());
			cso.setType(CouponStrategyOperateType.DELETE);
			cso.setValue(JSONUtils.obj2json(cs));
			cso.setStatus(BkPaymentOperateStatus.UNCHECKED);
			cso.setCreator(currentOperator.getUuid());
			cso.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cso.setCreatetime(new Timestamp(System.currentTimeMillis()));
			couponStrategyOperateService.insert(cso);
			return ResultManager.createSuccessResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????(??????)
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
		
		//???????????????????????????????????????????????????
		Integer totalResultCount = couponStrategyOperateService.getCount(searchMap);
		//????????????????????????????????????????????????
		List<Entity> list = couponStrategyOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, CouponStrategyOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				CouponStrategyOperate cao = (CouponStrategyOperate)e;
				CouponStrategyOperateVO caoVO = new CouponStrategyOperateVO(cao);
				if(CouponStrategyOperateType.ADD.equals(cao.getType())){
					CouponStrategy ca = JSONUtils.json2obj(cao.getValue(), CouponStrategy.class);
					caoVO.setCouponStrategy(ca.getUuid());
					caoVO.setStrategyIdentification(ca.getStrategyIdentification());
					String mapJson = ca.getCoupon();
					Map<String, Object> mapCs = JSONUtils.json2map(mapJson);
					String couponStrategyName = mapCs.get("name") == null ? "" : mapCs.get("name").toString();
					caoVO.setCouponStrategyName(couponStrategyName);
				}
				if(caoVO.getCouponStrategy() != null && !"".equals(caoVO.getCouponStrategy())){
					CouponStrategy ca = this.couponStrategyService.get(caoVO.getCouponStrategy());
					if(ca != null){
						caoVO.setCouponStrategy(ca.getUuid());
						caoVO.setStrategyIdentification(ca.getStrategyIdentification());
						String mapJson = ca.getCoupon();
						Map<String, Object> mapCs = JSONUtils.json2map(mapJson);
						String couponStrategyName = mapCs.get("name") == null ? "" : mapCs.get("name").toString();
						caoVO.setCouponStrategyName(couponStrategyName);
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
	 * ???????????????????????????(?????????)
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
		//???????????????????????????????????????????????????
		Integer totalResultCount = couponStrategyOperateService.getCount(searchMap);
		//????????????????????????????????????????????????
		List<Entity> list = couponStrategyOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, CouponStrategyOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				CouponStrategyOperate cao = (CouponStrategyOperate)e;
				CouponStrategyOperateVO caoVO = new CouponStrategyOperateVO(cao);
				if(CouponStrategyOperateType.ADD.equals(cao.getType())){
					CouponStrategy ca = JSONUtils.json2obj(cao.getValue(), CouponStrategy.class);
					caoVO.setCouponStrategy(ca.getUuid());
					caoVO.setStrategyIdentification(ca.getStrategyIdentification());
					String mapJson = ca.getCoupon();
					Map<String, Object> mapCs = JSONUtils.json2map(mapJson);
					String couponStrategyName = mapCs.get("name") == null ? "" : mapCs.get("name").toString();
					caoVO.setCouponStrategyName(couponStrategyName);
				}
				if(caoVO.getCouponStrategy() != null && !"".equals(caoVO.getCouponStrategy())){
					CouponStrategy ca = this.couponStrategyService.get(caoVO.getCouponStrategy());
					if(ca != null){
						caoVO.setCouponStrategy(ca.getUuid());
						caoVO.setStrategyIdentification(ca.getStrategyIdentification());
						String mapJson = ca.getCoupon();
						Map<String, Object> mapCs = JSONUtils.json2map(mapJson);
						String couponStrategyName = mapCs.get("name") == null ? "" : mapCs.get("name").toString();
						caoVO.setCouponStrategyName(couponStrategyName);
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
	
	/** ???????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//???????????????????????????
		CouponStrategyOperate cao = couponStrategyOperateService.get(uuid);
		if (cao == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		CouponStrategyOperateDetailVO caodVO = new CouponStrategyOperateDetailVO(cao);
		BkOperator creator = this.bkOperatorService.get(cao.getCreator());
		if(creator != null){
			caodVO.setCreatorName(creator.getRealname());
		}
		
		if(!CouponStrategyOperateType.ADD.equals(cao.getType())){
			if(CouponStrategyOperateType.EDIT.equals(cao.getType()) && CouponStrategyOperateStatus.CHECKED.equals(cao.getStatus()) 
					&& cao.getOld() != null && !"".equals(cao.getOld())){
				
				CouponStrategy ca = JSONUtils.json2obj(cao.getOld(), CouponStrategy.class);	
				CouponStrategyVO cadVO = new CouponStrategyVO(ca);
				if(cadVO.getCreator() != null){
					BkOperator op = this.bkOperatorService.get(cadVO.getCreator());
					if(op != null){
						cadVO.setCreatorCN(op.getRealname());
					}
				}
				caodVO.setOldData(cadVO);
			}else{
				CouponStrategy ca = couponStrategyService.get(cao.getCouponStrategy());
				CouponStrategyVO cadVO = new CouponStrategyVO(ca);
				if(cadVO.getCreator() != null){
					BkOperator op = this.bkOperatorService.get(cadVO.getCreator());
					if(op != null){
						cadVO.setCreatorCN(op.getRealname());
					}
				}
				caodVO.setOldData(cadVO);
			}
		}
		if(CouponStrategyOperateType.ADD.equals(cao.getType()) || CouponStrategyOperateType.EDIT.equals(cao.getType())
				 || CouponStrategyOperateType.CLOSE.equals(cao.getType()) || CouponStrategyOperateType.OPEN.equals(cao.getType())){
			CouponStrategy ca = JSONUtils.json2obj(cao.getValue(), CouponStrategy.class);	
			CouponStrategyVO cadVO = new CouponStrategyVO(ca);
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
	 * ???????????????????????????????????????
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "strategyIdentification", message="??????ID", type = DataType.STRING, required = true)
	@ActionParam(key = "expiryDate", message="????????????", type = DataType.DATE, required = true)
	@ActionParam(key = "coupon", message="???????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateEdit(String uuid, String strategyIdentification, String expiryDate, String coupon){
		//?????????????????????????????????
		CouponStrategyOperate cao = couponStrategyOperateService.get(uuid);
		if (cao != null) {
			if(this.couponStrategyService.isExistOperatorByStrategy(strategyIdentification, uuid)){
				return ResultManager.createFailResult("??????ID?????????");
			}
			
			CouponStrategy cs = JSONUtils.json2obj(cao.getValue(), CouponStrategy.class);
			
			cs.setStrategyIdentification(strategyIdentification);
			if(expiryDate != null){
				cs.setExpiryDate(Timestamp.valueOf(expiryDate));
			} else {
				cs.setExpiryDate(null);
			}
			
			Map<String, Object> couponMap = new HashMap<String, Object>();
			String[] couponArr = coupon.split(",");
			if(couponArr.length > 0){
				StringBuilder name = new StringBuilder();
				List<CouponLessVO> listvo = new ArrayList<CouponLessVO>();
				for(String couponStr : couponArr){
					String[] couponInfo = couponStr.split("_");
					if(couponInfo.length != 2){
						return ResultManager.createFailResult("??????????????????????????????????????????");
					}
					String couponUuid = couponInfo[0];
					String count1 = couponInfo[1];
					Coupon cp = this.couponService.get(couponUuid);
					if(cp != null){
						name.append(cp.getCouponName()+"+");
					} else {
						return ResultManager.createFailResult("?????????????????????????????????????????????");
					}
					CouponLessVO vo = new CouponLessVO(cp);
					vo.setCount(Integer.parseInt(count1));
					listvo.add(vo);
				}
				name.delete(name.length() - 1, name.length());
				couponMap.put("name", name.toString());
				couponMap.put("couponList", listvo);
				
				//??????JSON?????????
				String couponJson = JSONUtils.obj2json(couponMap);
				if(couponJson != null){
					cs.setCoupon(couponJson);
				} else {
					return ResultManager.createFailResult("??????????????????????????????????????????");
				}
			}
			//?????????????????????
			cao.setValue(JSONUtils.obj2json(cs));
			cao.setCreatetime(new Timestamp(System.currentTimeMillis()));
			couponStrategyOperateService.update(cao);
			return ResultManager.createSuccessResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//?????????????????????????????????
		CouponStrategyOperate cao = couponStrategyOperateService.get(uuid);
		if(cao != null){
			if(!CouponStrategyOperateStatus.DRAFT.equals(cao.getStatus()) && !CouponStrategyOperateStatus.UNPASSED.equals(cao.getStatus())){
				return ResultManager.createFailResult("??????????????????");
			}
			cao.setStatus(CouponStrategyOperateStatus.DELETED);
			couponStrategyOperateService.update(cao);
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
		//?????????????????????????????????
		CouponStrategyOperate cao = couponStrategyOperateService.get(uuid);
		if(cao != null){
			if(CouponStrategyOperateStatus.CHECKED.equals(cao.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			if(CouponStrategyOperateType.EDIT.equals(cao.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("couponStrategy", cao.getCouponStrategy());
				searchMap.put("type", cao.getType());
				searchMap.put("status", CouponStrategyOperateStatus.UNCHECKED);
				
				Integer count = this.couponStrategyOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("??????????????????????????????????????????????????????");
				}
			}
			cao.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cao.setStatus(CouponStrategyOperateStatus.UNCHECKED);
			couponStrategyOperateService.update(cao);
			return ResultManager.createSuccessResult("?????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????????????????
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
		if(!CouponStrategyOperateStatus.CHECKED.equals(status) && !CouponStrategyOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("??????????????????");
		}
		
		//?????????????????????????????????
		CouponStrategyOperate cao = couponStrategyOperateService.get(uuid);
		if(cao != null){
			if(!CouponStrategyOperateStatus.UNCHECKED.equals(cao.getStatus())){
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
				couponStrategyOperateService.check(cao);
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
	 * ??????????????????????????????????????????(??????)
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
		//????????????????????????????????????
		List<Entity> list = couponStrategyOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ??????????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//????????????????????????????????????
		List<Entity> list = couponStrategyOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ??????????????????????????????????????????(??????)
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
		
		List<Entity> list = couponStrategyOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ??????????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", status);
		
		List<Entity> list = couponStrategyOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
