package cn.zeppin.project.chinamobile.media.web.dao.api;

import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.VideoCommodityPoint;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.base.IBaseDAO;


public interface IVideoCommodityPointDAO extends IBaseDAO<VideoCommodityPoint, String> {
	
	public Integer getCountByParams(VideoCommodityPoint videoCommodityPoint);
	
	public List<Entity> getListByParams(VideoCommodityPoint videoCommodityPoint, String sorts, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass);
	
	public List<Entity> getListByParam(VideoCommodityPoint videoCommodityPoint, @SuppressWarnings("rawtypes") Class resultClass);
}
