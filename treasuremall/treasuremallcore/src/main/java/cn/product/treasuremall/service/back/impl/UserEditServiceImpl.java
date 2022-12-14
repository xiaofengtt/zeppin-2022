package cn.product.treasuremall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserEditDao;
import cn.product.treasuremall.dao.FrontUserMessageDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserEdit;
import cn.product.treasuremall.entity.FrontUserEdit.FrontUserEditStatus;
import cn.product.treasuremall.entity.FrontUserEdit.FrontUserEditType;
import cn.product.treasuremall.entity.FrontUserMessage;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.service.back.UserEditService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.FrontUserEditVO;

@Service("userEditService")
public class UserEditServiceImpl implements UserEditService{
	
	@Autowired
	private FrontUserEditDao frontUserEditDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserMessageDao frontUserMessageDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String showId = paramsMap.get("showId") == null ? "" : paramsMap.get("showId").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String createtime = paramsMap.get("createtime") == null ? "" : paramsMap.get("createtime").toString();
		
		//????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("showid", showId);
		searchMap.put("mobile", mobile);
		searchMap.put("name", name);
		searchMap.put("status", status);
		
		if(!Utlity.checkStringNull(createtime)) {
			String[] times = createtime.split("_");
			if(times != null && times.length == 2) {
				searchMap.put("timestart", times[0].trim());
				searchMap.put("timeend", times[1].trim());
			}
		}
		
		searchMap.put("sort", sort);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//????????????????????????????????????????????????
		Integer totalResultCount = frontUserEditDao.getLeftCountByParams(searchMap);
		//?????????????????????????????????????????????
		List<FrontUserEdit> list = frontUserEditDao.getLeftListByParams(searchMap);
		List<FrontUserEditVO> listvo = new ArrayList<FrontUserEditVO>();
		for(FrontUserEdit frontUserEdit : list) {
			//????????????????????????
			FrontUserEditVO frontUserEditVO = new FrontUserEditVO(frontUserEdit);
			
			if(FrontUserEditType.HEADIMAGE.equals(frontUserEdit.getType())) {
				Resource re = this.resourceDao.get(frontUserEdit.getInfoBefore());
				if(re != null) {
					frontUserEditVO.setInfoBeforeUrl(re.getUrl());
				}
				Resource reAfter = this.resourceDao.get(frontUserEdit.getInfoAfter());
				if(reAfter != null) {
					frontUserEditVO.setInfoAfterUrl(reAfter.getUrl());
				}
			}
			//????????????
			FrontUser fu = this.frontUserDao.get(frontUserEdit.getFrontUser());
			if(fu != null) {
				frontUserEditVO.setFrontUserShowId(fu.getShowId());
				frontUserEditVO.setNickName(fu.getNickname());
			}
			listvo.add(frontUserEditVO);
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
		//????????????????????????
		FrontUserEdit frontUserEdit = frontUserEditDao.get(uuid);
		if (frontUserEdit == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
			return;
		}
		
		//????????????????????????
		FrontUserEditVO frontUserEditVO = new FrontUserEditVO(frontUserEdit);
		
		if(FrontUserEditType.HEADIMAGE.equals(frontUserEdit.getType())) {
			Resource re = this.resourceDao.get(frontUserEdit.getInfoBefore());
			if(re != null) {
				frontUserEditVO.setInfoBeforeUrl(re.getUrl());
			}
			Resource reAfter = this.resourceDao.get(frontUserEdit.getInfoAfter());
			if(reAfter != null) {
				frontUserEditVO.setInfoAfterUrl(reAfter.getUrl());
			}
		}
		//????????????
		FrontUser fu = this.frontUserDao.get(frontUserEdit.getFrontUser());
		if(fu != null) {
			frontUserEditVO.setFrontUserShowId(fu.getShowId());
			frontUserEditVO.setNickName(fu.getNickname());
		}
		
		result.setData(frontUserEditVO);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void check(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String reason = paramsMap.get("reason") == null ? "" : paramsMap.get("reason").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		if(!FrontUserEditStatus.CHECKED.equals(status) && !FrontUserEditStatus.NOPASS.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????");
			return;
		}
		//????????????????????????
		FrontUserEdit frontUserEdit = frontUserEditDao.get(uuid);
		if(frontUserEdit != null && uuid.equals(frontUserEdit.getUuid())){
			frontUserEdit.setStatus(status);
			if(!Utlity.checkStringNull(reason)) {
				frontUserEdit.setReason(reason);
			}
			
			frontUserEdit.setOperator(admin);
			frontUserEdit.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			//????????????
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setTitle("??????????????????");
			fum.setSourceId(frontUserEdit.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.SYSTEM_NOTICE);
			fum.setSourceType(FrontUserMessageSourceType.USER_EDIT);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			StringBuilder content = new StringBuilder();
			FrontUser fu = this.frontUserDao.get(frontUserEdit.getFrontUser());
			if(fu != null) {
				fum.setFrontUser(fu.getUuid());
				fum.setFrontUserShowId(fu.getShowId());
				
				if(FrontUserEditType.NICKNAME.equals(frontUserEdit.getType())) {
					content.append("??????"+Utlity.timeSpanToChinaDateString(frontUserEdit.getCreatetime())+"????????????????????????"+frontUserEdit.getInfoBefore()+"?????????"+ frontUserEdit.getInfoAfter() +",");
					if(FrontUserEditStatus.CHECKED.equals(status)) {
						content.append("???????????????");
						fu.setNickname(frontUserEdit.getInfoAfter());
						this.frontUserEditDao.check(frontUserEdit, fu);
					}else {
						content.append("????????????????????????????????????"+reason);
						frontUserEditDao.update(frontUserEdit);
					}
					
				} else if (FrontUserEditType.HEADIMAGE.equals(frontUserEdit.getType())) {
					content.append("??????"+Utlity.timeSpanToChinaDateString(frontUserEdit.getCreatetime())+"??????????????????,");
					if(FrontUserEditStatus.CHECKED.equals(status)) {
						content.append("???????????????");
						fu.setImage(frontUserEdit.getInfoAfter());
						this.frontUserEditDao.check(frontUserEdit, fu);
					}else {
						content.append("????????????????????????????????????"+reason);
						frontUserEditDao.update(frontUserEdit);
					}
				}
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
				return;
			}
			fum.setContent(content.toString());
			
			//????????????????????????
			this.frontUserMessageDao.sendMessage(fum);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("????????????");
		} else {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
		}
	}
}
