/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.dao.api;


import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.Commodity;
import cn.zeppin.project.chinamobile.media.core.entity.CommodityDisplay;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.base.IBaseDAO;

/**
 * @author Clark.R 2016年3月29日
 *
 */
public interface ICommodityDisplayDAO extends IBaseDAO<CommodityDisplay, String> {
	
	List<Entity> getListByCommodity(Commodity commodity, @SuppressWarnings("rawtypes") Class resultClass);
	
	List<Entity> getListByCommodity(Commodity commodity, String sort, @SuppressWarnings("rawtypes") Class resultClass);
}
