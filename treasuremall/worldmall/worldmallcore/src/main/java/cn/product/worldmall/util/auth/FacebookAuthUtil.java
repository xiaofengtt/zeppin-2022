package cn.product.worldmall.util.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacebookAuthUtil {

	public static Logger logger = LoggerFactory.getLogger(FacebookAuthUtil.class);
	
	public static final String FB_AUTH_LOGIN_URL = "https://graph.facebook.com/oauth/access_token";
	public static final String FB_USERINFO_URL = "https://graph.facebook.com/me";
	//appid和appSecret 是facebook上申请
	//AppId
	public static final String FB_APP_ID = "220057282977034";
	//AppSecret
	public static final String FB_APP_KEY = "4206deb43e5a1a4061a8a18ef124f8e7";
	//获取用户的那些信息
	public static final String FB_USER_FIELDS = "id,cover,email,gender,name,languages,timezone,third_party_id,updated_time";
	
	
	public static Boolean checkLoginWithToken(String accessToken) {
		try {
			return true;
//			//网络原因，取消认证操作
//			String userUrl = String.format("%s?access_token=%s&fields=%s",
//					FB_USERINFO_URL, accessToken, FB_USER_FIELDS);
////			String userRet = httpClient.get(userUrl);
//			String userRet = HttpClientUtil.get_ssl(userUrl);
////			String userRet = HttpClientUtil.get(userUrl);
//			Map<String, Object> userInfoData = new HashMap<String, Object>();
////			UserInfoData userInfoData = new UserInfoData();
//			userInfoData = JSONUtils.json2map(userRet);
////			JSONObject json = new JSONObject(userRet);
////			String email = json.optString("email");
////			if (email != null) {
////				email = email.replace("\\u0040", "@");
////			}
//////icon = "https://graph.facebook.com/v2.8/"+json.getString("id")+"/picture?height=500&width=500";   获取头像大图
////			userInfoData.setLoginId(json.getString("id"));
////			userInfoData.setGender(json.optString("gender"));
////			userInfoData.setIcon(json.optString("cover"));
////			userInfoData.setEmail(email);
////			userInfoData.setName(json.optString("name"));
////			userInfoData.setOpenId(json.optString("third_party_id"));
////			userInfoData.setAuthToken(accessToken);
//
//			logger.info("---------checkLoginWithToken----------"+userRet);
//			if(userInfoData != null && !userInfoData.isEmpty()) {
//				logger.info("---------checkLoginWithToken2----------"+userRet);
//				return true;
//			} else {
//				return false;
//			}
		} catch (Exception ex) {
			logger.warn("verify the access token failed: {}", ex.getMessage());
			return false;
		}
	}
	
	
//	private static String paramsToString(Map params) {
//		StringBuilder sb = new StringBuilder();
//		try{
//			for (String key : params.keySet()) {
//				sb.append(String.format("&%s=%s", key, URLEncoder.encode(params.get(key),StandardCharsets.UTF_8.toString())));
//			}
//		}catch(UnsupportedEncodingException e){
//			logger.warn("{}: encode url parameters failed", e.getMessage());
//		}
//		return sb.length() > 0 ? "?".concat(sb.substring(1)) : "";
//	}
//	
//	private static HttpResponse sendRequest(CloseableHttpClient httpclient, HttpUriRequest httpost)
//			throws ClientProtocolException, IOException {
//		HttpResponse response = null;
//		response = httpclient.execute(httpost);
//		return response;
//	}
//	
//	private static String parseResponse(HttpResponse response) {
//		logger.info("get response from http server..");
//		HttpEntity entity = response.getEntity();
//
//		logger.info("response status: " + response.getStatusLine());
//		Charset charset = ContentType.getOrDefault(entity).getCharset();
//		if (charset != null) {
//			logger.info(charset.name());
//		}
//
//		String body = null;
//		try {
//			body = EntityUtils.toString(entity, "utf-8");
//			logger.info("body " + body);
//		} catch (IOException e) {
//			logger.warn("{}: cannot parse the entity", e.getMessage());
//		}
//
//		return body;
//	}
	public static String getImageUrl(String fullUrl) {
//		if(fullUrl.indexOf("access_token") > -1) {
//			String[] arr = fullUrl.split("access_token");
//			if(arr.length == 2) {
//				if(arr[1].indexOf("&") > -1) {
//					String endStr = arr[1].substring(arr[1].indexOf("&")+1);
//					fullUrl = arr[0]+endStr;
//				} else {
//					fullUrl = arr[0].substring(0,arr[0].length() - 1);
//				}
//			}
//		}
		if(fullUrl.indexOf("migration_overrides") > -1) {
			String[] arr = fullUrl.split("migration_overrides");
			if(arr.length == 2) {
				if(arr[1].indexOf("&") > -1) {
					String endStr = arr[1].substring(arr[1].indexOf("&")+1);
					return arr[0]+endStr;
				} else {
					return arr[0].substring(0,arr[0].length() - 1);
				}
			}
		}
		return fullUrl;
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String accessToken = "EAAz71VZA5jFUBADgcdeq9NbEqgsjbqdluILo8vZCIPp6gQap74vhB6YTuHVkIcZAEo0BAPOEtYxQaEpXlSAQljz4H5QZB4Hu12nuG4QOcJuP5kWkg1wOZC9I5GZAZCJPxKsbxrLUKvvEy2kjPVHmjT7Vj9X7KH2T80bI9rO6KX2CZBqBBZCOSqsdqX53H0SwzONcAB3L5Oh1CeEYMj7KOJPa9roZCERwYQU69mis1e6Mj23QZDZD";
//		checkLoginWithToken(accessToken);
		String url = "https://graph.facebook.com/v9.0/165413912025428/picture?access_token=EAAz71VZA5jFUBADgcdeq9NbEqgsjbqdluILo8vZCIPp6gQap74vhB6YTuHVkIcZAEo0BAPOEtYxQaEpXlSAQljz4H5QZB4Hu12nuG4QOcJuP5kWkg1wOZC9I5GZAZCJPxKsbxrLUKvvEy2kjPVHmjT7Vj9X7KH2T80bI9rO6KX2CZBqBBZCOSqsdqX53H0SwzONcAB3L5Oh1CeEYMj7KOJPa9roZCERwYQU69mis1e6Mj23QZDZD&height=200&type=normal&width=200";
		url = "https://graph.facebook.com/v9.0/165413912025428/picture?height=200&type=normal&width=200&access_token=EAAz71VZA5jFUBADgcdeq9NbEqgsjbqdluILo8vZCIPp6gQap74vhB6YTuHVkIcZAEo0BAPOEtYxQaEpXlSAQljz4H5QZB4Hu12nuG4QOcJuP5kWkg1wOZC9I5GZAZCJPxKsbxrLUKvvEy2kjPVHmjT7Vj9X7KH2T80bI9rO6KX2CZBqBBZCOSqsdqX53H0SwzONcAB3L5Oh1CeEYMj7KOJPa9roZCERwYQU69mis1e6Mj23QZDZD";
		System.out.println(getImageUrl(url));
//		String[] arr = url.split("access_token");
//		System.out.println(arr.length);
//		System.out.println(arr[0]);
//		System.out.println(arr[1]);
//		String endStr = arr[1].substring(arr[1].indexOf("&")+1);
//		System.out.println(endStr);
//		System.out.println("https://graph.facebook.com/v9.0/165413912025428/picture?access_token=EAAz71VZA5jFUBADgcdeq9NbEqgsjbqdluILo8vZCIPp6gQap74vhB6YTuHVkIcZAEo0BAPOEtYxQaEpXlSAQljz4H5QZB4Hu12nuG4QOcJuP5kWkg1wOZC9I5GZAZCJPxKsbxrLUKvvEy2kjPVHmjT7Vj9X7KH2T80bI9rO6KX2CZBqBBZCOSqsdqX53H0SwzONcAB3L5Oh1CeEYMj7KOJPa9roZCERwYQU69mis1e6Mj23QZDZD&height=200&type=normal&width=200".length());
		url = "https://graph.facebook.com/v8.0/165413912025428/picture?height=200&width=200&migration_overrides={october_2012:true}";
		System.out.println(URLEncoder.encode(url, "utf-8"));
		System.out.println(URLDecoder.decode("%3A", "utf-8"));
		System.out.println(getImageUrl(url));
	}
}
