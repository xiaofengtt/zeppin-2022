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
import cn.product.treasuremall.dao.AdminOffsetOrderDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.Admin;
import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderStatus;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderType;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.service.back.AdminOffsetOrderService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.AdminOffsetOrderVO;

@Service("adminOffsetOrderService")
public class AdminOffsetOrderServiceImpl implements AdminOffsetOrderService{
	
	@Autowired
	private AdminOffsetOrderDao adminOffsetOrderDao;
	
	@Autowired
    private FrontUserDao frontUserDao;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private AdminDao adminDao;
	
	@Autowired
    private ResourceDao resourceDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		AdminOffsetOrder aoo = adminOffsetOrderDao.get(uuid);
		
		AdminOffsetOrderVO vo = new AdminOffsetOrderVO(aoo);
		FrontUser fu = frontUserDao.get(aoo.getFrontUser());
		if(fu != null){
			vo.setFrontUserShowId(fu.getShowId());
			vo.setFrontUserNickname(fu.getNickname());
			vo.setFrontUserShowId(fu.getShowId());
			
			if(fu.getImage() != null){
				Resource image = resourceDao.get(fu.getImage());
				if(image != null){
					vo.setFrontUserImageURL(image.getUrl());
				}
			}
		}
		
		FrontUserAccount fua = frontUserAccountDao.get(aoo.getFrontUser());
		if(fua != null){
			vo.setFrontUserBalance(fua.getBalance());
			vo.setFrontUserBalanceLock(fua.getBalanceLock());
		}
		FrontUserHistory fuh = frontUserHistoryDao.getByOrderId(aoo.getUuid());
		if(fuh != null) {
			vo.setBalanceAfter(fuh.getBalanceAfter());
			vo.setBalanceBefore(fuh.getBalanceBefore());
		}
		if(!Utlity.checkStringNull(aoo.getOperator())) {
			Admin operator = adminDao.get(aoo.getOperator());
			if(operator != null){
				vo.setOperatorName(operator.getRealname());
			}
		} else {
			vo.setOperatorName("");
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
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String userType = paramsMap.get("userType") == null ? "" : paramsMap.get("userType").toString();
		String showid = paramsMap.get("showid") == null ? "" : paramsMap.get("showid").toString();
		String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
		String createtime = paramsMap.get("createtime") == null ? "" : paramsMap.get("createtime").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("type", type);
		searchMap.put("status", status);
		if(!Utlity.checkStringNull(userType)) {
			if(!FrontUserType.NORMAL.equals(userType) && !FrontUserType.ROBOT.equals(userType)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户类型错误！");
				return;
			}
			searchMap.put("userType", userType);
		}
		searchMap.put("showid", showid);
		searchMap.put("mobile", mobile);
		if(!Utlity.checkStringNull(createtime)) {
			String[] times = createtime.split("_");
			if(times != null && times.length == 2) {
				searchMap.put("timestart", times[0].trim());
				searchMap.put("timeend", times[1].trim());
			}
		}
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = adminOffsetOrderDao.getLeftCountByParams(searchMap);
		List<AdminOffsetOrder> aooList = adminOffsetOrderDao.getLeftListByParams(searchMap);
		
		List<AdminOffsetOrderVO> voList = new ArrayList<>();
		for(AdminOffsetOrder aoo : aooList){
			AdminOffsetOrderVO vo = new AdminOffsetOrderVO(aoo);
			FrontUser fu = frontUserDao.get(aoo.getFrontUser());
			if(fu != null){
				vo.setFrontUserShowId(fu.getShowId());
				vo.setFrontUserNickname(fu.getNickname());
				vo.setFrontUserShowId(fu.getShowId());
				vo.setFrontUserMobile(fu.getMobile());
				
				if(fu.getImage() != null){
					Resource image = resourceDao.get(fu.getImage());
					if(image != null){
						vo.setFrontUserImageURL(image.getUrl());
					}
				}
			}
			
			FrontUserAccount fua = frontUserAccountDao.get(aoo.getFrontUser());
			if(fua != null){
				vo.setFrontUserBalance(fua.getBalance());
				vo.setFrontUserBalanceLock(fua.getBalanceLock());
			}
			FrontUserHistory fuh = frontUserHistoryDao.getByOrderId(aoo.getUuid());
			if(fuh != null) {
				vo.setBalanceAfter(fuh.getBalanceAfter());
				vo.setBalanceBefore(fuh.getBalanceBefore());
			}
			if(!Utlity.checkStringNull(aoo.getOperator())) {
				Admin operator = adminDao.get(aoo.getOperator());
				if(operator != null){
					vo.setOperatorName(operator.getRealname());
				}
			} else {
				vo.setOperatorName("");
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
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		AdminOffsetOrder aoo = (AdminOffsetOrder) paramsMap.get("adminOffsetOrder");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		if(!AdminOffsetOrderType.ADMIN_ADD.equals(aoo.getType()) && !AdminOffsetOrderType.ADMIN_SUB.equals(aoo.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("类型不存在！");
			return;
		}
		
		FrontUser fu = frontUserDao.get(aoo.getFrontUser());
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户不存在！");
			return;
		}
		
		aoo.setUuid(UUID.randomUUID().toString());
		aoo.setOrderNum(Utlity.getOrderNum());
		if(AdminOffsetOrderType.ADMIN_ADD.equals(aoo.getType())){
			aoo.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
		}else{
			aoo.setOrderType(Constants.ORDER_TYPE_SYSTEM_SUB);
		}
		aoo.setStatus(AdminOffsetOrderStatus.NORMAL);
		aoo.setOperator(admin);
		aoo.setOperattime(new Timestamp(System.currentTimeMillis()));
		aoo.setCreatetime(aoo.getOperattime());
		adminOffsetOrderDao.insert(aoo);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}

	@Override
	public void close(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		AdminOffsetOrder aoo = adminOffsetOrderDao.get(uuid);
		if(aoo == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("订单不存在！");
			return;
		}
		if(!AdminOffsetOrderStatus.NORMAL.equals(aoo.getStatus()) && !AdminOffsetOrderStatus.CANCEL.equals(aoo.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("订单无法关闭！");
			return;
		}
		
		aoo.setStatus(AdminOffsetOrderStatus.CLOSE);
		aoo.setOperator(admin);
		
		adminOffsetOrderDao.update(aoo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("关闭成功！");
	}

	@Override
	public void check(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String reason = paramsMap.get("reason") == null ? "" : paramsMap.get("reason").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		if(!"checked".equals(status) && !"nopass".equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("审核状态错误！");
			return;
		}
		AdminOffsetOrder aoo = adminOffsetOrderDao.get(uuid);
		if(aoo == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("订单不存在！");
			return;
		}
		if(!AdminOffsetOrderStatus.NORMAL.equals(aoo.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("订单无法审核！");
			return;
		}
		
		if("checked".equals(status)){
			FrontUserAccount fua = frontUserAccountDao.get(aoo.getFrontUser());
			if(fua == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户账户异常！");
				return;
			}
			
			aoo.setStatus(FrontUserWithdrawOrderStatus.CHECKED);
			aoo.setReason(reason);
			aoo.setOperator(admin);
			aoo.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			FrontUserHistory fuh = new FrontUserHistory();
			fuh.setUuid(UUID.randomUUID().toString());
			fuh.setFrontUser(aoo.getFrontUser());
			fuh.setOrderNum(aoo.getOrderNum());
			fuh.setOrderId(aoo.getUuid());
			fuh.setdAmount(aoo.getdAmount());
			fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
			fuh.setCreatetime(aoo.getOperattime());
			fuh.setRemark(aoo.getRemark());
			
			if(AdminOffsetOrderType.ADMIN_ADD.equals(aoo.getType())){
				fuh.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
				fuh.setType(FrontUserHistoryType.USER_ADD);
				fuh.setReason("手动处理加币");
				
				fua.setBalance(fua.getBalance().add(aoo.getdAmount()));
				fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
			}else{
				fuh.setOrderType(Constants.ORDER_TYPE_SYSTEM_SUB);
				fuh.setType(FrontUserHistoryType.USER_SUB);
				fuh.setReason("手动处理减币");
				
				fua.setBalance(fua.getBalance().subtract(aoo.getdAmount()));
				fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
			}
			
			adminOffsetOrderDao.check(aoo, fua, fuh);
		}else{
			aoo.setStatus(AdminOffsetOrderStatus.CANCEL);
			aoo.setReason(reason);
			aoo.setOperator(admin);
			aoo.setOperattime(new Timestamp(System.currentTimeMillis()));
			adminOffsetOrderDao.update(aoo);
		}
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("审核成功！");
	}

}
