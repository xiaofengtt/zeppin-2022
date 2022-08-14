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
import cn.product.worldmall.entity.FrontUserCheckinHistory.FrontUserCheckinHistoryStatus;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontUserCheckinHistoryVO;

@Controller
@RequestMapping(value = "/back/userCheckin")
public class UserCheckinController extends BaseController{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3899444617065082854L;

	/**
	 * 获取列表
	 * @param showId
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @param bettime
	 * @param userType
	 * @param status
	 * @param prizeType
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ActionParam(key = "userType", message="用户类型", type = DataType.STRING)
	@ActionParam(key = "bettime", message="投注时间", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "prizeType", message="奖品类型", type = DataType.STRING)
	@ResponseBody
	public Result list(String showId, Integer pageNum, Integer pageSize, String sort
			, String bettime, String userType, String status, String prizeType){
		InputParams params = new InputParams("userCheckinService", "list");
		params.addParams("showId", null, showId);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("bettime", null, bettime);
		params.addParams("userType", null, userType);
		params.addParams("status", null, status);
		params.addParams("prizeType", null, prizeType);
		return this.execute(params);
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
		InputParams params = new InputParams("userCheckinService", "receive");
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
		InputParams params = new InputParams("userCheckinService", "confirm");
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
		InputParams params = new InputParams("userCheckinService", "reset");
		params.addParams("uuid", null, uuid);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
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
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ActionParam(key = "userType", message="用户类型", type = DataType.STRING)
	@ActionParam(key = "bettime", message="投注时间", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "prizeType", message="奖品类型", type = DataType.STRING)
	@ResponseBody
	public  ModelAndView exportreceive(String showId, Integer pageNum, Integer pageSize, String sort
			, String bettime, String userType, String status, String prizeType
			, HttpServletRequest request, HttpServletResponse response){
		InputParams params = new InputParams("userCheckinService", "list");
		params.addParams("showId", null, showId);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("bettime", null, bettime);
		params.addParams("userType", null, userType);
		params.addParams("status", null, status);
		params.addParams("prizeType", null, prizeType);
		

		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<FrontUserCheckinHistoryVO> voList = JSONUtils.json2list(jsonStr, FrontUserCheckinHistoryVO.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "中奖列表");
			XSSFRow row = s.createRow(0);
			String title[] = { "序号", "签到人/昵称", "签到人/ID", "签到人/手机号", "奖品", "抽奖时间", "领奖状态" 
						, "运单号", "快递公司", "派奖时间"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(FrontUserCheckinHistoryVO vo : voList){
				t++;
				row = s.createRow(t);
				
				String titleStr = vo.getPrizeTitle();
				String nickname = vo.getNickname();
				String showIdStr = String.valueOf(vo.getFrontUserShowId());
				String mobile = vo.getMobile();
				String statusStr = "未领奖";
				if(FrontUserCheckinHistoryStatus.FINISHED.equals(vo.getStatus())) {
					statusStr = "已派奖";
				}if(FrontUserCheckinHistoryStatus.RETURN.equals(vo.getStatus())) {
					statusStr = "退货";
				}if(FrontUserCheckinHistoryStatus.CONFIRM.equals(vo.getStatus())) {
					statusStr = "确认收货";
				}if(FrontUserCheckinHistoryStatus.RECEIVE.equals(vo.getStatus())) {
					statusStr = "未派奖";
				}
				String createtimeTimeStr = Utlity.timeSpanToString(vo.getCreatetime());
				String expressNumber = vo.getDetailInfo() == null ? "" : vo.getDetailInfo().getExpressNumber();
				String company = vo.getDetailInfo() == null ? "" : vo.getDetailInfo().getCompany();
				String operattime = vo.getOperattime() == null ? "--" : Utlity.timeSpanToString(vo.getOperattime());
				
				String tInfo[] = { t+"", nickname,
						showIdStr, mobile, titleStr, createtimeTimeStr, statusStr, expressNumber, company, operattime};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=userCheckinReceive.xlsx");
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
