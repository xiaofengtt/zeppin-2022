/**
 * 
 */
package cn.product.payment.controller.store;

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
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.entity.UserRecharge.UserRechargeStatus;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.Utlity;
import cn.product.payment.vo.store.UserRechargeVO;


/**
 * 充值订单管理
 */

@Controller
@RequestMapping(value = "/store/userRecharge")
public class StoreUserRechargeController extends BaseController {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1017739353042208206L;

	/**
	 * 根据条件查询
	 * @param channel
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
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING)
	@ActionParam(key = "orderNum", message="平台订单号", type = DataType.STRING)
	@ActionParam(key = "companyOrderNum", message="商户订单号", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String channel, String orderNum, String companyOrderNum, 
			String status, String starttime, String endtime, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("storeUserRechargeService", "list");
		params.addParams("channel", null, channel);
		params.addParams("orderNum", null, orderNum);
		params.addParams("companyOrderNum", null, companyOrderNum);
		params.addParams("status", null, status);
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
		InputParams params = new InputParams("storeUserRechargeService", "get");
		params.addParams("uuid", null, uuid);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
	
	/**
	 * 设为失败
	 * @param uuid
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result close(String uuid, String reason) {
		InputParams params = new InputParams("storeUserRechargeService", "close");
		params.addParams("uuid", null, uuid);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
	
	/**
	 * 商户导出用户充值记录Excel
	 * @param channel
	 * @param orderNum
	 * @param companyOrderNum
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING)
	@ActionParam(key = "orderNum", message="平台订单号", type = DataType.STRING)
	@ActionParam(key = "companyOrderNum", message="商户订单号", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ResponseBody
	public ModelAndView export(String channel, String orderNum, String companyOrderNum, String status, String starttime, String endtime, 
			HttpServletRequest request, HttpServletResponse response) {
		CompanyAdmin admin = getCompanyAdmin();
		
		InputParams params = new InputParams("storeUserRechargeService", "list");
		params.addParams("company", null, admin.getCompany());
		params.addParams("channel", null, channel);
		params.addParams("orderNum", null, orderNum);
		params.addParams("companyOrderNum", null, companyOrderNum);
		params.addParams("status", null, status);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<UserRechargeVO> voList = JSONUtils.json2list(jsonStr, UserRechargeVO.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "用户充值订单");
			XSSFRow row = s.createRow(0);
			String title[] = {"交易类型","平台订单号","商户名称","商户编码","商户订单号","充值渠道","充值总额","手续费","实际金额",
					"充值信息","订单状态","发起时间","处理时间","失败原因"};
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
				String operattime = "";
				if(vo.getOperattime() != null){
					operattime = Utlity.timestampToString(vo.getOperattime());
				}
				String failReason = Utlity.stringValue(vo.getFailReason());
				
				String tInfo[] = {tradeType,dOrderNum,companyName,companyCode,dCompanyOrderNum,channelName,
						totalAccount,poundage,amount,transData,dStatus,creattime,operattime,failReason};
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
