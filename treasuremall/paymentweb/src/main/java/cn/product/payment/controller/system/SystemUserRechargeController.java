/**
 * 
 */
package cn.product.payment.controller.system;

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

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;
import cn.product.payment.entity.UserRecharge.UserRechargeStatus;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.Utlity;
import cn.product.payment.vo.system.UserRechargeVO;


/**
 * 用户充值
 */

@Controller
@RequestMapping(value = "/system/userRecharge")
public class SystemUserRechargeController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7597448563331648203L;

	/**
	 * 根据条件查询
	 * @param company
	 * @param channel
	 * @param companyChannel
	 * @param channelAccount
	 * @param orderNum
	 * @param companyOrderNum
	 * @param transData
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "company", message="商户", type = DataType.STRING)
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING)
	@ActionParam(key = "companyChannel", message="商户渠道", type = DataType.STRING)
	@ActionParam(key = "channelAccount", message="渠道账户", type = DataType.STRING)
	@ActionParam(key = "orderNum", message="平台订单号", type = DataType.STRING)
	@ActionParam(key = "companyOrderNum", message="商户订单号", type = DataType.STRING)
	@ActionParam(key = "transData", message="充值信息", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String company, String channel, String companyChannel, String channelAccount, String orderNum, String companyOrderNum, 
			String transData, String status, String starttime, String endtime, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("systemUserRechargeService", "list");
		params.addParams("company", null, company);
		params.addParams("channel", null, channel);
		params.addParams("companyChannel", null, companyChannel);
		params.addParams("channelAccount", null, channelAccount);
		params.addParams("orderNum", null, orderNum);
		params.addParams("companyOrderNum", null, companyOrderNum);
		params.addParams("transData", null, transData);
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
		InputParams params = new InputParams("systemUserRechargeService", "get");
		params.addParams("uuid", null, uuid);
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
	@ActionParam(key = "reason", message="失败原因", type = DataType.STRING, required = true, maxLength = 100)
	@ResponseBody
	public Result fail(String uuid, String reason) {
		InputParams params = new InputParams("systemUserRechargeService", "fail");
		params.addParams("uuid", null, uuid);
		params.addParams("reason", null, reason);
		params.addParams("admin", null, getSystemAdmin());
		return this.execute(params);
	}
	
	/**
	 * 充值完成
	 * @param uuid
	 * @param proof
	 * @return
	 */
	@RequestMapping(value = "/success", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "proof", message="凭证", type = DataType.STRING, required = true, maxLength = 100)
	@ResponseBody
	public Result success(String uuid, String proof) {
		InputParams params = new InputParams("systemUserRechargeService", "success");
		params.addParams("uuid", null, uuid);
		params.addParams("proof", null, proof);
		params.addParams("admin", null, getSystemAdmin());
		return this.execute(params);
	}
	
	/**
	 * 关闭订单
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result close(String uuid) {
		InputParams params = new InputParams("systemUserRechargeService", "close");
		params.addParams("uuid", null, uuid);
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
	
	/**
	 * 导出用户充值记录Excel
	 * @param company
	 * @param channel
	 * @param orderNum
	 * @param companyOrderNum
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ActionParam(key = "company", message="商户", type = DataType.STRING)
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING)
	@ActionParam(key = "orderNum", message="平台订单号", type = DataType.STRING)
	@ActionParam(key = "companyOrderNum", message="商户订单号", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ResponseBody
	public ModelAndView export(String company, String channel, String orderNum, String companyOrderNum, String status, String starttime, String endtime, 
			HttpServletRequest request, HttpServletResponse response) {
		InputParams params = new InputParams("systemUserRechargeService", "list");
		params.addParams("company", null, company);
		params.addParams("channel", null, channel);
		params.addParams("orderNum", null, orderNum);
		params.addParams("companyOrderNum", null, companyOrderNum);
		params.addParams("status", null, status);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<UserRechargeVO> voList = JSONUtils.json2list(jsonStr, UserRechargeVO.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "用户充值审核记录");
			XSSFRow row = s.createRow(0);
			String title[] = {"交易类型","平台订单号","商户名称","商户编码","商户订单号","充值渠道","渠道账户","渠道账户号码","充值总额","手续费","实际金额",
					"充值信息","订单状态","发起时间","处理人","处理时间","失败原因","交易凭证"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			
			int t = 0;
			for(UserRechargeVO vo : voList){
				t++;
				row = s.createRow(t);
				
				String tradeType = "用户充值";
				String dOrderNum = vo.getOrderNum();
				String companyName = vo.getCompanyName();
				String companyCode = vo.getCompanyCode();
				String dCompanyOrderNum = vo.getCompanyOrderNum();
				String channelName = vo.getChannelName();
				String channelAccountName = Utlity.stringValue(vo.getChannelAccountName());
				String channelAccountNum = Utlity.stringValue(vo.getChannelAccountNum());
				
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
				if(UserRechargeStatus.NORMAL.equals(vo.getStatus())){
					dStatus = "待处理";
				}else if(UserRechargeStatus.CHECKING.equals(vo.getStatus())){
					dStatus = "待审核";
				}else if(UserRechargeStatus.CHECKED.equals(vo.getStatus())){
					dStatus = "已审核";
				}else if(UserRechargeStatus.SUCCESS.equals(vo.getStatus())){
					dStatus = "已完成";
				}else if(UserRechargeStatus.FAIL.equals(vo.getStatus())){
					dStatus = "处理失败";
				}else if(UserRechargeStatus.CLOSE.equals(vo.getStatus())){
					dStatus = "已关闭";
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
						totalAccount,poundage,amount,transData,dStatus,creattime,operator,operattime,failReason,proof};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=UserRecharge.xlsx");
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
