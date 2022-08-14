package cn.product.worldmall.util.auth;

import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwk.Jwk;

import cn.product.worldmall.util.Base64Util;
import cn.product.worldmall.util.Utlity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

public class AppleAuthUtil {

	public static Logger logger = LoggerFactory.getLogger(AppleAuthUtil.class);
	
	/**
     * 获取苹果的公钥
     * @return
     * @throws Exception
     */
    private static JSONArray getAuthKeys() throws Exception {
        String url = "https://appleid.apple.com/auth/keys";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject json = restTemplate.getForObject(url,JSONObject.class);
        JSONArray arr = json.getJSONArray("keys");
        return arr;
    }

    public static Boolean verify(String jwt) throws  Exception{
        JSONArray arr = getAuthKeys();
        if(arr == null){
            return false;
        }
        JSONObject authKey = null;
        
        //通过jwt的第一段获取pk的kid
        String kidJson = jwt.split("\\.")[0];
        JSONObject kidObject = parserIdentityToken("000."+kidJson);
        String kid = "";
        if(kidObject != null) {
        	kid = kidObject.getString("kid");
        }
        if(!Utlity.checkStringNull(kid)) {
            //根据kid获取pk
            for(int i = 0; i < arr.size(); i++) {
            	authKey = JSONObject.parseObject(arr.getString(i));
            	if(authKey != null) {
                	String id = authKey.getString("kid");
                	if(kid.equals(id)) {
                		break;
                	}
            	}
            }
            if(authKey != null) {
            	return verifyExc(jwt, authKey);
            } else {
            	return false;
            }
        } else {
            //先取苹果第一个key进行校验
            authKey = JSONObject.parseObject(arr.getString(0));
            if(verifyExc(jwt, authKey)){
                return true;
            }else{
                //再取第二个key校验
                authKey = JSONObject.parseObject(arr.getString(1));
                return verifyExc(jwt, authKey);
            }
        }

    }

    /**
     * 对前端传来的identityToken进行验证
     * @param jwt 对应前端传来的 identityToken
     * @param authKey 苹果的公钥 authKey
     * @return
     * @throws Exception
     */
    public static Boolean verifyExc(String jwt, JSONObject authKey) throws Exception {

        Jwk jwa = Jwk.fromValues(authKey);
        PublicKey publicKey = jwa.getPublicKey();

        String aud = "";
        String sub = "";
        if (jwt.split("\\.").length > 1) {
        	JSONObject token = parserIdentityToken(jwt);
        	aud = token.get("aud").toString();
        	sub = token.get("sub").toString();
//            String claim = new String(Base64Util.getFromBase64(jwt.split("\\.")[1]));
//            aud = JSONObject.parseObject(claim).get("aud").toString();
//            sub = JSONObject.parseObject(claim).get("sub").toString();
        }
        JwtParser jwtParser = Jwts.parser().setSigningKey(publicKey);
        jwtParser.requireIssuer("https://appleid.apple.com");
        jwtParser.requireAudience(aud);
        jwtParser.requireSubject(sub);

        try {
            Jws<Claims> claim = jwtParser.parseClaimsJws(jwt);
            if (claim != null && claim.getBody().containsKey("auth_time")) {
                System.out.println(claim);
                return true;
            }
            return false;
        } catch (ExpiredJwtException e) {
            logger.error("apple identityToken expired", e);
            return false;
        } catch (Exception e) {
            logger.error("apple identityToken illegal", e);
            return false;
        }
    }


    /**
     * 获取用户id信息
     * @param identityToken
     * @return
     */
    public static String getSub(String identityToken) {
    	 String[] arr = identityToken.split("\\.");
         String decode = new String (Base64Util.getFromBase64(arr[1]));
         String substring = decode.substring(0, decode.indexOf("}")+1);
         JSONObject jsonObject = JSON.parseObject(substring);
         if(jsonObject != null) {
        	 return jsonObject.getString("sub");
         } else {
        	 return null;
         }
    }

    /**
     * 对前端传来的JWT字符串identityToken的第二部分进行解码
     * 主要获取其中的aud和sub，aud大概对应ios前端的包名，sub大概对应当前用户的授权的openID
     * @param identityToken
     * @return  {"aud":"com.xkj.****","sub":"000***.8da764d3f9e34d2183e8da08a1057***.0***","c_hash":"UsKAuEoI-****","email_verified":"true","auth_time":1574673481,"iss":"https://appleid.apple.com","exp":1574674081,"iat":1574673481,"email":"****@qq.com"}
     */
    public static JSONObject parserIdentityToken(String identityToken){
        String[] arr = identityToken.split("\\.");
        String decode = new String (Base64Util.getFromBase64(arr[1]));
        String substring = decode.substring(0, decode.indexOf("}")+1);
        JSONObject jsonObject = JSON.parseObject(substring);
        return  jsonObject;
    }
}
