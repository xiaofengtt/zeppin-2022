 
package enfo.crm.system;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;

@Component(value="portal")
public class PortalBean extends enfo.crm.dao.CrmBusiExBean implements PortalLocal {	
	/* (non-Javadoc)
	 * @see enfo.crm.system.PortalLocal#updatePortalUserState(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void updatePortalUserState(String state,Integer opCode) throws BusiException {
		Object[] params = new Object[2];
		params[0] = state;
		params[1] = opCode;
		super.executeSql("UPDATE TOPERATOR SET PORTAL_WORKBENCH=? WHERE OP_CODE =? ",params); 		 
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.PortalLocal#updatePortalAdd(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void updatePortalAdd(String portalCodes,Integer opCode) throws BusiException {
		String[] ss = null;
		String portalCode = "";
		
		if(portalCodes == null){
			portalCodes  = "";
		}
		ss = portalCodes.split(",");
		
		if(ss!= null){
			for(int i=0;i<ss.length;i++){
				portalCode = "";
				
				if(ss[i] != null){
					portalCode = ss[i].trim();
				}
				
				if(!"".equals(portalCode) && portalCode.length() > 2){
					updatePortalClose(portalCode.substring(2),opCode, portalCode.startsWith("1#") ? "1" : "2");
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.PortalLocal#updatePortalClose(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public void updatePortalClose(String portalCode,Integer opCode,String visiable) throws BusiException {
		Object[] params = new Object[2];
		params[0] = portalCode;
		params[1] = opCode; 
		
		if(!"1".equals(visiable)){
			visiable = "2";
		}
		
		List list = super.listBySql("SELECT COUNT(*) CC FROM TPORTALUSER WHERE PORTAL_CODE =?  AND USER_CODE = ? ",params);		
		int conunt = ((Integer)((Map)list.get(0)).get("CC")).intValue();
		
		if(conunt > 0){
			super.executeSql("UPDATE TPORTALUSER SET VISIBLE = "+visiable+" WHERE   PORTAL_CODE =?  AND USER_CODE = ?",params);
		}else{
			super.executeSql("INSERT INTO TPORTALUSER(PORTAL_CODE,USER_CODE,VISIBLE,POSSORT,EXPAND) VALUES(?,?,"+visiable+",10,0)",params);
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.PortalLocal#updatePortalCollapse(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void updatePortalCollapse(String portalCode,Integer opCode) throws BusiException {
		Object[] params = new Object[2];
		params[0] = portalCode; 
		params[1] = opCode; 
		List list = super.listBySql("SELECT COUNT(*) CC FROM TPORTALUSER WHERE PORTAL_CODE =?  AND USER_CODE = ? ",params);
		int conunt = ((Integer)((Map)list.get(0)).get("CC")).intValue();
		if(conunt > 0){
			super.executeSql("UPDATE TPORTALUSER SET EXPAND = 0 WHERE  PORTAL_CODE =?  AND USER_CODE = ? ",params);
		}else{
			super.executeSql("INSERT INTO TPORTALUSER(PORTAL_CODE,USER_CODE,VISIBLE,POSSORT,EXPAND) VALUES(?,?,1,10,0)",params);
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.PortalLocal#updateProtalExpand(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void updateProtalExpand(String portalCode,Integer opCode) throws BusiException {
		Object[] params = new Object[2];
		params[0] = portalCode;
		params[1] = opCode; 
		List list = super.listBySql("SELECT COUNT(*) CC FROM TPORTALUSER WHERE PORTAL_CODE =?  AND USER_CODE = ? ",params);
		int conunt = ((Integer)((Map)list.get(0)).get("CC")).intValue();
		if(conunt > 0){
			super.executeSql("UPDATE TPORTALUSER SET EXPAND = 1 WHERE PORTAL_CODE =?  AND USER_CODE = ?  ",params);
		}else{
			super.executeSql("INSERT INTO TPORTALUSER(PORTAL_CODE,USER_CODE,VISIBLE,POSSORT,EXPAND) VALUES(?,?,1,10,1)",params);
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.PortalLocal#queryMyPortal(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List queryMyPortal(Integer bookCode,Integer opCode) throws BusiException {
		Object[] params = new Object[2];
		params[0] = opCode;
		params[1] = ""; 
		List list = super.listProcAll("{call SP_QUERY_MYPORTAL(?,?)}",params);
		List oList  = super.listBySql(" SELECT PORTAL_WORKBENCH FROM  TOPERATOR WHERE OP_CODE = "+opCode);
	 
		String leftColumn = "";
		String rightColumn = "";
		if(oList != null && oList.size() > 0){
			String state  = (String)((Map)oList.get(0)).get("PORTAL_WORKBENCH");
			if(state != null && !"".equals(state)){
				String[] columns = state.split("#");
				if(columns != null && columns.length > 0 && columns[0]!=null && columns[0].startsWith("0,")){
					leftColumn = columns[0].substring(2);
				}
				if(columns != null && columns.length > 1 && columns[1]!=null && columns[1].startsWith("1,")){
					rightColumn = columns[1].substring(2);
				}
			}		
		}
		List  allPortal  = new ArrayList();
		if(leftColumn!= null && !"".equals(leftColumn)){
			String[] leftPorts = leftColumn.split(",");
			if(leftPorts != null){
				for(int i=0; i<leftPorts.length; i++){
					String leftPortal = leftPorts[i];
					if(leftPortal!= null && leftPortal.trim().length() >2){
						leftPortal = leftPortal.trim().substring(2);
						for(int k=0; k<list.size(); ){
							Map map  = (Map)list.get(k);
							String productCode = (String)map.get("PORTAL_CODE");
							if(leftPortal.equals(productCode)){
								Map ttMap = (Map)list.remove(k);
								ttMap.put("POS_TYPE",new Integer(2));
								allPortal.add(ttMap);
							}else{
								k++;
							}
						}
						
					}
				}
			}
		}
		if(rightColumn!= null && !"".equals(rightColumn)){
			String[] rightPorts = rightColumn.split(",");
			if(rightPorts != null){
				for(int i=0; i<rightPorts.length; i++){
					String rightPortal = rightPorts[i];
					if(rightPortal!= null && rightPortal.trim().length() > 2){
						rightPortal = rightPortal.trim().substring(2);
						for(int k=0; k<list.size(); ){
							Map map  = (Map)list.get(k);
							String productCode = (String)map.get("PORTAL_CODE");
							if(rightPortal.equals(productCode)){
								Map ttMap = (Map)list.remove(k);
								ttMap.put("POS_TYPE",new Integer(3));
								allPortal.add(ttMap);
							}else{
								k++;
							}
						}
					}
				}
			}
		}
		allPortal.addAll(list);
		return allPortal;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.PortalLocal#queryPortalMenu3(java.lang.Integer, java.lang.String)
	 */
	@Override
	public List queryPortalMenu3(Integer op_code, String language) throws BusiException {
		Object[] params = new Object[2];
		params[0] = op_code;
		params[1] = language; 
		return super.listProcAll("{call SP_QUERY_PORTALMENU3(?,?)}",params);
	}
}