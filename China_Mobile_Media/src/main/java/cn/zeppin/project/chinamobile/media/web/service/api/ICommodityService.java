/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.Commodity;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.service.base.IBaseService;

/**
 * @author Clark.R 2016年3月29日
 *
 */
public interface ICommodityService extends IBaseService<Commodity, String> {
	
	public Commodity insert(Commodity commodity);
	
	public Commodity delete(Commodity commodity);
	
	public Commodity update(Commodity commodity);
	
	public Commodity get(String id);
	
	public List<Commodity> getAll();
	
	public Integer getCountByParams(HashMap<String,String> searchMap);
	
	public List<Commodity> getListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length);
	
	public Integer getChildCount(String id);
	
	public List<Entity> getListByParams(Commodity commodity, String sorts, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass);
	
	public Integer getCountByParams(Commodity commodity);
	
	public Commodity insert(Commodity commodity,String[] displays, String userId);
	
	public Commodity update(Commodity commodity,String[] displays,String userId);
}
