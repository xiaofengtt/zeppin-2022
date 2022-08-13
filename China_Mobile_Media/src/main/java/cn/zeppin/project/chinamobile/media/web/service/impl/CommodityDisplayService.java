/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.project.chinamobile.media.core.entity.Commodity;
import cn.zeppin.project.chinamobile.media.core.entity.CommodityDisplay;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.ICommodityDisplayDAO;
import cn.zeppin.project.chinamobile.media.web.service.api.ICommodityDisplayService;
import cn.zeppin.project.chinamobile.media.web.service.base.BaseService;

/**
 * @author Clark.R 2016年3月29日
 *
 */
@Service
public class CommodityDisplayService extends BaseService implements ICommodityDisplayService {
	
	@Autowired
	private ICommodityDisplayDAO commodityDisplayDAO;

	public CommodityDisplay insert(CommodityDisplay commodity) {
		return this.commodityDisplayDAO.insert(commodity);
	}

	public CommodityDisplay delete(CommodityDisplay commodity) {
		return this.commodityDisplayDAO.delete(commodity);
	}

	public CommodityDisplay update(CommodityDisplay commodity) {
		return this.commodityDisplayDAO.update(commodity);
	}

	public CommodityDisplay get(String id) {
		return this.commodityDisplayDAO.get(id);
	}

	public List<CommodityDisplay> getAll() {
		return this.commodityDisplayDAO.getAll();
	}

	@Override
	public List<Entity> getListByCommodity(Commodity commodity,
			 @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		
		return this.commodityDisplayDAO.getListByCommodity(commodity, resultClass);
	}

	@Override
	public List<Entity> getListByCommodity(Commodity commodity, String sort,
			 @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		return this.commodityDisplayDAO.getListByCommodity(commodity, sort, resultClass);
	}

}
