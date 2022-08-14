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
	 * 新增客户经理基本信息
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
	 * 修改客户经理基本信息
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
	 * 删除客户经理基本信息
	 * @param vo
	 * 	@IN_MANAGERID	int
		@IN_INPUT_MAN	int
	 */
	void delete(TcustmanagersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询客户经理基本信息
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
	 * 查询客户经理基本信息
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
	 * 查询操作员，用也新建立经理管理基本信息
	 * 
	 * TcustmanagersVO 后期可改造
	 * 
	 * @return
	 * @throws BusiException
	 */
	List operator_query(TcustmanagersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 树形结构查询信息
	 * 
	 * @IN_MANAGERID	int
	 * @param depart_id
	 * @return
	 * @throws BusiException
	 */
	List listBySql(Integer depart_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增树级
	 * @param dept
	 * @param input_operatorCode
	 * @throws Exception
	 */
	void tree_append(TcustmanagertreeVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改
	 * 
	 *  @IN_SERIAL_NO	int
		@IN_MANAGERID	int
		@IN_INPUT_MAN	int
	 * @throws Exception
	 */
	void tree_modi(TcustmanagertreeVO vo, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除
	 * @IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
	 * @param vo
	 * 
	 * @throws Exception
	 */
	void tree_delet(TcustmanagertreeVO vo, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询出该客户经理(当前操作员)的所有下级经理，包括所有下一级别的主经理，不包括自身
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List getManagerList_nextLevel(Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询出该客户经理(当前操作员)的所有下级经理，包括所有下一级别的主经理，不包括自身
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List getManagerList_sameLevel(Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 根据权限不同返回不同的结果集，如果有共享权限，返回所有客户经理，如果没有只返回操作员作为经理的记录
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List getManagerList_sortByAuth(Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 返回客户经理列表，不包括已经设置过级别的
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List getManagerList_forTree() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页返回客户经理列表，不包括已经设置过级别的
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
	 * 快捷授权-根据授权不同返回不同的结果集
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List getManagerListAuth(Integer input_man, Integer all_flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 增加部门级项目权限
	 * @param vo
	 * @param input_man
	 * @throws BusiException
	 */
	void addItemManagerId(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * 新增隐藏号码
	 * @param vo
	 * @throws BusiException
	 */
	void addManagerYchm(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * 查询隐藏号码
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
	 * 修改隐藏号码
	 * @param vo
	 * @throws BusiException
	 */
	void modiManagerYchm(TcustmanagersVO vo) throws BusiException;

	/**
	 * 查询隐藏号码
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Map queryManagerYchm(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * 删除隐藏号码
	 * @param vo
	 * @throws BusiException
	 */
	void deleteychm(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * 查询有权限的
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryTmanagerZshmOper(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * 权限关联项
	 * @param vo
	 * @throws BusiException
	 */
	void addTmanagerZshmext(TcustmanagersVO vo) throws BusiException;
	
	/**
	 * 删除关联信息
	 * @param vo
	 * @throws BusiException
	 */
	public void deleteychmext(TcustmanagersVO vo) throws BusiException;
}