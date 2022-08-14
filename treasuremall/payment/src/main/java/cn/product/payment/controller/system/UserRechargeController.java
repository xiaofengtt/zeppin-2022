/**
 * 
 */
package cn.product.payment.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.BaseController;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.entity.UserRecharge.UserRechargeStatus;
import cn.product.payment.service.AdminService;
import cn.product.payment.service.ChannelAccountService;
import cn.product.payment.service.ChannelService;
import cn.product.payment.service.CompanyAccountService;
import cn.product.payment.service.CompanyChannelService;
import cn.product.payment.service.CompanyService;
import cn.product.payment.service.UserRechargeService;
import cn.product.payment.vo.system.UserRechargeVO;


/**
 * 用户充值
 */

@Controller
@RequestMapping(value = "/system/userRecharge")
public class UserRechargeController extends BaseController {
 
	@Autowired
	private UserRechargeService userRechargeService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CompanyAccountService companyAccountService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ChannelAccountService channelAccountService;
	
	@Autowired
	private CompanyChannelService companyChannelService;
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 根据条件查询
	 * @param company
	 * @param channel
	 * @param companyChannel
	 * @param channelAccount
	 * @param orderNum
	 * @param companyOrderNum
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "company", message="商家", type = DataType.STRING)
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING)
	@ActionParam(key = "companyChannel", message="商家渠道", type = DataType.STRING)
	@ActionParam(key = "channelAccount", message="渠道账户", type = DataType.STRING)
	@ActionParam(key = "orderNum", message="平台订单号", type = DataType.STRING)
	@ActionParam(key = "companyOrderNum", message="商户订单号", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String company, String channel, String companyChannel, String channelAccount, String orderNum, String companyOrderNum, 
			String status, String starttime, String endtime, Integer pageNum, Integer pageSize, String sort) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", company);
		searchMap.put("channel", channel);
		searchMap.put("companyChannel", companyChannel);
		searchMap.put("channelAccount", channelAccount);
		searchMap.put("orderNum", orderNum);
		searchMap.put("companyOrderNum", companyOrderNum);
		searchMap.put("status", status);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalResultCount = userRechargeService.getCountByParams(searchMap);
		List<UserRecharge> list = userRechargeService.getListByParams(searchMap);
		
		List<UserRechargeVO> voList = new ArrayList<UserRechargeVO>();
		for(UserRecharge ur : list){
			UserRechargeVO vo = new UserRechargeVO(ur);
			
			Company c = this.companyService.get(ur.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
				vo.setCompanyCode(c.getCode());
			}
			
			CompanyAccount ca = this.companyAccountService.get(ur.getCompany());
			if(ca != null){
				vo.setCompanyBalance(ca.getBalance());
				vo.setCompanyBalanceLock(ca.getBalanceLock());
			}
			
			Channel ch = this.channelService.get(ur.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
				vo.setChannelCode(ch.getCode());
			}
			
			if(ur.getChannelAccount() != null){
				ChannelAccount cha = this.channelAccountService.get(ur.getChannelAccount());
				if(cha != null){
					vo.setChannelAccountName(cha.getName());
					vo.setChannelAccountNum(cha.getAccountNum());
				}
			}
			
			if(ur.getOperator() != null){
				Admin operator = this.adminService.get(ur.getOperator());
				if(operator != null){
					vo.setOperatorName(operator.getRealname());
				}
			}
			voList.add(vo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		UserRecharge ur = userRechargeService.get(uuid);
		if (ur == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		UserRechargeVO vo = new UserRechargeVO(ur);
		
		Company c = this.companyService.get(ur.getCompany());
		if(c != null){
			vo.setCompanyName(c.getName());
			vo.setCompanyCode(c.getCode());
		}
		
		Channel ch = this.channelService.get(ur.getChannel());
		if(ch != null){
			vo.setChannelName(ch.getName());
			vo.setChannelCode(ch.getCode());
		}
		
		if(ur.getChannelAccount() != null){
			ChannelAccount cha = this.channelAccountService.get(ur.getChannelAccount());
			if(cha != null){
				vo.setChannelAccountName(cha.getName());
				vo.setChannelAccountNum(cha.getAccountNum());
			}
		}
		
		ChannelAccount ca = this.channelAccountService.get(ur.getChannelAccount());
		if(ca != null){
			vo.setChannelAccountName(ca.getName());
			vo.setChannelAccountNum(ca.getAccountNum());
		}
		
		if(ur.getOperator() != null){
			Admin operator = this.adminService.get(ur.getOperator());
			if(operator != null){
				vo.setOperatorName(operator.getRealname());
			}
		}
		
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 异常订单处理
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result process(String uuid, String status) {
		Admin admin = getSystemAdmin();
		
		if(!UserRechargeStatus.SUCCESS.equals(status) && !UserRechargeStatus.CLOSE.equals(status)){
			return ResultManager.createFailResult("处理状态错误！");
		}
		
		UserRecharge ur = userRechargeService.get(uuid);
		if(ur == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		if(!UserRechargeStatus.FAIL.equals(ur.getStatus()) && !UserRechargeStatus.NORMAL.equals(ur.getStatus())){
			return ResultManager.createFailResult("该条记录无法处理！");
		}
		
		ur.setOperator(admin.getUuid());
		this.userRechargeService.processOrder(ur, null, status);
		
		return ResultManager.createSuccessResult("处理成功！");
	}
}
