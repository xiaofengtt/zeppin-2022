package cn.zeppin.action.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.Paper;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserPay;
import cn.zeppin.entity.Subject;
import cn.zeppin.service.api.IPaperService;
import cn.zeppin.service.api.ISsoUserPayService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.utility.Utlity;

public class SsoUserPayAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9071343070478346827L;
	
	private ISsoUserService ssoUserService;
	private ISubjectService subjectService;
	private IPaperService paperService;
	private ISsoUserPayService ssoUserPayService;
	
	public ISsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(ISsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}
	
	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public IPaperService getPaperService() {
		return paperService;
	}

	public void setPaperService(IPaperService paperService) {
		this.paperService = paperService;
	}
	
	public ISsoUserPayService getSsoUserPayService() {
		return ssoUserPayService;
	}

	public void setSsoUserPayService(ISsoUserPayService ssoUserPayService) {
		this.ssoUserPayService = ssoUserPayService;
	}
	
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "price", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "payType", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "device", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Subject() {
		ActionResult result = new ActionResult();
		
		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		if (currentUser == null) {
			int uid = this.getIntValue(request.getParameter("user.id"));
			currentUser = this.getSsoUserService().getById(uid);
		}
		if (currentUser == null) {
			result.init(FAIL_STATUS, "无此用户！", null);
		} else {
			Integer price = this.getIntValue(request.getParameter("price"));
			Integer subjectId = this.getIntValue(request.getParameter("subject.id"));
			Subject subject = this.getSubjectService().getSubjectById(subjectId);
			Short payType = Short.valueOf(request.getParameter("payType"));
			Short device = Short.valueOf(request.getParameter("device"));
			
			SsoUserPay ssoUserPay = new SsoUserPay();
			ssoUserPay.setSsoUser(currentUser);
			ssoUserPay.setSubject(subject);
			ssoUserPay.setPrice(price);
			ssoUserPay.setPayType(payType);
			ssoUserPay.setDevice(device);
			ssoUserPay.setCreatetime(new Timestamp((new Date()).getTime()));
			this.ssoUserPayService.addSsoUserPay(ssoUserPay);
			result.init(SUCCESS_STATUS, null, null);
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void SubjectPaperList() {
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		Integer subjectId = this.getIntValue(request.getParameter("subject.id"));
		
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("subject.id", subjectId);
		searchMap.put("isFree", 0);
		
		List<Paper> paperList = this.paperService.searchPaper(searchMap, null,  -1, -1);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(Paper p : paperList){
			Map<String,Object> data= new HashMap<String,Object>();
			data.put("id", p.getId());
			data.put("name", p.getName());
			data.put("price", p.getPrice());
			dataList.add(data);
		}
		
		result.init(SUCCESS_STATUS, null, dataList);
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "paper.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "price", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "payType", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "device", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Paper() {
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		
		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		if (currentUser == null) {
			int uid = this.getIntValue(request.getParameter("user.id"));
			currentUser = this.getSsoUserService().getById(uid);
		}
		if (currentUser == null) {
			result.init(FAIL_STATUS, "无此用户！", null);
		} else {
			Integer paperId = this.getIntValue(request.getParameter("paper.id"));
			Paper paper = this.getPaperService().getPaperById(paperId);
			if(paper != null){
				Integer price = this.getIntValue(request.getParameter("price"));
				Short payType = Short.valueOf(request.getParameter("payType"));
				Integer subjectId = this.getIntValue(request.getParameter("subject.id"));
				Subject subject = this.getSubjectService().getSubjectById(subjectId);
				Short device = Short.valueOf(request.getParameter("device"));
				
				SsoUserPay ssoUserPay = new SsoUserPay();
				ssoUserPay.setSsoUser(currentUser);
				ssoUserPay.setSubject(subject);
				ssoUserPay.setPaper(paper);
				ssoUserPay.setPayType(payType);
				ssoUserPay.setDevice(device);
				ssoUserPay.setPrice(price);
				ssoUserPay.setCreatetime(new Timestamp((new Date()).getTime()));
				this.ssoUserPayService.addSsoUserPay(ssoUserPay);
				result.init(SUCCESS_STATUS, null, null);
			}else{
				result.init(FAIL_STATUS, "无此试卷！", null);
			}
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
}
