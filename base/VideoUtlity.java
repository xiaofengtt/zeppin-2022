package com.cmos.china.mobile.media.core.base;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.bean.WaterMark;
import com.cmos.china.mobile.media.core.vo.VideoinfoVO;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

public class VideoUtlity
{
	private static Logger logger = LoggerFactory.getUtilLog(VideoUtlity.class);
	private static Integer imageWidth = 340;
	private static Integer workWidth = 1080;
	
	/**
     * 获取视频信息
     */
    public static String getVideoInfo(String url){
        try {
            List<String> command=new ArrayList<String>();
            command.add("ffmpeg");
            command.add("-i");
            command.add(url);
            return runCommand(command);
        } catch (Exception e) {
        	logger.error("获取视频信息错误", e);
            return null;
        }
    }
    
    /**
     * 获取视频时长
     */
    public static String getVideoLenth(String url) throws Exception{
    	String infomation = getVideoInfo(url);
	    PatternCompiler compiler =new Perl5Compiler();
		String regexDuration ="Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";  
	     
		Pattern patternDuration = compiler.compile(regexDuration,Perl5Compiler.CASE_INSENSITIVE_MASK);
		PatternMatcher matcherDuration = new Perl5Matcher();  
	     
	    if(matcherDuration.contains(infomation, patternDuration)){  
	        MatchResult re = matcherDuration.getMatch();
	        return re.group(1);
	    }else{
	    	return null;
	    }
    }
    
    /**
     * 获取视频分辨率
     */
    public static String getVideoDpi(String url) throws Exception{
    	String infomation = getVideoInfo(url);
		PatternCompiler compiler =new Perl5Compiler();
	    String regexVideo ="Video: (.*?), (.*?), (.*?)[,\\s]";  
	     
	    Pattern patternVideo = compiler.compile(regexVideo,Perl5Compiler.CASE_INSENSITIVE_MASK);  
	    PatternMatcher matcherVideo = new Perl5Matcher();  
	     
	    if(matcherVideo.contains(infomation, patternVideo)){  
	        MatchResult re = matcherVideo.getMatch();
	        return re.group(3);
	    }else{
	    	return null;
	    }
    }
    
    /**
     * 执行命令行返回处理结果
     */
    public static String runCommand(List<String> command) throws Exception{
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(command);
		builder.redirectErrorStream(true);
		Process p= builder.start();
		BufferedReader buf = null;
		String line = null;          
		buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
		 
		StringBuffer sb= new StringBuffer();
		while ((line = buf.readLine()) != null) {
		    sb.append(line);
		    continue;
		}           
		p.waitFor();
		logger.info(sb.toString());
		return sb.toString();
    }
    
    /**
     * 获取视频封面
     */
    public static Boolean makeThumbnail(Resource resource, Double timeLength, String serverPath){
    	Integer imageCount = 10;
    	String outputPath = serverPath+"/"+resource.getUrl().substring(0,resource.getUrl().lastIndexOf("/")+1) + "thumbnail.jpg";
    	Integer imageHeight = 255;
		String dpi = resource.getDpi();
		if(dpi!=null && !dpi.equals("")){
			String[] size = dpi.split("x");
			if(size.length==2){
				imageHeight = imageWidth*Integer.valueOf(size[1])/Integer.valueOf(size[0]);
			}
		}
		if(timeLength<imageCount){
			imageCount = (int)(double)timeLength;
		}
		Integer index = 0;
		Double difference = 1.0;
		for(int i=0; i<imageCount ;i++){
	    	List<String> command=new ArrayList<String>();
	        command.add("ffmpeg");
	        command.add("-ss");
	        command.add(i+"");
	        command.add("-i");
	        command.add(serverPath+"/"+resource.getUrl());
	        command.add("-y");
	        command.add("-f");
	        command.add("image2");
	        command.add("-vframes");
	        command.add("1");
	        command.add("-s");
	        command.add(imageWidth+"x"+imageHeight);
	        command.add(outputPath);
	        try {
				String output = runCommand(command);
				if(!output.contains("video:")){
					logger.error(output);
	            	return false;
	            }
				Double diff = getImageDifference(outputPath);
				if(diff<difference){
					index=i;
					difference=diff;
				}
			} catch (Exception e) {
				logger.error("获取视频封面错误", e);
				return false;
			}
		}
		List<String> command=new ArrayList<String>();
        command.add("ffmpeg");
        command.add("-ss");
        command.add(index+"");
        command.add("-i");
        command.add(serverPath+"/"+resource.getUrl());
        command.add("-y");
        command.add("-f");
        command.add("image2");
        command.add("-vframes");
        command.add("1");
        command.add("-s");
        command.add(imageWidth+"x"+imageHeight);
        command.add(outputPath);
		
        try {
			String output = runCommand(command);
			if(!output.contains("video:")){
            	return false;
            }
		} catch (Exception e) {
			logger.error("获取视频封面错误", e);
			return false;
		}
        return true;
    }
    
    /**
     * 视频截图
     */
    public static Boolean getIframe(Resource resource,String name,String timepoint,String serverPath){
    	Integer imageHeight = 255;
		String dpi = resource.getDpi();
		if(dpi!=null && !dpi.equals("")){
			String[] size = dpi.split("x");
			if(size.length==2){
				imageHeight = imageWidth*Integer.valueOf(size[1])/Integer.valueOf(size[0]);
			}
		}
		List<String> command=new ArrayList<String>();
        command.add("ffmpeg");
        command.add("-ss");
        command.add(timepoint);
        command.add("-i");
        command.add(serverPath+"/"+resource.getUrl());
        command.add("-y");
        command.add("-f");
        command.add("image2");
        command.add("-vframes");
        command.add("1");
        command.add("-s");
        command.add(imageWidth+"x"+imageHeight);
        command.add(serverPath+"/"+resource.getUrl().substring(0,resource.getUrl().lastIndexOf("/")+1) + name + ".jpg");
        try {
			String output = runCommand(command);
			if(!output.contains("video:")){
            	return false;
            }
		} catch (Exception e) {
			logger.error("视频截图错误", e);
			return false;
		}
        return true;
    }
    
    /**
     * 删除路径
     */
    public static boolean deleteDirectory(String sPath) {
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        Boolean flag = true;  
        File[] files = dirFile.listFiles();
        if(files!=null && files.length>0){
	        for (int i = 0; i < files.length; i++) {
	            if (files[i].isFile()) {  
	                flag = deleteFile(files[i].getAbsolutePath());  
	                if (!flag) break;  
	            }
	            else {  
	                flag = deleteDirectory(files[i].getAbsolutePath());  
	                if (!flag) break;  
	            }  
	        }  
        }
        if (!flag) return false;  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    }
    
    /**
     * 删除文件
     */
    public static boolean deleteFile(String sPath) {  
        Boolean flag = false;  
        File file = new File(sPath);  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            flag = true;  
        }  
        return flag;  
    } 
    
    /**
     * 获取图片色值差
     */
    public static Double getImageDifference(String path){
    	File file = new File(path);
    	try {
			BufferedImage image = ImageIO.read(file);
			int width = image.getWidth();  
		    int height = image.getHeight(); 
		    BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		    Integer whiteCount = 0;
		    Integer totalCount = 0;
		    for(int i= 0 ; i < width ; i++){
		        for(int j = 0 ; j < height; j++){  
			        int rgb = image.getRGB(i, j);  
			        grayImage.setRGB(i, j, rgb);
			        totalCount++;
			        if(grayImage.getRGB(i, j) == -1){
			        	whiteCount++;
			        }
		        }
		    }
		    Double rate = Double.valueOf(whiteCount.toString()) /Double.valueOf(totalCount.toString());
		    rate = rate>0.5 ? rate-0.5 : 0.5-rate;
		    return rate;
		} catch (IOException e) {
			logger.error("获取图片色值差错误", e);
			return 1.0;
		}
    }
    
    public static Boolean processVideo(VideoinfoVO videoVO, String serverPath, WaterMark waterMark){
		Boolean framesResult = framesVideo(videoVO, serverPath);
		if(!framesResult){
			return false;
		}
		Boolean transcodeResult = transcodeVideo(videoVO, serverPath, waterMark);
		if(!transcodeResult){
			return false;
		}
		return true;
    }
	
	public static Boolean framesVideo(VideoinfoVO videoVO, String serverPath){
		String basePath = serverPath + "/" + videoVO.getOriginalVideoUrl().substring(0, videoVO.getOriginalVideoUrl().lastIndexOf("/"));
		File tfFile = new File(basePath + "/frames/");
		if (!tfFile.exists()) {
			tfFile.mkdir();
		}else{
			deleteDirectory(basePath + "/frames/");
			tfFile.mkdir();
		}
		Integer imageHeight = 255;
		String dpi = videoVO.getOriginalVideoDpi();
		if(dpi!=null && !dpi.equals("")){
			String[] size = dpi.split("x");
			if(size.length==2){
				imageHeight = imageWidth*Integer.valueOf(size[1])/Integer.valueOf(size[0]);
			}
		}
        try {
            List<String> command=new ArrayList<String>();
            command.add("ffmpeg");
            command.add("-i");
            command.add(serverPath + "/" + videoVO.getOriginalVideoUrl());
            command.add("-y");
            command.add("-r");
            command.add("0.2");
            command.add("-f");
            command.add("image2");
            command.add("-s");
            command.add(imageWidth+"x"+imageHeight);
            command.add(basePath + "/frames/%1d.jpg");
            String output = runCommand(command);
            if(!output.contains("video:")){
            	return false;
            }
        } catch (Exception e) {
            logger.error("抽帧出错", e);
            return false;
        }
        
        Integer workHeight = 720;
		if(dpi!=null && !dpi.equals("")){
			String[] size = dpi.split("x");
			if(size.length==2){
				workHeight = workWidth*Integer.valueOf(size[1])/Integer.valueOf(size[0]);
			}
		}
        
        try {
            List<String> command=new ArrayList<String>();
            command.add("ffmpeg");
            command.add("-i");
            command.add(serverPath + "/" + videoVO.getOriginalVideoUrl());
            command.add("-y");
            command.add("-r");
            command.add("1");
            command.add("-f");
            command.add("image2");
            command.add("-s");
            command.add(workWidth+"x"+workHeight);
            command.add(basePath + "/works/%1d.jpg");
            String output = runCommand(command);
            if(!output.contains("video:")){
            	return false;
            }
        } catch (Exception e) {
            logger.error("抽帧出错", e);
            return false;
        }
        
        return true;
	}
	
	public static Boolean transcodeVideo(VideoinfoVO videoVO, String serverPath, WaterMark waterMark){
		String basePath = serverPath + "/" + videoVO.getOriginalVideoUrl().substring(0, videoVO.getOriginalVideoUrl().lastIndexOf("/"));
		File tfFile = new File(basePath + "/transcode/");
		if (!tfFile.exists()) {
			tfFile.mkdir();
		}else{
			deleteDirectory(basePath + "/transcode/");
			tfFile.mkdir();
		}
		Boolean highResult = videoTranscode(videoVO,"high", serverPath, waterMark);
		if(!highResult){
			return false;
		}
		Boolean middleResult = videoTranscode(videoVO,"middle", serverPath, waterMark);
		if(!middleResult){
			return false;
		}
		Boolean lowResult = videoTranscode(videoVO,"low", serverPath, waterMark);
		if(!lowResult){
			return false;
		}
        return true;
	}
    
    
    public static Boolean videoTranscode(VideoinfoVO videoVO, String type, String serverPath,WaterMark waterMark){
		String basePath = serverPath + "/" + videoVO.getOriginalVideoUrl().substring(0, videoVO.getOriginalVideoUrl().lastIndexOf("/"));
    	String dpi = videoVO.getOriginalVideoDpi();
    	Map<String,Integer> params = getVideoParams(type);
		Integer videoHeight = params.get("hight");
		if(dpi!=null && !dpi.equals("")){
			String[] size = dpi.split("x");
			if(size.length==2){
				videoHeight = params.get("width")*Integer.valueOf(size[1])/Integer.valueOf(size[0]);
				if(videoHeight%2==1){
					videoHeight += 1;
				}
			}
		}
        try {
            List<String> command=new ArrayList<String>();
            command.add("ffmpeg");
            command.add("-i");
            command.add(serverPath + "/" + videoVO.getOriginalVideoUrl());
            if(waterMark != null){
            	command.add("-i");
            	command.add(serverPath + "/" + waterMark.getPath());
            }
            command.add("-y");
            command.add("-vcodec");
            command.add("libx264");
            if(waterMark != null){
            	command.add("-filter_complex");
            	String waterMarkString = "overlay=";
            	if("right".equals(waterMark.getPositionX())){
            		waterMarkString += "main_w-overlay_w-";
            	}
            	waterMarkString += waterMark.getPaddingX();
            	waterMarkString += ":";
            	if("down".equals(waterMark.getPositionY())){
            		waterMarkString += "main_h-overlay_h-";
            	}
            	waterMarkString += waterMark.getPaddingY();
            	command.add(waterMarkString);
            }
            command.add("-s");
            command.add(params.get("width")+"x"+videoHeight);
            command.add("-b");
            command.add(params.get("rate") + "k");
            command.add(basePath + "/transcode/video_" + params.get("name") + "K_bak.mp4");
            String output = runCommand(command);
            if(!output.contains("Qavg:")){
            	return false;
            }else{
            	List<String> qtcommand=new ArrayList<String>();
            	qtcommand.add("qt-faststart");
            	qtcommand.add(basePath + "/transcode/video_" + params.get("name") + "K_bak.mp4");
            	qtcommand.add(basePath + "/transcode/video_" + params.get("name") + "K.mp4");
            	String qtoutput = runCommand(qtcommand);
            	if(!qtoutput.contains("copying rest of file")){
                	return false;
                }else{
                	deleteFile(basePath + "/transcode/video_" + params.get("name") + "K_bak.mp4");
                	return true;
            	}
            }
        } catch (Exception e) {
        	logger.error("转码出错", e);
            return false;
        }
    }
    
    public static Map<String,Integer> getVideoParams(String type){
    	Map<String,Integer> params = new HashMap<String,Integer>();
    	if(type.equals("high")){
    		params.put("width", 1280);
    		params.put("hight", 720);
    		params.put("rate", 1400);
    		params.put("name", 1000);
    	}else if(type.equals("middle")){
    		params.put("width", 640);
    		params.put("hight", 360);
    		params.put("rate", 900);
    		params.put("name", 500);
    	}else{
    		params.put("width", 640);
    		params.put("hight", 360);
    		params.put("rate", 400);
    		params.put("name", 250);
    	}
    	return params;
    }
}
