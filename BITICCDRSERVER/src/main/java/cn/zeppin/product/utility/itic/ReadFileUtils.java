package cn.zeppin.product.utility.itic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadFileUtils {
	private static Logger logger = LoggerFactory.getLogger(ReadFileUtils.class);
	
	//以行为单位读取文件内容
	@SuppressWarnings("finally")
	public static String readFileByLines(String fileName) throws IOException {  
		File file = new File(fileName);
		if (!file.exists()) {
			logger.info("============File not exists============");
			return "fileError";
		}
		BufferedReader reader = null;
		String context = null;
		int line = 1;
		try {  
			reader = new BufferedReader(new FileReader(file));  
			String tempString = null;  
			// 一次读入一行，直到读入null为文件结束  
			while ((tempString = reader.readLine()) != null) {  
				// 显示行号  
				System.out.println("line " + line + ": " + tempString);
				line++;
				context += tempString;
			}  
			reader.close();  
		} catch (IOException e) {  
			e.printStackTrace();  
		} finally {  
			if (reader != null) {  
				reader.close();  
			}
			return context;
		}  
	}  
}
