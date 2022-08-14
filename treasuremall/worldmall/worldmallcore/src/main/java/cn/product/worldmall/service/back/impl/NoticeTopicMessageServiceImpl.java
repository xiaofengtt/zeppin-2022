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
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.NoticeTopicDao;
import cn.product.worldmall.dao.NoticeTopicMessageDao;
import cn.product.worldmall.entity.NoticeTopic;
import cn.product.worldmall.entity.NoticeTopic.NoticeTopicStatus;
import cn.product.worldmall.entity.NoticeTopicMessage;
import cn.product.worldmall.entity.NoticeTopicMessage.NoticeTopicMessageStatus;
import cn.product.worldmall.entity.NoticeTopicMessage.NoticeTopicMessageType;
import cn.product.worldmall.service.back.NoticeTopicMessageService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.NoticeTopicMessageVO;

@Service("noticeTopicMessageService")
public class NoticeTopicMessageServiceImpl implements NoticeTopicMessageService{
	
	@Autowired
	private NoticeTopicMessageDao noticeTopicMessageDao;
	
	@Autowired
	private NoticeTopicDao noticeTopicDao;
	

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String topic = paramsMap.get("topic") == null ? "" : paramsMap.get("topic").toString();
		String title = paramsMap.get("title") == null ? "" : paramsMap.get("title").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String sendtime = paramsMap.get("sendtime") == null ? "" : paramsMap.get("sendtime").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("noticeTopic", topic);
		searchMap.put("title", title);
		searchMap.put("status", status);
		searchMap.put("type", type);
		
		if(!Utlity.checkStringNull(sendtime)) {
			String[] times = sendtime.split("_");
			if(times != null && times.length == 2) {
				searchMap.put("timestart", times[0].trim());
				searchMap.put("timeend", times[1].trim());
			}
		}
		searchMap.put("sort", sorts);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的公告信息的总数
		Integer totalResultCount = noticeTopicMessageDao.getCountByParams(searchMap);
		//查询符合条件的公告信息列表
		List<NoticeTopicMessage> list = noticeTopicMessageDao.getListByParams(searchMap);
		List<NoticeTopicMessageVO> listvo = new ArrayList<NoticeTopicMessageVO>();
		if(list != null && list.size() > 0) {
			for(NoticeTopicMessage ntm : list) {
				NoticeTopicMessageVO ntmvo = new NoticeTopicMessageVO(ntm);
				NoticeTopic nt = this.noticeTopicDao.get(ntm.getNoticeTopic());
				if(nt != null) {
					ntmvo.setDisplayName(nt.getDisplayName());
				}
				
				listvo.add(ntmvo);
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
		NoticeTopicMessage ntm = noticeTopicMessageDao.get(uuid);
		if (ntm == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		NoticeTopicMessageVO ntmvo = new NoticeTopicMessageVO(ntm);
		NoticeTopic nt = this.noticeTopicDao.get(ntm.getNoticeTopic());
		if(nt != null) {
			ntmvo.setDisplayName(nt.getDisplayName());
		}
		result.setData(ntmvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void send(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String topic = paramsMap.get("topic") == null ? "" : paramsMap.get("topic").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String title = paramsMap.get("title") == null ? "" : paramsMap.get("title").toString();
		String content = paramsMap.get("content") == null ? "" : paramsMap.get("content").toString();
		String sendtime = paramsMap.get("sendtime") == null ? "" : paramsMap.get("sendtime").toString();
		
		try {
			NoticeTopic nt = this.noticeTopicDao.get(topic);
			if(nt == null || !NoticeTopicStatus.NORMAL.equals(nt.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("消息主题已失效！");
				return;
			}
			
			if(Utlity.checkStringNull(type)) {
				type = NoticeTopicMessageType.SYSTEM_PUBLISH;
			}
			NoticeTopicMessage ntm = new NoticeTopicMessage();
			if(!Utlity.checkStringNull(uuid)) {
				ntm = this.noticeTopicMessageDao.get(uuid);
				if(ntm != null && NoticeTopicMessageStatus.FAIL.equals(ntm.getStatus())) {
					ntm.setNoticeTopic(topic);
					if(!Utlity.checkStringNull(sendtime)) {
						ntm.setSendtime(new Timestamp(Utlity.stringToDatetime(sendtime).getTime()));
					}
					ntm.setType(type);
					ntm.setTitle(title);
					ntm.setContent(content);
					ntm.setStatus(NoticeTopicMessageStatus.NORMAL);//待发送状态
					//入库--准备发送
					this.noticeTopicMessageDao.update(ntm);
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("数据错误，操作失败！");
					return;
				}
			} else {
				ntm.setUuid(UUID.randomUUID().toString());
				ntm.setCreatetime(new Timestamp(System.currentTimeMillis()));
				ntm.setNoticeTopic(topic);
				if(!Utlity.checkStringNull(sendtime)) {
					ntm.setSendtime(new Timestamp(Utlity.stringToDatetime(sendtime).getTime()));
				} else {
					ntm.setSendtime(ntm.getCreatetime());
				}
				ntm.setType(type);
				ntm.setTitle(title);
				ntm.setContent(content);
				ntm.setStatus(NoticeTopicMessageStatus.NORMAL);//待发送状态
				//入库--准备发送
				this.noticeTopicMessageDao.insert(ntm);
			}
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("操作成功");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
	}
}
