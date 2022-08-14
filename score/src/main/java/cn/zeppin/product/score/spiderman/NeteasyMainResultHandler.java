package cn.zeppin.product.score.spiderman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import net.kernal.spiderman.kit.Counter;
import net.kernal.spiderman.kit.Properties;
import net.kernal.spiderman.worker.extract.ExtractResult;
import net.kernal.spiderman.worker.result.ResultTask;
import net.kernal.spiderman.worker.result.handler.ResultHandler;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 结果处理器
 */
public class NeteasyMainResultHandler implements ResultHandler {
	
	public void handle(ResultTask task, Counter c) {
		final String pageUrl = task.getRequest().getUrl();
		final ExtractResult result = task.getResult();
		final Properties fields = result.getFields();
		
		String type = "";
		if(pageUrl.indexOf("/gjb/") > -1){
			type = "ucl";
		}else if(pageUrl.indexOf("/yc/") > -1){
			type = "premierleague";
		}else if(pageUrl.indexOf("/dj/") > -1){
			type = "bundesliga";
		}else if(pageUrl.indexOf("/xj/") > -1){
			type = "laliga";
		}else if(pageUrl.indexOf("/yj/") > -1){
			type = "seriea";
		}else if(pageUrl.indexOf("/china/") > -1){
			type = "cft";
		}else if(pageUrl.indexOf("/zc/") > -1){
			type = "csl";
		}else{
			return;
		}
		
		List<String> urls = fields.getListString("import");
		urls.addAll(fields.getListString("import1"));
		urls.addAll(fields.getListString("detail"));
		
		Resource resource = new ClassPathResource("spiderman/neteasy/urls.txt");
		try {
			File file = resource.getFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			for(String url : urls){
				if(url.indexOf("html") > -1){
					if(url.indexOf("http://sports.163.com/") > -1 || url.indexOf("https://sports.163.com") > -1){
						bw.write(type+"@_@"+url); 
						bw.newLine();
					}
				}
			}  
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
