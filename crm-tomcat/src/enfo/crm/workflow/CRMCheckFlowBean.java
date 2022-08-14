package enfo.crm.workflow;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;

@Component(value="cRMCheckFlow")
public class CRMCheckFlowBean extends CrmBusiExBean implements CRMCheckFlowLocal  {

/* (non-Javadoc)
 * @see enfo.crm.workflow.CRMCheckFlowLocal#actionFlow(java.lang.String, java.lang.String, java.lang.String)
 */
	@Override
	public void actionFlow(String object_id,String object_type,String action_flag) throws BusiException{
		String updateSql="";
		if(action_flag.equals("ActionFlow1010")){//…Í«Î…˙–ß
			updateSql="update TSTAFFREIMBURSE set STATUS='1010' where TSTAFFREIMBURSE_ID='"+object_id+"'";
		}else if(action_flag.equals("ActionFlow1020")){
			updateSql="update TSTAFFREIMBURSE set STATUS='1020' where TSTAFFREIMBURSE_ID='"+object_id+"'";
		}else if(action_flag.equals("ActionFlow1030")){
			updateSql="update TSTAFFREIMBURSE set STATUS='1030' where TSTAFFREIMBURSE_ID='"+object_id+"'";
		}
		super.executeSql(updateSql);
	}

	
}
