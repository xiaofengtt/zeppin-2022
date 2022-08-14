package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.GradeTotalVO;

public interface GradeTotalLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�����÷�
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return pageList
	 */
	IPageList queryGradeTotal(GradeTotalVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List queryProjectID(GradeTotalVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��������÷�
	 * @param vo
	 * @throws BusiException
	 */
	void appendGradeTotal(GradeTotalVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ�������÷�
	 * @param vo
	 * @throws BusiException
	 */
	void delGradeTotal(GradeTotalVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��˿ͻ�����
	 * @param vo
	 * @throws BusiException
	 */
	void checkGradeTotal(GradeTotalVO vo) throws BusiException;

}