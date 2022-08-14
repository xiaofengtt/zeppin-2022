package cn.product.score.service.back.impl;

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
import cn.product.score.dao.BankDao;
import cn.product.score.dao.CapitalAccountDao;
import cn.product.score.dao.CapitalPlatformDao;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserBankcardDao;
import cn.product.score.dao.FrontUserDao;
import cn.product.score.dao.FrontUserHistoryCheckDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.entity.Admin;
import cn.product.score.entity.Bank;
import cn.product.score.entity.CapitalAccount;
import cn.product.score.entity.CapitalAccount.CapitalAccountStatus;
import cn.product.score.entity.CapitalPlatform;
import cn.product.score.entity.CapitalPlatform.CapitalPlatformTransType;
import cn.product.score.entity.CapitalPlatform.CapitalPlatformType;
import cn.product.score.entity.FrontUser;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserBankcard;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.score.entity.FrontUserHistoryCheck;
import cn.product.score.entity.FrontUserHistoryCheck.FrontUserHistoryCheckStatus;
import cn.product.score.entity.FrontUserHistoryCheck.FrontUserHistoryCheckTransType;
import cn.product.score.entity.FrontUserHistoryCheck.FrontUserHistoryCheckType;
import cn.product.score.service.back.UserWithdrawService;
import cn.product.score.entity.Resource;
import cn.product.score.util.JSONUtils;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.FrontUserHistoryCheckDetailVO;
import cn.product.score.vo.back.FrontUserHistoryCheckVO;
import cn.product.score.vo.back.StatusCountVO;

@Service("userWithdrawService")
public class UserWithdrawServiceImpl implements UserWithdrawService{
	
	@Autowired
	private FrontUserHistoryCheckDao frontUserHistoryCheckDao;

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
    private CapitalPlatformDao capitalPlatformDao;
	
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
    	
		FrontUserHistoryCheck fuhc = this.frontUserHistoryCheckDao.get(uuid);
		if(fuhc == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录不存在！");
			return;
		}
		if(!FrontUserHistoryCheckTransType.WITHDRAW.equals(fuhc.getTransType())){
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
			
			if(!Utlity.checkStringNull(fuhcvo.getValue().getCapitalAccount())){
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
			
			if(!Utlity.checkStringNull(fuhcvo.getValue().getFrontUserBankcard())){
				FrontUserBankcard fub = this.frontUserBankcardDao.get(fuhcvo.getValue().getFrontUserBankcard());
				if(fub != null){
					fuhcvo.getValue().setFrontUserBankcardAccountHolder(fub.getAccountHolder());
					fuhcvo.getValue().setFrontUserBankcardAccountNumber(fub.getAccountNumber());
					Bank bank = this.bankDao.get(fub.getBank());
					if(bank != null){
						fuhcvo.getValue().setFrontUserBankcardBankName(bank.getShortName());
					}
				}
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
		searchMap.put("transType", FrontUserHistoryCheckTransType.WITHDRAW);
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
		searchMap.put("transType", FrontUserHistoryCheckTransType.WITHDRAW);
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
		searchMap.put("transType", FrontUserHistoryCheckTransType.WITHDRAW);
		
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
		searchMap.put("transType", FrontUserHistoryCheckTransType.WITHDRAW);
		searchMap.put("creator", admin);
		
		List<StatusCountVO> list = frontUserHistoryCheckDao.getStatusList(searchMap);
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setTotalResultCount(list.size());
	}

	@Override
	public void rollback(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String reason = paramsMap.get("reason") == null ? "" : paramsMap.get("reason").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistory fuh = this.frontUserHistoryDao.get(uuid);
		if(fuh == null || FrontUserHistoryStatus.DELETE.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("交易记录不存在！");
			return;
		}
		if(!FrontUserHistoryType.USER_WITHDRAW.equals(fuh.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		if(!FrontUserHistoryStatus.NORMAL.equals(fuh.getStatus()) && !FrontUserHistoryStatus.WARNNING.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该交易记录当前无法操作！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", uuid);
		searchMap.put("status", FrontUserHistoryCheckStatus.NORMAL);
		Integer count = this.frontUserHistoryCheckDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该提现正在等待审核，请勿重复提交！");
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(fuh.getFrontUserAccount());
		if(fua.getBalanceLock().compareTo(fuh.getPay()) < 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户冻结资金不足！");
			return;
		}
		
		fuh.setTransData(reason);
		FrontUserHistoryCheck fuhc = new FrontUserHistoryCheck();
		fuhc.setUuid(UUID.randomUUID().toString());
		fuhc.setFrontUserHistory(fuh.getUuid());
		fuhc.setType(FrontUserHistoryCheckType.ROLLBACK);
		fuhc.setTransType(FrontUserHistoryCheckTransType.WITHDRAW);
		fuhc.setValue(JSONUtils.obj2json(fuh));
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setCreator(admin);
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		fuhc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckDao.insert(fuhc);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功");
	}

	@Override
	public void rerollback(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String reason = paramsMap.get("reason") == null ? "" : paramsMap.get("reason").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistoryCheck fuhc = this.frontUserHistoryCheckDao.get(uuid);
		if(fuhc == null || FrontUserHistoryCheckStatus.DELETE.equals(fuhc.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录不存在！");
			return;
		}
		if(!FrontUserHistoryCheckType.ROLLBACK.equals(fuhc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该审核记录无法重新提交！");
			return;
		}
		if(!FrontUserHistoryCheckStatus.NOPASS.equals(fuhc.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该审核记录无法重新提交！");
			return;
		}
		if(!FrontUserHistoryCheckTransType.WITHDRAW.equals(fuhc.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", uuid);
		searchMap.put("status", FrontUserHistoryCheckStatus.NORMAL);
		Integer count = this.frontUserHistoryCheckDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该提现正在等待审核，请勿重复提交！");
			return;
		}
		
		FrontUserHistory fuh = JSONUtils.json2obj(fuhc.getValue(), FrontUserHistory.class);
		fuh.setTransData(reason);
		
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setValue(JSONUtils.obj2json(fuh));
		fuhc.setCreator(admin);
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckDao.update(fuhc);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功");
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String reason = paramsMap.get("reason") == null ? "" : paramsMap.get("reason").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistory fuh = this.frontUserHistoryDao.get(uuid);
		if(fuh == null || FrontUserHistoryStatus.DELETE.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("交易记录不存在！");
			return;
		}
		if(!FrontUserHistoryType.USER_WITHDRAW.equals(fuh.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		if(!FrontUserHistoryStatus.NORMAL.equals(fuh.getStatus()) && !FrontUserHistoryStatus.WARNNING.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该交易记录当前无法操作！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", uuid);
		searchMap.put("status", FrontUserHistoryCheckStatus.NORMAL);
		Integer count = this.frontUserHistoryCheckDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该提现正在等待审核，无法删除！");
			return;
		}
		
		fuh.setTransData(reason);
		FrontUserHistoryCheck fuhc = new FrontUserHistoryCheck();
		fuhc.setUuid(UUID.randomUUID().toString());
		fuhc.setFrontUserHistory(fuh.getUuid());
		fuhc.setType(FrontUserHistoryCheckType.DELETE);
		fuhc.setTransType(FrontUserHistoryCheckTransType.WITHDRAW);
		fuhc.setValue(JSONUtils.obj2json(fuh));
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setCreator(admin);
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		fuhc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckDao.insert(fuhc);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功");
	}

	@Override
	public void redelete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String reason = paramsMap.get("reason") == null ? "" : paramsMap.get("reason").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistoryCheck fuhc = this.frontUserHistoryCheckDao.get(uuid);
		if(fuhc == null || FrontUserHistoryCheckStatus.DELETE.equals(fuhc.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录不存在！");
			return;
		}
		if(!FrontUserHistoryCheckType.DELETE.equals(fuhc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该审核记录无法重新提交！");
			return;
		}
		if(!FrontUserHistoryCheckStatus.NOPASS.equals(fuhc.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该审核记录无法重新提交！");
			return;
		}
		if(!FrontUserHistoryCheckTransType.WITHDRAW.equals(fuhc.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", uuid);
		searchMap.put("status", FrontUserHistoryCheckStatus.NORMAL);
		Integer count = this.frontUserHistoryCheckDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该提现正在等待审核，请勿重复提交！");
			return;
		}
		
		FrontUserHistory fuh = JSONUtils.json2obj(fuhc.getValue(), FrontUserHistory.class);
		fuh.setTransData(reason);
		
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setValue(JSONUtils.obj2json(fuh));
		fuhc.setCreator(admin);
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckDao.update(fuhc);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功");
	}

	@Override
	public void apply(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistory fuh = this.frontUserHistoryDao.get(uuid);
		if(fuh == null || FrontUserHistoryStatus.DELETE.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("交易记录不存在！");
			return;
		}
		if(!FrontUserHistoryStatus.NORMAL.equals(fuh.getStatus()) && !FrontUserHistoryStatus.WARNNING.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该交易记录当前无法操作！");
			return;
		}
		if(!FrontUserHistoryType.USER_WITHDRAW.equals(fuh.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null || !CapitalAccountStatus.NORMAL.equals(ca.getStatus()) || !CapitalPlatformTransType.WITHDRAW.equals(ca.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("企业账户选择有误！");
			return;
		}
		if(!CapitalPlatformType.REAPAL.equals(ca.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("企业账户选择有误！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", uuid);
		searchMap.put("status", FrontUserHistoryCheckStatus.NORMAL);
		Integer count = this.frontUserHistoryCheckDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该提现正在等待审核，请勿重复提交！");
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(fuh.getFrontUserAccount());
		if(fua.getBalanceLock().compareTo(fuh.getPay()) < 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户冻结资金不足！");
			return;
		}
		
		FrontUserHistoryCheck fuhc = new FrontUserHistoryCheck();
		fuhc.setUuid(UUID.randomUUID().toString());
		fuhc.setFrontUserHistory(fuh.getUuid());
		fuhc.setType(FrontUserHistoryCheckType.APPLY);
		fuhc.setTransType(FrontUserHistoryCheckTransType.WITHDRAW);
		fuh.setCapitalAccount(capitalAccount);
		fuhc.setValue(JSONUtils.obj2json(fuh));
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setCreator(admin);
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		fuhc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckDao.insert(fuhc);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功");
	}

	@Override
	public void reapply(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistoryCheck fuhc = this.frontUserHistoryCheckDao.get(uuid);
		if(fuhc == null || FrontUserHistoryCheckStatus.DELETE.equals(fuhc.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录不存在！");
			return;
		}
		if(!FrontUserHistoryCheckType.APPLY.equals(fuhc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该审核记录无法重新提交！");
			return;
		}
		if(!FrontUserHistoryCheckStatus.NOPASS.equals(fuhc.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该审核记录无法重新提交！");
			return;
		}
		if(!FrontUserHistoryCheckTransType.WITHDRAW.equals(fuhc.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null || !CapitalAccountStatus.NORMAL.equals(ca.getStatus()) || !CapitalPlatformTransType.WITHDRAW.equals(ca.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("企业账户选择有误！");
			return;
		}
		if(!CapitalPlatformType.REAPAL.equals(ca.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("企业账户选择有误！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", uuid);
		searchMap.put("status", FrontUserHistoryCheckStatus.NORMAL);
		Integer count = this.frontUserHistoryCheckDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该提现正在等待审核，请勿重复提交！");
			return;
		}
		
		FrontUserHistory fuh = JSONUtils.json2obj(fuhc.getValue(), FrontUserHistory.class);
		fuh.setCapitalAccount(capitalAccount);
		fuhc.setValue(JSONUtils.obj2json(fuh));
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
		String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
		String proof = paramsMap.get("proof") == null ? "" : paramsMap.get("proof").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserHistory fuh = this.frontUserHistoryDao.get(uuid);
		if(fuh == null || FrontUserHistoryStatus.DELETE.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("交易记录不存在！");
			return;
		}
		if(!FrontUserHistoryType.USER_WITHDRAW.equals(fuh.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		if(!FrontUserHistoryStatus.NORMAL.equals(fuh.getStatus()) && !FrontUserHistoryStatus.WARNNING.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该交易记录当前无法审核！");
			return;
		}
		
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null || !CapitalAccountStatus.NORMAL.equals(ca.getStatus()) || !CapitalPlatformTransType.WITHDRAW.equals(ca.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("企业账户选择有误！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", uuid);
		searchMap.put("status", FrontUserHistoryCheckStatus.NORMAL);
		Integer count = this.frontUserHistoryCheckDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该提现正在等待审核，请勿重复提交！");
			return;
		}

		FrontUserAccount fua = this.frontUserAccountDao.get(fuh.getFrontUserAccount());
		if(fua.getBalanceLock().compareTo(fuh.getPay()) < 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户冻结资金不足！");
			return;
		}
		
		FrontUserHistoryCheck fuhc = new FrontUserHistoryCheck();
		fuhc.setUuid(UUID.randomUUID().toString());
		fuhc.setFrontUserHistory(fuh.getUuid());
		fuhc.setType(FrontUserHistoryCheckType.CONFIRM);
		fuhc.setTransType(FrontUserHistoryCheckTransType.WITHDRAW);
		fuh.setCapitalAccount(capitalAccount);
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
		String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
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
		if(!FrontUserHistoryCheckTransType.WITHDRAW.equals(fuhc.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null || !CapitalAccountStatus.NORMAL.equals(ca.getStatus()) || !CapitalPlatformTransType.WITHDRAW.equals(ca.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("企业账户选择有误！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", uuid);
		searchMap.put("status", FrontUserHistoryCheckStatus.NORMAL);
		Integer count = this.frontUserHistoryCheckDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该提现正在等待审核，请勿重复提交！");
			return;
		}
		
		FrontUserHistory fuh = JSONUtils.json2obj(fuhc.getValue(), FrontUserHistory.class);
		fuh.setCapitalAccount(capitalAccount);
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
		if(!FrontUserHistoryCheckTransType.WITHDRAW.equals(fuhc.getTransType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录类型错误！");
			return;
		}
		
		FrontUserHistory fuh = frontUserHistoryDao.get(fuhc.getFrontUserHistory());
		if(fuh == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("待审核记录有误！");
			return;
		}
		if(!FrontUserHistoryStatus.NORMAL.equals(fuh.getStatus()) && !FrontUserHistoryStatus.WARNNING.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该交易记录当前无法操作！");
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
			if(!FrontUserHistoryCheckType.CONFIRM.equals(fuhc.getType()) && !FrontUserHistoryCheckType.APPLY.equals(fuhc.getType()) && !FrontUserHistoryCheckType.ROLLBACK.equals(fuhc.getType()) && !FrontUserHistoryCheckType.DELETE.equals(fuhc.getType())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("待审核信息有误！");
				return;
			}
			
			FrontUserAccount fua = this.frontUserAccountDao.get(fuh.getFrontUserAccount());
			
			if(fua.getBalanceLock().compareTo(fuh.getPay()) < 0){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户冻结资金不足！");
				return;
			}
			
			fuhc.setReason(Utlity.checkStringNull(reason) ? "审核通过！" : reason);
			fuhc.setStatus(status);
			fuhc.setChecker(admin);
			fuhc.setChecktime(new Timestamp(System.currentTimeMillis()));
			this.frontUserHistoryCheckDao.checkWithdraw(fuhc);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("审核成功");
			return;
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("审核状态有误！");
			return;
		}
	}
}
