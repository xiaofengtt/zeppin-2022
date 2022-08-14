package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RemindersVO;

public interface RemindersLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����
	 * @param vo
		@IN_SCHEDULE_DATE	int
		@IN_CONTENT	nvarchar
		@IN_INPUT_MAN	int
	 */
	void append(RemindersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_SCHEDULE_DATE	int
		@IN_CONTENT	nvarchar
		@IN_CHECK_FLAG	int
		@IN_INPUT_MAN	int
	 */
	void modi(RemindersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ��
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
	 */
	void delete(RemindersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
		@IN_BEGIN_DATE	int
		@IN_END_DATE	int
		@IN_CHECK_FLAG	int
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(RemindersVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ
		@IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
		@IN_BEGIN_DATE	int
		@IN_END_DATE	int
		@IN_CHECK_FLAG	int
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_query(RemindersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ Ա���ճ�
	 * @param vo
		 @IN_OP_CODE             INT,              --Ա�����
	     @IN_BEGIN_DATE          INT = 0,          --��ʼʱ��
	     @IN_END_DATE            INT = 0,          --����ʱ��
	     @IN_SCHEDULE_TYPE       INT,              --�ճ����� --Լ��/�/�ƻ�         
	     @IN_INPUT_MAN           INT               --����Ա
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelistStaffSchedule(RemindersVO vo, int pageIndex, int pageSize) throws BusiException;

}