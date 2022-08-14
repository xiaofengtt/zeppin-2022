package enfo.crm.project;

import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;

@Component(value="businessLogic")
public class BusinessLogicBean extends enfo.crm.dao.CrmBusiExBean implements BusinessLogicLocal { 
	
	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#actionFlow(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void actionFlow(String object_id,String object_type,String action_flag) throws BusiException{
		String updateSql="";
		//这里一堆处理
		super.executeSql(updateSql);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#delObject(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void delObject(String object_id,String object_type,String action_flag) throws BusiException{
		String genSql="";
		//删除业务表的记录
		super.executeSql(genSql);
	  }

	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#updateObject(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateObject(String object_id,String object_type,String action_flag) throws BusiException{
		String genSql="";
		//业务表的处理
		super.executeSql(genSql);
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#listUserRole(java.lang.String)
	 */
	@Override
	public List listUserRole(String userID)throws BusiException{
		List list = null;
		String sql ="SELECT ROLE_ID FROM TOPROLE WHERE OP_CODE = '"+userID+"' order by ROLE_ID";
		list = super.listBySql(sql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#listAllWork(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List listAllWork(String userID,String roleID,String productType)throws BusiException{
		List list = null;
		String sql ="SELECT * FROM WORKINFO_CONFIG "+
				" where exists (select 1 from toprole where op_code ='"+userID+"' and WORKINFO_CONFIG.show_role like '%'+cast(role_id as nvarchar)+'%')"+
				" and Use_State='1' "+
			//	" and product_type='"+productType+"' "+
				" order by ORDER_NO";
		list = super.listBySql(sql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#listWorkCount(java.lang.String)
	 */
	@Override
	public List listWorkCount(String sql)throws BusiException{
		List list = null;
		list = super.listBySql(sql);
		return list;
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#listWorkTask(java.lang.String)
	 */
	@Override
	public List listWorkTask(String workNo)throws BusiException{
		List list = null;
		String sql="select TM.MENU_ID,TM.MENU_NAME,TM.MENU_DES,TM.LINK_URL,WC.WORK_CONDITION"
			+" from WORKINFO_CONFIG WC,CONFIG_MENU TM where WC.Work_NO='"+workNo+"' and WC.Menu_ID=TM.Menu_ID and WC.Use_State='1'";
		list = super.listBySql(sql);
		return list;
	}	
		
	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#listFlowStat(int)
	 */
	@Override
	public List listFlowStat(int flowNodeFlag)throws BusiException{
		List list = null;
		Object[] params = new Object[1];
		params[0] = new Integer(flowNodeFlag);
		list = super.listBySql("{call SP_FLOW_STAT01(?)}",params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#listFlowStatByUser(int, java.lang.String, int)
	 */
	@Override
	public List listFlowStatByUser(int opCode, String queryMonth, int flowNodeFlag)throws BusiException{
		List list = null;
		Object[] params = new Object[3];
		params[0] = new Integer(opCode);
		params[1] = queryMonth;
		params[2] = new Integer(flowNodeFlag);
		list = super.listBySql("{call SP_FLOW_STAT01_USER(?,?,?)}",params);
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#listFlowTask(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List listFlowTask(String userID,String genDate,String queryType)throws BusiException{
		List list = null;
		Object[]param = new Object[3];
		param[0] = Utility.trimNull(userID);
		param[1] = Utility.trimNull(genDate);
		param[2] = Utility.trimNull(queryType);
		//System.out.println("userID:"+userID);
		//System.out.println("genDate:"+genDate);		
		//System.out.println("queryType:"+queryType);		
		list = super.listBySql("{call SP_FLOW_COUNT01(?,?,?)}",param);
		return list;		
	}	
	
	
	//@Override
	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#remove()
	 */
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#resumeObject(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void resumeObject(String object_id,String object_type,String action_flag) throws BusiException{
		String genSql="";
		//处理业务表
		super.executeSql(genSql);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#listByTableCondition(java.lang.String, java.lang.String)
	 */
	@Override
	public List listByTableCondition(String tableName, String andSqlCondition) throws BusiException{
		String querySql = "select * from " + tableName +" where 1=1 ";
		querySql += andSqlCondition;
		return super.listBySql(querySql);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.project.BusinessLogicLocal#listDepartItemQuery(java.lang.String)
	 */
	@Override
	public List listDepartItemQuery(String item_id)throws BusiException{
		List list = null;
		String sql ="SELECT ManagerID,ManagerName FROM DEPTITEM_ROLE A,TCustManagers B "+
				" WHERE A.OP_CODE = B.ManagerID AND ITEM_ID = "+ item_id;
		list = super.listBySql(sql);
		return list;
	}

}
