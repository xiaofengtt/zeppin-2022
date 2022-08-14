package enfo.crm.callcenter;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CCVO;

public interface CallCenterLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param page
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList statCCCheckRecords(CCVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询坐席电话明细
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList query_cc_detail(CCVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询坐席电话明细
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List l_query_cc_detail(CCVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param page
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList statCCSeatCallDetail(CCVO vo, int page, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 * 	IN_Direction		INTEGER			方向1被叫接听2主叫拨打
		IN_CallTime			DATETIME		通话开始时间（索引）
		IN_CallLength		INTEGER			通话时长（秒）
		IN_ManagerID		INTEGER			客户经理ID（TCustManagers.ManagerID）（索引）
		IN_Extension		INTEGER			分机号码
		IN_CUST_ID			INTEGER			客户ID（TCustomers.CUST_ID）（索引）通话对方是客户时，必须有CUST_ID。不是客户且未保存为客户时，为空
		IN_ContactID		INTEGER			联系人ID(TCustomerContacts.ContactID)
		IN_PhoneNumber		NVARCHAR(50)	对方电话号码
		IN_BusinessID		INTEGER			本次通话所涉及业务ID
		IN_Content			NVARCHAR(MAX)	通话记事
		IN_Status			INTEGER			状态1正常完成2本次会话待处理9放弃接听
		IN_CallCenterID		BIGINT			CallCenter系统中标识（HelpCenter.calllog.id）
		IN_INPUT_MAN		INTEGER			操作员
	
	 */
	Integer addCallRecords(CCVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	void modiCallRecords(CCVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * 
	 * 	IN_Serial_no	BIGINT			序号（主键）
		IN_Direction	INTEGER			方向1被叫接听2主叫拨打
		IN_CallTime		INTEGER			通话开始日期（YYYYMMDD）
		IN_ManagerID	INTEGER			客户经理ID（TCustManagers.ManagerID）
		IN_Extension	INTEGER			分机号码
		IN_CUST_ID		INTEGER			客户ID（TCustomers.CUST_ID）（索引）
		IN_PhoneNumber	NVARCHAR(30)	对方电话号码
		IN_Content		NVARCHAR(MAX)	通话记事
		IN_Status		INTEGER			状态1正常完成2本次会话待处理9放弃接听
		IN_INPUT_MAN	INTEGER			操作员
	
	 */
	IPageList listCallRecords(CCVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustByPhone(CCVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustByPhone2(CCVO vo, int pageIndex, int pageSize) throws BusiException;

}