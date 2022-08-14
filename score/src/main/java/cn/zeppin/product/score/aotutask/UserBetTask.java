package cn.zeppin.product.score.aotutask;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.score.entity.FrontUserBet;
import cn.zeppin.product.score.entity.FrontUserBet.FrontUserBetStatus;
import cn.zeppin.product.score.entity.GuessingMatch;
import cn.zeppin.product.score.entity.GuessingMatch.GuessingMatchStatus;
import cn.zeppin.product.score.entity.GuessingMatchType;
import cn.zeppin.product.score.entity.GuessingMatchType.GuessingMatchTypeStatus;
import cn.zeppin.product.score.entity.SystemParam;
import cn.zeppin.product.score.entity.SystemParam.SystemParamKey;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserBetService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.GuessingMatchService;
import cn.zeppin.product.score.service.GuessingMatchTypeService;
import cn.zeppin.product.score.service.SystemParamService;

@Component
public class UserBetTask {
	
	@Autowired
	private FrontUserBetService frontUserBetService;
	
	@Autowired
	private FrontUserAccountService frontUserAccountService;
	
	@Autowired
    private FrontUserHistoryService frontUserHistoryService;
	
	@Autowired
	private GuessingMatchService guessingMatchService;

	@Autowired
	private GuessingMatchTypeService guessingMatchTypeService;
	
	@Autowired
    private SystemParamService systemParamService;
	
	/**
	 * 处理可自动出票的投注
	 */
//	@Scheduled(cron="2 * *  * * * ")
	public void betConfirmTask() {
		//取可自动付款列表
		SystemParam checkLine = this.systemParamService.get(SystemParamKey.BET_CHECK_LINE);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", FrontUserBetStatus.NORMAL);
		params.put("checkLine", checkLine.getParamValue());
		List<FrontUserBet> list = this.frontUserBetService.getListByParams(params);
		
		for(FrontUserBet fub : list){
			fub.setChecktime(new Timestamp(System.currentTimeMillis()));
			this.frontUserBetService.comfirmFrontUserBet(fub);
		}
	}
	
	/**
	 * 处理停止投注的竞猜类型
	 */
//	@Scheduled(cron="1 * *  * * * ")
	public void matchTypeWaitingTask() {
		List<GuessingMatchType> list = this.guessingMatchTypeService.getWaitingMatchTypeList();
		for(GuessingMatchType gmt : list){
			gmt.setStatus(GuessingMatchTypeStatus.WAITING);
			this.guessingMatchTypeService.update(gmt);
		}
	}
	
	/**
	 * 处理停止投注的比赛
	 */
//	@Scheduled(cron="1 0/5 *  * * * ")
	public void matchWaitingTask() {
		List<GuessingMatch> list = this.guessingMatchService.getWaitingMatchList();
		for(GuessingMatch gm : list){
			gm.setStatus(GuessingMatchStatus.WAITING);
			this.guessingMatchService.update(gm);
		}
	}
}