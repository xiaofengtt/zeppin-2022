package cn.product.score.service.front.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.controller.base.TransactionException;
import cn.product.score.dao.FrontUserDao;
import cn.product.score.entity.FrontUser;
import cn.product.score.service.front.FrontUserService;
import cn.product.score.util.Base64Util;
import cn.product.score.util.Utlity;

@Service("frontUserService")
public class FrontUserServiceImpl implements FrontUserService{
	
	@Autowired
	private FrontUserDao frontUserDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		
	}

	@Override
	public void certification(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
    	String idcard = paramsMap.get("idcard") == null ? "" : paramsMap.get("idcard").toString();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	
		name = Base64Util.getFromBase64(name);
		idcard = Base64Util.getFromBase64(idcard);
		idcard = idcard.toUpperCase();
		if(!Utlity.checkIdCard(idcard)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("认证失败，请填写正确的身份证号。");
			return;
		}
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("idcard", idcard);
		Integer count = this.frontUserDao.getCountByParams(paramsls);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("认证失败，该身份证信息已被其他用户认证。");
			return;
		}
		try {
			FrontUser frontUser = this.frontUserDao.get(fu);
			
			this.frontUserDao.certification(idcard, name, frontUser);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("实名认证成功！！");
			return;
		} catch (TransactionException e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("你输入的身份认证信息有误，请重新认证。");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常，实名认证失败！");
			return;
		}
	}
}
