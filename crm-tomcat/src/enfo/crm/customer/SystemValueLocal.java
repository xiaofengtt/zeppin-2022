package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RatingVO;

public interface SystemValueLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯϵͳ���ȡֵ��Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_tsystemvalue(RatingVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯϵͳ���ȡֵ��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_tsystemvalue(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����ϵͳ���ȡֵ��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void append_tsystemvalue(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�ϵͳ���ȡֵ��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modi_tsystemvalue(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ��ϵͳ���ȡֵ��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delete_tsystemvalue(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ÿͻ����ֵ����� ���������䣬�ȣ�
	 * @param cust_id
	 * @param table_name
	 * @param feiter_name
	 * @return
	 * @throws BusiException
	 */
	List queryDataSource(Integer cust_id, String table_name, String feiter_name) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����������ϵͳ��ֵ�ĳ����ֵ
	 * @IN_OPERAND_V_ID  	INT,                  --TSystemValue��ID
		@IN_SCORING_VALUE	INT
	 * @return
	 * @throws BusiException
	 */
	List queryScoing(Integer operand_v_id, Integer cust_id) throws BusiException;

	/**
	 * ���ͻ����
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	void saveTCustScoreDetail(String cust_all_id, Integer input_man) throws Exception;

	/**
	 * ��ѯ�ͻ��÷���ϸ-����
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryListBySqlDetail(RatingVO vo) throws BusiException;

}