package com.cmos.china.mobile.transcode.utlity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

public class BaseUtlity {
	private static Logger logger = LoggerFactory.getUtilLog(BaseUtlity.class);
	
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
        if (!flag) {
        	return false;
        }  
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
