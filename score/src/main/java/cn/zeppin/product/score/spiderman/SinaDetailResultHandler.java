package cn.zeppin.product.score.spiderman;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import net.kernal.spiderman.kit.Counter;
import net.kernal.spiderman.kit.Properties;
import net.kernal.spiderman.worker.extract.ExtractResult;
import net.kernal.spiderman.worker.result.ResultTask;
import net.kernal.spiderman.worker.result.handler.ResultHandler;
import cn.zeppin.product.score.entity.Admin.AdminUuid;
import cn.zeppin.product.score.entity.News;
import cn.zeppin.product.score.entity.News.NewsSource;
import cn.zeppin.product.score.entity.News.NewsStatus;
import cn.zeppin.product.score.service.NewsService;
import cn.zeppin.product.score.util.SpringUtil;

/**
 * 结果处理器
 */
public class SinaDetailResultHandler implements ResultHandler {
	
	private static NewsService newsService;
    static {
    	newsService = SpringUtil.getBean(NewsService.class);
    }
    
	public void handle(ResultTask task, Counter c) {
		final String pageUrl = task.getRequest().getUrl();
		final ExtractResult result = task.getResult();
		final Properties fields = result.getFields();
		
		String type = task.getSeed().getName();
				
		String title = fields.getString("title");
		List<String> contentList = fields.getListString("content");
		String content = "";
		for(String line : contentList){
			content = content + line + "<br>";
		}
		
		List<String> imageurlList = fields.getListString("imageurl");
		String imageurl = "";
		for(String line : imageurlList){
			if(!line.trim().startsWith("http:") && !line.trim().startsWith("https:")){
				line = "http:" + line.trim();
			}
			imageurl = imageurl + line + ",";
		}
		if(imageurl.length() > 0){
			imageurl = imageurl.substring(0, imageurl.length() - 1);
		}
		
		String author = fields.getString("author") == null ? "" : fields.getString("author");
		String newstime = fields.getString("newstime");
		
		News news = new News();
		news.setUuid(UUID.randomUUID().toString());
		news.setCategory(ResultHandlerUtlity.getCategory(type));
		news.setTitle(title);
		news.setContent(content);
		news.setAuthor(author);
		news.setNewstime(newstime);
		news.setSource(NewsSource.SINA);
		news.setSourceurl(pageUrl);
		news.setImageurl(imageurl);
		news.setStatus(NewsStatus.NORMAL);
		news.setCreator(AdminUuid.SPIDER);
		news.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		try{
			newsService.insert(news);
		}catch(Exception e){
			return;
		}
	} 
}
