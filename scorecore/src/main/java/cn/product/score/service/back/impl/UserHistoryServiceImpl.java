package cn.product.score.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.product.score.entity.CapitalPlatform;
import cn.product.score.entity.FrontUser;
import cn.product.score.entity.FrontUserBankcard;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.product.score.service.back.UserHistoryService;
import cn.product.score.entity.FrontUserHistoryCheck;
import cn.product.score.entity.Resource;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.FrontUserHistoryVO;
import cn.product.score.vo.back.StatusCountVO;

/**
 */
@Service("userHistoryService")
public class UserHistoryServiceImpl implements UserHistoryService{
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private FrontUserDao frontUserDao;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private FrontUserBankcardDao frontUserBankcardDao;
	
	@Autowired
    private FrontUserHistoryCheckDao frontUserHistoryCheckDao;
	
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
    	
		FrontUserHistory fuh = frontUserHistoryDao.get(uuid);
		if(fuh == null || FrontUserHistoryStatus.DELETE.equals(fuh.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("交易信息不存在");
			return;
		}
		
		FrontUserHistoryVO fuhvo = new FrontUserHistoryVO(fuh);
		
		FrontUser fu = this.frontUserDao.get(fuh.getFrontUser());
		if(fu != null){
			fuhvo.setFrontUserName(fu.getRealname());
			fuhvo.setFrontUserMobile(fu.getMobile());
		}
		
		if(!Utlity.checkStringNull(fuh.getCapitalAccount())){
			CapitalAccount ca = this.capitalAccountDao.get(fuh.getCapitalAccount());
			if(ca != null){
				fuhvo.setCapitalAccountName(ca.getName());
				
				CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
				if(cp != null){
					fuhvo.setCapitalPlatform(cp.getUuid());
					fuhvo.setCapitalPlatformName(cp.getName());
				}
			}
		}
		
		if(!Utlity.checkStringNull(fuh.getFrontUserBankcard())){
			FrontUserBankcard fub = this.frontUserBankcardDao.get(fuh.getFrontUserBankcard());
			if(fub != null){
				fuhvo.setFrontUserBankcardAccountHolder(fub.getAccountHolder());
				fuhvo.setFrontUserBankcardAccountNumber(fub.getAccountNumber());
				Bank bank = this.bankDao.get(fub.getBank());
				if(bank != null){
					fuhvo.setFrontUserBankcardBankName(bank.getShortName());
				}
			}
		}
		
		if(!Utlity.checkStringNull(fuh.getOperator())){
			Admin operator = this.adminDao.get(fuh.getOperator());
			if(operator != null){
				fuhvo.setOperatorName(operator.getRealname());
			}
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", uuid);
		
		List<FrontUserHistoryCheck> checkList = this.frontUserHistoryCheckDao.getListByParams(searchMap);
		if(checkList.size() > 0){
			FrontUserHistoryCheck check = checkList.get(0);
			fuhvo.setFrontUserAccountCheck(check.getUuid());
			
			if(!Utlity.checkStringNull(check.getProof())){
				Resource proof = this.resourceDao.get(check.getProof());
				if(proof != null){
					fuhvo.setFrontUserAccountCheckProof(proof.getUrl());
				}
			}
		}
		
		result.setData(fuhvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("capitalAccount", capitalAccount);
		searchMap.put("type", type);
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			searchMap.put("status", status);
		}
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserHistoryDao.getCountByParams(searchMap);
		List<FrontUserHistory> fuhList = frontUserHistoryDao.getListByParams(searchMap);
		
		List<FrontUserHistoryVO> voList = new ArrayList<FrontUserHistoryVO>();
		for(FrontUserHistory fuh : fuhList){
			FrontUserHistoryVO fuhvo = new FrontUserHistoryVO(fuh);
			
			FrontUser fu = this.frontUserDao.get(fuh.getFrontUser());
			if(fu != null){
				fuhvo.setFrontUserName(fu.getRealname());
				fuhvo.setFrontUserMobile(fu.getMobile());
			}
			
			if(!Utlity.checkStringNull(fuh.getCapitalAccount())){
				CapitalAccount ca = this.capitalAccountDao.get(fuh.getCapitalAccount());
				if(ca != null){
					fuhvo.setCapitalAccountName(ca.getName());
					
					CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
					if(cp != null){
						fuhvo.setCapitalPlatform(cp.getUuid());
						fuhvo.setCapitalPlatformName(cp.getName());
					}
				}
			}
			
			if(!Utlity.checkStringNull(fuh.getFrontUserBankcard())){
				FrontUserBankcard fub = this.frontUserBankcardDao.get(fuh.getFrontUserBankcard());
				if(fub != null){
					fuhvo.setFrontUserBankcardAccountHolder(fub.getAccountHolder());
					Bank bank = this.bankDao.get(fub.getBank());
					if(bank != null){
						fuhvo.setFrontUserBankcardBankName(bank.getShortName());
					}
				}
			}
			
			if(!Utlity.checkStringNull(fuh.getOperator())){
				Admin operator = this.adminDao.get(fuh.getOperator());
				if(operator != null){
					fuhvo.setOperatorName(operator.getRealname());
				}
			}
			voList.add(fuhvo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void statusList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
    	
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("type", type);
		
		List<StatusCountVO> list = frontUserHistoryDao.getStatusList(searchMap);
		result.setData(list);
		result.setTotalResultCount(list.size());
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
}
