package cn.zeppin.project.chinamobile.media.web.dao.api;

import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.VideoPublish;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.base.IBaseDAO;

/**
 * @author Clark.R 2016年4月30日
 *
 */

public interface IVideoPublishDAO extends IBaseDAO<VideoPublish, String> {
	
	public Integer getCountByParams(VideoPublish videoinfo);
	
	public List<Entity> getListByParams(VideoPublish videoinfo, String sorts, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass);
	
	public List<Entity> getListByParams(VideoPublish videoinfo, String scode, String sort, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass);

	public List<Object[]> getStatusCount(VideoPublish videoinfo);
	
	public Integer getCountByParams(VideoPublish videoinfo,String scode);
	
	public List<VideoPublish> getListByParams(String video);
}
