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
public class TencentMainResultHandler implements ResultHandler {
	
	public void handle(ResultTask task, Counter c) {
		final String pageUrl = task.getRequest().getUrl();
		final ExtractResult result = task.getResult();
		final Properties fields = result.getFields();
		
		String type = "";
		if(pageUrl.indexOf("/championsleague/") > -1){
			type = "ucl";
		}else if(pageUrl.indexOf("/premierleague/") > -1){
			type = "premierleague";
		}else if(pageUrl.indexOf("/bundesliga/") > -1){
			type = "bundesliga";
		}else if(pageUrl.indexOf("/laliga/") > -1){
			type = "laliga";
		}else if(pageUrl.indexOf("/seriea/") > -1){
			type = "seriea";
		}else if(pageUrl.indexOf("/csocce/cft/") > -1){
			type = "cft";
		}else if(pageUrl.indexOf("/csocce/csl/") > -1){
			type = "csl";
		}else{
			return;
		}
		
		List<String> urls = fields.getListString("import");
		urls.addAll(fields.getListString("cls"));
		urls.addAll(fields.getListString("detail1"));
		urls.addAll(fields.getListString("detail2"));
		urls.addAll(fields.getListString("detail3"));
		urls.addAll(fields.getListString("detail4"));
		urls.addAll(fields.getListString("detail5"));
		
		Resource resource = new ClassPathResource("spiderman/tencent/urls.txt");
		try {
			File file = resource.getFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			for(String url : urls){
				if(url.indexOf(".htm") > -1){
					if(url.indexOf("http://sports.qq.com") > -1 || url.indexOf("https://sports.qq.com") > -1){
						bw.write(type+"@_@"+url); 
					}else{
						bw.write(type+"@_@"+"https://sports.qq.com"+url); 
					}
					bw.newLine();
				}
			}  
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
