/**
 * 
 */
package cn.product.payment.service.system.impl;

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
import cn.product.payment.dao.ChannelAccountDao;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.dao.CompanyAccountDao;
import cn.product.payment.dao.CompanyChannelDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.dao.UserRechargeDao;
import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.entity.UserRecharge.UserRechargeStatus;
import cn.product.payment.locker.Locker;
import cn.product.payment.locker.ZkCuratorLocker;
import cn.product.payment.service.system.SystemUserRechargeService;
import cn.product.payment.vo.system.StatusCountVO;
import cn.product.payment.vo.system.UserRechargeVO;

/**
 * 用户充值
 */
@Service("systemUserRechargeService")
public class SystemUserRechargeServiceImpl implements SystemUserRechargeService {
 
	@Autowired
	private UserRechargeDao userRechargeDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyAccountDao companyAccountDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
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
		String transData = paramsMap.get("transData") == null ? null : paramsMap.get("transData").toString();
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
		searchMap.put("transData", transData);
		searchMap.put("status", status);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = userRechargeDao.getCountByParams(searchMap);
		List<UserRecharge> list = userRechargeDao.getListByParams(searchMap);
		
		List<UserRechargeVO> voList = new ArrayList<UserRechargeVO>();
		for(UserRecharge ur : list){
			UserRechargeVO vo = new UserRechargeVO(ur);
			
			//商户
			Company c = this.companyDao.get(ur.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
				vo.setCompanyCode(c.getCode());
			}
			
			//商户账户
			CompanyAccount ca = this.companyAccountDao.get(ur.getCompany());
			if(ca != null){
				vo.setCompanyBalance(ca.getBalance());
				vo.setCompanyBalanceLock(ca.getBalanceLock());
			}

			//渠道
			Channel ch = this.channelDao.get(ur.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
				vo.setChannelCode(ch.getCode());
			}
			
			//渠道账户
			if(ur.getChannelAccount() != null){
				ChannelAccount cha = this.channelAccountDao.get(ur.getChannelAccount());
				if(cha != null){
					vo.setChannelAccountName(cha.getName());
					vo.setChannelAccountNum(cha.getAccountNum());
				}
			}
			
			//处理人
			if(ur.getOperator() != null){
				Admin operator = this.adminDao.get(ur.getOperator());
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
    	
		UserRecharge ur = userRechargeDao.get(uuid);
		if (ur == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		UserRechargeVO vo = new UserRechargeVO(ur);
		
		//商户
		Company c = this.companyDao.get(ur.getCompany());
		if(c != null){
			vo.setCompanyName(c.getName());
			vo.setCompanyCode(c.getCode());
		}
		
		//渠道
		Channel ch = this.channelDao.get(ur.getChannel());
		if(ch != null){
			vo.setChannelName(ch.getName());
			vo.setChannelCode(ch.getCode());
		}
		
		//渠道账户
		if(ur.getChannelAccount() != null){
			ChannelAccount cha = this.channelAccountDao.get(ur.getChannelAccount());
			if(cha != null){
				vo.setChannelAccountName(cha.getName());
				vo.setChannelAccountNum(cha.getAccountNum());
			}
		}

		//商户账户
		CompanyAccount ca = this.companyAccountDao.get(ur.getCompany());
		if(ca != null){
			vo.setCompanyBalance(ca.getBalance());
			vo.setCompanyBalanceLock(ca.getBalanceLock());
		}
		
		//处理人
		if(ur.getOperator() != null){
			Admin operator = this.adminDao.get(ur.getOperator());
			if(operator != null){
				vo.setOperatorName(operator.getRealname());
			}
		}
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
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
		
		UserRecharge ur = userRechargeDao.get(uuid);
		if(ur == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		//处理成功的和已关闭的可以设为失败
		if(UserRechargeStatus.SUCCESS.equals(ur.getStatus()) || UserRechargeStatus.CLOSE.equals(ur.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		ur.setFailReason(reason);
		ur.setOperator(admin.getUuid());
		this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.FAIL);
		
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
		
		UserRecharge ur = userRechargeDao.get(uuid);
		if(ur == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}

		//已经成功的不能再设为成功
		if(UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		ur.setOperator(admin.getUuid());
		ur.setProof(proof);
		
		//入库
		List<String> errorList = new ArrayList<String>();
		locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
			this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.SUCCESS);
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
	 * 平台管理员关闭订单
	 */
	@Override
	public void close(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		UserRecharge ur = userRechargeDao.get(uuid);
		if(ur == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		//交易成功的不能关闭
		if(UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		ur.setOperator(admin.getUuid());
		
		this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
	
	/**
	 * 按渠道取待处理数据数量
	 */
	@Override
	public void typeList(InputParams params, DataResult<Object> result) {
    	List<StatusCountVO> voList = this.userRechargeDao.getCheckingChannelList();
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
