package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.RatingVO;

public interface SystemConditionLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯϵͳ���������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_tsystemcondition(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�ϵͳ���������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modi_tsystemcondition(RatingVO vo) throws BusiException;

}