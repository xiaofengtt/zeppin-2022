/**
 * 
 */
package cn.zeppin.product.score.controller.back;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Admin;
import cn.zeppin.product.score.entity.News;
import cn.zeppin.product.score.entity.News.NewsStatus;
import cn.zeppin.product.score.entity.NewsPublish;
import cn.zeppin.product.score.entity.NewsPublish.NewsPublishStatus;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.NewsPublishService;
import cn.zeppin.product.score.service.NewsService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.NewsPublishVO;
import cn.zeppin.product.score.vo.back.StatusCountVO;

/**
 * 新闻发布管理
 */

@Controller
@RequestMapping(value = "/back/newsPublish")
public class NewsPublishController extends BaseController{

	@Autowired
	private NewsPublishService newsPublishService;
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 根据条件查询列表
	 * @param category
	 * @param team
	 * @param title
	 * @param source
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "news", message = "新闻", type = DataType.STRING)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING)
	@ActionParam(key = "source", message = "来源", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result list(NewsPublish newsPublish, Integer pageNum, Integer pageSize, String sort) {
		Admin admin = this.getCurrentOperator();
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("news", newsPublish.getNews());
		searchMap.put("category", newsPublish.getCategory());
		searchMap.put("team", newsPublish.getTeam());
		searchMap.put("title", newsPublish.getTitle());
		searchMap.put("source", newsPublish.getSource());
//		searchMap.put("status", newsPublish.getStatus());
		if(!Utlity.checkStringNull(newsPublish.getStatus()) && !"all".equals(newsPublish.getStatus())){
			searchMap.put("status", newsPublish.getStatus());
		}
		searchMap.put("creator", admin.getUuid());
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = newsPublishService.getCountByParams(searchMap);
		List<NewsPublish> list = newsPublishService.getListByParams(searchMap);
		List<NewsPublishVO> voList = new LinkedList<NewsPublishVO>();
		for(NewsPublish newPub : list){
			NewsPublishVO newsPublishVO = new NewsPublishVO(newPub);
			Admin creator = adminService.get(newPub.getCreator());
			if(creator != null){
				newsPublishVO.setCreatorName(creator.getRealname());
			}
			if(!Utlity.checkStringNull(newPub.getCover())){
				Resource resource = this.resourceService.get(newPub.getCover());
				if(resource != null){
					newsPublishVO.setCoverUrl(resource.getUrl());
				}
			}
			if(!Utlity.checkStringNull(newPub.getChecker())){
				Admin chceker = adminService.get(newPub.getChecker());
				if(chceker != null){
					newsPublishVO.setCheckerName(chceker.getRealname());
				}
			}
			voList.add(newsPublishVO);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	/**
	 * 根据条件查询列表
	 * @param category
	 * @param team
	 * @param title
	 * @param source
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/checkList", method = RequestMethod.GET)
	@ActionParam(key = "news", message = "新闻", type = DataType.STRING)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING)
	@ActionParam(key = "source", message = "来源", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result checkList(NewsPublish newsPublish, Integer pageNum, Integer pageSize, String sort) {
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("news", newsPublish.getNews());
		searchMap.put("category", newsPublish.getCategory());
		searchMap.put("team", newsPublish.getTeam());
		searchMap.put("title", newsPublish.getTitle());
		searchMap.put("source", newsPublish.getSource());
		if(!Utlity.checkStringNull(newsPublish.getStatus()) && !"all".equals(newsPublish.getStatus())){
			searchMap.put("status", newsPublish.getStatus());
		}
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = newsPublishService.getCountByParams(searchMap);
		List<NewsPublish> list = newsPublishService.getListByParams(searchMap);
		List<NewsPublishVO> voList = new LinkedList<NewsPublishVO>();
		for(NewsPublish newPub : list){
			NewsPublishVO newsPublishVO = new NewsPublishVO(newPub);
			Admin admin = adminService.get(newPub.getCreator());
			if(admin != null){
				newsPublishVO.setCreatorName(admin.getRealname());
			}
			if(!Utlity.checkStringNull(newPub.getCover())){
				Resource resource = this.resourceService.get(newPub.getCover());
				if(resource != null){
					newsPublishVO.setCoverUrl(resource.getUrl());
				}
			}
			if(!Utlity.checkStringNull(newPub.getChecker())){
				Admin chceker = adminService.get(newPub.getChecker());
				if(chceker != null){
					newsPublishVO.setCheckerName(chceker.getRealname());
				}
			}
			voList.add(newsPublishVO);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	/**
	 * 获取信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		NewsPublish newsPublish = newsPublishService.get(uuid);
		if(newsPublish == null){
			return ResultManager.createFailResult("发布新闻不存在");
		}
		
		NewsPublishVO newsPublishVO = new NewsPublishVO(newsPublish);
		
		Admin admin = adminService.get(newsPublish.getCreator());
		if(admin != null){
			newsPublishVO.setCreatorName(admin.getRealname());
		}
		if(!Utlity.checkStringNull(newsPublish.getCover())){
			Resource resource = this.resourceService.get(newsPublish.getCover());
			if(resource != null){
				newsPublishVO.setCoverUrl(resource.getUrl());
			}
		}
		if(!Utlity.checkStringNull(newsPublish.getChecker())){
			Admin chceker = adminService.get(newsPublish.getChecker());
			if(chceker != null){
				newsPublishVO.setCheckerName(chceker.getRealname());
			}
		}
		return ResultManager.createDataResult(newsPublishVO);
	}
	
	/**
	 * 添加
	 * @param news
	 * @param category
	 * @param team
	 * @param title
	 * @param content
	 * @param cover
	 * @param author
	 * @param newstime
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "news", message = "新闻", type = DataType.STRING)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING, required = true)
	@ActionParam(key = "content", message = "内容", type = DataType.STRING, required = true)
	@ActionParam(key = "cover", message = "封面图", type = DataType.STRING)
	@ActionParam(key = "author", message = "作者", type = DataType.STRING)
	@ActionParam(key = "newstime", message = "发布时间", type = DataType.STRING)
	@ResponseBody
	public Result add(NewsPublish newsPublish) {
		Admin admin = this.getCurrentOperator();
		
		News news = newsService.get(newsPublish.getNews());
		if(news == null){
			return ResultManager.createFailResult("原新闻不存在");
		}
		if(NewsStatus.PUBLISH.equals(news.getStatus())){
			return ResultManager.createFailResult("原新闻已发布");
		}
		
		newsPublish.setUuid(UUID.randomUUID().toString());
		newsPublish.setSource(news.getSource());
		newsPublish.setSourceurl(news.getSourceurl());
		newsPublish.setImageurl(news.getImageurl());
		newsPublish.setStatus(NewsPublishStatus.UNCHECK);
		newsPublish.setCreator(admin.getUuid());
		newsPublish.setCreatetime(new Timestamp(System.currentTimeMillis()));
		newsPublishService.insertNewsPublish(newsPublish);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param category
	 * @param team
	 * @param title
	 * @param content
	 * @param cover
	 * @param author
	 * @param newstime
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING, required = true)
	@ActionParam(key = "content", message = "内容", type = DataType.STRING, required = true)
	@ActionParam(key = "cover", message = "封面图", type = DataType.STRING)
	@ActionParam(key = "author", message = "作者", type = DataType.STRING)
	@ActionParam(key = "newstime", message = "发布时间", type = DataType.STRING)
	@ResponseBody
	public Result edit(NewsPublish newsPublish) {
		NewsPublish newsPub = this.newsPublishService.get(newsPublish.getUuid());
		if(newsPub == null){
			return ResultManager.createFailResult("新闻不存在");
		}
		if(NewsPublishStatus.PUBLISH.equals(newsPub.getStatus())){
			return ResultManager.createFailResult("新闻已发布");
		}
		
		newsPub.setCategory(newsPublish.getCategory());
		newsPub.setTeam(newsPublish.getTeam());
		newsPub.setTitle(newsPublish.getTitle());
		newsPub.setContent(newsPublish.getContent());
		newsPub.setCover(newsPublish.getCover());
		newsPub.setAuthor(newsPublish.getAuthor());
		newsPub.setNewstime(newsPublish.getNewstime());
		newsPublishService.update(newsPub);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 重新提交审核
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/resubmit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result resubmit(String uuid) {
		NewsPublish newsPublish = this.newsPublishService.get(uuid);
		if(newsPublish == null){
			return ResultManager.createFailResult("新闻不存在");
		}
		
		if(!NewsPublishStatus.NOPASS.equals(newsPublish.getStatus())){
			return ResultManager.createFailResult("新闻状态不正确，状态不能变更");
		}
		
		newsPublish.setStatus(NewsPublishStatus.UNCHECK);
		this.newsPublishService.update(newsPublish);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 审核发布
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message = "审核状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result check(String uuid, String status) {
		Admin admin = this.getCurrentOperator();
		
		if(!NewsPublishStatus.PUBLISH.equals(status) && !NewsPublishStatus.NOPASS.equals(status)){
			return ResultManager.createFailResult("审核状态错误");
		}
		
		String[] uuids = uuid.split(",");
		for(String id : uuids){
			NewsPublish newsPublish = this.newsPublishService.get(id);
			if(newsPublish == null){
				return ResultManager.createFailResult("新闻不存在");
			}
			if(NewsPublishStatus.PUBLISH.equals(newsPublish.getStatus())){
				return ResultManager.createFailResult("新闻已发布");
			}
			
			newsPublish.setStatus(status);
			newsPublish.setChecker(admin.getUuid());
			newsPublish.setChecktime(new Timestamp(System.currentTimeMillis()));
			newsPublishService.update(newsPublish);
		}
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("uuid", uuids);
		searchMap.put("status", status);
		searchMap.put("checker", admin.getUuid());
		searchMap.put("checktime", new Timestamp(System.currentTimeMillis()));
		newsPublishService.updateStatus(searchMap);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 下架
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result remove(String uuid) {
		NewsPublish newsPublish = this.newsPublishService.get(uuid);
		if(newsPublish == null){
			return ResultManager.createFailResult("新闻不存在");
		}
		if(!NewsPublishStatus.PUBLISH.equals(newsPublish.getStatus())){
			return ResultManager.createFailResult("新闻未发布");
		}
		newsPublish.setStatus(NewsPublishStatus.NOPASS);
		newsPublishService.update(newsPublish);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		String[] uuids = uuid.split(",");
		String[] news = new String[uuids.length];
		for(int i=0 ; i < news.length ; i++){
			NewsPublish newsPublish = this.newsPublishService.get(uuids[i]);
			if(newsPublish == null){
				return ResultManager.createFailResult("新闻不存在");
			}
			if(NewsPublishStatus.PUBLISH.equals(newsPublish.getStatus())){
				return ResultManager.createFailResult("新闻已发布");
			}
			news[i] = newsPublish.getNews();
		}
		
		newsPublishService.deleteNewsPublish(uuids, news);
		return ResultManager.createSuccessResult("删除成功！");
	}
	
	/**
	 * 审核者分状态列表
	 * @return
	 */
	@RequestMapping(value = "/checkStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result checkStatusList() {
		List<StatusCountVO> list = newsPublishService.getStatusList(new HashMap<String, Object>());
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		Admin admin = this.getCurrentOperator();
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("creator", admin.getUuid());
		
		List<StatusCountVO> list = newsPublishService.getStatusList(searchMap);
		return ResultManager.createDataResult(list,list.size());
	}
}
