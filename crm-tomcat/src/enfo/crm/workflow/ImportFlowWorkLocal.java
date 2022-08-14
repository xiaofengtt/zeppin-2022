package enfo.crm.workflow;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;

public interface ImportFlowWorkLocal extends IBusiExLocal {

	/**
	 * 
	 * @param ProjectId
	 * @param NodesStr
	 * @param LinesStr
	 * @param input_operatorCode
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void del_nodes_lines(String ProjectId, String NodesStr, String LinesStr, Integer input_operatorCode)
			throws BusiException;

	/**
	 * 
	 * @param projectId
	 * @param input_operatorCode
	 * @param keyFlag
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void del_workflow(String projectId, Integer input_operatorCode, boolean keyFlag) throws BusiException;

	/**
	 * 
	 * @param projectId
	 * @param content
	 * @param input_operatorCode
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void import_workflow_lines(String projectId, String[] content, Integer input_operatorCode) throws BusiException;

	/**
	 * 
	 * @param projectId
	 * @param content
	 * @param input_operatorCode
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void import_workflow_nodes(String projectId, String[] content, Integer input_operatorCode) throws BusiException;

	/**
	 * 
	 * @param projectId
	 * @param input_operatorCode
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void update_flow_nodelineobject(String projectId, Integer input_operatorCode) throws BusiException;

	/**
	 * 
	 * @param projectId
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	String list_WorkFlow(String projectId) throws BusiException;

	/**
	 * 
	 * @param ProjectId
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List list_nodes_disabled(String ProjectId) throws BusiException;

	//@Override
	void remove();

	//  查询流程处理轨迹图
	/**
	 * 
	 * @param projectId objectId objectType
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	String list_WorkFlowTrack(String projectId, String objectId, String objectType) throws BusiException;

}