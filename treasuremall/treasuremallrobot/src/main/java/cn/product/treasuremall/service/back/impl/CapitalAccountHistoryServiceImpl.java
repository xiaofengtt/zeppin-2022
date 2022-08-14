package cn.product.treasuremall.service.back.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.AdminDao;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.CapitalAccountHistoryDao;
import cn.product.treasuremall.dao.CapitalPlatformDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.service.back.CapitalAccountHistoryService;

@Service("capitalAccountHistoryService")
public class CapitalAccountHistoryServiceImpl implements CapitalAccountHistoryService{
	
	@Autowired
	private CapitalAccountHistoryDao capitalAccountHistoryDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
	@Autowired
	private CapitalPlatformDao capitalPlatformDao;

	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private AdminDao adminDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
//		CapitalAccountHistory cah = capitalAccountHistoryDao.get(uuid);
//		if(cah == null || CapitalAccountHistoryStatus.DELETE.equals(cah.getStatus())){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("流水信息不存在");
//			return;
//		}
//		
//		CapitalAccountHistoryVO cahvo = new CapitalAccountHistoryVO(cah);
//		CapitalAccount ca = this.capitalAccountDao.get(cah.getCapitalAccount());
//		if(ca != null){
//			cahvo.setCapitalAccountName(ca.getName());
//		}
//		CapitalPlatform cp = this.capitalPlatformDao.get(cah.getCapitalPlatform());
//		if(cp != null){
//			cahvo.setCapitalPlatformName(cp.getName());
//		}
//		if(!Utlity.checkStringNull(cah.getFrontUser())){
//			FrontUser fu = this.frontUserDao.get(cah.getFrontUser());
//			if(fu != null){
//				cahvo.setFrontUserName(fu.getRealname());
//				cahvo.setFrontUserMobile(fu.getMobile());
//			}
//		}
//		if(!Utlity.checkStringNull(cah.getOperator())){
//			Admin operator = this.adminDao.get(cah.getOperator());
//			if(operator != null){
//				cahvo.setOperatorName(operator.getRealname());
//			}
//		}
//		
//		result.setData(cahvo);
//		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String capitalPlatform = paramsMap.get("capitalPlatform") == null ? "" : paramsMap.get("capitalPlatform").toString();
		String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		String operator = paramsMap.get("operator") == null ? "" : paramsMap.get("operator").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
//		Map<String, Object> paramsls = new HashMap<String, Object>();
//		paramsls.put("capitalPlatform", capitalPlatform);
//		paramsls.put("capitalAccount", capitalAccount);
//		paramsls.put("frontUser", frontUser);
//		paramsls.put("operator", operator);
//		if(!Utlity.checkStringNull(type) && !"all".equals(type)){
//			paramsls.put("type", type);
//		}
//		paramsls.put("status", status);
//		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
//			paramsls.put("offSet", (pageNum-1)*pageSize);
//			paramsls.put("pageSize", pageSize);
//		}
//		paramsls.put("sort", sort);
//		
//		Integer totalCount = capitalAccountHistoryDao.getCountByParams(paramsls);
//		List<CapitalAccountHistory> cahList = capitalAccountHistoryDao.getListByParams(paramsls);
//		
//		List<CapitalAccountHistoryVO> voList = new ArrayList<CapitalAccountHistoryVO>();
//		for(CapitalAccountHistory cah : cahList){
//			CapitalAccountHistoryVO cahvo = new CapitalAccountHistoryVO(cah);
//			CapitalAccount ca = this.capitalAccountDao.get(cah.getCapitalAccount());
//			if(ca != null){
//				cahvo.setCapitalAccountName(ca.getName());
//			}
//			CapitalPlatform cp = this.capitalPlatformDao.get(cah.getCapitalPlatform());
//			if(cp != null){
//				cahvo.setCapitalPlatformName(cp.getName());
//			}
//			if(!Utlity.checkStringNull(cah.getFrontUser())){
//				FrontUser fu = this.frontUserDao.get(cah.getFrontUser());
//				if(fu != null){
//					cahvo.setFrontUserName(fu.getRealname());
//					cahvo.setFrontUserMobile(fu.getMobile());
//				}
//			}
//			if(!Utlity.checkStringNull(cah.getOperator())){
//				Admin op = this.adminDao.get(cah.getOperator());
//				if(op != null){
//					cahvo.setOperatorName(op.getRealname());
//				}
//			}
//			voList.add(cahvo);
//		}
//		
//		result.setData(voList);
//		result.setStatus(ResultStatusType.SUCCESS);
//		result.setMessage("success");
//		result.setPageNum(pageNum);
//		result.setPageSize(pageSize);
//		result.setTotalResultCount(totalCount);
	}

	@Override
	public void typeList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsls = new HashMap<String, Object>();
//		paramsls.put("status", CapitalAccountHistoryStatus.SUCCESS);
//		List<StatusCountVO> list = capitalAccountHistoryDao.getTypeList(paramsls);
//		result.setData(list);
//		result.setStatus(ResultStatusType.SUCCESS);
//		result.setMessage("success");
//		result.setTotalResultCount(list.size());
	}
	
}
