package com.cmos.china.mobile.media.core.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cmos.china.mobile.media.core.base.VideoUtlity;
import com.cmos.china.mobile.media.core.bean.Videoinfo;
import com.cmos.china.mobile.media.core.bean.Videoinfo.VideoStatusType;
import com.cmos.china.mobile.media.core.dao.IBaseDao;
import com.cmos.china.mobile.media.core.service.impl.BaseServiceImpl;
import com.cmos.china.mobile.media.core.vo.VideoinfoVO;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

@Component
public class VideoTranscoder extends BaseServiceImpl {
	
	private static Logger logger = LoggerFactory.getServiceLog(VideoTranscoder.class);
	private static Integer maxProgress;
	private ExecutorService fixedThreadPool;
	private static Integer host;
	private static Integer totalHost;
	@Autowired
	IBaseDao baseDao ;
	
	VideoTranscoder(){
		Properties props = new Properties(); 
        InputStream inputStream = null; 
        try { 
            inputStream = getClass().getResourceAsStream("/config/system.properties"); 
            props.load(inputStream); 
            maxProgress = Integer.valueOf((String) props.get("zeppinTranscoderMax")); 
            totalHost = Integer.valueOf((String) props.get("zeppinTranscoderMax")); 
            host = Integer.valueOf((String) props.get("zeppinTranscoderHost")) - 1; 
            fixedThreadPool = Executors.newFixedThreadPool(maxProgress);
        } catch (IOException ex) { 
            logger.error("读取数据错误",ex);
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
		boolean marke = false;
		System.out.println("开始扫描");
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("status", VideoStatusType.UPLOADED);
		List<VideoinfoVO> list = this.baseDao.queryForList("videoinfo_getListByParams", paramMap, VideoinfoVO.class);
		if(list.size() > 0){
			System.out.println("有待转码："+list.size());
			VideoinfoVO videoVO = null;
			for(int i= 0 ; i < list.size() ; i++){
				videoVO = (VideoinfoVO) list.get(i);
				if(videoVO.getTcId() % totalHost == host){
					marke = true ;
					System.out.println("获取转码:"+videoVO.getTcId());
					break;
					}
			}
			if(marke){
				Videoinfo video = this.baseDao.queryForObject("videoinfo_get", videoVO.getId(), Videoinfo.class);
				if(video!=null){
//					String serverPath = Utlity.basePath;
					String beanPath = Videoinfo.class.getResource("").getPath();
					String serverPath = beanPath.substring(0,beanPath.indexOf("WEB-INF"));
					video.setTranscodingFlag(false);
					video.setStatus(VideoStatusType.TRANSCODING);
					this.baseDao.update("videoinfo_update", video);
					Boolean result = VideoUtlity.processVideo(videoVO, serverPath);
					if(result){
						video.setStatus(VideoStatusType.UNCHECKED);
					}else{
						video.setStatus(VideoStatusType.FAILED);
					}
					this.baseDao.update("videoinfo_update", video);
				}
			}
		}
	}
}
