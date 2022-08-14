package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.controller.base.TransactionException;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserBlacklist;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.entity.MobileCode;

public interface FrontUserDao extends IDao<FrontUser>{
	
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
	 * @param fu
	 * @param mc
	 * @return
	 */
	public FrontUser register(FrontUser fu, MobileCode mc);
	
	/**
	 * 实名认证
	 * @param token
	 * @return
	 * @throws TransactionException 
	 */
	public void certification(String bank, String bankcard, String mobile, String idcard, String username, FrontUser frontUser, MobileCode mc) throws TransactionException;

	/**
	 * 批量更新
	 */
	public void batchUpdateStatus(Map<String, Object> params);

	/**
	 * 添加机器人用户
	 * @param fu
	 */
	public void insertRobotUser(FrontUser fu);
	
	/**
	 * 修改手机号
	 * @param frontUser
	 * @param codeList
	 */
	public void updateFrontUser(FrontUser frontUser, String mobile, List<MobileCode> codeList);
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public Integer getCountAllByParams(Map<String, Object> params);

	public void updateVoucherTask(List<FrontUserVoucher> update);
	
	/**
	 * 设置黑名单
	 * @param frontUser
	 * @param fubl
	 */
	public void updateBlacklist(FrontUser frontUser, FrontUserBlacklist fubl);
	
	/**
	 * 获取注册统计数据
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getRegistListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getRobotCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<FrontUser> getRobotListByParams(Map<String, Object> params);
	
}
