/**
 * 
 */
package cn.product.payment.controller.system;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;
import cn.product.payment.entity.Company.CompanyStatus;
import cn.product.payment.entity.StatisticsCompany.StatisticsCompanyType;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.Utlity;

@Controller
@RequestMapping(value = "/system/statisticsCompany")
public class SystemStatisticsCompanyController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7832681758855213172L;

	/**
	 * 按日统计
	 * @param company
	 * @param starttime
	 * @param endtime
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/dailyList", method = RequestMethod.GET)
	@ActionParam(key = "company", message="商户", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result dailyList(String company, String starttime, String endtime, String sort) {
		InputParams params = new InputParams("systemStatisticsCompanyService", "dailyList");
		params.addParams("company", null, company);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 商户统计
	 * @param starttime
	 * @param endtime
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/companyList", method = RequestMethod.GET)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result companyList(String starttime, String endtime, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("systemStatisticsCompanyService", "companyList");
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 按日统计导出
	 * @param company
	 * @param starttime
	 * @param endtime
	 * @param sort
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/dailyExport", method = RequestMethod.GET)
	@ActionParam(key = "company", message="商户", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public ModelAndView dailyExport(String company, String starttime, String endtime, String sort, 
			HttpServletRequest request, HttpServletResponse response) {
		InputParams params = new InputParams("systemStatisticsCompanyService", "dailyList");
		params.addParams("company", null, company);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("sort", null, sort);
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		Map<String, Object> dataMap = JSONUtils.json2map(jsonStr);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "用户充值审核记录");
			XSSFRow row = s.createRow(0);
			String title[] = {"统计日期","注资总额","注资手续费","结算总额","结算手续费","充值总额","充值手续费","提现总额","提现手续费","总手续费"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			
			List<Object> dailyDateList = (List) dataMap.get("dailyDate");
			List<Object> companyRechargeAmountList = (List) dataMap.get(StatisticsCompanyType.COMPANY_RECHARGE + "_amount");
			List<Object> companyWithdrawAmountList = (List) dataMap.get(StatisticsCompanyType.COMPANY_WITHDRAW + "_amount");
			List<Object> userRechargeAmountList = (List) dataMap.get(StatisticsCompanyType.USER_RECHARGE + "_amount");
			List<Object> userWithdrawAmountList = (List) dataMap.get(StatisticsCompanyType.USER_WITHDRAW + "_amount");
			List<Object> companyRechargePoundageList = (List) dataMap.get(StatisticsCompanyType.COMPANY_RECHARGE + "_poundage");
			List<Object> companyWithdrawPoundageList = (List) dataMap.get(StatisticsCompanyType.COMPANY_WITHDRAW + "_poundage");
			List<Object> userRechargePoundageList = (List) dataMap.get(StatisticsCompanyType.USER_RECHARGE + "_poundage");
			List<Object> userWithdrawPoundageList = (List) dataMap.get(StatisticsCompanyType.USER_WITHDRAW + "_poundage");
			int t = 0;
			for(Object dailyDate : dailyDateList){
				t++;
				row = s.createRow(t);
				BigDecimal companyRechargePoundage = (BigDecimal)companyRechargePoundageList.get(t-1);
				BigDecimal companyWithdrawPoundage = (BigDecimal)companyWithdrawPoundageList.get(t-1);
				BigDecimal userRechargePoundage = (BigDecimal)userRechargePoundageList.get(t-1);
				BigDecimal userWithdrawPoundage = (BigDecimal)userWithdrawPoundageList.get(t-1);
				BigDecimal totalPoundage = companyRechargePoundage.add(companyWithdrawPoundage).add(userRechargePoundage).add(userWithdrawPoundage);
				
				String tInfo[] = {dailyDate.toString(), companyRechargeAmountList.get(t-1).toString(), companyRechargePoundage.toString(), 
						companyWithdrawAmountList.get(t-1).toString(), companyWithdrawPoundage.toString(), 
						userRechargeAmountList.get(t-1).toString(), userRechargePoundage.toString(), 
						userWithdrawAmountList.get(t-1).toString(), userWithdrawPoundage.toString(), totalPoundage.toString()};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=daily.xlsx");
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
	
	/**
	 * 商户统计导出
	 * @param starttime
	 * @param endtime
	 * @param sort
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/companyExport", method = RequestMethod.GET)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public ModelAndView companyExport(String starttime, String endtime, String sort,
			HttpServletRequest request, HttpServletResponse response) {
		InputParams params = new InputParams("systemStatisticsCompanyService", "companyList");
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("sort", null, sort);
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<Map> dataList = JSONUtils.json2list(jsonStr, Map.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "用户充值审核记录");
			XSSFRow row = s.createRow(0);
			String title[] = {"商户ID","商户名称","商户状态","注资总额","充值总额","代付总额","结算总额","总手续费","开户日期"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			
			int t = 0;
			for(Map<String, Object> data : dataList){
				t++;
				row = s.createRow(t);
				
				String status = "";
				if(CompanyStatus.NORMAL.equals(data.get("status").toString())){
					status = "正常";
				}else if(CompanyStatus.DISABLE.equals(data.get("status").toString())){
					status = "停用";
				}
				BigDecimal companyRechargePoundage = (BigDecimal)data.get("company_recharge_poundage");
				BigDecimal companyWithdrawPoundage = (BigDecimal)data.get("user_recharge_poundage");
				BigDecimal userRechargePoundage = (BigDecimal)data.get("user_withdraw_poundage");
				BigDecimal userWithdrawPoundage = (BigDecimal)data.get("company_withdraw_poundage");
				BigDecimal totalPoundage = companyRechargePoundage.add(companyWithdrawPoundage).add(userRechargePoundage).add(userWithdrawPoundage);
				String createtime = Utlity.timestampFormat(new Timestamp((Long)data.get("createtime")), "yyyy-MM-dd");
				
				String tInfo[] = {data.get("code").toString(), data.get("name").toString(), status, 
						data.get("company_recharge_amount").toString(), data.get("user_recharge_amount").toString(), 
						data.get("user_withdraw_amount").toString(), data.get("company_withdraw_amount").toString(), 
						totalPoundage.toString(), createtime};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=company.xlsx");
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
