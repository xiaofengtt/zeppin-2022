package cn.product.score.service.back.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.AdminDao;
import cn.product.score.dao.CategoryDao;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserBetDao;
import cn.product.score.dao.FrontUserBetDetailDao;
import cn.product.score.dao.FrontUserDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.GuessingMatchDao;
import cn.product.score.dao.GuessingMatchOddsDao;
import cn.product.score.dao.GuessingMatchTypeDao;
import cn.product.score.dao.InfoMatchDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.Admin;
import cn.product.score.entity.Category;
import cn.product.score.entity.FrontUser;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserBet;
import cn.product.score.entity.FrontUserBet.FrontUserBetStatus;
import cn.product.score.entity.FrontUserBetDetail;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.score.service.back.UserBetService;
import cn.product.score.entity.GuessingMatch;
import cn.product.score.entity.GuessingMatchOdds;
import cn.product.score.entity.InfoMatch;
import cn.product.score.entity.Team;
import cn.product.score.util.JSONUtils;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.FrontUserBetDetailVO;
import cn.product.score.vo.back.FrontUserBetVO;
import cn.product.score.vo.back.StatusCountVO;

@Service("userBetService")
public class UserBetServiceImpl implements UserBetService{
	
	@Autowired
	private FrontUserBetDao frontUserBetDao;
	
	@Autowired
    private FrontUserBetDetailDao frontUserBetDetailDao;
	
	@Autowired
    private FrontUserDao frontUserDao;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private CategoryDao categoryDao;
	
	@Autowired
    private InfoMatchDao infoMatchDao;
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
    private GuessingMatchDao guessingMatchDao;
	
	@Autowired
    private GuessingMatchTypeDao guessingMatchTypeDao;
	
	@Autowired
    private GuessingMatchOddsDao guessingMatchOddsDao;
	
	@Autowired
    private AdminDao adminDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUserBet fub = frontUserBetDao.get(uuid);
		if(fub == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("投注信息不存在！");
			return;
		}
		
		FrontUserBetVO fubvo = new FrontUserBetVO(fub);
		
		FrontUser fu = this.frontUserDao.get(fub.getFrontUser());
		if(fu != null){
			fubvo.setFrontUserName(fu.getRealname());
			fubvo.setFrontUserMobile(fu.getMobile());
		}

		if(fub.getChecker() != null){
			Admin checker = this.adminDao.get(fub.getChecker());
			if(checker != null){
				fubvo.setCheckerName(checker.getRealname());
			}
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserBet", fub.getUuid());
		List<FrontUserBetDetail> fubdList = this.frontUserBetDetailDao.getListByParams(searchMap);
		
		List<FrontUserBetDetailVO> fudbvoList = new ArrayList<FrontUserBetDetailVO>();
		for(FrontUserBetDetail fubd : fubdList){
			FrontUserBetDetailVO fubdvo = new FrontUserBetDetailVO(fubd);
			
			GuessingMatch gm = this.guessingMatchDao.get(fubd.getGuessingMatch());
			if(gm != null){
				InfoMatch im = this.infoMatchDao.get(gm.getInfoMatch());
				if(im != null){
					fubdvo.setTime(im.getTime());
					
					Category category = this.categoryDao.get(im.getCategory());
					if(category != null){
						fubdvo.setCategoryName(category.getName());
					}
					
					Team hometeam = this.teamDao.get(im.getHometeam());
					if(hometeam != null){
						fubdvo.setHometeamName(hometeam.getName());
					}
					
					Team awayteam = this.teamDao.get(im.getAwayteam());
					if(awayteam != null){
						fubdvo.setAwayteamName(awayteam.getName());
					}
				}
			}
			
			GuessingMatchOdds gmo = this.guessingMatchOddsDao.get(fubd.getGuessingMatchOdds());
			if(gmo != null){
				fubdvo.setSpread(gmo.getSpread());
				fubdvo.setResult(gmo.getResult());
			}
			
			fudbvoList.add(fubdvo);
		}
		
		fubvo.setDetailList(fudbvoList);
		
		result.setData(fubvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserBetDao.getCountByParams(searchMap);
		List<FrontUserBet> fubList = frontUserBetDao.getListByParams(searchMap);
		
		List<FrontUserBetVO> voList = new ArrayList<FrontUserBetVO>();
		for(FrontUserBet fub : fubList){
			FrontUserBetVO fubvo = new FrontUserBetVO(fub);
			
			FrontUser fu = this.frontUserDao.get(fub.getFrontUser());
			if(fu != null){
				fubvo.setFrontUserName(fu.getRealname());
				fubvo.setFrontUserMobile(fu.getMobile());
			}

			if(fub.getChecker() != null){
				Admin checker = this.adminDao.get(fub.getChecker());
				if(checker != null){
					fubvo.setCheckerName(checker.getRealname());
				}
			}
			
			voList.add(fubvo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void confirm(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserBet fub = frontUserBetDao.get(uuid);
		if(fub == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("投注信息不存在！");
			return;
		}
		
		if(!FrontUserBetStatus.NORMAL.equals(fub.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("投注状态异常！");
			return;
		}
		
		fub.setChecker(admin);
		fub.setChecktime(new Timestamp(System.currentTimeMillis()));
		this.frontUserBetDao.comfirmFrontUserBet(fub);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("确认成功！");
	}

	@Override
	public void refund(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUserBet fub = frontUserBetDao.get(uuid);
		if(fub == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("投注信息不存在！");
			return;
		}
		
		if(!FrontUserBetStatus.NORMAL.equals(fub.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("投注状态异常！");
			return;
		}
		
		fub.setChecker(admin);
		fub.setChecktime(new Timestamp(System.currentTimeMillis()));
		this.frontUserBetDao.refundFrontUserBet(fub);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("退款成功！");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void lottery(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("status", FrontUserBetStatus.CONFIRM);
		paramsls.put("lastTime", Utlity.timestampFormat(new Timestamp(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
		List<FrontUserBet> fubList = this.frontUserBetDao.getListByParams(paramsls);
		
		Map<String, Boolean> oddsResultMap = new HashMap<String, Boolean>();
		for(FrontUserBet fub : fubList){
			Map<String, Object> betMap = JSONUtils.json2map(fub.getBet());
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("frontUserBet", fub.getUuid());
			
			List<FrontUserBetDetail> fubdList = this.frontUserBetDetailDao.getListByParams(searchMap);
			Map<String, Boolean> detailResultMap = new HashMap<String, Boolean>();
			Map<String, BigDecimal> detailOddsMap = new HashMap<String, BigDecimal>();
			for(FrontUserBetDetail fubd : fubdList){
				if(oddsResultMap.get(fubd.getGuessingMatchOdds()) == null){
					GuessingMatchOdds gmo = this.guessingMatchOddsDao.get(fubd.getGuessingMatchOdds());
					detailOddsMap.put(fubd.getUuid(), gmo.getOdds());
					if(gmo.getIsRight() == null){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("有已结束的比赛尚未确认结果！");
						return;
					}
					oddsResultMap.put(gmo.getUuid(), gmo.getIsRight());
				}
				
				detailResultMap.put(fubd.getUuid(), oddsResultMap.get(fubd.getGuessingMatchOdds()));
			}
			
			List<List> composeList = JSONUtils.json2list(fub.getCompose(), List.class);
			BigDecimal award = BigDecimal.ZERO;
			for(List<String> singleList : composeList){
				Boolean flag = true;
				BigDecimal odds = BigDecimal.ONE;
				for(String uuid : singleList){
					if(!detailResultMap.get(uuid)){
						flag = false;
						break;
					}
					odds = odds.multiply(detailOddsMap.get(uuid));
				}
				if(flag){
					BigDecimal betNum = BigDecimal.valueOf(Integer.valueOf(betMap.get(singleList.size() + "").toString()));
					BigDecimal singleAward = fub.getMonovalent().multiply(odds);
					
					award = award.add(singleAward.multiply(betNum).setScale(2, BigDecimal.ROUND_FLOOR));
				}
			}
			
			fub.setAward(award);
			fub.setStatus(FrontUserBetStatus.FINISHED);
			
			if(fub.getAward().compareTo(BigDecimal.ZERO) > 0){
				FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(fub.getFrontUser());
				
				FrontUserHistory fuh = new FrontUserHistory();
				fuh.setUuid(UUID.randomUUID().toString());
				fuh.setFrontUser(fub.getFrontUser());
				fuh.setFrontUserBet(fub.getUuid());
				fuh.setFrontUserAccount(fua.getUuid());
				fuh.setPay(BigDecimal.ZERO);
				fuh.setIncome(fub.getAward());
				fuh.setPoundage(BigDecimal.ZERO);
				fuh.setBalance(fua.getBalanceFree());
				fuh.setType(FrontUserHistoryType.USER_BET_AWARD);
				fuh.setStatus(FrontUserHistoryStatus.SUCCESS);
				fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
				
				fua.setBalanceFree(fua.getBalanceFree().add(fub.getAward()));
				this.frontUserBetDao.lottery(fua, fub, fuh);
			}else{
				this.frontUserBetDao.update(fub);
			}
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}

	@Override
	public void statusList(InputParams params, DataResult<Object> result) {
		List<StatusCountVO> list = frontUserBetDao.getStatusList();
		result.setData(list);
		result.setTotalResultCount(list.size());
		result.setStatus(ResultStatusType.SUCCESS);
	}

}
