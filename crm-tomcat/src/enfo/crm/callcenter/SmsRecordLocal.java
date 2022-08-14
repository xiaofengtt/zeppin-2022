package enfo.crm.callcenter;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.SendSMSVO;

public interface SmsRecordLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 保存发送短信信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer append(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 编辑发送短信信息
	 * @param vo
	 * @throws BusiException
	 */
	void modi(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询短信日志信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询短信日志信息
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page(SendSMSVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询发送信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList queryMessageList(SendSMSVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询短信信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMessage(SendSMSVO vo) throws BusiException;

	/**
	/**
	 * @ejb.interface-method view-type = "local"
	 * 保存发送信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer appendMessage(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 保存发送信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiMessage(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 更新发送记录状态
	 * @param vo
	 * @throws BusiException
	 */
	void updateResult(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除发送信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void checkMessage(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除发送信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void deleteMessage(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询短信明细记录
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMessageDetail(SendSMSVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询短信明细记录
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMessageDetail_m(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增客户明细记录
	 * @param vo
	 * @throws BusiException
	 */
	void addMessageCustid(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除发送信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void deleteMessageDetail(SendSMSVO vo) throws BusiException;

	/**
	 * 发送短信
	 * @param vo
	 * @return
	 */
	public void sendSms(Integer serial_no, Integer input_man) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * 发送短信
	 * @param vo
	 * @return
	 */
	void sendSms(Integer serial_no, Integer input_man, Integer product_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 发送邮件
	 * @param vo
	 * @return
	 */
	void sendEmail(SendSMSVO vo) throws BusiException;

}