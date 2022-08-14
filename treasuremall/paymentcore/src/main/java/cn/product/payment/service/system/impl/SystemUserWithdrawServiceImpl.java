/**
 * 
 */
package cn.product.payment.service.system.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.AdminDao;
import cn.product.payment.dao.ChannelAccountDailyDao;
import cn.product.payment.dao.ChannelAccountDao;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.dao.CompanyAccountDao;
import cn.product.payment.dao.CompanyChannelAccountDao;
import cn.product.payment.dao.CompanyChannelDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.dao.UserWithdrawDao;
import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.ChannelAccount.ChannelAccountStatus;
import cn.product.payment.entity.ChannelAccountDaily;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.CompanyChannel;
import cn.product.payment.entity.CompanyChannelAccount;
import cn.product.payment.entity.UserWithdraw;
import cn.product.payment.entity.UserWithdraw.UserWithdrawStatus;
import cn.product.payment.locker.Locker;
import cn.product.payment.locker.ZkCuratorLocker;
import cn.product.payment.service.system.SystemUserWithdrawService;
import cn.product.payment.util.api.PaymentException;
import cn.product.payment.vo.system.ChannelAccountVO;
import cn.product.payment.vo.system.StatusCountVO;
import cn.product.payment.vo.system.UserWithdrawVO;

/**
 * 用户提现
 */
@Service("systemUserWithdrawService")
public class SystemUserWithdrawServiceImpl implements SystemUserWithdrawService {
 
	@Autowired
	private UserWithdrawDao userWithdrawDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyAccountDao companyAccountDao;
	
	@Autowired
	private CompanyChannelAccountDao companyChannelAccountDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private ChannelAccountDailyDao channelAccountDailyDao;
	
	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
    private Locker locker;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		String channelAccount = paramsMap.get("channelAccount") == null ? null : paramsMap.get("channelAccount").toString();
		String company = paramsMap.get("company") == null ? null : paramsMap.get("company").toString();
		String companyChannel = paramsMap.get("companyChannel") == null ? null : paramsMap.get("companyChannel").toString();
		String orderNum = paramsMap.get("orderNum") == null ? null : paramsMap.get("orderNum").toString();
		String companyOrderNum = paramsMap.get("companyOrderNum") == null ? null : paramsMap.get("companyOrderNum").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String starttime = paramsMap.get("starttime") == null ? null : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? null : paramsMap.get("endtime").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", company);
		searchMap.put("channel", channel);
		searchMap.put("companyChannel", companyChannel);
		searchMap.put("channelAccount", channelAccount);
		searchMap.put("orderNumLike", orderNum);
		searchMap.put("companyOrderNumLike", companyOrderNum);
		searchMap.put("status", status);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = userWithdrawDao.getCountByParams(searchMap);
		List<UserWithdraw> list = userWithdrawDao.getListByParams(searchMap);
		
		List<UserWithdrawVO> voList = new ArrayList<UserWithdrawVO>();
		for(UserWithdraw uw : list){
			UserWithdrawVO vo = new UserWithdrawVO(uw);
			
			//商户
			Company c = this.companyDao.get(uw.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
				vo.setCompanyCode(c.getCode());
			}
			
			//商户账户
			CompanyAccount ca = this.companyAccountDao.get(uw.getCompany());
			if(ca != null){
				vo.setCompanyBalance(ca.getBalance());
				vo.setCompanyBalanceLock(ca.getBalanceLock());
			}
			
			//渠道
			Channel ch = this.channelDao.get(uw.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
				vo.setChannelCode(ch.getCode());
			}
			
			//渠道账户
			if(uw.getChannelAccount() != null){
				ChannelAccount cha = this.channelAccountDao.get(uw.getChannelAccount());
				if(cha != null){
					vo.setChannelAccountName(cha.getName());
					vo.setChannelAccountNum(cha.getAccountNum());
				}
			}
			
			//处理人
			if(uw.getOperator() != null){
				Admin operator = this.adminDao.get(uw.getOperator());
				if(operator != null){
					vo.setOperatorName(operator.getRealname());
				}
			}
			voList.add(vo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if (uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		UserWithdrawVO vo = new UserWithdrawVO(uw);
		
		//商户
		Company c = this.companyDao.get(uw.getCompany());
		if(c != null){
			vo.setCompanyName(c.getName());
			vo.setCompanyCode(c.getCode());
		}
		
		//渠道
		Channel ch = this.channelDao.get(uw.getChannel());
		if(ch != null){
			vo.setChannelName(ch.getName());
			vo.setChannelCode(ch.getCode());
		}
		
		//渠道账户
		if(uw.getChannelAccount() != null){
			ChannelAccount cha = this.channelAccountDao.get(uw.getChannelAccount());
			if(cha != null){
				vo.setChannelAccountName(cha.getName());
				vo.setChannelAccountNum(cha.getAccountNum());
			}
		}
		
		//商户账户
		CompanyAccount ca = this.companyAccountDao.get(uw.getCompany());
		if(ca != null){
			vo.setCompanyBalance(ca.getBalance());
			vo.setCompanyBalanceLock(ca.getBalanceLock());
		}
		
		//处理人
		if(uw.getOperator() != null){
			Admin operator = this.adminDao.get(uw.getOperator());
			if(operator != null){
				vo.setOperatorName(operator.getRealname());
			}
		}

		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	/**
	 * 可用的提现渠道账户列表
	 */
	@Override
	public void channelAccountList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String companyChannel = paramsMap.get("companyChannel").toString();
    	
    	CompanyChannel cc = this.companyChannelDao.get(companyChannel);
    	if(cc == null){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商户渠道不存在！");
			return;
    	}
    	
    	//取可用渠道账户数据
    	List<ChannelAccount> list = new ArrayList<ChannelAccount>();
    	Map<String, Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("companyChannel", companyChannel);
		List<CompanyChannelAccount> ccaList = this.companyChannelAccountDao.getListByParams(searchMap);
		if(ccaList.size() > 0){
			//如果有绑定的账户 取绑定账户中可用的
			searchMap.clear();
			searchMap.put("companyChannel", companyChannel);
			searchMap.put("status", ChannelAccountStatus.NORMAL);
			list = this.companyChannelAccountDao.getChannelAccountListByParams(searchMap);
		}else{
			//如果未绑定账户 取所有可用账户
			searchMap.clear();
			searchMap.put("channel", cc.getChannel());
			searchMap.put("status", ChannelAccountStatus.NORMAL);
			list = this.channelAccountDao.getListByParams(searchMap);
		}
		
		List<ChannelAccountVO> voList = new ArrayList<ChannelAccountVO>();
		for(ChannelAccount ca : list){
			ChannelAccountVO vo = new ChannelAccountVO(ca);
			
			//渠道
			Channel ch = this.channelDao.get(ca.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
			}
			voList.add(vo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	/**
	 * 平台管理员审核
	 */
	@Override
	public void check(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String channelAccount = paramsMap.get("channelAccount").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if(uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		//处理失败的和待审核的可审核
		if(!UserWithdrawStatus.FAIL.equals(uw.getStatus()) && !UserWithdrawStatus.CHECKING.equals(uw.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		//指定处理用的渠道账户
		ChannelAccount ca = this.channelAccountDao.get(channelAccount);
		if(ca == null || ChannelAccountStatus.DELETE.equals(ca.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户不存在！");
			return;
		}
		if(!ChannelAccountStatus.NORMAL.equals(ca.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户不可用！");
			return;
		}
		
		//手续费
		BigDecimal chaPoundage = BigDecimal.ZERO;
		if(ca.getPoundage() != null){
			//定额
			chaPoundage = ca.getPoundage();
		}
		if(ca.getPoundageRate() != null){
			//比率
			chaPoundage = uw.getAmount().multiply(ca.getPoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
		}
		
		//日额度
		ChannelAccountDaily cad = this.channelAccountDailyDao.get(channelAccount);
		if(cad != null){
			if(cad.getAmount().add(uw.getAmount().add(chaPoundage)).compareTo(ca.getDailyMax()) > 0){
				ca.setStatus(ChannelAccountStatus.SUSPEND);
				this.channelAccountDao.update(ca);
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("渠道账户当日额度不足！");
				return;
			}
		}
		
		//总额度
		if(ca.getBalance().subtract(uw.getAmount().add(chaPoundage)).abs().compareTo(ca.getTotalMax()) > 0){
			ca.setStatus(ChannelAccountStatus.DISABLE);
			this.channelAccountDao.update(ca);
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户总额度不足！");
			return;
		}
		
		
		uw.setChannelAccount(channelAccount);
		uw.setOperator(admin.getUuid());
		
		//入库
		try {
			this.userWithdrawDao.processOrder(uw, UserWithdrawStatus.CHECKED);
		} catch (PaymentException e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(e.getMessage());
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("审核成功！");
	}
	
	/**
	 * 平台管理员设为失败
	 */
	@Override
	public void fail(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String reason = paramsMap.get("reason").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if(uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		//待审核和已审核的可设为失败
		if(!UserWithdrawStatus.CHECKING.equals(uw.getStatus()) && !UserWithdrawStatus.CHECKED.equals(uw.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		uw.setFailReason(reason);
		uw.setOperator(admin.getUuid());
		
		//入库
		try {
			this.userWithdrawDao.processOrder(uw, UserWithdrawStatus.FAIL);
		} catch (PaymentException e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(e.getMessage());
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
	
	/**
	 * 平台管理员设为成功
	 */
	@Override
	public void success(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String proof = paramsMap.get("proof").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if(uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		//已审核的可设为成功
		if(!UserWithdrawStatus.CHECKED.equals(uw.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		uw.setOperator(admin.getUuid());
		uw.setProof(proof);
		
		//入库
		List<String> errorList = new ArrayList<String>();
		locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
			try {
				this.userWithdrawDao.processOrder(uw, UserWithdrawStatus.SUCCESS);
			} catch (PaymentException e) {
				errorList.add(e.getMessage());
				e.printStackTrace();
			}
		});
		if(errorList.size() > 0){
			//入库出错
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
	
	/**
	 * 按渠道取待处理数据数量
	 */
	@Override
	public void typeList(InputParams params, DataResult<Object> result) {
    	List<StatusCountVO> voList = this.userWithdrawDao.getCheckingChannelList();
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
