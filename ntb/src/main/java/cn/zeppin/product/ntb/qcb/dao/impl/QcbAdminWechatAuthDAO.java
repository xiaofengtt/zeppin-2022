/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbAdminWechatAuth;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbAdminWechatAuthDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbAdminWechatAuthDAO extends BaseDAO<QcbAdminWechatAuth, String> implements IQcbAdminWechatAuthDAO {


	/**
	 * 向数据库添加一条qcbAdminWechatAuth数据
	 * @param qcbAdminWechatAuth
	 * @return qcbAdminWechatAuth
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbAdminWechatAuths", key = "'qcbAdminWechatAuths:' + #qcbAdminWechatAuth.uuid")})
	public QcbAdminWechatAuth insert(QcbAdminWechatAuth qcbAdminWechatAuth) {
		return super.insert(qcbAdminWechatAuth);
	}

	/**
	 * 向数据库删除一条qcbAdminWechatAuth数据
	 * @param qcbAdminWechatAuth
	 * @return qcbAdminWechatAuth
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbAdminWechatAuths", key = "'qcbAdminWechatAuths:' + #qcbAdminWechatAuth.uuid")})
	public QcbAdminWechatAuth delete(QcbAdminWechatAuth qcbAdminWechatAuth) {
		return super.delete(qcbAdminWechatAuth);
	}

	/**
	 * 向数据库更新一条QcbAdminWechatAuths数据
	 * @param qcbAdminWechatAuths
	 * @return QcbAdminWechatAuths
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbAdminWechatAuths", key = "'qcbAdminWechatAuths:' + #qcbAdminWechatAuth.uuid")})
	public QcbAdminWechatAuth update(QcbAdminWechatAuth qcbAdminWechatAuth) {
		return super.update(qcbAdminWechatAuth);
	}

	/**
	 * 根据uuid得到一个QcbAdminWechatAuths信息
	 * @param uuid
	 * @return QcbAdminWechatAuths
	 */
	@Override
	@Cacheable(value = "qcbAdminWechatAuths", key = "'qcbAdminWechatAuths:' + #uuid")
	public QcbAdminWechatAuth get(String uuid) {
		return super.get(uuid);
	}
}
