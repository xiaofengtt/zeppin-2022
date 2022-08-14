/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkWebmarketSwitchDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkWebmarketSwitchService;
import cn.zeppin.product.ntb.core.entity.BkWebmarketSwitch;
import cn.zeppin.product.ntb.core.entity.BkWebmarketSwitch.BkWebmarketSwitchStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class BkWebmarketSwitchService extends BaseService implements IBkWebmarketSwitchService {

	@Autowired
	private IBkWebmarketSwitchDAO bkWebmarketSwitchDAO;

	@Override
	public BkWebmarketSwitch insert(BkWebmarketSwitch bkWebmarketSwitch) {
		return bkWebmarketSwitchDAO.insert(bkWebmarketSwitch);
	}

	@Override
	public BkWebmarketSwitch delete(BkWebmarketSwitch bkWebmarketSwitch) {
		bkWebmarketSwitch.setWebMarket(bkWebmarketSwitch.getWebMarket()+"_#"+bkWebmarketSwitch.getUuid());
		bkWebmarketSwitch.setStatus(BkWebmarketSwitchStatus.DELETED);
		return bkWebmarketSwitchDAO.update(bkWebmarketSwitch);
	}

	@Override
	public BkWebmarketSwitch update(BkWebmarketSwitch bkWebmarketSwitch) {
		return bkWebmarketSwitchDAO.update(bkWebmarketSwitch);
	}

	@Override
	public BkWebmarketSwitch get(String uuid) {
		return bkWebmarketSwitchDAO.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return bkWebmarketSwitchDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return bkWebmarketSwitchDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return bkWebmarketSwitchDAO.getCount(inputParams);
	}

	@Override
	public boolean isExistBkWebmarketSwitch(String name, String version, String uuid) {
		// TODO Auto-generated method stub
		return bkWebmarketSwitchDAO.isExistBkWebmarketSwitch(name, version, uuid);
	}
}
