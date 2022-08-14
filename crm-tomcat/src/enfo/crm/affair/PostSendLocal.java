package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PostSendVO;

public interface PostSendLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����
	 * @param vo
	    @IN_INPUT_DATE       INTEGER,          --�ʼ�����
	    @IN_PRODUCT_ID       INTEGER,          --��ƷID
	    @IN_CONTRACT_SUB_BH  NVARCHAR(200),    --��ͬ���
	    @IN_POST_NO          NVARCHAR(30),     --�ʼĵ���
	    @IN_POST_CONTENT     NVARCHAR(30),     --�ʼ����ݣ�1��������2ȷ�ϵ�3������4��ʱ��Ϣ��¶5��ֹ����6�����������ж��������|�����
	    @IN_SUMMARY          NVARCHAR(500),    --��ע
	 */
	void append(PostSendVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�
	 * @param vo
	   @IN_INPUT_DATE       INTEGER,           --�ʼ�����
	   @IN_PRODUCT_ID       INTEGER,           --��ƷID
	   @IN_CONTRACT_SUB_BH  NVARCHAR(200),    --��ͬ���
	   @IN_POST_NO           NVARCHAR(30),     --�ʼĵ���
	   @IN_POST_CONTENT  NVARCHAR(30),     --�ʼ����ݣ�1��������2ȷ�ϵ�3������4��ʱ��Ϣ��¶5��ֹ����6�����������ж��������|�����
	   @IN_SUMMARY           NVARCHAR(500),    --��ע
	   @IN_INPUT_MAN        INTEGER           --����Ա
	 */
	void modi(PostSendVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ
	    @IN_INPUT_DATE  INTEGER,           --�ʼ�����
	    @IN_PRODUCT_ID       INTEGER,          --��ƷID
	    @IN_CONTRACT_SUB_BH  NVARCHAR(200),    --��ͬ���
	    @IN_POST_NO          NVARCHAR(30),     --�ʼĵ���
	    @IN_POST_CONTENT     NVARCHAR(30)      --�ʼ����ݣ�1��������2ȷ�ϵ�3������4��ʱ��Ϣ��¶5��ֹ����6�����������ж��������|�����
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(PostSendVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ
	    @IN_INPUT_DATE  INTEGER,           --�ʼ�����
	    @IN_PRODUCT_ID       INTEGER,          --��ƷID
	    @IN_CONTRACT_SUB_BH  NVARCHAR(200),    --��ͬ���
	    @IN_POST_NO          NVARCHAR(30),     --�ʼĵ���
	    @IN_POST_CONTENT     NVARCHAR(30)      --�ʼ����ݣ�1��������2ȷ�ϵ�3������4��ʱ��Ϣ��¶5��ֹ����6�����������ж��������|�����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_query(PostSendVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ
	    @IN_PRODUCT_ID       INTEGER,          --��ƷID
	    @IN_CUST_NAME         NVARCHAR(50)='', --�ͻ�����
		@IN_CONTRACT_SUB_BH	 VARCHAR(80)=''	   --��ͬ���
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList query(PostSendVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�
	 * @param vo
	   @IN_INPUT_DATE       INTEGER,           --�ʼ�����
	   @IN_PRODUCT_ID       INTEGER,           --��ƷID
	   @IN_CONTRACT_SUB_BH  NVARCHAR(200),    --��ͬ���
	   @IN_POST_NO           NVARCHAR(30),     --�ʼĵ���
	   @IN_POST_CONTENT  NVARCHAR(30),     --�ʼ����ݣ�1��������2ȷ�ϵ�3������4��ʱ��Ϣ��¶5��ֹ����6�����������ж��������|�����
	   @IN_SUMMARY           NVARCHAR(500),    --��ע
	   @IN_INPUT_MAN        INTEGER           --����Ա
	   @IN_SERIAL_NO        INTEGER           --��¼���
	 */
	void batchModi(PostSendVO vo) throws BusiException;

}