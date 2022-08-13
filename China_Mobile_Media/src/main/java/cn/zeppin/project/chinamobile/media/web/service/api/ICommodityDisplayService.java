/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.service.api;

import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.Commodity;
import cn.zeppin.project.chinamobile.media.core.entity.CommodityDisplay;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.service.base.IBaseService;

/**
 * @author Clark.R 2016年3月29日
 *
 */
public interface ICommodityDisplayService extends IBaseService<CommodityDisplay, String> {
	
	public CommodityDisplay insert(CommodityDisplay commodity);
	
	public CommodityDisplay delete(CommodityDisplay commodity);
	
	public CommodityDisplay update(CommodityDisplay commodity);
	
	public CommodityDisplay get(String id);
	
	public List<CommodityDisplay> getAll();
	
	public List<Entity> getListByCommodity(Commodity commodity, @SuppressWarnings("rawtypes") Class resultClass);
	
	List<Entity> getListByCommodity(Commodity commodity, String sort, @SuppressWarnings("rawtypes") Class resultClass);
}
