package cn.zeppin.project.chinamobile.media.web.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.project.chinamobile.media.core.entity.VideoPublish;
import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo.VideoStatusType;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.IResourceDAO;
import cn.zeppin.project.chinamobile.media.web.dao.api.IVideoPublishDAO;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoPublishService;
import cn.zeppin.project.chinamobile.media.web.service.base.BaseService;

@Service
public class VideoPublishSerivce extends BaseService implements IVideoPublishService {

	@Autowired
	private IVideoPublishDAO videoPublishDAO;
	
	@Autowired
	private IResourceDAO resourceDAO;

	@Override
	public VideoPublish insert(VideoPublish videoinfo){
		videoinfo = videoPublishDAO.insert(videoinfo);
		return videoinfo;
	}
	
	@Override
	public VideoPublish delete(VideoPublish videoinfo) {

		videoinfo.setStatus(VideoStatusType.DELETED);
		videoinfo = videoPublishDAO.update(videoinfo);
//		if(videoinfo.getVideo()!=null){
//			Resource video = resourceDAO.get(videoinfo.getVideo());
//			if(video!=null){
//				video.setStatus(GerneralStatusType.DELETED);
//				resourceDAO.update(video);
//			}
//		}
//		if(videoinfo.getThumbnail()!=null){
//			Resource thumbnail  = resourceDAO.get(videoinfo.getThumbnail());
//			if(thumbnail!=null){
//				thumbnail.setStatus(GerneralStatusType.DELETED);
//				resourceDAO.update(thumbnail);
//			}
//		}
//		if(videoinfo.getOriginalVideo()!=null){
//			Resource originalVideo = resourceDAO.get(videoinfo.getOriginalVideo());
//			if(originalVideo!=null){
//				originalVideo.setStatus(GerneralStatusType.DELETED);
//				resourceDAO.update(originalVideo);
//			}
//		}
		return videoinfo;
	}
	
	@Override
	public VideoPublish update(VideoPublish videoinfo) {
		return videoPublishDAO.update(videoinfo);
	}
	
	@Override
	public VideoPublish get(String id) {
		return this.videoPublishDAO.get(id);
	}
	
	@Override
	public Integer getCountByParams(VideoPublish videoinfo){
		return this.videoPublishDAO.getCountByParams(videoinfo);
	}
	
	@Override
	public List<Entity> getListForPage(VideoPublish videoinfo, String sorts,
			Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		return this.videoPublishDAO.getListByParams(videoinfo, sorts, offset, length, resultClass);
	}
	
	@Override
	public List<Entity> getListByParams(VideoPublish videoinfo, @SuppressWarnings("rawtypes") Class resultClass) {
		return this.videoPublishDAO.getListByParams(videoinfo, null, null, null, resultClass);
	}
	
	@Override
	public Map<String,Integer> getStatusCount(VideoPublish videoinfo){
		List<Object[]> objList = this.videoPublishDAO.getStatusCount(videoinfo);
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		for(Object[] obj : objList){
			countMap.put(obj[0].toString(), Integer.valueOf(obj[1].toString()));
		}
		return countMap;
	}

	@Override
	public List<Entity> getListByParams(VideoPublish videoinfo, String scode,
			String sort, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		return this.videoPublishDAO.getListByParams(videoinfo, scode, sort, offset, length, resultClass);
	}

	@Override
	public Integer getCountByParams(VideoPublish videoinfo, String scode) {
		// TODO Auto-generated method stub
		return this.videoPublishDAO.getCountByParams(videoinfo, scode);
	}

	@Override
	public List<VideoPublish> getListByParams(String video) {
		// TODO Auto-generated method stub
		return this.videoPublishDAO.getListByParams(video);
	}
}
