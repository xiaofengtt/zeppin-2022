/**
 * 
 */
package cn.product.worldmall.controller.back;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.entity.StatisticsFinanceDaily;
import cn.product.worldmall.controller.BaseController;

@Controller
@RequestMapping(value = "/back/statisticsFinanceDaily")
public class StatisticsFinanceDailyController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5721417872429694993L;

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
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ResponseBody
	public Result list(String starttime, String endtime, Integer pageNum, Integer pageSize) {
		
		InputParams params = new InputParams("statisticsFinanceDailyService", "list");
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
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
		
		InputParams params = new InputParams("statisticsFinanceDailyService", "list");
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		List<StatisticsFinanceDaily> dataList = (List<StatisticsFinanceDaily>)result.getData();
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "每日统计");
			XSSFRow row = s.createRow(0);
			String title[] = { "日期", "消耗/金币", "充值/元", "提现/元", "中奖/元", "总支出/元", "实物领取/元"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(StatisticsFinanceDaily data : dataList){
				t++;
				row = s.createRow(t);
				
				String tInfo[] = {data.getDailyDate(), data.getdPayment().setScale(2).toString(), data.getRecharge().setScale(2).toString(), 
						data.getWithdraw().setScale(2).toString(), data.getWinning().setScale(2).toString(), 
						data.getDelivery().add(data.getWithdraw()).setScale(2).toString(), data.getDelivery().setScale(2).toString()};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=statisticsDaily.xlsx");
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
