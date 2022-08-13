package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.bean.VideoIframe;
import com.cmos.china.mobile.media.core.bean.Videoinfo;
import com.cmos.china.mobile.media.core.service.IVideoIframeService;
import com.cmos.china.mobile.media.core.util.VideoUtlity;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public class VideoIframeServiceImpl extends BaseServiceImpl implements IVideoIframeService {

	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws Exception {
		String video = inputObject.getValue("video");
		String timepoint = inputObject.getValue("timePoint");
		
		if(timepoint==null||timepoint.equals("")){
			throw new Exception("参数有误");
		}
		if(video==null||video.equals("")){
			throw new Exception("参数有误");
		}
		
		VideoIframe iframe = new VideoIframe();
		iframe.setTimepoint(timepoint);
		iframe.setCreatetime(new Timestamp((new Date()).getTime()));
		Videoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", video, Videoinfo.class);
		if(videoinfo!=null){
			iframe.setVideo(video);
			Resource resource = this.getBaseDao().queryForObject("resource_get", videoinfo.getOriginalVideo(), Resource.class);
			if(resource!=null){
				String id = UUID.randomUUID().toString();
				iframe.setId(id);
				iframe.setPath("");
				this.getBaseDao().insert("videoIframe_add", iframe);
				Boolean iframeResult = VideoUtlity.getIframe(resource,iframe.getId(),iframe.getTimepoint());
				if(iframeResult){
					iframe.setPath(resource.getUrl().substring(0,resource.getUrl().lastIndexOf("/")+1) + iframe.getId() + ".jpg");
					this.getBaseDao().update("videoIframe_update", iframe);
					outputObject.convertBean2Map(iframe);
				}else{
					throw new Exception("视频不存在");
				}
			}else{
				throw new Exception("视频不存在");
			}
		}else{
			throw new Exception("视频不存在");
		}
	}

}
