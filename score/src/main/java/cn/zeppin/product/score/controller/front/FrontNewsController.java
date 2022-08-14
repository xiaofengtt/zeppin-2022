/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
import cn.zeppin.product.score.entity.Category;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.NewsComment;
import cn.zeppin.product.score.entity.NewsComment.NewsCommentStatus;
import cn.zeppin.product.score.entity.NewsPublish;
import cn.zeppin.product.score.entity.NewsPublish.NewsPublishStatus;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.service.NewsCommentService;
import cn.zeppin.product.score.service.NewsPublishService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.front.CategoryVO;
import cn.zeppin.product.score.vo.front.NewsCommentVO;
import cn.zeppin.product.score.vo.front.NewsPublishVO;

/**
 * 前端新闻接口
 */

@Controller
@RequestMapping(value = "/front/news")
public class FrontNewsController extends BaseController{

	@Autowired
	private NewsPublishService newsPublishService;
	
	@Autowired
	private NewsCommentService newsCommentService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private FrontUserService frontUserService;
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 新闻列表
	 * @param category
	 * @param team
	 * @param title
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING)
	@ActionParam(key = "except", message = "排除", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result list(NewsPublish newsPublish, String except, Integer pageNum, Integer pageSize, String sort) {
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", newsPublish.getCategory());
		searchMap.put("team", newsPublish.getTeam());
		searchMap.put("title", newsPublish.getTitle());
		searchMap.put("except", except);
		searchMap.put("status", NewsPublishStatus.PUBLISH);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = newsPublishService.getCountByParams(searchMap);
		List<NewsPublish> list = newsPublishService.getListByParams(searchMap);
		List<NewsPublishVO> voList = new LinkedList<NewsPublishVO>();
		for(NewsPublish newsPub : list){
			NewsPublishVO newsPublishVO = new NewsPublishVO(newsPub);
			newsPublishVO.setContent(null);
			if(!Utlity.checkStringNull(newsPub.getCover())){
				Resource resource = this.resourceService.get(newsPub.getCover());
				if(resource != null){
					newsPublishVO.setCoverUrl(resource.getUrl());
				}
			}
			if(!Utlity.checkStringNull(newsPub.getCategory())){
				String[] categorys = newsPub.getCategory().split(",");
				for(String cate : categorys){
					Category category = this.categoryService.get(cate);
					if(category != null){
						CategoryVO categoryVO = new CategoryVO(category);
						newsPublishVO.getCategoryList().add(categoryVO);
					}
				}
			}
			if(!Utlity.checkStringNull(newsPub.getTeam())){
				String[] teams = newsPub.getTeam().split(",");
				for(String te : teams){
					Team team = this.teamService.get(te);
					if(team != null){
						newsPublishVO.getTeamList().add(team);
					}
				}
			}
			voList.add(newsPublishVO);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	/**
	 * 新闻详细信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		NewsPublish newsPublish = newsPublishService.get(uuid);
		if(newsPublish == null){
			return ResultManager.createFailResult("新闻不存在");
		}
		if(!NewsPublishStatus.PUBLISH.equals(newsPublish.getStatus())){
			return ResultManager.createFailResult("新闻处于未发布状态");
		}
		NewsPublishVO newsPublishVO = new NewsPublishVO(newsPublish);
		if(!Utlity.checkStringNull(newsPublish.getCategory())){
			if(!Utlity.checkStringNull(newsPublish.getCover())){
				Resource resource = this.resourceService.get(newsPublish.getCover());
				if(resource != null){
					newsPublishVO.setCoverUrl(resource.getUrl());
				}
			}
			String[] categorys = newsPublish.getCategory().split(",");
			String[] teams = newsPublish.getTeam().split(",");
			for(String cate : categorys){
				Category category = this.categoryService.get(cate);
				if(category != null){
					CategoryVO categoryVO = new CategoryVO(category);
					newsPublishVO.getCategoryList().add(categoryVO);
				}
			}
			for(String te : teams){
				Team team = this.teamService.get(te);
				if(team != null){
					newsPublishVO.getTeamList().add(team);
				}
			}
		}
		
		return ResultManager.createDataResult(newsPublishVO);
	}
	
	/**
	 * 评论列表
	 * @param newsPublish
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/commentList", method = RequestMethod.GET)
	@ActionParam(key = "newsPublish", message = "新闻", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ResponseBody
	public Result commentList(String newsPublish, Integer pageNum, Integer pageSize) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("newspublish", newsPublish);
		searchMap.put("status", NewsCommentStatus.CONFIRM);
		searchMap.put("sort", "createtime desc");
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		Integer totalCount = this.newsCommentService.getCountByParams(searchMap);
		List<NewsComment> commentList = this.newsCommentService.getListByParams(searchMap);
		List<NewsCommentVO> voList = new ArrayList<NewsCommentVO>();
		
		for(NewsComment nc : commentList){
			NewsCommentVO ncvo = new NewsCommentVO(nc);
			if(nc.getParent() != null){
				NewsComment parent = this.newsCommentService.get(nc.getParent());
				NewsCommentVO parentVO = new NewsCommentVO(parent);
				
				FrontUser pCreator = this.frontUserService.get(parent.getCreator());
				if(pCreator != null){
					parentVO.setCreatorName(pCreator.getNickname());
				}
				ncvo.setParent(parentVO);
			}
			FrontUser creator = this.frontUserService.get(nc.getCreator());
			if(creator != null){
				ncvo.setCreatorName(creator.getNickname());
			}
			voList.add(ncvo);
		}
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	/**
	 * 添加评论
	 * @param newspublish
	 * @param parent
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ActionParam(key = "newspublish", message = "新闻", type = DataType.STRING, required = true)
	@ActionParam(key = "parent", message = "回复主体", type = DataType.STRING)
	@ActionParam(key = "content", message = "评论内容", type = DataType.STRING, required = true)
	@ResponseBody
	public Result comment(NewsComment nc, HttpServletRequest request) {
		FrontUser user = getFrontUser(request);
		
		NewsPublish newsPublish = newsPublishService.get(nc.getNewspublish());
		if(newsPublish == null){
			return ResultManager.createFailResult("新闻不存在！");
		}
		
		if(nc.getParent() != null){
			NewsComment parent = newsCommentService.get(nc.getParent());
			if(parent == null){
				return ResultManager.createFailResult("回复主体不存在！");
			}
		}
		
		nc.setUuid(UUID.randomUUID().toString());
		nc.setContent(nc.getContent());
		nc.setStatus(NewsCommentStatus.NORMAL);
		nc.setCreator(user.getUuid());
		nc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.newsCommentService.insert(nc);
		return ResultManager.createDataResult("评论成功！");
	}
}
