/**
 * 
 */
package com.product.worldpay.controller.system;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

import com.product.worldpay.controller.BaseController;
import com.product.worldpay.controller.base.ActionParam;
import com.product.worldpay.controller.base.ActionParam.DataType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;
import com.product.worldpay.util.JSONUtils;

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
	@ActionParam(key = "company", message="company", type = DataType.STRING)
	@ActionParam(key = "starttime", message="start time", type = DataType.STRING)
	@ActionParam(key = "endtime", message="end time", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
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
	 * 按日统计导出
	 * @param company
	 * @param starttime
	 * @param endtime
	 * @param sort
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/dailyExport", method = RequestMethod.GET)
	@ActionParam(key = "company", message="company", type = DataType.STRING)
	@ActionParam(key = "starttime", message="start time", type = DataType.STRING)
	@ActionParam(key = "endtime", message="end time", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
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
		
		List<String> titleList = new ArrayList<String>();
		titleList.add("Date");
		for(String key : dataMap.keySet()){
			if(!"dailyDate".equals(key)){
				titleList.add(key);
			}
		}
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "Statistics");
			XSSFRow row = s.createRow(0);
			for (int j = 0; j < titleList.size(); j++) {
				row.createCell(j).setCellValue(titleList.get(j));
			}
			
			List<Object> dailyDateList = (List) dataMap.get("dailyDate");
			int t = 0;
			for(Object dailyDate : dailyDateList){
				t++;
				row = s.createRow(t);
				List<String> tInfoList = new ArrayList<String>();
				tInfoList.add(dailyDate.toString());
				
				for(String title: titleList){
					if(!"Date".equals(title)){
						List<Object> dataList = (List<Object>) dataMap.get(title);
						tInfoList.add(dataList.get(t-1).toString());
					}
				}
				for (int j = 0; j < tInfoList.size(); j++) {
					row.createCell(j).setCellValue(tInfoList.get(j));
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=daily.xlsx");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("ERROR！");
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
