package cn.product.worldmall.service.back.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.AdminDao;
import cn.product.worldmall.dao.BankDao;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.CapitalPlatformDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserBankcardDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserRechargeAmountSetDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.FrontUserRechargeAmountSet;
import cn.product.worldmall.entity.FrontUserRechargeAmountSet.FrontUserRechargeAmountSetStatus;
import cn.product.worldmall.service.back.UserRechargeAmountSetService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("userRechargeAmountSetService")
public class UserRechargeAmountSetServiceImpl implements UserRechargeAmountSetService{
	
	@Autowired
    private FrontUserRechargeAmountSetDao frontUserRechargeAmountSetDao;
	
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
    	
    	FrontUserRechargeAmountSet furo = frontUserRechargeAmountSetDao.get(uuid);
    	if(furo == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值金额设置信息不存在");
			return;
		}
    	
		result.setData(furo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			searchMap.put("status", status);
		}
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<FrontUserRechargeAmountSet> list = this.frontUserRechargeAmountSetDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserRechargeAmountSetDao.getCountByParams(searchMap);
		
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
		return;
	}

	/**
	 * 添加
	 */
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
		String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
		String giveDAmountStr = paramsMap.get("giveDAmount") == null ? "0" : paramsMap.get("giveDAmount").toString();
		Integer sort = Integer.valueOf(paramsMap.get("sort") == null ? "0" : paramsMap.get("sort").toString());
		Boolean isPreferential = paramsMap.get("isPreferential") == null ? false : Boolean.valueOf(paramsMap.get("isPreferential").toString());
		
		if(Utlity.checkStringNull(amountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("请输入正确的充值金额！");
			return;
		}
		
		if(Utlity.checkStringNull(dAmountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("请输入正确的充值金币数！");
			return;
		}
		
		if(Utlity.checkStringNull(giveDAmountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("请输入正确的赠送金币数！");
			return;
		}
		
		BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountStr));
		BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(dAmountStr));
		BigDecimal giveDAmount = BigDecimal.valueOf(Double.valueOf(giveDAmountStr));
		
		FrontUserRechargeAmountSet furas = new FrontUserRechargeAmountSet();
		furas.setUuid(UUID.randomUUID().toString());
		furas.setAmount(amount);
		furas.setdAmount(dAmount);
		furas.setGiveDAmount(giveDAmount);
		furas.setIsPreferential(isPreferential);
		furas.setSort(sort);
		furas.setStatus(FrontUserRechargeAmountSetStatus.DISABLE);//默认不可用
		
		this.frontUserRechargeAmountSetDao.insert(furas);
	}

	/**
	 * 修改
	 */
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
		String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
		String giveDAmountStr = paramsMap.get("giveDAmount") == null ? "0" : paramsMap.get("giveDAmount").toString();
		Integer sort = Integer.valueOf(paramsMap.get("sort") == null ? "0" : paramsMap.get("sort").toString());
		Boolean isPreferential = paramsMap.get("isPreferential") == null ? false : Boolean.valueOf(paramsMap.get("isPreferential").toString());
		
		FrontUserRechargeAmountSet furas = this.frontUserRechargeAmountSetDao.get(uuid);
		if(furas == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("请输入正确的充值金额！");
			return;
		}
			
		
		if(Utlity.checkStringNull(amountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("请输入正确的充值金额！");
			return;
		}
		
		if(Utlity.checkStringNull(dAmountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("请输入正确的充值金币数！");
			return;
		}
		
		if(Utlity.checkStringNull(giveDAmountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("请输入正确的赠送金币数！");
			return;
		}
		
		BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountStr));
		BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(dAmountStr));
		BigDecimal giveDAmount = BigDecimal.valueOf(Double.valueOf(giveDAmountStr));
		
		furas.setAmount(amount);
		furas.setdAmount(dAmount);
		furas.setGiveDAmount(giveDAmount);
		furas.setIsPreferential(isPreferential);
		furas.setSort(sort);
		
		this.frontUserRechargeAmountSetDao.update(furas);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	/**
	 * 设置失效生效
	 */
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		if(!FrontUserRechargeAmountSetStatus.NORMAL.equals(status) && !FrontUserRechargeAmountSetStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("变更状态有误");
			return;
		}
		try {
			FrontUserRechargeAmountSet furas = frontUserRechargeAmountSetDao.get(uuid);
			if(furas == null || FrontUserRechargeAmountSetStatus.DELETE.equals(furas.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("充值金额设置信息不存在");
				return;
			}
			furas.setStatus(status);
			this.frontUserRechargeAmountSetDao.update(furas);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("修改过程异常！");
			return;
		}

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
    	FrontUserRechargeAmountSet furo = frontUserRechargeAmountSetDao.get(uuid);
    	if(furo == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值金额设置信息不存在");
			return;
		}
    	
    	furo.setStatus(FrontUserRechargeAmountSetStatus.DELETE);
    	this.frontUserRechargeAmountSetDao.update(furo);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("删除成功！");
	}

	
}
