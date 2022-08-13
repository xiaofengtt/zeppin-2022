package cn.zeppin.project.chinamobile.media.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo;
import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo.VideoStatusType;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.utility.VideoUtlity;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoinfoService;
import cn.zeppin.project.chinamobile.media.web.vo.VideoinfoVO;

@Component
public class VideoTranscoder {
	
	private static Integer maxProgress;
	private static Integer progress = 0;
	private ExecutorService fixedThreadPool;
	@Autowired
	private IVideoinfoService videoinfoService;
	
	VideoTranscoder(){
		Properties props = new Properties(); 
        InputStream inputStream = null; 
        try { 
            inputStream = getClass().getResourceAsStream("/jdbc.properties"); 
            props.load(inputStream); 
            maxProgress = Integer.valueOf((String) props.get("zeppinTranscoderMax")); 
            fixedThreadPool = Executors.newFixedThreadPool(maxProgress);
        } catch (IOException ex) { 
            ex.printStackTrace(); 
        } 
	}
	
	@Scheduled(cron="0/10 * *  * * ? ")
	public void calculate1() {
		fixedThreadPool.execute(new Runnable() {
			public void run() {
				calculate();
			}
		});
	}
	
	public void calculate() {
		if(progress<maxProgress){
			Videoinfo searchVideoinfo = new Videoinfo();
			searchVideoinfo.setStatus(VideoStatusType.UPLOADED);
			List<Entity> list = this.videoinfoService.getListByParams(searchVideoinfo, VideoinfoVO.class);
			if(list.size() > 0){
				VideoinfoVO videoVO = (VideoinfoVO) list.get(0);
				Videoinfo video = this.videoinfoService.get(videoVO.getId());
				if(video!=null){
					video.setTranscodingFlag(false);
					video.setStatus(VideoStatusType.TRANSCODING);
					this.videoinfoService.update(video);
					progress++;
					Boolean result = VideoUtlity.processVideo(videoVO);
					progress--;
					if(result){
						video.setStatus(VideoStatusType.UNCHECKED);
					}else{
						video.setStatus(VideoStatusType.FAILED);
					}
					this.videoinfoService.update(video);
				}
			}
		}
	}
}
