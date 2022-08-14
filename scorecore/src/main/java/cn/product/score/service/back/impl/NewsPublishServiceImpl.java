package cn.product.score.service.back.impl;

import java.sql.Timestamp;
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
import cn.product.score.dao.AdminDao;
import cn.product.score.dao.NewsDao;
import cn.product.score.dao.NewsPublishDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.entity.Admin;
import cn.product.score.entity.News;
import cn.product.score.entity.News.NewsStatus;
import cn.product.score.entity.NewsPublish;
import cn.product.score.entity.NewsPublish.NewsPublishStatus;
import cn.product.score.service.back.NewsPublishService;
import cn.product.score.entity.Resource;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.NewsPublishVO;
import cn.product.score.vo.back.StatusCountVO;

@Service("newsPublishDao")
public class NewsPublishServiceImpl implements NewsPublishService{
	
	@Autowired
	private NewsPublishDao newsPublishDao;
	
	@Autowired
	private NewsDao newsDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		NewsPublish newsPublish = (NewsPublish)paramsMap.get("newsPublish");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
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
		searchMap.put("creator", admin);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = newsPublishDao.getCountByParams(searchMap);
		List<NewsPublish> list = newsPublishDao.getListByParams(searchMap);
		List<NewsPublishVO> voList = new LinkedList<NewsPublishVO>();
		for(NewsPublish newPub : list){
			NewsPublishVO newsPublishVO = new NewsPublishVO(newPub);
			Admin creator = adminDao.get(newPub.getCreator());
			if(creator != null){
				newsPublishVO.setCreatorName(creator.getRealname());
			}
			if(!Utlity.checkStringNull(newPub.getCover())){
				Resource resource = this.resourceDao.get(newPub.getCover());
				if(resource != null){
					newsPublishVO.setCoverUrl(resource.getUrl());
				}
			}
			if(!Utlity.checkStringNull(newPub.getChecker())){
				Admin chceker = adminDao.get(newPub.getChecker());
				if(chceker != null){
					newsPublishVO.setCheckerName(chceker.getRealname());
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
	public void checkList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		NewsPublish newsPublish = (NewsPublish)paramsMap.get("newsPublish");
		
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
		
		Integer totalCount = newsPublishDao.getCountByParams(searchMap);
		List<NewsPublish> list = newsPublishDao.getListByParams(searchMap);
		List<NewsPublishVO> voList = new LinkedList<NewsPublishVO>();
		for(NewsPublish newPub : list){
			NewsPublishVO newsPublishVO = new NewsPublishVO(newPub);
			Admin admin = adminDao.get(newPub.getCreator());
			if(admin != null){
				newsPublishVO.setCreatorName(admin.getRealname());
			}
			if(!Utlity.checkStringNull(newPub.getCover())){
				Resource resource = this.resourceDao.get(newPub.getCover());
				if(resource != null){
					newsPublishVO.setCoverUrl(resource.getUrl());
				}
			}
			if(!Utlity.checkStringNull(newPub.getChecker())){
				Admin chceker = adminDao.get(newPub.getChecker());
				if(chceker != null){
					newsPublishVO.setCheckerName(chceker.getRealname());
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
			result.setMessage("发布新闻不存在");
			return;
		}
		
		NewsPublishVO newsPublishVO = new NewsPublishVO(newsPublish);
		
		Admin admin = adminDao.get(newsPublish.getCreator());
		if(admin != null){
			newsPublishVO.setCreatorName(admin.getRealname());
		}
		if(!Utlity.checkStringNull(newsPublish.getCover())){
			Resource resource = this.resourceDao.get(newsPublish.getCover());
			if(resource != null){
				newsPublishVO.setCoverUrl(resource.getUrl());
			}
		}
		if(!Utlity.checkStringNull(newsPublish.getChecker())){
			Admin chceker = adminDao.get(newsPublish.getChecker());
			if(chceker != null){
				newsPublishVO.setCheckerName(chceker.getRealname());
			}
		}
		result.setData(newsPublishVO);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		NewsPublish newsPublish = (NewsPublish) paramsMap.get("newsPublish");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		News news = newsDao.get(newsPublish.getNews());
		if(news == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("原新闻不存在");
			return;
		}
		if(NewsStatus.PUBLISH.equals(news.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("原新闻已发布");
			return;
		}
		
		newsPublish.setUuid(UUID.randomUUID().toString());
		newsPublish.setSource(news.getSource());
		newsPublish.setSourceurl(news.getSourceurl());
		newsPublish.setImageurl(news.getImageurl());
		newsPublish.setStatus(NewsPublishStatus.UNCHECK);
		newsPublish.setCreator(admin);
		newsPublish.setCreatetime(new Timestamp(System.currentTimeMillis()));
		newsPublishDao.insertNewsPublish(newsPublish);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		NewsPublish newsPublish = (NewsPublish) paramsMap.get("newsPublish");
		
		NewsPublish newsPub = this.newsPublishDao.get(newsPublish.getUuid());
		if(newsPub == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新闻不存在");
			return;
		}
		if(NewsPublishStatus.PUBLISH.equals(newsPub.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新闻已发布");
			return;
		}
		
		newsPub.setCategory(newsPublish.getCategory());
		newsPub.setTeam(newsPublish.getTeam());
		newsPub.setTitle(newsPublish.getTitle());
		newsPub.setContent(newsPublish.getContent());
		newsPub.setCover(newsPublish.getCover());
		newsPub.setAuthor(newsPublish.getAuthor());
		newsPub.setNewstime(newsPublish.getNewstime());
		newsPublishDao.update(newsPub);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void resubmit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		NewsPublish newsPublish = this.newsPublishDao.get(uuid);
		if(newsPublish == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新闻不存在");
			return;
		}
		
		if(!NewsPublishStatus.NOPASS.equals(newsPublish.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新闻状态不正确，状态不能变更");
			return;
		}
		
		newsPublish.setStatus(NewsPublishStatus.UNCHECK);
		this.newsPublishDao.update(newsPublish);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void check(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
    	String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
    	
		if(!NewsPublishStatus.PUBLISH.equals(status) && !NewsPublishStatus.NOPASS.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("审核状态错误");
			return;
		}
		
		String[] uuids = uuid.split(",");
		for(String id : uuids){
			NewsPublish newsPublish = this.newsPublishDao.get(id);
			if(newsPublish == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("新闻不存在");
				return;
			}
			if(NewsPublishStatus.PUBLISH.equals(newsPublish.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("新闻已发布");
				return;
			}
			
			newsPublish.setStatus(status);
			newsPublish.setChecker(admin);
			newsPublish.setChecktime(new Timestamp(System.currentTimeMillis()));
			newsPublishDao.update(newsPublish);
		}
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("uuid", uuids);
		searchMap.put("status", status);
		searchMap.put("checker", admin);
		searchMap.put("checktime", new Timestamp(System.currentTimeMillis()));
		newsPublishDao.updateStatus(searchMap);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void remove(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		NewsPublish newsPublish = this.newsPublishDao.get(uuid);
		if(newsPublish == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新闻不存在");
			return;
		}
		if(!NewsPublishStatus.PUBLISH.equals(newsPublish.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新闻未发布");
			return;
		}
		newsPublish.setStatus(NewsPublishStatus.NOPASS);
		newsPublishDao.update(newsPublish);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		String[] uuids = uuid.split(",");
		String[] news = new String[uuids.length];
		for(int i=0 ; i < news.length ; i++){
			NewsPublish newsPublish = this.newsPublishDao.get(uuids[i]);
			if(newsPublish == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("新闻不存在");
				return;
			}
			if(NewsPublishStatus.PUBLISH.equals(newsPublish.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("新闻已发布");
				return;
			}
			news[i] = newsPublish.getNews();
		}
		
		newsPublishDao.deleteNewsPublish(uuids, news);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("删除成功！");
	}

	@Override
	public void checkStatusList(InputParams params, DataResult<Object> result) {
		List<StatusCountVO> list = newsPublishDao.getStatusList(new HashMap<String, Object>());
		result.setData(list);
		result.setTotalResultCount(list.size());
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void statusList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
    	
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("creator", admin);
		
		List<StatusCountVO> list = newsPublishDao.getStatusList(searchMap);
		result.setData(list);
		result.setTotalResultCount(list.size());
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
}
