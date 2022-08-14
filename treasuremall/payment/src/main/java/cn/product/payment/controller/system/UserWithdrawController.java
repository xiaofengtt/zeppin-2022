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
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.ChannelAccount.ChannelAccountStatus;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.Resource;
import cn.product.payment.entity.UserWithdraw;
import cn.product.payment.entity.UserWithdraw.UserWithdrawStatus;
import cn.product.payment.service.AdminService;
import cn.product.payment.service.ChannelAccountService;
import cn.product.payment.service.ChannelService;
import cn.product.payment.service.CompanyAccountService;
import cn.product.payment.service.CompanyChannelService;
import cn.product.payment.service.CompanyService;
import cn.product.payment.service.ResourceService;
import cn.product.payment.service.UserWithdrawService;
import cn.product.payment.util.api.PaymentException;
import cn.product.payment.vo.system.UserWithdrawVO;


/**
 * 用户提现
 */

@Controller
@RequestMapping(value = "/system/userWithdraw")
public class UserWithdrawController extends BaseController {
 
	@Autowired
	private UserWithdrawService userWithdrawService;
	
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
	
	@Autowired
	private ResourceService resourceService;
	
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
		
		Integer totalResultCount = userWithdrawService.getCountByParams(searchMap);
		List<UserWithdraw> list = userWithdrawService.getListByParams(searchMap);
		
		List<UserWithdrawVO> voList = new ArrayList<UserWithdrawVO>();
		for(UserWithdraw uw : list){
			UserWithdrawVO vo = new UserWithdrawVO(uw);
			
			Company c = this.companyService.get(uw.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
				vo.setCompanyCode(c.getCode());
			}

			CompanyAccount ca = this.companyAccountService.get(uw.getCompany());
			if(ca != null){
				vo.setCompanyBalance(ca.getBalance());
				vo.setCompanyBalanceLock(ca.getBalanceLock());
			}
			
			Channel ch = this.channelService.get(uw.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
				vo.setChannelCode(ch.getCode());
			}
			
			if(uw.getChannelAccount() != null){
				ChannelAccount cha = this.channelAccountService.get(uw.getChannelAccount());
				if(cha != null){
					vo.setChannelAccountName(cha.getName());
					vo.setChannelAccountNum(cha.getAccountNum());
				}
			}
			
			if(uw.getOperator() != null){
				Admin operator = this.adminService.get(uw.getOperator());
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
		UserWithdraw uw = userWithdrawService.get(uuid);
		if (uw == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		UserWithdrawVO vo = new UserWithdrawVO(uw);
		
		Company c = this.companyService.get(uw.getCompany());
		if(c != null){
			vo.setCompanyName(c.getName());
			vo.setCompanyCode(c.getCode());
		}
		
		Channel ch = this.channelService.get(uw.getChannel());
		if(ch != null){
			vo.setChannelName(ch.getName());
			vo.setChannelCode(ch.getCode());
		}
		
		if(uw.getChannelAccount() != null){
			ChannelAccount cha = this.channelAccountService.get(uw.getChannelAccount());
			if(cha != null){
				vo.setChannelAccountName(cha.getName());
				vo.setChannelAccountNum(cha.getAccountNum());
			}
		}
		
		CompanyAccount ca = this.companyAccountService.get(uw.getCompany());
		if(ca != null){
			vo.setCompanyBalance(ca.getBalance());
			vo.setCompanyBalanceLock(ca.getBalanceLock());
		}
		
		if(uw.getOperator() != null){
			Admin operator = this.adminService.get(uw.getOperator());
			if(operator != null){
				vo.setOperatorName(operator.getRealname());
			}
			
			if(uw.getProof() != null){
				Resource proof = this.resourceService.get(uw.getProof());
				if(proof != null){
					vo.setProofUrl(proof.getUrl());
				}
			}
		}
		
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 审核
	 * @param uuid
	 * @param channelAccount
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "channelAccount", message="渠道账户", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result check(String uuid, String channelAccount) {
		Admin admin = getSystemAdmin();
		
		UserWithdraw uw = userWithdrawService.get(uuid);
		if(uw == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		if(!UserWithdrawStatus.FAIL.equals(uw.getStatus()) && !UserWithdrawStatus.CHECKING.equals(uw.getStatus())){
			return ResultManager.createFailResult("该条记录无法处理！");
		}
		
		ChannelAccount ca = this.channelAccountService.get(channelAccount);
		if(ca == null || ChannelAccountStatus.DELETE.equals(ca.getStatus())){
			return ResultManager.createFailResult("渠道账户不存在！");
		}
		if(ChannelAccountStatus.DISABLE.equals(ca.getStatus())){
			return ResultManager.createFailResult("渠道账户不可用！");
		}
		
		uw.setChannelAccount(channelAccount);
		uw.setOperator(admin.getUuid());
		try {
			this.userWithdrawService.processOrder(uw, UserWithdrawStatus.CHECKED);
		} catch (PaymentException e) {
			return ResultManager.createFailResult(e.getMessage());
		}
		
		return ResultManager.createSuccessResult("审核成功！");
	}
	
	/**
	 * 设为失败
	 * @param uuid
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/fail", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "reason", message="失败原因", type = DataType.STRING, required = true, maxLength = 100)
	@ResponseBody
	public Result fail(String uuid, String reason) {
		Admin admin = getSystemAdmin();
		
		UserWithdraw uw = userWithdrawService.get(uuid);
		if(uw == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		if(!UserWithdrawStatus.CHECKING.equals(uw.getStatus()) && !UserWithdrawStatus.CHECKED.equals(uw.getStatus())){
			return ResultManager.createFailResult("该条记录无法处理！");
		}
		
		uw.setFailReason(reason);
		uw.setOperator(admin.getUuid());
		try {
			this.userWithdrawService.processOrder(uw, UserWithdrawStatus.FAIL);
		} catch (PaymentException e) {
			return ResultManager.createFailResult(e.getMessage());
		}
		
		return ResultManager.createSuccessResult("操作成功！");
	}
	
	/**
	 * 提现完成
	 * @param uuid
	 * @param proof
	 * @return
	 */
	@RequestMapping(value = "/success", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "proof", message="凭证", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result success(String uuid, String proof) {
		Admin admin = getSystemAdmin();
		
		UserWithdraw uw = userWithdrawService.get(uuid);
		if(uw == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		if(!UserWithdrawStatus.CHECKED.equals(uw.getStatus())){
			return ResultManager.createFailResult("该条记录无法处理！");
		}
		
		uw.setOperator(admin.getUuid());
		uw.setProof(proof);
		try {
			this.userWithdrawService.processOrder(uw, UserWithdrawStatus.SUCCESS);
		} catch (PaymentException e) {
			return ResultManager.createFailResult(e.getMessage());
		}
		
		return ResultManager.createSuccessResult("操作成功！");
	}
}
