package cn.zeppin.task;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.entity.Resource;
import cn.zeppin.service.api.IResourceService;
import cn.zeppin.utility.DataTimeConvert;
import cn.zeppin.utility.ImageProcessing;

@Component("taskJob")
public class TaskJobService{

	private static Integer maxProgress = 3;
	private static Integer progress = 0;
	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
	
	private IResourceService resourceService;

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	@Scheduled(cron="0/10 * *  * * ? ")
	public void calculate1() {
		fixedThreadPool.execute(new Runnable() {
			public void run() {
				imageCut();
			}
		});
	}
	
	public void imageCut() {
		if(progress<maxProgress){
			HashMap<String,String> searchMap = new HashMap<String,String>();
			searchMap.put("status", "4");
			List<Resource> resList = this.resourceService.getListByParams(searchMap);
			if(resList.size()>0){
				Resource resource = this.resourceService.getResource(resList.get(0).getId());
				if(resource!=null){
					String serverPath = this.getClass().getResource("/").getPath();
					serverPath = serverPath.substring(0,serverPath.indexOf("WEB-INF"));
					resource.setStatus(5);
					this.resourceService.updateResource(resource);
					progress++;
					HashMap<String, List<String>> result = ImageProcessing.imageCut(resource ,serverPath);
					progress--;
					if(result.get("status").get(0).equals("success")){
						resource.setStatus(1);
						this.resourceService.updateResource(resource);
						
						String directoryPath = resource.getUrl().substring(0, resource.getUrl().lastIndexOf("/"));
						for(String name : result.get("result")){
							HashMap<String,String> childSearchMap = new HashMap<String,String>();
							childSearchMap.put("url", directoryPath + name);
							Integer childCount = this.getResourceService().getCountByParams(childSearchMap);
							if(childCount > 0){
								continue;
							}
							Resource childRes = new Resource();
							childRes.setCategory(resource.getCategory());
							childRes.setCreatetime(DataTimeConvert.getCurrentTime(""));
							childRes.setEminent(0);
							childRes.setIsObject(resource.getIsObject());
							childRes.setLevel(resource.getLevel() + 1);
							childRes.setNational(resource.getNational());
							childRes.setOwner(resource.getOwner());
							childRes.setParent(resource);
							childRes.setSource(resource.getSource());
							childRes.setSourcePath(resource.getSourcePath());
							childRes.setSourceTime(resource.getSourceTime());
							childRes.setStatus(1);
							childRes.setTitle(resource.getTitle()+ "-基因素材");
							childRes.setType(".jpg");
							childRes.setUrl(directoryPath + name);
							try {
								File file2 = new File(serverPath + "/" + directoryPath + name);
								BufferedImage src = ImageIO.read(file2);
								childRes.setRatio(src.getWidth(null) + "x" + src.getHeight(null));
								childRes.setSize(file2.length());
								this.resourceService.addResource(childRes);
							}catch (Exception e) {
								e.printStackTrace();
								continue;
							}
						}
					}else{
						resource.setStatus(1);
						this.resourceService.updateResource(resource);
					}
				}
			}
		}
	}
}
