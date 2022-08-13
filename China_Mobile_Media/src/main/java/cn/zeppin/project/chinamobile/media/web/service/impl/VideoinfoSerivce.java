package cn.zeppin.project.chinamobile.media.web.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.project.chinamobile.media.core.entity.Resource;
import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo;
import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo.VideoStatusType;
import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity.GerneralStatusType;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.IResourceDAO;
import cn.zeppin.project.chinamobile.media.web.dao.api.IVideoinfoDAO;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoinfoService;
import cn.zeppin.project.chinamobile.media.web.service.base.BaseService;

@Service
public class VideoinfoSerivce extends BaseService implements IVideoinfoService {

	@Autowired
	private IVideoinfoDAO videoinfoDAO;
	
	@Autowired
	private IResourceDAO resourceDAO;

	@Override
	public Videoinfo insert(Videoinfo videoinfo){
		videoinfo = videoinfoDAO.insert(videoinfo);
		return videoinfo;
	}
	
	@Override
	public Videoinfo delete(Videoinfo videoinfo) {

		videoinfo.setStatus(VideoStatusType.DELETED);
		videoinfo = videoinfoDAO.update(videoinfo);
		if(videoinfo.getVideo()!=null){
			Resource video = resourceDAO.get(videoinfo.getVideo());
			if(video!=null){
				video.setStatus(GerneralStatusType.DELETED);
				resourceDAO.update(video);
			}
		}
		if(videoinfo.getThumbnail()!=null){
			Resource thumbnail  = resourceDAO.get(videoinfo.getThumbnail());
			if(thumbnail!=null){
				thumbnail.setStatus(GerneralStatusType.DELETED);
				resourceDAO.update(thumbnail);
			}
		}
		if(videoinfo.getOriginalVideo()!=null){
			Resource originalVideo = resourceDAO.get(videoinfo.getOriginalVideo());
			if(originalVideo!=null){
				originalVideo.setStatus(GerneralStatusType.DELETED);
				resourceDAO.update(originalVideo);
			}
		}
		return videoinfo;
	}
	
	@Override
	public Videoinfo update(Videoinfo videoinfo) {
		return videoinfoDAO.update(videoinfo);
	}
	
	@Override
	public Videoinfo get(String id) {
		return this.videoinfoDAO.get(id);
	}
	
	@Override
	public Integer getCountByParams(Videoinfo videoinfo){
		return this.videoinfoDAO.getCountByParams(videoinfo);
	}
	
	@Override
	public List<Entity> getListForPage(Videoinfo videoinfo, String sorts,
			Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		return this.videoinfoDAO.getListByParams(videoinfo, sorts, offset, length, resultClass);
	}
	
	@Override
	public List<Entity> getListByParams(Videoinfo videoinfo, @SuppressWarnings("rawtypes") Class resultClass) {
		return this.videoinfoDAO.getListByParams(videoinfo, null, null, null, resultClass);
	}
	
	@Override
	public Map<String,Integer> getStatusCount(){
		List<Object[]> objList = this.videoinfoDAO.getStatusCount();
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		for(Object[] obj : objList){
			countMap.put(obj[0].toString(), Integer.valueOf(obj[1].toString()));
		}
		return countMap;
	}
}
