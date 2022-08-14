/**
 * 
 */
package cn.product.payment.controller.store;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import org.springframework.web.servlet.ModelAndView;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;
import cn.product.payment.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.Utlity;
import cn.product.payment.vo.store.CompanyAccountHistoryVO;


/**
 * 商户流水
 */

@Controller
@RequestMapping(value = "/store/companyAccountHistory")
public class StoreCompanyAccountHistoryController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6815379317358657293L;

	/**
	 * 根据条件查询
	 * @param channel
	 * @param companyChannel
	 * @param orderNum
	 * @param companyOrderNum
	 * @param type
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING)
	@ActionParam(key = "companyChannel", message="商户渠道", type = DataType.STRING)
	@ActionParam(key = "orderNum", message="平台订单号", type = DataType.STRING)
	@ActionParam(key = "companyOrderNum", message="商户订单号", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String channel, String companyChannel, String orderNum, String companyOrderNum, 
			String type, String starttime, String endtime, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("storeCompanyAccountHistoryService", "list");
		params.addParams("channel", null, channel);
		params.addParams("companyChannel", null, companyChannel);
		params.addParams("orderNum", null, orderNum);
		params.addParams("companyOrderNum", null, companyOrderNum);
		params.addParams("type", null, type);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
	
	/**
	 * 获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		InputParams params = new InputParams("storeCompanyAccountHistoryService", "get");
		params.addParams("uuid", null, uuid);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
	
	
	/**
	 * 商户导出交易记录Excel
	 * @param channel
	 * @param orderNum
	 * @param companyOrderNum
	 * @param type
	 * @param starttime
	 * @param endtime
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING)
	@ActionParam(key = "orderNum", message="平台订单号", type = DataType.STRING)
	@ActionParam(key = "companyOrderNum", message="商户订单号", type = DataType.STRING)
	@ActionParam(key = "type", message="交易类型", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ResponseBody
	public ModelAndView export(String channel, String orderNum, String companyOrderNum, String type, String starttime, String endtime, 
			HttpServletRequest request, HttpServletResponse response) {
		InputParams params = new InputParams("storeCompanyAccountHistoryService", "list");
		params.addParams("channel", null, channel);
		params.addParams("orderNum", null, orderNum);
		params.addParams("companyOrderNum", null, companyOrderNum);
		params.addParams("type", null, type);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<CompanyAccountHistoryVO> voList = JSONUtils.json2list(jsonStr, CompanyAccountHistoryVO.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "商户流水");
			XSSFRow row = s.createRow(0);
			String title[] = {"交易类型","平台订单号","商户名称","商户编码","商户订单号","交易渠道","交易金额","手续费","商户余额","发起时间","完成时间"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			
			int t = 0;
			for(CompanyAccountHistoryVO vo : voList){
				t++;
				row = s.createRow(t);
				
				String tradeType = "";
				if(CompanyAccountHistoryType.USER_RECHARGE.equals(vo.getType())){
					tradeType = "用户支付";
				}else if(CompanyAccountHistoryType.USER_WITHDRAW.equals(vo.getType())){
					tradeType = "用户代付";
				}else if(CompanyAccountHistoryType.COMPANY_RECHARGE.equals(vo.getType())){
					tradeType = "商户注资";
				}else if(CompanyAccountHistoryType.COMPANY_WITHDRAW.equals(vo.getType())){
					tradeType = "商户结算";
				}
				String dOrderNum = vo.getOrderNum();
				String companyName = vo.getCompanyName();
				String companyCode = vo.getCompanyCode();
				String dCompanyOrderNum = vo.getCompanyOrderNum();
				String channelName = vo.getChannelName();
				
				String poundage = vo.getPoundage().divide(new BigDecimal(100)).setScale(2).toString();
				String amount = vo.getAmount().divide(new BigDecimal(100)).setScale(2).toString();
				String balance = vo.getBalance().divide(new BigDecimal(100)).setScale(2).toString();
				
				String submittime = Utlity.timestampToString(vo.getSubmittime());
				String creattime = Utlity.timestampToString(vo.getCreatetime());
				
				String tInfo[] = {tradeType,dOrderNum,companyName,companyCode,dCompanyOrderNum,channelName,
						amount,poundage,balance,submittime,creattime};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=CompanyAccountHistory.xlsx");
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
