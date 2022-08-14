package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.RatingVO;

public interface SystemConditionLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询系统打分条件信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_tsystemcondition(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改系统打分条件信息
	 * @param vo
	 * @throws BusiException
	 */
	void modi_tsystemcondition(RatingVO vo) throws BusiException;

}