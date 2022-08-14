/*
 * 创建日期 2012-3-22
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import enfo.crm.affair.ServiceTaskLocal;
import enfo.crm.callcenter.SmsRecordLocal;
import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Utility;
import enfo.crm.vo.SendSMSVO;
import enfo.crm.vo.ServiceTaskVO;
import enfo.crm.web.SmsClient;

/**
 * @author taoc
 * 短信发送动作类 单例
 * 
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class SmsSendAction {
	private static SmsSendAction instance = null;
	
	public static synchronized  SmsSendAction getInstance(){
		if(instance == null){
			instance = new SmsSendAction();
		}
		
		return instance;
	}
	
	/**
	 * 
	 * 查询自动发送短信任务
	 * @return
	 * @throws BusiException
	 */
	public List querySmsAutoTask() throws BusiException{
		List rsList = null;	
		String sqlStr = "SELECT * FROM TServiceTaskDetail WHERE ServiceWay='110905' AND Status =2 AND AutoFlag = 1 ";
		
		rsList = CrmDBManager.listBySql(sqlStr);
		return rsList;
	}
	
	/**
	 * 处理任务明细
	 * @param vo
	 * @throws BusiException
	 */
	public void treat_details(ServiceTaskVO vo) throws BusiException{
		Object[] params = new Object[7];
		String sql ="{?=call SP_Treat_TServiceTaskDetail(?,?,?,?,?,?,?)}";
		
		params[0]= vo.getSerial_no();
		params[1]= vo.getServiceWay();
		params[2]= vo.getExecutorTime();
		params[3]= vo.getStatus();
		params[4]= vo.getResult();
		params[5]= vo.getNeedFeedBack();		
		params[6]= vo.getInputMan();
		
		CrmDBManager.cudProc(sql,params);			
	}
	
	/**
	 * 单例短信发送
	 * @param mobile 手机
	 * @param content 发送内容
	 * @param sendLevel 发送Leval
	 * @param input_operatorCode
	 * @return
	 */
	public String sendSmsWithSigln(String mobile,String content,Integer sendLevel,Integer input_operatorCode){
		SendSMSVO vo_sms = null;		

		//声明变量
		Integer serial_no_detail = new Integer(0);
		Integer targetCustId = new Integer(0);
		String cust_name = "";
		String cust_mobile = Utility.trimNull(mobile);
		String sms_content = Utility.trimNull(content);
		String putType="待发";
		Integer smsIndex = new Integer(0);
		String[] sendBackInfo = null;

		try {
			vo_sms = new SendSMSVO();
			vo_sms.setSmsUser(Utility.trimNull(input_operatorCode));
			vo_sms.setPutType(putType);
			vo_sms.setNewFlag(new Integer(1));
			vo_sms.setSendLevel(sendLevel);
			vo_sms.setInputOperator(input_operatorCode);
			vo_sms.setSmsContent(sms_content);
			vo_sms.setPhoneNumber(cust_mobile);
			vo_sms.setSerial_no_detail(serial_no_detail);

			//先把短信信息保存在系统表里
			smsIndex = this.append(vo_sms);
			vo_sms.setUserDefineNo(smsIndex);

			try {
				vo_sms.setSmsIndex(smsIndex);
				vo_sms.setBat_serial_no(new Integer(1));
				vo_sms.setSmstotal(new Integer(1));
				sendBackInfo = SmsClient.sendMessage(vo_sms);
			}
			catch(Exception e){
				vo_sms = new SendSMSVO();

				vo_sms.setSmsIndex(smsIndex);
				vo_sms.setStatus(new Integer(2));
				vo_sms.setStatusName("提交失败");
				vo_sms.setInputOperator(input_operatorCode);

				this.modi(vo_sms);

				return "2|请检查短信发送平台！";
			}

			vo_sms = new SendSMSVO();

			vo_sms.setSmsIndex(smsIndex);
			vo_sms.setStatus(Utility.parseInt(sendBackInfo[0],new Integer(0)));
			vo_sms.setStatusName(sendBackInfo[1]);
			vo_sms.setInputOperator(input_operatorCode);
			this.modi(vo_sms);

		} catch (Exception e) {
			return "2|"+e.getMessage();
		}

		return "1|提交短信成功！";
	}
	
	/**
	 * 
	 * 保存发送短信信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	private Integer append(SendSMSVO vo) throws BusiException{
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
			newId = Utility.parseInt((Integer)CrmDBManager.cudProc(sqlStr, params,9,java.sql.Types.INTEGER),new Integer(0));
		} catch (BusiException e) {
			throw new BusiException("保存发送短信日志失败:" + e.getMessage());
		}

		return newId;
	}
	
	/**
	 * 
	 * 编辑发送短信信息
	 * @param vo
	 * @throws BusiException
	 */
	private void modi(SendSMSVO vo) throws BusiException{
		String sqlStr = "{?=call SP_MODI_TSmsRecord(?,?,?,?)}";
		Object[] params = new Object[4];
		
		params[0] = vo.getSmsIndex();
		params[1] = vo.getStatus();
		params[2] = vo.getStatusName();
		params[3] = vo.getInputOperator();
		
		try {
			CrmDBManager.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("编辑发送短信日志失败:" + e.getMessage());
		}		
	}
}
