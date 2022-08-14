package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ApplyreachVO;

public interface ApplyreachLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * �깺---��ѯ����ҳ��ʾ��
	 * 
	 * SP_QUERY_TCONTRACTSG	IN_BOOK_CODE	INT	����
							IN_SERIAL_NO	INT	���
							IN_PRODUCT_ID	INT	��ƷID
							IN_PRODUCT_CODE	VARCHAR(6)	��Ʒ���
							IN_CUST_NAME	VARCHAR(100)	�ͻ�����
							IN_CONTRACT_BH	VARCHAR(16)	��ͬ���							
							IN_CHECK_FLAG	INT	��˱�־1δ���2�����
							IN_INPUT_MAN	INT	����Ա
							IN_PRODUCT_NAME NVARCHAR(60) ��Ʒ����
							IN_FLAG         INT 1��һ��2����
							IN_OPEN_DATE	INT ������
							IN_SUB_PRODUCT_ID INT �Ӳ�ƷID
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
	 * �깺-----ɾ��
	 * 
	 * SP_DEL_TCONTRACTSG	IN_SERIAL_NO	INT	���
							IN_INPUT_MAN	INT	����Ա
	 * @param vo
	 * @throws BusiException
	 */
	void delete(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �깺----��ӣ�û���Ϲ���ͬʱ����ã�
	 * 
	 * SP_ADD_TCONTRACTSG 0	IN_BOOK_CODE	INT	����
						1	IN_CUST_ID	INT	�ͻ�ID
						2	IN_PRODUCT_ID	INT	��ƷID
						3	IN_CONTRACT_BH	VARCHAR(16)	��ͬ���
						4	IN_CONTRACT_SUB_BH	VARCHAR(50)	��ͬʵ�ʱ��
						5	IN_SG_MONEY	DECIMAL(16,3)	�깺���
						6	IN_SG_PRICE	DECIMAL(5,4)	�깺�۸�
						7	IN_JK_TYPE	VARCHAR(10)	�ɿʽ(1114)
						8	IN_BANK_ID	VARCHAR(10)	�����������б��(1103)
						9	IN_BANK_ACCT	VARCHAR(30)	�������������˺�
						10	IN_BANK_SUB_NAME	VARCHAR(30)	֧������
						11	IN_GAIN_ACCT	VARCHAR(60)	���������ʻ�����
						12	IN_QS_DATE	INT	ǩ������
						13	IN_JK_DATE	INT	�ɿ�����
						14	IN_START_DATE	INT	��ʼ����
						15	IN_VALID_PERIOD	INT	��ͬ����
						16	IN_LINK_MAN	INT	��ϵ��
						17	IN_SERVICE_MAN	INT	�ͻ�����
						18	IN_CITY_SERIAL_NO	INT	�ƽ�ر��
						19	IN_TOUCH_TYPE	VARCHAR(40)	��ϵ��ʽ
						20	IN_TOUCH_TYPE_NAME	VARCHAR(30)	��ϵ��ʽ˵��
						21	IN_FEE_JK_TYPE	INT	0���轻��1�ӱ���ۣ�2���⽻
						22	IN_SUMMARY	VARCHAR(200)	����
						23	IN_INPUT_MAN	INT	����Ա
						24	IN_BANK_ACCT_TYPE	VARCHAR(10)	�����˻�����(9920)
						25  IN_BONUS_FLAG INT = 1 1���Ҹ���2��ת�ݶ� 
						26	IN_SUB_PRODUCT_ID 	INT �Ӳ�Ʒ				
						27	OUT_SERIAL_NO	INT	�¼�¼�����
						28	OUT_CONTRACT_BH	VARCHAR(16)	��ͬ���
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Object[] append(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �깺----��ӣ����Ϲ���ͬʱ����ã�
	 * 
	 * SP_ADD_TCONTRACTSG_SIMP  IN_CNTR_SERIAL_NO	INT	��ͬ��SERIAL_NO
								IN_SG_MONEY	DECIMAL(16,3)	�깺���
								IN_SG_PRICE	DECIMAL(5,4)	�깺�۸�
								IN_JK_TYPE	VARCHAR(10)	�ɿʽ(1114)
								IN_BANK_ID	VARCHAR(10)	�����������б��(1103)
								IN_BANK_ACCT	VARCHAR(30)	�������������˺�
								IN_BANK_SUB_NAME	VARCHAR(30)	֧������
								IN_GAIN_ACCT	VARCHAR(60)	���������ʻ�����
								IN_QS_DATE	INT	ǩ������
								IN_JK_DATE	INT	�ɿ�����
								IN_START_DATE	INT	��ʼ����
								IN_VALID_PERIOD	INT	��ͬ����
								IN_FEE_JK_TYPE	INT	0���轻��1�ӱ���ۣ�2���⽻
								IN_SUMMARY	VARCHAR(200)	����
								IN_INPUT_MAN	INT	����Ա
								IN_BANK_ACCT_TYPE	VARCHAR(10)	�����˻�����(9920)	
								IN_BOUNS_FLAG     INT = 1 1���Ҹ���2��ת�ݶ�  
								OUT_SERIAL_NO	INT	�¼�¼�����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer append2(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �깺---�޸�
	 * 
	 * SP_MODI_TCONTRACTSG  IN_SERIAL_NO	INT	���
							IN_NEW_CONTRACT_BH	VARCHAR(16)	��ͬ���
							IN_CONTRACT_SUB_BH	VARCHAR(50)	��ͬʵ�ʱ��
							IN_SG_MONEY	DECIMAL(16,3)	�깺���
							IN_SG_PRICE	DECIMAL(5,4)	�깺�۸�
							IN_JK_TYPE	VARCHAR(10)	�ɿʽ(1114)
							IN_BANK_ID	VARCHAR(10)	�����������б��(1103)
							IN_BANK_ACCT	VARCHAR(30)	�������������˺�
							IN_BANK_SUB_NAME	VARCHAR(30)	֧������
							IN_GAIN_ACCT	VARCHAR(60)	���������ʻ�����
							IN_QS_DATE	INT	ǩ������
							IN_JK_DATE	INT	�ɿ�����
							IN_START_DATE	INT	��ʼ����
							IN_VALID_PERIOD	INT	��ͬ����
							IN_LINK_MAN	INT	��ϵ��
							IN_SERVICE_MAN	INT	�ͻ�����
							IN_CITY_SERIAL_NO	INT	�ƽ�ر��
							IN_TOUCH_TYPE	VARCHAR(40)	��ϵ��ʽ
							IN_TOUCH_TYPE_NAME	VARCHAR(30)	��ϵ��ʽ˵��
							IN_FEE_JK_TYPE	INT	0���轻��1�ӱ���ۣ�2���⽻
							IN_SUMMARY	VARCHAR(200)	����
							IN_INPUT_MAN	INT	����Ա
							
							IN_BANK_ACCT_TYPE	VARCHAR(10)	�����˻�����(9920)
							IN_BOUNS_FLAG INT = 1 1���Ҹ���2��ת�ݶ�  
	 * @throws BusiException
	 * @param vo
	 */
	void modi(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �깺---��ѯ��������Ϣ��
	 * 
	 * SP_QUERY_TCONTRACTSG	IN_BOOK_CODE	INT	����
							IN_SERIAL_NO	INT	���
							IN_PRODUCT_ID	INT	��ƷID
							IN_PRODUCT_CODE	VARCHAR(6)	��Ʒ���
							IN_CUST_NAME	VARCHAR(100)	�ͻ�����
							IN_CONTRACT_BH	VARCHAR(16)	��ͬ���							
							IN_CHECK_FLAG	INT	��˱�־1δ���2�����
							IN_INPUT_MAN	INT	����Ա
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listBySql(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �깺-----���
	 * 
	 * SP_CHECK_TCONTRACTSG	IN_SERIAL_NO	INT	���
							IN_INPUT_MAN	INT	����Ա
	 * @throws BusiException
	 */
	void checkApplyreachContract(ApplyreachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �깺��˻ָ�
	 * SP_CHECK_TCONTRACTSG_BACK  @IN_SERIAL_NO INT,
	 *                     		  @IN_INPUT_MAN INT 
	 * @throws BusiException
	 */
	void recheckApplyreachContract(ApplyreachVO vo) throws BusiException;

}