/*
 * 创建日期 2009-12-17
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enfo.crm.affair.ServiceTaskLocal;
import enfo.crm.callcenter.CallCenterLocal;
import enfo.crm.callcenter.SmsRecordLocal;
import enfo.crm.customer.CustomerLocal;
import enfo.crm.dao.BusiException;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Utility;
import enfo.crm.util.DwrDecoder;
import enfo.crm.util.JsonUtil;
import enfo.crm.vo.CCVO;
import enfo.crm.vo.CustomerVO;
import enfo.crm.vo.SendSMSVO;
import enfo.crm.vo.ServiceTaskVO;
import enfo.crm.web.DocumentFile;
import enfo.crm.web.SmsClient;
import enfo.crm.dao.CrmDBManager;

/**
 * @author lzhd
 * 
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CallCenterService {

	//获取客户信息
	public String getCustInfoById(Integer cust_id) throws Exception {

		List list;
		try {
			CustomerLocal local = EJBFactory.getCustomer();
			CustomerVO vo = new CustomerVO();
			vo.setCust_id(cust_id);
			list = local.listProcAll(vo);
		} catch (Exception e) {
			throw new BusiException("获取用户信息失败:" + e.getMessage());
		}
		return JsonUtil.object2json(list);
	}

	//获取历史话务记录
	public List getCallRecords(Integer cust_id, String cust_tel)
			throws Exception {
		List list = null;
		try {
			CallCenterLocal local = EJBFactory.getCallCenter();
			CCVO vo = new CCVO();
			vo.setCust_id(cust_id);
			vo.setPhoneNumStr(cust_tel);
			list = local.listCallRecords(vo, -1, -1).getRsList();
		} catch (Exception e) {
			throw new BusiException("获取历史话务记录失败:" + e.getMessage());
		}
		return list;
	}

	public String getCustInfo(String cust_name, String card_num, Integer input_operatorCode)
			throws Exception {
		List list;
		try {
			CustomerLocal local = EJBFactory.getCustomer();
			CustomerVO vo = new CustomerVO();
			vo.setCust_name(DwrDecoder.unescape(cust_name));
			vo.setCard_id(DwrDecoder.unescape(card_num));
			vo.setInput_man(input_operatorCode);
			list = (local.listProcAll(vo, -1, -1)).getRsList();
		} catch (Exception e) {
			throw new BusiException("获取用户信息失败:" + e.getMessage());
		}
		return JsonUtil.object2json(list);
	}

	public String getCustList(String callinNum, Integer op_code)
			throws Exception {
		List list;
		try {
			CallCenterLocal local = EJBFactory.getCallCenter();
			CCVO vo = new CCVO();
			vo.setPhoneNumStr(callinNum);
			vo.setInputMan(op_code);
			list = (local.listCustByPhone(vo, -1, -1)).getRsList();
		} catch (Exception e) {
			throw new BusiException("获取用户信息失败:" + e.getMessage());
		}
		return JsonUtil.object2json(list);
	}

	/**
	 * 新增CC
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String appendCallRecord(CCVO vo) throws Exception {
		CallCenterLocal local = EJBFactory.getCallCenter();
		vo.setCallTime(null);
		Integer cc_id = local.addCallRecords(vo);
		return cc_id.toString();
	}

	/**
	 * 修改CallRecord表信息
	 * 
	 * @param sql
	 * @param inputMan
	 * @param serialNo
	 * @return
	 * @throws BusiException
	 */
	public String updateCallRecord(String sql, Integer inputMan,
			Integer callRecordID) throws BusiException {
		String summary = "通话记录处理，记录序号：" + callRecordID;
		String loglistSql = "INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY) VALUES(50002,N'通话记录处理',"
				+ inputMan + ",'" + summary + "')";
		CrmDBManager.executeSql(sql);
		CrmDBManager.executeSql(loglistSql);
		return "1|修改成功！";
	}

	/**
	 * 修改服务任务明细 短信内容
	 * 
	 * @param serialNo
	 * @param smsCntent
	 * @param inputMan
	 * @return
	 */
	public String updateSMSContent(Integer serialNo, String smsCntent,
			Integer inputMan) {
		String summary = "修改服务任务明细，操作员：" + inputMan;
		String loglistSql = "INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY) VALUES(50301,N'服务任务明细处理',"
				+ inputMan + ",'" + summary + "')";
		String updateSql = "UPDATE TServiceTaskDetail SET SmsContent =N\'"
				+ smsCntent + "\' WHERE Serial_no=" + serialNo;
		try {
			CrmDBManager.executeSql(updateSql);
			CrmDBManager.executeSql(loglistSql);
		} catch (BusiException e) {
			return "2|" + e.getMessage();
		}
		return "1|修改成功！";
	}

	/**
	 * 单纯的发送短信
	 * @param smsUser 插入用户
	 * @param phoneNumber 接收短信手机号码
	 * @param smsContent 发送短信内容
	 * @param sendLevel 发送级别
	 * @param putType 提交方式
	 * @param serialNo_details 明细任务ID
	 * @param input_man 操作员
	 * @return
	 * @throws Exception
	 */
	public String sendSMSSimple(String smsUser, String phoneNumber,
			String smsContent, Integer sendLevel, String putType,
			Integer serialNo_details, Integer input_man) throws Exception {
		Integer smsIndex = new Integer(0);
		String[] sendBackInfo = null;
		String s_result = "";
		try {
			SmsRecordLocal smsRecordLocal =  EJBFactory.getSmsRecord();
			//1.保存短信记录
			SendSMSVO vo_sms = new SendSMSVO();
			vo_sms.setSmsUser(smsUser);
			vo_sms.setPhoneNumber(phoneNumber);
			vo_sms.setSmsContent(smsContent);
			vo_sms.setSendLevel(sendLevel);
			vo_sms.setPutType(putType);
			vo_sms.setSerial_no_detail(serialNo_details);
			vo_sms.setInputOperator(input_man);
			smsIndex = smsRecordLocal.append(vo_sms);
			vo_sms.setUserDefineNo(smsIndex);
			
			//2.发送短信
			try {
				vo_sms.setBat_serial_no(new Integer(1));
				vo_sms.setSmsIndex(smsIndex);
				vo_sms.setSmstotal(new Integer(1));
				sendBackInfo = SmsClient.sendMessage(vo_sms);
			} catch (Exception e) {
				vo_sms.setSmsIndex(smsIndex);
				vo_sms.setStatus(new Integer(2));
				vo_sms.setStatusName("提交失败");
				vo_sms.setInputOperator(input_man);
				smsRecordLocal.modi(vo_sms);
				smsRecordLocal.remove();
				return "2|请检查短信发送平台！";
			}
			vo_sms.setSmsIndex(smsIndex);
			vo_sms.setStatus(Utility.parseInt(sendBackInfo[0],
					new Integer(0)));
			vo_sms.setStatusName(sendBackInfo[1]);
			vo_sms.setInputOperator(input_man);
			smsRecordLocal.modi(vo_sms);
			int ret = Utility.parseInt(sendBackInfo[0],
					new Integer(0)).intValue();
			if (ret == 3 || ret == 1) {
				s_result = "1|提交短信成功！";
			} else {
				s_result = sendBackInfo[0] + "|" + sendBackInfo[1];
			}
			smsRecordLocal.remove();
		} catch (Exception e) {
			return "2|" + e.getMessage();
		}
		return s_result;
	}

	/**
	 * 发送短信
	 * 
	 * @param serialNoDetail_Message
	 * @param sendLevel
	 * @param input_operatorCode
	 * @return
	 */
	public String sendSMS(String serialNoDetail_Message, Integer sendLevel,
			Integer input_operatorCode) {
		String[] serialNoDetail_Array = serialNoDetail_Message.split("_");
		int serialNoDetail_len = serialNoDetail_Array.length;
		//声明变量
		Integer serial_no_detail = new Integer(0);
		Integer targetCustId = new Integer(0);
		String s_result = "";
		String cust_name = "";
		String cust_mobile = "";
		String sms_content = "";
		String putType = "待发";
		Integer smsIndex = new Integer(0);
		String[] sendBackInfo = null;
		//获得对象
		List rsList_task = new ArrayList();
		Map map_task = null;
		ServiceTaskVO vo_details = new ServiceTaskVO();
		SendSMSVO vo_sms = null;
		ServiceTaskLocal serviceTaskLocal;//服务任务
		SmsRecordLocal smsRecordLocal;//短信日志
		try {
			serviceTaskLocal = EJBFactory.getServiceTask();
			smsRecordLocal = EJBFactory.getSmsRecord();
		} catch (Exception e1) {
			return "2|" + e1.getMessage();
		}
		//循环插入
		for (int i = 0; i < serialNoDetail_len; i++) {
			serial_no_detail = Utility.parseInt(Utility
					.trimNull(serialNoDetail_Array[i]), new Integer(0));

			if (serial_no_detail.intValue() > 0) {
				vo_details.setSerial_no(serial_no_detail);
				vo_details.setInputMan(input_operatorCode);

				try {
					rsList_task = serviceTaskLocal.query_details(vo_details);

					if (rsList_task.size() > 0) {
						map_task = (Map) rsList_task.get(0);
						targetCustId = Utility.parseInt(Utility
								.trimNull(map_task.get("TargetCustID")),
								new Integer(0));
						cust_name = Utility.trimNull(map_task.get("CUST_NAME"));
						cust_mobile = Utility.trimNull(map_task.get("Mobile"));
						sms_content = Utility.trimNull(map_task
								.get("SmsContent"));
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
						smsIndex = smsRecordLocal.append(vo_sms);
						vo_sms.setUserDefineNo(smsIndex);

						try {
							if (serialNoDetail_len > 1)
								vo_sms.setBat_serial_no(new Integer(i + 1));
							else
								vo_sms.setBat_serial_no(new Integer(1));
							vo_sms.setSmsIndex(smsIndex);
							vo_sms.setSmstotal(new Integer(serialNoDetail_len));
							sendBackInfo = SmsClient.sendMessage(vo_sms);
						} catch (Exception e) {
							vo_sms = new SendSMSVO();

							vo_sms.setSmsIndex(smsIndex);
							vo_sms.setStatus(new Integer(2));
							vo_sms.setStatusName("提交失败");
							vo_sms.setInputOperator(input_operatorCode);

							smsRecordLocal.modi(vo_sms);
							//throw new Exception("请检查短信发送平台！");
							return "2|请检查短信发送平台！";
						}

						vo_sms = new SendSMSVO();

						vo_sms.setSmsIndex(smsIndex);
						vo_sms.setStatus(Utility.parseInt(sendBackInfo[0],
								new Integer(0)));
						vo_sms.setStatusName(sendBackInfo[1]);
						vo_sms.setInputOperator(input_operatorCode);
						smsRecordLocal.modi(vo_sms);
						int ret = Utility.parseInt(sendBackInfo[0],
								new Integer(0)).intValue();
						if (ret == 3 || ret == 1) {
							s_result = "1|提交短信成功！";
						} else {
							s_result = sendBackInfo[0] + "|" + sendBackInfo[1];
						}
					}
				} catch (BusiException e) {
					return "2|" + e.getMessage();
				}
			}
		}

		return s_result;
	}

	/**
	 * 单例短信发送
	 * 
	 * @param mobile
	 *            手机
	 * @param content
	 *            发送内容
	 * @param sendLevel
	 *            发送Leval
	 * @param input_operatorCode
	 * @return
	 */
	public String sendSmsWithSigln(String mobile, String content,
			Integer sendLevel, Integer input_operatorCode) {
		SendSMSVO vo_sms = null;
		SmsRecordLocal smsRecordLocal;

		//声明变量
		Integer serial_no_detail = new Integer(0);
		Integer targetCustId = new Integer(0);
		String cust_name = "";
		String cust_mobile = Utility.trimNull(mobile);
		String sms_content = Utility.trimNull(content);
		String putType = "待发";
		Integer smsIndex = new Integer(0);
		String[] sendBackInfo = null;

		try {
			smsRecordLocal = EJBFactory.getSmsRecord(); //短信日志

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
			smsIndex = smsRecordLocal.append(vo_sms);
			vo_sms.setUserDefineNo(smsIndex);

			try {
				sendBackInfo = SmsClient.sendMessage(vo_sms);
			} catch (Exception e) {
				vo_sms = new SendSMSVO();

				vo_sms.setSmsIndex(smsIndex);
				vo_sms.setStatus(new Integer(2));
				vo_sms.setStatusName("提交失败");
				vo_sms.setInputOperator(input_operatorCode);

				smsRecordLocal.modi(vo_sms);
				
                e.printStackTrace();
				return "2|" + e.getMessage();
			}

			vo_sms = new SendSMSVO();

			vo_sms.setSmsIndex(smsIndex);
			vo_sms.setStatus(Utility.parseInt(sendBackInfo[0], new Integer(0)));
			vo_sms.setStatusName(sendBackInfo[1]);
			vo_sms.setInputOperator(input_operatorCode);
			smsRecordLocal.modi(vo_sms);
            
            int ret = Utility.parseInt(sendBackInfo[0],
                    new Integer(0)).intValue();
            if (ret!=3 && ret!=1) {
                return sendBackInfo[0] + "|" + sendBackInfo[1];
            }

		} catch (Exception e) {
			return "2|" + e.getMessage();
		}

		return "1|提交短信成功！";
	}

	//修改/添加联系方式
	public String operatorIngCustTel(Integer cust_id, String cust_tel,
			Integer flag, Integer input_man, Integer tel_selected_flag) throws Exception {
		Object[] params = new Object[5];
		String str = Utility.parseInt(flag, new Integer(1)).intValue() == 1 ? "修改"
				: "添加";
		params[0] = Utility.parseInt(cust_id, new Integer(0));
		params[1] = Utility.trimNull(cust_tel);
		params[2] = Utility.parseInt(flag, new Integer(1));
		params[3] = Utility.parseInt(input_man, new Integer(888));
		params[4] = Utility.parseInt(tel_selected_flag, new Integer(1));
		try {
			CrmDBManager.cudProc("{?=call SP_ADD_TCUSTTELINFO(?,?,?,?,?)}",
					params);
		} catch (Exception e) {
			return "2|联系方式" + str + "失败" + e.getMessage();
		}
		return "1|联系方式" + str + "成功";
	}
	
}