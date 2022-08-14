package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ChannelVO;

public interface ChannelLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * �������������Ϣ
	 * @param vo
	 * @throws BusiException 
	 * @IN_BOOK_CODE
	 * @IN_CHANNEL_CODE
	 * @IN_CHANNEL_NAME
	 * @IN_ADDRESS
	 * @IN_LINK_MAN
	 * @IN_ZIP
	 * @IN_PHONE
	 * @IN_FAX
	 * @IN_SUMMARY
	 * @IN_BANK_ID
	 * @IN_INPUT_MAN
	 */
	void append(ChannelVO vo) throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 *  @param vo
	 *  @throws BusiException 
	 *  �޸�����������Ϣ
	 *  @IN_CHANNEL_ID
	 *	@IN_CHANNEL_CODE
	 *	@IN_CHANNEL_NAME
	 *  @IN_ADDRESS
	 *	@IN_LINK_MAN
	 *	@IN_ZIP
	 *	@IN_PHONE
	 *	@IN_FAX
	 *	@IN_SUMMARY
	 *	@IN_INPUT_MAN
	 */
	void modi(ChannelVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException 
	 */
	void del(ChannelVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��������
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(ChannelVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�������� ��ҳ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryPage(ChannelVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�������� ά���ƻ���ҳ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryServicePlanPage(ChannelVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��������ά���ƻ�
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryServicePlan(ChannelVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����������ά���ƻ�
	 * @param vo
	 * @throws BusiException 
	 */
	void appendServicePlans(ChannelVO vo) throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 *  @param vo
	 *  @throws BusiException 
	 *  �޸���������ά���ƻ�
	 */
	void modiServicePlans(ChannelVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException 
	 * ɾ����������ά���ƻ�
	 */
	void delServicePlans(ChannelVO vo) throws BusiException;

	/**
	 * �쵼��ѯҳ��-��ҳ
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryPageAnalysis(ChannelVO vo, String[] totalColumn, int pageIndex, int pageSize, int flag)
			throws BusiException;

	/**
	 * �쵼��ѯҳ��-����
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryByAllAnalysis(ChannelVO vo, int flag) throws BusiException;

}