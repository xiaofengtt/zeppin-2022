/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.zeppin.product.score.entity.Category;
import cn.zeppin.product.score.entity.GuessingMatch;
import cn.zeppin.product.score.entity.GuessingMatchOdds;
import cn.zeppin.product.score.entity.GuessingMatchOdds.GuessingMatchOddsStatus;
import cn.zeppin.product.score.entity.GuessingMatchType;
import cn.zeppin.product.score.entity.GuessingMatchType.GuessingMatchTypeStatus;
import cn.zeppin.product.score.entity.InfoMatch;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.SystemParam;
import cn.zeppin.product.score.entity.SystemParam.SystemParamKey;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.GuessingMatchOddsService;
import cn.zeppin.product.score.service.GuessingMatchService;
import cn.zeppin.product.score.service.GuessingMatchTypeService;
import cn.zeppin.product.score.service.InfoMatchService;
import cn.zeppin.product.score.service.MobileCodeService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.SystemParamService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.front.CategoryCountVO;
import cn.zeppin.product.score.vo.front.GuessingMatchTypeVO;
import cn.zeppin.product.score.vo.front.GuessingMatchVO;
import cn.zeppin.product.score.vo.front.MatchVO;

/**
 * 竞猜信息
 */

@Controller
@RequestMapping(value = "/front/guessingMatch")
public class FrontGuessingMatchController extends BaseController{
	
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
    private InfoMatchService infoMatchService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MobileCodeService mobileCodeService;
	
	@Autowired
	private SystemParamService systemParamService;
	
	@Autowired
	private GuessingMatchOddsService guessingMatchOddsService;

	@Autowired
	private GuessingMatchService guessingMatchService;
	
	@Autowired
	private GuessingMatchTypeService guessingMatchTypeService;
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "type", message="类型", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ActionParam(key = "category", message="联赛", type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result list(String type, String[] category, String sort){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", GuessingMatchTypeStatus.PUBLISH);
		if(category != null){
			String cateStr = "(";
			for(String cate : category){
				cateStr = cateStr + "'" + cate + "',";
			}
			if(category != null && category.length > 0){
				cateStr = cateStr.substring(0, cateStr.length() - 1);
			}
			cateStr = cateStr + ")";
			params.put("category", cateStr);
		}
		params.put("type", type);
		params.put("sort", sort);
		params.put("deadline", "1");
		
		List<GuessingMatchType> gmtList = guessingMatchTypeService.getListByParams(params);
		SystemParam sp = this.systemParamService.get(SystemParamKey.BET_USER_RELOAD_DURATION);
		
		List<GuessingMatchVO> voList = new ArrayList<GuessingMatchVO>();
		for(GuessingMatchType gmt : gmtList){
			GuessingMatch gm = guessingMatchService.get(gmt.getGuessingMatch());
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
				
				List<GuessingMatchTypeVO> gmtvoList = new ArrayList<GuessingMatchTypeVO>();
				GuessingMatchTypeVO gmtvo = new GuessingMatchTypeVO(gmt);
				
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("guessingMatchType", gmt.getUuid());
				searchMap.put("status",GuessingMatchOddsStatus.NORMAL);
				searchMap.put("sort", "spread, result");
				List<GuessingMatchOdds> oddsList = this.guessingMatchOddsService.getListByParams(searchMap);
				gmtvo.setOddsList(oddsList);
				gmtvoList.add(gmtvo);
				
				gmvo.setOddsDetail(gmtvoList);
				gmvo.setReloadDuration(sp.getParamValue());
				
				voList.add(gmvo);
			}
		}
		return ResultManager.createDataResult(voList);
	}
	
	@RequestMapping(value="/categoryList",method=RequestMethod.GET)
	@ActionParam(key = "type", message="类型", type = DataType.STRING, required = true)
	@ResponseBody
	public Result list(String type){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		
		List<CategoryCountVO> voList = this.guessingMatchTypeService.getCategoryList(params);
		return ResultManager.createDataResult(voList);
	}
}
