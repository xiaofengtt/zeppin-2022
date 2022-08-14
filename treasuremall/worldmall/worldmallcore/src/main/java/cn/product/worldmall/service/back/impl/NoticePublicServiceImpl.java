package cn.product.worldmall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.AdminDao;
import cn.product.worldmall.dao.NoticePublicDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.Admin;
import cn.product.worldmall.entity.NoticePublic;
import cn.product.worldmall.entity.NoticePublic.NoticePublicStatus;
import cn.product.worldmall.service.back.NoticePublicService;
import cn.product.worldmall.vo.back.NoticePublicVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("noticePublicService")
public class NoticePublicServiceImpl implements NoticePublicService{
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private NoticePublicDao noticePublicDao;
	
	@Autowired
	private AdminDao adminDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String title = paramsMap.get("title") == null ? "" : paramsMap.get("title").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("title", title);
		searchMap.put("status", status);
		searchMap.put("sort", sorts);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的公告信息的总数
		Integer totalResultCount = noticePublicDao.getCountByParams(searchMap);
		//查询符合条件的公告信息列表
		List<NoticePublic> list = noticePublicDao.getListByParams(searchMap);
		List<NoticePublicVO> listvo = new ArrayList<NoticePublicVO>();
		if(list != null && list.size() > 0) {
			for(NoticePublic np : list) {
				NoticePublicVO npvo = new NoticePublicVO(np);
				
				Admin ad = this.adminDao.get(np.getCreator());
				if(ad != null) {
					npvo.setCreatorCN(ad.getRealname());
				}
				
				listvo.add(npvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取公告信息
		NoticePublic np = noticePublicDao.get(uuid);
		if (np == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		
		result.setData(np);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		NoticePublic noticePublic = paramsMap.get("noticePublic") == null ? null : (NoticePublic)paramsMap.get("noticePublic");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		//创建公告信息
		NoticePublic np = new NoticePublic();
		np.setUuid(UUID.randomUUID().toString());
		np.setType(noticePublic.getType());
		np.setTitle(noticePublic.getTitle());
		np.setDetails(noticePublic.getDetails());
		np.setStatus(noticePublic.getStatus());
		np.setCreatetime(new Timestamp(System.currentTimeMillis()));
		np.setCreator(admin);
		np.setOnlinetime(noticePublic.getOnlinetime());
		//是否要加发送消息至用户？
		noticePublicDao.insert(np);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		NoticePublic noticePublic = paramsMap.get("noticePublic") == null ? null : (NoticePublic)paramsMap.get("noticePublic");
		
		//获取公告信息
		NoticePublic np = noticePublicDao.get(noticePublic.getUuid());
		if(np != null && noticePublic.getUuid().equals(np.getUuid())){
			
			//修改公告信息
			np.setType(noticePublic.getType());
			np.setTitle(noticePublic.getTitle());
			np.setDetails(noticePublic.getDetails());
			np.setStatus(noticePublic.getStatus());
			np.setOnlinetime(noticePublic.getOnlinetime());
			
			noticePublicDao.update(np);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("保存成功");
		} else {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
		}
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取公告信息
		NoticePublic np = noticePublicDao.get(uuid);
		if(np != null && uuid.equals(np.getUuid())){
			//删除公告信息
			np.setStatus(NoticePublicStatus.DELETE);
			noticePublicDao.update(np);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("删除成功");
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
		}
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		//获取公告信息
		NoticePublic np = noticePublicDao.get(uuid);
		if(np != null && uuid.equals(np.getUuid())){
			//删除公告信息
			np.setStatus(status);
			noticePublicDao.update(np);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("处理成功");
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
		}
	}
	
}
