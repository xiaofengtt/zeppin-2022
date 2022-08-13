package cn.zeppin.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.zeppin.entity.Resource;

public class ImageProcessing {
	public static HashMap<String,List<String>> imageCut(Resource resource ,String serverPath){
		HashMap<String,List<String>> resultMap = new HashMap<String,List<String>>();
		List<String> statusList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();
		resultMap.put("status", statusList);
		resultMap.put("result", resultList);
		
		String directoryPath = resource.getUrl().substring(0, resource.getUrl().lastIndexOf("/"));
		List<String> command=new ArrayList<String>();
        command.add("/usr/local/linux-matlab/image_output.output");
        command.add(serverPath + "/" + resource.getUrl());
        command.add(serverPath + "/" + directoryPath);
		
		ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        builder.redirectErrorStream(true);
        Process p;
		try {
			p = builder.start();
			BufferedReader buf = null;
	        String line = null;          
	        buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        
	        StringBuffer sb= new StringBuffer();
	        while ((line = buf.readLine()) != null) {
	            sb.append(line);
	            if(line.contains("_segment_")){
	            	resultList.add(line.trim());
	            }
	            continue;
	        }           
	        p.waitFor();
	        if(!sb.toString().contains("[success]")){
	        	statusList.add("failed");
	        	resultList = new ArrayList<String>();
	        	resultList.add(sb.toString());
	        	return resultMap;
	        }
		} catch (Exception e) {
			e.printStackTrace();
			statusList.add("failed");
			resultList = new ArrayList<String>();
        	resultList.add("操作异常");
			return resultMap;
		}
		statusList.add("success");
		return resultMap;
   }
	
	public static HashMap<String,List<String>> imageBuild(Resource resource, Integer size, String serverPath){
		HashMap<String,List<String>> resultMap = new HashMap<String,List<String>>();
		List<String> statusList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();
		resultMap.put("status", statusList);
		resultMap.put("result", resultList);
		String directoryPath = "build/" + UUID.randomUUID().toString() +"/";
		File tfFile = new File(serverPath + "/" + directoryPath);
		if (!tfFile.exists()) {
			tfFile.mkdir();
		}
		List<String> command=new ArrayList<String>();
        command.add("/usr/local/linux-matlab/SymGeneration");
        command.add(serverPath + "/" + resource.getUrl());
        command.add(size+"");
        command.add(serverPath + "/" + directoryPath);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        builder.redirectErrorStream(true);
        Process p;
        try {
			p = builder.start();
			BufferedReader buf = null;
	        String line = null;
	        buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        
	        StringBuffer sb= new StringBuffer();
	        while ((line = buf.readLine()) != null) {
	            sb.append(line);
	            if(line.contains("_out")){
	            	resultList.add(directoryPath + line.trim() + ".jpg");
	            }
	            continue;
	        }           
	        p.waitFor();
	        if(!sb.toString().contains("[success]")){
	        	statusList.add("failed");
	        	resultList = new ArrayList<String>();
	        	resultList.add(sb.toString());
	        	return resultMap;
	        }
		} catch (Exception e) {
			e.printStackTrace();
			statusList.add("failed");
			resultList = new ArrayList<String>();
        	resultList.add("操作异常");
			return resultMap;
		}
        statusList.add("success");
		return resultMap;
	}
}
