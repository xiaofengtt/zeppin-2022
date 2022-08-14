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
import cn.zeppin.product.score.entity.News.NewsSource;
import cn.zeppin.product.score.entity.News.NewsStatus;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.NewsService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.NewsVO;
import cn.zeppin.product.score.vo.back.StatusCountVO;

/**
 * 新闻管理
 */

@Controller
@RequestMapping(value = "/back/news")
public class NewsController extends BaseController{

	@Autowired
	private NewsService newsService;
	
	@Autowired
	private AdminService adminService;
	
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
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING)
	@ActionParam(key = "source", message = "来源", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result list(News news, Integer pageNum, Integer pageSize, String sort) {
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", news.getCategory());
		searchMap.put("team", news.getTeam());
		searchMap.put("title", news.getTitle());
		searchMap.put("source", news.getSource());
		if(!Utlity.checkStringNull(news.getStatus()) && !"all".equals(news.getStatus())){
			searchMap.put("status", news.getStatus());
		}
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = newsService.getCountByParams(searchMap);
		List<News> list = newsService.getListByParams(searchMap);
		List<NewsVO> voList = new LinkedList<NewsVO>();
		for(News anews : list){
			NewsVO newsVO = new NewsVO(anews);
			Admin admin = adminService.get(anews.getCreator());
			if(admin != null){
				newsVO.setCreatorName(admin.getRealname());
			}
			voList.add(newsVO);
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
		News news = newsService.get(uuid);
		if(news == null){
			return ResultManager.createFailResult("新闻不存在");
		}
		
		NewsVO newsVO = new NewsVO(news);
		
		Admin admin = adminService.get(news.getCreator());
		if(admin != null){
			newsVO.setCreatorName(admin.getRealname());
		}
		return ResultManager.createDataResult(newsVO);
	}
	
	/**
	 * 添加
	 * @param category
	 * @param team
	 * @param title
	 * @param content
	 * @param author
	 * @param newstime
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING, required = true)
	@ActionParam(key = "content", message = "内容", type = DataType.STRING, required = true)
	@ActionParam(key = "author", message = "作者", type = DataType.STRING)
	@ActionParam(key = "newstime", message = "发布时间", type = DataType.STRING)
	@ResponseBody
	public Result add(News news) {
		Admin admin = this.getCurrentOperator();
		
		news.setUuid(UUID.randomUUID().toString());
		news.setSource(NewsSource.SELF);
		news.setSourceurl(news.getUuid());
		news.setStatus(NewsStatus.NORMAL);
		news.setCreator(admin.getUuid());
		news.setCreatetime(new Timestamp(System.currentTimeMillis()));
		newsService.insert(news);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param name
	 * @param shortname
	 * @param parent
	 * @param scode
	 * @param level
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING, required = true)
	@ActionParam(key = "content", message = "内容", type = DataType.STRING, required = true)
	@ActionParam(key = "author", message = "作者", type = DataType.STRING)
	@ActionParam(key = "newstime", message = "发布时间", type = DataType.STRING)
	@ResponseBody
	public Result edit(News news) {
		News anews = this.newsService.get(news.getUuid());
		if(anews == null){
			return ResultManager.createFailResult("新闻不存在");
		}
		
		anews.setCategory(news.getCategory());
		anews.setTeam(news.getTeam());
		anews.setTitle(news.getTitle());
		anews.setContent(news.getContent());
		anews.setAuthor(news.getAuthor());
		anews.setNewstime(news.getNewstime());
		newsService.update(anews);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 变更状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status) {
		if(!NewsStatus.NORMAL.equals(status) && !NewsStatus.DISABLE.equals(status)){
			return ResultManager.createFailResult("变更状态有误");
		}
		
		String[] uuids = uuid.split(",");
		for(String id : uuids){
			News news = this.newsService.get(id);
			if(news == null){
				return ResultManager.createFailResult("新闻不存在");
			}
			
			if(NewsStatus.PUBLISH.equals(news.getStatus())){
				return ResultManager.createFailResult("新闻已发布，状态不能变更");
			}
		}
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("uuid", uuids);
		searchMap.put("status", status);
		newsService.updateStatus(searchMap);
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
		for(String id : uuids){
			News news = this.newsService.get(id);
			if(news == null){
				return ResultManager.createFailResult("新闻不存在");
			}
			if(NewsStatus.PUBLISH.equals(news.getStatus())){
				return ResultManager.createFailResult("新闻已发布，状态不能变更");
			}
		}
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("uuid", uuids);
		searchMap.put("status", NewsStatus.DELETE);
		newsService.updateStatus(searchMap);
		return ResultManager.createSuccessResult("删除成功！");
	}
	
	/**
	 * 获取分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<StatusCountVO> list = newsService.getStatusList();
		return ResultManager.createDataResult(list,list.size());
	}
}
