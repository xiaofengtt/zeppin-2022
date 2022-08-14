package cn.product.score.service.back.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.FrontUserDao;
import cn.product.score.dao.NewsCommentDao;
import cn.product.score.dao.NewsPublishDao;
import cn.product.score.entity.FrontUser;
import cn.product.score.entity.NewsComment;
import cn.product.score.entity.NewsComment.NewsCommentStatus;
import cn.product.score.service.back.NewsCommentService;
import cn.product.score.entity.NewsPublish;
import cn.product.score.vo.back.NewsCommentVO;

@Service("newsCommentDao")
public class NewsCommentServiceImpl implements NewsCommentService{
	
	@Autowired
	private NewsCommentDao newsCommentDao;
	
	@Autowired
	private NewsPublishDao newsPublishDao;
	
	@Autowired
	private FrontUserDao frontUserDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String content = paramsMap.get("content") == null ? "" : paramsMap.get("content").toString();
		String newspublish = paramsMap.get("newspublish") == null ? "" : paramsMap.get("newspublish").toString();
		
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("content", content);
		searchMap.put("newspublish", newspublish);
		searchMap.put("status", NewsCommentStatus.NORMAL);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", "createtime desc");
		
		Integer totalCount = newsCommentDao.getCountByParams(searchMap);
		List<NewsComment> list = newsCommentDao.getListByParams(searchMap);
		
		List<NewsCommentVO> voList = new LinkedList<NewsCommentVO>();
		for(NewsComment nc : list){
			NewsCommentVO vo = new NewsCommentVO(nc);
			
			NewsPublish np = this.newsPublishDao.get(nc.getNewspublish());
			if(np != null){
				vo.setNewsTitle(np.getTitle());
			}
			
			FrontUser fu = this.frontUserDao.get(nc.getCreator());
			if(fu != null){
				vo.setCreatorName(fu.getRealname());
				vo.setCreatorMobile(fu.getMobile());
			}
			
			voList.add(vo);
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
    	
		NewsComment nc = newsCommentDao.get(uuid);
		if(nc == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("评论不存在");
			return;
		}
		
		result.setData(nc);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		NewsComment nc = newsCommentDao.get(uuid);
		if(nc == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("评论不存在");
			return;
		}
		
		nc.setStatus(NewsCommentStatus.DELETE);
		newsCommentDao.update(nc);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("删除成功！");
	}
	
}
