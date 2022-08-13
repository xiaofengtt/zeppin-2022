package cn.zeppin.project.chinamobile.media.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import cn.zeppin.project.chinamobile.media.core.entity.Resource;
import cn.zeppin.project.chinamobile.media.web.vo.VideoinfoVO;

public class VideoUtlity
{
	private static Integer imageWidth = 340;
	
	public static Boolean processVideo(VideoinfoVO videoVO){
		Boolean framesResult = framesVideo(videoVO);
		if(!framesResult){
			return false;
		}
		Boolean transcodeResult = transcodeVideo(videoVO);
		if(!transcodeResult){
			return false;
		}
		return true;
    }
	
	public static Boolean framesVideo(VideoinfoVO videoVO){
		String basePath = videoVO.getOriginalVideoPath().substring(0, videoVO.getOriginalVideoPath().lastIndexOf("/"));
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
            command.add(videoVO.getOriginalVideoPath());
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
            e.printStackTrace();
            return false;
        }
        return true;
	}
	
	public static Boolean transcodeVideo(VideoinfoVO videoVO){
		String basePath = videoVO.getOriginalVideoPath().substring(0, videoVO.getOriginalVideoPath().lastIndexOf("/"));
		File tfFile = new File(basePath + "/transcode/");
		if (!tfFile.exists()) {
			tfFile.mkdir();
		}else{
			deleteDirectory(basePath + "/transcode/");
			tfFile.mkdir();
		}
		Boolean highResult = videoTranscode(videoVO,"high");
		if(!highResult){
			return false;
		}
		Boolean middleResult = videoTranscode(videoVO,"middle");
		if(!middleResult){
			return false;
		}
		Boolean lowResult = videoTranscode(videoVO,"low");
		if(!lowResult){
			return false;
		}
        return true;
	}
	
    public static String getVideoInfo(String url){
        try {
            List<String> command=new ArrayList<String>();
            command.add("ffmpeg");
            command.add("-i");
            command.add(url);
            return runCommand(command);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
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
         return sb.toString();
    }
    
    public static Boolean videoTranscode(VideoinfoVO videoVO , String type){
    	String basePath = videoVO.getOriginalVideoPath().substring(0, videoVO.getOriginalVideoPath().lastIndexOf("/"));
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
            command.add(videoVO.getOriginalVideoPath());
            command.add("-y");
            command.add("-vcodec");
            command.add("libx264");
            command.add("-s");
            command.add(params.get("width")+"x"+videoHeight);
            command.add("-b");
            command.add(params.get("rate") + "k");
            command.add(basePath + "/transcode/video_" + params.get("name") + "K.mp4");
            System.out.println(command);
            String output = runCommand(command);
            System.out.println(output);
            if(!output.contains("Qavg:")){
            	return false;
            }else{
            	return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    
    public static Boolean makeThumbnail(Resource resource, String timeLength){
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
        command.add("00:00:02");
        command.add("-i");
        command.add(resource.getPath());
        command.add("-y");
        command.add("-f");
        command.add("image2");
        command.add("-vframes");
        command.add("1");
        command.add("-s");
        command.add(imageWidth+"x"+imageHeight);
        command.add(resource.getPath().substring(0,resource.getPath().lastIndexOf("/")+1) + "thumbnail.jpg");
        try {
			String output = runCommand(command);
			if(!output.contains("video:")){
            	return false;
            }
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
        return true;
    }
    
    public static Boolean getIframe(Resource resource,String name,String timepoint){
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
        command.add(resource.getPath());
        command.add("-y");
        command.add("-f");
        command.add("image2");
        command.add("-vframes");
        command.add("1");
        command.add("-s");
        command.add(imageWidth+"x"+imageHeight);
        command.add(resource.getPath().substring(0,resource.getPath().lastIndexOf("/")+1) + name + ".jpg");
        try {
			String output = runCommand(command);
			if(!output.contains("video:")){
            	return false;
            }
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
        return true;
    }
    
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
        if (!flag) return false;  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    }
        
    public static boolean deleteFile(String sPath) {  
        Boolean flag = false;  
        File file = new File(sPath);  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            flag = true;  
        }  
        return flag;  
    } 
}
