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
import cn.product.treasuremall.controller.base.TransactionException;
import cn.product.treasuremall.dao.AdminDao;
import cn.product.treasuremall.dao.BankDao;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserBankcardDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserMessageDao;
import cn.product.treasuremall.dao.FrontUserWithdrawOrderDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.Admin;
import cn.product.treasuremall.entity.Bank;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserBankcard;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.FrontUserMessage;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.service.back.UserWithdrawService;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.SendSmsNew;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.util.unionpay.UnionPayUtlity;
import cn.product.treasuremall.vo.back.FrontUserWithdrawOrderVO;

@Service("userWithdrawService")
public class UserWithdrawServiceImpl implements UserWithdrawService{
	
	@Autowired
    private FrontUserWithdrawOrderDao frontUserWithdrawOrderDao;
	
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
    private FrontUserMessageDao frontUserMessageDao;
	
	@Autowired
    private BankDao bankDao;
	
	@Autowired
    private AdminDao adminDao;
	
	@Autowired
    private ResourceDao resourceDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
    	FrontUserWithdrawOrder fuwo = frontUserWithdrawOrderDao.get(uuid);
    	if(fuwo == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("提现订单不存在");
			return;
		}
		
    	FrontUserWithdrawOrderVO vo = new FrontUserWithdrawOrderVO(fuwo);
    	FrontUser fu = frontUserDao.get(fuwo.getFrontUser());
    	if(fu != null){
    		vo.setFrontUserNickname(fu.getNickname());
    		
    		FrontUserAccount fua = frontUserAccountDao.get(fu.getUuid());
    		if(fua != null){
    			vo.setFrontUserBalance(fua.getBalance());
    			vo.setFrontUserBalanceLock(fua.getBalanceLock());
    		}
    	}
    	
    	if(fuwo.getOperator() != null){
    		Admin operator = adminDao.get(fuwo.getOperator());
    		if(operator != null){
    			vo.setOperatorName(operator.getRealname());
    		}
    	}
    	
    	if(fuwo.getFrontUserBankcard() != null){
    		FrontUserBankcard fub = frontUserBankcardDao.get(fuwo.getFrontUserBankcard());
    		if(fub != null){
    			vo.setFrontUserBranchBank(fub.getBranchBank());
    			vo.setFrontUserAccountNumber(fub.getAccountNumber());
    			vo.setFrontUserAccountHolder(fub.getAccountHolder());
    			Bank bank = bankDao.get(fub.getBank());
    			if(bank != null){
    				vo.setFrontUserBankName(bank.getName());
    			}
    		}
    	}
    	
		result.setData(fuwo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

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
		
		Integer totalCount = frontUserWithdrawOrderDao.getCountByParams(searchMap);
		List<FrontUserWithdrawOrder> fuwoList = frontUserWithdrawOrderDao.getListByParams(searchMap);
		
		List<FrontUserWithdrawOrderVO> voList =  new ArrayList<>();
		for(FrontUserWithdrawOrder fuwo : fuwoList){
			FrontUserWithdrawOrderVO vo = new FrontUserWithdrawOrderVO(fuwo);
	    	FrontUser fu = frontUserDao.get(fuwo.getFrontUser());
	    	if(fu != null){
	    		vo.setFrontUserNickname(fu.getNickname());
	    		
	    		FrontUserAccount fua = frontUserAccountDao.get(fu.getUuid());
	    		if(fua != null){
	    			vo.setFrontUserBalance(fua.getBalance());
	    			vo.setFrontUserBalanceLock(fua.getBalanceLock());
	    		}
	    	}
	    	
	    	if(fuwo.getOperator() != null){
	    		Admin operator = adminDao.get(fuwo.getOperator());
	    		if(operator != null){
	    			vo.setOperatorName(operator.getRealname());
	    		}
	    	}
	    	
	    	if(fuwo.getFrontUserBankcard() != null){
	    		FrontUserBankcard fub = frontUserBankcardDao.get(fuwo.getFrontUserBankcard());
	    		if(fub != null){
	    			vo.setFrontUserBranchBank(fub.getBranchBank());
	    			vo.setFrontUserAccountNumber(fub.getAccountNumber());
	    			vo.setFrontUserAccountHolder(fub.getAccountHolder());
	    			Bank bank = bankDao.get(fub.getBank());
	    			if(bank != null){
	    				vo.setFrontUserBankName(bank.getName());
	    			}
	    		}
	    	}
	    	voList.add(vo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void firstCheck(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String remark = paramsMap.get("remark") == null ? "" : paramsMap.get("remark").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		if(!"checked".equals(status) && !"nopass".equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("审核状态错误！");
			return;
		}
		
		FrontUserWithdrawOrder fuwo = frontUserWithdrawOrderDao.get(uuid);
		if(fuwo == null || !FrontUserWithdrawOrderStatus.NORMAL.equals(fuwo.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("审核记录错误，无法审核！");
			return;
		}
		
		if("checked".equals(status)){
			fuwo.setStatus(FrontUserWithdrawOrderStatus.CHECKING);
			fuwo.setRemark(remark);
			fuwo.setOperator(admin);
			fuwo.setOperattime(new Timestamp(System.currentTimeMillis()));
			frontUserWithdrawOrderDao.update(fuwo);
		}else{
			FrontUserAccount fua = frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null || fua.getBalanceLock().compareTo(fuwo.getReduceDAmount()) < 0){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户账户异常！");
				return;
			}
			
			fuwo.setStatus(FrontUserWithdrawOrderStatus.CANCEL);
			fuwo.setRemark(remark);
			fuwo.setOperator(admin);
			fuwo.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
			fua.setBalance(fua.getBalance().add(fuwo.getReduceDAmount()));
			
			frontUserWithdrawOrderDao.check(fuwo, fua, null);
		}
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("审核成功！");
	}

	@Override
	public void finalCheck(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String remark = paramsMap.get("remark") == null ? "" : paramsMap.get("remark").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		if(!"checked".equals(status) && !"nopass".equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("审核状态错误！");
			return;
		}
		
		FrontUserWithdrawOrder fuwo = frontUserWithdrawOrderDao.get(uuid);
		if(fuwo == null || !FrontUserWithdrawOrderStatus.CHECKING.equals(fuwo.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("审核记录错误，无法审核！");
			return;
		}
		
		if("checked".equals(status)){
			FrontUserAccount fua = frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null || fua.getBalanceLock().compareTo(fuwo.getReduceDAmount()) < 0){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户账户异常或资金不足，无法提现！");
				return;
			}
			
			fuwo.setStatus(FrontUserWithdrawOrderStatus.CHECKED);
			fuwo.setRemark(remark);
			fuwo.setOperator(admin);
			fuwo.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			FrontUserHistory fuh = new FrontUserHistory();
			fuh.setUuid(UUID.randomUUID().toString());
			fuh.setFrontUser(fuwo.getFrontUser());
			fuh.setOrderNum(fuwo.getOrderNum());
			fuh.setType(FrontUserHistoryType.USER_SUB);
			fuh.setOrderId(fuwo.getUuid());
			fuh.setOrderType(Constants.ORDER_TYPE_USER_WITHDRAW);
			fuh.setdAmount(fuwo.getReduceDAmount());
			fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
			fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_WITHDRAW));
			fuh.setCreatetime(fuwo.getOperattime());
			fuh.setRemark(fuwo.getRemark());
			
			fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
			fua.setTotalWithdraw(fua.getTotalWithdraw().add(fuwo.getAmount()));
			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
			
			frontUserWithdrawOrderDao.check(fuwo, fua, fuh);
		}else{
			FrontUserAccount fua = frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null || fua.getBalanceLock().compareTo(fuwo.getReduceDAmount()) < 0){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户账户异常！");
				return;
			}
			
			fuwo.setStatus(FrontUserWithdrawOrderStatus.CANCEL);
			fuwo.setRemark(remark);
			fuwo.setOperator(admin);
			fuwo.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
			fua.setBalance(fua.getBalance().add(fuwo.getReduceDAmount()));
			
			frontUserWithdrawOrderDao.check(fuwo, fua, null);
		}
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("审核成功！");
	}

	/**
	 * 管理员取消订单
	 * 管理员取消提现订单，将提现金额返还，
	 */
	@Override
	public void cancel(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String remark = paramsMap.get("remark") == null ? "" : paramsMap.get("remark").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		if(!"cancel".equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作状态错误！");
			return;
		}
		
		FrontUserWithdrawOrder fuwo = frontUserWithdrawOrderDao.get(uuid);
		if(fuwo == null || (!FrontUserWithdrawOrderStatus.NORMAL.equals(fuwo.getStatus()) &&  !FrontUserWithdrawOrderStatus.FAIL.equals(fuwo.getStatus()))){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("提现记录错误，无法取消！");
			return;
		}
		
		if("cancel".equals(status)){
			FrontUserAccount fua = frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null || fua.getBalanceLock().compareTo(fuwo.getReduceDAmount()) < 0){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户账户异常！");
				return;
			}
			
			Map<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put("orderNum", fuwo.getOrderNum() + "");
			try {
				Map<String, Object> resultMap = UnionPayUtlity.closeWithdrawOrder(requestMap);
				if((Boolean)resultMap.get("result")){//关闭成功
					Map<String, Object> paramsls = JSONUtils.json2map(resultMap.get("response").toString()) ;
					String message = "";
					if(!FrontUserWithdrawOrderStatus.NORMAL.equals(fuwo.getStatus())) {
						message = "账单已处理完毕！不要重复处理";
						throw new TransactionException(message);
					}
					//聚合支付交易状态
					String resultStatus = paramsls.get("status") == null ? "" : paramsls.get("status").toString();
					if("close".equals(resultStatus)) {
						FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
						if(fu == null) {
							message = "账单错误C";
							throw new TransactionException(message);
						}
						fuwo.setStatus(FrontUserWithdrawOrderStatus.CANCEL);
						fuwo.setRemark(remark);
						fuwo.setOperator(admin);
						fuwo.setOperattime(new Timestamp(System.currentTimeMillis()));
						fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
						fua.setBalance(fua.getBalance().add(fuwo.getReduceDAmount()));
						frontUserWithdrawOrderDao.check(fuwo, fua, null);
						
						//提现通知消息
						FrontUserMessage fum = new FrontUserMessage();
						fum.setUuid(UUID.randomUUID().toString());
						fum.setFrontUser(fu.getUuid());
						fum.setFrontUserShowId(fu.getShowId());
						fum.setTitle("订单取消提醒");
						fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"提现"+fuwo.getActualAmount()+"元，提现失败，订单取消，请注意账户余额变动信息！");
						fum.setSourceId(fuwo.getUuid());
						fum.setStatus(FrontUserMessageStatus.NORMAL);
						fum.setType(FrontUserMessageType.USER_ORDER);
						fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
						fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
						
						this.frontUserMessageDao.sendMessage(fum);
						this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW_FAIL);
					}
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage(resultMap.get("message").toString());
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户账户异常！");
				return;
			}
		} else {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误,操作失败！");
			return;
		}
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
		return;
	}
public static void main(String[] args) {
	System.out.println("9223372036854775807".length());
}
}
