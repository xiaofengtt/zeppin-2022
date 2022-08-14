package com.cmos.china.mobile.transcode.utlity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

public class TranscodeUtlity {
	private static Logger logger = LoggerFactory.getUtilLog(TranscodeUtlity.class);
	
	public static Boolean videoTranscode(String inputPath , String outputPath, String outputName, String type){
    	Map<String,Integer> params = getVideoParams(type);
        try {
            List<String> command=new ArrayList<String>();
            command.add("ffmpeg");
            command.add("-i");
            command.add(inputPath);
            command.add("-y");
            command.add("-vcodec");
            command.add("libx264");
            command.add("-s");
            command.add(params.get("width")+"x"+params.get("hight"));
            command.add("-b");
            command.add(params.get("rate") + "k");
            command.add(outputPath + outputName+ "_" + type + "_bak.mp4");
            String output = BaseUtlity.runCommand(command);
            if(!output.contains("Qavg:")){
            	return false;
            }else{
            	List<String> qtcommand=new ArrayList<String>();
            	qtcommand.add("qt-faststart");
            	qtcommand.add(outputPath + outputName+ "_" + type + "_bak.mp4");
            	qtcommand.add(outputPath + outputName+ "_" + type + ".mp4");
            	String qtoutput = BaseUtlity.runCommand(qtcommand);
            	if(!qtoutput.contains("copying rest of file")){
                	return false;
                }else{
                	BaseUtlity.deleteFile(outputPath + outputName+ "_" + type + "_bak.mp4");
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
    	if(type.equals("super")){
    		params.put("width", 1920);
    		params.put("hight", 1080);
    		params.put("rate", 2900);
    	}else if(type.equals("high")){
    		params.put("width", 1280);
    		params.put("hight", 720);
    		params.put("rate", 1400);
    	}else if(type.equals("standard")){
    		params.put("width", 960);
    		params.put("hight", 540);
    		params.put("rate", 800);
    	}else if(type.equals("fluency")){
    		params.put("width", 640);
    		params.put("hight", 360);
    		params.put("rate", 300);
    	}
    	return params;
    }
	
	public static Boolean videoCombine(String inputBase, List<String> inputPathList , String outputPath, String outputName){
		String inputTxtPath = inputBase + "/combine" + UUID.randomUUID().toString() + ".txt";
		BaseUtlity.deleteFile(inputTxtPath);
		FileWriter fw = null;
		
		File txtFile = new File(inputTxtPath);
		try {
			fw = new FileWriter(txtFile, true);
		} catch (Exception e) {
			logger.error("文件合并出错", e);
		}
		PrintWriter pw = new PrintWriter(fw);
		for(String inputPath: inputPathList){
			pw.println("file '" + inputPath + "'");
			pw.flush();
		}
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			logger.error("文件合并出错", e);
		}
		try {
            List<String> command=new ArrayList<String>();
            command.add("ffmpeg");
            command.add("-f");
            command.add("concat");
            command.add("-safe");
            command.add("0");
            command.add("-i");
            command.add(inputTxtPath);
            command.add("-y");
            command.add("-c");
            command.add("copy");
            command.add(outputPath + outputName + "_bak.mp4");
            String output = BaseUtlity.runCommand(command);
            if(!output.contains("global headers:")){
            	return false;
            }else{
            	List<String> qtcommand=new ArrayList<String>();
            	qtcommand.add("qt-faststart");
            	qtcommand.add(outputPath + outputName + "_bak.mp4");
            	qtcommand.add(outputPath + outputName + ".mp4");
            	String qtoutput = BaseUtlity.runCommand(qtcommand);
            	if(!qtoutput.contains("copying rest of file")){
                	return false;
                }else{
                	BaseUtlity.deleteFile(outputPath + outputName + "_bak.mp4");
                	BaseUtlity.deleteFile(inputTxtPath);
                	return true;
            	}
            }
        } catch (Exception e) {
        	logger.error("文件合并出错", e);
            return false;
        }
	}
}
