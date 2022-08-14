package cn.product.score.aotutask;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserBetDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.GuessingMatchDao;
import cn.product.score.dao.GuessingMatchTypeDao;
import cn.product.score.dao.SystemParamDao;
import cn.product.score.entity.FrontUserBet;
import cn.product.score.entity.FrontUserBet.FrontUserBetStatus;
import cn.product.score.entity.GuessingMatch;
import cn.product.score.entity.GuessingMatch.GuessingMatchStatus;
import cn.product.score.entity.GuessingMatchType;
import cn.product.score.entity.GuessingMatchType.GuessingMatchTypeStatus;
import cn.product.score.entity.SystemParam;
import cn.product.score.entity.SystemParam.SystemParamKey;

@Component
public class UserBetTask {
	
	@Autowired
	private FrontUserBetDao frontUserBetDao;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private GuessingMatchDao guessingMatchDao;

	@Autowired
	private GuessingMatchTypeDao guessingMatchTypeDao;
	
	@Autowired
    private SystemParamDao systemParamDao;
	
	/**
	 * 处理可自动出票的投注
	 */
//	@Scheduled(cron="2 * *  * * * ")
	public void betConfirmTask() {
		//取可自动付款列表
		SystemParam checkLine = this.systemParamDao.get(SystemParamKey.BET_CHECK_LINE);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", FrontUserBetStatus.NORMAL);
		params.put("checkLine", checkLine.getParamValue());
		List<FrontUserBet> list = this.frontUserBetDao.getListByParams(params);
		
		for(FrontUserBet fub : list){
			fub.setChecktime(new Timestamp(System.currentTimeMillis()));
			this.frontUserBetDao.comfirmFrontUserBet(fub);
		}
	}
	
	/**
	 * 处理停止投注的比赛
	 */
//	@Scheduled(cron="1 0/5 *  * * * ")
	public void matchWaitingTask() {
		List<GuessingMatch> list = this.guessingMatchDao.getWaitingMatchList();
		for(GuessingMatch gm : list){
			gm.setStatus(GuessingMatchStatus.WAITING);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", GuessingMatchTypeStatus.PUBLISH);
			params.put("guessingMatch", gm.getUuid());
			List<GuessingMatchType> gmtList = this.guessingMatchTypeDao.getListByParams(params);
			for(GuessingMatchType gmt : gmtList){
				gmt.setStatus(GuessingMatchTypeStatus.WAITING);
			}
			this.guessingMatchDao.updateGuessingMatch(gm,gmtList);
		}
	}
}