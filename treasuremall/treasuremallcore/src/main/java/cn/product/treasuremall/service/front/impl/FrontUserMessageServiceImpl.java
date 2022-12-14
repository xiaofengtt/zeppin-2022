package cn.product.treasuremall.service.front.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserMessageDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.FrontUserMessage;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.service.front.FrontUserMessageService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.front.FrontUserMessageVO;

@Service("frontUserMessageService")
public class FrontUserMessageServiceImpl implements FrontUserMessageService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
    private FrontUserMessageDao frontUserMessageDao;
	
	@Autowired
    private ResourceDao resourceDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		//????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("sort", sorts);
		searchMap.put("status", status);
		searchMap.put("frontUser", frontUser);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//??????????????????????????????????????????
		Integer totalResultCount = frontUserMessageDao.getCountByParams(searchMap);
		//???????????????????????????????????????
		List<FrontUserMessage> list = frontUserMessageDao.getListByParams(searchMap);
		
		List<FrontUserMessageVO> listvo = new ArrayList<FrontUserMessageVO>();
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		for(FrontUserMessage fum : list) {
			//????????????????????????
			FrontUserMessageVO fumvo = new FrontUserMessageVO(fum);
			
			//????????????
			//??????
			if(!Utlity.checkStringNull(fum.getSourceImage())) {
				Resource re = this.resourceDao.get(fum.getSourceImage());
				if(re != null) {
					fumvo.setSourceImageUrl(pathUrl + re.getUrl());
				}
			}
			
			listvo.add(fumvo);
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
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		FrontUserMessage fum = this.frontUserMessageDao.get(uuid);
		if(fum == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????");
			return;
		}
		if(!fum.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????");
			return;
		}
			
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		//????????????????????????
		FrontUserMessageVO fumvo = new FrontUserMessageVO(fum);
		
		//????????????
		//??????
		if(!Utlity.checkStringNull(fum.getSourceImage())) {
			Resource re = this.resourceDao.get(fum.getSourceImage());
			if(re != null) {
				fumvo.setSourceImageUrl(pathUrl + re.getUrl());
			}
		}
		fum.setStatus(FrontUserMessageStatus.READ);
		this.frontUserMessageDao.update(fum);
		result.setData(fumvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		return;
	}

	@Override
	public void readAll(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		//????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", FrontUserMessageStatus.NORMAL);
		searchMap.put("frontUser", frontUser);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//???????????????????????????????????????
		List<FrontUserMessage> list = frontUserMessageDao.getListByParams(searchMap);		
		List<FrontUserMessage> updateList = new ArrayList<FrontUserMessage>();
		if(list != null && list.size() > 0) {
			for(FrontUserMessage fum : list) {
				fum.setStatus(FrontUserMessageStatus.READ);
				updateList.add(fum);
			}
		}
		this.frontUserMessageDao.readAll(updateList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("????????????");
		return;
	}

	@Override
	public void unRead(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		//????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", FrontUserMessageStatus.NORMAL);
		searchMap.put("frontUser", frontUser);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//???????????????????????????????????????
		Integer totalResultCount = frontUserMessageDao.getCountByParams(searchMap);
		result.setData(totalResultCount == null ? 0 : totalResultCount);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("????????????");
		return;
	}
}
