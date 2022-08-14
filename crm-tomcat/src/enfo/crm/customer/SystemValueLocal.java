package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RatingVO;

public interface SystemValueLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询系统打分取值信息
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
	 * 查询系统打分取值信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_tsystemvalue(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 增加系统打分取值信息
	 * @param vo
	 * @throws BusiException
	 */
	void append_tsystemvalue(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改系统打分取值信息
	 * @param vo
	 * @throws BusiException
	 */
	void modi_tsystemvalue(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除系统打分取值信息
	 * @param vo
	 * @throws BusiException
	 */
	void delete_tsystemvalue(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 获得客户评分的条件 （按金额，年龄，等）
	 * @param cust_id
	 * @param table_name
	 * @param feiter_name
	 * @return
	 * @throws BusiException
	 */
	List queryDataSource(Integer cust_id, String table_name, String feiter_name) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 按条件，获得系统打分的某个分值
	 * @IN_OPERAND_V_ID  	INT,                  --TSystemValue表ID
		@IN_SCORING_VALUE	INT
	 * @return
	 * @throws BusiException
	 */
	List queryScoing(Integer operand_v_id, Integer cust_id) throws BusiException;

	/**
	 * 给客户打分
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	void saveTCustScoreDetail(String cust_all_id, Integer input_man) throws Exception;

	/**
	 * 查询客户得分明细-数据
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryListBySqlDetail(RatingVO vo) throws BusiException;

}