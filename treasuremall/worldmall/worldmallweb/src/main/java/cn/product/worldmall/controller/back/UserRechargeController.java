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
import org.springframework.web.servlet.ModelAndView;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderStatus;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontUserRechargeOrderVO;

@Controller
@RequestMapping(value = "/back/userRecharge")
public class UserRechargeController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2349139947940383233L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("userRechargeService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "orderNum", message="订单号", type = DataType.STRING)
	@ActionParam(key = "frontUserShowId", message="用户Id", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String orderNum, String frontUserShowId, String starttime, String endtime, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("userRechargeService", "list");
		params.addParams("orderNum", null, orderNum);
		params.addParams("frontUserShowId", null, frontUserShowId);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 手工处理充值订单
	 * @param uuid
	 * @param status
	 * @param remark
	 * @return
	 */
	@RequestMapping(value="/statusSetting",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ResponseBody
	public Result statusSetting(String uuid, String status, String remark){
		InputParams params = new InputParams("userRechargeService", "statusSetting");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("remark", null, remark);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ActionParam(key = "orderNum", message="订单号", type = DataType.STRING)
	@ActionParam(key = "frontUserShowId", message="用户Id", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public ModelAndView export(String orderNum, String frontUserShowId, String starttime, String endtime, String status, 
			String sort, HttpServletRequest request, HttpServletResponse response) {
		InputParams params = new InputParams("userRechargeService", "list");
		params.addParams("orderNum", null, orderNum);
		params.addParams("frontUserShowId", null, frontUserShowId);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("status", null, status);
		params.addParams("sort", null, sort);
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<FrontUserRechargeOrderVO> voList = JSONUtils.json2list(jsonStr, FrontUserRechargeOrderVO.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "用户充值记录");
			XSSFRow row = s.createRow(0);
			String title[] = {"操作类型","订单号","用户ID", "用户昵称","用户余额","充值法币","充值金币",
					"充值渠道","充值账户","交易数据","备注","状态","处理人","处理时间","提现时间"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			
			int t = 0;
			for(FrontUserRechargeOrderVO vo : voList){
				t++;
				row = s.createRow(t);
				
				String type = "用户充值";
				String dOrderNum = String.valueOf(vo.getOrderNum());
				String showId = String.valueOf(vo.getFrontUserShowId());
				String nickname = vo.getFrontUserNickname();
				String balance = vo.getFrontUserBalance().add(vo.getFrontUserBalanceLock()).toString();
				
				String increaseDAmount = vo.getIncreaseDAmount().toString();
				String amount = vo.getAmount().toString();
				
				String capitalPlatform = vo.getCapitalPlatform();
				String capitalAccount = vo.getAccountName();
				
				String transData = vo.getTransData();
				String remark = vo.getRemark();
				String dStatus = "";
				if(FrontUserRechargeOrderStatus.NORMAL.equals(vo.getStatus())){
					dStatus = "待审核";
				}else if(FrontUserRechargeOrderStatus.CHECKING.equals(vo.getStatus())){
					dStatus = "审核中";
				}else if(FrontUserRechargeOrderStatus.CHECKED.equals(vo.getStatus())){
					dStatus = "已完成";
				}else if(FrontUserRechargeOrderStatus.CANCEL.equals(vo.getStatus())){
					dStatus = "已取消";
				}else if(FrontUserRechargeOrderStatus.CLOSE.equals(vo.getStatus())){
					dStatus = "已关闭";
				}
				
				String operator = vo.getOperatorName() == null ? "--" : vo.getOperatorName();
				String operattime = vo.getOperattime() == null ? "--" :Utlity.timeSpanToString(vo.getOperattime());
				String creattime = Utlity.timeSpanToString(vo.getCreatetime());
				
				String tInfo[] = {type,dOrderNum,showId,nickname,balance,increaseDAmount,amount,
						capitalPlatform,capitalAccount,transData,remark,dStatus,operator,operattime,creattime};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=frontUserRecharge.xlsx");
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
