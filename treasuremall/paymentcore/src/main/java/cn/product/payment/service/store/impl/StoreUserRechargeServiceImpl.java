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
import cn.product.payment.dao.UserRechargeDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.entity.UserRecharge.UserRechargeStatus;
import cn.product.payment.service.store.StoreUserRechargeService;
import cn.product.payment.vo.store.UserRechargeVO;

/**
 * 用户充值
 * @author Administrator
 *
 */
@Service("storeUserRechargeService")
public class StoreUserRechargeServiceImpl implements StoreUserRechargeService {
 
	@Autowired
	private UserRechargeDao userRechargeDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyAccountDao companyAccountDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private CompanyChannelDao companyChannelDao;
	
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
    	
		UserRecharge ur = userRechargeDao.get(uuid);
		if (ur == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		if(!ur.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法查询！");
			return;
		}
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
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	/**
	 * 关闭订单
	 */
	@Override
	public void close(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		UserRecharge ur = userRechargeDao.get(uuid);
		if(ur == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		if(!ur.getCompany().equals(admin.getCompany())){
			//非本商户订单
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法处理！");
			return;
		}
		if(UserRechargeStatus.CLOSE.equals(ur.getStatus()) || UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
			//订单状态无法关闭
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
}
