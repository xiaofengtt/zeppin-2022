/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.api;

import cn.zeppin.product.ntb.core.entity.QcbAdminWechatAuth;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IQcbAdminWechatAuthService extends IBaseService<QcbAdminWechatAuth, String> {
	
	/**
	 * 身份认证
	 */
	public void check(QcbAdminWechatAuth qawa) throws TransactionException;
}
