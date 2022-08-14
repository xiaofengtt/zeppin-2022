package cn.zeppin.product.score.controller.back;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Admin;
import cn.zeppin.product.score.entity.Category;
import cn.zeppin.product.score.entity.GuessingMatch;
import cn.zeppin.product.score.entity.GuessingMatch.GuessingMatchStatus;
import cn.zeppin.product.score.entity.GuessingMatchOdds;
import cn.zeppin.product.score.entity.GuessingMatchOdds.GuessingMatchOddsStatus;
import cn.zeppin.product.score.entity.GuessingMatchType;
import cn.zeppin.product.score.entity.GuessingMatchType.GuessingMatchTypeStatus;
import cn.zeppin.product.score.entity.InfoMatch;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.FrontUserBetDetailService;
import cn.zeppin.product.score.service.GuessingMatchOddsService;
import cn.zeppin.product.score.service.GuessingMatchService;
import cn.zeppin.product.score.service.GuessingMatchTypeService;
import cn.zeppin.product.score.service.InfoMatchService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.BetSumVO;
import cn.zeppin.product.score.vo.back.GuessingMatchOddsChangeVO;
import cn.zeppin.product.score.vo.back.GuessingMatchTypeVO;
import cn.zeppin.product.score.vo.back.GuessingMatchVO;
import cn.zeppin.product.score.vo.back.MatchVO;

@Controller
@RequestMapping(value = "/back/guessingMatchOdds")
public class GuessingMatchOddsController extends BaseController{
	
	@Autowired
    private GuessingMatchService guessingMatchService;
	
	@Autowired
    private GuessingMatchTypeService guessingMatchTypeService;
	
	@Autowired
    private GuessingMatchOddsService guessingMatchOddsService;
	
	@Autowired
    private FrontUserBetDetailService frontUserBetDetailService;
	
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
    private InfoMatchService infoMatchService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
    private AdminService adminService;

	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value="/controlList",method=RequestMethod.GET)
	@ResponseBody
	public Result controlList(){
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", GuessingMatchStatus.PUBLISH);
		List<GuessingMatch> gmList = guessingMatchService.getListByParams(searchMap);
		
		List<GuessingMatchVO> gmvoList = new ArrayList<GuessingMatchVO>();
		for(GuessingMatch gm : gmList){
			GuessingMatchVO gmvo = new GuessingMatchVO(gm);
			InfoMatch match = this.infoMatchService.get(gm.getInfoMatch());
			if(match != null){
				MatchVO matchVO = new MatchVO(match);
				
				Category cat = this.categoryService.get(match.getCategory());
				matchVO.setCategoryName(cat.getName());
				
				Team homeTeam = this.teamService.get(match.getHometeam());
				matchVO.setHometeamName(homeTeam.getName());
				if(!Utlity.checkStringNull(homeTeam.getIcon())){
					Resource resource = this.resourceService.get(homeTeam.getIcon());
					if(resource != null){
						matchVO.setHometeamIconUrl(resource.getUrl());
					}
				}
				
				Team awayTeam = this.teamService.get(match.getAwayteam());
				matchVO.setAwayteamName(awayTeam.getName());
				if(!Utlity.checkStringNull(awayTeam.getIcon())){
					Resource resource = this.resourceService.get(awayTeam.getIcon());
					if(resource != null){
						matchVO.setAwayteamIconUrl(resource.getUrl());
					}
				}
				gmvo.setMatchDetail(matchVO);
			}
			
			Admin creator = this.adminService.get(gm.getCreator());
			if(creator != null){
				gmvo.setCreator(creator.getRealname());
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("guessingMatch", gm.getUuid());
			List<GuessingMatchTypeVO> gmtvoList = new ArrayList<GuessingMatchTypeVO>();
			List<GuessingMatchType> gmtList = this.guessingMatchTypeService.getListByParams(params);
			
			for(GuessingMatchType gmt : gmtList){
				GuessingMatchTypeVO gmtvo = new GuessingMatchTypeVO(gmt);
				
				params.clear();
				params.put("guessingMatchType", gmt.getUuid());
				params.put("status", GuessingMatchOddsStatus.NORMAL);
				params.put("sort", "spread, result");
				List<GuessingMatchOdds> oddsList = this.guessingMatchOddsService.getListByParams(params);
				gmtvo.setOddsList(oddsList);
				
				gmtvoList.add(gmtvo);
			}
			
			gmvo.setOddsDetail(gmtvoList);
			gmvoList.add(gmvo);
		}
		return ResultManager.createDataResult(gmvoList);
	}
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "guessingMatch", message="竞猜比赛", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String guessingMatch){
		GuessingMatch gm = guessingMatchService.get(guessingMatch);
		if(gm == null || GuessingMatchStatus.DELETE.equals(gm.getStatus())){
			return ResultManager.createFailResult("赛事不存在！");
		}
		
		GuessingMatchVO gmvo = new GuessingMatchVO(gm);
		InfoMatch match = this.infoMatchService.get(gm.getInfoMatch());
		if(match != null){
			MatchVO matchVO = new MatchVO(match);
			
			Category cat = this.categoryService.get(match.getCategory());
			matchVO.setCategoryName(cat.getName());
			
			Team homeTeam = this.teamService.get(match.getHometeam());
			matchVO.setHometeamName(homeTeam.getName());
			if(!Utlity.checkStringNull(homeTeam.getIcon())){
				Resource resource = this.resourceService.get(homeTeam.getIcon());
				if(resource != null){
					matchVO.setHometeamIconUrl(resource.getUrl());
				}
			}
			
			Team awayTeam = this.teamService.get(match.getAwayteam());
			matchVO.setAwayteamName(awayTeam.getName());
			if(!Utlity.checkStringNull(awayTeam.getIcon())){
				Resource resource = this.resourceService.get(awayTeam.getIcon());
				if(resource != null){
					matchVO.setAwayteamIconUrl(resource.getUrl());
				}
			}
			gmvo.setMatchDetail(matchVO);
		}
		
		Admin creator = this.adminService.get(gm.getCreator());
		if(creator != null){
			gmvo.setCreator(creator.getRealname());
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("guessingMatch", guessingMatch);
		List<GuessingMatchTypeVO> gmtvoList = new ArrayList<GuessingMatchTypeVO>();
		List<GuessingMatchType> gmtList = this.guessingMatchTypeService.getListByParams(params);
		
		for(GuessingMatchType gmt : gmtList){
			GuessingMatchTypeVO gmtvo = new GuessingMatchTypeVO(gmt);
			
			params.clear();
			params.put("guessingMatchType", gmt.getUuid());
			params.put("status", GuessingMatchOddsStatus.NORMAL);
			params.put("sort", "spread, result");
			List<GuessingMatchOdds> oddsList = this.guessingMatchOddsService.getListByParams(params);
			gmtvo.setOddsList(oddsList);
			
			gmtvoList.add(gmtvo);
		}
		
		gmvo.setOddsDetail(gmtvoList);
		return ResultManager.createDataResult(gmvo);
	}
	
	@RequestMapping(value="/getBetSum",method=RequestMethod.GET)
	@ActionParam(key = "guessingMatch", message="竞猜比赛", type = DataType.STRING)
	@ResponseBody
	public Result getBetSum(String guessingMatch){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("guessingMatch", guessingMatch);
		
		List<BetSumVO> betSumList = this.frontUserBetDetailService.getBetSumList(params);
		return ResultManager.createDataResult(betSumList);
	}
	
	@RequestMapping(value="/addType",method=RequestMethod.POST)
	@ActionParam(key = "guessingMatch", message="竞猜赛事", type = DataType.STRING, required = true)
	@ActionParam(key = "oddsType", message="赔率类型", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="竞猜类型", type = DataType.STRING, required = true)
	@ActionParam(key = "maxMoney", message="最大投注限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "flagSingle", message="是否可以单投", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "betEndtime", message="截止时间", type = DataType.STRING, required = true)
	@ActionParam(key = "odds", message="赔率", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
    public Result addType(GuessingMatchType guessingMatchType, String betEndtime, String[] odds){
		Admin admin = this.getCurrentOperator();
		
		GuessingMatch gm = this.guessingMatchService.get(guessingMatchType.getGuessingMatch());
		if(gm == null || GuessingMatchStatus.DELETE.equals(gm.getStatus())){
			return ResultManager.createFailResult("竞猜赛事不存在！");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("guessingMatch", gm.getUuid());
		params.put("type", guessingMatchType.getType());
		params.put("oddsType", guessingMatchType.getOddsType());
		Integer count = this.guessingMatchTypeService.getCountByParams(params);
		if(count > 0){
			return ResultManager.createFailResult("当前赛事已有该类型赔率信息！");
		}
		
		guessingMatchType.setUuid(UUID.randomUUID().toString());
		guessingMatchType.setEndtime(Timestamp.valueOf(betEndtime));
		guessingMatchType.setStatus(GuessingMatchTypeStatus.NORMAL);
		guessingMatchType.setCreator(admin.getUuid());
		guessingMatchType.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		List<GuessingMatchOdds> oddsList = new ArrayList<GuessingMatchOdds>();
		for(String odd : odds){
			String[] oddDetails = odd.split("@_@");
			if(oddDetails.length != 3){
				return ResultManager.createFailResult("赔率信息不正确！");
			}
			String spread = Utlity.checkStringNull(oddDetails[0]) ? null : oddDetails[0];
			String result = oddDetails[1];
			String oddsValue = oddDetails[2];
			if(!Utlity.isNumeric(oddsValue)){
				return ResultManager.createFailResult("赔率值信息不正确！");
			}
			
			GuessingMatchOdds gmo = new GuessingMatchOdds();
			gmo.setUuid(UUID.randomUUID().toString());
			gmo.setGuessingMatch(guessingMatchType.getGuessingMatch());
			gmo.setGuessingMatchType(guessingMatchType.getUuid());
			gmo.setSpread(spread == null ? "" : spread);
			gmo.setResult(result);
			gmo.setOdds(BigDecimal.valueOf(Double.valueOf(oddsValue)));
			gmo.setStatus(GuessingMatchOddsStatus.NORMAL);
			gmo.setCreator(guessingMatchType.getCreator());
			gmo.setCreatetime(guessingMatchType.getCreatetime());
			
			oddsList.add(gmo);
		}
		
		guessingMatchTypeService.addGuessingMatchType(guessingMatchType, oddsList);
		return ResultManager.createSuccessResult("添加成功！");
    }
    
	@RequestMapping(value="/editType",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "oddsType", message="赔率类型", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="竞猜类型", type = DataType.STRING, required = true)
	@ActionParam(key = "maxMoney", message="最大投注限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "flagSingle", message="是否可以单投", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "betEndtime", message="截止时间", type = DataType.STRING, required = true)
	@ActionParam(key = "odds", message="赔率", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result editType(GuessingMatchType guessingMatchType, String betEndtime, String[] odds){
		Admin admin = this.getCurrentOperator();
		
		GuessingMatchType gmt = guessingMatchTypeService.get(guessingMatchType.getUuid());
		if(gmt == null  || GuessingMatchTypeStatus.DELETE.equals(gmt.getStatus())){
			return ResultManager.createFailResult("竞猜类型不存在！");
		}
		
		if(GuessingMatchTypeStatus.PUBLISH.equals(gmt.getStatus())){
			return ResultManager.createFailResult("竞猜类型已发布！");
		}
		
		gmt.setOddsType(guessingMatchType.getOddsType());
		gmt.setType(guessingMatchType.getType());
		gmt.setMaxMoney(guessingMatchType.getMaxMoney());
		gmt.setFlagSingle(guessingMatchType.getFlagSingle());
		gmt.setEndtime(Timestamp.valueOf(betEndtime));
		gmt.setCreator(admin.getUuid());
		gmt.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		List<GuessingMatchOdds> oddsList = new ArrayList<GuessingMatchOdds>();
		for(String odd : odds){
			String[] oddDetails = odd.split("@_@");
			if(oddDetails.length != 3){
				return ResultManager.createFailResult("赔率信息不正确！");
			}
			String spread = Utlity.checkStringNull(oddDetails[0]) ? null : oddDetails[0];
			String result = oddDetails[1];
			String oddsValue = oddDetails[2];
			if(!Utlity.isNumeric(oddsValue)){
				return ResultManager.createFailResult("赔率值信息不正确！");
			}
			
			GuessingMatchOdds gmo = new GuessingMatchOdds();
			gmo.setUuid(UUID.randomUUID().toString());
			gmo.setGuessingMatch(gmt.getGuessingMatch());
			gmo.setGuessingMatchType(gmt.getUuid());
			gmo.setSpread(spread == null ? "" : spread);
			gmo.setResult(result);
			gmo.setOdds(BigDecimal.valueOf(Double.valueOf(oddsValue)));
			gmo.setStatus(GuessingMatchOddsStatus.NORMAL);
			gmo.setCreator(gmt.getCreator());
			gmo.setCreatetime(gmt.getCreatetime());
			
			oddsList.add(gmo);
		}
		
		guessingMatchTypeService.updateGuessingMatchType(gmt, oddsList);
		return ResultManager.createSuccessResult("修改成功！");
	}
	

	@RequestMapping(value="/publishType",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result publishType(String uuid, String status){
		GuessingMatchType gmt = guessingMatchTypeService.get(uuid);
		if(gmt == null || GuessingMatchTypeStatus.DELETE.equals(gmt.getStatus())){
			return ResultManager.createFailResult("竞猜类型不存在！");
		}
		
		if(!GuessingMatchStatus.NORMAL.equals(gmt.getStatus())){
			return ResultManager.createFailResult("无法再次发布！");
		}
		GuessingMatch gm = guessingMatchService.get(gmt.getGuessingMatch());
		gmt.setStatus(GuessingMatchTypeStatus.PUBLISH);
		gm.setStatus(GuessingMatchStatus.PUBLISH);
		
		guessingMatchTypeService.publishGuessingMatchType(gmt, gm);
		return ResultManager.createSuccessResult("修改成功！");
	}
	
	@RequestMapping(value="/deleteType",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result deleteType(String uuid){
		GuessingMatchType gmt = guessingMatchTypeService.get(uuid);
		if(gmt == null || GuessingMatchTypeStatus.DELETE.equals(gmt.getStatus())){
			return ResultManager.createFailResult("竞猜类型不存在！");
		}
		
		if(!GuessingMatchStatus.NORMAL.equals(gmt.getStatus())){
			return ResultManager.createFailResult("该类型无法删除！");
		}
		gmt.setStatus(GuessingMatchTypeStatus.DELETE);
		
		guessingMatchTypeService.deleteGuessingMatchType(gmt);
		return ResultManager.createSuccessResult("修改成功！");
	}
	
	@RequestMapping(value="/historyList",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result historyList(String uuid){
		GuessingMatchOdds gmo = guessingMatchOddsService.get(uuid);
		if(gmo == null || GuessingMatchOddsStatus.DELETE.equals(gmo.getStatus())){
			return ResultManager.createFailResult("竞猜赔率不存在！");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("guessingMatchType", gmo.getGuessingMatchType());
		params.put("spread", gmo.getSpread());
		params.put("result", gmo.getResult());
		params.put("sort", "createtime desc");
		
		List<GuessingMatchOdds> gmoList = this.guessingMatchOddsService.getListByParams(params);
		
		return ResultManager.createDataResult(gmoList);
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ActionParam(key = "datas", message="数据", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result update(String[] datas){
		Admin admin = this.getCurrentOperator();
		
		List<GuessingMatchOddsChangeVO> changevoList = new ArrayList<GuessingMatchOddsChangeVO>();
		for(String data : datas){
			String[] dataArray = data.split("@_@");
			if(dataArray.length != 2){
				return ResultManager.createFailResult("数据格式有误！");
			}
			
			String uuid = dataArray[0];
			String oddsValue = dataArray[1];
			if(!Utlity.isNumeric(oddsValue)){
				return ResultManager.createFailResult("数据格式有误！");
			}
			BigDecimal odds = new BigDecimal(oddsValue);
			
			GuessingMatchOdds gmo = guessingMatchOddsService.get(uuid);
			if(gmo == null || GuessingMatchOddsStatus.DELETE.equals(gmo.getStatus())){
				return ResultManager.createFailResult("竞猜赔率不存在！");
			}
			
			gmo.setStatus(GuessingMatchOddsStatus.DISABLE);
			GuessingMatchOdds gmoNew = new GuessingMatchOdds();
			gmoNew.setUuid(UUID.randomUUID().toString());
			gmoNew.setGuessingMatch(gmo.getGuessingMatch());
			gmoNew.setGuessingMatchType(gmo.getGuessingMatchType());
			gmoNew.setSpread(gmo.getSpread());
			gmoNew.setResult(gmo.getResult());
			gmoNew.setOdds(odds);
			gmoNew.setStatus(GuessingMatchOddsStatus.NORMAL);
			gmoNew.setCreator(admin.getUuid());
			gmoNew.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			GuessingMatchOddsChangeVO changevo = new GuessingMatchOddsChangeVO();
			changevo.setOldData(gmo);
			changevo.setNewData(gmoNew);
			
			changevoList.add(changevo);
		}
		
		this.guessingMatchOddsService.updateOdds(changevoList);
		return ResultManager.createDataResult(changevoList);
	}
	
	
}
