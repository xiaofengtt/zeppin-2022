package enfo.crm.project;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;

public interface BusinessLogicLocal extends IBusiExLocal {

	/**
	 * ����ҵ��״̬
	 * @param object_id
	 * @param object_type
	 * @param action_flag
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void actionFlow(String object_id, String object_type, String action_flag) throws BusiException;

	/**
	 * ɾ��������Ϣ
	 * @param object_id
	 * @param object_type
	 * @param action_flag 
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void delObject(String object_id, String object_type, String action_flag) throws BusiException;

	/**
	 * ���¶�����Ϣ
	 * @param object_id
	 * @param object_type
	 * @param action_flag 
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void updateObject(String object_id, String object_type, String action_flag) throws BusiException;

	/**
	 * ��õ�ǰ�û��¾������н�ɫ
	 * @param userID
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listUserRole(String userID) throws BusiException;

	/**
	 * ��õ�ǰ�û��¾��н�ɫ�����е���ʾ����
	 * @param userID
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listAllWork(String userID, String roleID, String productType) throws BusiException;

	/**
	 * ��õ�ǰ�û��¾��н�ɫ�����е���ʾ���ݻ��ܺϼ�
	 * @param userID
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listWorkCount(String sql) throws BusiException;

	/**
	 * �������������ʾ����
	 * @param workNo
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listWorkTask(String workNo) throws BusiException;

	/**
	 * ��ʾ����Ա����δ���������ѽ�������ͳ��
	 * @param flowNodeFlag
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listFlowStat(int flowNodeFlag) throws BusiException;

	/**
	 * �����û����·ݺͽ�����־��ѯ
	 * @param opCode �û�ID
	 * @param queryMonth ��ѯ�·�
	 * @param flowNodeFlag 1δ�������̣�2�ѽ�������
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listFlowStatByUser(int opCode, String queryMonth, int flowNodeFlag) throws BusiException;

	/**
	 * ��������ͳ������
	 * @param userID
	 * @param genDate
	 * @param queryType
	 * @throws Exception
	 * @ejb.interface-method view-type = "local"
	 */
	List listFlowTask(String userID, String genDate, String queryType) throws BusiException;

	//@Override
	void remove();

	/**
	 * ��ɾ���лָ�����
	 * @param object_id
	 * @param object_type
	 * @param action_flag
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void resumeObject(String object_id, String object_type, String action_flag) throws BusiException;

	/**
	 * ���ݱ�����and������ѯ
	 * @ejb.interface-method view-type = "local"
	 */
	List listByTableCondition(String tableName, String andSqlCondition) throws BusiException;

	/**
	 * ��ò��ż���ĿȨ���б�
	 * @param userID
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listDepartItemQuery(String item_id) throws BusiException;

}