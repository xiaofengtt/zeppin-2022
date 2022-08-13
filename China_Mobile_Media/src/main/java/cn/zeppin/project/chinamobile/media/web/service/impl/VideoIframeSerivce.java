package cn.zeppin.project.chinamobile.media.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.project.chinamobile.media.core.entity.VideoIframe;
import cn.zeppin.project.chinamobile.media.web.dao.api.IVideoIframeDAO;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoIframeService;
import cn.zeppin.project.chinamobile.media.web.service.base.BaseService;

@Service
public class VideoIframeSerivce extends BaseService implements IVideoIframeService {

	@Autowired
	private IVideoIframeDAO videoIframeDAO;

	@Override
	public VideoIframe insert(VideoIframe videoIframe){
		videoIframe = videoIframeDAO.insert(videoIframe);
		return videoIframe;
	}
	
	@Override
	public VideoIframe update(VideoIframe videoCommodityPoint) {
		return videoIframeDAO.update(videoCommodityPoint);
	}
	
	@Override
	public VideoIframe get(String id) {
		return this.videoIframeDAO.get(id);
	}
}
