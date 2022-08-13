package cn.zeppin.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

//import _147._7._83._222.ecs.service.APUserSMS_jws.APUserSMS;
import sun.misc.BASE64Encoder;

/**
 * 发送短信
 * 说明:		http://api.duanxin.cm/?ac=send&username=用户账号&password=MD5位32密码&phone=号码&content=内容
 * 状态:
 * 100 发送成功
 * 101 验证失败
 * 102 短信不足
 * 103 操作失败
 * 104 非法字符
 * 105 内容过多
 * 106 号码过多
 * 107 频率过快
 * 108 号码内容空
 * 109 账号冻结
 * 110 禁止频繁单条发送
 * 111 系统暂定发送
 * 112 号码不正确
 * 120 系统升级
 * 
 */
public class SendSms {
	
	public static String sendSms(String content,String mobile){
//		String path = "../\\build\\classes\\smss.xml";
//		path = path.replaceAll("%20", " ");
//		DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
//		Element root = null;
//		StringBuilder url = new StringBuilder();
//		String uid = "";
//		String pwd = "";
//		try {
//			dBuilderFactory.setIgnoringElementContentWhitespace(true);
//			DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
//
//			Document document = dBuilder.parse(new File(path));
//			root = document.getDocumentElement();
//
//			uid = document.getElementsByTagName("uid").item(0).getFirstChild().getNodeValue();
//			pwd = root.getElementsByTagName("pwd").item(0).getFirstChild().getNodeValue();
//			url.append(document.getElementsByTagName("url").item(0).getFirstChild().getNodeValue());
//		} catch (Exception e) {
//			// TODO: handle exception
//			String eString = e.getMessage();
//			System.out.print(eString);
//
//		}
//		try {
//			String xmlStr = getXmlStr(mobile, content);
//			
//			String url = "http://222.83.7.147:8080/ecs/service/APUserSMS.jws?wsdl";
////			
////			URL urls = new URL(url);
////			
////			HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
////			connection.setRequestMethod("POST");
//			
////			APUserSMS apu = new APUserSMS() {
////				
////				@Override
////				public String sendStatQuery(String xmlStr) throws RemoteException {
////					// TODO Auto-generated method stub
////					return null;
////				}
////				
////				@Override
////				public String sendMessage(String xmlStr) throws RemoteException {
////					// TODO Auto-generated method stub
////					return null;
////				}
////				
////				@Override
////				public String moQuery(String xmlStr) throws RemoteException {
////					// TODO Auto-generated method stub
////					return null;
////				}
////			};
//			
//			Service service = new Service();
//			Call call = (Call) service.createCall();
//			
//			call.setTimeout(new Integer(5000));
//			call.setTargetEndpointAddress(url);
//			call.setOperationName("sendMessage");
//			call.addParameter("xmlStr", XMLType.XSD_STRING,ParameterMode.IN);
//			
//			
//			call.setReturnType(XMLType.XSD_STRING);
//			String result = (String)call.invoke(new Object[]{xmlStr});
//			
////			System.out.println("000000-----------"+result);
//			
//			return getRsult(result);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return "";
//		}
		
		StringBuilder url = new StringBuilder();
//		String uid = "70208580";
//		String pwd = "b3aae0eeeb290755569d22c167c77f63";
		String uid = "70211188";
		String pwd = "04ad2d52a0176ab18ed14e468bf70661";
		url.append("http://api.duanxin.cm/?");
		try {
			content = URLEncoder.encode(content, "utf-8");
			// 向StringBuffer追加用户名
			url.append("action=send&username="+uid);
			
			// 向StringBuffer追加密码（密码采用MD5 32位 小写）
			url.append("&password="+pwd);

			// 向StringBuffer追加手机号码
			url.append("&phone="+mobile);

			// 向StringBuffer追加消息内容转URL标准码
			url.append("&content="+content);
			
			// 向StringBuffer追加字符集编码
			url.append("&encode=utf8");
			
			// 创建url对象
			URL urls = new URL(url.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) urls.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(urls.openStream()));

			// 返回发送结果
			String inputline = in.readLine();
			return getRsult(inputline);
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/**
	 * 获取发送的xml数据
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static String getXmlStr(String mobile,String content){
		
		
		content = getBase64(content);//转换base64编码
		
		StringBuilder xmlStr = new StringBuilder("<?xml version='1.0' encoding='GB2312' ?>");//重组xml信息
		
		xmlStr.append("<request>");
		xmlStr.append("<spnumber>09914336811</spnumber>");
		xmlStr.append("<uid>09914336811</uid>");
		xmlStr.append("<pwd>d66e2afd3a7ac1077379822d09ec31af</pwd>");
		xmlStr.append("<feemobile>09914336811</feemobile>");
		xmlStr.append("<ismobilesend>0</ismobilesend>");
		xmlStr.append("<mobile>");
		xmlStr.append(mobile);
		xmlStr.append("</mobile>");
		xmlStr.append("<msg>");
		xmlStr.append(content);
		xmlStr.append("</msg>");
		xmlStr.append("</request>");
		
		return xmlStr.toString();
	}
	
	 /**
	  *  加密  
	  * @param str
	  * @return
	  */
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
        	b = str.getBytes("GB2312");
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
    
    /**
     * 
     * @param result
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getRsult(String result){
    	String status = "";
//    	try {
//    		Document doc = DocumentHelper.parseText(result);
//    		Element books = doc.getRootElement();
////    		System.out.println("根节点"+books.getName());
//
//    		
//    		
//			List<Element> elements  = books.elements();
////			System.out.println(elements.size());
//			Element childNode = elements.get(0);
//			Element childNode1 = elements.get(1);
//			String resultValue = childNode.getTextTrim();
//			String msgValue = childNode1.getTextTrim();
//			
////			System.out.println(resultValue+" : "+msgValue);
//			
//			//遍历根节点的子节点
////			for(Iterator<Element> it = elements.iterator();it.hasNext();){
////				Element element = it.next();  
////	            System.out.println(element.getName()+" : "+element.getTextTrim());
////			}
//
//			status = resultValue+"_"+msgValue;
//			
//			return status;
//    		
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return status;
//		}
    	try {
    		
    		switch (result) {
			case "100":
				status = 0+"_"+"发送成功";
				break;
			case "101":
				status = result+"_"+"验证失败";
				break;
			case "102":
				status = result+"_"+"短信不足";
				break;
			case "103":
				status = result+"_"+"操作失败";
				break;
			case "104":
				status = result+"_"+"非法字符";
				break;
			case "105":
				status = result+"_"+"内容过多";
				break;
			case "106":
				status = result+"_"+"号码过多";
				break;
			case "107":
				status = result+"_"+"频率过快";
				break;
			case "108":
				status = result+"_"+"号码内容空";
				break;
			case "109":
				status = result+"_"+"账号冻结";
				break;
			case "110":
				status = result+"_"+"禁止频繁单条发送";
				break;
			case "111":
				status = result+"_"+"系统暂定发送";
				break;
			case "112":
				status = result+"_"+"号码不正确";
				break;
			case "120":
				status = result+"_"+"系统升级";
				break;
			default:
				status = 0+"_"+0;
				break;
			}
    		
    		return status;
			
		} catch (Exception e) {
			
			// TODO: handle exception
			return status;
		}
    }
    
    public static String getXmlStr2(){
		
		
//		content = getBase64(content);//转换base64编码
		
		StringBuilder xmlStr = new StringBuilder("<?xml version='1.0' encoding='GB2312' ?>");//重组xml信息
		
		xmlStr.append("<request>");
		xmlStr.append("<spnumber>09914336811</spnumber>");
		xmlStr.append("<uid>09914336811</uid>");
		xmlStr.append("<pwd>d66e2afd3a7ac1077379822d09ec31af</pwd>");
		xmlStr.append("<msgid>46978785</msgid>");
		xmlStr.append("</request>");
		
		return xmlStr.toString();
	}	
    
    public static String getStatus(){
    	
    	try {
			
    		String xmlStr = getXmlStr2();
			
			String url = "http://222.83.7.147:8080/ecs/service/APUserSMS.jws?wsdl";
			
			Service service = new Service();
			Call call = (Call) service.createCall();
			
			call.setTimeout(new Integer(5000));
			call.setTargetEndpointAddress(url);
			call.setOperationName("sendStatQuery");
			call.addParameter("xmlStr", XMLType.XSD_STRING,ParameterMode.IN);
			
			
			call.setReturnType(XMLType.XSD_STRING);
			String result = (String)call.invoke(new Object[]{xmlStr});
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		} 
			
			
    	
    	return "";
    }
    
}






