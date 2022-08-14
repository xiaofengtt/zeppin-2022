package cn.product.treasuremall.controller.back;

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

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.controller.BaseController;
import cn.product.treasuremall.entity.FrontUser.FrontUserLevel;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.FrontUserVO;

@Controller
@RequestMapping(value = "/back/user")
public class UserController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1908844808379870830L;

	/**
	 * 获取用户详情
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("userService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 获取用户列表
	 * @param realname
	 * @param mobile
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @param channel -- 填写
	 * @param level -- registered/recharged/VIP
	 * @param createtime
	 * @param totalRecharge
	 * @param totalWinning
	 * @param totalWithdraw
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "realname", message="真实姓名", type = DataType.STRING)
	@ActionParam(key = "showid", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "mobile", message="手机号码", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ActionParam(key = "channel", message="注册渠道", type = DataType.STRING)
	@ActionParam(key = "level", message="用户等级", type = DataType.STRING)
	@ActionParam(key = "createtime", message="创建时间", type = DataType.STRING)
	@ActionParam(key = "totalRecharge", message="充值金额", type = DataType.STRING)
	@ActionParam(key = "totalWinning", message="中奖金额", type = DataType.STRING)
	@ActionParam(key = "totalWithdraw", message="提现金额", type = DataType.STRING)
	@ActionParam(key = "type", message="用户类型", type = DataType.STRING)
	@ResponseBody
	public Result list(String realname, String showid, String mobile, String status, Integer pageNum, Integer pageSize, String sort
			, String channel, String level, String createtime, String totalRecharge, String totalWinning, String totalWithdraw, String type){
		InputParams params = new InputParams("userService", "list");
		params.addParams("realname", null, realname);
		params.addParams("showid", null, showid);
		params.addParams("mobile", null, mobile);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("channel", null, channel);
		params.addParams("level", null, level);
		params.addParams("createtime", null, createtime);
		params.addParams("totalRecharge", null, totalRecharge);
		params.addParams("totalWinning", null, totalWinning);
		params.addParams("totalWithdraw", null, totalWithdraw);
		params.addParams("type", null, type);
		return this.execute(params);
	}
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		InputParams params = new InputParams("userService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
	
	/**
	 * 获取用户收货地址列表
	 * @param uuid
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/addresslist",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="用户ID", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ResponseBody
	public Result addresslist(String uuid, Integer pageNum, Integer pageSize){
		InputParams params = new InputParams("userService", "addresslist");
		params.addParams("uuid", null, uuid);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		return this.execute(params);
	}

	/**
	 * 批量导出用户
	 * @param realname
	 * @param mobile
	 * @param status
	 * @param sort
	 * @param channel
	 * @param level
	 * @param createtime
	 * @param totalRecharge
	 * @param totalWinning
	 * @param totalWithdraw
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ActionParam(key = "realname", message="真实姓名", type = DataType.STRING)
	@ActionParam(key = "mobile", message="手机号码", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ActionParam(key = "channel", message="注册渠道", type = DataType.STRING)
	@ActionParam(key = "level", message="用户等级", type = DataType.STRING)
	@ActionParam(key = "createtime", message="创建时间", type = DataType.STRING)
	@ActionParam(key = "totalRecharge", message="充值金额", type = DataType.STRING)
	@ActionParam(key = "totalWinning", message="中奖金额", type = DataType.STRING)
	@ActionParam(key = "totalWithdraw", message="提现金额", type = DataType.STRING)
	@ActionParam(key = "type", message="用户类型", type = DataType.STRING)
	@ResponseBody
	public ModelAndView export(String realname, String mobile, String status, String sort
			, String channel, String level, String createtime, String totalRecharge, String totalWinning, String totalWithdraw, String type
			, HttpServletRequest request, HttpServletResponse response) {
		InputParams params = new InputParams("userService", "list");
		params.addParams("realname", null, realname);
		params.addParams("mobile", null, mobile);
		params.addParams("status", null, status);
		params.addParams("sort", null, sort);
		params.addParams("channel", null, channel);
		params.addParams("level", null, level);
		params.addParams("createtime", null, createtime);
		params.addParams("totalRecharge", null, totalRecharge);
		params.addParams("totalWinning", null, totalWinning);
		params.addParams("totalWithdraw", null, totalWithdraw);
		params.addParams("type", null, type);
		
		
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		String jsonStr = JSONUtils.obj2json(result.getData());
		List<FrontUserVO> voList = JSONUtils.json2list(jsonStr, FrontUserVO.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "用户");
			XSSFRow row = s.createRow(0);
			String title[] = { "ID", "昵称", "手机号", "真实姓名", "地区", "用户级别", "充值总额/元", "提现总额/元", "领取实物总额/元", "用户获利/元"
					, "投注总额", "中奖总额", "投注次数", "中奖次数", "账户余额", "注册渠道", "注册时间", "上次上线时间"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(FrontUserVO vo : voList){
				t++;
				row = s.createRow(t);
				
				String showId = String.valueOf(vo.getShowId());
				String nickname = vo.getNickname();
				String mobileStr = vo.getMobile();
				String realnameStr = vo.getRealname() == null ? "" : vo.getRealname();
				String area = vo.getArea();
				String levelStr = vo.getLevel();
				if(FrontUserLevel.REGISTERED.equals(vo.getLevel())) {//注册/充值/高级
					levelStr = "注册";
				} else if(FrontUserLevel.RECHARGED.equals(vo.getLevel())) {
					levelStr = "充值";
				} else if(FrontUserLevel.VIP.equals(vo.getLevel())) {
					levelStr = "VIP";
				} else if(FrontUserLevel.DEMO.equals(vo.getLevel())) {
					levelStr = "测试用户";
				}
				BigDecimal totalRechargeNum = vo.getTotalRecharge();
				BigDecimal totalWithdrawNum = vo.getTotalWithdraw();
				BigDecimal totalDeliveryNum = vo.getTotalDelivery();
//				BigDecimal totalProfitNum = totalWithdrawNum.add(totalDeliveryNum).subtract(totalRechargeNum);//用户获利=总充值-（总体现+总实物+当前余额）
				BigDecimal totalProfitNum = totalRechargeNum.subtract(totalWithdrawNum.add(totalDeliveryNum));
				BigDecimal totalPaymentNum = vo.getTotalPayment();
				BigDecimal totalWinningNum = vo.getTotalWinning();
				
				Integer paymentTimes = vo.getPaymentTimes();
				Integer winningTimes = vo.getWinningTimes();
				
				BigDecimal balance = vo.getBalance();
				
				String channelStr = vo.getRegisterChannelCN();
				String createtimeCN = Utlity.timeSpanToString(vo.getCreatetime());
				String lastonlineCN = Utlity.timeSpanToString(vo.getLastonline());
				
				String tInfo[] = { showId, nickname , mobileStr, realnameStr, area, levelStr, totalRechargeNum.toString(),
						totalWithdrawNum.toString(), totalDeliveryNum.toString(), totalProfitNum.toString(), totalPaymentNum.toString(),
						totalWinningNum.toString(), String.valueOf(paymentTimes), String.valueOf(winningTimes), balance.toString(),
						channelStr, createtimeCN, lastonlineCN};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=frontusers.xlsx");
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
	
	@RequestMapping(value="/group",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "level", message="用户级别", type = DataType.STRING, required = true)
	@ResponseBody
	public Result group(String uuid, String level){
		InputParams params = new InputParams("userService", "group");
		params.addParams("uuid", null, uuid);
		params.addParams("level", null, level);
		return this.execute(params);
	}
	
	@RequestMapping(value="/blackadd",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message="原因", type = DataType.STRING, required = true)
	@ResponseBody
	public Result blackadd(String uuid, String reason){
		InputParams params = new InputParams("userService", "blackadd");
		params.addParams("uuid", null, uuid);
		params.addParams("reason", null, reason);
		return this.execute(params);
	}
	
	@RequestMapping(value="/blacklist",method=RequestMethod.GET)
	@ActionParam(key = "nickname", message="用户昵称", type = DataType.STRING)
	@ActionParam(key = "showid", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result blacklist(String nickname, String showid, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("userService", "blacklist");
		params.addParams("nickname", null, nickname);
		params.addParams("showid", null, showid);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/blackdrop",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result blackdrop(String uuid){
		InputParams params = new InputParams("userService", "blackdrop");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
}
