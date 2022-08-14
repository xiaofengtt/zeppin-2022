package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.HmdVO;

public interface IntrustHmdLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ������������ҳ CREATE PROCEDURE SP_AML_QUERY_TBLACKLIST
	 * 
	 * @IN_FULL_NAME_C NVARCHAR (60), --����ȫ��
	 * @IN_FOR_SHORT_C NVARCHAR (30), --���ļ��
	 * @IN_FULL_NAME_E NVARCHAR (80), --Ӣ��ȫ��
	 * @IN_FOR_SHORT_E NVARCHAR (30), --Ӣ�ļ��
	 * @IN_OTHER_LANG_NAME NVARCHAR (80), --������������
	 * @IN_CLASSIFICATION_NO INTEGER , --������,1�ֲ����ӡ�2��ֹ������ɢ��3�����Ʋõ�
	 * @IN_CATEGORY_NO INTEGER , --�����,1���ˡ�2��֯
	 * @IN_CARD_TYPE NVARCHAR (40), --֤������
	 * @IN_CARD_NO NVARCHAR (40), --֤�����
	 * @IN_COUNTRY VARCHAR(30), --��������
	 * @IN_EXPLANATION VARCHAR(200) --��Ҫ˵��
	 * 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList queryBlack(HmdVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ��˾������Ϣ�����б���ʾ
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_hmd(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ������Ϣ�����б���ʾ CREATE PROCEDURE SP_AML_QUERY_TALIAS
	 * 
	 * @IN_BIRTH_NAME_ID INTEGER --��ӦTBLACKLIST�е�SERIAL_NO
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_bm(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ������������Ϣ CREATE PROCEDURE SP_ADD_TBLACKLIST
	 * 
	 * @IN_FULL_NAME_C NVARCHAR (60), --����ȫ��
	 * @IN_FOR_SHORT_C NVARCHAR (30), --���ļ��
	 * @IN_FULL_NAME_E NVARCHAR (80), --Ӣ��ȫ��
	 * @IN_FOR_SHORT_E NVARCHAR (30), --Ӣ�ļ��
	 * @IN_OTHER_LANG_NAME NVARCHAR (80), --������������
	 * @IN_CLASSIFICATION_NO INTEGER , --������,1�ֲ����ӡ�2��ֹ������ɢ��3�����Ʋõ�
	 * @IN_CATEGORY_NO INTEGER , --�����
	 * @IN_CARD_TYPE NVARCHAR (40), --֤������
	 * @IN_CARD_NO NVARCHAR (40), --֤�����
	 * @IN_COUNTRY VARCHAR(30), --��������
	 * @IN_EXPLANATION VARCHAR(200), --��Ҫ˵��
	 * @IN_INPUT_MAN INT --����Ա
	 */
	Integer addBlack(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �޸ĺ����� CREATE PROCEDURE SP_AML_MODI_TBLACKLIST
	 * 
	 * @IN_SERIAL_NO INTEGER, --��ˮ��
	 * @IN_FULL_NAME_C NVARCHAR (60), --����ȫ��
	 * @IN_FOR_SHORT_C NVARCHAR (30), --���ļ��
	 * @IN_FULL_NAME_E NVARCHAR (80), --Ӣ��ȫ��
	 * @IN_FOR_SHORT_E NVARCHAR (30), --Ӣ�ļ��
	 * @IN_OTHER_LANG_NAME NVARCHAR (80), --������������
	 * @IN_CLASSIFICATION_NO INTEGER , --������,1�ֲ����ӡ�2��ֹ������ɢ��3�����Ʋõ�
	 * @IN_CATEGORY_NO INTEGER , --�����,1���ˡ�2��֯
	 * @IN_CARD_TYPE NVARCHAR (40), --֤������
	 * @IN_CARD_NO NVARCHAR (40), --֤�����
	 * @IN_COUNTRY VARCHAR(30), --��������
	 * @IN_EXPLANATION VARCHAR(200), --��Ҫ˵��
	 * @IN_INPUT_MAN INTEGER --����Ա
	 * @param vo
	 * @throws BusiException
	 */

	void modiBlack(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ���ӱ��� CREATE PROCEDURE SP_ADD_TALIAS
	 * 
	 * @IN_FULL_NAME_C NVARCHAR (60), --����ȫ��
	 * @IN_FOR_SHORT_C NVARCHAR (30), --���ļ��
	 * @IN_FULL_NAME_E NVARCHAR (80), --Ӣ��ȫ��
	 * @IN_FOR_SHORT_E NVARCHAR (30), --Ӣ�ļ��
	 * @IN_OTHER_LANG_NAME NVARCHAR (80), --������������
	 * @IN_BIRTH_NAME_ID INTEGER, --��ӦTBLACKLIST�е�SERIAL_NO
	 * @IN_CLASSIFICATION_NO NVARCHAR (10),
	 *                       --������,511701�ֲ����ӡ�511702��ֹ������ɢ��511703�����Ʋõ�
	 * @IN_CATEGORY_NO NVARCHAR (10), --�����,511801���ˡ�511802��֯
	 * @IN_INPUT_MAN INT --����Ա
	 * @param vo
	 * @throws BusiException
	 */

	void addTalias(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ɾ�������� CREATE PROCEDURE SP_AML_DEL_TBLACKLIST
	 * 
	 * @IN_SERIAL_NO INT, --���
	 * @IN_INPUT_MAN INT --����Ա
	 * @param vo
	 * @throws BusiException
	 */

	void removeBlack(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ɾ������
	 * 
	 * @param vo
	 * @throws BusiException
	 */
	void removeTalis(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ��˾������Ϣ�����б���ʾ
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryAll(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��������������пͻ������������Ϣƥ��ļ�¼
	 * 
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList checkCustList(Object params[], int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ�������ĵ�������ҳ CREATE PROCEDURE SP_AML_QUERY_TBLACKLIST_FILE
	 * 
	 * @IN_SERIAL_NO INTEGER,
	 * @IN_FILE_NAME NVARCHAR (60),--�ĵ�����
	 * @IN_CLASSIFICATION_NO NVARCHAR
	 *                       (10),--������,511701�ֲ�����������511702��ֹ������ɢ������511703�����Ʋ�����
	 * @IN_FILE_DATE NVARCHAR (8) --����
	 * 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList queryBlackFile(HmdVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �����Ʋ�������Ϣ CREATE PROCEDURE SP_ADD_TBLACKLIST_FILE
	 * 
	 * @IN_SERIAL_NO INT, --����
	 * @IN_FILE_NAME NVARCHAR (60), --�ĵ�����
	 * @IN_CLASSIFICATION_NO NVARCHAR (10),
	 *                       --������,511701�ֲ�����������511702��ֹ������ɢ������511703�����Ʋ�����
	 * @IN_INPUT_MAN INT, --����Ա
	 * @OUT_ID INT OUTPUT
	 */
	Integer addBlackListFile(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ɾ�������� CREATE PROCEDURE SP_AML_DEL_TBLACKLIST_FILE
	 * 
	 * @IN_SERIAL_NO INT, --���
	 * @IN_INPUT_MAN INT --����Ա
	 * @param vo
	 * @throws BusiException
	 */

	void removeBlackFile(HmdVO vo) throws BusiException;

}