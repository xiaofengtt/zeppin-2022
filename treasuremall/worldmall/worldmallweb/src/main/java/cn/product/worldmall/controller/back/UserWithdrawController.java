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
import cn.product.worldmall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.worldmall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontUserWithdrawOrderVO;

@Controller
@RequestMapping(value = "/back/userWithdraw")
public class UserWithdrawController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8492617160143410866L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("userWithdrawService", "get");
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
		InputParams params = new InputParams("userWithdrawService", "list");
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
	
	@RequestMapping(value="/firstCheck",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="审核原因", type = DataType.STRING, required = true)
	@ResponseBody
	public Result firstCheck(String uuid, String status, String remark){
		InputParams params = new InputParams("userWithdrawService", "firstCheck");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("remark", null, remark);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/finalCheck",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="审核原因", type = DataType.STRING, required = true)
	@ResponseBody
	public Result finalCheck(String uuid, String status, String remark){
		InputParams params = new InputParams("userWithdrawService", "finalCheck");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("remark", null, remark);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 管理员手动取消功能
	 * @param uuid
	 * @param status 取值固定为cancel
	 * @param remark
	 * @return
	 */
	@RequestMapping(value="/cancel",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="审核原因", type = DataType.STRING, required = true)
	@ResponseBody
	public Result cancel(String uuid, String status, String remark){
		InputParams params = new InputParams("userWithdrawService", "cancel");
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
		InputParams params = new InputParams("userWithdrawService", "list");
		params.addParams("orderNum", null, orderNum);
		params.addParams("frontUserShowId", null, frontUserShowId);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("status", null, status);
		params.addParams("sort", null, sort);
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<FrontUserWithdrawOrderVO> voList = JSONUtils.json2list(jsonStr, FrontUserWithdrawOrderVO.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "用户提现记录");
			XSSFRow row = s.createRow(0);
			String title[] = {"操作类型","订单号","用户ID", "用户昵称","用户余额","提现金币","提现法币","手续费","实际提现法币",
					"银行卡所属银行","银行卡持卡人","银行卡账号","银行卡开户行","交易数据","备注","状态","处理人","处理时间","提现时间"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			
			int t = 0;
			for(FrontUserWithdrawOrderVO vo : voList){
				t++;
				row = s.createRow(t);
				
				String type = "";
				if(FrontUserWithdrawOrderType.DEDUCT.equals(vo.getType())){
					type = "手动扣除";
				}else{
					type = "用户提现";
				}
				String dOrderNum = String.valueOf(vo.getOrderNum());
				String showId = String.valueOf(vo.getFrontUserShowId());
				String nickname = vo.getFrontUserNickname();
				String balance = vo.getFrontUserBalance().add(vo.getFrontUserBalanceLock()).toString();
				
				String reduceDAmount = vo.getReduceDAmount().toString();
				String amount = vo.getAmount().toString();
				String poundage = vo.getPoundage() == null ? "0" : vo.getPoundage().toString();
				String actualAmount = vo.getActualAmount().toString();
				
				String bankName = vo.getFrontUserBankName();
				String bankcardHolder = vo.getFrontUserAccountHolder();
				String bankcardNum = vo.getFrontUserAccountNumber();
				String branchBank = vo.getFrontUserBranchBank();
				
				String transData = vo.getTransData() == null ? "" : vo.getTransData();
				String remark = vo.getRemark() == null ? "" : vo.getRemark();
				String dStatus = "";
				if(FrontUserWithdrawOrderStatus.NORMAL.equals(vo.getStatus())){
					dStatus = "待审核";
				}else if(FrontUserWithdrawOrderStatus.CHECKING.equals(vo.getStatus())){
					dStatus = "审核中";
				}else if(FrontUserWithdrawOrderStatus.CHECKED.equals(vo.getStatus())){
					dStatus = "已完成";
				}else if(FrontUserWithdrawOrderStatus.CANCEL.equals(vo.getStatus())){
					dStatus = "已取消";
				}else if(FrontUserWithdrawOrderStatus.CLOSE.equals(vo.getStatus())){
					dStatus = "已关闭";
				}
				
				String operator = vo.getOperatorName() == null ? "--" : vo.getOperatorName();
				String operattime = vo.getOperattime() == null ? "--" :Utlity.timeSpanToString(vo.getOperattime());
				String creattime = Utlity.timeSpanToString(vo.getCreatetime());
				
				String tInfo[] = {type,dOrderNum,showId,nickname,balance,reduceDAmount,amount,poundage,actualAmount,
						bankName,bankcardHolder,bankcardNum,branchBank,transData,remark,dStatus,operator,operattime,creattime};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=frontUserWithdraw.xlsx");
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
