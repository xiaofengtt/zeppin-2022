/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkAreaDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author elegantclack
 *
 */
@Service
public class BkAreaService extends BaseService implements IBkAreaService {

	@Autowired
	private IBkAreaDAO  bkAreaDAO;

	
	@Override
	public BkArea get(String uuid) {
		return bkAreaDAO.get(uuid);
	}
	
	/**
	 * 向表中插入一条Controller数据
	 */
	@Override
	public BkArea insert(BkArea area) {
		return bkAreaDAO.insert(area);
	}

	@Override
	public BkArea delete(BkArea area) {
		return bkAreaDAO.delete(area);
	}

	@Override
	public BkArea update(BkArea area) {
		return bkAreaDAO.update(area);
	}

	/**
	 * 获取所有功能信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return bkAreaDAO.getAll(resultClass);
	}
}
