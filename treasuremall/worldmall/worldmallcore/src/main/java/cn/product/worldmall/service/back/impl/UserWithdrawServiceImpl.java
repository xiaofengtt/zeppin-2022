package cn.product.worldmall.service.back.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.dao.AdminDao;
import cn.product.worldmall.dao.BankDao;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserBankcardDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.FrontUserWithdrawOrderDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.Admin;
import cn.product.worldmall.entity.Bank;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserBankcard;
import cn.product.worldmall.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.worldmall.entity.FrontUserWithdrawOrder;
import cn.product.worldmall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.service.back.UserWithdrawService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.util.unionpay.UnionPayUtlity;
import cn.product.worldmall.vo.back.FrontUserWithdrawOrderVO;

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
			result.setMessage("?????????????????????");
			return;
		}
		
    	FrontUserWithdrawOrderVO vo = new FrontUserWithdrawOrderVO(fuwo);
    	FrontUser fu = frontUserDao.get(fuwo.getFrontUser());
    	if(fu != null){
    		vo.setFrontUserNickname(fu.getNickname());
    		vo.setIp(fu.getIp() == null ? "" : fu.getIp());
    		vo.setArea(fu.getArea() == null ? "" : fu.getArea());
    		vo.setRegistertime(fu.getCreatetime());
    		vo.setFrontUserLevel(fu.getLevel());
    		vo.setFrontUserStatus(fu.getStatus());
    		FrontUserAccount fua = frontUserAccountDao.get(fu.getUuid());
    		if(fua != null){
    			vo.setFrontUserBalance(fua.getBalance());
    			vo.setFrontUserBalanceLock(fua.getBalanceLock());
    			vo.setScoreBalance(fua.getScoreBalance());
    			vo.setTotalPayment(fua.getTotalPayment());
    			vo.setTotalRecharge(fua.getTotalRecharge());
    			vo.setTotalWithdraw(fua.getTotalWithdraw());
    			vo.setTotalDelivery(fua.getTotalDelivery());
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
    			vo.setFrontUserBranchBank(fub.getBank());
    			vo.setFrontUserAccountNumber(fub.getAccountNumber());
    			vo.setFrontUserAccountHolder(fub.getAccountHolder());
    			if(!Utlity.checkStringNull(fub.getBank())) {
    				Bank bank = bankDao.get(fub.getBank());
        			if(bank != null){
        				vo.setFrontUserBankName(bank.getName());
        			}
    			}
    		}
    	}
    	
		result.setData(vo);
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
	    		vo.setIp(fu.getIp() == null ? "" : fu.getIp());
	    		vo.setArea(fu.getArea() == null ? "" : fu.getArea());
	    		vo.setRegistertime(fu.getCreatetime());
	    		vo.setFrontUserLevel(fu.getLevel());
	    		vo.setFrontUserStatus(fu.getStatus());
	    		FrontUserAccount fua = frontUserAccountDao.get(fu.getUuid());
	    		if(fua != null){
	    			vo.setFrontUserBalance(fua.getBalance());
	    			vo.setFrontUserBalanceLock(fua.getBalanceLock());
	    			vo.setScoreBalance(fua.getScoreBalance());
	    			vo.setTotalPayment(fua.getTotalPayment());
	    			vo.setTotalRecharge(fua.getTotalRecharge());
	    			vo.setTotalWithdraw(fua.getTotalWithdraw());
	    			vo.setTotalDelivery(fua.getTotalDelivery());
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
//	    			Bank bank = bankDao.get(fub.getBank());
//	    			if(bank != null){
//	    				vo.setFrontUserBankName(bank.getName());
//	    			}
	    			if(!Utlity.checkStringNull(fub.getBank())) {
	    				Bank bank = bankDao.get(fub.getBank());
	        			if(bank != null){
	        				vo.setFrontUserBankName(bank.getName());
	        			}
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
			result.setMessage("?????????????????????");
			return;
		}
		
		FrontUserWithdrawOrder fuwo = frontUserWithdrawOrderDao.get(uuid);
		if(fuwo == null || !FrontUserWithdrawOrderStatus.NORMAL.equals(fuwo.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????????????????");
			return;
		}
		
		if("checked".equals(status)){
//			fuwo.setStatus(FrontUserWithdrawOrderStatus.CHECKING);
//			fuwo.setRemark(remark);
//			fuwo.setOperator(admin);
//			fuwo.setOperattime(new Timestamp(System.currentTimeMillis()));
//			frontUserWithdrawOrderDao.update(fuwo);
			//????????????
			FrontUserBankcard fub = this.frontUserBankcardDao.get(fuwo.getFrontUserBankcard());
			if(fub == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????!");
				return;
			}
			if(!fuwo.getFrontUser().equals(fub.getFrontUser())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????!");
				return;
			}
			if(!FrontUserBankcardStatus.NORMAL.equals(fub.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????!");
				return;
			}
			
//			Bank bank = this.bankDao.get(fub.getBank());
//			if(bank == null){
//				result.setStatus(ResultStatusType.FAILED);
//				result.setMessage("??????????????????!");
//				return;
//			}
			CapitalAccount ca = this.capitalAccountDao.get(fuwo.getCapitalAccount());
			if(ca == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("??????????????????!");
				return;
			}
			//????????????ID
			String channel = UnionPayUtlity.CHANNEL_WITHDRAW_ALI;
			if(!Utlity.checkStringNull(ca.getData())) {
				Map<String, Object> data = JSONUtils.json2map(ca.getData());
				channel = data.get("withdrawChannel").toString();
			}
			
			Map<String, Object> requestMap = new HashMap<String, Object>();
//			requestMap.put("bank", bank.getName());
			requestMap.put("account", fub.getAccountNumber());
//			requestMap.put("holder", fub.getAccountHolder());
			requestMap.put("channel", channel);
			requestMap.put("title", "????????????");
			requestMap.put("orderNum", fuwo.getOrderNum());
			requestMap.put("totalAmount", fuwo.getActualAmount().multiply(BigDecimal.valueOf(Double.valueOf(100))));
			requestMap.put("passbackParams", fuwo.getUuid());
			
			try {
				Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwithdraw(requestMap);
				if((Boolean)resultMap.get("result")) {
					fuwo.setOperator(admin);
					fuwo.setOperattime(new Timestamp(System.currentTimeMillis()));
					fuwo.setStatus(FrontUserWithdrawOrderStatus.CHECKING);
					this.frontUserWithdrawOrderDao.update(fuwo);
					result.setStatus(ResultStatusType.SUCCESS);
					result.setMessage("???????????????");
					return;
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage(resultMap.get("message").toString());
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????????????????");
				return;
			}
		}else{
			FrontUserAccount fua = frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null || fua.getBalanceLock().compareTo(fuwo.getReduceDAmount()) < 0){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????");
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
		result.setMessage("???????????????");
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
			result.setMessage("?????????????????????");
			return;
		}
		
		FrontUserWithdrawOrder fuwo = frontUserWithdrawOrderDao.get(uuid);
		if(fuwo == null || !FrontUserWithdrawOrderStatus.NORMAL.equals(fuwo.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????????????????");
			return;
		}
		
		if("checked".equals(status)){
			FrontUserAccount fua = frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null || fua.getBalanceLock().compareTo(fuwo.getReduceDAmount()) < 0){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????????????????????????????");
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
			fua.setTotalWithdraw(fua.getTotalWithdraw().add(fuwo.getActualAmount()));
			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
			
			frontUserWithdrawOrderDao.check(fuwo, fua, fuh);
		}else{
			FrontUserAccount fua = frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null || fua.getBalanceLock().compareTo(fuwo.getReduceDAmount()) < 0){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????");
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
		result.setMessage("???????????????");
	}

	/**
	 * ?????????????????????
	 * ??????????????????????????????????????????????????????
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
			result.setMessage("?????????????????????");
			return;
		}
		
		FrontUserWithdrawOrder fuwo = frontUserWithdrawOrderDao.get(uuid);
		if(fuwo == null || (!FrontUserWithdrawOrderStatus.NORMAL.equals(fuwo.getStatus()) &&  !FrontUserWithdrawOrderStatus.FAIL.equals(fuwo.getStatus()))){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????????????????");
			return;
		}
		
		if("cancel".equals(status)){
			FrontUserAccount fua = frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null || fua.getBalanceLock().compareTo(fuwo.getReduceDAmount()) < 0){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????");
				return;
			}
			
			Map<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put("orderNum", fuwo.getOrderNum() + "");
			try {
				Map<String, Object> resultMap = UnionPayUtlity.closeWithdrawOrder(requestMap);
				if((Boolean)resultMap.get("result")){//????????????
					Map<String, Object> paramsls = JSONUtils.json2map(resultMap.get("response").toString()) ;
					String message = "";
					if(!FrontUserWithdrawOrderStatus.NORMAL.equals(fuwo.getStatus())) {
						message = "??????????????????????????????????????????";
						throw new TransactionException(message);
					}
					//????????????????????????
					String resultStatus = paramsls.get("status") == null ? "" : paramsls.get("status").toString();
					if("close".equals(resultStatus)) {
						FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
						if(fu == null) {
							message = "????????????C";
							throw new TransactionException(message);
						}
						fuwo.setStatus(FrontUserWithdrawOrderStatus.CANCEL);
						fuwo.setRemark(remark);
						fuwo.setOperator(admin);
						fuwo.setOperattime(new Timestamp(System.currentTimeMillis()));
						fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
						fua.setBalance(fua.getBalance().add(fuwo.getReduceDAmount()));
						frontUserWithdrawOrderDao.check(fuwo, fua, null);
						
						//??????????????????
						this.frontUserMessageDao.sendMessage(fuwo, "");
//						//??????????????????
//						FrontUserMessage fum = new FrontUserMessage();
//						fum.setUuid(UUID.randomUUID().toString());
//						fum.setFrontUser(fu.getUuid());
//						fum.setFrontUserShowId(fu.getShowId());
//						fum.setTitle("Order Canceled");
////						fum.setContent("??????"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"??????"+fuwo.getActualAmount()+"????????????????????????????????????????????????????????????????????????");
//						fum.setContent("Your withdrawal of $" + fuwo.getActualAmount() + " at " + Utlity.timeSpanToUsString(fuwo.getCreatetime())
//						+ " was failed, the order was closed, please pay attention to the account balance.");
//						fum.setSourceId(fuwo.getUuid());
//						fum.setStatus(FrontUserMessageStatus.NORMAL);
//						fum.setType(FrontUserMessageType.USER_ORDER);
//						fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
//						fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//						
//						this.frontUserMessageDao.sendMessage(fum);
//						this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW_FAIL);
					}
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage(resultMap.get("message").toString());
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????");
				return;
			}
		} else {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????,???????????????");
			return;
		}
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("???????????????");
		return;
	}
public static void main(String[] args) {
	System.out.println("9223372036854775807".length());
}
}
