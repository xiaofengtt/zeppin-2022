package com.cmos.china.mobile.media.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




import com.cmos.china.mobile.media.core.bean.LeaveMessage;
import com.cmos.china.mobile.media.core.bean.LeaveMessage.LeaveMessageStatusType;
import com.cmos.china.mobile.media.core.service.ILeaveMessageService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.LeaveMessageVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;

public class LeaveMessageServiceImpl extends BaseServiceImpl implements ILeaveMessageService {
 
	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String province = inputObject.getValue("province");
		String status = inputObject.getValue("status");
		String content = inputObject.getValue("content");
		String videoPublish = inputObject.getValue("videoPublish");
		String search = inputObject.getValue("search");
		Integer pagenum = Utlity.getIntValue(inputObject.getValue("pagenum"), 1);
		Integer pagesize = Utlity.getIntValue(inputObject.getValue("pagesize"), 10);
		String sort = inputObject.getValue("sort");
		
		if(province==null||"".equals(province)){
			throw new ExceptionUtil("地区不能为空");
		}
		
		if(!Utlity.checkOrderBy(sort)){
			throw new ExceptionUtil("参数异常");
		}
		
		Integer start = (pagenum - 1) * pagesize;
		
		if(sort == null || "".equals(sort)){
			sort = "createtime";
		}else{
			sort = sort.replaceAll("-", " ");
		}
		if(status == null || "".equals(status)){
			status = " in ('unchecked','checked','nopass')";
		}else{
			status = " = '"+status+"'";
		}
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("province", province);
		paramMap.put("content", content);
		paramMap.put("videoPublish", videoPublish);
		paramMap.put("status", status);
		paramMap.put("start", start+"");
		paramMap.put("limit", pagesize+"");
		paramMap.put("sort", sort);
		paramMap.put("search", search);
		
		Integer count = this.getBaseDao().getTotalCount("leaveMessage_getTotalCountByParams", paramMap);
		Integer pageCount = (int) Math.ceil((double) count / pagesize);
		
		List<LeaveMessageVO> list = this.getBaseDao().queryForList("leaveMessage_getListByParams", paramMap, LeaveMessageVO.class);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("pageNum", pagenum);
		resultMap.put("pageSize", pagesize);
		resultMap.put("totalPageCount", pageCount);
		resultMap.put("totalResultCount", count);
		
		outputObject.convertBeans2List(list);
		outputObject.setObject(resultMap);
	}

	@Override
	public void changeStatus(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String status = inputObject.getValue("status");
		String auditor = inputObject.getValue("auditor");
		String id = inputObject.getValue("id");
		
		if(!LeaveMessageStatusType.CHECKED.equals(status) && !LeaveMessageStatusType.NOPASS.equals(status)
				&& !LeaveMessageStatusType.DELETED.equals(status)){
			throw new ExceptionUtil("参数异常");
		}
		
		if (id != null && !"".equals(id)) {
			if (status != null && !"".equals(status)) {
				LeaveMessage leavemessage = new LeaveMessage();
				leavemessage.setId(id);
				leavemessage.setAuditor(auditor);
				leavemessage.setStatus(status);
				this.getBaseDao().update("leaveMessage_changeStatus",leavemessage);
			} else {
				throw new ExceptionUtil("参数异常");
			}
		} else {
			throw new ExceptionUtil("参数异常");
		}
	}
}
