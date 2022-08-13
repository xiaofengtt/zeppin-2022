package cn.zeppin.project.chinamobile.media.web.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.project.chinamobile.media.core.entity.VideoCommodityPoint;
import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity.GerneralStatusType;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.IVideoCommodityPointDAO;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoCommodityPointService;
import cn.zeppin.project.chinamobile.media.web.service.base.BaseService;

@Service
public class VideoCommodityPointSerivce extends BaseService implements IVideoCommodityPointService {

	@Autowired
	private IVideoCommodityPointDAO videoCommodityPointDAO;

	@Override
	public VideoCommodityPoint insert(VideoCommodityPoint videoCommodityPoint){
		videoCommodityPoint = videoCommodityPointDAO.insert(videoCommodityPoint);
		return videoCommodityPoint;
	}
	
	@Override
	public VideoCommodityPoint delete(VideoCommodityPoint videoCommodityPoint) {

		videoCommodityPoint.setStatus(GerneralStatusType.DELETED);
		videoCommodityPoint = videoCommodityPointDAO.update(videoCommodityPoint);
		return videoCommodityPoint;
	}
	
	@Override
	public VideoCommodityPoint update(VideoCommodityPoint videoCommodityPoint) {
		return videoCommodityPointDAO.update(videoCommodityPoint);
	}
	
	@Override
	public VideoCommodityPoint get(String id) {
		return this.videoCommodityPointDAO.get(id);
	}
	
	@Override
	public Integer getCountByParams(VideoCommodityPoint videoCommodityPoint){
		return this.videoCommodityPointDAO.getCountByParams(videoCommodityPoint);
	}
	
	@Override
	public List<Entity> getListForPage(VideoCommodityPoint videoCommodityPoint, String sorts,
			Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		return this.videoCommodityPointDAO.getListByParams(videoCommodityPoint, sorts, offset, length, resultClass);
	}
	
	@Override
	public List<Entity> getListByParams(VideoCommodityPoint videoCommodityPoint, @SuppressWarnings("rawtypes") Class resultClass) {
		return this.videoCommodityPointDAO.getListByParams(videoCommodityPoint, null, null, null, resultClass);
	}

	@Override
	public List<Entity> getListByParam(VideoCommodityPoint videoCommodityPoint, @SuppressWarnings("rawtypes")Class resultClass) {
		// TODO Auto-generated method stub
		return this.videoCommodityPointDAO.getListByParam(videoCommodityPoint, resultClass);
	}
}
