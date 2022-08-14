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
	private static long bats_count=0; //�������μ�����
	/*
	public static String smsAddress = "127.0.0.1:8080";
	//ȡ�ö���ƽ̨��IP��ַ
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
	 * ���Ͷ��ŷ���
	 * @param vo
	 * @return
	 * @throws RemoteException 
	 */
	public static String[] sendMessage(SendSMSVO vo) throws Exception{
		String sendType=enfo.crm.tools.Argument.getDictParamName(800211,"800211");
		if (sendType!=null && !"".equals(sendType)){
			//ͨ������ƽ̨���Ͷ���Ϣ
			return SmsClient.sendMessage2smsPlat(vo,sendType);
		}
		int user_id =  new DocumentFile().getUserId();
		String[] result = null;
		if (vo.getPhoneNumber()==null || vo.getPhoneNumber().length()<11){
			result = new String[]{"-4", "�����ֻ�����Ƿ�"};
        	return result;
		}
		if (user_id == 21){//ֱ�Ӱ����ݷŵ����У��ɵ����������ȡ����
        	String sql = "{?=call SP_ADD_SendingSmsTable(?,?,?,?,?,?,?,?)}";
        	Object[] params = new Object[8];
        	params[1]=vo.getPhoneNumber();
        	params[2]=vo.getSmsContent();
        	params[7]=vo.getInputOperator();
        	if (params[1]==null || "".equals(params[1])) return new String[]{"3", "�ֻ�����Ϊ��"};
        	if (params[2]==null || "".equals(params[2])) return new String[]{"4", "��������Ϊ��"};
        	try{
        		enfo.crm.dao.CrmDBManager.cudProc(sql,params);
        	}catch(Exception e){
        		result = new String[]{"2", "���ݱ���ʧ��"};
        		throw e;
        	}
        	result = new String[]{"1", "�ѳɹ��ύ"};
        	return result;
        }
		SendMessageWSStub smsWs = new SendMessageWSStub();				
		SendMessageWSStub.SendMessage sendsms = new SendMessageWSStub.SendMessage();
		SendMessageWSStub.ArrayOfString messageArray= new SendMessageWSStub.ArrayOfString();
		String message = "";
		String[] smsInfo = new String[1];
		if (user_id == 2 ) 
            vo.setSmsContent(vo.getSmsContent()/*+ "���������С�"*/); //20140528������"��������"�ᱻͨѶ���������ζ��·�ʧ��
		//else if (user_id == 17) 
        //    vo.setSmsContent(vo.getSmsContent()+ "�������������С�");
        else if (user_id == 15) 
            vo.setSmsContent(vo.getSmsContent()+ "���������С�");
        String send_time = vo.getSend_time();//yyyy-MM-dd hh:mm:ssת����yyyyMMddhhmmss
		
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		send_time = Utility.replaceAll(send_time,"-","");
		send_time = Utility.replaceAll(send_time," ","");
		send_time = Utility.replaceAll(send_time,":","");
        
        if (user_id == 15 || user_id==17) { // ��������/��������
            message = Utility.trimNull(vo.getPhoneNumber()) + "~~~" + Utility.trimNull(vo.getSmsContent());
        } else {
            message = Utility.trimNull(vo.getSmsUser()) + "_" + Utility.trimNull(vo.getPhoneNumber()) + "_" 
					+ Utility.trimNull(vo.getSmsContent()) + "_" + Utility.trimNull(vo.getUserDefineNo()) + "_"
					+ Utility.trimNull(vo.getNewFlag())  + "_" + Utility.trimNull(vo.getSendLevel())  + "_"
					+ Utility.trimNull(vo.getPutType())  + "_"  + Utility.trimNull(vo.getInputOperator()) + "_"
					+ Utility.trimNull(send_time) //��ʾ��ʱ���͵�ʱ��ΪyyyyMMddhhmmss���Ϊ����Ϊ��ʱ����
					+ "_" + Utility.trimNull(vo.getSmsIndex()) +"_"
					+ Utility.trimNull(vo.getBat_serial_no()) + "_" + Utility.trimNull(vo.getSmstotal()); 
        }
		smsInfo[0] = message;
		System.out.println(message);
		messageArray.setString(smsInfo);
		sendsms.setIn0(message);
		
		result = smsWs.sendMessage(sendsms).getOut().getString();
        
		if (user_id==15) { // ��������
            /*
            ���ŷ�����WS�ķ���ֵ
            00���ɹ�
            01�����루��������50���������ݵ�Ϊ�ջ����ݳ��ȳ���500
            02���������
            07����ҵid��Ч
            10������
            11��δ֪����
            99������������ʧ��
            ������ֵ�����϶��壬����Ϊĳ���֡�����ʱ��ʾ������������
         */
            if ("00".equals(result[0])) { 
            	result = new String[]{"1", "�ѳɹ��ύ"};   
            } else if ("01".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:���루��������50���������ݵ�Ϊ�ջ����ݳ��ȳ���500"};   
            } else if ("02".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:�������"};                
            } else if ("07".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:��ҵid��Ч"};                
            } else if ("10".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:����"};                
            } else if ("11".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:δ֪����"};                
            } else if ("99".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:����������ʧ��"};                
            } else if ("".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:�����������쳣"}; 
            } else {
                result = new String[]{"2", "�ύʧ��:��������"+ result[0]}; 
            }
        } else if (user_id==17) { // ��������
         /*
          ����������WS�ķ���ֵ
           0:  �ѳɹ��ύ
          -1�� �û��Ƿ�
          -2�� û����д����Ȩ��
          -3�� ��Ϣ���ݷǷ�
          -4�� �����ֻ�����Ƿ�
          -20�����Ͷ�������ʧ��
          -21���Ƿ���ַ 
          */
            if ("0".equals(result[0])) { 
                result = new String[]{"1", "�ѳɹ��ύ"};   
            } else if ("-1".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:�û��Ƿ�"};   
            } else if ("-2".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:û����д����Ȩ��"};                
            } else if ("-3".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:��Ϣ���ݷǷ�"};                
            } else if ("-4".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:�����ֻ�����Ƿ�"};                
            } else if ("-20".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:���Ͷ�������ʧ��"};                
            } else if ("-21".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:�Ƿ���ַ"};                
            } else if ("".equals(result[0])) {
                result = new String[]{"2", "�ύʧ��:�����������쳣"}; 
            } else {
                result = new String[]{"2", "�ύʧ��:δ֪����"}; 
            } 
        }
        
		return result;
	}
	
	//ͨ������ƽ̨������
	public static String[] sendMessage2smsPlat(SendSMSVO vo,String sendType) throws Exception{
		String sendURI=enfo.crm.tools.Argument.getDictParamName(800212,"800212");
		if (sendURI==null || "".equals(sendURI))
			return new String[]{"11", "����ƽ̨δ��ȷ����"};
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
			macroJson=URLEncoder.encode(macroJson,"UTF-8");//����URLEncoder����
			//macroJson=URLEncoder.encode(macroJson,"UTF-8");
			String uri=templateCode+"&phoneNumber="+vo.getPhoneNumber()+"&macro="+macroJson
				+"&bats="+theBats+"&batsIndex="+bats_list+"&batsCount="+bats_count+"&sender="+vo.getInputOperator();
			//uri=URLEncoder.encode(uri,"UTF-8");//����URLEncoder����
			//uri=URLEncoder.encode(uri,"UTF-8");
			String url=sendURI+uri;
			return SmsClient.sendMessageDoHttp(url);
		}
		else if ("DB".equals(sendType)){ //DB�ӿڵ�֪ͨ���󣬵������б����ύ����Ӧ�ı���ʱ�����ʹ���Ϣ�Ա�����ƽ̨��ȡ
			String url=sendURI+"&bats="+vo.getBats();
			return SmsClient.sendMessageDoHttp(url);
		}
		else
			return new String[]{"20", "����ƽ̨��֧�ֵĵ��ýӿ�"};
	}
	
	public static String[] sendMessageDoHttp(String url){
		String[] result = null;
		try{
			result = new String[]{"2", "����ʱ"};
			//String response = Request.Post(url).execute().returnContent().asString();
			String response = SmsClient.doGet(url,"UTF-8");
			if (response==null){
				result = new String[]{"2", "����ʱ"};
				return result;
			}
			response=response.trim();
			if ("".equals(response)){
				result = new String[]{"2", "���󷵻ؿ�"};
				return result;
			}
			result[0]=response.substring(0,1);
			if (response.length()>1){
				result[1]=response.substring(1,response.length());
			}else if ("0".equals(response)){
				result[1]="�ύ�ɹ�";
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
	 * ��ѯ���ŷ��ͽ��
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
	 * @return ���� bats_count��
	 */
	public static String getStrBats_count() {
		bats_count++;
		String ret="1"+new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		return ret;
	}
	
}
