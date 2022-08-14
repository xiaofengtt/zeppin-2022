/**
 * 
 */
package cn.zeppin.product.ntb.shbx.service.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IShbxUserService extends IBaseService<ShbxUser, String> {
	
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
	 * 根据mobile获取信息
	 * @param mobile
	 * @return
	 */
	public ShbxUser getByMobile(String mobile);
	
	/**
	 * 根据openId获取信息
	 * @param openId
	 * @return
	 */
	public ShbxUser getByOpenId(String openId);
	
	/**
	 * 验证同手机号的用户是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	public boolean isExistShbxUserByMobile(String mobile, String uuid);
	
	/**
	 * 验证同身份证号的用户是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	public boolean isExistShbxUserByIdcard(String idcard, String uuid);
	
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
	public HashMap<String, Object> loginBycode(String token, String phone) throws TransactionException;
	
	/**
	 * 实名认证
	 * @param token
	 * @return
	 * @throws TransactionException 
	 */
	public void logincertification(String idcard, String username, ShbxUser shbxUser) throws TransactionException;
	
	/**
	 * 鉴权绑卡请求
	 * @param phone
	 * @param bankcard
	 * @param idcard
	 * @param username
	 * @param investor
	 * @param billDevice
	 * @return
	 */
	public HashMap<String, Object> updateBindingBankcard(String phone, String bankcard, String idcard, String username, ShbxUser shbxUser, String billDevice);

	/**
	 * 批处理更新
	 * @param listUpdate
	 */
	public void updateBatch(List<ShbxUser> listUpdate);
}
