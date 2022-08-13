package cn.zeppin.project.chinamobile.media.web.service.api;

import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.VideoCommodityPoint;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.service.base.IBaseService;


public interface IVideoCommodityPointService extends IBaseService<VideoCommodityPoint, String> {

	public VideoCommodityPoint insert(VideoCommodityPoint videoCommodityPoint);
	
	public VideoCommodityPoint delete(VideoCommodityPoint videoCommodityPoint);
	
	public VideoCommodityPoint update(VideoCommodityPoint videoCommodityPoint);
	
	public VideoCommodityPoint get(String id);
	
	public Integer getCountByParams(VideoCommodityPoint videoCommodityPoint);
	
	public List<Entity> getListForPage(VideoCommodityPoint videoCommodityPoint, String sorts, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass);

	public List<Entity> getListByParams(VideoCommodityPoint videoCommodityPoint, @SuppressWarnings("rawtypes") Class resultClass);
	
	public List<Entity> getListByParam(VideoCommodityPoint videoCommodityPoint, @SuppressWarnings("rawtypes") Class resultClass);
}
