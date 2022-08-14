package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoMoneyTaskVO;

public interface TcoMoneyTaskLocal extends IBusiExLocal {

	/**
	 * ��ѯ--��ҳ���տ�ƻ������б�
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoMoneyTaskVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ����ͬ�Ǽ��б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoMoneyTaskVO vo) throws BusiException;

	/**
	 * �����տ����Ѽ�¼
	 * @param updateSql
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	void createMoneyTask(TcoMoneyTaskVO vo) throws BusiException;

}