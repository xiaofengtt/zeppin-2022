package cn.product.score.service.front.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.CategoryDao;
import cn.product.score.dao.FrontUserDao;
import cn.product.score.dao.NewsCommentDao;
import cn.product.score.dao.NewsPublishDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.Category;
import cn.product.score.entity.FrontUser;
import cn.product.score.entity.NewsComment;
import cn.product.score.entity.NewsComment.NewsCommentStatus;
import cn.product.score.entity.NewsPublish;
import cn.product.score.entity.NewsPublish.NewsPublishStatus;
import cn.product.score.entity.Resource;
import cn.product.score.entity.Team;
import cn.product.score.service.front.FrontNewsService;
import cn.product.score.util.Utlity;
import cn.product.score.vo.front.CategoryVO;
import cn.product.score.vo.front.NewsCommentVO;
import cn.product.score.vo.front.NewsPublishVO;

@Service("frontNewsService")
public class FrontNewsServiceImpl implements FrontNewsService{

	@Autowired
	private NewsPublishDao newsPublishDao;
	
	@Autowired
	private NewsCommentDao newsCommentDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String except = paramsMap.get("except") == null ? "" : paramsMap.get("except").toString();
		NewsPublish newsPublish = (NewsPublish)paramsMap.get("newsPublish");
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
		
		Integer totalCount = newsPublishDao.getCountByParams(searchMap);
		List<NewsPublish> list = newsPublishDao.getListByParams(searchMap);
		List<NewsPublishVO> voList = new LinkedList<NewsPublishVO>();
		for(NewsPublish newsPub : list){
			NewsPublishVO newsPublishVO = new NewsPublishVO(newsPub);
			newsPublishVO.setContent(null);
			if(!Utlity.checkStringNull(newsPub.getCover())){
				Resource resource = this.resourceDao.get(newsPub.getCover());
				if(resource != null){
					newsPublishVO.setCoverUrl(resource.getUrl());
				}
			}
			if(!Utlity.checkStringNull(newsPub.getCategory())){
				String[] categorys = newsPub.getCategory().split(",");
				for(String cate : categorys){
					Category category = this.categoryDao.get(cate);
					if(category != null){
						CategoryVO categoryVO = new CategoryVO(category);
						newsPublishVO.getCategoryList().add(categoryVO);
					}
				}
			}
			if(!Utlity.checkStringNull(newsPub.getTeam())){
				String[] teams = newsPub.getTeam().split(",");
				for(String te : teams){
					Team team = this.teamDao.get(te);
					if(team != null){
						newsPublishVO.getTeamList().add(team);
					}
				}
			}
			voList.add(newsPublishVO);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		NewsPublish newsPublish = newsPublishDao.get(uuid);
		if(newsPublish == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新闻不存在");
			return;
		}
		if(!NewsPublishStatus.PUBLISH.equals(newsPublish.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新闻处于未发布状态");
			return;
		}
		NewsPublishVO newsPublishVO = new NewsPublishVO(newsPublish);
		if(!Utlity.checkStringNull(newsPublish.getCategory())){
			if(!Utlity.checkStringNull(newsPublish.getCover())){
				Resource resource = this.resourceDao.get(newsPublish.getCover());
				if(resource != null){
					newsPublishVO.setCoverUrl(resource.getUrl());
				}
			}
			String[] categorys = newsPublish.getCategory().split(",");
			String[] teams = newsPublish.getTeam().split(",");
			for(String cate : categorys){
				Category category = this.categoryDao.get(cate);
				if(category != null){
					CategoryVO categoryVO = new CategoryVO(category);
					newsPublishVO.getCategoryList().add(categoryVO);
				}
			}
			for(String te : teams){
				Team team = this.teamDao.get(te);
				if(team != null){
					newsPublishVO.getTeamList().add(team);
				}
			}
		}
		
		result.setData(newsPublishVO);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void commentList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String newsPublish = paramsMap.get("newsPublish") == null ? "" : paramsMap.get("newsPublish").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("newspublish", newsPublish);
		searchMap.put("status", NewsCommentStatus.CONFIRM);
		searchMap.put("sort", "createtime desc");
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		Integer totalCount = this.newsCommentDao.getCountByParams(searchMap);
		List<NewsComment> commentList = this.newsCommentDao.getListByParams(searchMap);
		List<NewsCommentVO> voList = new ArrayList<NewsCommentVO>();
		
		for(NewsComment nc : commentList){
			NewsCommentVO ncvo = new NewsCommentVO(nc);
			if(nc.getParent() != null){
				NewsComment parent = this.newsCommentDao.get(nc.getParent());
				NewsCommentVO parentVO = new NewsCommentVO(parent);
				
				FrontUser pCreator = this.frontUserDao.get(parent.getCreator());
				if(pCreator != null){
					parentVO.setCreatorName(pCreator.getNickname());
				}
				ncvo.setParent(parentVO);
			}
			FrontUser creator = this.frontUserDao.get(nc.getCreator());
			if(creator != null){
				ncvo.setCreatorName(creator.getNickname());
			}
			voList.add(ncvo);
		}
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void comment(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		NewsComment nc = (NewsComment) paramsMap.get("nc");
		String user = paramsMap.get("user") == null ? "" : paramsMap.get("user").toString();
		
		NewsPublish newsPublish = newsPublishDao.get(nc.getNewspublish());
		if(newsPublish == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新闻不存在");
			return;
		}
		
		if(nc.getParent() != null){
			NewsComment parent = newsCommentDao.get(nc.getParent());
			if(parent == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("回复主体不存在！");
				return;
			}
		}
		
		nc.setUuid(UUID.randomUUID().toString());
		nc.setContent(nc.getContent());
		nc.setStatus(NewsCommentStatus.NORMAL);
		nc.setCreator(user);
		nc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.newsCommentDao.insert(nc);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("评论成功！");
	}

}
