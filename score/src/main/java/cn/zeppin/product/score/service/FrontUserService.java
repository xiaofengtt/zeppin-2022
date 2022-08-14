package cn.zeppin.product.score.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.controller.base.TransactionException;
import cn.zeppin.product.score.entity.FrontUser;

public interface FrontUserService extends IService<FrontUser>{
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<FrontUser> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据电话号码获取用户
	 * @param mobile
	 * @return
	 */
	public FrontUser getByMobile(String mobile);
	
	/**
	 * 是否存在相同电话号码
	 * @param mobile
	 * @param mobile
	 * @return
	 */
	public Boolean isExistFrontUserByMobile(String mobile, String uuid);
	
	/**
	 * 注册
	 * @param token
	 * @param phone
	 * @return
	 * @throws TransactionException 
	 */
	public HashMap<String, Object> register(String token,String mobile) throws TransactionException;
	
	/**
	 * 登录
	 * @param token
	 * @param phone
	 * @return
	 * @throws TransactionException 
	 */
	public HashMap<String, Object> loginByCode(String token, String phone) throws TransactionException;
	
	/**
	 * 实名认证
	 * @param token
	 * @return
	 * @throws TransactionException 
	 */
	public void certification(String idcard, String username, FrontUser frontUser) throws TransactionException;
}
