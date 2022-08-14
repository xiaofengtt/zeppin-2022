package cn.zeppin.product.score.util;

import java.util.HashMap;
import java.util.Map;

import cn.zeppin.product.score.entity.Category.CategoryMainUuid;

public class GetInfoUtil {
	
	private static final String API_KEY = "bf762ae06059cc1e50b211d7c6430d508679d72747ae53e58c7f5f279a4a0e40";
	
	/**
	 * 获取国家数据URL
	 */
	public static final String URL_MET_GET_Countries_INFO = "https://allsportsapi.com/api/football/?met=Countries&APIkey=!!";
	
	/**
	 * 获取联赛数据URL
	 * 参数：countryId=国家ID 
	 */
	public static final String URL_MET_GET_Leagues_INFO = "https://allsportsapi.com/api/football/?met=Leagues&APIkey=!!";
	
	/**
	 * 获取赛程数据URL
	 * 参数：from=start date(yyyy-mm-dd)
	 * to=end date(yyyy-mm-dd)
	 * countryId=国家ID
	 * leagueId=联赛ID
	 * matchId=比赛赛程ID
	 */
	public static final String URL_MET_GET_Fixtures_INFO = "https://allsportsapi.com/api/football/?met=Fixtures&APIkey=!!";
	
	/**
	 * 获取H2H数据URL
	 * 参数：firstTeamId
	 * secondTeamId
	 */
	public static final String URL_MET_GET_H2H_INFO = "https://allsportsapi.com/api/football/?met=H2H&APIkey=!!";
	
	/**
	 * 获取比赛直播数据URL
	 * 参数：countryId
	 * leagueId
	 * matchId
	 */
	public static final String URL_MET_GET_Livescore_INFO = "https://allsportsapi.com/api/football/?met=Livescore&APIkey=!!";
	
	/**
	 * 获取联赛内排名数据URL
	 * 参数：leagueId
	 */
	public static final String URL_MET_GET_Standings_INFO = "https://allsportsapi.com/api/football/?&met=Standings&APIkey=!!";
	
	/**
	 * 获取联盟内最佳射手数据URL
	 * 参数：leagueId
	 */
	public static final String URL_MET_GET_Topscorers_INFO = "https://allsportsapi.com/api/football/?&met=Topscorers&APIkey=!!";
	
	/**
	 * 获取球队数据URL
	 * 参数：leagueId
	 * teamId--必填
	 */
	public static final String URL_MET_GET_Teams_INFO = "https://allsportsapi.com/api/football/?&met=Teams&APIkey=!!";
	
	/**
	 * 获取球员数据URL
	 * 参数：
	 * playerId=球员ID
	 * playerName=球员姓名
	 */
	public static final String URL_MET_GET_Players_INFO = "https://allsportsapi.com/api/football/?&met=Players&APIkey=!!";
	
	/**
	 * 获取比赛视频数据URL
	 * 参数：
	 * eventId=比赛ID
	 */
	public static final String URL_MET_GET_Videos_INFO = "https://allsportsapi.com/api/football/?&met=Videos&APIkey=!!";
	
	/**
	 * 获取ODDS数据URL
	 * 参数：
	 * eventId=比赛ID
	 * from=start date(yyyy-mm-dd)
	 * to=end date(yyyy-mm-dd)
	 * countryId=国家ID
	 * leagueId=联赛ID
	 * matchId=比赛赛程ID
	 */
	public static final String URL_MET_GET_Odds_INFO = "https://allsportsapi.com/api/football/?&met=Odds&&APIkey=!!";
	
	//平台关注的赛事列表
	public static final String[] LEAGUES_UUID = 
		{
		CategoryMainUuid.UCL, 
		CategoryMainUuid.PREMIERLEAGUE,
		CategoryMainUuid.LALIGA,
		CategoryMainUuid.SERIEA,
		CategoryMainUuid.BUNDESLIGA,
		CategoryMainUuid.CSL,
		CategoryMainUuid.AFCCL
		};
	
	public static final String[] LEAGUES_SECONDARY_UUID = 
		{
		 "5e740f9b-e387-43b2-b266-ae0b5d2c87ff",//中甲
		 "4fd62b10-a604-40d2-9f04-2d7e57ea4a0b",//英冠
		 "91cbf042-beb7-45a1-88ff-fbd622a313af",//西乙
		 "4c69b79d-712a-469e-bed9-62ef1e179ad6",//意乙
		 "ec2205e6-bfd4-467d-9916-08eb002c5c87"//德乙
		};
	/**
	 * 接口获取国家信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getCountryInfoList()
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		String url = URL_MET_GET_Countries_INFO.replaceAll("!!", API_KEY);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("请求URL："+url);
		String post = HttpClientUtil.get_ssl(url);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("返回数据："+post);
		result = JSONUtils.json2map(post);
		return result;
	}
	
	/**
	 * 接口获取联赛信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getLeaguesInfoList()
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		String url = URL_MET_GET_Leagues_INFO.replaceAll("!!", API_KEY);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("请求URL："+url);
		String post = HttpClientUtil.get_ssl(url);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("返回数据："+post);
		result = JSONUtils.json2map(post);
		return result;
	}
	
	/**
	 * 接口获取球队信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getTeamInfoList(String leagues)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		String url = URL_MET_GET_Teams_INFO.replaceAll("!!", API_KEY);
		url+="&leagueId="+leagues;
//		LoggerFactory.getLogger(GetInfoUtil.class).info("请求URL："+url);
		String post = HttpClientUtil.get_ssl(url);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("返回数据："+post);
		result = JSONUtils.json2map(post);
		return result;
	}
	
	public static Map<String, Object> getTeamInfoByIdList(String teamId)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		String url = URL_MET_GET_Teams_INFO.replaceAll("!!", API_KEY);
		url+="&teamId="+teamId;
//		LoggerFactory.getLogger(GetInfoUtil.class).info("请求URL："+url);
		String post = HttpClientUtil.get_ssl(url);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("返回数据："+post);
		result = JSONUtils.json2map(post);
		return result;
	}
	
	/**
	 * 接口获取赛程信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getFixturesInfoList(Map<String, String> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		String url = URL_MET_GET_Fixtures_INFO.replaceAll("!!", API_KEY);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("请求URL："+url);
		String post = HttpClientUtil.post(url, params);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("返回数据："+post);
		result = JSONUtils.json2map(post);
		return result;
	}
	
	/**
	 * 接口获取直播信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getLivingInfoList(Map<String, String> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		String url = URL_MET_GET_Livescore_INFO.replaceAll("!!", API_KEY);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("请求URL："+url);
		String post = HttpClientUtil.post(url, params);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("返回数据："+post);
		result = JSONUtils.json2map(post);
		return result;
	}
	
	/**
	 * 接口获取积分榜信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getStandingsInfoList(String leagueId) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		String url = URL_MET_GET_Standings_INFO.replaceAll("!!", API_KEY);
		url+="&leagueId="+leagueId;
//		LoggerFactory.getLogger(GetInfoUtil.class).info("请求URL："+url);
		String post = HttpClientUtil.get_ssl(url);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("返回数据："+post);
		result = JSONUtils.json2map(post);
		return result;
	}
	
	/**
	 * 接口获取射手榜信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getTopscoresInfoList(String leagueId) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		String url = URL_MET_GET_Topscorers_INFO.replaceAll("!!", API_KEY);
		url+="&leagueId="+leagueId;
//		LoggerFactory.getLogger(GetInfoUtil.class).info("请求URL："+url);
		String post = HttpClientUtil.get_ssl(url);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("返回数据："+post);
		result = JSONUtils.json2map(post);
		return result;
	}
	
	/**
	 * 接口获取球员信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getPlayerInfoList(String playerId) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		String url = URL_MET_GET_Players_INFO.replaceAll("!!", API_KEY);
		url+="&playerId="+playerId;
//		LoggerFactory.getLogger(GetInfoUtil.class).info("请求URL："+url);
		String post = HttpClientUtil.get_ssl(url);
//		LoggerFactory.getLogger(GetInfoUtil.class).info("返回数据："+post);
		result = JSONUtils.json2map(post);
		return result;
	}
}
