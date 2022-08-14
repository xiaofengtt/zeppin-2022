package enfo.crm.project;

import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;

@Component(value="levelAppRecord")
public class LevelAppRecordBean extends CrmBusiExBean implements LevelAppRecordLocal {
	/* (non-Javadoc)
	 * @see enfo.crm.project.LevelAppRecordLocal#initStartFlow(java.lang.String[])
	 */
	@Override
	public void initStartFlow(String[] sql) throws BusiException{
		super.batchUpdate(sql);
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.project.LevelAppRecordLocal#listCustomerInfo(java.lang.String)
	 */
	@Override
	public List listCustomerInfo(String sql) throws BusiException{
		List list = null;
		list = super.listBySql(sql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.project.LevelAppRecordLocal#getFieldShowList(java.lang.String)
	 */
	@Override
	public List getFieldShowList(String interfaceCode) throws BusiException{
		List list = null;
		String sql ="SELECT IC.*,ID.* FROM INTERFACE_CATALOG IC,INTERFACE_FIELD ID "+
					" WHERE IC.INTERFACETYPE_CODE=ID.INTERFACETYPE_CODE"+
					" and ID.INTERFACETYPE_CODE= '"+interfaceCode+"' AND ID.USE_STATE = '1'  order by ID.ORDER_NO";
		list = super.listBySql(sql);
		return list;
	}	
	
	//@Override
	/* (non-Javadoc)
	 * @see enfo.crm.project.LevelAppRecordLocal#remove()
	 */
	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}	
	

}
