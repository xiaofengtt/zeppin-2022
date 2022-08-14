/**
 * 
 */
package com.product.worldpay.controller.system;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import com.product.worldpay.entity.UserWithdraw.UserWithdrawStatus;
import com.product.worldpay.util.JSONUtils;
import com.product.worldpay.util.Utlity;
import com.product.worldpay.vo.system.UserWithdrawVO;


/**
 * 用户提现
 */

@Controller
@RequestMapping(value = "/system/userWithdraw")
public class SystemUserWithdrawController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6140693249241767203L;

	/**
	 * 根据条件查询
	 * @param company
	 * @param channel
	 * @param companyChannel
	 * @param channelAccount
	 * @param orderNum
	 * @param companyOrderNum
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "company", message="company", type = DataType.STRING)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ActionParam(key = "companyChannel", message="company channel", type = DataType.STRING)
	@ActionParam(key = "channelAccount", message="channel account", type = DataType.STRING)
	@ActionParam(key = "orderNum", message="order number", type = DataType.STRING)
	@ActionParam(key = "companyOrderNum", message="company order number", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "starttime", message="start time", type = DataType.STRING)
	@ActionParam(key = "endtime", message="end time", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="page number", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="page size", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String company, String channel, String companyChannel, String channelAccount, String orderNum, String companyOrderNum,
			String status, String starttime, String endtime, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("systemUserWithdrawService", "list");
		params.addParams("company", null, company);
		params.addParams("channel", null, channel);
		params.addParams("companyChannel", null, companyChannel);
		params.addParams("channelAccount", null, channelAccount);
		params.addParams("orderNum", null, orderNum);
		params.addParams("companyOrderNum", null, companyOrderNum);
		params.addParams("status", null, status);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
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
		InputParams params = new InputParams("systemUserWithdrawService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 获取可用渠道账户列表
	 * @param companyChannel
	 * @return
	 */
	@RequestMapping(value = "/channelAccountList", method = RequestMethod.GET)
	@ActionParam(key = "companyChannel", message="company channel", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result channelAccountList(String companyChannel) {
		InputParams params = new InputParams("systemUserWithdrawService", "channelAccountList");
		params.addParams("companyChannel", null, companyChannel);
		return this.execute(params);
	}
	
	
	/**
	 * 审核
	 * @param uuid
	 * @param channelAccount
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "channelAccount", message="channel account", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result check(String uuid, String channelAccount) {
		InputParams params = new InputParams("systemUserWithdrawService", "check");
		params.addParams("uuid", null, uuid);
		params.addParams("channelAccount", null, channelAccount);
		params.addParams("admin", null, getSystemAdmin());
		return this.execute(params);
	}
	
	/**
	 * 设为失败
	 * @param uuid
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/fail", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "reason", message="fail reason", type = DataType.STRING, required = true, maxLength = 100)
	@ResponseBody
	public Result fail(String uuid, String reason) {
		InputParams params = new InputParams("systemUserWithdrawService", "fail");
		params.addParams("uuid", null, uuid);
		params.addParams("reason", null, reason);
		params.addParams("admin", null, getSystemAdmin());
		return this.execute(params);
	}
	
	/**
	 * 提现完成
	 * @param uuid
	 * @param proof
	 * @return
	 */
	@RequestMapping(value = "/success", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "proof", message="proof", type = DataType.STRING, required = true, maxLength = 100)
	@ResponseBody
	public Result success(String uuid, String proof) {
		InputParams params = new InputParams("systemUserWithdrawService", "success");
		params.addParams("uuid", null, uuid);
		params.addParams("proof", null, proof);
		params.addParams("admin", null, getSystemAdmin());
		return this.execute(params);
	}
	
	/**
	 * 取待处理数量
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/typeList", method = RequestMethod.GET)
	@ResponseBody
	public Result typeList() {
		InputParams params = new InputParams("systemUserRechargeService", "typeList");
		return this.execute(params);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ActionParam(key = "company", message="company", type = DataType.STRING)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ActionParam(key = "orderNum", message="order number", type = DataType.STRING)
	@ActionParam(key = "companyOrderNum", message="company order number", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "starttime", message="start time", type = DataType.STRING)
	@ActionParam(key = "endtime", message="end time", type = DataType.STRING)
	@ResponseBody
	public ModelAndView export(String company, String channel, String orderNum, String companyOrderNum, String status, String starttime, String endtime, 
			HttpServletRequest request, HttpServletResponse response) {
		InputParams params = new InputParams("systemUserWithdrawService", "list");
		params.addParams("company", null, company);
		params.addParams("channel", null, channel);
		params.addParams("orderNum", null, orderNum);
		params.addParams("companyOrderNum", null, companyOrderNum);
		params.addParams("status", null, status);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<UserWithdrawVO> voList = JSONUtils.json2list(jsonStr, UserWithdrawVO.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "withdraw order");
			XSSFRow row = s.createRow(0);
			String title[] = {"transcation type","payment order number","store name","store code","company order number","channel",
					"channel account","channel account number","currency","total amount","poundage","really amount","transcation data",
					"order status","submit time","process admin","process time","fail reason","transcation proof"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			
			int t = 0;
			for(UserWithdrawVO vo : voList){
				t++;
				row = s.createRow(t);
				
				String tradeType = "user withdraw";
				String dOrderNum = vo.getOrderNum();
				String companyName = vo.getCompanyName();
				String companyCode = vo.getCompanyCode();
				String dCompanyOrderNum = vo.getCompanyOrderNum();
				String channelName = vo.getChannelName();
				String channelAccountName = Utlity.stringValue(vo.getChannelAccountName());
				String channelAccountNum = Utlity.stringValue(vo.getChannelAccountNum());
				
				String currency = vo.getCurrency();
				String totalAccount = vo.getTotalAmount().divide(new BigDecimal(100)).setScale(2).toString();
				String poundage = vo.getPoundage().divide(new BigDecimal(100)).setScale(2).toString();
				String amount = vo.getAmount().divide(new BigDecimal(100)).setScale(2).toString();
				
				Map<String, Object> dataMap = JSONUtils.json2map(vo.getTransData());
				Map<String, String> titleMap = Utlity.getTransDataTitleMap(vo.getChannelCode());
				String transData = "";
				for(String key : titleMap.keySet()){
					transData = transData + titleMap.get(key)+":"+Utlity.stringValue(dataMap.get(key)) + "\r\n";
				}
				String dStatus = "";
				if(UserWithdrawStatus.CHECKING.equals(vo.getStatus())){
					dStatus = "checking";
				}else if(UserWithdrawStatus.CHECKED.equals(vo.getStatus())){
					dStatus = "checked";
				}else if(UserWithdrawStatus.SUCCESS.equals(vo.getStatus())){
					dStatus = "success";
				}else if(UserWithdrawStatus.FAIL.equals(vo.getStatus())){
					dStatus = "fail";
				}else if(UserWithdrawStatus.CLOSE.equals(vo.getStatus())){
					dStatus = "close";
				}
				
				String creattime = Utlity.timestampToString(vo.getCreatetime());
				String operator = Utlity.stringValue(vo.getOperatorName());
				String operattime = "";
				if(vo.getOperattime() != null){
					operattime = Utlity.timestampToString(vo.getOperattime());
				}
				String failReason = Utlity.stringValue(vo.getFailReason());
				String proof = Utlity.stringValue(vo.getProof());
				
				String tInfo[] = {tradeType,dOrderNum,companyName,companyCode,dCompanyOrderNum,channelName,channelAccountName,channelAccountNum,
						currency,totalAccount,poundage,amount,transData,dStatus,creattime,operator,operattime,failReason,proof};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=UserWithdraw.xlsx");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("Error, Export failed");
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
