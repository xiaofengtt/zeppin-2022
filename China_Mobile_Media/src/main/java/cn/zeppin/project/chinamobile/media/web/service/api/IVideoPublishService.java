package cn.zeppin.project.chinamobile.media.web.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.project.chinamobile.media.core.entity.VideoPublish;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.service.base.IBaseService;


public interface IVideoPublishService extends IBaseService<VideoPublish, String> {

	public VideoPublish insert(VideoPublish videoinfo);
	
	public VideoPublish delete(VideoPublish videoinfo);
	
	public VideoPublish update(VideoPublish videoinfo);
	
	public VideoPublish get(String id);
	
	public Integer getCountByParams(VideoPublish videoinfo);
	
	public List<Entity> getListForPage(VideoPublish videoinfo, String sorts, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass);
	
	public List<Entity> getListByParams(VideoPublish videoinfo, String scode, String sort, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass);

	public List<Entity> getListByParams(VideoPublish videoinfo, @SuppressWarnings("rawtypes") Class resultClass);
	
	public Map<String,Integer> getStatusCount(VideoPublish videoinfo);
	
	public Integer getCountByParams(VideoPublish videoinfo,String scode);
	
	public List<VideoPublish> getListByParams(String video);
}
