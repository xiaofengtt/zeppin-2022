package cn.product.score.service.front.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.service.front.FrontUserAccountService;
import cn.product.score.vo.front.FrontUserHistoryVO;

@Service("frontUserAccountService")
public class FrontUserAccountServiceImpl implements FrontUserAccountService{
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	
		FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(fu);
		
		result.setData(fua);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void historyList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("frontUser", fu);
		paramsls.put("type", type);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			paramsls.put("offSet", (pageNum-1)*pageSize);
			paramsls.put("pageSize", pageSize);
		}
		paramsls.put("sort", "createtime desc");
		
		List<FrontUserHistory> list = this.frontUserHistoryDao.getListByParams(paramsls);
		List<FrontUserHistoryVO> voList = new ArrayList<FrontUserHistoryVO>();
		for(FrontUserHistory fuh : list){
			voList.add(new FrontUserHistoryVO(fuh));
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
