/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IPlatformAccountDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IPlatformAccountService;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 *
 */
@Service
public class PlatformAccountService extends BaseService implements IPlatformAccountService {

	@Autowired
	private IPlatformAccountDAO platformAccountDAO;
	
	@Override
	public PlatformAccount insert(PlatformAccount platformAccount) {
		return platformAccountDAO.insert(platformAccount);
	}

	@Override
	public PlatformAccount delete(PlatformAccount platformAccount) {
		return platformAccountDAO.delete(platformAccount);
	}

	@Override
	public PlatformAccount update(PlatformAccount platformAccount) {
		return platformAccountDAO.update(platformAccount);
	}

	@Override
	public PlatformAccount get(String uuid) {
		return platformAccountDAO.get(uuid);
	}
}
