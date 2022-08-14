package enfo.crm.web;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.enfo.ws.service.SendMessageWSStub;
import java.net.URLEncoder;
import enfo.crm.tools.Utility;
import enfo.crm.vo.SendSMSVO;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;

public class SmsClient {
	private static long bats_count=0; //短信批次计数器
	/*
	public static String smsAddress = "127.0.0.1:8080";
	//取得短信平台的IP地址
	static{
		String sqlStr = "SELECT SMS_ADDRESS " +
						"FROM TUSERINFO u,TSYSTEMINFO s " +
						"WHERE	u.USER_ID = s.USER_ID";
		List rsList = null;
		try {
			rsList = enfo.crm.dao.CrmDBManager.listBySql(sqlStr);
		} catch (BusiException e) {
			System.err.println(e.getMessage());
		}
		
		if(rsList.size()>0){
			Map map = (Map)rsList.get(0);
			
			if(map!=null){
				smsAddress = Utility.trimNull(map.get("SMS_ADDRESS"));				
			}
		}		
	}
	*/
	
	/**
	 * 发送短信方法
	 * @param vo
	 * @return
	 * @throws RemoteException 
	 */
	public static String[] sendMessage(SendSMSVO vo) throws Exception{
		String sendType=enfo.crm.tools.Argument.getDictParamName(800211,"800211");
		if (sendType!=null && !"".equals(sendType)){
			//通过短信平台发送短消息
			return SmsClient.sendMessage2smsPlat(vo,sendType);
		}
		int user_id =  new DocumentFile().getUserId();
		String[] result = null;
		if (vo.getPhoneNumber()==null || vo.getPhoneNumber().length()<11){
			result = new String[]{"-4", "接收手机号码非法"};
        	return result;
		}
		if (user_id == 21){//直接把数据放到表中，由第三方程序读取发送
        	String sql = "{?=call SP_ADD_SendingSmsTable(?,?,?,?,?,?,?,?)}";
        	Object[] params = new Object[8];
        	params[1]=vo.getPhoneNumber();
        	params[2]=vo.getSmsContent();
        	params[7]=vo.getInputOperator();
        	if (params[1]==null || "".equals(params[1])) return new String[]{"3", "手机号码为空"};
        	if (params[2]==null || "".equals(params[2])) return new String[]{"4", "短信内容为空"};
        	try{
        		enfo.crm.dao.CrmDBManager.cudProc(sql,params);
        	}catch(Exception e){
        		result = new String[]{"2", "数据保存失败"};
        		throw e;
        	}
        	result = new String[]{"1", "已成功提交"};
        	return result;
        }
		SendMessageWSStub smsWs = new SendMessageWSStub();				
		SendMessageWSStub.SendMessage sendsms = new SendMessageWSStub.SendMessage();
		SendMessageWSStub.ArrayOfString messageArray= new SendMessageWSStub.ArrayOfString();
		String message = "";
		String[] smsInfo = new String[1];
		if (user_id == 2 ) 
            vo.setSmsContent(vo.getSmsContent()/*+ "【北京信托】"*/); //20140528据需求"北京信托"会被通讯运行商屏蔽而下发失败
		//else if (user_id == 17) 
        //    vo.setSmsContent(vo.getSmsContent()+ "【方正东亚信托】");
        else if (user_id == 15) 
            vo.setSmsContent(vo.getSmsContent()+ "【建信信托】");
        String send_time = vo.getSend_time();//yyyy-MM-dd hh:mm:ss转换成yyyyMMddhhmmss
		
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		send_time = Utility.replaceAll(send_time,"-","");
		send_time = Utility.replaceAll(send_time," ","");
		send_time = Utility.replaceAll(send_time,":","");
        
        if (user_id == 15 || user_id==17) { // 建信信托/方正信托
            message = Utility.trimNull(vo.getPhoneNumber()) + "~~~" + Utility.trimNull(vo.getSmsContent());
        } else {
            message = Utility.trimNull(vo.getSmsUser()) + "_" + Utility.trimNull(vo.getPhoneNumber()) + "_" 
					+ Utility.trimNull(vo.getSmsContent()) + "_" + Utility.trimNull(vo.getUserDefineNo()) + "_"
					+ Utility.trimNull(vo.getNewFlag())  + "_" + Utility.trimNull(vo.getSendLevel())  + "_"
					+ Utility.trimNull(vo.getPutType())  + "_"  + Utility.trimNull(vo.getInputOperator()) + "_"
					+ Utility.trimNull(send_time) //表示定时发送的时间为yyyyMMddhhmmss如果为空则为及时发送
					+ "_" + Utility.trimNull(vo.getSmsIndex()) +"_"
					+ Utility.trimNull(vo.getBat_serial_no()) + "_" + Utility.trimNull(vo.getSmstotal()); 
        }
		smsInfo[0] = message;
		System.out.println(message);
		messageArray.setString(smsInfo);
		sendsms.setIn0(message);
		
		result = smsWs.sendMessage(sendsms).getOut().getString();
        
		if (user_id==15) { // 建信信托
            /*
            建信发短信WS的返回值
            00：成功
            01：号码（超过上限50个）、内容等为空或内容长度超过500
            02：密码错误
            07：企业id无效
            10：余额不足
            11：未知错误
            99：服务器接收失败
            当返回值非以上定义，表现为某个字、词组时表示内容有屏蔽字
         */
            if ("00".equals(result[0])) { 
            	result = new String[]{"1", "已成功提交"};   
            } else if ("01".equals(result[0])) {
                result = new String[]{"2", "提交失败:号码（超过上限50个）、内容等为空或内容长度超过500"};   
            } else if ("02".equals(result[0])) {
                result = new String[]{"2", "提交失败:密码错误"};                
            } else if ("07".equals(result[0])) {
                result = new String[]{"2", "提交失败:企业id无效"};                
            } else if ("10".equals(result[0])) {
                result = new String[]{"2", "提交失败:余额不足"};                
            } else if ("11".equals(result[0])) {
                result = new String[]{"2", "提交失败:未知错误"};                
            } else if ("99".equals(result[0])) {
                result = new String[]{"2", "提交失败:服务器接收失败"};                
            } else if ("".equals(result[0])) {
                result = new String[]{"2", "提交失败:服务器发生异常"}; 
            } else {
                result = new String[]{"2", "提交失败:含屏蔽字"+ result[0]}; 
            }
        } else if (user_id==17) { // 方正信托
         /*
          方正发短信WS的返回值
           0:  已成功提交
          -1： 用户非法
          -2： 没有自写短信权限
          -3： 信息内容非法
          -4： 接收手机号码非法
          -20：发送短信数据失败
          -21：非法地址 
          */
            if ("0".equals(result[0])) { 
                result = new String[]{"1", "已成功提交"};   
            } else if ("-1".equals(result[0])) {
                result = new String[]{"2", "提交失败:用户非法"};   
            } else if ("-2".equals(result[0])) {
                result = new String[]{"2", "提交失败:没有自写短信权限"};                
            } else if ("-3".equals(result[0])) {
                result = new String[]{"2", "提交失败:信息内容非法"};                
            } else if ("-4".equals(result[0])) {
                result = new String[]{"2", "提交失败:接收手机号码非法"};                
            } else if ("-20".equals(result[0])) {
                result = new String[]{"2", "提交失败:发送短信数据失败"};                
            } else if ("-21".equals(result[0])) {
                result = new String[]{"2", "提交失败:非法地址"};                
            } else if ("".equals(result[0])) {
                result = new String[]{"2", "提交失败:服务器发生异常"}; 
            } else {
                result = new String[]{"2", "提交失败:未知错误"}; 
            } 
        }
        
		return result;
	}
	
	//通过短信平台发短信
	public static String[] sendMessage2smsPlat(SendSMSVO vo,String sendType) throws Exception{
		String sendURI=enfo.crm.tools.Argument.getDictParamName(800212,"800212");
		if (sendURI==null || "".equals(sendURI))
			return new String[]{"11", "短信平台未正确配置"};
		if ("HTTP".equals(sendType)){
			String templateCode=vo.getTemplateCode();
			String macroJson="";
			if (templateCode==null || "".equals(templateCode)){
				templateCode="&templateId=0001";
				macroJson="{\"content\":\""+vo.getSmsContent()+"\"}";
			}else{
				templateCode="&templateId="+templateCode;
				macroJson=vo.getMacroJson();
			}
			String theBats=vo.getBats();
			Integer bats_list=vo.getBat_serial_no();
			Integer bats_count=vo.getSmstotal();
			if (theBats==null || "".equals(theBats)){
				theBats=SmsClient.getStrBats_count();
				SmsClient.bats_count++;
				bats_list=new Integer(1);
				bats_count=new Integer(1);
			}
			macroJson=URLEncoder.encode(macroJson,"UTF-8");//进行URLEncoder编码
			//macroJson=URLEncoder.encode(macroJson,"UTF-8");
			String uri=templateCode+"&phoneNumber="+vo.getPhoneNumber()+"&macro="+macroJson
				+"&bats="+theBats+"&batsIndex="+bats_list+"&batsCount="+bats_count+"&sender="+vo.getInputOperator();
			//uri=URLEncoder.encode(uri,"UTF-8");//进行URLEncoder编码
			//uri=URLEncoder.encode(uri,"UTF-8");
			String url=sendURI+uri;
			return SmsClient.sendMessageDoHttp(url);
		}
		else if ("DB".equals(sendType)){ //DB接口的通知请求，当待发列表已提交到相应的表中时，发送此消息以备短信平台读取
			String url=sendURI+"&bats="+vo.getBats();
			return SmsClient.sendMessageDoHttp(url);
		}
		else
			return new String[]{"20", "短信平台不支持的调用接口"};
	}
	
	public static String[] sendMessageDoHttp(String url){
		String[] result = null;
		try{
			result = new String[]{"2", "请求超时"};
			//String response = Request.Post(url).execute().returnContent().asString();
			String response = SmsClient.doGet(url,"UTF-8");
			if (response==null){
				result = new String[]{"2", "请求超时"};
				return result;
			}
			response=response.trim();
			if ("".equals(response)){
				result = new String[]{"2", "请求返回空"};
				return result;
			}
			result[0]=response.substring(0,1);
			if (response.length()>1){
				result[1]=response.substring(1,response.length());
			}else if ("0".equals(response)){
				result[1]="提交成功";
			}else{
				result[1]="";
			}
			
		}catch (Exception e){
			e.printStackTrace();
			result = new String[]{"-1", e.getMessage()};
		}
		return result;
	}
	
	public static String doGet(String url,String charSet) throws Exception{
		HttpURLConnection httpURLConn=null;
		String res="";
		try{
            String temp = new String();
            URL url0 = new  URL(url);
            httpURLConn= (HttpURLConnection)url0.openConnection();
            httpURLConn.setDoOutput(true);
            httpURLConn.setRequestMethod("GET");
            //httpURLConn.setIfModifiedSince(999999999);
            //httpURLConn.setRequestProperty("Referer", "http://localhost:80");
            //httpURLConn.setRequestProperty("User-Agent", "test");
            httpURLConn.connect();
            InputStream in =httpURLConn.getInputStream();
            StringBuffer sb = new StringBuffer();
            byte[] bt= new byte[2048];
            //BufferedReader bd = new BufferedReader(new InputStreamReader(in));
            while(in.read(bt)>0){
            	sb.append(new String(bt,charSet));
            }
            res=sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        finally{
            if(httpURLConn!=null){
                httpURLConn.disconnect();
            }
        }
        return res.trim();
    }
	
	
	/**
	 * 查询短信发送结果
	 * @param smsIndex
	 * @return
	 * @throws RemoteException
	 */
	public static String[] querySMS(Integer smsIndex) throws RemoteException{
		SendMessageWSStub smsWs = new SendMessageWSStub();				
		SendMessageWSStub.QuerySMS qs = new SendMessageWSStub.QuerySMS();
		String[] result = null;
		
		qs.setIn0(smsIndex.intValue());
		result = smsWs.querySMS(qs).getOut().getString();
		return result;
	}
	
	/**
	 * @return 返回 bats_count。
	 */
	public static String getStrBats_count() {
		bats_count++;
		String ret="1"+new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		return ret;
	}
	
}
