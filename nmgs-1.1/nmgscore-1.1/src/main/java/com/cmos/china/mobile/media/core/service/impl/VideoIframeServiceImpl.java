package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import com.cmos.china.mobile.media.core.base.VideoUtlity;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.bean.VideoIframe;
import com.cmos.china.mobile.media.core.bean.Videoinfo;
import com.cmos.china.mobile.media.core.service.IVideoIframeService;
import com.cmos.china.mobile.media.core.util.DataTimeConvert;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public class VideoIframeServiceImpl extends BaseServiceImpl implements IVideoIframeService {
 
	/**
	 * 添加视频截图
	 */
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String video = inputObject.getValue("video");
		String timepoint = inputObject.getValue("timePoint");
		
		if(timepoint==null||"".equals(timepoint)){
			throw new ExceptionUtil("参数有误");
		}
		if(video==null||"".equals(video)){
			throw new ExceptionUtil("参数有误");
		}
		
		VideoIframe iframe = new VideoIframe();
		timepoint = DataTimeConvert.getFormatTime(timepoint);
		iframe.setTimepoint(timepoint);
		iframe.setCreatetime(new Timestamp((new Date()).getTime()));
		Videoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", video, Videoinfo.class);
		if(videoinfo!=null){
			iframe.setVideo(video);
			Resource resource = this.getBaseDao().queryForObject("resource_get", videoinfo.getOriginalVideo(), Resource.class);
			if(resource!=null){
				String serverPath = Utlity.basePath;
//				String beanPath = Resource.class.getResource("").getPath();
//				String serverPath = beanPath.substring(0,beanPath.indexOf("WEB-INF"));
				
				String id = UUID.randomUUID().toString();
				iframe.setId(id);
				iframe.setPath("");
				this.getBaseDao().insert("videoIframe_add", iframe);
				Boolean iframeResult = VideoUtlity.getIframe(resource,iframe.getId(),iframe.getTimepoint(),serverPath);
				if(iframeResult){
					iframe.setPath(resource.getUrl().substring(0,resource.getUrl().lastIndexOf("/")+1) + iframe.getId() + ".jpg");
					this.getBaseDao().update("videoIframe_update", iframe);
					outputObject.convertBean2Map(iframe);
				}else{
					throw new ExceptionUtil("视频不存在");
				}
			}else{
				throw new ExceptionUtil("视频不存在");
			}
		}else{
			throw new ExceptionUtil("视频不存在");
		}
	}

}
