/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.ShbxSecurityOrderInfoDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.ShbxSecurityOrderInfoVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.ShbxInsured;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder.ShbxSecurityOrderStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.shbx.service.api.IShbxInsuredService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxSecurityOrderService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserHistoryService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserService;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * ???????????????
 */

@Controller
@RequestMapping(value = "/backadmin/shbxOrder")
public class ShbxUserOrderController extends BaseController {
	
	@Autowired
	private IShbxUserService shbxUserService;
	
	@Autowired
	private IShbxInsuredService shbxInsuredService;
	
	@Autowired
	private IShbxSecurityOrderService shbxSecurityOrderService;
	
	@Autowired
	private IShbxUserHistoryService shbxUserHistoryService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * ???????????????????????????????????????
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/orderInfoList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="????????????", type = DataType.STRING)
	@ActionParam(key = "name", message="??????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result orderInfoList(String uuid, String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("creator", uuid);
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			searchMap.put("status", status);
		}
		
		if(!Utlity.checkStringNull(name)){
			searchMap.put("name", name);
		}
		
		Integer totalResultCount = this.shbxSecurityOrderService.getCount(searchMap);
		List<Entity> list = this.shbxSecurityOrderService.getListForPage(searchMap, pageNum, pageSize, sorts, ShbxSecurityOrder.class);
		List<ShbxSecurityOrderInfoVO> listResult = new ArrayList<ShbxSecurityOrderInfoVO>();
		if(list != null && list.size() > 0){
			for(Entity e : list){
				ShbxSecurityOrder sso = (ShbxSecurityOrder)e;
				ShbxSecurityOrderInfoVO ssovo = new ShbxSecurityOrderInfoVO(sso);
				
				//?????????
				if(!Utlity.checkStringNull(sso.getShbxUserHistory())){
					ShbxUserHistory suh = this.shbxUserHistoryService.get(sso.getShbxUserHistory());
					if(suh != null){
						ssovo.setTotalAmount(suh.getPay());
						ssovo.setTotalAmountCN(Utlity.numFormat4UnitDetail(suh.getPay()));
					}
				}
				
				//???????????????
				ShbxInsured si = this.shbxInsuredService.get(sso.getShbxInsured());
				if(si != null){
					ssovo.setShbxInsuredName(si.getName());
				}
				listResult.add(ssovo);
			}
		}
		
		return ResultManager.createDataResult(listResult, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ????????????????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<Entity> list = this.shbxSecurityOrderService.getStatusList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????
	 * @return
	 */ 
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//????????????????????????
		ShbxSecurityOrder sso = this.shbxSecurityOrderService.get(uuid);
		if (sso == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		ShbxSecurityOrderInfoDetailVO vo = new ShbxSecurityOrderInfoDetailVO(sso);
		//?????????
		if(!Utlity.checkStringNull(sso.getShbxUserHistory())){
			ShbxUserHistory suh = this.shbxUserHistoryService.get(sso.getShbxUserHistory());
			if(suh != null){
				vo.setTotalAmount(suh.getPay());
				vo.setTotalAmountCN(Utlity.numFormat4UnitDetail(suh.getPay()));
			}
		}
		
		//????????????
		ShbxUser su = this.shbxUserService.get(sso.getCreator());
		if(su != null){
			vo.setCreatorName(su.getRealname());
		}
		
		//???????????????
		ShbxInsured si = this.shbxInsuredService.get(sso.getShbxInsured());
		if(si != null){
			vo.setShbxInsuredName(si.getName());
		}
		
		//???????????????
		if(!Utlity.checkStringNull(sso.getProcessCreator())){
			BkOperator op = this.bkOperatorService.get(sso.getProcessCreator());
			if(op != null){
				vo.setProcessCreatorName(op.getRealname());
			}
		}
		
		//????????????
		if(!Utlity.checkStringNull(sso.getReceipt())){
			String[] recArr = sso.getReceipt().split(",");
			List<Object> listReceipt = new ArrayList<Object>();
			for(String str : recArr){
				Resource res = this.resourceService.get(str);
				if(res != null){
					listReceipt.add(res);
				}
			}
			vo.setReceipts(listReceipt);		
		}
		
		
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * ????????????
	 * @param uuid
	 * @param company
	 * @param receipt
	 * @return
	 */
	@RequestMapping(value = "/process", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="??????ID", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "company", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "receipt", message="????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result process(String uuid, String company, String receipt) {
		//????????????????????????
		ShbxSecurityOrder sso = this.shbxSecurityOrderService.get(uuid);
		if (sso == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		try {
			sso.setCompany(company);
			sso.setReceipt(receipt);
			
			sso.setStatus(ShbxSecurityOrderStatus.NORMAL);
			sso.setProcessCreatetime(new Timestamp(System.currentTimeMillis()));
			sso.setProcessCreator(currentOperator.getUuid());
			
			this.shbxSecurityOrderService.update(sso);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("????????????");	
		}

		
		return ResultManager.createSuccessResult("????????????");
	}
	
//	/**
//	 * ?????????????????????????????????
//	 * @param shbxUser
//	 * @param pageNum
//	 * @param pageSize
//	 * @param sorts
//	 * @return
//	 */
//	@RequestMapping(value = "/insuredList", method = RequestMethod.GET)
//	@ActionParam(key = "shbxUser", message="??????ID", type = DataType.STRING, required = true, maxLength = 36)
//	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
//	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
//	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
//	@ResponseBody
//	public Result insuredList(String shbxUser, Integer pageNum, Integer pageSize, String sorts) {
//		
//		//????????????????????????
//		ShbxUser su = this.shbxUserService.get(shbxUser);
//		if(su == null){
//			return ResultManager.createFailResult("??????????????????");
//		}
//		Map<String, String> searchMap = new HashMap<String, String>();
//		searchMap.put("status", ShbxInsuredStatus.NORMAL);
//		searchMap.put("shbxUser", su.getUuid());
//		
//		Integer totalRecordsCount = this.shbxInsuredService.getCountForShbx(searchMap);
//		List<Entity> list = this.shbxInsuredService.getListForShbxPage(searchMap, pageNum, pageSize, sorts, ShbxInsured.class);
//		
//		return ResultManager.createDataResult(list, pageNum, pageSize, totalRecordsCount);
//	}
//	
//	/**
//	 * ???????????????????????????
//	 * @param uuid
//	 * @return
//	 */
//	@RequestMapping(value = "/insuredGet", method = RequestMethod.GET)
//	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
//	@ResponseBody
//	public Result insuredGet(String uuid) {
//		ShbxInsured si = this.shbxInsuredService.get(uuid);
//		if(si != null){
//			return ResultManager.createDataResult(si);
//		} else {
//			return ResultManager.createFailResult("?????????????????????");
//		}
//	}
}
