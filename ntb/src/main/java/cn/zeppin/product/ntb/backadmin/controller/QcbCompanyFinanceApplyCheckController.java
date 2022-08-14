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

import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyFinanceApplyLessVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyFinanceApplyVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountFinanceStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFinanceApply;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFinanceApply.QcbCompanyFinanceApplyStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyFinanceApplyService;

/**
 * @author hehe
 *
 * 企财宝财税服务信息管理
 */

@Controller
@RequestMapping(value = "/backadmin/qcbfinance")
public class QcbCompanyFinanceApplyCheckController extends BaseController {
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbCompanyFinanceApplyService qcbCompanyFinanceApplyService;

	@Autowired
	private IBkAreaService bkAreaService;
	
	/**
	 * 获取状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<Entity> list = qcbCompanyFinanceApplyService.getStatusListForFinance(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取列表
	 * @param name
	 * @param status 'normal','uncheck','unauth'
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		
		List<QcbCompanyFinanceApplyLessVO> listvo = new ArrayList<QcbCompanyFinanceApplyLessVO>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		Integer totalResultCount = this.qcbCompanyFinanceApplyService.getCountForFinance(searchMap);
		List<Entity> list = this.qcbCompanyFinanceApplyService.getListForFinancePage(searchMap, pageNum, pageSize, sorts, QcbCompanyFinanceApplyLessVO.class);
		if(list != null && !list.isEmpty()){
			for(Entity entity : list){
				QcbCompanyFinanceApplyLessVO vo = (QcbCompanyFinanceApplyLessVO)entity;
				if(QcbCompanyAccountFinanceStatus.UNAUTH.equals(vo.getStatus())){
					QcbCompanyAccount qca = this.qcbCompanyAccountService.get(vo.getQcbCompany());
					if(qca == null){
						return ResultManager.createFailResult("信息查询有误！");
					}
					vo.setQcbCompanyName(qca.getName());
					vo.setQcbCompanyMerchantId(qca.getMerchantId());
				}
				BkArea area = this.bkAreaService.get(vo.getQcbCompanyArea());
				if(area != null){
					Integer level = area.getLevel();
					if(level == 1){
						vo.setQcbCompanyAreaStr(area.getName());
					} else if (level == 2) {
						BkArea province = this.bkAreaService.get(area.getPid());
						vo.setQcbCompanyAreaStr(province.getName());
					} else if (level == 3) {
						BkArea city = this.bkAreaService.get(area.getPid());
						BkArea province = this.bkAreaService.get(city.getPid());
						vo.setQcbCompanyAreaStr(province.getName());
					} else {
						vo.setQcbCompanyAreaStr("--");
					}
				} else {
					vo.setQcbCompanyAreaStr("--");
				}
				listvo.add(vo);
			}
		}
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取一财税服务申请信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="申请编码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		
		QcbCompanyFinanceApply qcfa = this.qcbCompanyFinanceApplyService.get(uuid);
		
		if(qcfa != null){
			//查询当前企业是否发起过财税服务申请
			QcbCompanyFinanceApplyVO  qcfavo = new QcbCompanyFinanceApplyVO(qcfa);
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcfa.getQcbCompany());
			if(qca == null){
				return ResultManager.createFailResult("信息查询有误！");
			}
			qcfavo.setQcbCompany(qca.getName());
			qcfavo.setQcbCompanyMerchantId(qca.getMerchantId());
			BkArea area = this.bkAreaService.get(qca.getBkArea());
			if(area != null){
				Integer level = area.getLevel();
				if(level == 1){
					qcfavo.setQcbCompanyArea(area.getName());
				} else if (level == 2) {
					BkArea province = this.bkAreaService.get(area.getPid());
					qcfavo.setQcbCompanyArea(province.getName());
				} else if (level == 2) {
					BkArea city = this.bkAreaService.get(area.getPid());
					BkArea province = this.bkAreaService.get(city.getPid());
					qcfavo.setQcbCompanyArea(province.getName());
				} else {
					qcfavo.setQcbCompanyArea("--");
				}
			} else {
				qcfavo.setQcbCompanyArea("--");
			}
			return ResultManager.createDataResult(qcfavo);
		}
		
		return ResultManager.createFailResult("数据不存在！");
		
	}
	
	/**
	 * 开通
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="申请编码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result check(String uuid) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		QcbCompanyFinanceApply qcfa = this.qcbCompanyFinanceApplyService.get(uuid);
		
		if(qcfa != null){
			if(QcbCompanyFinanceApplyStatus.UNCHECKED.equals(qcfa.getStatus())){
				qcfa.setChecker(currentOperator.getUuid());
				qcfa.setCheckreason("通过");
				qcfa.setChecktime(new Timestamp(System.currentTimeMillis()));
				qcfa.setStatus(QcbCompanyFinanceApplyStatus.CHECKED);
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcfa.getQcbCompany());
				if(qca == null){
					return ResultManager.createFailResult("该企业数据不存在！");
				}
				qca.setFinanceStatus(QcbCompanyAccountFinanceStatus.NORMAL);
				
				try {
					this.qcbCompanyFinanceApplyService.update(qcfa,qca);
				} catch (Exception e) {
					e.printStackTrace();
					super.flushAll();
					return ResultManager.createFailResult("操作异常！");
				}
				
			} else {
				return ResultManager.createFailResult("审核状态错误！");
			}
		}
		return ResultManager.createFailResult("数据不存在！");
	}
}
