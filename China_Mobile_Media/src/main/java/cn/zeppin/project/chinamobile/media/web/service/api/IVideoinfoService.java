package cn.zeppin.project.chinamobile.media.web.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.service.base.IBaseService;


public interface IVideoinfoService extends IBaseService<Videoinfo, String> {

	public Videoinfo insert(Videoinfo videoinfo);
	
	public Videoinfo delete(Videoinfo videoinfo);
	
	public Videoinfo update(Videoinfo videoinfo);
	
	public Videoinfo get(String id);
	
	public Integer getCountByParams(Videoinfo videoinfo);
	
	public List<Entity> getListForPage(Videoinfo videoinfo, String sorts, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass);

	public List<Entity> getListByParams(Videoinfo videoinfo, @SuppressWarnings("rawtypes") Class resultClass);
	
	public Map<String,Integer> getStatusCount();
}
