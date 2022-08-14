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
import cn.product.worldmall.entity.FrontUserBuyfreeOrder.FrontUserBuyfreeOrderStatus;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontUserBuyfreeOrderVO;

@Controller
@RequestMapping(value = "/back/userBuyfree")
public class UserBuyfreeController extends BaseController{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2772809914802526848L;

	/**
	 * 获取列表
	 * @param showId
	 * @param goodsIssue
	 * @param goodsId
	 * @param isLuck
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @param bettime
	 * @param buyCount
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "goodsId", message="奖品", type = DataType.STRING)
	@ActionParam(key = "isLuck", message="中奖状态", type = DataType.BOOLEAN)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ActionParam(key = "bettime", message="投注时间", type = DataType.STRING)
	@ActionParam(key = "userType", message="用户类型", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ResponseBody
	public Result list(String showId, String goodsId, String isLuck, Integer pageNum, Integer pageSize, String sort
			, String bettime, String userType, String status){
		InputParams params = new InputParams("userBuyfreeService", "list");
		params.addParams("showId", null, showId);
		params.addParams("goodsId", null, goodsId);
		params.addParams("isLuck", null, isLuck);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("bettime", null, bettime);
		params.addParams("userType", null, userType);
		params.addParams("status", null, status);
		return this.execute(params);
	}
	
	/**
	 * 导出
	 * @param showId
	 * @param goodsId
	 * @param isLuck
	 * @param sort
	 * @param bettime
	 * @param userType
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/export",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "goodsId", message="奖品", type = DataType.STRING)
	@ActionParam(key = "isLuck", message="中奖状态", type = DataType.BOOLEAN)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ActionParam(key = "bettime", message="投注时间", type = DataType.STRING)
	@ActionParam(key = "userType", message="用户类型", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ResponseBody
	public  ModelAndView exportreceive(String showId, String goodsId, String isLuck, String sort
			, String bettime, String userType, String status, HttpServletRequest request, HttpServletResponse response){
		InputParams params = new InputParams("userBuyfreeService", "list");
		params.addParams("showId", null, showId);
		params.addParams("goodsId", null, goodsId);
		params.addParams("isLuck", null, isLuck);
		params.addParams("sort", null, sort);
		params.addParams("bettime", null, bettime);
		params.addParams("userType", null, userType);
		params.addParams("status", null, status);
		

		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<FrontUserBuyfreeOrderVO> voList = JSONUtils.json2list(jsonStr, FrontUserBuyfreeOrderVO.class);
//		List<WinningInfoVO> voList = (List<WinningInfoVO>) result.getData();
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "中奖列表");
			XSSFRow row = s.createRow(0);
			String title[] = { "期号", "奖品", "奖品价值", "中奖人/昵称", "中奖人/ID", "状态", "参与时间", "抽奖号" 
						, "运单号", "快递公司", "派奖时间"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(FrontUserBuyfreeOrderVO vo : voList){
				t++;
				row = s.createRow(t);
				
				String issueNum = String.valueOf(vo.getIssueNum());
				String titleStr = vo.getTitle();
				String price = vo.getPrice().toString();
				String nickname = vo.getNickname();
				String showIdStr = String.valueOf(vo.getFrontUserShowId());
				String statusStr = "未开奖";
				if(FrontUserBuyfreeOrderStatus.FINISHED.equals(vo.getStatus())) {
					statusStr = "已派奖";
				}if(FrontUserBuyfreeOrderStatus.RETURN.equals(vo.getStatus())) {
					statusStr = "退货";
				}if(FrontUserBuyfreeOrderStatus.CONFIRM.equals(vo.getStatus())) {
					statusStr = "确认收货";
				}if(FrontUserBuyfreeOrderStatus.NOWIN.equals(vo.getStatus())) {
					statusStr = "未中奖";
				}if(FrontUserBuyfreeOrderStatus.WIN.equals(vo.getStatus())) {
					statusStr = "未领奖";
				}if(FrontUserBuyfreeOrderStatus.RECEIVE.equals(vo.getStatus())) {
					statusStr = "未派奖";
				}
				String createtimeTimeStr = Utlity.timeSpanToString(vo.getCreatetime());
				String expressNumber = vo.getDetailInfo() == null ? "" : vo.getDetailInfo().getExpressNumber();
				String company = vo.getDetailInfo() == null ? "" : vo.getDetailInfo().getCompany();
				String operattime = vo.getOperattime() == null ? "--" : Utlity.timeSpanToString(vo.getOperattime());
				
				String tInfo[] = { issueNum, titleStr , price, nickname,
						showIdStr, statusStr, createtimeTimeStr, expressNumber, company, operattime};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=buyFreeReceive.xlsx");
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
	 * 派奖
	 * @param uuid
	 * @param company
	 * @param expressNumber
	 * @return
	 */
	@RequestMapping(value="/receive",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "company", message="快递公司", type = DataType.STRING, required = true)
	@ActionParam(key = "expressNumber", message="快递单号", type = DataType.STRING, required = true)
	@ResponseBody
	public Result receive(String uuid, String company, String expressNumber){
		InputParams params = new InputParams("userBuyfreeService", "receive");
		params.addParams("uuid", null, uuid);
		params.addParams("company", null, company);
		params.addParams("expressNumber", null, expressNumber);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 确认收货
	 * @param uuid
	 * @param status 取值confirm/return
	 * @return
	 */
	@RequestMapping(value="/confirm",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result confirm(String uuid, String status){
		InputParams params = new InputParams("userBuyfreeService", "confirm");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 重置收货
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/reset",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result reset(String uuid){
		InputParams params = new InputParams("userBuyfreeService", "reset");
		params.addParams("uuid", null, uuid);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
