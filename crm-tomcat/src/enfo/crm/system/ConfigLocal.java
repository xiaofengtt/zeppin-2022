package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;

public interface ConfigLocal extends IBusiExLocal {

	/**
	 * ��ѯĳ����������µ�Ҫ�����ư�order_no����
	 * @param catalog_code ���ͱ���
	 * @param record_id �ж������޸ı�ʶ
	 * @param queryFlag ��ѯ����
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listElementOrderNO(String catalog_code, Integer record_id, String queryFlag) throws BusiException;

	/**
	 * ��ѯ��Ӧ������Ҫ����
	 * @ejb.interface-method view-type = "local"
	 */
	List listRegionParams(String region_code) throws BusiException;

	/**
	 * ��ѯ��Ӧ���������
	 * @ejb.interface-method view-type = "local"
	 */
	List listBySql(String catalog_code) throws BusiException;

	/**
	 * ��ѯ��Ӧ��Ҫ��������
	 * @param region_code �����
	 * @param item_code ��Ŀ����
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listRegionParamName(String region_code, String item_code) throws BusiException;

	/**
	 * ����Ҫ��Ӧ�ñ�����
	 * @param addField �����ֶ�
	 * @param addValue �����ֶ�ֵ
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void addConfigRecord(String addField, String addValue) throws BusiException;

	/**
	 *  �������ñ�����
	 * @ejb.interface-method view-type = "local"
	 */
	void addConfigData(String addField, String addValue) throws BusiException;

	/**
	 * ��ѯ���ñ�����
	 * @param relation_id config_record����ID
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listDataValues(String relation_id, String query_flag) throws BusiException;

	/**
	 * ��ȡĳ���������ֵ��
	 * @param interfacetype_code ����������
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listTableIdentity(String interfacetype_code) throws BusiException;

	/**
	 * ��ȡ��ǰģ����½���������Ϣ
	 * @param interfacetype_code ����������
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List getConfigInterface(String interfacetype_code) throws BusiException;

	/**
	 * ��ȡ��ǰģ����½��水ť��Ϣ
	 * @param interfacetype_code ����������
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List getConfigButton(String interfacetype_code) throws BusiException;

	/**
	 * ��ȡĳ����ı���
	 * @param interfacetype_code ����������
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listTableName(String interfacetype_code) throws BusiException;

	/**
	 * �޸����ñ�����
	 * @param updateValue �޸��ֶ�
	 * @param queryWhere where����
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void modConfigData(String updateValue, String queryWhere, Integer record_id) throws BusiException;

	/**
	 * �޸�Ӧ�ñ�����
	 * @param record_id
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void modConfigRecord(Integer record_id) throws BusiException;

	/**
	 * �޸�ʱ��ѯ�Ѿ����ڵ���������
	 * @param relation_id
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listExistDataValues(String relation_id, String query_flag, String catalog_code) throws BusiException;

	/**
	 * ��ʾ������Ч�ı����ʾ�ֶ�
	 * @param interfaceType_Id
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List tableFieldShowList(String interfaceType_code) throws BusiException;

	/**
	 * ��ʾ���в���ʾ�ı����ʾ�ֶ�
	 * @param interfaceType_Id
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List tableNoShowList(String interfaceType_code) throws BusiException;

	/**
	 * ��ѯ������Ч�Ĳ�ѯ�ֶ�
	 * @ejb.interface-method view-type = "local"
	 */
	List queryShowList(String interfaceType_code) throws BusiException;

	/**
	 * ��ѯҳ���б����ݰ�����ѯ��֮��Ĳ�ѯ����
	 * @param interfaceType_code ģ����
	 * @param tableName ����
	 * @param queryFieldCol ��ѯ���ѯ�ֶ��м�
	 * @param queryFieldValue ��ѯ���ѯ�ֶ���ֵ��
	 * @param conditionType ��Ҫ��ʾ���ֶεĲ�ѯ���ͼ�
	 * @param otherQueryStr ������ѯ��ѯ�������
	 * @return
	 * @throws Exception 
	 * @ejb.interface-method view-type = "local"
	 */
	IPageList showPageDataOther(String interfaceType_code, String tableName, String[] queryFieldCol,
			String[] queryFieldValue, String[] conditionType, String otherQueryStr, int pageIndex, int pageSize)
			throws Exception;

	/**
	 * �鿴ҳ������
	 * @param showSql ��ѯ���
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	IPageList getListPageData(String showSql, int pageIndex, int pageSize) throws BusiException;

	/**
	 * �鿴ҳ������
	 * @param tableName ����
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	IPageList previewPageData(String tableName, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ʾĳ��ҳ����������Ч���ֶ�
	 * @param vo
	 * @param edit_flag �������޸ı�ʶ��1��������2���޸�
	 * @param query_flag �����ֶ����ͱ�ʶ;1�������ֶΣ�2��δ�����ֶΣ�3�������ֶ�
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listVaildFieldOrderNO(String interfaceType_code, String edit_flag, String query_flag) throws BusiException;

	/**
	 * ��ѯ�鿴ҳ����Ҫ��ʾ���ֶ�
	 * @param vo ��ѯVO
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listVaildViewFieldOrderNO(String interfaceType_code) throws BusiException;

	/**
	 * ����ҳ��Ԫ��
	 * @param table_name ����
	 * @param addFieldName �����ֶμ���
	 * @param addFieldValue �����ֶ�ֵ����
	 * @param addType ����ʱ�����Ƿ��Զ����ɱ�ʶ 1�����£�2:������
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void addInterfaceField(String table_name, String[] showTypeStrs, String[] addFieldName, String[] addFieldValue,
			String[] updateFlagStrs) throws BusiException;

	/**
	 * �޸�ҳ������
	 * @param table_name
	 * @param addFieldName
	 * @param addFieldValue
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void modInterfaceField(String table_name, String[] showTypeStrs, String[] modFieldName, String[] modFieldValue,
			String identyityCol, String identityValue, String[] updateFlagStr) throws BusiException;

	/**
	 * ��ѯ�޸�ҳ����ʾ����
	 * @param sql ��ѯ���
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List getInfoPageData(String querySql) throws BusiException;

	/**
	 * ��ѯ�޸�ҳ����ʾ����
	 * @param table_name ����
	 * @param identity_code Ψһ�ֶ���
	 * @param identity_codeValue Ψһ�ֶ�ֵ
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List modPageShowData(String table_name, String identity_code, String identity_codeValue) throws BusiException;

	/**
	 * ɾ��ҳ������
	 * @param table_code ����
	 * @param identity_code ��������
	 * @param identity_value ����ֵ
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void delInfo(String table_code, String identity_code, String identity_value) throws BusiException;

	/**
	 * ɾ����������
	 * @param identity_value ����ֵ
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void delRelationInfo(String identity_value) throws BusiException;

	/**
	 * �����޸���־
	 * @param catalog_Code ���ý������
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void modLogInfo(String catalog_Code, String table_code, String identityCode, String identity_code,
			String fieldColName, String fieldName, String beforeValue, String afterValue, String userID, String deptID)
			throws BusiException;

	/**
	 * �����ֶα��д���ĳ���������ͱ���ļ���
	 * @param interfacetype_code �������ͱ���
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List interfaceFieldList(String interfacetype_code) throws BusiException;

	/**
	 * �����ֶα��д���ĳ���������ͱ���ļ���
	 * @param region_code �������ͱ���
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List interfaceRegionList(String region_code) throws BusiException;

	/**
	 * ����Ԫ�ر��д��ڽ��渨�����ͱ���ļ���
	 * @param catalog_code
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List configElementList(String catalog_code) throws BusiException;

	/**
	 * �������ñ���ĳ���������ͱ���͹���ID��Ӧ���ݼ���
	 * @param catalog_code �������ͱ���
	 * @param relation_id ����ID
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List configDataList(String catalog_code, String relation_id) throws BusiException;

	/**
	 * ĳ������������ĳ���ֶα���ĸ���
	 * @param codeName �ֶα���
	 * @param interfaceType_code ��������
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List interfaceFieldNums(String codeName, String interfaceType_code) throws BusiException;

	/**
	 * ĳ����������������ĳ��Ҫ�ر���ĸ���
	 * @param elementCode Ҫ�ر���
	 * @param catalogCode ������������
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List configElementNums(String elementCode, String catalogCode) throws BusiException;

	/**
	 * ȡģ���������е���Ϣ
	 * @param showType
	 * @param codeName
	 * @param showSql
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List showSelectInfo(String showType, String codeName, String showSql) throws BusiException;

	/**
	 * ȡ���˵���Ӧ����Ϣ
	 * @param MenuID
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List showMenuInfo(String MenuID, String opCode, String product_type) throws BusiException;

	/**
	 * ȡ���˵����е���Ϣ
	 * @param roleID
	 * @param existFlag
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listAllMenu(String roleID, String existFlag) throws BusiException;

	/**
	 * ȡ��ӡ�������е���Ϣ
	 * @param formCode
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List queryPrintContentList(String formCode) throws BusiException;

	/**
	 * ȡϵͳ���˵����е���Ϣ
	 * @param MenuID
	 * @param MenuType
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List getMenuInfo(String MenuID, String MenuType, String opCode, String productType) throws BusiException;

	//@Override
	void remove();

}