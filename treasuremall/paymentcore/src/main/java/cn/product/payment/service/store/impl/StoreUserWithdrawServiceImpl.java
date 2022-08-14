/**
 * 
 */
package cn.product.payment.service.store.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.dao.CompanyAccountDao;
import cn.product.payment.dao.CompanyChannelDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.dao.UserWithdrawDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.entity.UserWithdraw;
import cn.product.payment.entity.UserWithdraw.UserWithdrawStatus;
import cn.product.payment.locker.Locker;
import cn.product.payment.locker.ZkCuratorLocker;
import cn.product.payment.service.store.StoreUserWithdrawService;
import cn.product.payment.util.api.PaymentException;
import cn.product.payment.vo.store.UserWithdrawVO;

/**
 * 用户提现
 * @author Administrator
 *
 */
@Service("storeUserWithdrawService")
public class StoreUserWithdrawServiceImpl implements StoreUserWithdrawService {
 
	@Autowired
	private UserWithdrawDao userWithdrawDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyAccountDao companyAccountDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
    private Locker locker;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		String orderNum = paramsMap.get("orderNum") == null ? null : paramsMap.get("orderNum").toString();
		String companyOrderNum = paramsMap.get("companyOrderNum") == null ? null : paramsMap.get("companyOrderNum").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String starttime = paramsMap.get("starttime") == null ? null : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? null : paramsMap.get("endtime").toString();
		CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", admin.getCompany());
		searchMap.put("channel", channel);
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
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
    	
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if (uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		if(!uw.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法查询！");
			return;
		}
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
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	/**
	 * 关闭
	 */
	@Override
	public void close(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if(uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		if(!uw.getCompany().equals(admin.getCompany())){
			//非本商户订单
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法处理！");
			return;
		}
		if(UserWithdrawStatus.CLOSE.equals(uw.getStatus()) || UserWithdrawStatus.SUCCESS.equals(uw.getStatus())){
			//当前状态不能关闭
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		//入库
		List<String> errorList = new ArrayList<String>();
		locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
			try {
				this.userWithdrawDao.processOrder(uw, UserWithdrawStatus.CLOSE);
			} catch (PaymentException e) {
				errorList.add(e.getMessage());
				e.printStackTrace();
			}
		});
		if(errorList.size() > 0){
			//操作失败
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
	
	/**
	 * 重试
	 */
	@Override
	public void retry(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if(uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		if(!uw.getCompany().equals(admin.getCompany())){
			//非本商户订单
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法处理！");
			return;
		}
		if(!UserWithdrawStatus.FAIL.equals(uw.getStatus())){
			//非错误状态不得重试
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		//入库
		List<String> errorList = new ArrayList<String>();
		locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
			try {
				this.userWithdrawDao.processOrder(uw, UserWithdrawStatus.CHECKING);
			} catch (PaymentException e) {
				errorList.add(e.getMessage());
				e.printStackTrace();
			}
		});
		if(errorList.size() > 0){
			//操作失败
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
}
