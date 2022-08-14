package com.cmos.chinamobile.media.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;



import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

public class VideoUtlity
{
	private static Logger logger = LoggerFactory.getUtilLog(VideoUtlity.class);
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
    	if(VideoResultUtlity.getResult()){
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
    	}else{
    		return "";
    	}
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
    


}
