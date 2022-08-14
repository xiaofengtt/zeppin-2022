package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustLevelAuthVO;
import enfo.crm.vo.OperatorVO;
import enfo.crm.vo.TOperatorVO;
import enfo.crm.vo.TcustmanagersVO;

public interface OperatorLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ������Ϣ-����Ա��
	 * @author 
	 * @param vo
	 * @param inputOperatorCode
	 * @throws BusiException
	 *	@IN_OP_CODE     INT,                                      
		@IN_OP_NAME     VARCHAR(20),                              
		@IN_DEPART_ID   INT,                                      
		@IN_ROLE_ID     INT,                                      
		@IN_SUMMARY     VARCHAR(200),                             
		@IN_INPUT_MAN   INT,                                      
		@IN_ADDRESS     NVARCHAR (60) ,                           
		@IN_EMAIL       NVARCHAR (40) ,                           
		@IN_O_TEL       NVARCHAR (40) ,                           
		@IN_MOBILE      NVARCHAR (100),                           
		@IN_LOGIN_USER  NVARCHAR(20), -- ��¼�û���               
		@IN_CARD_TYPE   VARCHAR(10), --���֤��/֤���ļ�����(1108)
		@IN_CARD_ID     VARCHAR(30), --���֤��/֤���ļ�����      
		@IN_CARD_VALID_DATE  INT     --���֤��/֤���ļ���Ч����  
	
	
	 */
	void appendOperator(TOperatorVO vo, Integer inputOperatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ������Ϣ-��ѯԱ����Ϣ
	 * @author ³����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listOpAll(TOperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ������Ϣ-��ѯINTRUST����Ա���б�
	 * @author
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getToperIntrust(TOperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ������Ϣ-�޸�Ա����Ϣ
	 * @author 
	 * @param vo
	 * @param inputOperatorCode
	 * @throws BusiException
	 *  @IN_OP_CODE     INT,                                      
		@IN_OP_NAME     VARCHAR(20),                              
		@IN_DEPART_ID   INT,                                      
		@IN_ROLE_ID     INT,                                      
		@IN_SUMMARY     VARCHAR(200),                             
		@IN_INPUT_MAN   INT,                                      
		@IN_ADDRESS     NVARCHAR (60) ,                           
		@IN_EMAIL       NVARCHAR (40) ,                           
		@IN_O_TEL       NVARCHAR (40) ,                           
		@IN_MOBILE      NVARCHAR (100),                           
		@IN_INIT_FLAG   INT = 0,  -- 1 ��ʼ������                 
		@IN_LOGIN_USER  NVARCHAR(20),  -- ��¼�û���              
		@IN_CARD_TYPE   VARCHAR(10), --���֤��/֤���ļ�����(1108)
		@IN_CARD_ID     VARCHAR(30), --���֤��/֤���ļ�����      
		@IN_CARD_VALID_DATE  INT     --���֤��/֤���ļ���Ч����  
	 */
	void modiOperator(TOperatorVO vo, Integer inputOperatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ������Ϣ-�޸Ĳ���Աӳ���ϵ��
	 * @author 
	 * @param vo
	 * @param inputOperatorCode
	 * @throws BusiException
	 * @IN_CRM_Operator INT, --��ӦCRMϵͳ��TOperator.OP_CODE
	   @IN_INTRUST_Operator INT, --��ӦIntrustϵͳ��TOperator.OP_CODE
	   @IN_INTRUST_BOOKCODE INT, --����Ա��¼�󣬲�������Intrustϵͳ�󣬸��ֶα��������Intrustϵͳ���ĸ�����
	   @IN_ETRUST_Operator INT, --��ӦeTrustϵͳ��TOperator.OP_CODE
	   @IN_INPUT_MAN   INT��  
	 */
	void modiTopertorMap(TOperatorVO vo, Integer inputOperatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ������Ϣ-ɾ��Ա����Ϣ
	 * @author ³����
	 * @param vo
	 * @param input_man
	 * @throws BusiException
	 */
	void delete(TOperatorVO vo, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ������Ϣ-��ҳ��ѯ
	 * @author ³����
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listProcPage(TOperatorVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ���û��Ľ�ɫ
	 * 
	 * @author ³����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listSerno(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����Ա����ɫ
	 * @param vo
	 * @param input_man
	 * @throws BusiException
	 */
	void appendOperator2(OperatorVO vo, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸ĵ�¼����
	 * @author TSG 
	 * 
	 * SP_MODI_PASSWORD @IN_OP_CODE INT,
						@IN_OLD_PASSWORD VARCHAR(40),
						@IN_NEW_PASSWORD VARCHAR(40),
						@OUT_RET_CODE INT OUTPUT
	 * */
	void changePass(Integer opcode, String old_password, String new_pass1, String new_pass2) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸ĵ�¼����
	 * @author TSG
	 * 
	 * SP_MODI_LOGIN_USER @IN_LOGIN_USER     NVARCHAR(20),
						  @IN_NEW_LOGIN_USER NVARCHAR(20),
						 -- @OUT_RET_CODE INT OUTPUT
	 * */
	void changeLoginUser(String old_login_user, String new_login_user) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��ǰ�������û������Է�ҳ����ʽ��ʾ
	 * @author ���Ǿ�
	 * @param pageIndex ��ǰ�ĵڼ�ҳ
	 * @param pageSize ÿҳ��ʾ�ļ�¼��
	 * @return
	 * @throws BusiException
	*/
	IPageList listOnLineAllOp(int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ʼ��������ʾ
	 * @param op_code
	 * @param book_code
	 * @throws Exception
	 */
	void updateBookCode(Integer op_code) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �˳���½
	 * @param op_code
	 * @throws Exception
	 */
	void logOut(Integer op_code) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����login_user��ѯ��½Ա����Op_Code
	 */
	List listOpCodeByUser(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_LOGIN @IN_OP_CODE SMALLINT,
	            @IN_PASSWORD VARCHAR(40),
	            @IN_IP_ADDRESS VARCHAR(15),
				--@OUT_BOOK_CODE TINYINT OUTPUT,
	            @OUT_RET_CODE INT OUTPUT
	            
			    @OUT_RET_CODE = 100
			    @OUT_RET_CODE =  @V_RET_CODE-1  -- ����Ա������
			    @OUT_RET_CODE = @V_RET_CODE-2  -- ���벻��           
	 * */
	Integer loginSystem(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����¼ ��¼����
	 * @param vo
	 * @throws BusiException 
	 */
	void loginSystem2(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_MAIN_MENURIGHT  @IN_OP_CODE  INT,
								@IN_MAIN_ID  CHAR(1),
								@OUT_FLAG    INT OUTPUT
	 */
	boolean checkMainMenu(String menu_id, String op_code) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�����
	 */
	Integer modiPassWord(Integer op_code, String oldPassWord, String newPassWord) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�����
	 */
	String modiPassWord(OperatorVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�����
	 */
	String modiLoginUser(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 *	2008-10-29 YZJ
	 * ͨ����Ա���š�������ѯ��Ա���ɲ����������б����׺š���������)����
	 * �洢���̣�SP_QUERY_OP_BOOKRIGHT  IN_OP_CODE	SMALL	Ա����
	 * @param opcode
	 * @return
	 * @throws BusiException
	 */
	List listCodeAcount(Integer opcode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listOpAll(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��������ڲ�����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList listInsideServiceTasks(OperatorVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��������ⲿ����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList listOutsideServiceTasks(OperatorVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�����ճ�����
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listScheDule(OperatorVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ������ǰ����Ա��ϵͳ��Ϣ
	 * @param vo
	 *     @IN_SERIAL_NO           INT,              --���
	 *     @IN_TITLE               NVARCHAR(200),    --��Ϣ����
	 *     @IN_FROM_OPCODE         INT,              --��Ϣ������
	 *     @IN_TO_OPCODE           INT,              --��Ϣ������   
	 *     @IN_DATE_START          INT,              --��Ϣ���ڣ���
	 *     @IN_DATE_END            INT,              --��Ϣ���ڣ�ֹ��
	 *     @IN_IS_READ             INT,              --�Ƿ����Ķ� 1��2��3�ѷ���
	 *     @IN_INPUT_MAN           INT               --����Ա
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listSysMessage(OperatorVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void addToperator_map(TOperatorVO vo, Integer inputOperatorCode) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void addSysMessage(OperatorVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void addCustLevelAuth(CustLevelAuthVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	void delCustLevelAuth(CustLevelAuthVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * IN_OP_CODE
		IN_PRODUCT_ID
		IN_LEVEL_ID
		IN_LEVEL_VALUE_ID
		IN_LEVEL_VALUE_NAME
		IN_INPUT_MAN
	
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList listCustLevelAuth(CustLevelAuthVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param menu_id
	 * @param op_code
	 * @param vierStr
	 * @throws Exception
	 */
	void addMenuView(String menu_id, Integer op_code, String vierStr) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param menu_id
	 * @param op_code
	 * @return
	 * @throws Exception
	 */
	String listMenuView(String menu_id, Integer op_code) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ���ù���̨ģ��
	 * @param opCode
	 * @return
	 * @throws Exception
	 */
	String getWorkBench(Integer opCode) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸ĳ��ù���̨ģ��
	 * @param opCode
	 * @param workbench
	 * @throws Exception
	 */
	void modiWorkBench(Integer opCode, String workbench) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ͬ������Ա
	 * @param opCode
	 * @throws BusiException
	 */
	void syschroOperator(Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryProductMarketing(TcustmanagersVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List queryProductMarketing1(TcustmanagersVO vo) throws BusiException;

	/**
	 * ��ѯ�������ڿͻ�
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustDue(Integer opCode, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯ�����ͻ�
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List listEfficientCust(Integer opCode) throws BusiException;

	/**
	 * �ͻ�����ҵ������
	 * @ejb.interface-method view-type = "local"
	 * @param opCode
	 * @return
	 * @throws BusiException
	 */
	List listCustManagersRank(Integer opCode) throws BusiException;

	/**
	 * �����Ŷ�ͳ��
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List StatTeamRank(TOperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �޸��û�ѡ��Ĳ�Ʒ
	 * 
	 * @throws BusiException
	 */
	void changeProduct(TOperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �޸ĳ�ʼҳ����ʾ����
	 * 
	 * @throws BusiException
	 */
	void changePagesize(Integer op_code, String pagesize) throws BusiException;

}