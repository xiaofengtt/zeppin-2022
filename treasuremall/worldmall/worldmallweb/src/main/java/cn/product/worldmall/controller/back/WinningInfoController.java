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
import cn.product.worldmall.entity.WinningInfo.WinningInfoType;
import cn.product.worldmall.entity.WinningInfoReceive.WinningInfoReceiveStatus;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.WinningInfoVO;

@Controller
@RequestMapping(value = "/back/winningInfo")
public class WinningInfoController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1816213185875075799L;

	/**
	 * 中奖列表
	 * @param showId
	 * @param type
	 * @param goodsIssue
	 * @param goodsId
	 * @param winningTime
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "type", message="领奖类型", type = DataType.STRING)
	@ActionParam(key = "goodsIssue", message="奖品抽奖ID", type = DataType.STRING)
	@ActionParam(key = "goodsI", message="奖品ID", type = DataType.STRING)
	@ActionParam(key = "winningTime", message="开奖时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ActionParam(key = "userType", message="用户类型", type = DataType.STRING)
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING)
	@ResponseBody
	public Result list(Integer showId, String type, String goodsIssue, String goodsId, String winningTime
			, Integer pageNum, Integer pageSize, String sort, String userType, String gameType){
		InputParams params = new InputParams("winningInfoService", "list");
		params.addParams("showId", null, showId);
		params.addParams("type", null, type);
		params.addParams("winningTime", null, winningTime);
		params.addParams("goodsIssue", null, goodsIssue);
		params.addParams("goodsId", null, goodsId);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("userType", null, userType);
		params.addParams("gameType", null, gameType);
		return this.execute(params);
	}
	
	/**
	 * 导出中奖列表
	 * @param showId
	 * @param type
	 * @param goodsIssue
	 * @param goodsId
	 * @param winningTime
	 * @param sort
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/exportlist",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "type", message="领奖类型", type = DataType.STRING)
	@ActionParam(key = "goodsIssue", message="奖品抽奖ID", type = DataType.STRING)
	@ActionParam(key = "goodsId", message="奖品ID", type = DataType.STRING)
	@ActionParam(key = "winningTime", message="开奖时间", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ActionParam(key = "userType", message="用户类型", type = DataType.STRING)
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING)
	@ResponseBody
	public ModelAndView exportlist(Integer showId, String type, String goodsIssue, String goodsId, String winningTime, String sort, String userType, String gameType
			, HttpServletRequest request, HttpServletResponse response){
		InputParams params = new InputParams("winningInfoService", "list");
		params.addParams("showId", null, showId);
		params.addParams("type", null, type);
		params.addParams("winningTime", null, winningTime);
		params.addParams("goodsIssue", null, goodsIssue);
		params.addParams("goodsId", null, goodsId);
		params.addParams("sort", null, sort);
		params.addParams("userType", null, userType);
		params.addParams("gameType", null, gameType);
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<WinningInfoVO> voList = JSONUtils.json2list(jsonStr, WinningInfoVO.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "中奖列表");
			XSSFRow row = s.createRow(0);
			String title[] = { "期号", "奖品", "奖品价值", "开始时间", "开奖时间", "幸运号", "中奖人/昵称", "中奖人/ID", "投注量", "是否马甲"
					, "领奖IP", "领奖类型", "领奖时间"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(WinningInfoVO vo : voList){
				t++;
				row = s.createRow(t);
				
				String issueNum = String.valueOf(vo.getIssueNum());
				String titleStr = vo.getTitle();
				String price = vo.getPrice().toString();
				String starttime = Utlity.timeSpanToString(vo.getStarttime());
				String finishedtime = Utlity.timeSpanToString(vo.getFinishedtime());
				String lucknum = String.valueOf(vo.getLuckynum());
				String nickname = vo.getNickname();
				String showIdStr = String.valueOf(vo.getShowId());
				String paymentDAmount = vo.getPaymentDAmount().toString();
				String isRobot = vo.getIsRobot() ? "是" : "否";
				String ip = vo.getIp() == null ? "--" : vo.getIp();
				String typeStr = "未领奖";
				if(WinningInfoType.ENTITY.equals(vo.getType())) {
					typeStr = "实物";
				} else if(WinningInfoType.GOLD.equals(vo.getType())) {
					typeStr = "金币";
				}
				String receiveTime = vo.getCreatetime() == null ? "--" : Utlity.timeSpanToString(vo.getCreatetime());
				
				String tInfo[] = { issueNum, titleStr , price, starttime, finishedtime, lucknum, nickname,
						showIdStr, paymentDAmount, isRobot,ip, typeStr, receiveTime};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=winningInfo.xlsx");
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
	 * 派奖列表
	 * @param showId
	 * @param source
	 * @param name
	 * @param receiveTime
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/receivelist",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "source", message="奖品来源", type = DataType.STRING)
	@ActionParam(key = "name", message="奖品名称", type = DataType.STRING)
	@ActionParam(key = "receiveTime", message="领奖时间", type = DataType.STRING)
	@ActionParam(key = "status", message="领奖状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING)
	@ResponseBody
	public Result receivelist(String showId, String source, String name, String receiveTime, String status, String gameType
			, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("winningInfoService", "receivelist");
		params.addParams("showId", null, showId);
		params.addParams("source", null, source);
		params.addParams("name", null, name);
		params.addParams("receiveTime", null, receiveTime);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("gameType", null, gameType);
		return this.execute(params);
	}
	
	/**
	 * 导出派奖列表
	 * @param showId
	 * @param source
	 * @param name
	 * @param receiveTime
	 * @param status
	 * @param sort
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/exportreceive",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "source", message="奖品来源", type = DataType.STRING)
	@ActionParam(key = "name", message="奖品名称", type = DataType.STRING)
	@ActionParam(key = "receiveTime", message="领奖时间", type = DataType.STRING)
	@ActionParam(key = "status", message="领奖状态", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING)
	@ResponseBody
	public  ModelAndView exportreceive(String showId, String source, String name, String receiveTime, String status, String gameType
			, String sort, HttpServletRequest request, HttpServletResponse response){
		InputParams params = new InputParams("winningInfoService", "receivelist");
		params.addParams("showId", null, showId);
		params.addParams("source", null, source);
		params.addParams("name", null, name);
		params.addParams("receiveTime", null, receiveTime);
		params.addParams("status", null, status);
		params.addParams("sort", null, sort);
		params.addParams("gameType", null, gameType);
		

		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<WinningInfoVO> voList = JSONUtils.json2list(jsonStr, WinningInfoVO.class);
//		List<WinningInfoVO> voList = (List<WinningInfoVO>) result.getData();
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "中奖列表");
			XSSFRow row = s.createRow(0);
			String title[] = { "订单号", "奖品", "奖品价值", "中奖人/昵称", "中奖人/ID", "状态", "领奖时间" , "领奖IP"
						, "运单号", "快递公司", "派奖时间"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(WinningInfoVO vo : voList){
				t++;
				row = s.createRow(t);
				
				String orderNum = String.valueOf(vo.getOrderNum());
				String titleStr = vo.getTitle();
				String price = vo.getPrice().toString();
				String nickname = vo.getNickname();
				String showIdStr = String.valueOf(vo.getShowId());
				String statusStr = "未派奖";
				if(WinningInfoReceiveStatus.FINISHED.equals(vo.getStatus())) {
					statusStr = "已派奖";
				}if(WinningInfoReceiveStatus.RETURN.equals(vo.getStatus())) {
					statusStr = "退货";
				}if(WinningInfoReceiveStatus.CONFIRM.equals(vo.getStatus())) {
					statusStr = "确认收货";
				}
				String receiveTimeStr = Utlity.timeSpanToString(vo.getCreatetime());
				String ip = vo.getIp() == null ? "--" : vo.getIp();
				String expressNumber = vo.getDetailInfo() == null ? "" : vo.getDetailInfo().getExpressNumber();
				String company = vo.getDetailInfo() == null ? "" : vo.getDetailInfo().getCompany();
				String operattime = vo.getOperattime() == null ? "--" : Utlity.timeSpanToString(vo.getOperattime());
				
				String tInfo[] = { orderNum, titleStr , price, nickname,
						showIdStr, statusStr, receiveTimeStr,ip, expressNumber, company, operattime};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=winningInfoReceive.xlsx");
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
		InputParams params = new InputParams("winningInfoService", "receive");
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
		InputParams params = new InputParams("winningInfoService", "confirm");
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
		InputParams params = new InputParams("winningInfoService", "reset");
		params.addParams("uuid", null, uuid);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
