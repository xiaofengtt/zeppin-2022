package cn.zeppin.product.ntb.qcb.controller.wechat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbNotice;
import cn.zeppin.product.ntb.core.entity.QcbNoticeEmployee;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbNoticeEmployeeService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbNoticeService;
import cn.zeppin.product.ntb.qcb.vo.QcbNoticeEmployeeVO;

@Controller
@RequestMapping(value = "/qcbWechat/notice")
public class WechatNoticeController extends BaseController{
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IQcbNoticeService qcbNoticeService;
	
	@Autowired
	private IQcbNoticeEmployeeService qcbNoticeEmployeeService;
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * 获取用户收到的消息列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	public Result getList(ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", qe.getUuid());
		Integer totalCount = this.qcbNoticeEmployeeService.getCount(inputParams);
		List<Entity> list = this.qcbNoticeEmployeeService.getListForPage(inputParams, -1, -1, null, QcbNoticeEmployeeVO.class);
		List<String> listVO = new ArrayList<String>();
		if( list != null && !list.isEmpty()){
			for(Entity entity : list){
				QcbNoticeEmployeeVO ii = (QcbNoticeEmployeeVO)entity;
				listVO.add(ii.getUuid());
			}
		}
		try {
			//批处理isshow
			this.qcbNoticeEmployeeService.updateBatchIsShow(listVO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return ResultManager.createDataResult(list, totalCount);
	}
	
	/**
	 * 获取用户收到的消息内容
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户通知编号", required = true)
	@ResponseBody
	public Result get(String uuid, ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		QcbNoticeEmployee qne = this.qcbNoticeEmployeeService.get(uuid);
		if(qne != null){
			if(!qne.getQcbEmployee().equals(qe.getUuid())){
				return ResultManager.createFailResult("信息不存在");
			}
			QcbNoticeEmployeeVO qnevo = new QcbNoticeEmployeeVO(qne);
			QcbNotice qn = this.qcbNoticeService.get(qne.getQcbNotice());
			if(qn == null){
				return ResultManager.createFailResult("信息不存在");
			}
			qnevo.setTitle(qn.getTitle());
			qnevo.setContent(qn.getContent());
			qnevo.setCreatetime(qn.getCreatetime());
			qnevo.setStarttime(qn.getStarttime());
			qnevo.setEndtime(qn.getEndtime());
			
			try {
				qne.setIsRead(true);
				this.qcbNoticeEmployeeService.update(qne);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return ResultManager.createDataResult(qnevo);
		} else {
			return ResultManager.createFailResult("信息不存在");
		}
	}
	
	/**
	 * 全部表为已读
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/read", method = RequestMethod.POST)
	@ResponseBody
	public Result read(ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", qe.getUuid());
		List<Entity> list = this.qcbNoticeEmployeeService.getListForPage(inputParams, -1, -1, null, QcbNoticeEmployeeVO.class);
		List<String> listVO = new ArrayList<String>();
		if( list != null && !list.isEmpty()){
			for(Entity entity : list){
				QcbNoticeEmployeeVO ii = (QcbNoticeEmployeeVO)entity;
				listVO.add(ii.getUuid());
			}
		}
		
		try {
			//批处理isread
			this.qcbNoticeEmployeeService.updateBatchIsRead(listVO);
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("处理异常，请重新操作！");
		}
		return ResultManager.createSuccessResult("成功！");
	}
}
