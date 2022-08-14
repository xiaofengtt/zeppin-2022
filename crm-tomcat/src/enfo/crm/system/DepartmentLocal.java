package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.DepartmentVO;

public interface DepartmentLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯȫ��
	 * @return
	 * @throws Exception
	 */
	List listProcAll(DepartmentVO dept) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_TDEPARTMENT_TREE @IN_DEPART_ID INT
	 */
	List listAll(Integer depart_id) throws BusiException;

	//���νṹ��ѯ��Ϣ
	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_TDEPARTMENT_TREE @IN_DEPART_ID INT
	 */
	List listBySql(Integer depart_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����
	 * @throws Exception
	 */
	void append(DepartmentVO dept, Integer input_operatorCode) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�
	 * @throws Exception
	 */
	void modi(DepartmentVO dept, Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ��
	 * @param dept
	 * @throws Exception
	 */
	void delet(DepartmentVO dept, Integer input_man) throws Exception;

}