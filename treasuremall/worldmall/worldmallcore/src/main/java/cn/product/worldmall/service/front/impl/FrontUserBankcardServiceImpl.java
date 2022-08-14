package cn.product.worldmall.service.front.impl;

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
import cn.product.worldmall.dao.BankDao;
import cn.product.worldmall.dao.FrontUserBankcardDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.MobileCodeDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserBankcard;
import cn.product.worldmall.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.product.worldmall.entity.FrontUserBankcard.FrontUserBankcardType;
import cn.product.worldmall.service.front.FrontUserBankcardService;

/**
 * 20200820暂时取消银行卡的相关操作
 * 改为绑定paypol账户
 * @author user
 *
 */
@Service("frontUserBankcardService")
public class FrontUserBankcardServiceImpl implements FrontUserBankcardService{
	
	@Autowired
	private FrontUserBankcardDao frontUserBankcardDao;
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserDao frontUserDao;

	@Autowired
	private SystemParamDao systemParamDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
    	
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("frontUser", fu);
		paramsls.put("status", FrontUserBankcardStatus.NORMAL);
		paramsls.put("type", type);
		List<FrontUserBankcard> list = this.frontUserBankcardDao.getListByParams(paramsls);
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		if(list != null && !list.isEmpty()){
			for(FrontUserBankcard fub : list){
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("uuid", fub.getUuid());
				resultMap.put("accountNumber", fub.getAccountNumber());
				listResult.add(resultMap);
			}
		}
		result.setData(listResult);
		result.setTotalResultCount(listResult.size());
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUserBankcard fub = this.frontUserBankcardDao.get(uuid);
		if(!fu.equals(fub.getFrontUser())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Account information is wrong!");
			return;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("uuid", fub.getUuid());
		resultMap.put("accountNumber", fub.getAccountNumber());
		result.setData(resultMap);
		result.setMessage("Successful!");
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	String accountNumber = paramsMap.get("accountNumber") == null ? "" : paramsMap.get("accountNumber").toString();
    	
		FrontUser frontUser = this.frontUserDao.get(fu);
    	if(frontUser == null) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User status abnormality, please contact customer service!");
			return;
    	}
    	
		
		try {
			
			Map<String, Object> paramsls = new HashMap<String, Object>();
			paramsls.put("frontUser", fu);
			paramsls.put("accountNumber", accountNumber);
			paramsls.put("type", FrontUserBankcardType.ACCOUNT);
			List<FrontUserBankcard> list = this.frontUserBankcardDao.getListByParams(paramsls);
			if(list != null && list.size() > 0){//编辑
//				result.setStatus(ResultStatusType.FAILED);
//				result.setMessage("Account has been bound");
//				return;
				FrontUserBankcard fub = list.get(0);
				fub.setAccountNumber(accountNumber);
				this.frontUserBankcardDao.update(fub);
				
				result.setMessage("Successful!");
				result.setStatus(ResultStatusType.SUCCESS);
				return;
			}
			
			//信息入库-新增
			FrontUserBankcard fub = new FrontUserBankcard();
			fub.setUuid(UUID.randomUUID().toString());
			fub.setCreatetime(new Timestamp(System.currentTimeMillis()));
			fub.setAccountNumber(accountNumber);
			fub.setStatus(FrontUserBankcardStatus.NORMAL);
			fub.setFrontUser(frontUser.getUuid());
			fub.setType(FrontUserBankcardType.ACCOUNT);
			
			this.frontUserBankcardDao.insert(fub);
			result.setMessage("Successful!");
			result.setStatus(ResultStatusType.SUCCESS);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Exception！");
			return;
		}
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUserBankcard fub = this.frontUserBankcardDao.get(uuid);
		if(!fu.equals(fub.getFrontUser())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Account information is wrong!");
			return;
		}
		
		fub.setStatus(FrontUserBankcardStatus.DELETE);
		fub.setAccountNumber(fub.getAccountNumber()+"_#"+fub.getUuid());
		
		this.frontUserBankcardDao.update(fub);
		result.setMessage("Successful!");
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
}
