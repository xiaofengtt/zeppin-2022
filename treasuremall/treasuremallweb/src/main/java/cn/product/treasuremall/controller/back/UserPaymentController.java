package cn.product.treasuremall.controller.back;

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

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.controller.BaseController;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.FrontUserPaymentOrderVO;

@Controller
@RequestMapping(value = "/back/userPayment")
public class UserPaymentController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8834824086856797681L;

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
	@ActionParam(key = "goodsIssue", message="奖品抽奖ID", type = DataType.STRING)
	@ActionParam(key = "goodsId", message="奖品", type = DataType.STRING)
	@ActionParam(key = "isLuck", message="中奖状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ActionParam(key = "bettime", message="投注时间", type = DataType.STRING)
	@ActionParam(key = "buyCount", message="投注量", type = DataType.STRING)
	@ActionParam(key = "userType", message="用户类型", type = DataType.STRING)
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING)
	@ResponseBody
	public Result list(String showId, String goodsIssue, String goodsId, String isLuck, Integer pageNum, Integer pageSize, String sort
			, String bettime, String buyCount, String userType, String gameType){
		InputParams params = new InputParams("userPaymentService", "list");
		params.addParams("showId", null, showId);
		params.addParams("goodsIssue", null, goodsIssue);
		params.addParams("goodsId", null, goodsId);
		params.addParams("isLuck", null, isLuck);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("bettime", null, bettime);
		params.addParams("buyCount", null, buyCount);
		params.addParams("userType", null, userType);
		params.addParams("gameType", null, gameType);
		return this.execute(params);
	}
	
	/**
	 * 获取统计
	 * @param starttime
	 * @param endtime
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value="/getStatistics",method=RequestMethod.GET)
	@ActionParam(key = "starttime", message="起始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="终止时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result getStatistics(String starttime, String endtime, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("userPaymentService", "getStatistics");
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 批量导出用户投注记录
	 * @param showId
	 * @param goodsIssue
	 * @param goodsId
	 * @param isLuck
	 * @param sort
	 * @param bettime
	 * @param buyCount
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "goodsIssue", message="奖品抽奖ID", type = DataType.STRING)
	@ActionParam(key = "goodsId", message="奖品", type = DataType.STRING)
	@ActionParam(key = "isLuck", message="中奖状态", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ActionParam(key = "bettime", message="投注时间", type = DataType.STRING)
	@ActionParam(key = "buyCount", message="投注量", type = DataType.STRING)
	@ActionParam(key = "userType", message="用户类型", type = DataType.STRING)
	@ResponseBody
	public ModelAndView export(String showId, String goodsIssue, String goodsId, String isLuck, String sort, String userType
			, String bettime, String buyCount, HttpServletRequest request, HttpServletResponse response) {
		InputParams params = new InputParams("userPaymentService", "list");
		params.addParams("showId", null, showId);
		params.addParams("goodsIssue", null, goodsIssue);
		params.addParams("goodsId", null, goodsId);
		params.addParams("isLuck", null, isLuck);
		params.addParams("sort", null, sort);
		params.addParams("bettime", null, bettime);
		params.addParams("buyCount", null, buyCount);
		params.addParams("userType", null, userType);
		
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<FrontUserPaymentOrderVO> voList = JSONUtils.json2list(jsonStr, FrontUserPaymentOrderVO.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "投注记录");
			XSSFRow row = s.createRow(0);
			String title[] = { "ID", "订单号", "用户ID", "昵称", "奖品信息", "奖品金额", "投注量", "金币投注量", "红包投注量", "投注时间", "是否中奖"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(FrontUserPaymentOrderVO vo : voList){
				t++;
				row = s.createRow(t);
				
				String orderNum = String.valueOf(vo.getOrderNum());
				String showIdStr = String.valueOf(vo.getFrontUserShowId());
				String nickname = vo.getNickname();
				String titleStr = vo.getTitle();
				String priceStr = vo.getPrice().toString();
				String dAmountStr = vo.getTotalDAmount().toString();
				String actualDAmountStr = vo.getActualDAmount().toString();
				String voucherDAmountStr = vo.getVoucherDAmount() == null ? "0" : vo.getVoucherDAmount().toString();
				String createtimeCN = Utlity.timeSpanToString(vo.getCreatetime());
				String isLuckCN = vo.getIsLucky() == null ? "未开奖" : (vo.getIsLucky() ? "是" : "否");
				
				String tInfo[] = { String.valueOf(t), orderNum, showIdStr , nickname, titleStr, priceStr, dAmountStr, actualDAmountStr,
						voucherDAmountStr, createtimeCN, isLuckCN};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=frontuserpayments.xlsx");
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
