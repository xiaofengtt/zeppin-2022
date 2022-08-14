package enfo.crm.manager;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TmanagerteammembersVO;
import enfo.crm.vo.TmanagerteamsVO;

public interface TmanagerteamsLocal2 extends IBusiExLocal {

	/**
	 * �����Ŷӹ���
	 * @param vo
	 * @IN_TEAM_NO
		@IN_TEAM_NAME
		@IN_CREATE_DATE
		@IN_LEADER
		@IN_DESCRIPTION
		@IN_INPUT_MAN
		@OUT_TEAM_ID
	 */
	Integer append(TmanagerteamsVO vo) throws BusiException;

	/**
	 * �޸��Ŷӹ���
	 * @param vo
	 * 	@IN_TEAM_ID	int
		@IN_TEAM_NO	nvarchar
		@IN_TEAM_NAME	nvarchar
		@IN_CREATE_DATE	int
		@IN_LEADER	int
		@IN_DESCRIPTION	nvarchar
		@IN_INPUT_MAN	int
	 */
	void modi(TmanagerteamsVO vo) throws BusiException;

	/**
	 * ɾ���Ŷӹ���
	 * @param vo
		@IN_TEAM_ID	int
		@IN_INPUT_MAN	int
	 */
	void delete(TmanagerteamsVO vo) throws BusiException;

	/**
	 * ��ҳ��ѯ�Ŷӹ���
	 * @param vo
		@IN_TEAM_ID	int
		@IN_TEAM_NO	nvarchar
		@IN_TEAM_NAME	nvarchar
		@IN_BEGIN_DATE	int
		@IN_END_DATE	int
		@IN_LEADER_NAME	nvarchar
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(TmanagerteamsVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * ��ѯ�Ŷӹ���
		@IN_TEAM_ID	int
		@IN_TEAM_NO	nvarchar
		@IN_TEAM_NAME	nvarchar
		@IN_CREATE_DATE	int
		@IN_LEADER	int
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_query(TmanagerteamsVO vo) throws BusiException;

	/**
	 * �����Ŷӹ���-��Ա����
	 * @param vo
		@IN_TEAM_ID	nvarchar
		@IN_MANAGERID	nvarchar
		@IN_DESCRIPTION	nvarchar
		@IN_INPUT_MAN	int
	 */
	void append(TmanagerteammembersVO vo) throws BusiException;

	/**
	 * �޸��Ŷӹ���-��Ա����
	 * @param vo
	 * 	@IN_SERIAL_NO	int
		@IN_TEAM_ID	nvarchar
		@IN_MANAGERID	nvarchar
		@IN_DESCRIPTION	nvarchar
		@IN_INPUT_MAN	int
	 */
	void modi(TmanagerteammembersVO vo) throws BusiException;

	/**
	 * ɾ���Ŷӹ���-��Ա����
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
	 */
	void delete(TmanagerteammembersVO vo) throws BusiException;

	/**
	 * ��ҳ��ѯ�Ŷӹ���-��Ա����
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_TEAM_ID	int
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(TmanagerteammembersVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * ��ѯ�Ŷӹ���-��Ա����
		@IN_SERIAL_NO	int
		@IN_TEAM_ID	int
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_query(TmanagerteammembersVO vo) throws BusiException;

	/**
	 * ��ѯ���Ŷӹ���-��Ա����
		@IN_TEAM_ID	int
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_query1(Integer team_id) throws BusiException;

	/**
	 * ��ѯ���Ŷӹ���-��Ա����
		@IN_TEAM_ID	int
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_queryOp(Integer op_code) throws BusiException;

	/**
	 * �������Ŷ�
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List my_list_query(TmanagerteammembersVO vo) throws BusiException;

	/**
	 * @param team_id
	 * @param productid
	 * @return
	 * @throws BusiException
	 */
	List quotaMoneyQueryById(Integer team_id, Integer productid) throws BusiException;

}