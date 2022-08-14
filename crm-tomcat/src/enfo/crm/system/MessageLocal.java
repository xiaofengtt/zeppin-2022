package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.MessageVO;

public interface MessageLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * ��ѯ�Զ��幤����ʾ----��ҳ��ʾ---����pagelist
	 * SP_QUERY_TTASKINFO 
						 @IN_INPUT_MAN	INT	����Ա
						 @IN_OP_CODE	INT	������
						 @IN_TITLE	VARCHAR(100)	����
						 @IN_SERIAL_NO	INT	���
	
	 *
	*/
	IPageList listPrivateMessage(MessageVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * ��ѯ�Զ��幤����ʾ----
	 * SP_QUERY_TTASKINFO
						 @IN_INPUT_MAN	INT	����Ա
						 @IN_OP_CODE	INT	������
						 @IN_TITLE	VARCHAR(100)	����
						 @IN_SERIAL_NO	INT	���
	
	 *
	*/
	List listPrivateMessage(MessageVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * ��ѯ�Ķ�����Ϣ---
	 * SP_QUERY_TTASKLIST @IN_REC_NO INT
	 *
	 */
	List listReaders(Integer rec_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-08
	 * ��ѯ֪ͨ�Ļظ���Ϣ
	 * SP_QUERY_TTASKREPLY  @IN_REC_NO INT
	 *
	*/
	List listReply(Integer rec_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-08
	 * ��ӹ�����ʾ�Ļظ���Ϣ
	 *
	 * SP_ADD_TTASKREPLY @IN_REC_NO	INT	��Ӧ������ʾ��Ϣ���¼��SERIAL_NO
						@IN_REPLY_STR	NTEXT	����
						@IN_INPUT_MAN	INT	����Ա
	
	 *
	 */
	void appendReply(Integer rec_no, String reply_str, Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * ��ӹ�����ʾ
	 *
	 * SP_ADD_TTASKINFO   @IN_BOOK_CODE	TINYINT	����
						 @IN_TITLE	VARCHAR(100)	����
						 @IN_INFO_STR	NTEXT	����
						 @IN_END_DATE	INT	֪ͨ��������
						 @IN_INPUT_MAN	INT	����Ա
						 @OUT_SERIAL_NO	INT	���
	
	 *
	 */
	Integer appendMessage(MessageVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * �޸Ĺ�����ʾ
	 *
	 * SP_MODI_TTASKINFO_COMM
	                @IN_SERIAL_NO  INT,
	                @IN_TITLE      VARCHAR(100),
	                @IN_INFO_STR   NTEXT,
	                @IN_END_DATE   INT,
	                @IN_INPUT_MAN  INT
	 *
	 */
	void modiMessage(MessageVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * ɾ��������ʾ�Ķ�����Ϣ
	 *
	 * SP_DEL_TTASKLIST_ALL @IN_REC_NO INT
	 *
	 */
	void delReaders(Integer rec_no) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * ��ӹ�����ʾ���Ķ�����Ϣ
	 *
	 * SP_ADD_TTASKLIST @IN_REC_NO INT,
	                   @IN_OP_CODE INT,
	                   @IN_INPUT_MAN INT
	 *
	 */
	void appendReaders(Integer rec_no, Integer op_code, Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * ɾ��������ʾ��Ϣ
	 *
	 * SP_DEL_TTASKINFO  @IN_SERIAL_NO INT,
	                    @IN_INPUT_MAN INT,
	                    @IN_FLAG      INT = 1 -- ��־��1 ɾ��  2 �ر� 3 �ؿ�
	 *
	 */
	void delMessage(Integer serial_no, Integer input_man, Integer flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * ��ѯ��ϵͳ�����Ĺ�����ʾ---
	 * SP_QUERY_TTASKINFO_COMM   
								@IN_OP_CODE	INT	����Ա
								@IN_TASK_TYPE	INT	90��ͬ�������ѡ�91��Ϣ����
								@IN_BUSI_ID	VARCHAR(10)	��ͬ���120301���120302����120305Ͷ��
								@IN_TITLE	VARCHAR(100)	����
								@IN_READ_FLAG	INT	1δ��2�Ѷ�0ȫ��
								@IN_SERIAL_NO	INT	TTASKLIST���е����
					
	 *
	*/
	List listSystemMessage(MessageVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-08
	 * �޸Ĺ�����ʾ���Ķ���־
	 *
	 * SP_MODI_TTASKINFO  @IN_SERIAL_NO	INT	���
						 @IN_READ_FLAG	INT	�Ķ���־��1δ����2�Ѷ�3������ʾ
						 @IN_INPUT_MAN	INT	Ա����
	
	 *
	 */
	void modiMessageReadFlag(Integer serial_no, Integer read_flag, Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ADD BY TSG 2008-08-11 ��̨����
	 * ��ѯ�Զ��幤����ʾ----��ҳ��ʾ---����pagelist
	 * SP_QUERY_TTASKINFO_1 @IN_BOOK_CODE 	INT	����
							   @IN_OP_CODE	INT	������
							   @IN_TITLE	VARCHAR(100)	����
							   @IN_SERIAL_NO	INT	���
	
	 *
	*/
	IPageList listPrivateMessage1(MessageVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ADD BY TSG 2008-08-11 ��̨����
	 * ��ѯ�Զ��幤����ʾ----��ҳ��ʾ---����list
	 * SP_QUERY_TTASKINFO_1 @IN_BOOK_CODE 	INT	����
						   @IN_OP_CODE	INT	������
						   @IN_TITLE	VARCHAR(100)	����
						   @IN_SERIAL_NO	INT	���
	
	 *
	*/
	List listPrivateMessage1(MessageVO vo) throws BusiException;

}