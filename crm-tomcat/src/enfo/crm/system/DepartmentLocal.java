package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.DepartmentVO;

public interface DepartmentLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询全部
	 * @return
	 * @throws Exception
	 */
	List listProcAll(DepartmentVO dept) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_TDEPARTMENT_TREE @IN_DEPART_ID INT
	 */
	List listAll(Integer depart_id) throws BusiException;

	//树形结构查询信息
	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_TDEPARTMENT_TREE @IN_DEPART_ID INT
	 */
	List listBySql(Integer depart_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增
	 * @throws Exception
	 */
	void append(DepartmentVO dept, Integer input_operatorCode) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改
	 * @throws Exception
	 */
	void modi(DepartmentVO dept, Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除
	 * @param dept
	 * @throws Exception
	 */
	void delet(DepartmentVO dept, Integer input_man) throws Exception;

}