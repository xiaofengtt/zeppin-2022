package enfo.crm.system;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.LogListVO;

public interface LogListLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯϵͳ������־
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @return
	 * @throws BusiException
	 */
	IPageList listLogList(LogListVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����ѯ�������
	 * @param srcQuery
	 * @return
	 */
	String[] parseQuery(String srcQuery);

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ϵͳ������־
	 * @param busi_flag
	 * @param busi_name
	 * @param input_opCode
	 * @param summary
	 * @throws BusiException
	 */
	void addLog(Integer busi_flag, String busi_name, Integer input_opCode, String summary) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ݱ���ʱ��ӵ���־
	 * @param busi_flag
	 * @param backup_flag
	 * @param comment
	 * @param input_man
	 * @throws BusiException
	 */
	void backLog(Integer busi_flag, String backup_flag, String comment, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯϵͳ������־
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @return
	 * @throws BusiException
	 */
	IPageList getCustLog(LogListVO vo, int pageIndex, int pageSize) throws BusiException;

}