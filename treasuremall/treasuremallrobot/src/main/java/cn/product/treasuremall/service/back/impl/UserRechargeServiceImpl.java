package cn.product.treasuremall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.AdminDao;
import cn.product.treasuremall.dao.BankDao;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.CapitalPlatformDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserBankcardDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.Admin;
import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.entity.CapitalPlatform;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.FrontUserRechargeOrder;
import cn.product.treasuremall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderStatus;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.service.back.UserRechargeService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.FrontUserRechargeOrderVO;

@Service("userRechargeService")
public class UserRechargeServiceImpl implements UserRechargeService{
	
	@Autowired
    private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Autowired
    private FrontUserDao frontUserDao;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private FrontUserBankcardDao frontUserBankcardDao;
	
	@Autowired
    private CapitalAccountDao capitalAccountDao;
	
	@Autowired
    private BankDao bankDao;
	
	@Autowired
    private AdminDao adminDao;
	
	@Autowired
    private ResourceDao resourceDao;
	
	@Autowired
    private CapitalPlatformDao capitalPlatformDao;

	/**
	 * 获取详情
	 */
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
    	FrontUserRechargeOrder furo = frontUserRechargeOrderDao.get(uuid);
    	if(furo == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值订单不存在");
			return;
		}
		
    	FrontUserRechargeOrderVO furovo = new FrontUserRechargeOrderVO(furo);
    	
    	FrontUser fu = frontUserDao.get(furo.getFrontUser());
    	if(fu != null){
    		furovo.setFrontUserNickname(fu.getNickname());
    		
    		FrontUserAccount fua = frontUserAccountDao.get(fu.getUuid());
    		if(fua != null){
    			furovo.setFrontUserBalance(fua.getBalance());
    			furovo.setFrontUserBalanceLock(fua.getBalanceLock());
    		}
    	}
    	
    	if(furo.getOperator() != null){
    		Admin operator = adminDao.get(furo.getOperator());
    		if(operator != null){
    			furovo.setOperatorName(operator.getRealname());
    		}
    	}
    	
    	
		CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		
		furovo.setAccountName(ca.getName());
		furovo.setAccountNum(ca.getAccountNum());
		furovo.setCapitalPlatform(cp.getName());
    	
		result.setData(furo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	/**
	 * 根据条件查询充值订单列表
	 */
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String orderNum = paramsMap.get("orderNum") == null ? "" : paramsMap.get("orderNum").toString();
		String frontUserShowId = paramsMap.get("frontUserShowId") == null ? "" : paramsMap.get("frontUserShowId").toString();
		String starttime = paramsMap.get("starttime") == null ? "" : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? "" : paramsMap.get("endtime").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("orderNum", orderNum);
		searchMap.put("frontUserShowId", frontUserShowId);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			searchMap.put("status", status);
		}
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<FrontUserRechargeOrder> list = this.frontUserRechargeOrderDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserRechargeOrderDao.getCountByParams(searchMap);
		
		List<FrontUserRechargeOrderVO> listvo = new ArrayList<FrontUserRechargeOrderVO>();
		if(list != null) {
			for(FrontUserRechargeOrder furo : list) {
				FrontUserRechargeOrderVO furovo = new FrontUserRechargeOrderVO(furo);
				FrontUser fu = frontUserDao.get(furo.getFrontUser());
		    	if(fu != null){
		    		furovo.setFrontUserNickname(fu.getNickname());
		    		
		    		FrontUserAccount fua = frontUserAccountDao.get(fu.getUuid());
		    		if(fua != null){
		    			furovo.setFrontUserBalance(fua.getBalance());
		    			furovo.setFrontUserBalanceLock(fua.getBalanceLock());
		    		}
		    	}
		    	
		    	if(furo.getOperator() != null){
		    		Admin operator = adminDao.get(furo.getOperator());
		    		if(operator != null){
		    			furovo.setOperatorName(operator.getRealname());
		    		}
		    	}
		    	
				CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
				CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
				
				furovo.setAccountName(ca.getName());
				furovo.setAccountNum(ca.getAccountNum());
				furovo.setCapitalPlatform(cp.getName());
				
				listvo.add(furovo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
		return;
	}

	/**
	 * 手工处理收款成功或失败
	 */
	@Override
	public void statusSetting(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
    	String remark = paramsMap.get("remark") == null ? "" : paramsMap.get("remark").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
    	
		try {
			FrontUserRechargeOrder furo = frontUserRechargeOrderDao.get(uuid);
	    	if(furo == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("充值订单不存在");
				return;
			}
	    	if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus()) && !FrontUserRechargeOrderStatus.CHECKING.equals(furo.getStatus())) {
	    		result.setStatus(ResultStatusType.FAILED);
				result.setMessage("充值订单状态错误");
				return;
	    	}
	    	FrontUserAccount fua = frontUserAccountDao.get(furo.getFrontUser());
	    	if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
	    		result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户账户异常，操作失败");
				return;
	    	}
	    	if(FrontUserRechargeOrderStatus.CHECKED.equals(status)) {//成功
	    		furo.setStatus(FrontUserWithdrawOrderStatus.CHECKED);
	    		furo.setRemark(remark);
	    		furo.setOperator(admin);
	    		furo.setOperattime(new Timestamp(System.currentTimeMillis()));
				
				FrontUserHistory fuh = new FrontUserHistory();
				fuh.setUuid(UUID.randomUUID().toString());
				fuh.setFrontUser(furo.getFrontUser());
				fuh.setOrderNum(furo.getOrderNum());
				fuh.setType(FrontUserHistoryType.USER_ADD);
				fuh.setOrderId(furo.getUuid());
				fuh.setOrderType(Constants.ORDER_TYPE_USER_RECHARGE);
				fuh.setdAmount(furo.getIncreaseDAmount());
				fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
				fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_RECHARGE));
				fuh.setCreatetime(furo.getOperattime());
				fuh.setRemark(furo.getRemark());
				
				fua.setBalance(fua.getBalanceLock().add(furo.getIncreaseDAmount()));
				fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//总充值（法币）
				fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
	    		
				this.frontUserRechargeOrderDao.check(furo, fua, fuh);
	    	} else if(FrontUserRechargeOrderStatus.CANCEL.equals(status)) {//失败 无账变
	    		furo.setStatus(FrontUserWithdrawOrderStatus.CANCEL);
	    		furo.setRemark(remark);
	    		furo.setOperator(admin);
	    		furo.setOperattime(new Timestamp(System.currentTimeMillis()));
	    		this.frontUserRechargeOrderDao.update(furo);
	    	} else {
	    		result.setStatus(ResultStatusType.FAILED);
				result.setMessage("处理状态错误！");
				return;
	    	}
	    	
	    	result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("处理成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常");
			return;		
		}
	}
	
}
