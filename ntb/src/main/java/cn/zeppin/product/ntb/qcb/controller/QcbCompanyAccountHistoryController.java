/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbVirtualAccountsService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyAccountHistoryLessVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyAccountHistoryVO;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 企财宝企业账户信息
 */

@Controller
@RequestMapping(value = "/qcb/companyAccountHistory")
public class QcbCompanyAccountHistoryController extends BaseController {
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbVirtualAccountsService qcbVirtualAccountsService;
	
	@Autowired
	private IQcbCompanyAccountHistoryService qcbCompanyAccountHistoryService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	/**
	 * 获取账户明细
	 * @param type redeem--赎回/收益
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param deadline
	 * @param starttime
	 * @param endtime
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "deadline", message="期限", type = DataType.STRING)
	@ActionParam(key = "starttime", message="起始时间", type = DataType.DATE)//起始时间
	@ActionParam(key = "endtime", message="结束时间", type = DataType.DATE)//结束时间
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String type, Integer pageNum, Integer pageSize, String deadline, String starttime, String endtime, String sorts) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//校验企业是否存在
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("企业不正确！");
		}
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("type", type);
		searchMap.put("status", QcbCompanyAccountHistoryStatus.SUCCESS);
		searchMap.put("qcbCompany", qca.getUuid());
		
		Calendar c = Calendar.getInstance();
		if(deadline!= null && !"all".equals(deadline)){
			switch (deadline) {
			case "1":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -1);
		        Date m = c.getTime();
				starttime = Utlity.timeSpanToDateString(m);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "2":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -7);
		        Date m2 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m2);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "3":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -30);
		        Date m3 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m3);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "4":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -90);
		        Date m4 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m4);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "5":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -365);
		        Date m5 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m5);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			default:
				break;
			}
		}
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		
		Integer totalRecordsCount = this.qcbCompanyAccountHistoryService.getCount(searchMap);
		List<Entity> list = this.qcbCompanyAccountHistoryService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyAccountHistory.class);
		List<QcbCompanyAccountHistoryLessVO> listvo = new ArrayList<QcbCompanyAccountHistoryLessVO>();
		if(list != null && !list.isEmpty()){
			for(Entity entity : list){
				QcbCompanyAccountHistory qcah = (QcbCompanyAccountHistory)entity;
				QcbCompanyAccountHistoryLessVO vo = new QcbCompanyAccountHistoryLessVO(qcah);
				if(qcah.getCompanyAccount() != null){
					CompanyAccount ca = this.companyAccountService.get(qcah.getCompanyAccount());
					if(ca == null){
						return ResultManager.createFailResult("账户信息错误！");
					}
					Bank bank = this.bankService.get(ca.getBank());
					if(bank == null){
						return ResultManager.createFailResult("银行信息错误！");
					}
					String cardNum = ca.getAccountNum().substring(ca.getAccountNum().length() - 4, ca.getAccountNum().length());
					String bankName = bank.getShortName();
					String caName = ca.getCompanyName();
					vo.setCardNum(cardNum);
					vo.setBankName(bankName);
					vo.setCompanyAccount(caName);
				}
				listvo.add(vo);
			}
		}
		
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalRecordsCount);
	}
	
	/**
	 * 获取详情
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="数据编码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//校验企业是否存在
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("企业不正确！");
		}
		
		QcbCompanyAccountHistory qcah = this.qcbCompanyAccountHistoryService.get(uuid);
		if(qcah != null){
			if(!qcah.getQcbCompany().equals(qca.getUuid())){
				return ResultManager.createFailResult("无权查看该企业账务信息！");
			}
			QcbCompanyAccountHistoryVO vo = new QcbCompanyAccountHistoryVO(qcah);
			if(qcah.getCompanyAccount() != null){
				CompanyAccount ca = this.companyAccountService.get(qcah.getCompanyAccount());
				if(ca == null){
					return ResultManager.createFailResult("账户信息错误！");
				}
				Bank bank = this.bankService.get(ca.getBank());
				if(bank == null){
					return ResultManager.createFailResult("银行信息错误！");
				}
				String cardNum = ca.getAccountNum().substring(ca.getAccountNum().length() - 4, ca.getAccountNum().length());
				String bankName = bank.getShortName();
				String caName = ca.getCompanyName();
				vo.setCardNum(cardNum);
				vo.setBankName(bankName);
				vo.setCompanyAccount(caName);
			}
			
			return ResultManager.createDataResult(vo);
		} else {
			return ResultManager.createFailResult("账户历史信息不存在！");
		}
	}

	/**
	 * 批量导出账单列表
	 * @param type
	 * @param deadline
	 * @param starttime
	 * @param endtime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "deadline", message="期限", type = DataType.STRING)
	@ActionParam(key = "starttime", message="起始时间", type = DataType.DATE)//起始时间
	@ActionParam(key = "endtime", message="结束时间", type = DataType.DATE)//结束时间
	@ResponseBody
	public ModelAndView export(String type, String deadline, String starttime, String endtime, HttpServletRequest request, HttpServletResponse response) {
		
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		try {
			if(qcbAdmin == null){
//				return ResultManager.createFailResult("用户未登录！");
				response.getWriter().write("用户未登录！");
				return null;
			}
			
			String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
			if("".equals(company)){
				response.getWriter().write("无权查看该企业员工信息！");
				return null;
			}
			
			//校验企业是否存在
			QcbCompanyAccount qcbCom = this.qcbCompanyAccountService.get(company);
			if(qcbCom == null){
				response.getWriter().write("企业信息错误！");
				return null;
			}
			//校验企业是否存在
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcbAdmin.getQcbCompany());
			if(qca == null){
				response.getWriter().write("企业不正确！");
				return null;
			}
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("type", type);
			searchMap.put("status", QcbCompanyAccountHistoryStatus.SUCCESS);
			searchMap.put("qcbCompany", qca.getUuid());
			
			Calendar c = Calendar.getInstance();
			if(deadline!= null && !"all".equals(deadline)){
				switch (deadline) {
				case "1":
					c.setTime(new Date());
			        c.add(Calendar.DATE, -1);
			        Date m = c.getTime();
					starttime = Utlity.timeSpanToDateString(m);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;
				case "2":
					c.setTime(new Date());
			        c.add(Calendar.DATE, -7);
			        Date m2 = c.getTime();
					starttime = Utlity.timeSpanToDateString(m2);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;
				case "3":
					c.setTime(new Date());
			        c.add(Calendar.DATE, -30);
			        Date m3 = c.getTime();
					starttime = Utlity.timeSpanToDateString(m3);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;
				case "4":
					c.setTime(new Date());
			        c.add(Calendar.DATE, -90);
			        Date m4 = c.getTime();
					starttime = Utlity.timeSpanToDateString(m4);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;
				case "5":
					c.setTime(new Date());
			        c.add(Calendar.DATE, -365);
			        Date m5 = c.getTime();
					starttime = Utlity.timeSpanToDateString(m5);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;
				default:
					break;
				}
			}
			searchMap.put("starttime", starttime);
			searchMap.put("endtime", endtime);
			
			List<Entity> list = this.qcbCompanyAccountHistoryService.getListForPage(searchMap, -1, -1, null, QcbCompanyAccountHistory.class);
			List<QcbCompanyAccountHistoryLessVO> listvo = new ArrayList<QcbCompanyAccountHistoryLessVO>();
			if(list != null && !list.isEmpty()){
				for(Entity entity : list){
					QcbCompanyAccountHistory qcah = (QcbCompanyAccountHistory)entity;
					QcbCompanyAccountHistoryLessVO vo = new QcbCompanyAccountHistoryLessVO(qcah);
					if(qcah.getCompanyAccount() != null){
						CompanyAccount ca = this.companyAccountService.get(qcah.getCompanyAccount());
						if(ca == null){
							response.getWriter().write("账户信息错误！");
							return null;
						}
						Bank bank = this.bankService.get(ca.getBank());
						if(bank == null){
							response.getWriter().write("银行信息错误！");
							return null;
						}
						String cardNum = ca.getAccountNum().substring(ca.getAccountNum().length() - 4, ca.getAccountNum().length());
						String bankName = bank.getShortName();
						String caName = ca.getCompanyName();
						vo.setCardNum(cardNum);
						vo.setBankName(bankName);
						vo.setCompanyAccount(caName);
					}
					if(!Utlity.checkStringNull(qcah.getProduct())){
						if ("bank_product".equals(qcah.getProductType())) {
							BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(qcah.getProduct());
							if(bfpp != null){
								String productBank = bfpp.getCustodian();
								Bank bank = this.bankService.get(productBank);
								String productCN = "【"+bank.getShortName()+"】"+bfpp.getShortname();
								vo.setProductCN(productCN);
							}
						}
					}
					listvo.add(vo);
				}
			}
			
			
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "账务明细");
			XSSFRow row = s.createRow(0);
			String title[] = { "序号", "入账时间", "流水号", "账务类型", "对方信息", "交易金额", "账户余额", "备注"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(QcbCompanyAccountHistoryLessVO vo : listvo){
				
				t++;
				row = s.createRow(t);
				
				String time = vo.getCreatetimeCN();
				String orderNum = vo.getOrderNum();
				String typeCN = vo.getTypeCN();
				String accountInfo = "";
				if (QcbCompanyAccountHistoryType.INCOME.equals(vo.getType()) || QcbCompanyAccountHistoryType.WITHDRAW.equals(vo.getType())) {
					if(!Utlity.checkStringNull(vo.getCompanyAccount())){
						accountInfo = vo.getCompanyAccount() + "," + vo.getBankName() + "(尾号" + vo.getCardNum() + ")";
					}
				} else if(QcbCompanyAccountHistoryType.PAYROLL.equals(vo.getType())){
					accountInfo = "企财宝员工";
				} else if(QcbCompanyAccountHistoryType.BUY.equals(vo.getType()) || QcbCompanyAccountHistoryType.RETURN.equals(vo.getType()) 
						|| QcbCompanyAccountHistoryType.DIVIDEND.equals(vo.getType())){
					
					accountInfo = vo.getProductCN();
					
					
				} else if(QcbCompanyAccountHistoryType.EXPEND.equals(vo.getType())){
					accountInfo = "企财宝";
				} 
			
				String price = vo.getPrice();
				if(vo.getPriceflag()){
					price = "+"+price;
				} else {
					price = "-"+price;
				}
				String accountBalance = vo.getAccountBalanceCN();
				String remark = vo.getRemark();
				
				String tInfo[] = { String.valueOf(t), time , orderNum, typeCN, accountInfo, price, accountBalance, remark};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
				
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=order.xlsx");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("操作异常,导出失败！");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		} finally {
			if(ouputStream != null){
				try {
					ouputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
			if(wb != null){
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
		}
		return null;
	}
}
