/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.vo.QcbNoticeVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.QcbNotice;
import cn.zeppin.product.ntb.core.entity.QcbNotice.QcbNoticeStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbNoticeService;
import cn.zeppin.product.utility.Utlity;

/**
 * @author elegantclack
 *
 * 后台通知信息管理
 */

@Controller
@RequestMapping(value = "/backadmin/qcbNotice")
public class QcbNoticeController extends BaseController {
 
	@Autowired
	private IQcbNoticeService qcbNoticeService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	/**
	 * 根据条件查询信息 
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//查询符合条件的信息的总数
		Integer totalResultCount = qcbNoticeService.getCount(searchMap);
		//查询符合条件的信息列表
		List<Entity> list = qcbNoticeService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbNotice.class);
		List<QcbNoticeVO> listvo = new ArrayList<QcbNoticeVO>();
		if(list != null && list.size() > 0){
			for(Entity e : list){
				QcbNotice qn = (QcbNotice)e;
				QcbNoticeVO qnvo = new QcbNoticeVO(qn);
				if(qn.getCreator() != null){
					BkOperator bko = this.bkOperatorService.get(qn.getCreator());
					if(bko != null){
						qnvo.setCreatorCN(bko.getRealname());
					}
				}
				listvo.add(qnvo);
			}
		}
		
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<Entity> list = qcbNoticeService.getStatusList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取一条信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取信息
		QcbNotice notice = qcbNoticeService.get(uuid);
		if (notice == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		//界面返回封装对象
		QcbNoticeVO noticeVO = new QcbNoticeVO(notice);
		

		return ResultManager.createDataResult(noticeVO);
	}
	
	/**
	 * 添加一条信息
	 * @param title
	 * @param content
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "title", message="标题", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "content", message="内容", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING, required = true)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String title, String content, String status, String starttime, String endtime) {
		
//		//验证是否有重名的情况
//		if (qcbNoticeService.isExistQcbNoticeByName(name,null)) {
//			return ResultManager.createFailResult("该名称的已存在！");
//		}
		if(Utlity.checkStringNull(status)){
			status = QcbNoticeStatus.NORMAL;
		} else {
			if(!QcbNoticeStatus.NORMAL.equals(status) && !QcbNoticeStatus.DISABLED.equals(status) && !QcbNoticeStatus.DELETED.equals(status)){
				return ResultManager.createFailResult("请选择正确的状态值！");
			}
		}
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//创建信息
		QcbNotice notice = new QcbNotice();
		notice.setUuid(UUID.randomUUID().toString());
		notice.setTitle(title);
		notice.setContent(content);
		notice.setStatus(status);
		
		notice.setStarttime(Timestamp.valueOf(starttime));
		notice.setEndtime(Timestamp.valueOf(endtime));
		
		notice.setCreator(currentOperator.getUuid());
		notice.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		notice = qcbNoticeService.insert(notice);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑一条信息
	 * @param uuid
	 * @param title
	 * @param content
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "title", message="标题", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "content", message="内容", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING, required = true)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String title, String content, String status, String starttime, String endtime) {
		
		//获取信息
		QcbNotice notice = qcbNoticeService.get(uuid);
		if(notice != null && uuid.equals(notice.getUuid())){
			
			if(!QcbNoticeStatus.NORMAL.equals(status) && !QcbNoticeStatus.DISABLED.equals(status) && !QcbNoticeStatus.DELETED.equals(status)){
				return ResultManager.createFailResult("请选择正确的状态值！");
			}
			
			//修改信息
			notice.setTitle(title);
			notice.setContent(content);
			notice.setStatus(status);
			notice.setStarttime(Timestamp.valueOf(starttime));
			notice.setEndtime(Timestamp.valueOf(endtime));
			
			notice = qcbNoticeService.update(notice);
			
			return ResultManager.createSuccessResult("保存成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//获取信息
		QcbNotice bank = qcbNoticeService.get(uuid);
		if(bank != null && uuid.equals(bank.getUuid())){
			//删除信息
			qcbNoticeService.delete(bank);
			return ResultManager.createSuccessResult("删除成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
}
