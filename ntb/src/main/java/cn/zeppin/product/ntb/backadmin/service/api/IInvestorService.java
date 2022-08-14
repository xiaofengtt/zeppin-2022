/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IInvestorService extends IBaseService<Investor, String> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 获取状态列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 获取未上传状态列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getNotuploadCount(Class<? extends Entity> resultClass);
	
	/**
	 * 根据openID获取信息
	 * @param openID
	 * @return
	 */
	public Investor getByOpenID(String openID, Class<? extends Entity> resultClass);
	
	/**
	 * 根据username获取信息
	 * @param openID
	 * @return
	 */
	public Investor getByMobile(String mobile, Class<? extends Entity> resultClass);
	
	/**
	 * 根据支付宝账号获取用户信息
	 * @param openID
	 * @param resultClass
	 * @return
	 */
	public Investor getByAliUserid(String userid, Class<? extends Entity> resultClass);
	
	/**
	 * 验证同手机号的用户是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	public boolean isExistInvestorByMobile(String mobile, String uuid);
	
	/**
	 * 注册
	 * @param token
	 * @param phone
	 * @return
	 * @throws TransactionException 
	 */
	public HashMap<String, Object> register(String token,String phone, Map<String, Object> other) throws TransactionException;
	
	/**
	 * 登录
	 * @param token
	 * @return
	 * @throws TransactionException 
	 */
	public HashMap<String, Object> login(String token) throws TransactionException;
	
	/**
	 * 登录
	 * @param token
	 * @return
	 * @throws TransactionException 
	 */
	public HashMap<String, Object> loginBycode(String token) throws TransactionException;
	
	/**
	 * 重置密码
	 * @param token
	 * @return
	 * @throws TransactionException 
	 */
	public void loginResetpwd(String token,String phone, String step) throws TransactionException;
	
	/**
	 * 实名认证
	 * @param token
	 * @return
	 * @throws TransactionException 
	 */
	public void logincertification(String idcard, String username, Investor investor, String imgface, String imgback) throws TransactionException;
	
	public void updateBatch(List<Investor> listUpdate);
	
	/**
	 * 畅捷支付-鉴权绑卡请求
	 * @param phone
	 * @param bankcard
	 * @param idcard
	 * @param username
	 * @param investor
	 * @param billDevice
	 * @return
	 */
	public HashMap<String, Object> updateBindingBankcard(String phone, String bankcard, String idcard, String username, Investor investor, String billDevice);
	
	/**
	 * 畅捷支付-鉴权绑卡确认
	 * @param orderNum
	 * @param scode
	 * @param investor
	 * @param billDevice
	 * @return
	 */
	public HashMap<String, Object> updateBindingBankcard(String bank, String bankcard, String phone, String orderNum, String scode, Investor investor, String orderNumStr);
	
	/**
	 * 畅捷支付-鉴权解绑
	 * @param ib
	 * @param investor
	 * @param billDevice
	 * @return
	 */
	public HashMap<String, Object> updateunBindingBankcard(MobileCode mc, InvestorBankcard ib, Investor investor, String orderNumStr);

	public HashMap<String, Object> updateBindingAliUserInfo(Investor investor, String accessToken);
	
	/**
	 * 余额自动转入活期理财
	 */
	public void updateCurrentAccount();
	
	/**
	 * 更新活期理财
	 */
	public void updateCurrentPay(Investor investor, InvestorAccountHistory iah);
	
	/**
	 * 更新昨日余额
	 */
	public void updateYesterdayAccount();
}
