package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ApplyreachVO;

public interface ApplyreachLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 申购---查询（分页显示）
	 * 
	 * SP_QUERY_TCONTRACTSG	IN_BOOK_CODE	INT	帐套
							IN_SERIAL_NO	INT	序号
							IN_PRODUCT_ID	INT	产品ID
							IN_PRODUCT_CODE	VARCHAR(6)	产品编号
							IN_CUST_NAME	VARCHAR(100)	客户姓名
							IN_CONTRACT_BH	VARCHAR(16)	合同编号							
							IN_CHECK_FLAG	INT	审核标志1未审核2已审核
							IN_INPUT_MAN	INT	操作员
							IN_PRODUCT_NAME NVARCHAR(60) 产品名称
							IN_FLAG         INT 1单一，2集合
							IN_OPEN_DATE	INT 开放日
							IN_SUB_PRODUCT_ID INT 子产品ID
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll(ApplyreachVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 申购-----删除
	 * 
	 * SP_DEL_TCONTRACTSG	IN_SERIAL_NO	INT	序号
							IN_INPUT_MAN	INT	操作员
	 * @param vo
	 * @throws BusiException
	 */
	void delete(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 申购----添加（没有认购合同时候调用）
	 * 
	 * SP_ADD_TCONTRACTSG 0	IN_BOOK_CODE	INT	帐套
						1	IN_CUST_ID	INT	客户ID
						2	IN_PRODUCT_ID	INT	产品ID
						3	IN_CONTRACT_BH	VARCHAR(16)	合同编号
						4	IN_CONTRACT_SUB_BH	VARCHAR(50)	合同实际编号
						5	IN_SG_MONEY	DECIMAL(16,3)	申购金额
						6	IN_SG_PRICE	DECIMAL(5,4)	申购价格
						7	IN_JK_TYPE	VARCHAR(10)	缴款方式(1114)
						8	IN_BANK_ID	VARCHAR(10)	信托利益银行编号(1103)
						9	IN_BANK_ACCT	VARCHAR(30)	信托利益银行账号
						10	IN_BANK_SUB_NAME	VARCHAR(30)	支行名称
						11	IN_GAIN_ACCT	VARCHAR(60)	受益银行帐户户名
						12	IN_QS_DATE	INT	签署日期
						13	IN_JK_DATE	INT	缴款日期
						14	IN_START_DATE	INT	起始日期
						15	IN_VALID_PERIOD	INT	合同期限
						16	IN_LINK_MAN	INT	联系人
						17	IN_SERVICE_MAN	INT	客户经理
						18	IN_CITY_SERIAL_NO	INT	推介地编号
						19	IN_TOUCH_TYPE	VARCHAR(40)	联系方式
						20	IN_TOUCH_TYPE_NAME	VARCHAR(30)	联系方式说明
						21	IN_FEE_JK_TYPE	INT	0无需交，1从本金扣，2另外交
						22	IN_SUMMARY	VARCHAR(200)	描述
						23	IN_INPUT_MAN	INT	操作员
						24	IN_BANK_ACCT_TYPE	VARCHAR(10)	银行账户类型(9920)
						25  IN_BONUS_FLAG INT = 1 1、兑付　2、转份额 
						26	IN_SUB_PRODUCT_ID 	INT 子产品				
						27	OUT_SERIAL_NO	INT	新记录的序号
						28	OUT_CONTRACT_BH	VARCHAR(16)	合同编号
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Object[] append(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 申购----添加（有认购合同时候调用）
	 * 
	 * SP_ADD_TCONTRACTSG_SIMP  IN_CNTR_SERIAL_NO	INT	合同表SERIAL_NO
								IN_SG_MONEY	DECIMAL(16,3)	申购金额
								IN_SG_PRICE	DECIMAL(5,4)	申购价格
								IN_JK_TYPE	VARCHAR(10)	缴款方式(1114)
								IN_BANK_ID	VARCHAR(10)	信托利益银行编号(1103)
								IN_BANK_ACCT	VARCHAR(30)	信托利益银行账号
								IN_BANK_SUB_NAME	VARCHAR(30)	支行名称
								IN_GAIN_ACCT	VARCHAR(60)	受益银行帐户户名
								IN_QS_DATE	INT	签署日期
								IN_JK_DATE	INT	缴款日期
								IN_START_DATE	INT	起始日期
								IN_VALID_PERIOD	INT	合同期限
								IN_FEE_JK_TYPE	INT	0无需交，1从本金扣，2另外交
								IN_SUMMARY	VARCHAR(200)	描述
								IN_INPUT_MAN	INT	操作员
								IN_BANK_ACCT_TYPE	VARCHAR(10)	银行账户类型(9920)	
								IN_BOUNS_FLAG     INT = 1 1、兑付　2、转份额  
								OUT_SERIAL_NO	INT	新记录的序号
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer append2(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 申购---修改
	 * 
	 * SP_MODI_TCONTRACTSG  IN_SERIAL_NO	INT	序号
							IN_NEW_CONTRACT_BH	VARCHAR(16)	合同编号
							IN_CONTRACT_SUB_BH	VARCHAR(50)	合同实际编号
							IN_SG_MONEY	DECIMAL(16,3)	申购金额
							IN_SG_PRICE	DECIMAL(5,4)	申购价格
							IN_JK_TYPE	VARCHAR(10)	缴款方式(1114)
							IN_BANK_ID	VARCHAR(10)	信托利益银行编号(1103)
							IN_BANK_ACCT	VARCHAR(30)	信托利益银行账号
							IN_BANK_SUB_NAME	VARCHAR(30)	支行名称
							IN_GAIN_ACCT	VARCHAR(60)	受益银行帐户户名
							IN_QS_DATE	INT	签署日期
							IN_JK_DATE	INT	缴款日期
							IN_START_DATE	INT	起始日期
							IN_VALID_PERIOD	INT	合同期限
							IN_LINK_MAN	INT	联系人
							IN_SERVICE_MAN	INT	客户经理
							IN_CITY_SERIAL_NO	INT	推介地编号
							IN_TOUCH_TYPE	VARCHAR(40)	联系方式
							IN_TOUCH_TYPE_NAME	VARCHAR(30)	联系方式说明
							IN_FEE_JK_TYPE	INT	0无需交，1从本金扣，2另外交
							IN_SUMMARY	VARCHAR(200)	描述
							IN_INPUT_MAN	INT	操作员
							
							IN_BANK_ACCT_TYPE	VARCHAR(10)	银行账户类型(9920)
							IN_BOUNS_FLAG INT = 1 1、兑付　2、转份额  
	 * @throws BusiException
	 * @param vo
	 */
	void modi(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 申购---查询（单条信息）
	 * 
	 * SP_QUERY_TCONTRACTSG	IN_BOOK_CODE	INT	帐套
							IN_SERIAL_NO	INT	序号
							IN_PRODUCT_ID	INT	产品ID
							IN_PRODUCT_CODE	VARCHAR(6)	产品编号
							IN_CUST_NAME	VARCHAR(100)	客户姓名
							IN_CONTRACT_BH	VARCHAR(16)	合同编号							
							IN_CHECK_FLAG	INT	审核标志1未审核2已审核
							IN_INPUT_MAN	INT	操作员
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listBySql(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 申购-----审核
	 * 
	 * SP_CHECK_TCONTRACTSG	IN_SERIAL_NO	INT	序号
							IN_INPUT_MAN	INT	操作员
	 * @throws BusiException
	 */
	void checkApplyreachContract(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 申购审核恢复
	 * SP_CHECK_TCONTRACTSG_BACK  @IN_SERIAL_NO INT,
	 *                     		  @IN_INPUT_MAN INT 
	 * @throws BusiException
	 */
	void recheckApplyreachContract(ApplyreachVO vo) throws BusiException;

}