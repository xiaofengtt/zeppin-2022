package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.SystemInfoVO;

public interface SystemInfoLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询单条记录
	 * @param user_id
	 * @return
	 * @throws BusiException
	 */
	List listBySig(Integer user_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-05-30
	 * 查询单条记录
	 * @param system_id
	 * @return
	 * @throws BusiException
	 * SP_QUERY_TSYSTEMINFO @IN_USER_ID=0     SMALLINT
	 *                      @IN_SYS_ID=0     INT
	 */
	List listBySig1(Integer system_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改
	 * @param vo
	 * @param input_operatorCode
	 * @throws BusiException
	 */
	void modi(SystemInfoVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 备份数据库
	 */
	void backupDataBase(int flag, String backup_file, String comment, Integer op_code) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-06_18
	 * 根据用户ID查询用户记录
	 * @param system_id
	 * @return
	 * @throws BusiException
	 * SP_QUERY_TSYSTEMINFO @IN_USER_ID=0     SMALLINT
	 *                      @IN_SYS_ID=0     INT
	 */
	List loadUserByUserId(Integer user_id) throws BusiException;

}