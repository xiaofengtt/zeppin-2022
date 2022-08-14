package cn.product.score.service.back.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.AdminDao;
import cn.product.score.dao.CapitalAccountDao;
import cn.product.score.dao.CapitalPlatformDao;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserDao;
import cn.product.score.dao.FrontUserHistoryCheckDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.entity.Admin;
import cn.product.score.entity.CapitalAccount;
import cn.product.score.entity.CapitalPlatform;
import cn.product.score.entity.FrontUser;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.score.entity.FrontUserHistoryCheck;
import cn.product.score.entity.FrontUserHistoryCheck.FrontUserHistoryCheckStatus;
import cn.product.score.entity.FrontUserHistoryCheck.FrontUserHistoryCheckTransType;
import cn.product.score.entity.FrontUserHistoryCheck.FrontUserHistoryCheckType;
import cn.product.score.service.back.UserRechargeService;
import cn.product.score.entity.Resource;
import cn.product.score.util.JSONUtils;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.FrontUserHistoryCheckDetailVO;
import cn.product.score.vo.back.FrontUserHistoryCheckVO;
import cn.product.score.vo.back.StatusCountVO;

@Service("userRechargeService")
public class UserRechargeServiceImpl implements UserRechargeService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private AdminDao adminDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private FrontUserHistoryCheckDao frontUserHistoryCheckDao;
	
	@Autowired
    private CapitalAccountDao capitalAccountDao;
	
	@Autowired
    private CapitalPlatformDao capitalPlatformDao;
	
	@Autowired
    private ResourceDao resourceDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
    	FrontUserHistoryCheck fuhc = this.frontUserHistoryCheckDao.get(uuid);
		if(fuhc == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录不存在！");
			return;
		}
		if(!FrontUserHistoryCheckTransType.RECHARGE.equals(fuhc.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		FrontUserHistoryCheckDetailVO fuhcvo = new FrontUserHistoryCheckDetailVO(fuhc);
		if(!Utlity.checkStringNull(fuhc.getValue())){
			FrontUser fu = this.frontUserDao.get(fuhcvo.getValue().getFrontUser());
			if(fu != null){
				fuhcvo.getValue().setFrontUserMobile(fu.getMobile());
				fuhcvo.getValue().setFrontUserName(fu.getRealname());
			}
			
			CapitalAccount ca = this.capitalAccountDao.get(fuhcvo.getValue().getCapitalAccount());
			if(ca != null){
				fuhcvo.getValue().setCapitalAccountName(ca.getName());
			}
			CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
			if(cp != null){
				fuhcvo.getValue().setCapitalPlatform(cp.getUuid());
				fuhcvo.getValue().setCapitalPlatformName(cp.getName());
			}
		}
		
		if(!Utlity.checkStringNull(fuhc.getChecker())){
			Admin checker = this.adminDao.get(fuhc.getChecker());
			if(checker != null){
				fuhcvo.setCheckerName(checker.getRealname());
			}
		}
		
		Admin creator = this.adminDao.get(fuhc.getCreator());
		if(creator != null){
			fuhcvo.setCreatorName(creator.getRealname());
		}
		
		if(!Utlity.checkStringNull(fuhc.getProof())){
			Resource resource = this.resourceDao.get(fuhc.getProof());
			if(resource != null){
				fuhcvo.setProofUrl(resource.getUrl());
			}
		}
		
		result.setData(fuhcvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("type", type);
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			searchMap.put("status", status);
		}
		searchMap.put("transType", FrontUserHistoryCheckTransType.RECHARGE);
		searchMap.put("creator", admin);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserHistoryCheckDao.getCountByParams(searchMap);
		List<FrontUserHistoryCheck> fuhcList = frontUserHistoryCheckDao.getListByParams(searchMap);
		
		List<FrontUserHistoryCheckVO> voList = new ArrayList<FrontUserHistoryCheckVO>();
		for(FrontUserHistoryCheck fuhc : fuhcList){
			FrontUserHistoryCheckVO fuhcvo = new FrontUserHistoryCheckVO(fuhc);
			FrontUser fu = this.frontUserDao.get(fuhcvo.getFrontUser());
			if(fu != null){
				fuhcvo.setFrontUserMobile(fu.getMobile());
				fuhcvo.setFrontUserName(fu.getRealname());
			}
			
			if(!Utlity.checkStringNull(fuhc.getChecker())){
				Admin checker = this.adminDao.get(fuhc.getChecker());
				if(checker != null){
					fuhcvo.setCheckerName(checker.getRealname());
				}
			}
			
			Admin creator = this.adminDao.get(fuhc.getCreator());
			if(creator != null){
				fuhcvo.setCreatorName(creator.getRealname());
			}
			voList.add(fuhcvo);
		}
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void checkList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("type", type);
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			searchMap.put("status", status);
		}
		searchMap.put("transType", FrontUserHistoryCheckTransType.RECHARGE);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserHistoryCheckDao.getCountByParams(searchMap);
		List<FrontUserHistoryCheck> fuhcList = frontUserHistoryCheckDao.getListByParams(searchMap);
		
		List<FrontUserHistoryCheckVO> voList = new ArrayList<FrontUserHistoryCheckVO>();
		for(FrontUserHistoryCheck fuhc : fuhcList){
			FrontUserHistoryCheckVO fuhcvo = new FrontUserHistoryCheckVO(fuhc);
			FrontUser fu = this.frontUserDao.get(fuhcvo.getFrontUser());
			if(fu != null){
				fuhcvo.setFrontUserMobile(fu.getMobile());
				fuhcvo.setFrontUserName(fu.getRealname());
			}
			
			if(!Utlity.checkStringNull(fuhc.getChecker())){
				Admin checker = this.adminDao.get(fuhc.getChecker());
				if(checker != null){
					fuhcvo.setCheckerName(checker.getRealname());
				}
			}
			
			Admin creator = this.adminDao.get(fuhc.getCreator());
			if(creator != null){
				fuhcvo.setCreatorName(creator.getRealname());
			}
			voList.add(fuhcvo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void checkStatusList(InputParams params, DataResult<Object> result) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("transType", FrontUserHistoryCheckTransType.RECHARGE);
		
		List<StatusCountVO> list = frontUserHistoryCheckDao.getStatusList(searchMap);
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setTotalResultCount(list.size());
	}

	@Override
	public void statusList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("transType", FrontUserHistoryCheckTransType.RECHARGE);
		searchMap.put("creator", admin);
		
		List<StatusCountVO> list = frontUserHistoryCheckDao.getStatusList(searchMap);
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setTotalResultCount(list.size());
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> paramsMap = params.getParams();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		BigDecimal fee = paramsMap.get("fee") == null ? null : BigDecimal.valueOf(Double.valueOf(paramsMap.get("fee").toString()));
		BigDecimal poundage = paramsMap.get("poundage") == null ? null : BigDecimal.valueOf(Double.valueOf(paramsMap.get("poundage").toString()));
		String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
		String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
		String proof = paramsMap.get("proof") == null ? "" : paramsMap.get("proof").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUser fu = this.frontUserDao.get(frontUser);	
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户不存在！");
			return;
		}
		
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户不存在！");
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(frontUser);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户账户不存在！");
			return;
		}
		
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setFrontUser(frontUser);
		fuh.setFrontUserAccount(fua.getUuid());
		fuh.setCapitalAccount(capitalAccount);
		fuh.setPay(BigDecimal.ZERO);
		fuh.setIncome(fee);
		fuh.setPoundage(poundage);
		fuh.setTransData(transData);
		fuh.setType(FrontUserHistoryType.USER_RECHARGE);
		
		FrontUserHistoryCheck fuhc = new FrontUserHistoryCheck();
		fuhc.setUuid(UUID.randomUUID().toString());
		fuhc.setType(FrontUserHistoryCheckType.ADD);
		fuhc.setTransType(FrontUserHistoryCheckTransType.RECHARGE);
		fuhc.setValue(JSONUtils.obj2json(fuh));
		fuhc.setProof(proof);
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setCreator(admin);
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		fuhc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckDao.insert(fuhc);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		BigDecimal fee = paramsMap.get("fee") == null ? null : BigDecimal.valueOf(Double.valueOf(paramsMap.get("fee").toString()));
		BigDecimal poundage = paramsMap.get("poundage") == null ? null : BigDecimal.valueOf(Double.valueOf(paramsMap.get("poundage").toString()));
		String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
		String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
		String proof = paramsMap.get("proof") == null ? "" : paramsMap.get("proof").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistoryCheck fuhc = this.frontUserHistoryCheckDao.get(uuid);
		if(fuhc == null || FrontUserHistoryCheckStatus.DELETE.equals(fuhc.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录不存在！");
			return;
		}
		if(FrontUserHistoryCheckStatus.CHECKED.equals(fuhc.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录已审核通过，不能修改！");
			return;
		}
		if(!FrontUserHistoryCheckType.ADD.equals(fuhc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录不能修改！");
			return;
		}
		if(!FrontUserHistoryCheckTransType.RECHARGE.equals(fuhc.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("审核记录类型错误！");
			return;
		}
		
		FrontUserHistory fuhOld = JSONUtils.json2obj(fuhc.getValue(), FrontUserHistory.class);
		FrontUser fu = this.frontUserDao.get(fuhOld.getFrontUser());	
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户不存在！");
			return;
		}
		
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户不存在！");
			return;
		}
		
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setFrontUser(fu.getUuid());
		fuh.setCapitalAccount(capitalAccount);
		fuh.setIncome(fee);
		fuh.setPay(BigDecimal.ZERO);
		fuh.setPoundage(poundage);
		fuh.setTransData(transData);
		fuh.setType(FrontUserHistoryType.USER_RECHARGE);
		FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(fu.getUuid());
		fuh.setFrontUserAccount(fua.getUuid());
		
		fuhc.setValue(JSONUtils.obj2json(fuh));
		fuhc.setProof(proof);
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setCreator(admin);
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckDao.update(fuhc);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功");
	}

	@Override
	public void confirm(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String proof = paramsMap.get("proof") == null ? "" : paramsMap.get("proof").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistory fuh = this.frontUserHistoryDao.get(uuid);
		if(fuh == null || FrontUserHistoryStatus.DELETE.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("交易记录不存在！");
			return;
		}
		if(!FrontUserHistoryType.USER_RECHARGE.equals(fuh.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		if(!FrontUserHistoryStatus.NORMAL.equals(fuh.getStatus()) && !FrontUserHistoryStatus.FAILED.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该交易记录当前无法审核！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", uuid);
		Integer count = this.frontUserHistoryCheckDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该充值正在等待审核，请勿重复提交！");
			return;
		}
		
		FrontUserHistoryCheck fuhc = new FrontUserHistoryCheck();
		fuhc.setUuid(UUID.randomUUID().toString());
		fuhc.setFrontUserHistory(fuh.getUuid());
		fuhc.setType(FrontUserHistoryCheckType.CONFIRM);
		fuhc.setTransType(FrontUserHistoryCheckTransType.RECHARGE);
		fuhc.setValue(JSONUtils.obj2json(fuh));
		fuhc.setProof(proof);
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setCreator(admin);
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		fuhc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckDao.insert(fuhc);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功");
	}

	@Override
	public void resubmit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String proof = paramsMap.get("proof") == null ? "" : paramsMap.get("proof").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistoryCheck fuhc = this.frontUserHistoryCheckDao.get(uuid);
		if(fuhc == null || FrontUserHistoryCheckStatus.DELETE.equals(fuhc.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录不存在！");
			return;
		}
		if(!FrontUserHistoryCheckType.CONFIRM.equals(fuhc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该审核记录无法重新提交！");
			return;
		}
		if(!FrontUserHistoryCheckStatus.NOPASS.equals(fuhc.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该审核记录无法重新提交！");
			return;
		}
		if(!FrontUserHistoryCheckTransType.RECHARGE.equals(fuhc.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		
		fuhc.setProof(proof);
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setCreator(admin);
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckDao.update(fuhc);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功");
	}

	@Override
	public void close(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistory fuh = this.frontUserHistoryDao.get(uuid);
		if(fuh == null || FrontUserHistoryCheckStatus.DELETE.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值信息不存在！");
			return;
		}
		if(!FrontUserHistoryType.USER_RECHARGE.equals(fuh.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("交易记录类型错误！");
			return;
		}
		if(!FrontUserHistoryStatus.NORMAL.equals(fuh.getStatus()) && !FrontUserHistoryStatus.FAILED.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("交易记录状态错误！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", fuh.getUuid());
		searchMap.put("status", FrontUserHistoryCheckStatus.NORMAL);
		int count = this.frontUserHistoryCheckDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("订单正在审核中，无法关闭！");
			return;
		}
		
		fuh.setStatus(FrontUserHistoryStatus.CLOSE);
		fuh.setOperator(admin);
		fuh.setOperattime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryDao.update(fuh);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功");
	}

	@Override
	public void check(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String reason = paramsMap.get("reason") == null ? "" : paramsMap.get("reason").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistoryCheck fuhc = frontUserHistoryCheckDao.get(uuid);
		if(fuhc == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录不存在！");
			return;
		}
		if(!FrontUserHistoryCheckStatus.NORMAL.equals(fuhc.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("核记录无法被审核！");
			return;
		}
		if(!FrontUserHistoryCheckTransType.RECHARGE.equals(fuhc.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		
		if(FrontUserHistoryCheckStatus.NOPASS.equals(status)){
			fuhc.setReason(Utlity.checkStringNull(reason) ? "审核不通过！" : reason);
			fuhc.setStatus(status);
			fuhc.setChecker(admin);
			fuhc.setChecktime(new Timestamp(System.currentTimeMillis()));
			this.frontUserHistoryCheckDao.update(fuhc);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("审核成功");
			return;
		}else if(FrontUserHistoryCheckStatus.CHECKED.equals(status)){
			if(FrontUserHistoryCheckType.CONFIRM.equals(fuhc.getType())|| FrontUserHistoryCheckType.ADD.equals(fuhc.getType())){
				fuhc.setReason(Utlity.checkStringNull(reason) ? "审核通过！" : reason);
				fuhc.setStatus(status);
				fuhc.setChecker(admin);
				fuhc.setChecktime(new Timestamp(System.currentTimeMillis()));
				this.frontUserHistoryCheckDao.checkRecharge(fuhc);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("审核成功");
				return;
			}else{
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("待审核信息有误！");
				return;
			}
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("审核状态有误！");
			return;
		}
	}

	
}
