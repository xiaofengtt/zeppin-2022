/**
 * 
 */
package cn.product.treasuremall.controller.back;

import java.io.IOException;
import java.io.OutputStream;
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

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.controller.BaseController;

@Controller
@RequestMapping(value = "/back/userDaily")
public class UserDailyController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4396484401747939768L;

	/**
	 * 统计列表
	 * @param starttime
	 * @param endtime
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING, maxLength = 10)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING, maxLength = 10)
	@ResponseBody
	public Result list(String starttime, String endtime) {
		
		InputParams params = new InputParams("userDailyService", "list");
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		return this.execute(params);
	}
	
	/**
	 * 统计列表
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING, maxLength = 10)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING, maxLength = 10)
	@ResponseBody
	public Result export(String starttime, String endtime, HttpServletRequest request, HttpServletResponse response) {
		
		InputParams params = new InputParams("userDailyService", "list");
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		List<Map<String, Object>> dataList = (List<Map<String, Object>>)result.getData();
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "每日统计");
			XSSFRow row = s.createRow(0);
			String title[] = { "日期", "注册人数", "首充人数", "日活人数"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(Map<String, Object> data : dataList){
				t++;
				row = s.createRow(t);
				
				String tInfo[] = {data.get("dailyDate").toString(), data.get("regist").toString(), data.get("first").toString(), 
						data.get("active").toString()};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=userDaily.xlsx");
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
