package cn.product.worldmall.service.back.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.NoticeTopicDao;
import cn.product.worldmall.entity.NoticeTopic;
import cn.product.worldmall.service.back.NoticeTopicService;

@Service("noticeTopicService")
public class NoticeTopicServiceImpl implements NoticeTopicService{
	
	@Autowired
	private NoticeTopicDao noticeTopicDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String displayName = paramsMap.get("displayName") == null ? "" : paramsMap.get("displayName").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("displayName", displayName);
		searchMap.put("status", status);
		searchMap.put("sort", sorts);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的主题的总数
		Integer totalResultCount = noticeTopicDao.getCountByParams(searchMap);
		//查询符合条件的主题列表
		List<NoticeTopic> list = noticeTopicDao.getListByParams(searchMap);
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
		return;
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取主题
		NoticeTopic np = noticeTopicDao.get(uuid);
		if (np == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		
		result.setData(np);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		return;
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
	}
	
}
