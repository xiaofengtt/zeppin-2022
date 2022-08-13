package cn.zeppin.project.chinamobile.media.web.service.api;

import cn.zeppin.project.chinamobile.media.core.entity.VideoIframe;
import cn.zeppin.project.chinamobile.media.web.service.base.IBaseService;


public interface IVideoIframeService extends IBaseService<VideoIframe, String> {

	public VideoIframe insert(VideoIframe videoIframe);
	
	public VideoIframe update(VideoIframe videoIframe);
	
	public VideoIframe get(String id);
}
