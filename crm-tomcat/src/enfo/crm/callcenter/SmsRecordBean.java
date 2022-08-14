package enfo.crm.callcenter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Argument;
import enfo.crm.tools.Utility;
import enfo.crm.vo.SendSMSVO;
import enfo.crm.web.SendMail2;
import enfo.crm.web.SmsClient;

@Component(value="smsRecord")
public class SmsRecordBean extends enfo.crm.dao.CrmBusiExBean implements SmsRecordLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#append(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public Integer append(SendSMSVO vo) throws BusiException{
		String sqlStr = "{?=call SP_ADD_TSmsRecord(?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[7];
		Integer newId = null;
		
		params[0] = vo.getSmsUser();
		params[1] = vo.getPhoneNumber();
		params[2] = vo.getSmsContent();
		params[3] = vo.getSendLevel();
		params[4] = vo.getPutType();
		params[5] = vo.getSerial_no_detail();
		params[6] = vo.getInputOperator();
		
		try {
			newId = (Integer)super.cudProc(sqlStr, params,9,java.sql.Types.INTEGER);
		} catch (BusiException e) {
			throw new BusiException("保存发送短信日志失败:" + e.getMessage());
		}

		return newId;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#modi(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public void modi(SendSMSVO vo) throws BusiException{
		String sqlStr = "{?=call SP_MODI_TSmsRecord(?,?,?,?)}";
		Object[] params = new Object[4];
		
		params[0] = vo.getSmsIndex();
		params[1] = vo.getStatus();
		params[2] = vo.getStatusName();
		params[3] = vo.getInputOperator();
		
		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("编辑发送短信日志失败:" + e.getMessage());
		}		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#query(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public List query(SendSMSVO vo) throws BusiException{
		List rsList = null;
		Object[] params = new Object[12];
		String sqlStr = "{call SP_QUERY_TSmsRecord(?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		params[0] = vo.getSmsIndex();
		params[1] = vo.getSmsUser();
		params[2] = vo.getPhoneNumber();
		params[3] = vo.getSmsContent();
		params[4] = vo.getSendLevel();
		params[5] = vo.getPutType();
		params[6] = vo.getCust_name();
		params[7] = vo.getSerial_no();
		params[8] = vo.getSerial_no_detail();
		
		params[9] = vo.getServiceTitle();
		params[10] = vo.getDate_1();
		params[11] = vo.getDate_2();
		try {
			rsList = super.listProcAll(sqlStr,params);
		} catch (BusiException e) {
			throw new BusiException("查询发送短信日志失败:" + e.getMessage());
		}
		return rsList;	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#query_page(enfo.crm.vo.SendSMSVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList query_page (SendSMSVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;
		Object[] params = new Object[12];
		String sqlStr = "{call SP_QUERY_TSmsRecord(?,?,?,?,?,?,?,?,?,?,?,?)}";		
		params[0] = vo.getSmsIndex();
		params[1] = vo.getSmsUser();
		params[2] = vo.getPhoneNumber();
		params[3] = vo.getSmsContent();
		params[4] = vo.getSendLevel();
		params[5] = vo.getPutType();
		params[6] = vo.getCust_name();
		params[7] = vo.getSerial_no();
		params[8] = vo.getSerial_no_detail();
		
		params[9] = vo.getServiceTitle();
		params[10] = vo.getDate_1();
		params[11] = vo.getDate_2();
		
		try {
			rsList = super.listProcPage(sqlStr,params,totalColumn,pageIndex,pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询发送短信日志失败:" + e.getMessage());
		}
		return rsList;	
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#queryMessageList(enfo.crm.vo.SendSMSVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList queryMessageList(SendSMSVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;
		Object[] params = new Object[10];
		String sqlStr = "{call SP_QUERY_TDIRECTSENDTOTAL(?,?,?,?,?,?,?,?,?,?)}";
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getWay_type();
		params[2] = vo.getBegin_plan_time();
		params[3] = vo.getEnd_plan_time();
		params[4] = vo.getMobiles();
		params[5] = vo.getContent_templet();
		params[6] = vo.getCust_name();
		params[7] = vo.getCheck_flag();	
		params[8] = vo.getCheck_man();
		params[9] = vo.getInputOperator();
		try {
			rsList =  super.listProcPage(sqlStr,params,totalColumn,pageIndex,pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询发送短信日志失败:" + e.getMessage());
		}
		return rsList;	
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#queryMessage(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public List queryMessage(SendSMSVO vo) throws BusiException{
		List rsList = null;
		Object[] params = new Object[10];
		String sqlStr = "{call SP_QUERY_TDIRECTSENDTOTAL(?,?,?,?,?,?,?,?,?,?)}";
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getWay_type();
		params[2] = vo.getBegin_plan_time();
		params[3] = vo.getEnd_plan_time();
		params[4] = vo.getMobiles();
		params[5] = vo.getContent_templet();
		params[6] = vo.getCust_name();
		params[7] = vo.getCheck_flag();	
		params[8] = vo.getCheck_man();
		params[9] = vo.getInputOperator();
		try {
			rsList = super.listProcAll(sqlStr,params);
		} catch (BusiException e) {
			throw new BusiException("查询发送短信日志失败:" + e.getMessage());
		}
		return rsList;	
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#appendMessage(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public Integer appendMessage(SendSMSVO vo) throws BusiException{
		//String sqlStr = "{?=call SP_ADD_TDIRECTSENDTOTAL(?,?,?,?,?,?,?,?,?,?,?,?)}";
		String sqlStr = "{?=call SP_ADD_TDIRECTSENDTOTAL(?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[9];
		Integer newId = null;

		params[0] = vo.getWay_type();
		params[1] = vo.getSend_type();
		params[2] = vo.getPlan_time();
		params[3] = vo.getMsg_type();
		params[4] = vo.getMobiles();
		params[5] = vo.getContent_templet();
		params[6] = vo.getCheck_flag();
		params[7] = vo.getCheck_man();
		params[8] = vo.getInputOperator();
		//params[9] = vo.getTemplateCode();
		//params[10] = vo.getBats();
		try {
			newId = (Integer)super.cudProc(sqlStr, params,11,java.sql.Types.INTEGER);
		} catch (BusiException e) {
			throw new BusiException("保存发送信息失败:" + e.getMessage());
		}

		return newId;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#modiMessage(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public void modiMessage(SendSMSVO vo) throws BusiException{
		String sqlStr = "{?=call SP_MODI_TDIRECTSENDTOTAL(?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[10];
		Integer newId = null;
		params[0] = vo.getSerial_no();
		params[1] = vo.getWay_type();
		params[2] = vo.getSend_type();
		params[3] = vo.getPlan_time();
		params[4] = vo.getMsg_type();
		params[5] = vo.getMobiles();
		params[6] = vo.getContent_templet();
		params[7] = vo.getCheck_flag();
		params[8] = vo.getCheck_man();
		params[9] = vo.getInputOperator();
		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("保存发送信息失败:" + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#updateResult(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public void updateResult(SendSMSVO vo) throws BusiException{
		String sqlStr = "UPDATE TDIRECTSENDTOTAL SET SEND_RESULT = '"+ Utility.trimNull(vo.getSend_result()) + "' WHERE  SERIAL_NO = "+ vo.getSerial_no();
		Utility.debug(sqlStr);
		try {
			super.executeSql(sqlStr);
		} catch (BusiException e) {
			throw new BusiException("更新发送记录状态失败:" + e.getMessage());
		}
	}	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#checkMessage(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public void checkMessage(SendSMSVO vo) throws BusiException{
		String sqlStr = "{?=call SP_CHECK_TDIRECTSENDTOTAL(?,?,?)}";
		Object[] params = new Object[3];
		Integer newId = null;
		params[0] = vo.getSerial_no();
		params[1] = vo.getCheck_flag();
		params[2] = vo.getInputOperator();
		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("保存发送信息失败:" + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#deleteMessage(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public void deleteMessage(SendSMSVO vo) throws BusiException{
		String sqlStr = "{?=call SP_DEL_TDIRECTSENDTOTAL(?,?)}";
		Object[] params = new Object[2];
		Integer newId = null;
		params[0] = vo.getSerial_no();
		params[1] = vo.getInputOperator();
		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("保存发送信息失败:" + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#queryMessageDetail(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public List queryMessageDetail(SendSMSVO vo) throws BusiException{
		List rsList = null;
		Object[] params = new Object[3];
		String sqlStr = "{call SP_QUERY_TDIRECTSENDDETAIL(?,?,?)}";
		params[0] = vo.getSerial_no();
		params[1] = vo.getSerial_no_total();
		params[2] = vo.getInputOperator();;
		try {
			rsList = super.listProcAll(sqlStr,params);
		} catch (BusiException e) {
			throw new BusiException("查询发送短信日志失败:" + e.getMessage());
		}
		return rsList;	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#queryMessageDetail_m(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public List queryMessageDetail_m(SendSMSVO vo) throws BusiException{
		List rsList = null;
		Object[] params = new Object[3];
		String sqlStr = "{call SP_QUERY_TDIRECTSENDDETAIL_M(?,?,?)}";
		params[0] = vo.getSerial_no();
		params[1] = vo.getSerial_no_total();
		params[2] = vo.getInputOperator();;
		try {
			rsList = super.listProcAll(sqlStr,params);
		} catch (BusiException e) {
			throw new BusiException("查询发送短信日志失败:" + e.getMessage());
		}
		return rsList;	
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#addMessageCustid(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public void addMessageCustid(SendSMSVO vo) throws BusiException{
		String sqlStr = "{?=call SP_ADD_TDIRECTSENDDETAIL(?,?,?)}";
		Object[] params = new Object[3];
		
		params[0] = vo.getSerial_no_total();
		params[1] = vo.getCust_id();
		params[2] = vo.getInputOperator();	
		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("编辑发送短信日志失败:" + e.getMessage());
		}		
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#deleteMessageDetail(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public void deleteMessageDetail(SendSMSVO vo) throws BusiException{
		String sqlStr = "{?=call SP_DEL_TDIRECTSENDDETAIL(?,?)}";
		Object[] params = new Object[2];
		Integer newId = null;
		params[0] = vo.getSerial_no();
		params[1] = vo.getInputOperator();
		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("保存发送信息失败:" + e.getMessage());
		}
	}
	
	public void sendSms(Integer serial_no, Integer input_man) throws BusiException {
	    String[] sendBackInfo = (String[])null;
	    String content_templet = null;
	    Integer send_type = null;
	    String plan_time = null;
	    String mobiles = null;
	    List rsList = null;
	    Map rsMap = null;
	    try
	    {
	      SendSMSVO total_vo = new SendSMSVO();
	      total_vo.setSerial_no(serial_no);
	      total_vo.setInputOperator(input_man);
	      rsList = queryMessage(total_vo);
	      if (rsList.size() > 0) {
	        rsMap = (Map)rsList.get(0);
	      }
	      if (rsMap != null)
	      {
	        Integer way_type = Utility.parseInt(Utility.trimNull(rsMap.get("WAY_TYPE")), new Integer(0));
	        send_type = Utility.parseInt(Utility.trimNull(rsMap.get("SEND_TYPE")), new Integer(0));
	        plan_time = Utility.trimNull(rsMap.get("PLAN_TIME"));
	        String msg_type = Utility.trimNull(rsMap.get("MSG_TYPE"));
	        content_templet = Utility.trimNull(rsMap.get("CONTENT_TEMPLET"));
	        Integer check_man = Utility.parseInt(Utility.trimNull(rsMap.get("CHECK_MAN")), new Integer(0));
	        mobiles = Utility.trimNull(rsMap.get("MOBILES"));
	      }
	      SendSMSVO detail_vo = new SendSMSVO();
	      detail_vo.setSerial_no_total(serial_no);
	      detail_vo.setInputOperator(input_man);
	      List custList = queryMessageDetail_m(detail_vo);
	      
	      SendSMSVO vo_sms = new SendSMSVO();
	      vo_sms.setSmsUser("");
	      vo_sms.setSmsUser(Utility.trimNull(input_man));
	      vo_sms.setPutType("待发");
	      vo_sms.setNewFlag(new Integer(1));
	      vo_sms.setSendLevel(new Integer(1));
	      vo_sms.setInputOperator(input_man);
	      if (send_type.intValue() == 2)
	      {
	        if (plan_time.length() > 0) {
	          plan_time = plan_time.substring(0, 19);
	        }
	        vo_sms.setSend_time(plan_time);
	      }
	      else
	      {
	        vo_sms.setSend_time("");
	      }
	      if (!"".equals(Utility.trimNull(mobiles)))
	      {
	        mobiles = Utility.replaceAll(mobiles, "；", ";");
	        String[] mobile = Utility.splitString(mobiles, ";");
	        for (int i = 0; i < mobile.length; i++) {
	          if (mobile[i].length() >= 11)
	          {
	            vo_sms.setPhoneNumber(Utility.trimNull(mobile[i]));
	            vo_sms.setSmsContent(content_templet);
	            sendBackInfo = SmsClient.sendMessage(vo_sms);
	          }
	        }
	      }
	      else
	      {
	        for (int i = 0; i < custList.size(); i++)
	        {
	          Map custMap = (Map)custList.get(i);
	          String cust_name = Utility.trimNull(custMap.get("CUST_NAME"));
	          String send_content = Utility.replaceAll(content_templet, "%1", cust_name);
	          vo_sms.setPhoneNumber(Utility.trimNull(custMap.get("MOBILE")));
	          vo_sms.setSmsContent(send_content);
	          if (Utility.trimNull(custMap.get("MOBILE")).length() >= 11) {
	            sendBackInfo = SmsClient.sendMessage(vo_sms);
	          }
	        }
	      }
	      if (sendBackInfo != null) {
	        if (sendBackInfo.length >= 2) {
	          total_vo.setSend_result(sendBackInfo[0] + "--" + sendBackInfo[1]);
	        } else {
	          total_vo.setSend_result("0");
	        }
	      }
	      updateResult(total_vo);
	    }
	    catch (Exception e)
	    {
	      throw new BusiException("发送短信失败:" + e.getMessage());
	    }
	}
		  
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#sendSms(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void sendSms(Integer serial_no,Integer input_man,Integer product_id) throws BusiException{
		String sendBackInfo[] = null;
		String content_templet = null;
		String templateCode="";
		String bats="";
		Integer send_type  = null;
		String plan_time = null;
		String mobiles = null;
		String operatorName="";
		List rsList = null;
		Map rsMap = null;
		try {
			SendSMSVO total_vo = new SendSMSVO();//信息
			total_vo.setSerial_no(serial_no);
			total_vo.setInputOperator(input_man);
			rsList = this.queryMessage(total_vo);
			if(rsList.size()>0){
				rsMap = (Map)rsList.get(0);
			}
			if(rsMap!=null){
				Integer way_type = Utility.parseInt(Utility.trimNull(rsMap.get("WAY_TYPE")), new Integer(0));
				send_type = Utility.parseInt(Utility.trimNull(rsMap.get("SEND_TYPE")), new Integer(0));
				plan_time = Utility.trimNull(rsMap.get("PLAN_TIME"));
				String msg_type = Utility.trimNull(rsMap.get("MSG_TYPE"));
				content_templet = Utility.trimNull(rsMap.get("CONTENT_TEMPLET"));
				templateCode=Utility.trimNull(rsMap.get("Template_Code")); //模板代码
				bats=Utility.trimNull(rsMap.get("Bats")); //批次号
				Integer check_man = Utility.parseInt(Utility.trimNull(rsMap.get("CHECK_MAN")), new Integer(0));
				mobiles = Utility.trimNull(rsMap.get("MOBILES"));
				operatorName=Utility.trimNull(rsMap.get("OPERATOR_NAME"));
			}
			SendSMSVO detail_vo = new SendSMSVO();
			detail_vo.setSerial_no_total(serial_no);
			detail_vo.setInputOperator(input_man);
			List custList =this.queryMessageDetail(detail_vo);
			
			SendSMSVO vo_sms = new SendSMSVO();//短信发送对象
			vo_sms.setSmsUser("");
			vo_sms.setSmsUser(Utility.trimNull(input_man));
			vo_sms.setPutType("待发");
			vo_sms.setNewFlag(new Integer(1));
			vo_sms.setSendLevel(new Integer(1));
			vo_sms.setInputOperator(input_man);	
			vo_sms.setBats(bats); //批次
			vo_sms.setOperatorName(operatorName);
			vo_sms.setTemplateCode(templateCode);
			
			if(send_type.intValue() == 2){//定时
				if(plan_time.length()>0){
					//预定发送时间(格式为：yyyymmddhhnnss)
					plan_time = plan_time.substring(0,19);
				}
				vo_sms.setSend_time(plan_time);
			}else{
				vo_sms.setSend_time("");
			}
			if(!"".equals(Utility.trimNull(mobiles))){//自定义发送
				mobiles = Utility.replaceAll(mobiles,"；",";");
				String[] mobile = Utility.splitString(mobiles,";");
				vo_sms.setSmstotal(new Integer(mobile.length)); //本批短信数量
				vo_sms.setSmsContent(content_templet);
				for(int i = 0;i< mobile.length;i++){
					//if(mobile[i].length()>= 11){		
						vo_sms.setBat_serial_no(new Integer(i+1));//本批短信内部序号
						vo_sms.setPhoneNumber(Utility.trimNull(mobile[i]));
						sendBackInfo = SmsClient.sendMessage(vo_sms);
					//}
				}
			}else {//按客户信息来发送
				vo_sms.setSmstotal(new Integer(custList.size()));//本批短信数量
				for(int i = 0;i< custList.size();i++){ 
					Map custMap = (Map)custList.get(i);
					Integer cust_id = Utility.parseInt(Utility.trimNull(custMap.get("CUST_ID")), new Integer(0));
					BigDecimal rgmoney = Argument.getRgmoney(cust_id,product_id);
					String cust_name = Utility.trimNull(custMap.get("CUST_NAME"));
					String cust_sex = Utility.trimNull(custMap.get("SEX"));//1男2女
					String service_man = Utility.trimNull(custMap.get("SERVICE_MAN_NAME"));
					String service_man_mobile = Utility.trimNull(custMap.get("SERVICE_MAN_MOBILE"));
					if(cust_sex=="1"){
						cust_sex ="先生";
					}else{
						cust_sex = "女士";
					}
					
					String send_content = "";
					send_content = Utility.replaceAll(content_templet,"<@CUST>",cust_name);
					content_templet = send_content;
					send_content =  Utility.replaceAll(content_templet,"<@NAMED>",cust_sex);
					content_templet = send_content;
					send_content =  Utility.replaceAll(content_templet,"<@SERVICEMANNAME>",service_man);
					content_templet = send_content;
					send_content =  Utility.replaceAll(content_templet,"<@SERVICEMANMOBILE>",service_man_mobile);
					content_templet = send_content;
					send_content =  Utility.replaceAll(content_templet,"<@SUBSMONEY>",rgmoney.toString());
					
					String strJson=getMacroJson(send_content,custMap);
					vo_sms.setMacroJson(strJson);
					vo_sms.setPhoneNumber(Utility.trimNull(custMap.get("MOBILE")));
					vo_sms.setSmsContent(send_content);
					vo_sms.setBat_serial_no(new Integer(i+1));//本批短信内部序号
					//if(Utility.trimNull(custMap.get("MOBILE")).length()>= 11){
						sendBackInfo = SmsClient.sendMessage(vo_sms);
					//}
				}
			}
			if(sendBackInfo != null)
				//Utility.debug("sendBackInfo.length:"+sendBackInfo.length);	
				if(sendBackInfo.length >= 2)
					total_vo.setSend_result(sendBackInfo[0]+"--"+sendBackInfo[1]);
				else
					total_vo.setSend_result("0");
			this.updateResult(total_vo);
		}catch (Exception e) {
			throw new BusiException("发送短信失败:" + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsRecordLocal#sendEmail(enfo.crm.vo.SendSMSVO)
	 */
	@Override
	public void sendEmail(SendSMSVO vo) throws BusiException{
		SendMail2 mail=new SendMail2();	
		String content_templet = null;
		String emails = null;
		List rsList = null;
		Map rsMap = null;
		try {
			String smtp_server  = Utility.trimNull(Argument.getADDITIVE_VALUE("190201"));
			String from_email = Utility.trimNull(vo.getFrom_email());
			String[] email_split = Utility.splitString(from_email,"@");
			String smtp_user = Utility.trimNull(email_split[0]);
			String smtp_userpwd = Utility.trimNull(vo.getSmtp_password());
			Integer product_id  =Utility.parseInt(Utility.trimNull(vo.getProduct_id()), new Integer(0)); 
			//Utility.debug("from_email:"+from_email+"--"+smtp_user+"---"+smtp_userpwd);
			SendSMSVO total_vo = new SendSMSVO();//信息
			total_vo.setSerial_no(vo.getSerial_no());
			total_vo.setInputOperator(vo.getInputOperator());
			rsList = this.queryMessage(total_vo);
			if(rsList.size()>0){
				rsMap = (Map)rsList.get(0);
			}
			if(rsMap!=null){
				Integer way_type = Utility.parseInt(Utility.trimNull(rsMap.get("WAY_TYPE")), new Integer(0));
				content_templet = Utility.trimNull(rsMap.get("CONTENT_TEMPLET"));
				emails = Utility.trimNull(rsMap.get("MOBILES"));
			}
			SendSMSVO detail_vo = new SendSMSVO();
			detail_vo.setSerial_no_total(vo.getSerial_no());
			detail_vo.setInputOperator(vo.getInputOperator());
			List custList =this.queryMessageDetail(detail_vo);
			
			String to_email = "";
			mail.setFromAddress(from_email);
			mail.setSMTPHost(smtp_server,"","");
			mail.setSubject(Argument.getCompanyName(vo.getCom_user_id())+"邮件信息");
			String sendTrue = null;
			if("".equals(smtp_user) && "".equals(smtp_userpwd))
				sendTrue = "false";
			else
				sendTrue = "true";	
			if(!"".equals(Utility.trimNull(emails))){//自定义发送
				emails = Utility.replaceAll(emails,"；",";");
				mail.setAddress(emails,SendMail2.BCC);//各收件人不能看到发送该邮件发送给其他人.	
				mail.setHtmlBody(Utility.trimNull(content_templet));
				mail.sendBatch(sendTrue,smtp_user,smtp_userpwd,vo.getInputOperator());
			}else {
				for(int i = 0;i< custList.size();i++){ 
					Map custMap = (Map)custList.get(i);
					
					//String cust_name = Utility.trimNull(custMap.get("CUST_NAME"));
					//content_templet = Utility.replaceAll(content_templet,"%1",cust_name);
					Integer cust_id = Utility.parseInt(Utility.trimNull(custMap.get("CUST_ID")), new Integer(0));
					BigDecimal rgmoney = Argument.getRgmoney(cust_id,product_id);
					String cust_name = Utility.trimNull(custMap.get("CUST_NAME"));
					String cust_sex = Utility.trimNull(custMap.get("SEX"));//1男2女
					String service_man = Utility.trimNull(custMap.get("SERVICE_MAN_NAME"));
					String service_man_mobile = Utility.trimNull(custMap.get("SERVICE_MAN_MOBILE"));
					if(cust_sex=="1"){
						cust_sex ="先生";
					}else{
						cust_sex = "女士";
					}
					String send_content = "";
					send_content = Utility.replaceAll(content_templet,"<@CUST>",cust_name);
					content_templet = send_content;
					send_content =  Utility.replaceAll(content_templet,"<@NAMED>",cust_sex);
					content_templet = send_content;
					send_content =  Utility.replaceAll(content_templet,"<@SERVICEMANNAME>",service_man);
					content_templet = send_content;
					send_content =  Utility.replaceAll(content_templet,"<@SERVICEMANMOBILE>",service_man_mobile);
					content_templet = send_content;
					send_content =  Utility.replaceAll(content_templet,"<@SUBSMONEY>",rgmoney.toString());
					
					to_email = Utility.trimNull(custMap.get("E_MAIL"));
					if(!"".equals(to_email))
					{
						mail.setAddress(to_email,SendMail2.BCC);	
						mail.setHtmlBody(send_content);
						mail.sendBatch(sendTrue,smtp_user,smtp_userpwd,vo.getInputOperator());
					}	
				}
			}	
			total_vo.setSend_result("邮件发送成功");
			this.updateResult(total_vo);
		}catch(Exception e){
			throw new BusiException("发送邮件失败:" + e.getMessage());
		}
	}
	
	private String getMacroJson(String send_content,Map custMap){
		String macro="{";
		Integer sexi=(Integer)custMap.get("SEX");
		if (send_content.indexOf("<custname>")>0){
			macro=macro+"\"custname\":\""+custMap.get("CUST_NAME")+"\",";//组成宏JSON串
		}
		if (send_content.indexOf("<sexcall>")>0){
			if (sexi==null || sexi.intValue()==0)
				macro=macro+"\"sexcall\":\"\",";//组成宏JSON串
			else if (sexi.intValue()==1)
				macro=macro+"\"sexcall\":\"先生\",";
			else if (sexi.intValue()==2)
				macro=macro+"\"sexcall\":\"女士\",";
			else
				macro=macro+"\"sexcall\":\"\",";
		}
		if (send_content.indexOf("<sexname>")>0){
			if (sexi==null || sexi.intValue()==0)
				macro=macro+"\"sexname\":\"\",";//组成宏JSON串
			else if (sexi.intValue()==1)
				macro=macro+"\"sexname\":\"男\",";
			else if (sexi.intValue()==2)
				macro=macro+"\"sexname\":\"女\",";
			else
				macro=macro+"\"sexname\":\"\",";
		}
		if (send_content.indexOf("<birthday>")>0){
			String birthday=(String)custMap.get("BIRTHDAY");
			macro=macro+get1ValueJson("birthday",birthday);
		}
		if (send_content.indexOf("<mobile>")>0){
			String mobile=(String)custMap.get("MOBILE");
			macro=macro+get1ValueJson("mobile",mobile);
		}
		if (send_content.indexOf("<managername>")>0){
			String managername=(String)custMap.get("SERVICE_MAN_NAME");
			macro=macro+get1ValueJson("managername",managername);
		}
		if (send_content.indexOf("<managermobile>")>0){
			String managermobile=(String)custMap.get("SERVICE_MAN_MOBILE");
			macro=macro+get1ValueJson("managermobile",managermobile);
		}
		if (send_content.indexOf("<cardid>")>0){
			String cardid=(String)custMap.get("CARD_ID");
			macro=macro+get1ValueJson("cardid",cardid);
		}
		if (send_content.indexOf("<currenttime>")>0){
			String currenttime=Utility.getstrCurrentTime();
			macro=macro+get1ValueJson("currenttime",currenttime);
		}
		if (macro!=null&&macro.length()>2){
			macro=macro.substring(0,macro.length()-1);//去掉最后的逗号
		}
		macro=macro+"}";
		return macro;
	}
	
	private String get1ValueJson(String key,String value){
		if (value==null || "".equals(value))
			return "\""+key+"\":\"\",";
		else
			return "\""+key+"\":\""+value+"\",";
	}
}
