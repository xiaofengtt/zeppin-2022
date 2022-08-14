package com.cmos.china.mobile.transcode.utlity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

public class FFmpegUtlity
{
	private static Logger logger = LoggerFactory.getUtilLog(FFmpegUtlity.class);
	private static Integer imageWidth = 340;
	
	/**
     * 获取视频信息
     */
    public static String getVideoInfo(String url){
        try {
            List<String> command=new ArrayList<String>();
            command.add("ffmpeg");
            command.add("-i");
            command.add(url);
            return BaseUtlity.runCommand(command);
        } catch (Exception e) {
        	logger.error("获取视频信息错误", e);
            return null;
        }
    }
    
    /**
     * 视频截图
     */
    public static Boolean getIframe(String inputPath,String inputName,String timepoint,String outputPath){
    	Integer imageHeight = 255;
		List<String> command=new ArrayList<String>();
        command.add("ffmpeg");
        command.add("-ss");
        command.add(timepoint);
        command.add("-i");
        command.add(inputPath);
        command.add("-y");
        command.add("-f");
        command.add("image2");
        command.add("-vframes");
        command.add("1");
        command.add("-s");
        command.add(imageWidth+"x"+imageHeight);
        command.add(outputPath + "/" + inputName + ".jpg");
        try {
			String output = BaseUtlity.runCommand(command);
			if(!output.contains("video:")){
            	return false;
            }
		} catch (Exception e) {
			logger.error("视频截图错误", e);
			return false;
		}
        return true;
    } 
	
    //视频抽帧
	public static Boolean framesVideo(String inputPath, String outputPath){
		File tfFile = new File(outputPath + "/frames/");
		if (!tfFile.exists()) {
			tfFile.mkdir();
		}else{
			BaseUtlity.deleteDirectory(outputPath + "/frames/");
			tfFile.mkdir();
		}
		Integer imageHeight = 255;
        try {
            List<String> command=new ArrayList<String>();
            command.add("ffmpeg");
            command.add("-i");
            command.add(inputPath);
            command.add("-y");
            command.add("-r");
            command.add("0.2");
            command.add("-f");
            command.add("image2");
            command.add("-s");
            command.add(imageWidth+"x"+imageHeight);
            command.add(outputPath + "/frames/%1d.jpg");
            String output = BaseUtlity.runCommand(command);
            if(!output.contains("video:")){
            	return false;
            }
        } catch (Exception e) {
            logger.error("抽帧出错", e);
            return false;
        }
        return true;
	}
}
