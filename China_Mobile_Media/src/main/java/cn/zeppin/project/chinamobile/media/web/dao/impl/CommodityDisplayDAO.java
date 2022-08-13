/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zeppin.project.chinamobile.media.core.entity.Commodity;
import cn.zeppin.project.chinamobile.media.core.entity.CommodityDisplay;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.ICommodityDisplayDAO;
import cn.zeppin.project.chinamobile.media.web.dao.base.BaseDAO;

/**
 * @author Clark.R 2016年3月29日
 *
 */
@Repository
public class CommodityDisplayDAO extends BaseDAO<CommodityDisplay, String> implements ICommodityDisplayDAO {

	@Override
	public List<Entity> getListByCommodity(Commodity commodity, @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select cd.id from commodity_display360 cd where 1=1 ");
		if(commodity.getId() != null && !"".equals(commodity.getId())){
			sb.append(" and cd.commodity='"+commodity.getId()+"'");
		}
		
		List<Entity> results = this.getBySQL(sb.toString(), -1, -1, null, resultClass);
		return results;
	}

	@Override
	public List<Entity> getListByCommodity(Commodity commodity, String sort, @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		
		StringBuilder sb = new StringBuilder();
		sb.append("select cd.display_index as displayIndex,re.url as displayImageURL from commodity_display360 cd,resource re,commodity com where 1=1 ");
		sb.append(" and cd.display_image=re.id and cd.commodity=com.id ");
		sb.append(" and com.status='normal'");
		if(commodity.getId() != null && !"".equals(commodity.getId())){
			sb.append(" and cd.commodity='"+commodity.getId()+"'");
		}
		
		if(sort != null && !"".equals(sort)){
			sb.append(" order by cd." +sort +" asc");
		}
		List<Entity> results = this.getBySQL(sb.toString(), -1, -1, null, resultClass);
		return results;
	}


}
