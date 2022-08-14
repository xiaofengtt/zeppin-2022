/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.math.BigDecimal;
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
import cn.zeppin.product.ntb.backadmin.vo.CouponVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.Coupon;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 * 后台优惠券信息管理
 */

@Controller
@RequestMapping(value = "/backadmin/coupon")
public class CouponController extends BaseController {
 
	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	/**
	 * 根据条件查询优惠券信息 
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
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, String type, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(type)){
			searchMap.put("couponType", type);
		}
		
		//查询符合条件的优惠券信息的总数
		Integer totalResultCount = couponService.getCount(searchMap);
		//查询符合条件的优惠券信息列表
		List<Entity> list = couponService.getListForPage(searchMap, pageNum, pageSize, sorts, Coupon.class);
		List<CouponVO> listVO = new ArrayList<CouponVO>();
		if(list != null && !list.isEmpty()){
			for(Entity entity : list){
				Coupon cp = (Coupon)entity;
				CouponVO cpvo = new CouponVO(cp);
				BkOperator operator = this.bkOperatorService.get(cp.getCreator());
				if(operator != null){
					cpvo.setCreatorCN(operator.getRealname());
				}
				listVO.add(cpvo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取优惠券信息分类型列表
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/typeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, maxLength = 10)
	@ResponseBody
	public Result typeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		List<Entity> list = couponService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取优惠券信息分状态列表
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, maxLength = 10)
	@ResponseBody
	public Result statusList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		List<Entity> list = couponService.getStatusList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取一条优惠券信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取优惠券信息
		Coupon cp = couponService.get(uuid);
		if (cp == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		//界面返回封装对象
		CouponVO cpvo = new CouponVO(cp);
		BkOperator operator = this.bkOperatorService.get(cp.getCreator());
		if(operator != null){
			cpvo.setCreatorCN(operator.getRealname());
		}
		
		return ResultManager.createDataResult(cpvo);
	}
	
	/**
	 * 添加一条优惠券信息
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "couponName", message="优惠券名称", type = DataType.STRING, required = true)
	@ActionParam(key = "couponType", message="优惠券类型", type = DataType.STRING, required = true)
	@ActionParam(key = "couponPrice", message="面值或加息", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAccount", message="最小投资额", type = DataType.NUMBER)
	@ActionParam(key = "expiryDate", message="截止时间", type = DataType.DATE)
	@ActionParam(key = "deadline", message="使用期限", type = DataType.NUMBER)
	@ResponseBody
	public Result add(String couponName, String couponType, BigDecimal couponPrice, BigDecimal minInvestAccount, String expiryDate, Integer deadline) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//创建优惠券信息
		Coupon cp = new Coupon();
		cp.setUuid(UUID.randomUUID().toString());
		cp.setCreator(currentOperator.getUuid());
		cp.setCreatetime(new Timestamp(System.currentTimeMillis()));
		cp.setStatus(CouponStatus.NORMAL);
		cp.setCouponName(couponName);
		cp.setCouponPrice(couponPrice);
		cp.setCouponType(couponType);
		minInvestAccount = minInvestAccount == null ? BigDecimal.ZERO : minInvestAccount;
		cp.setMinInvestAccount(minInvestAccount);
		if(expiryDate != null){
			cp.setExpiryDate(Timestamp.valueOf(expiryDate));
		} else {
			cp.setExpiryDate(null);
		}
		deadline = deadline == null ? 0 : deadline;
		cp.setDeadline(deadline);
		
		couponService.insert(cp);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑一条优惠券信息
	 * @param uuid
	 * @param name
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "couponName", message="优惠券名称", type = DataType.STRING, required = true)
	@ActionParam(key = "couponType", message="优惠券类型", type = DataType.STRING, required = true)
	@ActionParam(key = "couponPrice", message="面值或加息", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAccount", message="最小投资额", type = DataType.NUMBER)
	@ActionParam(key = "expiryDate", message="截止时间", type = DataType.DATE)
	@ActionParam(key = "deadline", message="使用期限", type = DataType.NUMBER)
	@ResponseBody
	public Result edit(String uuid, String couponName, String couponType, BigDecimal couponPrice, BigDecimal minInvestAccount, String expiryDate, Integer deadline) {
		
		//获取优惠券信息
		Coupon cp = couponService.get(uuid);
		if(cp != null && uuid.equals(cp.getUuid())){
			cp.setCouponName(couponName);
			cp.setCouponPrice(couponPrice);
			cp.setCouponType(couponType);
			minInvestAccount = minInvestAccount == null ? BigDecimal.ZERO : minInvestAccount;
			cp.setMinInvestAccount(minInvestAccount);
			if(expiryDate != null){
				cp.setExpiryDate(Timestamp.valueOf(expiryDate));
			} else {
				cp.setExpiryDate(null);
			}
			deadline = deadline == null ? 0 : deadline;
			cp.setDeadline(deadline);
			
			couponService.update(cp);
			
			return ResultManager.createSuccessResult("保存成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条优惠券信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//获取优惠券信息
		Coupon cp = couponService.get(uuid);
		if(cp != null && uuid.equals(cp.getUuid())){
			//删除优惠券信息
			cp.setStatus(CouponStatus.DELETED);
			couponService.update(cp);
			return ResultManager.createSuccessResult("删除成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
}
