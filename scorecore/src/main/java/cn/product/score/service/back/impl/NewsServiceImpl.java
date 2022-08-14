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
import cn.product.score.entity.Admin;
import cn.product.score.entity.News;
import cn.product.score.entity.News.NewsSource;
import cn.product.score.entity.News.NewsStatus;
import cn.product.score.service.back.NewsService;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.NewsVO;
import cn.product.score.vo.back.StatusCountVO;

@Service("newsDao")
public class NewsServiceImpl implements NewsService{
	
	@Autowired
	private NewsDao newsDao;
	
	@Autowired
	private AdminDao adminDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		News news = (News)paramsMap.get("news");
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
		
		Integer totalCount = newsDao.getCountByParams(searchMap);
		List<News> list = newsDao.getListByParams(searchMap);
		List<NewsVO> voList = new LinkedList<NewsVO>();
		for(News anews : list){
			NewsVO newsVO = new NewsVO(anews);
			Admin admin = adminDao.get(anews.getCreator());
			if(admin != null){
				newsVO.setCreatorName(admin.getRealname());
			}
			voList.add(newsVO);
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
    	
		News news = newsDao.get(uuid);
		if(news == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新闻不存在");
			return;
		}
		
		NewsVO newsVO = new NewsVO(news);
		
		Admin admin = adminDao.get(news.getCreator());
		if(admin != null){
			newsVO.setCreatorName(admin.getRealname());
		}
		result.setData(newsVO);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		News news = (News)paramsMap.get("news");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		news.setUuid(UUID.randomUUID().toString());
		news.setSource(NewsSource.SELF);
		news.setSourceurl(news.getUuid());
		news.setStatus(NewsStatus.NORMAL);
		news.setCreator(admin);
		news.setCreatetime(new Timestamp(System.currentTimeMillis()));
		newsDao.insert(news);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		News news = (News)paramsMap.get("news");
		
		News anews = this.newsDao.get(news.getUuid());
		if(anews == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新闻不存在");
			return;
		}
		
		anews.setCategory(news.getCategory());
		anews.setTeam(news.getTeam());
		anews.setTitle(news.getTitle());
		anews.setContent(news.getContent());
		anews.setAuthor(news.getAuthor());
		anews.setNewstime(news.getNewstime());
		newsDao.update(anews);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		if(!NewsStatus.NORMAL.equals(status) && !NewsStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("变更状态有误");
			return;
		}
		
		String[] uuids = uuid.split(",");
		for(String id : uuids){
			News news = this.newsDao.get(id);
			if(news == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("新闻不存在");
				return;
			}
			
			if(NewsStatus.PUBLISH.equals(news.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("新闻已发布，状态不能变更");
				return;
			}
		}
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("uuid", uuids);
		searchMap.put("status", status);
		newsDao.updateStatus(searchMap);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		String[] uuids = uuid.split(",");
		for(String id : uuids){
			News news = this.newsDao.get(id);
			if(news == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("新闻不存在");
				return;
			}
			if(NewsStatus.PUBLISH.equals(news.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("新闻已发布，状态不能变更");
				return;
			}
		}
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("uuid", uuids);
		searchMap.put("status", NewsStatus.DELETE);
		newsDao.updateStatus(searchMap);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("删除成功！");
	}

	@Override
	public void statusList(InputParams params, DataResult<Object> result) {
		List<StatusCountVO> list = newsDao.getStatusList();
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setTotalResultCount(list.size());
	}
	
}
