package enfo.crm.affair;

import java.util.List;
import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.OperatorVO;
import enfo.crm.vo.TcustmanagersVO;
import enfo.crm.vo.TcustmanagertreeVO;

public interface TcustmanagersLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����ͻ����������Ϣ
	 * @param vo
		@IN_MANAGERID	int
		@IN_EXTENSION	varchar
		@IN_RECORDEXTENSION	varchar
		@IN_DUTYNAME	nvarchar
		@IN_PROVIDESERVICES	int
		@IN_INPUT_MAN	int
	 */
	void append(TcustmanagersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ����������Ϣ
	 * @param vo
		@IN_MANAGERID	int
		@IN_EXTENSION	varchar
		@IN_RECORDEXTENSION	varchar
		@IN_DUTYNAME	nvarchar
		@IN_PROVIDESERVICES	int
		@IN_INPUT_MAN	int
	 */
	void modi(TcustmanagersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���ͻ����������Ϣ
	 * @param vo
	 * 	@IN_MANAGERID	int
		@IN_INPUT_MAN	int
	 */
	void delete(TcustmanagersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ�ͻ����������Ϣ
	 * @param vo
		@IN_MANAGERID	int
		@IN_MANAGERNAME	nvarchar
		@IN_EXTENSION	varchar
		@IN_RECORDEXTENSION	nvarchar
		@IN_DUTYNAME	nvarchar
		@IN_PROVIDESERVICES	int
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(TcustmanagersVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ����������Ϣ
		@IN_MANAGERID	int
		@IN_MANAGERNAME	nvarchar
		@IN_EXTENSION	varchar
		@IN_RECORDEXTENSION	nvarchar
		@IN_DUTYNAME	nvarchar
		@IN_PROVIDESERVICES	int
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_query(TcustmanagersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����Ա����Ҳ�½���������������Ϣ
	 * 
	 * TcustmanagersVO ���ڿɸ���
	 * 
	 * @return
	 * @throws BusiException
	 */
	List operator_query(TcustmanagersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���νṹ��ѯ��Ϣ
	 * 
	 * @IN_MANAGERID	int
	 * @param depart_id
	 * @return
	 * @throws BusiException
	 */
	List listBySql(Integer depart_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��������
	 * @param dept
	 * @param input_operatorCode
	 * @throws Exception
	 */
	void tree_append(TcustmanagertreeVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�
	 * 
	 *  @IN_SERIAL_NO	int
		@IN_MANAGERID	int
		@IN_INPUT_MAN	int
	 * @throws Exception
	 */
	void tree_modi(TcustmanagertreeVO vo, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ��
	 * @IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
	 * @param vo
	 * 
	 * @throws Exception
	 */
	void tree_delet(TcustmanagertreeVO vo, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ���ÿͻ�����(��ǰ����Ա)�������¼���������������һ���������������������
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List getManagerList_nextLevel(Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ���ÿͻ�����(��ǰ����Ա)�������¼���������������һ���������������������
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List getManagerList_sameLevel(Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����Ȩ�޲�ͬ���ز�ͬ�Ľ����������й���Ȩ�ޣ��������пͻ��������û��ֻ���ز���Ա��Ϊ����ļ�¼
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List getManagerList_sortByAuth(Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ؿͻ������б��������Ѿ����ù������
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List getManagerList_forTree() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ���ؿͻ������б��������Ѿ����ù������
	 * @param vo
		@IN_MANAGERID	int
		@IN_MANAGERNAME	nvarchar
		@IN_EXTENSION	varchar
		@IN_RECORDEXTENSION	nvarchar
		@IN_DUTYNAME	nvarchar
		@IN_PROVIDESERVICES	int
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query_forTree(TcustmanagersVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����Ȩ-������Ȩ��ͬ���ز�ͬ�Ľ����
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List getManagerListAuth(Integer input_man, Integer all_flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���Ӳ��ż���ĿȨ��
	 * @param vo
	 * @param input_man
	 * @throws BusiException
	 */
	void addItemManagerId(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * �������غ���
	 * @param vo
	 * @throws BusiException
	 */
	void addManagerYchm(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * ��ѯ���غ���
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageManagerYchmList_query(TcustmanagersVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;
	
	/**
	 * �޸����غ���
	 * @param vo
	 * @throws BusiException
	 */
	void modiManagerYchm(TcustmanagersVO vo) throws BusiException;

	/**
	 * ��ѯ���غ���
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Map queryManagerYchm(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * ɾ�����غ���
	 * @param vo
	 * @throws BusiException
	 */
	void deleteychm(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * ��ѯ��Ȩ�޵�
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryTmanagerZshmOper(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * Ȩ�޹�����
	 * @param vo
	 * @throws BusiException
	 */
	void addTmanagerZshmext(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * ɾ��������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	public void deleteychmext(TcustmanagersVO vo) throws BusiException;
}