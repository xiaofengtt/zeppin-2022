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
 * 后台优惠券策略信息管理
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
	 * 获取优惠券策略信息分类型列表
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, maxLength = 20)
	@ResponseBody
	public Result statusList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		List<Entity> list = couponStrategyService.getStatusList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 根据条件查询优惠券策略信息 
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
		
		//查询符合条件的优惠券策略信息的总数
		Integer totalResultCount = couponStrategyService.getCount(searchMap);
		//查询符合条件的优惠券策略信息列表
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
	 * 获取一条优惠券策略信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取优惠券策略信息
		CouponStrategy couponStrategy = couponStrategyService.get(uuid);
		if (couponStrategy == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		//封装对象
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
	 * 添加一条优惠券策略信息
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param type
	 * @param bank
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "strategyIdentification", message="投放ID", type = DataType.STRING, required = true)
	@ActionParam(key = "expiryDate", message="截止时间", type = DataType.DATE, required = true)
	@ActionParam(key = "coupon", message="优惠券信息", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String strategyIdentification, String expiryDate, String coupon) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		if(this.couponStrategyService.isExistOperatorByStrategy(strategyIdentification, null)){
			return ResultManager.createFailResult("投放ID已存在");
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
						return ResultManager.createFailResult("优惠券信息错误，请重新操作！");
					}
					String couponUuid = couponInfo[0];
					String count = couponInfo[1];
					Coupon cp = this.couponService.get(couponUuid);
					if(cp != null){
						name.append(cp.getCouponName()+"+");
					} else {
						return ResultManager.createFailResult("优惠券信息不存在，请重新操作！");
					}
					CouponLessVO vo = new CouponLessVO(cp);
					vo.setCount(Integer.parseInt(count));
					listvo.add(vo);
				}
				name.delete(name.length() - 1, name.length());
				couponMap.put("name", name.toString());
				couponMap.put("couponList", listvo);
				
				//转成JSON字符串
				String couponJson = JSONUtils.obj2json(couponMap);
				if(couponJson != null){
					cs.setCoupon(couponJson);
				} else {
					return ResultManager.createFailResult("数据解析错误，请刷新后重试！");
				}
				
				//添加待审核记录
				CouponStrategyOperate cso = new CouponStrategyOperate();
				cso.setUuid(UUID.randomUUID().toString());
				cso.setType(CouponStrategyOperateType.ADD);
				cso.setValue(JSONUtils.obj2json(cs));
				cso.setStatus(CouponStrategyOperateStatus.UNCHECKED);
				cso.setCreator(currentOperator.getUuid());
				cso.setSubmittime(new Timestamp(System.currentTimeMillis()));
				cso.setCreatetime(new Timestamp(System.currentTimeMillis()));
				couponStrategyOperateService.insert(cso);
				return ResultManager.createDataResult(cs, "添加待审记录成功！");
			} else {
				return ResultManager.createFailResult("优惠券信息有误，请重新操作！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常，请重新操作！");
		}
	}
	
	/**
	 * 编辑一条优惠券策略信息
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "strategyIdentification", message="投放ID", type = DataType.STRING, required = true)
	@ActionParam(key = "expiryDate", message="截止时间", type = DataType.DATE, required = true)
	@ActionParam(key = "coupon", message="优惠券信息", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String strategyIdentification, String expiryDate, String coupon) {
		
		//获取优惠券策略信息
		CouponStrategy cs = couponStrategyService.get(uuid);
		if(cs != null && uuid.equals(cs.getUuid())){
			
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("couponStrategy", uuid);
			searchMap.put("type", CouponStrategyOperateType.EDIT);
			searchMap.put("status", CouponStrategyOperateStatus.UNCHECKED);
			
			Integer count = this.couponStrategyOperateService.getCount(searchMap);
			if(count > 0 ){
				return ResultManager.createFailResult("该条数据有其他修改操作正在等待审核！");
			}
			
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(this.couponStrategyService.isExistOperatorByStrategy(strategyIdentification, uuid)){
				return ResultManager.createFailResult("投放ID已存在");
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
							return ResultManager.createFailResult("优惠券信息错误，请重新操作！");
						}
						String couponUuid = couponInfo[0];
						String count1 = couponInfo[1];
						Coupon cp = this.couponService.get(couponUuid);
						if(cp != null){
							name.append(cp.getCouponName()+"+");
						} else {
							return ResultManager.createFailResult("优惠券信息不存在，请重新操作！");
						}
						CouponLessVO vo = new CouponLessVO(cp);
						vo.setCount(Integer.parseInt(count1));
						listvo.add(vo);
					}
					name.delete(name.length() - 1, name.length());
					couponMap.put("name", name.toString());
					couponMap.put("couponList", listvo);
					
					//转成JSON字符串
					String couponJson = JSONUtils.obj2json(couponMap);
					if(couponJson != null){
						cs.setCoupon(couponJson);
					} else {
						return ResultManager.createFailResult("数据解析错误，请刷新后重试！");
					}
					
					//添加待审核记录
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
					return ResultManager.createDataResult(cs, "添加待审记录成功！");
				} else {
					return ResultManager.createFailResult("优惠券信息有误，请重新操作！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("操作异常，请重新操作！");
			}
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	 
	
	/**
	 * 编辑一条优惠券策略信息
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
		
		//获取优惠券策略信息
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
				return ResultManager.createFailResult("该条数据有其他修改操作正在等待审核！");
			}
			
			//取管理员信息
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
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条优惠券策略信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		//获取优惠券策略信息
		CouponStrategy cs = couponStrategyService.get(uuid);
		if(cs != null && uuid.equals(cs.getUuid())){
			//取管理员信息
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
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 优惠券策略操作列表(编辑)
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
		
		//查询符合条件的优惠券策略信息的总数
		Integer totalResultCount = couponStrategyOperateService.getCount(searchMap);
		//查询符合条件的优惠券策略信息列表
		List<Entity> list = couponStrategyOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, CouponStrategyOperate.class);
		
		//封装可用信息到前台List
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
	 * 优惠券策略操作列表(管理员)
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
		//查询符合条件的优惠券策略信息的总数
		Integer totalResultCount = couponStrategyOperateService.getCount(searchMap);
		//查询符合条件的优惠券策略信息列表
		List<Entity> list = couponStrategyOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, CouponStrategyOperate.class);
		
		//封装可用信息到前台List
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
	
	/** 获取优惠券策略操作审核信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//获取优惠券策略信息
		CouponStrategyOperate cao = couponStrategyOperateService.get(uuid);
		if (cao == null) {
			return ResultManager.createFailResult("该条数据不存在！");
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
	 * 编辑一条优惠券策略操作信息
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "strategyIdentification", message="投放ID", type = DataType.STRING, required = true)
	@ActionParam(key = "expiryDate", message="截止时间", type = DataType.DATE, required = true)
	@ActionParam(key = "coupon", message="优惠券信息", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateEdit(String uuid, String strategyIdentification, String expiryDate, String coupon){
		//获取优惠券策略操作信息
		CouponStrategyOperate cao = couponStrategyOperateService.get(uuid);
		if (cao != null) {
			if(this.couponStrategyService.isExistOperatorByStrategy(strategyIdentification, uuid)){
				return ResultManager.createFailResult("投放ID已存在");
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
						return ResultManager.createFailResult("优惠券信息错误，请重新操作！");
					}
					String couponUuid = couponInfo[0];
					String count1 = couponInfo[1];
					Coupon cp = this.couponService.get(couponUuid);
					if(cp != null){
						name.append(cp.getCouponName()+"+");
					} else {
						return ResultManager.createFailResult("优惠券信息不存在，请重新操作！");
					}
					CouponLessVO vo = new CouponLessVO(cp);
					vo.setCount(Integer.parseInt(count1));
					listvo.add(vo);
				}
				name.delete(name.length() - 1, name.length());
				couponMap.put("name", name.toString());
				couponMap.put("couponList", listvo);
				
				//转成JSON字符串
				String couponJson = JSONUtils.obj2json(couponMap);
				if(couponJson != null){
					cs.setCoupon(couponJson);
				} else {
					return ResultManager.createFailResult("数据解析错误，请刷新后重试！");
				}
			}
			//修改待审核记录
			cao.setValue(JSONUtils.obj2json(cs));
			cao.setCreatetime(new Timestamp(System.currentTimeMillis()));
			couponStrategyOperateService.update(cao);
			return ResultManager.createSuccessResult("修改待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条优惠券策略操作信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//获取优惠券策略操作信息
		CouponStrategyOperate cao = couponStrategyOperateService.get(uuid);
		if(cao != null){
			if(!CouponStrategyOperateStatus.DRAFT.equals(cao.getStatus()) && !CouponStrategyOperateStatus.UNPASSED.equals(cao.getStatus())){
				return ResultManager.createFailResult("审核状态错误");
			}
			cao.setStatus(CouponStrategyOperateStatus.DELETED);
			couponStrategyOperateService.update(cao);
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
		//获取优惠券策略操作信息
		CouponStrategyOperate cao = couponStrategyOperateService.get(uuid);
		if(cao != null){
			if(CouponStrategyOperateStatus.CHECKED.equals(cao.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			if(CouponStrategyOperateType.EDIT.equals(cao.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("couponStrategy", cao.getCouponStrategy());
				searchMap.put("type", cao.getType());
				searchMap.put("status", CouponStrategyOperateStatus.UNCHECKED);
				
				Integer count = this.couponStrategyOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("该条数据有其他修改操作正在等待审核！");
				}
			}
			cao.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cao.setStatus(CouponStrategyOperateStatus.UNCHECKED);
			couponStrategyOperateService.update(cao);
			return ResultManager.createSuccessResult("提交审核成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 审核优惠券策略操作修改操作
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
		if(!CouponStrategyOperateStatus.CHECKED.equals(status) && !CouponStrategyOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("审核状态错误");
		}
		
		//获取优惠券策略操作信息
		CouponStrategyOperate cao = couponStrategyOperateService.get(uuid);
		if(cao != null){
			if(!CouponStrategyOperateStatus.UNCHECKED.equals(cao.getStatus())){
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
				couponStrategyOperateService.check(cao);
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
	 * 获取优惠券策略操作分状态列表(编辑)
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
		//获取优惠券策略分状态信息
		List<Entity> list = couponStrategyOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取优惠券策略操作分状态列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//获取优惠券策略分状态信息
		List<Entity> list = couponStrategyOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取优惠券策略操作分类型列表(编辑)
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
		
		List<Entity> list = couponStrategyOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取优惠券策略操作分类型列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", status);
		
		List<Entity> list = couponStrategyOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
